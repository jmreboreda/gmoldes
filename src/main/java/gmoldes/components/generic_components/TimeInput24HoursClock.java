package gmoldes.components.generic_components;

import gmoldes.components.ViewLoader;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;


public class TimeInput24HoursClock extends HBox {

    private static final String TEXT_INPUT = "/fxml/generic_components/text_input.fxml";

    private Parent parent;

    @FXML
    private Label textLabel;
    @FXML
    private TextField textField;

    public TimeInput24HoursClock() {

        this.parent = ViewLoader.load(this, TEXT_INPUT);
    }

    @FXML
    private void initialize(){

        this.textLabel.setMaxWidth(60);
        this.textField.setPrefWidth(75);
        textField.setAlignment(Pos.CENTER);
        setMargin(textLabel, new Insets(0, 5, 0, 0));
        setMargin(textField, new Insets(0, 5, 0, 0));
        textField.promptTextProperty().setValue("hh:mm");
        textField.setText(null);

        textField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue)
            {
                onAction(new ActionEvent());
            }
        });

        textField.setOnAction(this::onAction);
    }

    public String getText(){
        return this.textField.getText();
    }

    public void setText(String text){
        this.textField.setText(text);
    }

    public LocalTime getTime(){
        if(textField.getText().isEmpty()){
            return null;
        }

        return LocalTime.parse(this.textField.getText() + ":00");

    }

    public void setTextLabel (String text){
        this.textLabel.setText(text);
    }

    public void setLabelPreferredWidth(Double width){
        this.textLabel.setPrefWidth(width);
    }

    public void setInputPreferredWidth(Double width){
        this.textField.setPrefWidth(width);
    }

    public void setEditable(Boolean bol){
        this.textField.setEditable(bol);
    }

    public void setInputMinWidth(Double width){
        this.textField.setMinWidth(width);
    }

    public void inputRequestFocus(){
        this.textField.requestFocus();
    }

    private void onAction(ActionEvent event) {
        Pattern timePatternOne = Pattern.compile("\\d{2}[:]\\d{2}");
        if(textField.getText() == null){
            return;
        }
        if (!timePatternOne.matcher(textField.getText()).matches()) {
            this.textField.setText(null);
        } else {
            String hoursWorkPerWeek = textField.getText();
            if ((Utilities.converterTimeStringToDuration(hoursWorkPerWeek) == null)) {
                textField.setText(null);
            }else if(Utilities.converterTimeStringToDuration(hoursWorkPerWeek).compareTo(Duration.ofDays(1L)) > 0){
                textField.setText(null);
            }else if(Utilities.converterTimeStringToDuration(hoursWorkPerWeek).compareTo(Duration.ofDays(1L)) == 0){
                textField.setText("00:00");
            }
        }
    }
}
