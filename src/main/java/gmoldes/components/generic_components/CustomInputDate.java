package gmoldes.components.generic_components;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract.events.ChangeContractDataHoursWorkWeekEvent;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class CustomInputDate extends HBox {

    private static final String CUSTOM_DATE_INPUT = "/fxml/generic_components/custom_datePicker.fxml";

    private Parent parent;

    @FXML
    private Label labelDate;
    @FXML
    private DatePicker dateInput;

    public CustomInputDate() {

        this.parent = ViewLoader.load(this, CUSTOM_DATE_INPUT);
    }

    @FXML
    private void initialize(){

        this.dateInput.setPrefHeight(25);
        this.labelDate.setPrefWidth(28);
        //this.labelDate.setText(Parameters.DATE_TEXT);
        //this.labelDate.setMinWidth(215);
        this.dateInput.setMinWidth(100);
        setMargin(dateInput, new Insets(0, 0, 0, 0));

        dateInput.setConverter(Utilities.converter);
        dateInput.showWeekNumbersProperty().set(false);
        dateInput.setEditable(false);
        dateInput.setValue(LocalDate.now());
    }
}
