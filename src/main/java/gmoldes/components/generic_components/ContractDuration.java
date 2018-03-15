package gmoldes.components.generic_components;

import gmoldes.components.ViewLoader;
import gmoldes.utilities.Utilities;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.time.LocalDate;

public class ContractDuration extends HBox {

    private static final String CONTRACT_DURATION = "/fxml/generic_components/contract_duration.fxml";

    private Parent parent;

    @FXML
    private Label label;
    @FXML
    private TextField textField;

    public ContractDuration() {

        this.parent = ViewLoader.load(this, CONTRACT_DURATION);
    }

    @FXML
    private void initialize(){

        this.textField.setPrefHeight(25);
        this.label.setPrefWidth(28);
        this.textField.setMinWidth(100);
        setMargin(textField, new Insets(0, 0, 0, 0));

    }
}
