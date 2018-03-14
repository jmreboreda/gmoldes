package gmoldes.components.contract.new_contract;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract.events.ChangeContractDataHoursWorkWeekEvent;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.Utilities;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.time.Duration;
import java.util.regex.Pattern;

public class CustomTextFieldHoursWorkWeek extends HBox {

    private static final String CUSTOM_TEXTFIELD = "/fxml/generic_components/custom_textfield.fxml";

    private EventHandler<ChangeContractDataHoursWorkWeekEvent> changeContractDataHoursWorkWeekEventHandler;

    private Parent parent;

    @FXML
    private Label labelOfTextField;
    @FXML
    private TextField textFieldComponent;

    public CustomTextFieldHoursWorkWeek() {

        this.parent = ViewLoader.load(this, CUSTOM_TEXTFIELD);
    }

    @FXML
    private void initialize(){

        this.labelOfTextField.setPrefHeight(25);
        this.labelOfTextField.setPrefWidth(28);
        this.labelOfTextField.setText(Parameters.HOURS_WORK_WEEK_TEXT);
        this.labelOfTextField.setMinWidth(215);
        this.textFieldComponent.setMaxWidth(60);
        this.setMargin(textFieldComponent, new Insets(0, 0, 0, 10));

    }

    public Label getLabelTextField() {
        return labelOfTextField;
    }

    public void setLabelTextField(Label labelTextField) {
        this.labelOfTextField = labelTextField;
    }

    public TextField getTextFieldComponent() {
        return textFieldComponent;
    }

    public void setTextFieldComponent(TextField textFieldComponent) {
        this.textFieldComponent = textFieldComponent;
    }

    public Boolean verifyHoursWorkWeekChangeIsValid(){

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
}
