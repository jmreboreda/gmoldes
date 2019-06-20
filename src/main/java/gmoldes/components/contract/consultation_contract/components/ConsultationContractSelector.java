package gmoldes.components.contract.consultation_contract.components;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract_documentation_control.events.ContractSelectedEvent;
import gmoldes.components.contract_documentation_control.events.ContractVariationSelectedEvent;
import gmoldes.components.contract_documentation_control.events.SelectClientEmployerEvent;
import gmoldes.components.contract_documentation_control.events.SelectEmployerEmployeeEvent;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.person.dto.PersonDTO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ConsultationContractSelector extends AnchorPane {

    private static final String CONSULTATION_CONTRACT_SELECTOR_FXML = "/fxml/consultation_contract/consultation_contract_selector.fxml";

    private Parent parent;
    private Stage stage;

    private EventHandler<MouseEvent> changeContractsInForceOnlyEventHandler;
    private EventHandler<SelectClientEmployerEvent> selectClientEmployerEventEventHandler;
    private EventHandler<SelectEmployerEmployeeEvent> selectEmployerEmployeeEventEventHandler;
    private EventHandler<ContractSelectedEvent> contractSelectedEventEventHandler;
    private EventHandler<ContractVariationSelectedEvent> contractVariationSelectedEventEventHandler;

    @FXML
    private CheckBox contractsWithTraceabilityOnly;
    @FXML
    private CheckBox contractsInForceOnly;
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
        contractsWithTraceabilityOnly.setMouseTransparent(true);
        contractsInForceOnly.setOnMouseClicked(this::onChangeContractsInForceOnly);

        clientSelector.setOnAction(this::onClientSelectorChange);
        employeeSelector.setOnAction(this::onEmployeeSelectorChange);
        contractSelector.setOnAction(this::onContractSelectorChange);
    }

    public CheckBox getContractsInForceOnly() {
        return contractsInForceOnly;
    }

    public CheckBox getContractsWithTraceabilityOnly() {
        return contractsWithTraceabilityOnly;
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

    private void onChangeContractsInForceOnly(MouseEvent event){
        changeContractsInForceOnlyEventHandler.handle(event);
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

    private void onContractSelectorChange(ActionEvent event){
        if(contractSelector.getSelectionModel().getSelectedItem() == null){

            return;
        }

        contractSelectedEventEventHandler.handle(new ContractSelectedEvent(contractSelector.getValue()));
    }

    public void loadClientSelector(ObservableList<ClientDTO> clientDTOOL){
        clientSelector.setItems(clientDTOOL);
    }

    public void setOnChangeContractsInForceOnly(EventHandler<MouseEvent> changeContractsInForceOnlyEvent){
        this.changeContractsInForceOnlyEventHandler = changeContractsInForceOnlyEvent;
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

    public void setOnContractVariationSelectorChange(EventHandler<ContractVariationSelectedEvent> contractVariationSelectedEventEventHandler){
        this.contractVariationSelectedEventEventHandler = contractVariationSelectedEventEventHandler;
    }
}
