package gmoldes.components.contract_variation.components;

import gmoldes.ApplicationConstants;
import gmoldes.components.ViewLoader;
import gmoldes.components.contract_variation.events.ClientChangeEvent;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.dto.ContractFullDataDTO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ContractVariationParts extends VBox {

    private static final String CONTRACT_VARIATION_PARTS_FXML = "/fxml/contract_variations/contractvariation_parts.fxml";

    private Parent parent;

    private EventHandler<ClientChangeEvent> actionEventEventHandlerClientSelectorAction;
    private EventHandler<ActionEvent> actionEventEventHandlerContractSelectorAction;
    private EventHandler<ActionEvent> actionEventEventHandlerDateChange;


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

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);
        inForceDate.setConverter(new LocalDateStringConverter(dateFormatter, null));
        inForceDate.setValue(LocalDate.now());
        //inForceDate.setMouseTransparent(true);
        inForceDate.setOnAction(this::onDateChange);
        client.setOnAction(this::onChangeEmployer);
        contract.setOnAction(this::onChangeEmployee);
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

    private void onChangeEmployer(ActionEvent event){

        actionEventEventHandlerClientSelectorAction.handle(new ClientChangeEvent(getClientSelector().getSelectionModel().getSelectedItem(), getInForceDate().getValue()));
    }

    private void onChangeEmployee(ActionEvent event){

        this.actionEventEventHandlerContractSelectorAction.handle(event);
    }

    private void onDateChange(ActionEvent event){

        this.actionEventEventHandlerDateChange.handle(event);
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

    public void setOnDateChange(EventHandler<ActionEvent> actionEventEventHandlerDateChange){

        this.actionEventEventHandlerDateChange = actionEventEventHandlerDateChange;
    }
}
