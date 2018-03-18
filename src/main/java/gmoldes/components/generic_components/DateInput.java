package gmoldes.components.generic_components;

import gmoldes.components.ViewLoader;
import gmoldes.utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.time.LocalDate;

public class DateInput extends HBox {

    private static final String DATE_INPUT = "/fxml/generic_components/date_input.fxml";
    private EventHandler<ActionEvent> actionEventHandler;

    private Parent parent;

    @FXML
    private Label labelDate;
    @FXML
    private DatePicker dateInput;

    public DateInput() {

        this.parent = ViewLoader.load(this, DATE_INPUT);
    }

    @FXML
    private void initialize(){

        this.dateInput.setMaxWidth(130);
        setMargin(dateInput, new Insets(0, 0, 0, 5));

        dateInput.setConverter(Utilities.converter);
        dateInput.showWeekNumbersProperty().set(false);
        dateInput.setEditable(false);
        dateInput.setValue(LocalDate.now());

        dateInput.setOnAction(this::onAction);
    }

    private void onAction(ActionEvent event){
        actionEventHandler.handle(event);
    }

    public LocalDate getDate(){
        return this.dateInput.getValue();
    }

    public void setDate(LocalDate date){
        this.dateInput.setValue(date);
    }

    public void setLabelText(String text){
        this.labelDate.setText(text);
    }

    public void setOnAction(EventHandler<ActionEvent> eventHandler){
        this.actionEventHandler = eventHandler;
    }
}
