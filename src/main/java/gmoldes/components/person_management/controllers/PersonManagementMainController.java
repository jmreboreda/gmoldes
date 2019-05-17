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

        personManagementSelector.setOnSelectorAction(this::onSelectorAction);

        personManagementAction.setOnOkButton(this::onOkButton);
        personManagementAction.setOnExitButton(this::onExitButton);
    }

    public PersonManagementMainController() {
        logger.info("Initializing new person main fxml");
        this.parent = ViewLoader.load(this, NEW_PERSON_MAIN_FXML);
    }

    private void onSelectorAction(ActionEvent event){
        personManagementData.setDisable(false);
        personManagementAction.getOkButton().setDisable(false);
    }

    private void onOkButton(MouseEvent event){

        if(!validateEntryAllData()){
            System.out.println("Datos incompletos.");

            return;
        }

        if(!verifyNieNifIsCorrect(personManagementData.getPersonNIF().getText())){
            System.out.println("NIE / NIF incorrecto.");

            return;
        }

        if(personManagementSelector.getNewPerson().isSelected()){


        }else if(personManagementSelector.getModificationPerson().isSelected()){


        }else{


        }
    }

    private void onExitButton(MouseEvent event){
        Stage stage = (Stage) personManagementHeader.getScene().getWindow();
        stage.close();
    }

    private Boolean validateEntryAllData(){

        if(personManagementData.getPersonSurName().getText().isEmpty() ||
                personManagementData.getPersonName().getText().isEmpty() ||
                personManagementData.getPersonNIF().getText().isEmpty() ||
                personManagementData.getPersonNASS().getText().isEmpty() ||
                personManagementData.getPersonBirthDate().getValue() == null ||
                personManagementData.getCivilStatus().getText().isEmpty() ||
                personManagementData.getNationality().getText().isEmpty() ||
                personManagementData.getPersonExtendedDirection().getText().isEmpty() ||
                personManagementData.getPersonPostalCode().getText().isEmpty() ||
                personManagementData.getPersonLocation().getText().isEmpty() ||
                personManagementData.getPersonMunicipality().getText().isEmpty() ||
                personManagementData.getPersonStudy().getSelectionModel().getSelectedItem() == null){

            return false;
        }

        return true;
    }

    private Boolean verifyNieNifIsCorrect(String nieNif){
        if(false){

            return false;
        }

        return true;
    }
}
