package gmoldes.components.contract.new_contract;

import gmoldes.components.ViewLoader;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class ContractActionComponents extends AnchorPane {

    private static final String CURRENT_CONTRACT_FXML = "/fxml/new_contract/contract_action_components.fxml";

    private Parent parent;

    private EventHandler<MouseEvent> SendMailButtonEventHandler;
    private EventHandler<MouseEvent> OkButtonEventHandler;
    private EventHandler<MouseEvent> viewPDFButtonEventHandler;
    private EventHandler<MouseEvent> onExistButtonEventHandler;

    @FXML
    private Button sendMailButton;
    @FXML
    private Button OkButton;
    @FXML
    private Button viewPDFButton;
    @FXML
    private Button exitButton;

    public ContractActionComponents() {

        this.parent = ViewLoader.load(this, CURRENT_CONTRACT_FXML);
        sendMailButton.setOnMouseClicked(this::onSendMailButton);
        OkButton.setOnMouseClicked(this::onOkButton);
        viewPDFButton.setOnMouseClicked(this::onViewPDFButton);
        exitButton.setOnMouseClicked(this::onExitButton);
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
        onExistButtonEventHandler.handle(event);
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
        this.onExistButtonEventHandler = event;
    }
}
