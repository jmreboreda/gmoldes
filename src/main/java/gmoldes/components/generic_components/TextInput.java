package gmoldes.components.generic_components;

import gmoldes.components.ViewLoader;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class TextInput extends HBox {

    private static final String TEXT_INPUT = "/fxml/generic_components/text_input.fxml";

    private Parent parent;

    @FXML
    private Label textLabel;
    @FXML
    private TextField textField;

    public TextInput() {

        this.parent = ViewLoader.load(this, TEXT_INPUT);
    }

    @FXML
    private void initialize(){

        this.textLabel.setPrefWidth(28);
        this.textField.setMaxWidth(50);
        setMargin(textField, new Insets(0, 0, 0, 0));

    }

    public String getText(){
        return this.textField.getText();
    }

    public void setText(String text){
        this.textField.setText(text);
    }

    public void setTextLabel (String text){
        this.textLabel.setText(text);
    }
}
