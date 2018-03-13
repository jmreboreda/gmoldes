package gmoldes.components.contract.new_contract;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract.events.SearchEmployeesEvent;
import gmoldes.components.contract.events.SearchEmployersEvent;
import gmoldes.components.contract.events.SelectEmployerEvent;
import gmoldes.domain.dto.ClientCCCDTO;
import gmoldes.domain.dto.ClientDTO;
import gmoldes.domain.dto.PersonDTO;
import gmoldes.domain.dto.ProvisionalContractDataDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import java.util.List;


public class ContractParts extends HBox {

    private static final String CONTRACT_PARTS_FXML = "/fxml/new_contract/contract_parts.fxml";
    private EventHandler<SearchEmployersEvent> onEmployerNamePatternChangedEventHandler;
    private EventHandler<SearchEmployeesEvent> onEmployeeNamePatternChangedEventHandler;
    private EventHandler<SelectEmployerEvent> onSelectEmployerEventHandler;

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
    private ChoiceBox<ClientCCCDTO> cotizationCode;
    @FXML
    private Button newEmployee;


    public ContractParts() {

        this.parent = ViewLoader.load(this, CONTRACT_PARTS_FXML);
    }

    @FXML
    public void initialize() {

        employerName.setOnKeyReleased(this::onEmployerNamePatternChanged);

        employeeName.setOnKeyReleased(this::onEmployeeNamePatternChanged);

        employersNames.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newEmployerSelected) -> onSelectEmployer(newEmployerSelected));

        employeesNames.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newPersonValue) -> onSelectEmployee(newPersonValue));
    }

    private void onEmployerNamePatternChanged(KeyEvent keyEvent) {
        String pattern = employerName.getText();
        final SearchEmployersEvent searchEmployersEvent = new SearchEmployersEvent(pattern);
        onEmployerNamePatternChangedEventHandler.handle(searchEmployersEvent);
    }

    private void onEmployeeNamePatternChanged(KeyEvent keyEvent) {
        String pattern = employeeName.getText();
        final SearchEmployeesEvent searchEmployeesEvent = new SearchEmployeesEvent(pattern);
        onEmployeeNamePatternChangedEventHandler.handle(searchEmployeesEvent);
    }

    private void onSelectEmployer(ClientDTO newEmployerSelected){
        employerName.setText(newEmployerSelected.getPersonOrCompanyName());
//        KeyEvent ke = new KeyEvent(KeyEvent.KEY_RELEASED,"", "",
//                KeyCode.UNDEFINED, false, false, false, false);
//        employerName.fireEvent(ke);

        final SelectEmployerEvent selectEmployerEvent = new SelectEmployerEvent(newEmployerSelected);
        onSelectEmployerEventHandler.handle(selectEmployerEvent);
    }

    private void onSelectEmployee(PersonDTO newPersonValue){
        if(newPersonValue != null) {
            employeeName.setText(newPersonValue.getApellidos().concat(", ").concat(newPersonValue.getNom_rzsoc()));
//            KeyEvent ke = new KeyEvent(KeyEvent.KEY_RELEASED,"", "", KeyCode.UNDEFINED, false, false, false, false);
//            employeeName.fireEvent(ke);
        }
    }

    public ProvisionalContractDataDTO getAllData(){

        String selectedEmployerName = "";
        if(this.employersNames.getSelectionModel().getSelectedItem() != null){
            selectedEmployerName = this.employersNames.getSelectionModel().getSelectedItem().toString();
        }
        String selectedEmployeeName = "";
        if(this.employeesNames.getSelectionModel().getSelectedItem() != null){
            selectedEmployeeName = this.employeesNames.getSelectionModel().getSelectedItem().toString();
        }
        String CCC = "";
        if(this.cotizationCode.getSelectionModel().getSelectedItem() != null){
            CCC = this.cotizationCode.getSelectionModel().getSelectedItem().toString();
        }

        return ProvisionalContractDataDTO.create()
                .withEmployerFullName(selectedEmployerName)
                .withQuoteAccountCode(CCC)
                .withEmployeeFullName(selectedEmployeeName)
                .build();
    }

    public void clearEmployersData(){
        employerName.clear();
        clearEmployersNames();
    }

    public void clearEmployersNames(){
        if(!employersNames.getItems().isEmpty()) {
            employersNames.getItems().clear();
        }
    }

    public void clearEmployeesData(){
        employeeName.clear();
        clearEmployeesNames();
    }

    public void clearEmployeesNames(){
        if(!employeesNames.getItems().isEmpty()) {
            employeesNames.getItems().clear();
        }
    }

    public void clearEmployerCCC(){
        if(!cotizationCode.getItems().isEmpty()){
            cotizationCode.getItems().clear();
        }
    }

    public void refreshEmployers(List<ClientDTO> employers){
        ObservableList<ClientDTO> listPersonsWhoMatchPattern = FXCollections.observableList(employers);
        employersNames.setItems(listPersonsWhoMatchPattern);
    }

    public void refreshEmployees(List<PersonDTO> employees){
        ObservableList<PersonDTO> listPersonsWhoMatchPattern = FXCollections.observableList(employees);
        employeesNames.setItems(listPersonsWhoMatchPattern);

    }

    public void refreshEmployerCCC(List<ClientCCCDTO> clientCCCDTO){
        ObservableList<ClientCCCDTO> clientCCCDTOL = FXCollections.observableArrayList(clientCCCDTO);
        cotizationCode.setItems(clientCCCDTOL);
        if (clientCCCDTOL.size() == 1) {
            cotizationCode.getSelectionModel().selectFirst();
        }
    }

    public void setOnSearchEmployers(EventHandler<SearchEmployersEvent> handler) {
        this.onEmployerNamePatternChangedEventHandler = handler;
    }

    public void setOnSearchEmployees(EventHandler<SearchEmployeesEvent> handler) {
        this.onEmployeeNamePatternChangedEventHandler = handler;
    }

    public void setOnSelectEmployer(EventHandler<SelectEmployerEvent> handler){
        this.onSelectEmployerEventHandler = handler;
    }
}
