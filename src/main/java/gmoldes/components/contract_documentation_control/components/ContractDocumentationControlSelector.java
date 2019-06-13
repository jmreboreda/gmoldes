package gmoldes.components.contract_documentation_control.components;

import gmoldes.components.ViewLoader;
import gmoldes.domain.client.dto.ClientDTO;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ContractDocumentationControlSelector extends AnchorPane {

    private static final String CONTRACT_DOCUMENTATION_CONTROL_SELECTOR_FXML = "/fxml/contract_documentation_control/contract_documentation_control_selector.fxml";

    private Parent parent;
    private Stage stage;

    private EventHandler<MouseEvent> onChangeContractsInForceOnlyEventHandler;


    @FXML
    private CheckBox contractsInForceOnly;
    @FXML
    private ComboBox<ClientDTO> clientSelector;

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

    public ComboBox<ClientDTO> getClientSelector() {
        return clientSelector;
    }

    public void setClientSelector(ComboBox<ClientDTO> clientSelector) {
        this.clientSelector = clientSelector;
    }

    private void onChangeContractsInForceOnly(MouseEvent event){
        onChangeContractsInForceOnlyEventHandler.handle(event);
    }

    public void loadClientSelector(ObservableList<ClientDTO> clientDTOOL){
        clientSelector.setItems(clientDTOOL);
    }

    public void setOnChangeContractsInForceOnly(EventHandler<MouseEvent> onChangeContractsInForceOnly){
        this.onChangeContractsInForceOnlyEventHandler = onChangeContractsInForceOnly;
    }
}
