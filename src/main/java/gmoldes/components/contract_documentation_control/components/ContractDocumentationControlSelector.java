package gmoldes.components.contract_documentation_control.components;

import gmoldes.components.ViewLoader;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.person.dto.PersonDTO;
import javafx.collections.ObservableList;
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


    @FXML
    private CheckBox contractsInForceOnly;
    @FXML
    private ChoiceBox<ClientDTO> clientSelector;
    @FXML
    private ChoiceBox<PersonDTO> employeeSelector;
    @FXML
    private ChoiceBox<String> contractSelector;

    public ContractDocumentationControlSelector() {
        this.parent = ViewLoader.load(this, CONTRACT_DOCUMENTATION_CONTROL_SELECTOR_FXML);

    }

    public void initialize(){
        contractsInForceOnly.setOnMouseClicked(this::onChangeContractsInForceOnly);


    }

    public CheckBox getContractsInForceOnly() {
        return contractsInForceOnly;
    }

    public void setContractsInForceOnly(CheckBox contractsInForceOnly) {
        this.contractsInForceOnly = contractsInForceOnly;
    }

    public ChoiceBox<ClientDTO> getClientSelector() {
        return clientSelector;
    }

    public void setClientSelector(ChoiceBox<ClientDTO> clientSelector) {
        this.clientSelector = clientSelector;
    }

    public ChoiceBox<PersonDTO> getEmployerSelector() {
        return employeeSelector;
    }

    public ChoiceBox<String> getContractSelector() {
        return contractSelector;
    }

    public void setEmployerSelector(ChoiceBox<PersonDTO> employerSelector) {
        this.employeeSelector = employerSelector;
    }

    private void onChangeContractsInForceOnly(MouseEvent event){
        changeContractsInForceOnlyEventHandler.handle(event);
    }

    public void loadClientSelector(ObservableList<ClientDTO> clientDTOOL){
        clientSelector.setItems(clientDTOOL);
    }

    public void setOnChangeContractsInForceOnly(EventHandler<MouseEvent> changeContractsInForceOnlyEvent){
        this.changeContractsInForceOnlyEventHandler = changeContractsInForceOnlyEvent;
    }
}
