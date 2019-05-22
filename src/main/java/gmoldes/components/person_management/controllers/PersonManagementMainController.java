package gmoldes.components.person_management.controllers;

import gmoldes.components.ViewLoader;
import gmoldes.components.person_management.PersonManagementConstants;
import gmoldes.components.person_management.components.PersonManagementAction;
import gmoldes.components.person_management.components.PersonManagementData;
import gmoldes.components.person_management.components.PersonManagementHeader;
import gmoldes.components.person_management.components.PersonManagementSelector;
import gmoldes.components.person_management.events.PersonSurNamesItemSelectedEvent;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

public class PersonManagementMainController extends VBox {

    private static final Logger logger = Logger.getLogger(PersonManagementMainController.class.getSimpleName());
    private static final String NEW_PERSON_MAIN_FXML = "/fxml/person_management/person_management_main.fxml";

    private Parent parent;

    private PersonDTO personDTO;
    private PersonController personController = new PersonController();


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

        personManagementData.setOnSurNamesPatternChanged(this::onPersonSurNamesPatternChanged);
        personManagementData.setOnSurNamesItemSelected(this::onPersonSurNamesItemSelected);

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
        personManagementSelector.getNewPerson().setSelected(false);
        personManagementSelector.getModificationPerson().setDisable(false);
        personManagementSelector.getModificationPerson().setSelected(false);
        personManagementSelector.getDeletePerson().setDisable(false);
        personManagementSelector.getDeletePerson().setSelected(false);

        personManagementData.getNormalizeText().setSelected(true);
        personManagementData.setDisable(true);
        personManagementData.setMouseTransparent(false);

        personManagementAction.getSaveButton().setDisable(true);
        personManagementAction.getOkButton().setDisable(true);

        personManagementData.getPersonSurNames().getSelectionModel().clearSelection();
        personManagementData.getPersonName().setText("");
        personManagementData.getPersonNIF().setText("");
        personManagementData.getPersonNASS().setText("");
        personManagementData.getPersonBirthDate().setValue(null);
        personManagementData.getPersonCivilStatus().setText("");
        personManagementData.getPersonNationality().setText("Española");
        personManagementData.getPersonExtendedDirection().setText("");
        personManagementData.getPersonPostalCode().setText("");
        personManagementData.getPersonLocation().setText("");
        personManagementData.getPersonMunicipality().setText("");
        personManagementData.getPersonStudyLevel().getSelectionModel().select(null);
    }

    private void loadStudy(){
        StudyController studyController = new StudyController();

        List<StudyDTO> studyDTOList = studyController.findAllStudy();
        personManagementData.loadPersonStudyLevel(studyDTOList);
    }

    private void onPersonSurNamesPatternChanged(PersonSurNamesPatternChangedEvent personSurNamesPattern){
        if(personSurNamesPattern == null){

            return;
        }

        PersonController personController = new PersonController();

        List<PersonDTO> personDTOList = personController.findAllPersonsByNamePatternInAlphabeticalOrder(personSurNamesPattern.getPattern());
        ObservableList<PersonDTO> personDTOObservableList = FXCollections.observableList(personDTOList);
        personManagementData.refreshPersonSurNames(personDTOObservableList);
    }

    private void onPersonSurNamesItemSelected(PersonSurNamesItemSelectedEvent personSurNamesItemSelectedEvent){
        if(personSurNamesItemSelectedEvent.getPersonDTO() == null){

            return;
        }

        if(personManagementSelector.getModificationPerson().isSelected()){

            //personManagementData.refreshPersonData(personDTOItemSelected);;
        }else{

            personManagementData.getPersonSurNames().getSelectionModel().clearSelection();
            personManagementData.getPersonSurNames().setItems(null);
        }
    }

    private void onSelectorAction(ActionEvent event){

        if(personManagementSelector.getNewPerson().isSelected()){
            personManagementData.getPersonNameLabel().setVisible(true);
            personManagementData.getPersonName().setVisible(true);
            personManagementData.getNewPersonGroup().setVisible(true);
            personManagementData.getModificationPersonGroup().setVisible(false);
            personManagementData.getNewPersonHbox().setVisible(true);
            personManagementData.getModificationPersonHbox().setVisible(false);
        }

        if(personManagementSelector.getModificationPerson().isSelected()){
            personManagementData.getPersonNameLabel().setVisible(false);
            personManagementData.getPersonName().setVisible(false);
            personManagementData.getPersonName().setVisible(false);
            personManagementData.getNewPersonGroup().setVisible(true);
            personManagementData.getModificationPersonGroup().setVisible(true);
            personManagementData.getNewPersonHbox().setVisible(true);
            personManagementData.getModificationPersonHbox().setVisible(true);
        }

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
            Message.errorMessage(personManagementHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, PersonManagementConstants.NIE_NIF_IS_NOT_VALID);

            return;
        }

        if(personManagementData.getPersonNASS().getText().length() != 12){
            Message.errorMessage(personManagementHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, PersonManagementConstants.NASS_IS_NOT_VALID);

            return;
        }

        normalizeDataEntry();
        personManagementAction.getSaveButton().setDisable(false);
    }

    private void onSaveButton(MouseEvent event){
        personManagementData.setMouseTransparent(true);

        Short zeroShort = 0;
        String direction = personManagementData.getPersonLocation().getText().equals(personManagementData.getPersonMunicipality().getText()) ?
                personManagementData.getPersonExtendedDirection().getText() :
                personManagementData.getPersonExtendedDirection().getText() + "   " + personManagementData.getPersonLocation().getText();

        if(personManagementSelector.getNewPerson().isSelected()) {
            personDTO = PersonDTO.create()
                    .withIdpersona(null)
                    .withApellidos(personManagementData.getPersonSurNames().getEditor().getText())
                    .withNom_rzsoc(personManagementData.getPersonName().getText())
                    .withNifcif(personManagementData.getPersonNIF().getText())
                    .withNifcifdup(zeroShort)
                    .withNumafss(personManagementData.getPersonNASS().getText())
                    .withFechanacim(personManagementData.getPersonBirthDate().getValue())
                    .withEstciv(personManagementData.getPersonCivilStatus().getText())
                    .withDireccion(direction)
                    .withLocalidad(personManagementData.getPersonMunicipality().getText())
                    .withCodpostal(BigDecimal.valueOf(Long.parseLong(personManagementData.getPersonPostalCode().getText())))
                    .withNivestud(personManagementData.getPersonStudyLevel().getValue().getStudyId())
                    .withNacionalidad(personManagementData.getPersonNationality().getText())
                    .build();

        }else if(personManagementSelector.getModificationPerson().isSelected()){
            personDTO = PersonDTO.create()
                    .withIdpersona(null)
                    .withApellidos(personManagementData.getPersonNewSurnames().getText())
                    .withNom_rzsoc(personManagementData.getPersonNewName().getText())
                    .withNifcif(personManagementData.getPersonNIF().getText())
                    .withNifcifdup(zeroShort)
                    .withNumafss(personManagementData.getPersonNASS().getText())
                    .withFechanacim(personManagementData.getPersonBirthDate().getValue())
                    .withEstciv(personManagementData.getPersonCivilStatus().getText())
                    .withDireccion(direction)
                    .withLocalidad(personManagementData.getPersonMunicipality().getText())
                    .withCodpostal(BigDecimal.valueOf(Long.parseLong(personManagementData.getPersonPostalCode().getText())))
                    .withNivestud(personManagementData.getPersonStudyLevel().getValue().getStudyId())
                    .withNacionalidad(personManagementData.getPersonNationality().getText())
                    .build();
        }

        // Create person
        if(personManagementSelector.getNewPerson().isSelected()){
            Integer personId = personCreate();

            if(personId != null){
                Message.warningMessage(personManagementHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, PersonManagementConstants.PERSON_SAVED_OK);
            }else{
                Message.errorMessage(personManagementHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, PersonManagementConstants.PERSON_NOT_SAVED_OK);
                personManagementData.setMouseTransparent(false);

                return;
            }
        // Update person
        }else if(personManagementSelector.getModificationPerson().isSelected()){
            Integer personId = personUpdate();

            if(personId != null){
                Message.warningMessage(personManagementHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, PersonManagementConstants.PERSON_MODIFICATION_SAVED_OK);
            }else{
                Message.errorMessage(personManagementHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, PersonManagementConstants.PERSON_MODIFICATION_NOT_SAVED_OK);
                personManagementData.setMouseTransparent(false);

                return;
            }

        // Delete person
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
        if(personManagementData.getPersonSurNames().getEditor().getText().equals("") ||
                personManagementData.getPersonName().getText().equals("") ||
                personManagementData.getPersonNIF().getText().equals("") ||
                personManagementData.getPersonNASS().getText().equals("") ||
                personManagementData.getPersonBirthDate().getValue() == null ||
                personManagementData.getPersonCivilStatus().getText().equals("") ||
                personManagementData.getPersonNationality().getText().equals("")||
                personManagementData.getPersonExtendedDirection().getText().equals("") ||
                personManagementData.getPersonPostalCode().getText().equals("") ||
                personManagementData.getPersonLocation().getText().equals("") ||
                personManagementData.getPersonMunicipality().getText().equals("") ||
                personManagementData.getPersonStudyLevel().getSelectionModel().getSelectedItem() == null){

            return false;
        }

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

        return personController.createPerson(personDTO);
    }

    private Integer personUpdate(){

        return personController.updatePerson(personDTO);
    }

    private Integer personDelete(){
        Integer personId = null;

        return personId;
    }
}
