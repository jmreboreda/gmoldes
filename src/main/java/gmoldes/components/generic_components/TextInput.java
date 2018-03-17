package gmoldes.components.generic_components;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract.events.ChangeContractDataHoursWorkWeekEvent;
import gmoldes.components.contract.events.ChangeTextInputEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;


public class TextInput extends HBox {

    private static final String TEXT_INPUT = "/fxml/generic_components/text_input.fxml";
    private EventHandler<ChangeTextInputEvent> changeInputEventHandler;

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
        textField.setAlignment(Pos.CENTER);
        setMargin(textField, new Insets(0, 0, 0, 0));

    }

    public String getText(){
        return this.textField.getText();
    }

    public void setText(String text){
        this.textField.setText(text);
        if(getText() != null) {
            ChangeTextInputEvent changeTextInputEvent = new ChangeTextInputEvent(getText());
//            this.changeInputEventHandler.handle(changeTextInputEvent);
        }
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

    public void setInputMinWidth(Double width){
        this.textField.setMinWidth(width);
    }

    public void inputRequestFocus(){
        this.textField.requestFocus();
    }

    public void setOnAction(EventHandler<ChangeTextInputEvent> handler) {
        this.changeInputEventHandler = handler;
    }
}
