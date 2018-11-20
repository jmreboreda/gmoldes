package gmoldes.components.contract.contract_variation.components;

import gmoldes.ApplicationMainController;
import gmoldes.components.ViewLoader;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.dto.ContractFullDataDTO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;

public class ContractVariationParts extends VBox {

    private static final String CONTRACT_VARIATION_PARTS_FXML = "/fxml/contract_variations/contractvariation_parts.fxml";

    private Parent parent;

    private EventHandler<ActionEvent> actionEventEventHandlerLoadDataInClientSelector;
    private EventHandler<ActionEvent> actionEventEventHandlerClientSelectorAction;
    private EventHandler<ActionEvent> actionEventEventHandlerContractSelectorAction;


    @FXML
    private ChoiceBox<ClientDTO> client;
    @FXML
    private ChoiceBox<ContractFullDataDTO> contract;

    public ContractVariationParts() {

        this.parent = ViewLoader.load(this, CONTRACT_VARIATION_PARTS_FXML);

    }

    @FXML
    public void initialize() {
        client.setOnAction(this::onChangeEmployer);
        contract.setOnAction(this::onChangeEmployee);
    }

    public ChoiceBox<ClientDTO> getClientSelector() {
        return client;
    }

    public void setClient(ChoiceBox<ClientDTO> client) {
        this.client = client;
    }

    public ChoiceBox<ContractFullDataDTO> getContractSelector() {
        return contract;
    }

    public void setContract(ChoiceBox<ContractFullDataDTO> contract) {
        this.contract = contract;
    }

    private void onChangeEmployer(ActionEvent event){
        actionEventEventHandlerClientSelectorAction.handle(event);
    }

    private void onChangeEmployee(ActionEvent event){
        this.actionEventEventHandlerContractSelectorAction.handle(event);
    }

    public void loadDataInClientSelector() { actionEventEventHandlerLoadDataInClientSelector.handle(new ActionEvent());

    }

    public void setOnLoadDataInClientSelector(EventHandler<ActionEvent> actionEventEventHandlerLoadDataInClientSelector){
        this.actionEventEventHandlerLoadDataInClientSelector = actionEventEventHandlerLoadDataInClientSelector;
    }

    public void setOnClientSelectorAction(EventHandler<ActionEvent> actionEventHandlerClientSelectorAction){
        this.actionEventEventHandlerClientSelectorAction = actionEventHandlerClientSelectorAction;
    }

    public void setOnContractSelectorAction(EventHandler<ActionEvent> actionEventEventHandlerContractSelectorAction){
        this.actionEventEventHandlerContractSelectorAction = actionEventEventHandlerContractSelectorAction;
    }
}
