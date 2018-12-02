package gmoldes.components.payroll_check_list.components;

import gmoldes.components.ViewLoader;
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

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PayrollCheckListData extends VBox {

    private static final String PAYROLL_CHECKLIST_DATA_FXML = "/fxml/payroll_check_list/payrollchecklist_data.fxml";

    private Parent parent;

    private EventHandler<ActionEvent> actionEventEventHandlerMonthChanged;
    private EventHandler<ActionEvent> actionEventEventHandlerYearChanged;

    @FXML
    private ChoiceBox<PayrollCheckListDTO> month;
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
        this.year.setOnAction(this::onYearChanged);

        loadMonth();
    }

    public ChoiceBox<PayrollCheckListDTO> getMonth() {
        return month;
    }

    public void setMonth(ChoiceBox<PayrollCheckListDTO> month) {
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

    private void loadMonth(){

        List<PayrollCheckListDTO> payrollCheckListDTOList = new ArrayList<>();
        for(Month month : Month.values()){
            PayrollCheckListDTO payrollCheckListDTO = new PayrollCheckListDTO();
            payrollCheckListDTO.setMonth(month);
            payrollCheckListDTOList.add(payrollCheckListDTO);
        }

        ObservableList monthNamesOS = FXCollections.observableArrayList(payrollCheckListDTOList);
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
