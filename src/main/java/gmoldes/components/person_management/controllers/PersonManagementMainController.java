package gmoldes.components.person_management.controllers;

import gmoldes.components.ViewLoader;
import gmoldes.components.person_management.PersonManagementConstants;
import gmoldes.components.person_management.components.PersonManagementAction;
import gmoldes.components.person_management.components.PersonManagementData;
import gmoldes.components.person_management.components.PersonManagementHeader;
import gmoldes.components.person_management.components.PersonManagementSelector;
import gmoldes.components.person_management.events.PersonSurNamesPatternChangedEvent;
import gmoldes.domain.nie_nif.NieNif;
import gmoldes.domain.person.controllers.PersonController;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.study.StudyController;
import gmoldes.domain.study.dto.StudyDTO;
import gmoldes.utilities.Message;
import gmoldes.utilities.Parameters;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.lang.WordUtils;

import java.util.List;
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
        personManagementSelector.setOnSelectorAction(this::onSelectorAction);

        personManagementData.setOnSurNamesPatternChanged(this::onSurNamesPatternChanged);

        personManagementAction.setOnOkButton(this::onOkButton);
        personManagementAction.setOnSaveButton(this::onSaveButton);
        personManagementAction.setOnExitButton(this::onExitButton);
    }

    public PersonManagementMainController() {
        logger.info("Initializing new person main fxml");
        this.parent = ViewLoader.load(this, NEW_PERSON_MAIN_FXML);

        loadInitialInterfaceStatus();
        loadStudy();
    }

    private void loadInitialInterfaceStatus(){
        personManagementSelector.getNewPerson().setDisable(false);
        personManagementSelector.getModificationPerson().setDisable(false);
        personManagementSelector.getDeletePerson().setDisable(false);
        personManagementData.setDisable(true);
        personManagementData.setMouseTransparent(false);
        personManagementAction.getSaveButton().setDisable(true);
        personManagementAction.getOkButton().setDisable(true);

        personManagementData.getPersonSurNames().getEditor().setText(null);
        personManagementData.getPersonName().setText(null);
        personManagementData.getPersonNIF().setText(null);
        personManagementData.getPersonNASS().setText(null);
        personManagementData.getPersonBirthDate().setValue(null);
        personManagementData.getPersonCivilStatus().setText(null);
        personManagementData.getPersonNationality().setText("Española");
        personManagementData.getPersonExtendedDirection().setText(null);
        personManagementData.getPersonPostalCode().setText(null);
        personManagementData.getPersonLocation().setText(null);
        personManagementData.getPersonMunicipality().setText(null);
        personManagementData.getPersonStudyLevel().getSelectionModel().select(null);
    }

    private void loadStudy(){
        StudyController studyController = new StudyController();

        List<StudyDTO> studyDTOList = studyController.findAllStudy();
        personManagementData.loadPersonStudyLevel(studyDTOList);
    }

    private void onSurNamesPatternChanged(PersonSurNamesPatternChangedEvent personSurNamesPattern){
        PersonController personController = new PersonController();

        List<PersonDTO> personDTOList = personController.findAllPersonsByNamePatternInAlphabeticalOrder(personSurNamesPattern.getPattern());
        ObservableList<PersonDTO> personDTOObservableList = FXCollections.observableList(personDTOList);
        personManagementData.refreshPersonSurNames(personDTOObservableList);
    }

    private void onSelectorAction(ActionEvent event){
        personManagementData.setDisable(false);
        personManagementAction.getOkButton().setDisable(false);
    }

    private void onOkButton(MouseEvent event){
        if(!validateEntryAllData()){
            Message.warningMessage(personManagementHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, PersonManagementConstants.INCOMPLETE_DATA_ENTRY);

            return;
        }

        personManagementData.getPersonNIF().setText(personManagementData.getPersonNIF().getText().toUpperCase());
        NieNif introducedNieNif = new NieNif(personManagementData.getPersonNIF().getText());
        if(!introducedNieNif.validateNieNif()){
            Message.warningMessage(personManagementHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, PersonManagementConstants.NIE_NIF_IS_NOT_VALID);

            return;
        }

        normalizeDataEntry();
    }

    private void onSaveButton(MouseEvent event){
        personManagementData.setMouseTransparent(true);

        if(personManagementSelector.getNewPerson().isSelected()){
            Integer personId = personCreate();


        }else if(personManagementSelector.getModificationPerson().isSelected()){
            Integer personId = personUpdate();


        }else{
            Integer personId = personDelete();
        }

        loadInitialInterfaceStatus();
    }

    private void onExitButton(MouseEvent event){
        Stage stage = (Stage) personManagementHeader.getScene().getWindow();
        stage.close();
    }

    private Boolean validateEntryAllData(){

        if(personManagementData.getPersonSurNames().getEditor().getText() == null ||
                personManagementData.getPersonName().getText() == null ||
                personManagementData.getPersonNIF().getText() == null||
                personManagementData.getPersonNASS().getText() == null ||
                personManagementData.getPersonBirthDate().getValue() == null ||
                personManagementData.getPersonCivilStatus().getText() == null ||
                personManagementData.getPersonNationality().getText() == null ||
                personManagementData.getPersonExtendedDirection().getText() == null ||
                personManagementData.getPersonPostalCode().getText() == null ||
                personManagementData.getPersonLocation().getText() == null ||
                personManagementData.getPersonMunicipality().getText() == null ||
                personManagementData.getPersonStudyLevel().getSelectionModel().getSelectedItem() == null){

            return false;
        }

        personManagementAction.getSaveButton().setDisable(false);
        return true;
    }

    private void normalizeDataEntry(){
        if(personManagementData.getNormalizeText().isSelected()) {
            personManagementData.getPersonSurNames().getEditor().setText(WordUtils.capitalizeFully(personManagementData.getPersonSurNames().getEditor().getText()));
            personManagementData.getPersonName().setText(WordUtils.capitalizeFully(personManagementData.getPersonName().getText()));
            personManagementData.getPersonExtendedDirection().setText(WordUtils.capitalizeFully(personManagementData.getPersonExtendedDirection().getText()));
            personManagementData.getPersonCivilStatus().setText(WordUtils.capitalizeFully(personManagementData.getPersonCivilStatus().getText()));
            personManagementData.getPersonNationality().setText(WordUtils.capitalizeFully(personManagementData.getPersonNationality().getText()));
            personManagementData.getPersonLocation().setText(WordUtils.capitalizeFully(personManagementData.getPersonLocation().getText()));
            personManagementData.getPersonMunicipality().setText(WordUtils.capitalizeFully(personManagementData.getPersonMunicipality().getText()));
        }
    }

    private Integer personCreate(){
        Integer personId = null;

        return personId;
    }

    private Integer personUpdate(){
        Integer personId = null;

        return personId;
    }

    private Integer personDelete(){
        Integer personId = null;

        return personId;
    }
}
