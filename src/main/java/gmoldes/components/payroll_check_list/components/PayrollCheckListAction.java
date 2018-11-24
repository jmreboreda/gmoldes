package gmoldes.components.payroll_check_list.components;

import gmoldes.components.ViewLoader;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class PayrollCheckListAction extends HBox {

    private static final String PAYROLL_CHECKLIST_ACTION_FXML = "/fxml/payroll_check_list/payrollchecklist_action_components.fxml";

    private EventHandler<MouseEvent> mouseEventEventHandlerOnExitButton;
    private EventHandler<MouseEvent> mouseEventEventHandlerOnClipboardButton;

    private Parent parent;

    @FXML
    private Button clipboardCopyButton;
    @FXML
    private Button exitButton;

    public PayrollCheckListAction() {
        this.parent = ViewLoader.load(this, PAYROLL_CHECKLIST_ACTION_FXML);
    }

    @FXML
    public void initialize(){

        clipboardCopyButton.setOnMouseClicked(this::onClipboardButton);
        exitButton.setOnMouseClicked(this::onExitButton);
    }

    public Button getClipboardCopyButton() {
        return clipboardCopyButton;
    }

    public void setClipboardCopyButton(Button clipboardCopyButton) {
        this.clipboardCopyButton = clipboardCopyButton;
    }

    public Button getExitButton() {
        return exitButton;
    }

    public void setExitButton(Button exitButton) {
        this.exitButton = exitButton;
    }

    private void onExitButton(MouseEvent event){
        this.mouseEventEventHandlerOnExitButton.handle(event);
    }

    private void onClipboardButton(MouseEvent event){
        this.mouseEventEventHandlerOnClipboardButton.handle(event);
    }

    public void setOnClipboardButton(EventHandler<MouseEvent> mouseEventEventHandlerOnClipboardButton){
        this.mouseEventEventHandlerOnClipboardButton = mouseEventEventHandlerOnClipboardButton;
    }

    public void setOnExitButton(EventHandler<MouseEvent> mouseEventEventHandlerOnExitButton){
       this.mouseEventEventHandlerOnExitButton = mouseEventEventHandlerOnExitButton;
    }
}
