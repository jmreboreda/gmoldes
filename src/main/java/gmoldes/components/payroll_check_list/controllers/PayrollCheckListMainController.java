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
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.logging.Logger;

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
        payrollCheckListAction.setOnExitButton(this::onExit);
        payrollCheckListAction.setOnClipboardButton(this::onCopyToClipboard);

        this.payrollCheckListData.getMonth().setItems(FXCollections.observableArrayList(
                Month.JANUARY,
                Month.FEBRUARY,
                Month.MARCH,
                Month.APRIL,
                Month.MAY,
                Month.JUNE,
                Month.JULY,
                Month.AUGUST,
                Month.SEPTEMBER,
                Month.OCTOBER,
                Month.NOVEMBER,
                Month.DECEMBER
                )
        );

        this.payrollCheckListData.getYear().setText(Integer.toString(LocalDate.now().getYear()));
    }

    private void onMonthChanged(ActionEvent event){

        Month month = payrollCheckListData.getMonth().getSelectionModel().getSelectedItem();
        Integer year = Integer.parseInt(payrollCheckListData.getYear().getText());

        PayrollCheckList payrollCheckList = new PayrollCheckList();
        List<PayrollCheckListDTO> payrollCheckListDTOList = payrollCheckList.retrieveAllContractInForceInPeriod(month, year);

        ObservableList<PayrollCheckListDTO> payrollCheckListDTOS = FXCollections.observableArrayList(payrollCheckListDTOList);
        payrollCheckListData.getPayrollTable().setItems(payrollCheckListDTOS);
    }

    private void onExit(MouseEvent event){
        Stage stage = (Stage) payrollCheckListAction.getExitButton().getScene().getWindow();
        stage.close();
    }

    private void onCopyToClipboard(MouseEvent event){

        PayrollCheckList payrollCheckList = new PayrollCheckList();
        payrollCheckList.loadClipboard(payrollCheckListData.getMonth().getSelectionModel().getSelectedItem(), Integer.parseInt(payrollCheckListData.getYear().getText()));
    }


}
