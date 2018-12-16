package gmoldes.components.contract.contract_variation.components;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract.contract_variation.events.ClientChangeEvent;
import gmoldes.domain.client.dto.ClientDTOOk;
import gmoldes.domain.contract.dto.ContractFullDataDTO;
import gmoldes.utilities.Utilities;
import javafx.collections.ObservableList;
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

    private EventHandler<ClientChangeEvent> actionEventEventHandlerClientSelectorAction;
    private EventHandler<ActionEvent> actionEventEventHandlerContractSelectorAction;


    @FXML
    private DatePicker inForceDate;
    @FXML
    private ChoiceBox<ClientDTOOk> client;
    @FXML
    private ChoiceBox<ContractFullDataDTO> contract;

    public ContractVariationParts() {

        this.parent = ViewLoader.load(this, CONTRACT_VARIATION_PARTS_FXML);
    }

    @FXML
    public void initialize() {

        inForceDate.setConverter(Utilities.dateConverter);
        inForceDate.setValue(LocalDate.now());
        inForceDate.setMouseTransparent(true);
        client.setOnAction(this::onChangeEmployer);
        contract.setOnAction(this::onChangeEmployee);
    }

    public DatePicker getInForceDate() {
        return inForceDate;
    }

    public void setInForceDate(DatePicker inForceDate) {
        this.inForceDate = inForceDate;
    }

    public ChoiceBox<ClientDTOOk> getClientSelector() {
        return client;
    }

    public void setClient(ChoiceBox<ClientDTOOk> client) {
        this.client = client;
    }

    public ChoiceBox<ContractFullDataDTO> getContractSelector() {
        return contract;
    }

    public void setContract(ChoiceBox<ContractFullDataDTO> contract) {
        this.contract = contract;
    }

    private void onChangeEmployer(ActionEvent event){

        actionEventEventHandlerClientSelectorAction.handle(new ClientChangeEvent(getClientSelector().getSelectionModel().getSelectedItem(), getInForceDate().getValue()));
    }

    private void onChangeEmployee(ActionEvent event){
        this.actionEventEventHandlerContractSelectorAction.handle(event);
    }

    public void loadDataInClientSelector(ObservableList clientOS) {

        this.client.setItems(clientOS);
    }

    public void setOnClientSelectorAction(EventHandler<ClientChangeEvent> actionEventHandlerClientSelectorAction){
        this.actionEventEventHandlerClientSelectorAction = actionEventHandlerClientSelectorAction;
    }

    public void setOnContractSelectorAction(EventHandler<ActionEvent> actionEventEventHandlerContractSelectorAction){
        this.actionEventEventHandlerContractSelectorAction = actionEventEventHandlerContractSelectorAction;
    }
}
