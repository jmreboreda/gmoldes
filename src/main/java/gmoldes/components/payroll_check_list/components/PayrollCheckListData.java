package gmoldes.components.payroll_check_list.components;

import gmoldes.components.ViewLoader;
import gmoldes.domain.payroll_checklist.dto.PayrollCheckListDTO;
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
import java.time.format.TextStyle;
import java.util.Locale;

public class PayrollCheckListData extends VBox {

    private static final String PAYROLL_CHECKLIST_DATA_FXML = "/fxml/payroll_check_list/payrollchecklist_data.fxml";

    private Parent parent;

    private EventHandler<ActionEvent> actionEventEventHandlerMonthChanged;
//    private EventHandler<ActionEvent> actionEventEventHandlerClientSelectorAction;
//    private EventHandler<ActionEvent> actionEventEventHandlerContractSelectorAction;


    @FXML
    private ChoiceBox<Month> month;
    @FXML
    private TextField year;
    @FXML
    private TableColumn<PayrollCheckListDTO, String> columnEmployer;
    @FXML
    private TableColumn<PayrollCheckListDTO, String> columnWorker;
    @FXML
    private TableView<PayrollCheckListDTO> payrollTable;

    public PayrollCheckListData() {

        this.parent = ViewLoader.load(this, PAYROLL_CHECKLIST_DATA_FXML);
    }

    @FXML
    public void initialize() {

        columnEmployer.setCellValueFactory(new PropertyValueFactory<PayrollCheckListDTO,String>("employerFullName"));
        columnWorker.setCellValueFactory(new PropertyValueFactory<PayrollCheckListDTO,String>("workerFullName"));

        this.month.setOnAction(this::onMonthChanged);

    }

    public ChoiceBox<Month> getMonth() {
        return month;
    }

    public void setMonth(ChoiceBox<Month> month) {
        this.month = month;
    }

    public TextField getYear() {
        return year;
    }

    public void setYear(TextField year) {
        this.year = year;
    }

    public TableView<PayrollCheckListDTO> getPayrollTable() {
        return payrollTable;
    }

    public void setPayrollTable(TableView<PayrollCheckListDTO> payrollTable) {
        this.payrollTable = payrollTable;
    }

    @Override
    public String toString(){

        return getMonth().getSelectionModel().getSelectedItem().getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    private void onMonthChanged(ActionEvent event){
        if(getMonth() == null){
            return;
        }
        this.actionEventEventHandlerMonthChanged.handle(event);
    }



    public void setOnMonthChanged(EventHandler<ActionEvent> actionEventEventHandlerMonthChanged){
        this.actionEventEventHandlerMonthChanged = actionEventEventHandlerMonthChanged;
    }

//
//    public void setOnLoadDataInClientSelector(EventHandler<ActionEvent> actionEventEventHandlerLoadDataInClientSelector){
//        this.actionEventEventHandlerLoadDataInClientSelector = actionEventEventHandlerLoadDataInClientSelector;
//    }
//
//    public void setOnClientSelectorAction(EventHandler<ActionEvent> actionEventHandlerClientSelectorAction){
//        this.actionEventEventHandlerClientSelectorAction = actionEventHandlerClientSelectorAction;
//    }
//
//    public void setOnContractSelectorAction(EventHandler<ActionEvent> actionEventEventHandlerContractSelectorAction){
//        this.actionEventEventHandlerContractSelectorAction = actionEventEventHandlerContractSelectorAction;
//    }
}
