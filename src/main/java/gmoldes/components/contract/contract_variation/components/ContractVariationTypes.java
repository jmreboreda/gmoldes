package gmoldes.components.contract.contract_variation.components;

import gmoldes.components.ViewLoader;
import gmoldes.components.generic_components.DateInput;
import gmoldes.components.generic_components.TimeInput24HoursClock;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ContractVariationTypes extends VBox {

    private static final String CONTRACT_VARIATION_TYPES_FXML = "/fxml/contract_variations/contractvariation_types.fxml";

    private Parent parent;

    private EventHandler<MouseEvent> actionEventEventHandlerContractExtinction;
    private EventHandler<MouseEvent> actionEventEventHandlerContractExtension;
    private EventHandler<MouseEvent> actionEventEventHandlerContractConversion;


    @FXML
    private ToggleGroup contractVariationToggleGroup;
    @FXML
    private DateInput dateNotification;
    @FXML
    private TimeInput24HoursClock hourNotification;
    @FXML
    private RadioButton rbContractExtinction;
    @FXML
    private RadioButton rbContractExtension;
    @FXML
    private RadioButton rbWeeklyWorkHoursVariation;
    @FXML
    private RadioButton rbContractConversion;
    @FXML
    private RadioButton rbOtherVariations;



    public ContractVariationTypes() {
        this.parent = ViewLoader.load(this, CONTRACT_VARIATION_TYPES_FXML);

        dateNotification.setLabelText("Fecha");
        hourNotification.setTextLabel("Hora");
    }

    @FXML
    private void initialize(){

        rbContractExtinction.setOnMouseClicked(this::onContractExtinction);
        rbContractExtension.setOnMouseClicked(this::onContractExtension);
        rbContractConversion.setOnMouseClicked(this::onContractConversion);

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

    public RadioButton getRbContractExtinction() {
        return rbContractExtinction;
    }

    public void setRbContractExtinction(RadioButton rbContractExtinction) {
        this.rbContractExtinction = rbContractExtinction;
    }

    public RadioButton getRbContractExtension() {
        return rbContractExtension;
    }

    public void setRbContractExtension(RadioButton rbContractExtension) {
        this.rbContractExtension = rbContractExtension;
    }

    public RadioButton getRbWeeklyWorkHoursVariation() {
        return rbWeeklyWorkHoursVariation;
    }

    public void setRbWeeklyWorkHoursVariation(RadioButton rbWeeklyWorkHoursVariation) {
        this.rbWeeklyWorkHoursVariation = rbWeeklyWorkHoursVariation;
    }

    public RadioButton getRbContractConversion() {
        return rbContractConversion;
    }

    public void setRbContractConversion(RadioButton rbContractConversion) {
        this.rbContractConversion = rbContractConversion;
    }

    public RadioButton getRbOtherVariations() {
        return rbOtherVariations;
    }

    public void setRbOtherVariations(RadioButton rbOtherVariations) {
        this.rbOtherVariations = rbOtherVariations;
    }

    private void onContractExtinction(MouseEvent event){
        this.actionEventEventHandlerContractExtinction.handle(event);
    }

    private void onContractExtension(MouseEvent event){
        this.actionEventEventHandlerContractExtension.handle(event);
    }

    private void onContractConversion(MouseEvent event){
        this.actionEventEventHandlerContractConversion.handle(event);
    }


    public void setOnContractExtinction(EventHandler<MouseEvent> actionEventEventHandlerContractExtinction){
        this.actionEventEventHandlerContractExtinction = actionEventEventHandlerContractExtinction;
    }

    public void setOnContractExtension(EventHandler<MouseEvent> actionEventEventHandlerContractExtension){
        this.actionEventEventHandlerContractExtension = actionEventEventHandlerContractExtension;
    }

    public void setOnContractConversion(EventHandler<MouseEvent> actionEventEventHandlerContractConversion){
        this.actionEventEventHandlerContractConversion = actionEventEventHandlerContractConversion;
    }

}