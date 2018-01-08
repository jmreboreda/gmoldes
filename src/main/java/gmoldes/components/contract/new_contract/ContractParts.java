package gmoldes.components.contract.new_contract;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract.new_contract.events.SearchEmployeesEvent;
import gmoldes.components.contract.new_contract.events.SearchEmployersEvent;
import gmoldes.domain.dto.ClientDTO;
import gmoldes.domain.dto.PersonDTO;
import gmoldes.domain.dto.ProvisionalContractDataDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import java.util.List;


public class ContractParts extends HBox {

    private static final String CONTRACT_PARTS_FXML = "/fxml/new_contract/contract_parts.fxml";
    private EventHandler<SearchEmployersEvent> onEmployerNamePatternChangedEventHandler;
    private EventHandler<SearchEmployeesEvent> onEmployeeNamePatternChangedEventHandler;

    private Parent parent;

    @FXML
    private ContractParts contractParts;

    @FXML
    private TextField employerName;
    @FXML
    private ListView<ClientDTO> employersNames;
    @FXML
    private TextField employeeName;
    @FXML
    private ListView<PersonDTO> employeesNames;
    @FXML
    private ChoiceBox cotizationCode;
    @FXML
    private javafx.scene.control.Button newEmployee;


    public ContractParts() {

        this.parent = ViewLoader.load(this, CONTRACT_PARTS_FXML);
    }

    @FXML
    public void initialize() {

        employerName.setOnKeyReleased(this::onEmployerNamePatternChanged);
        employeeName.setOnKeyReleased(this::onEmployeeNamePatternChanged);

    }

    private void onEmployerNamePatternChanged(KeyEvent keyEvent) {
        String pattern = employerName.getText();
        String employersNameSelectedItem = null;
        if(this.employersNames.getSelectionModel().getSelectedItem() != null) {
            employersNameSelectedItem = this.employersNames.getSelectionModel().getSelectedItem().toString();
        }
        final SearchEmployersEvent searchEmployersEvent = new SearchEmployersEvent(pattern, employersNameSelectedItem);
        onEmployerNamePatternChangedEventHandler.handle(searchEmployersEvent);

        employersNames.getSelectionModel().selectedItemProperty()
               .addListener((observable, oldValue, newClientValue) -> onSelectClient(newClientValue));
    }

    private void onEmployeeNamePatternChanged(KeyEvent keyEvent) {
        String pattern = employeeName.getText();
        String employeeNameSelectedItem = null;
        if(this.employeesNames.getSelectionModel().getSelectedItem() != null) {
            employeeNameSelectedItem = this.employeesNames.getSelectionModel().getSelectedItem().toString();
        }
        final SearchEmployeesEvent searchEmployeesEvent = new SearchEmployeesEvent(pattern, employeeNameSelectedItem);
        onEmployeeNamePatternChangedEventHandler.handle(searchEmployeesEvent);

        employeesNames.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newPersonValue) -> onSelectEmployee(newPersonValue));
    }

    public ProvisionalContractDataDTO getAllData(){

        String employerName = "";
        if(this.employersNames.getSelectionModel().getSelectedItem() != null){
            employerName = this.employersNames.getSelectionModel().getSelectedItem().toString();
        }
        String employeeName = "";
        if(this.employeesNames.getSelectionModel().getSelectedItem() != null){
            employeeName = this.employeesNames.getSelectionModel().getSelectedItem().toString();
        }
        String CCC = "";
        if(this.cotizationCode.getSelectionModel().getSelectedItem() != null){
            CCC = this.cotizationCode.getSelectionModel().getSelectedItem().toString();
        }

        return ProvisionalContractDataDTO.create()
                .withEmployerFullName(employerName)
                .withQuoteAccountCode(CCC)
                .withEmployeeFullName(employeeName)
                .build();
    }

    private void onSelectClient(ClientDTO newClientValue){
        employerName.setText(newClientValue.getNom_rzsoc());
    }

    private void onSelectEmployee(PersonDTO newPersonValue){

        employeeName.setText(newPersonValue.getApellidos().concat(", ").concat(newPersonValue.getNom_rzsoc()));
    }

    public void clearEmployersData(){
        employerName.clear();
        if(!employersNames.getItems().isEmpty()) {
            employersNames.getItems().clear();
        }
    }

    public void clearEmployeesData(){
        employeeName.clear();
        if(!employeesNames.getItems().isEmpty()) {
            employeesNames.getItems().clear();
        }
    }

    public void refreshEmployers(List<ClientDTO> employers){
        if(!employersNames.getItems().isEmpty()) {
            employersNames.getItems().clear();
        }
        ObservableList<ClientDTO> listPersonsWhoMatchPattern = FXCollections.observableList(employers);
        employersNames.getItems().addAll(listPersonsWhoMatchPattern);
    }

    public void refreshEmployees(List<PersonDTO> employees){
        if(!employeesNames.getItems().isEmpty()) {
            employeesNames.getItems().clear();
        }
        ObservableList<PersonDTO> listPersonsWhoMatchPattern = FXCollections.observableList(employees);
        employeesNames.getItems().addAll(listPersonsWhoMatchPattern);
    }

    public void setOnSearchEmployers(EventHandler<SearchEmployersEvent> handler) {
        this.onEmployerNamePatternChangedEventHandler = handler;
    }

    public void setOnSearchEmployees(EventHandler<SearchEmployeesEvent> handler) {
        this.onEmployeeNamePatternChangedEventHandler = handler;
    }
}
