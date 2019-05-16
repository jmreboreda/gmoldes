package gmoldes.components.person_management.controllers;

import gmoldes.components.ViewLoader;
import gmoldes.components.person_management.components.PersonManagementAction;
import gmoldes.components.person_management.components.PersonManagementData;
import gmoldes.components.person_management.components.PersonManagementHeader;
import gmoldes.components.person_management.components.PersonManagementSelector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class PersonManagementMainController extends VBox {

    private static final Logger logger = Logger.getLogger(PersonManagementMainController.class.getSimpleName());
    private static final String NEW_PERSON_MAIN_FXML = "/fxml/person_management/person_management_main.fxml";

    private Parent parent;

    @FXML
    PersonManagementHeader personManagementHeader;
    @FXML
    PersonManagementSelector personManagementSelector;
    @FXML
    PersonManagementData personManagementData;
    @FXML
    PersonManagementAction personManagementAction;

    @FXML
    public void initialize() {

        personManagementData.setDisable(true);
        personManagementAction.getOkButton().setDisable(true);

        personManagementSelector.setOnNewPerson(this::onNewPerson);
        personManagementSelector.setOnModificationPerson(this::onModificationPerson);
        personManagementSelector.setOnDeletePerson(this::onDeletePerson);



        personManagementAction.setOnOkButton(this::onOkButton);
        personManagementAction.setOnExitButton(this::onExitButton);

    }

    public PersonManagementMainController() {
        logger.info("Initializing new person main fxml");
        this.parent = ViewLoader.load(this, NEW_PERSON_MAIN_FXML);
    }

    private void onNewPerson(ActionEvent event){
        personManagementData.setDisable(false);
        personManagementAction.getOkButton().setDisable(false);
        System.out.println("New Person");
    }

    private void onModificationPerson(ActionEvent event){
        personManagementData.setDisable(false);
        personManagementAction.getOkButton().setDisable(false);
        System.out.println("Modification Person");
    }

    private void onDeletePerson(ActionEvent event){
        personManagementData.setDisable(false);
        personManagementAction.getOkButton().setDisable(false);
        System.out.println("Delete Person");
    }

    private void onOkButton(MouseEvent event){
        System.out.println("Ok Button");
    }

    private void onExitButton(MouseEvent event){
        Stage stage = (Stage) personManagementHeader.getScene().getWindow();
        stage.close();
    }
}
