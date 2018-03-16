package gmoldes.components.contract.new_contract;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract.events.ChangeContractDataHoursWorkWeekEvent;
import gmoldes.components.generic_components.CustomInput;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.Utilities;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.layout.HBox;

import java.time.Duration;
import java.util.regex.Pattern;

public class CustomInputHoursWorkWeek extends CustomInput {

    private static final String CUSTOM_TEXTFIELD = "/fxml/generic_components/custom_textfield.fxml";

    private EventHandler<ChangeContractDataHoursWorkWeekEvent> changeContractDataHoursWorkWeekEventHandler;

    private Parent parent;

    @FXML
    private Label labelOfTextField;
    @FXML
    private TextField textFieldComponent;

    public CustomInputHoursWorkWeek() {

        this.parent = ViewLoader.load(this, CUSTOM_TEXTFIELD);
    }

    @FXML
    private void initialize(){

        this.labelOfTextField.setPrefHeight(25);
        this.labelOfTextField.setText(Parameters.HOURS_WORK_WEEK_TEXT);
        this.labelOfTextField.setMinWidth(150);

        this.textFieldComponent.setMaxWidth(60);
        setMargin(textFieldComponent, new Insets(0, 0, 0, 10));
        textFieldComponent.setDisable(true);
        this. textFieldComponent.setOnAction(this::onHoursWorkWeekChanged);
        textFieldComponent.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue)   // Textfield out focus
            {
                if(!textFieldComponent.getText().isEmpty()){
                    onHoursWorkWeekChanged(new ActionEvent());
                }
                else{
                    textFieldComponent.setText("00:00");
                }
            }
        });

    }

    public void onHoursWorkWeekChanged(ActionEvent event){

        if(!verifyHoursWorkWeekChangeIsValid()){

            return;

        }else {
            String timeString = textFieldComponent.getText();
            Duration hoursWorkWeekDuration = Utilities.converterTimeStringToDuration(timeString);
            ChangeContractDataHoursWorkWeekEvent contractDataHoursWorkWeekEvent = new ChangeContractDataHoursWorkWeekEvent(hoursWorkWeekDuration);
            changeContractDataHoursWorkWeekEventHandler.handle(contractDataHoursWorkWeekEvent);
        }
    }

    public TextField getInputComponent() {
        return textFieldComponent;
    }

    private Boolean verifyHoursWorkWeekChangeIsValid(){

    Pattern timePattern = Pattern.compile("\\d{2}[:]\\d{2}");
        if(!timePattern.matcher(this.textFieldComponent.getText()).matches()) {
            this.textFieldComponent.setText("00:00");

            return false;
        }

        String hoursWorkPerWeek = textFieldComponent.getText();
        if((Utilities.converterTimeStringToDuration(hoursWorkPerWeek) == null)) {
            textFieldComponent.setText("00:00");

            return false;
        }

        Duration durationEnteredByUser = Utilities.converterTimeStringToDuration(hoursWorkPerWeek);
        assert durationEnteredByUser != null;
        if(durationEnteredByUser.compareTo(Parameters.LEGAL_MAXIMUM_HOURS_OF_WORK_PER_WEEK ) > 0 ){
            textFieldComponent.setText("00:00");

            return false;
        }

        return true;
    }

    public void setOnChangeContractDataHoursWorkWeek(EventHandler<ChangeContractDataHoursWorkWeekEvent> changeContractDataHoursWorkWeekEventHandler){
        this.changeContractDataHoursWorkWeekEventHandler = changeContractDataHoursWorkWeekEventHandler;
    }
}
