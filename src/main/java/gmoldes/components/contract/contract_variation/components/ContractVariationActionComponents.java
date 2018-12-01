package gmoldes.components.contract.contract_variation.components;

import gmoldes.components.ViewLoader;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class ContractVariationActionComponents extends AnchorPane {

    private static final String CONTRACT_ACTION_COMPONENTS_FXML = "/fxml/contract_variations/contractvariation_action_components.fxml";

    private Parent parent;

    private EventHandler<MouseEvent> SendMailButtonEventHandler;
    private EventHandler<MouseEvent> OkButtonEventHandler;
    private EventHandler<MouseEvent> viewPDFButtonEventHandler;
    private EventHandler<MouseEvent> onExitButtonEventHandler;

    @FXML
    private Button sendMailButton;
    @FXML
    private Button OkButton;
    @FXML
    private Button viewPDFButton;
    @FXML
    private Button exitButton;

    public ContractVariationActionComponents() {

        this.parent = ViewLoader.load(this, CONTRACT_ACTION_COMPONENTS_FXML);
        sendMailButton.setOnMouseClicked(this::onSendMailButton);
        OkButton.setOnMouseClicked(this::onOkButton);
        viewPDFButton.setOnMouseClicked(this::onViewPDFButton);
        exitButton.setOnMouseClicked(this::onExitButton);
    }

    public Button getSendMailButton() {
        return sendMailButton;
    }

    public void setSendMailButton(Button sendMailButton) {
        this.sendMailButton = sendMailButton;
    }

    public Button getOkButton() {
        return OkButton;
    }

    public void setOkButton(Button okButton) {
        OkButton = okButton;
    }

    public Button getViewPDFButton() {
        return viewPDFButton;
    }

    public void setViewPDFButton(Button viewPDFButton) {
        this.viewPDFButton = viewPDFButton;
    }

    public Button getExitButton() {
        return exitButton;
    }

    public void setExitButton(Button exitButton) {
        this.exitButton = exitButton;
    }

    private void onSendMailButton(MouseEvent event){
        this.SendMailButtonEventHandler.handle(event);
    }

    private void onOkButton(MouseEvent event){
        this.OkButtonEventHandler.handle(event);
    }

    private void onViewPDFButton(MouseEvent event){
        this.viewPDFButtonEventHandler.handle(event);
    }

    private void onExitButton(MouseEvent event){
        onExitButtonEventHandler.handle(event);
    }


    public void enableSendMailButton(Boolean bol){
        this.sendMailButton.setDisable(!bol);
    }

    public void enablePDFButton(Boolean bol){
        this.viewPDFButton.setDisable(!bol);
    }

    public void enableOkButton(Boolean bol){
        this.OkButton.setDisable(!bol);
    }

    public void setOnSendMailButton(EventHandler<MouseEvent> sendMailButtonEventHandler){
        this.SendMailButtonEventHandler = sendMailButtonEventHandler;
    }

    public void setOnOkButton(EventHandler<MouseEvent> okButtonEventHandler){
        this.OkButtonEventHandler = okButtonEventHandler;
    }

    public void setOnViewPDFButton(EventHandler<MouseEvent> viewPDFButtonEventHandler){
        this.viewPDFButtonEventHandler = viewPDFButtonEventHandler;
    }

    public void setOnExitButton(EventHandler<MouseEvent> event){
        this.onExitButtonEventHandler = event;
    }
}
