package gmoldes.components.client_invoice_check_list.components;

import gmoldes.components.ViewLoader;
import gmoldes.domain.client_invoice_checklist.dto.ClientInvoiceCheckListDTO;
import gmoldes.domain.payroll_checklist.dto.PayrollCheckListDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class ClientInvoiceCheckListData extends VBox {

    private static final String CLIENT_INVOICE_CHECKLIST_DATA_FXML = "/fxml/client_invoice_check_list/client_invoice_checklist_data.fxml";

    private Parent parent;

    private EventHandler<ActionEvent> actionEventEventHandlerMonthChanged;
    private EventHandler<ActionEvent> actionEventEventHandlerYearChanged;

    @FXML
    private ChoiceBox<ClientInvoiceCheckListDTO> month;
    @FXML
    private TextField year;
    @FXML
    private TableColumn<ClientInvoiceCheckListDTO, String> columnClientGMFullName;
    @FXML
    private TableColumn<ClientInvoiceCheckListDTO, String> columnSg21Code;
    @FXML
    private TableView<ClientInvoiceCheckListDTO> clientInvoiceTable;

    public ClientInvoiceCheckListData() {

        this.parent = ViewLoader.load(this, CLIENT_INVOICE_CHECKLIST_DATA_FXML);
    }

    @FXML
    public void initialize() {

        columnClientGMFullName.setCellValueFactory(new PropertyValueFactory<ClientInvoiceCheckListDTO,String>("clientGMFullName"));
        columnSg21Code.setCellValueFactory(new PropertyValueFactory<ClientInvoiceCheckListDTO,String>("sg21Code"));
        columnSg21Code.setStyle("-fx-alignment: CENTER;");


        this.month.setOnAction(this::onMonthChanged);
        this.year.setOnAction(this::onYearChanged);

        loadMonth();
    }

    public ChoiceBox<ClientInvoiceCheckListDTO> getMonth() {
        return month;
    }

    public void setMonth(ChoiceBox<ClientInvoiceCheckListDTO> month) {
        this.month = month;
    }

    public TextField getYear() {
        return year;
    }

    public void setYear(TextField year) {
        this.year = year;
    }

    public TableView<ClientInvoiceCheckListDTO> getClientInvoiceTable() {
        return clientInvoiceTable;
    }

    public void setClientInvoiceTable(TableView<ClientInvoiceCheckListDTO> clientInvoiceTable) {
        this.clientInvoiceTable = clientInvoiceTable;
    }

    private void loadMonth(){

        List<ClientInvoiceCheckListDTO> clientInvoiceCheckListDTOList = new ArrayList<>();
        for(Month month : Month.values()){
            ClientInvoiceCheckListDTO clientInvoiceCheckListDTO = new ClientInvoiceCheckListDTO();
            clientInvoiceCheckListDTO.setMonth(month);
            clientInvoiceCheckListDTOList.add(clientInvoiceCheckListDTO);
        }

        ObservableList monthNamesOS = FXCollections.observableArrayList(clientInvoiceCheckListDTOList);
        month.setItems(monthNamesOS);

    }

    private void onMonthChanged(ActionEvent event){
        if(getMonth() == null){
            return;
        }
        this.actionEventEventHandlerMonthChanged.handle(event);
    }

    private void onYearChanged(ActionEvent event){

        actionEventEventHandlerYearChanged.handle(event);

    }

    public void setOnMonthChanged(EventHandler<ActionEvent> actionEventEventHandlerMonthChanged){
        this.actionEventEventHandlerMonthChanged = actionEventEventHandlerMonthChanged;
    }

    public void setOnYearChanged(EventHandler<ActionEvent> actionEventEventHandlerYearChanged){
        this.actionEventEventHandlerYearChanged = actionEventEventHandlerYearChanged;
    }
}
