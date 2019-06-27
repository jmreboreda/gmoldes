package gmoldes.components.consultation_contract.components;

import gmoldes.ApplicationConstants;
import gmoldes.components.ViewLoader;
import gmoldes.components.contract_documentation_control.events.ContractSelectedEvent;
import gmoldes.components.contract_documentation_control.events.SelectClientEmployerEvent;
import gmoldes.components.contract_documentation_control.events.SelectEmployerEmployeeEvent;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.person.dto.PersonDTO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConsultationContractSelector extends AnchorPane {

    private static final String CONSULTATION_CONTRACT_SELECTOR_FXML = "/fxml/consultation_contract/consultation_contract_selector.fxml";

    private Parent parent;
    private Stage stage;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);

    private EventHandler<MouseEvent> onActiveClientsOnlyEventHandler;
    private EventHandler<MouseEvent> onContractInForceOnlyEventEventHandler;
    private EventHandler<MouseEvent> onAllContractEventEventHandler;
    private EventHandler<SelectClientEmployerEvent> selectClientEmployerEventEventHandler;
    private EventHandler<SelectEmployerEmployeeEvent> selectEmployerEmployeeEventEventHandler;
    private EventHandler<ContractSelectedEvent> contractSelectedEventEventHandler;

    @FXML
    private ToggleGroup contractActiveOrNot;
    @FXML
    private CheckBox activeClientsOnly;
    @FXML
    private RadioButton contractInForceOnly;
    @FXML
    private DatePicker inForceDate;
    @FXML
    private RadioButton allContract;
    @FXML
    private ChoiceBox<ClientDTO> clientSelector;
    @FXML
    private ChoiceBox<PersonDTO> employeeSelector;
    @FXML
    private ChoiceBox<Integer> contractSelector;

    public ConsultationContractSelector() {
        this.parent = ViewLoader.load(this, CONSULTATION_CONTRACT_SELECTOR_FXML);

    }

    public void initialize(){
        activeClientsOnly.setOnMouseClicked(this::onActiveClientsOnly);
        contractInForceOnly.setOnMouseClicked(this::onContractInForceOnly);
        inForceDate.setOnAction(this::onInForceDate);
        allContract.setOnMouseClicked(this::onAllContract);

        clientSelector.setOnAction(this::onClientSelectorChange);
        employeeSelector.setOnAction(this::onEmployeeSelectorChange);
        contractSelector.setOnAction(this::onContractSelectorChange);

        inForceDate.setConverter(new LocalDateStringConverter(dateFormatter, null));
        inForceDate.setValue(LocalDate.now());

    }

    public CheckBox getActiveClientsOnly() {
        return activeClientsOnly;
    }

    public RadioButton getContractInForceOnly() {
        return contractInForceOnly;
    }

    public DatePicker getInForceDate() {
        return inForceDate;
    }

    public RadioButton getAllContract() {
        return allContract;
    }

    public ChoiceBox<ClientDTO> getClientSelector() {
        return clientSelector;
    }

    public ChoiceBox<PersonDTO> getEmployeeSelector() {
        return employeeSelector;
    }

    public ChoiceBox<Integer> getContractSelector() {
        return contractSelector;
    }

    private void onActiveClientsOnly(MouseEvent event){
        onActiveClientsOnlyEventHandler.handle(event);
    }

    private void onContractInForceOnly(MouseEvent event){
        onContractInForceOnlyEventEventHandler.handle(event);
    }

    private void onAllContract(MouseEvent event){
        onAllContractEventEventHandler.handle(event);
    }

    private void onClientSelectorChange(ActionEvent event){
        if(getClientSelector().getValue() == null){

            return;
        }

        SelectClientEmployerEvent clientEmployerEvent = new SelectClientEmployerEvent(getClientSelector().getValue());
        selectClientEmployerEventEventHandler.handle(clientEmployerEvent);
    }

    private void onEmployeeSelectorChange(ActionEvent event){
        if(getEmployeeSelector().getValue() == null){

            return;
        }

    SelectEmployerEmployeeEvent employerEmployeeEvent = new SelectEmployerEmployeeEvent(getClientSelector().getValue(), getEmployeeSelector().getValue());
    selectEmployerEmployeeEventEventHandler.handle(employerEmployeeEvent);
    }

    private void onInForceDate(ActionEvent event){

    }

    private void onContractSelectorChange(ActionEvent event){
        if(contractSelector.getSelectionModel().getSelectedItem() == null){

            return;
        }

        contractSelectedEventEventHandler.handle(new ContractSelectedEvent(contractSelector.getValue()));
    }

    public void loadClientSelector(ObservableList<ClientDTO> clientDTOOL){
        clientSelector.setItems(clientDTOOL);
    }

    public void setOnActiveClientsOnly(EventHandler<MouseEvent> onActiveClientsOnlyEventHandler){
        this.onActiveClientsOnlyEventHandler = onActiveClientsOnlyEventHandler;
    }

    public void setOnContractInForceOnly(EventHandler<MouseEvent> event){
        this.onContractInForceOnlyEventEventHandler = event;
    }

    public void setOnAllContract(EventHandler<MouseEvent> event){
        this.onAllContractEventEventHandler = event;
    }
    public void setOnClientSelectorChange(EventHandler<SelectClientEmployerEvent> selectClientEmployerEventEventHandler){
        this.selectClientEmployerEventEventHandler = selectClientEmployerEventEventHandler;
    }

    public void setOnEmployeeSelectorChange(EventHandler<SelectEmployerEmployeeEvent> selectEmployerEmployeeEventEventHandler){
        this.selectEmployerEmployeeEventEventHandler = selectEmployerEmployeeEventEventHandler;
    }

    public void setOnContractSelectorChange(EventHandler<ContractSelectedEvent> contractSelectedEventEventHandler){
        this.contractSelectedEventEventHandler = contractSelectedEventEventHandler;
    }
}
