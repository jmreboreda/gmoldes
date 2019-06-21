package gmoldes.components.contract.consultation_contract.components;

import gmoldes.components.ViewLoader;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ConsultationContractAction extends AnchorPane {

    private static final String CONSULTATION_CONTRACT_ACTION_FXML = "/fxml/consultation_contract/consultation_contract_action_components.fxml";

    private EventHandler<MouseEvent> onOkButtonEventHandler;
    private EventHandler<MouseEvent> onSaveButtonEventHandler;
    private EventHandler<MouseEvent> onExitButtonEventEventHandler;


    private Parent parent;

    @FXML
    private Button okButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button exitButton;

    public ConsultationContractAction() {
        this.parent = ViewLoader.load(this, CONSULTATION_CONTRACT_ACTION_FXML);
    }

    @FXML
    public void initialize(){

        okButton.setOnMouseClicked(this::onOkButton);
        saveButton.setOnMouseClicked(this::onSaveButton);
        exitButton.setOnMouseClicked(this::onExitButton);
    }

    public Button getOkButton() {
        return okButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getExitButton() {
        return exitButton;
    }

    private void onOkButton(MouseEvent event){
        this.onOkButtonEventHandler.handle(event);
    }

    private void onSaveButton(MouseEvent event){
        this.onSaveButtonEventHandler.handle(event);
    }

    private void onExitButton(MouseEvent event){
        this.onExitButtonEventEventHandler.handle(event);
    }

    public void setOnOkButton(EventHandler<MouseEvent> mouseEventEventHandlerOnOkButton){
        this.onOkButtonEventHandler = mouseEventEventHandlerOnOkButton;
    }

    public void setOnSaveButton(EventHandler<MouseEvent> onSaveButtonEventHandler){
        this.onSaveButtonEventHandler = onSaveButtonEventHandler;
    }

    public void setOnExitButton(EventHandler<MouseEvent> mouseEventEventHandlerOnExitButton){
       this.onExitButtonEventEventHandler = mouseEventEventHandlerOnExitButton;
    }
}
