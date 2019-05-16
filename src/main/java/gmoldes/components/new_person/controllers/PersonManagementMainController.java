package gmoldes.components.new_person.controllers;

import gmoldes.components.ViewLoader;
import gmoldes.components.new_person.components.NewPersonAction;
import gmoldes.components.new_person.components.NewPersonData;
import gmoldes.components.new_person.components.NewPersonHeader;
import gmoldes.components.new_person.components.NewPersonManagementSelector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class PersonManagementMainController extends VBox {

    private static final Logger logger = Logger.getLogger(PersonManagementMainController.class.getSimpleName());
    private static final String NEW_PERSON_MAIN_FXML = "/fxml/new_person/new_person_main.fxml";

    private Parent parent;

    @FXML
    NewPersonHeader newPersonHeader;
    @FXML
    NewPersonManagementSelector newPersonManagementSelector;
    @FXML
    NewPersonData newPersonData;
    @FXML
    NewPersonAction newPersonAction;

    @FXML
    public void initialize() {

        newPersonData.setDisable(true);
        newPersonAction.getOkButton().setDisable(true);

        newPersonManagementSelector.setOnNewPerson(this::onNewPerson);
        newPersonManagementSelector.setOnModificationPerson(this::onModificationPerson);
        newPersonManagementSelector.setOnDeletePerson(this::onDeletePerson);



        newPersonAction.setOnOkButton(this::onOkButton);
        newPersonAction.setOnExitButton(this::onExitButton);

    }

    public PersonManagementMainController() {
        logger.info("Initializing new person main fxml");
        this.parent = ViewLoader.load(this, NEW_PERSON_MAIN_FXML);
    }

    private void onNewPerson(ActionEvent event){
        newPersonData.setDisable(false);
        newPersonAction.getOkButton().setDisable(false);
        System.out.println("New Person");
    }

    private void onModificationPerson(ActionEvent event){
        newPersonData.setDisable(false);
        newPersonAction.getOkButton().setDisable(false);
        System.out.println("Modification Person");
    }

    private void onDeletePerson(ActionEvent event){
        newPersonData.setDisable(false);
        newPersonAction.getOkButton().setDisable(false);
        System.out.println("Delete Person");
    }

    private void onOkButton(MouseEvent event){
        System.out.println("Ok Button");
    }

    private void onExitButton(MouseEvent event){
        Stage stage = (Stage) newPersonHeader.getScene().getWindow();
        stage.close();
//        System.out.println("Exit Button");
    }
}
