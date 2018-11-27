package gmoldes.components.contract.contract_variation.components;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract.contract_variation.events.ClientChangeEvent;
import gmoldes.components.contract.contract_variation.events.DateChangeEvent;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.dto.ContractFullDataDTO;
import gmoldes.utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import java.time.LocalDate;

public class ContractVariationParts extends VBox {

    private static final String CONTRACT_VARIATION_PARTS_FXML = "/fxml/contract_variations/contractvariation_parts.fxml";

    private Parent parent;

    private EventHandler<DateChangeEvent> actionEventEventHandlerInForceDateChanged;
    private EventHandler<DateChangeEvent> actionEventEventHandlerLoadDataInClientSelector;
    private EventHandler<ClientChangeEvent> actionEventEventHandlerClientSelectorAction;
    private EventHandler<ActionEvent> actionEventEventHandlerContractSelectorAction;


    @FXML
    private DatePicker inForceDate;
    @FXML
    private ChoiceBox<ClientDTO> client;
    @FXML
    private ChoiceBox<ContractFullDataDTO> contract;

    public ContractVariationParts() {

        this.parent = ViewLoader.load(this, CONTRACT_VARIATION_PARTS_FXML);

    }

    @FXML
    public void initialize() {

        inForceDate.setConverter(Utilities.converter);
        inForceDate.setValue(LocalDate.now());
        client.setOnAction(this::onChangeEmployer);
        contract.setOnAction(this::onChangeEmployee);
        inForceDate.setOnAction(this::onDateChanged);
    }

    public DatePicker getInForceDate() {
        return inForceDate;
    }

    public void setInForceDate(DatePicker inForceDate) {
        this.inForceDate = inForceDate;
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

    private void onDateChanged(ActionEvent event){
        this.actionEventEventHandlerInForceDateChanged.handle(new DateChangeEvent(client.getSelectionModel().getSelectedItem(), inForceDate.getValue()));
    }

    private void onChangeEmployer(ActionEvent event){

        actionEventEventHandlerClientSelectorAction.handle(new ClientChangeEvent(getClientSelector().getSelectionModel().getSelectedItem(), getInForceDate().getValue()));
    }

    private void onChangeEmployee(ActionEvent event){
        this.actionEventEventHandlerContractSelectorAction.handle(event);
    }

    public void loadDataInClientSelector() {

        actionEventEventHandlerLoadDataInClientSelector.handle(new DateChangeEvent(null, getInForceDate().getValue()));

    }

    public void setOnInForceDateChanged(EventHandler<DateChangeEvent> actionEventEventHandlerInForceDateChanged){
        this.actionEventEventHandlerInForceDateChanged = actionEventEventHandlerInForceDateChanged;
    }

    public void setOnLoadDataInClientSelector(EventHandler<DateChangeEvent> actionEventEventHandlerLoadDataInClientSelector){
        this.actionEventEventHandlerLoadDataInClientSelector = actionEventEventHandlerLoadDataInClientSelector;
    }

    public void setOnClientSelectorAction(EventHandler<ClientChangeEvent> actionEventHandlerClientSelectorAction){
        this.actionEventEventHandlerClientSelectorAction = actionEventHandlerClientSelectorAction;
    }

    public void setOnContractSelectorAction(EventHandler<ActionEvent> actionEventEventHandlerContractSelectorAction){
        this.actionEventEventHandlerContractSelectorAction = actionEventEventHandlerContractSelectorAction;
    }
}
