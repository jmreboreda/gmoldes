package gmoldes.components.new_person.components;

import gmoldes.components.ViewLoader;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class NewPersonAction extends HBox {

    private static final String NEW_PERSON_ACTION_FXML = "/fxml/new_person/new_person_action_components.fxml";

    private EventHandler<MouseEvent> mouseEventEventHandlerOnExitButton;
    private EventHandler<MouseEvent> mouseEventEventHandlerOnOkButton;

    private Parent parent;

    @FXML
    private Button okButton;
    @FXML
    private Button exitButton;

    public NewPersonAction() {
        this.parent = ViewLoader.load(this, NEW_PERSON_ACTION_FXML);
    }

    @FXML
    public void initialize(){

        okButton.setOnMouseClicked(this::onOkButton);
        exitButton.setOnMouseClicked(this::onExitButton);
    }

    public Button getOkButton() {
        return okButton;
    }

    public Button getExitButton() {
        return exitButton;
    }

    private void onExitButton(MouseEvent event){
        this.mouseEventEventHandlerOnExitButton.handle(event);
    }

    private void onOkButton(MouseEvent event){
        this.mouseEventEventHandlerOnOkButton.handle(event);
    }

    public void setOnOkButton(EventHandler<MouseEvent> mouseEventEventHandlerOnOkButton){
        this.mouseEventEventHandlerOnOkButton = mouseEventEventHandlerOnOkButton;
    }

    public void setOnExitButton(EventHandler<MouseEvent> mouseEventEventHandlerOnExitButton){
       this.mouseEventEventHandlerOnExitButton = mouseEventEventHandlerOnExitButton;
    }
}
