package gmoldes.components.contract_documentation_control.components;

import gmoldes.components.ViewLoader;
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

public class ContractDocumentationControlSelector extends AnchorPane {

    private static final String CONTRACT_DOCUMENTATION_CONTROL_SELECTOR_FXML = "/fxml/contract_documentation_control/contract_documentation_control_selector.fxml";

    private Parent parent;
    private Stage stage;

    private EventHandler<MouseEvent> changeContractsInForceOnlyEventHandler;
    private EventHandler<SelectClientEmployerEvent> selectClientEmployerEventEventHandler;
    private EventHandler<SelectEmployerEmployeeEvent> selectEmployerEmployeeEventEventHandler;


    @FXML
    private CheckBox contractsInForceOnly;
    @FXML
    private ChoiceBox<ClientDTO> clientSelector;
    @FXML
    private ChoiceBox<PersonDTO> employeeSelector;
    @FXML
    private ChoiceBox<Integer> contractSelector;

    public ContractDocumentationControlSelector() {
        this.parent = ViewLoader.load(this, CONTRACT_DOCUMENTATION_CONTROL_SELECTOR_FXML);

    }

    public void initialize(){
        contractsInForceOnly.setOnMouseClicked(this::onChangeContractsInForceOnly);
        clientSelector.setOnAction(this::onClientSelectorChange);
        employeeSelector.setOnAction(this::onEmployeeSelectorChange);



    }

    public CheckBox getContractsInForceOnly() {
        return contractsInForceOnly;
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
}
