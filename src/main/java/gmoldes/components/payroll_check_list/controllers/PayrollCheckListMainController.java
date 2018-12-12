package gmoldes.components.payroll_check_list.controllers;

import gmoldes.components.ViewLoader;
import gmoldes.components.payroll_check_list.components.PayrollCheckListAction;
import gmoldes.components.payroll_check_list.components.PayrollCheckListData;
import gmoldes.components.payroll_check_list.components.PayrollCheckListHeader;
import gmoldes.domain.payroll_checklist.PayrollCheckList;
import gmoldes.domain.payroll_checklist.dto.PayrollCheckListDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.Collator;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PayrollCheckListMainController extends VBox {

    private static final Logger logger = Logger.getLogger(PayrollCheckListMainController.class.getSimpleName());
    private static final String PAYROLL_CHECK_LIST_MAIN_FXML = "/fxml/payroll_check_list/payroll_checklist_main.fxml";

    private Parent parent;

    @FXML
    private PayrollCheckListHeader payrollCheckListHeader;
    @FXML
    private PayrollCheckListData payrollCheckListData;
    @FXML
    private PayrollCheckListAction payrollCheckListAction;


    public PayrollCheckListMainController() {
        logger.info("Initializing PayrollCheckList Main fxml");
        this.parent = ViewLoader.load(this, PAYROLL_CHECK_LIST_MAIN_FXML);
    }

    @FXML
    public void initialize() {

        payrollCheckListData.setOnMonthChanged(this::onMonthChanged);
        payrollCheckListData.setOnYearChanged(this::onYearChanged);
        payrollCheckListAction.setOnExitButton(this::onExit);
        payrollCheckListAction.setOnClipboardButton(this::onCopyToClipboard);

        this.payrollCheckListData.getYear().setText(Integer.toString(LocalDate.now().getYear()));
    }

    private void onYearChanged(ActionEvent event){

        try{

            Integer.parseInt(payrollCheckListData.getYear().getText());

        }catch (NumberFormatException e){

            payrollCheckListData.getYear().setText(String.valueOf(LocalDate.now().getYear()));
        }

        onMonthChanged(event);
    }

    private void onMonthChanged(ActionEvent event){

        Map<String, PayrollCheckListDTO> withoutPersonDuplicates = new HashMap<>();

        Month month = payrollCheckListData.getMonth().getSelectionModel().getSelectedItem().getMonth();
        Integer year = Integer.parseInt(payrollCheckListData.getYear().getText());

        PayrollCheckList payrollCheckList = new PayrollCheckList();
        List<PayrollCheckListDTO> payrollCheckListDTOList = payrollCheckList.retrieveAllContractInForceInPeriod(month, year);
        for(PayrollCheckListDTO payrollCheckListDTO : payrollCheckListDTOList){
            withoutPersonDuplicates.put(payrollCheckListDTO.getWorkerFullName(), payrollCheckListDTO);
        }

        List<PayrollCheckListDTO> withoutDuplicatesPayrollCheckListDTO = new ArrayList<>();
        for (Map.Entry<String, PayrollCheckListDTO> itemMap : withoutPersonDuplicates.entrySet()) {
            withoutDuplicatesPayrollCheckListDTO.add(itemMap.getValue());
        }

        Collator primaryCollator = Collator.getInstance(new Locale("es","ES"));
        primaryCollator.setStrength(Collator.PRIMARY);

        List<PayrollCheckListDTO> orderedPayrollCheckListDTO = withoutDuplicatesPayrollCheckListDTO
                .stream()
                .sorted(Comparator.comparing(PayrollCheckListDTO::getEmployerFullName, primaryCollator)).collect(Collectors.toList());

        ObservableList<PayrollCheckListDTO> payrollCheckListDTOS = FXCollections.observableArrayList(orderedPayrollCheckListDTO);
        payrollCheckListData.getPayrollTable().setItems(payrollCheckListDTOS);

        payrollCheckListAction.getClipboardCopyButton().setDisable(false);
    }

    private void onExit(MouseEvent event){
        Stage stage = (Stage) payrollCheckListAction.getExitButton().getScene().getWindow();
        stage.close();
    }

    private void onCopyToClipboard(MouseEvent event){
        PayrollCheckList payrollCheckList = new PayrollCheckList();

        payrollCheckList.loadClipboard(payrollCheckListData.getPayrollTable().getItems());

        payrollCheckListAction.getClipboardCopyButton().setDisable(true);
    }


}
