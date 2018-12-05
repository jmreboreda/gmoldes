package gmoldes.components.contract.contract_variation.components;

import gmoldes.components.ViewLoader;
import gmoldes.components.generic_components.DateInput;
import gmoldes.components.generic_components.TimeInput24HoursClock;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ContractVariationContractVariations extends VBox {

    private static final String CONTRACT_VARIATION_CONTRACT_VARIATIONS_FXML = "/fxml/contract_variations/contractvariation_contractvariations.fxml";

    private Parent parent;

    private EventHandler<MouseEvent> actionEventEventHandlerContractExtinction;
    private EventHandler<MouseEvent> actionEventEventHandlerContractExtension;


    @FXML
    private ToggleGroup contractVariationToggleGroup;
    @FXML
    private DateInput dateNotification;
    @FXML
    private TimeInput24HoursClock hourNotification;
    @FXML
    private ContractVariationContractExtinction contractVariationContractExtinction;
    @FXML
    private ContractVariationContractExtension contractVariationContractExtension;
    @FXML
    private ContractVariationContractConversion contractVariationContractConversion;



    public ContractVariationContractVariations() {
        this.parent = ViewLoader.load(this, CONTRACT_VARIATION_CONTRACT_VARIATIONS_FXML);
    }

    @FXML
    private void initialize(){

        dateNotification.setLabelText("Fecha");
        hourNotification.setTextLabel("Hora");

        contractVariationContractExtinction.setOnExtinctionButton(this::onExtinctionButton);
        contractVariationContractExtension.setOnExtensionButton(this::onExtensionButton);

    }

    public DateInput getDateNotification() {
        return dateNotification;
    }

    public void setDateNotification(DateInput dateNotification) {
        this.dateNotification = dateNotification;
    }

    public TimeInput24HoursClock getHourNotification() {
        return hourNotification;
    }

    public void setHourNotification(TimeInput24HoursClock hourNotification) {
        this.hourNotification = hourNotification;
    }

    public ContractVariationContractExtinction getContractVariationContractExtinction() {
        return contractVariationContractExtinction;
    }

    public void setContractVariationContractExtinction(ContractVariationContractExtinction contractVariationContractExtinction) {
        this.contractVariationContractExtinction = contractVariationContractExtinction;
    }

    public ContractVariationContractExtension getContractVariationContractExtension() {
        return contractVariationContractExtension;
    }

    public void setContractVariationContractExtension(ContractVariationContractExtension contractVariationContractExtension) {
        this.contractVariationContractExtension = contractVariationContractExtension;
    }

    public ContractVariationContractConversion getContractVariationContractConversion() {
        return contractVariationContractConversion;
    }

    public void setContractVariationContractConversion(ContractVariationContractConversion contractVariationContractConversion) {
        this.contractVariationContractConversion = contractVariationContractConversion;
    }

    private void onExtinctionButton(MouseEvent event){
        this.actionEventEventHandlerContractExtinction.handle(event);
    }

    private void onExtensionButton(MouseEvent event){
        this.actionEventEventHandlerContractExtension.handle(event);
    }

    public void setOnContractExtinction(EventHandler<MouseEvent> actionEventEventHandlerContractExtinction){
        this.actionEventEventHandlerContractExtinction = actionEventEventHandlerContractExtinction;
    }

    public void setOnContractExtension(EventHandler<MouseEvent> actionEventEventHandlerContractExtension){
        this.actionEventEventHandlerContractExtension = actionEventEventHandlerContractExtension;
    }
}
