package gmoldes.components.client_invoice_check_list.controllers;

import gmoldes.components.ViewLoader;
import gmoldes.components.client_invoice_check_list.components.ClientInvoiceCheckListAction;
import gmoldes.components.client_invoice_check_list.components.ClientInvoiceCheckListData;
import gmoldes.components.client_invoice_check_list.components.ClientInvoiceCheckListHeader;
import gmoldes.domain.client_invoice_checklist.ClientInvoiceCheckList;
import gmoldes.domain.client_invoice_checklist.dto.ClientInvoiceCheckListDTO;
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

public class ClientInvoiceCheckListMainController extends VBox {

    private static final Logger logger = Logger.getLogger(ClientInvoiceCheckListMainController.class.getSimpleName());
    private static final String CLIENT_INVOICE_CHECK_LIST_MAIN_FXML = "/fxml/client_invoice_check_list/client_invoice_checklist_main.fxml";

    private Parent parent;

    @FXML
    private ClientInvoiceCheckListHeader clientInvoiceCheckListHeader;
    @FXML
    private ClientInvoiceCheckListData clientInvoiceCheckListData;
    @FXML
    private ClientInvoiceCheckListAction clientInvoiceCheckListAction;


    public ClientInvoiceCheckListMainController() {
        logger.info("Initializing client invoice checklist Main fxml");
        this.parent = ViewLoader.load(this, CLIENT_INVOICE_CHECK_LIST_MAIN_FXML);
    }

    @FXML
    public void initialize() {

        clientInvoiceCheckListData.setOnMonthChanged(this::onMonthChanged);
        clientInvoiceCheckListData.setOnYearChanged(this::onYearChanged);
        clientInvoiceCheckListAction.setOnExitButton(this::onExit);
        clientInvoiceCheckListAction.setOnClipboardButton(this::onCopyToClipboard);

        this.clientInvoiceCheckListData.getYear().setText(Integer.toString(LocalDate.now().getYear()));
    }

    private void onYearChanged(ActionEvent event){

        try{

            Integer.parseInt(clientInvoiceCheckListData.getYear().getText());

        }catch (NumberFormatException e){

            clientInvoiceCheckListData.getYear().setText(String.valueOf(LocalDate.now().getYear()));
        }

        onMonthChanged(event);
    }

    private void onMonthChanged(ActionEvent event){

        Map<String, ClientInvoiceCheckListDTO> withoutClientGMDuplicates = new HashMap<>();

        Month month = clientInvoiceCheckListData.getMonth().getSelectionModel().getSelectedItem().getMonth();
        Integer year = Integer.parseInt(clientInvoiceCheckListData.getYear().getText());

        ClientInvoiceCheckList clientInvoiceCheckList = new ClientInvoiceCheckList();
        List<ClientInvoiceCheckListDTO> clientInvoiceCheckListDTOList = clientInvoiceCheckList.findAllClientGMWithInvoicesToClaimInPeriod(month, year);
        for(ClientInvoiceCheckListDTO clientInvoiceCheckListDTO : clientInvoiceCheckListDTOList){
            withoutClientGMDuplicates.put(clientInvoiceCheckListDTO.getClientGMFullName(), clientInvoiceCheckListDTO);
        }

        List<ClientInvoiceCheckListDTO> withoutDuplicatesClientInvoiceCheckListDTO = new ArrayList<>();
        for (Map.Entry<String, ClientInvoiceCheckListDTO> itemMap : withoutClientGMDuplicates.entrySet()) {
            withoutDuplicatesClientInvoiceCheckListDTO.add(itemMap.getValue());
        }

        Collator primaryCollator = Collator.getInstance(new Locale("es","ES"));
        primaryCollator.setStrength(Collator.PRIMARY);

        List<ClientInvoiceCheckListDTO> orderedClientInvoiceCheckListDTO = withoutDuplicatesClientInvoiceCheckListDTO
                .stream()
                .sorted(Comparator.comparing(ClientInvoiceCheckListDTO::getClientGMFullName, primaryCollator)).collect(Collectors.toList());

        ObservableList<ClientInvoiceCheckListDTO> clientInvoiceCheckListDTOS = FXCollections.observableArrayList(orderedClientInvoiceCheckListDTO);
        clientInvoiceCheckListData.getClientInvoiceTable().setItems(clientInvoiceCheckListDTOS);

        clientInvoiceCheckListAction.getClipboardCopyButton().setDisable(false);
    }

    private void onExit(MouseEvent event){
        Stage stage = (Stage) clientInvoiceCheckListAction.getExitButton().getScene().getWindow();
        stage.close();
    }

    private void onCopyToClipboard(MouseEvent event){
        ClientInvoiceCheckList clientInvoiceCheckList = new ClientInvoiceCheckList();

        clientInvoiceCheckList.loadClipboard(clientInvoiceCheckListData.getClientInvoiceTable().getItems());

        clientInvoiceCheckListAction.getClipboardCopyButton().setDisable(true);
    }


}
