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
import gmoldes.domain.study.StudyService;
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
    private static final String SPANISH_NATIONALITY = "Española";
    private static final String NEW_PERSON_MAIN_FXML = "/fxml/person_management/person_management_main.fxml";

    private Parent parent;

    private PersonDTO personDTO;
    private PersonController personController = new PersonController();


    @FXML
    private
    PersonManagementHeader personManagementHeader;
    @FXML
    private
    PersonManagementSelector personManagementSelector;
    @FXML
    private
    PersonManagementData personManagementData;
    @FXML
    private
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
        logger.info("Initializing person management main fxml");
        this.parent = ViewLoader.load(this, NEW_PERSON_MAIN_FXML);

        loadInitialStateDataInterface();
        loadCivilStatus();
        loadStudy();
    }

    private void loadInitialStateDataInterface(){
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
        personManagementData.getPersonSurNames().getItems().clear();
        personManagementData.getPersonSurNames().setMouseTransparent(false);
        personManagementData.getPersonName().setText("");
        personManagementData.getPersonNewSurNames().setText("");
        personManagementData.getPersonSurNames().setStyle(PersonManagementConstants.BLUE_COLOR);
        personManagementData.getPersonNewName().setText("");
        personManagementData.getPersonNIF().setText("");
        personManagementData.getPersonNASS().setText("");
        personManagementData.getPersonBirthDate().setValue(null);
        personManagementData.getPersonCivilStatus().getSelectionModel().clearSelection();
        personManagementData.getPersonCivilStatus().setStyle(PersonManagementConstants.BLUE_COLOR);
        personManagementData.getPersonNationality().setText(SPANISH_NATIONALITY);
        personManagementData.getPersonExtendedDirection().setText("");
        personManagementData.getPersonPostalCode().setText("");
        personManagementData.getPersonLocation().setText("");
        personManagementData.getPersonMunicipality().setText("");
        personManagementData.getPersonStudyLevel().getSelectionModel().select(null);
    }

    private void loadCivilStatus(){
        personManagementData.loadPersonCivilStatus();
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

            personManagementData.getPersonSurNames().setStyle(PersonManagementConstants.RED_COLOR);
            personManagementData.getPersonSurNames().setMouseTransparent(true);

            StudyService studyService = StudyService.StudyServiceFactory.getInstance();
            Integer studyLevel = personManagementData.getPersonSurNames().getSelectionModel().getSelectedItem() != null ?  personManagementData.getPersonSurNames().getSelectionModel().getSelectedItem().getNivestud() : null;
            if(studyLevel != null) {
                StudyDTO studyDTO = studyService.findStudyByStudyId(personManagementData.getPersonSurNames().getSelectionModel().getSelectedItem().getNivestud());
                personManagementData.completePersonData(personSurNamesItemSelectedEvent.getPersonDTO(), studyDTO);
            }
        }else if(personManagementSelector.getNewPerson().isSelected()){

            personManagementData.getPersonSurNames().getSelectionModel().clearSelection();
            personManagementData.getPersonSurNames().getItems().clear();
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

            personManagementData.getNormalizeText().setSelected(false);
        }

        if(personManagementSelector.getDeletePerson().isSelected()){
            Message.warningMessage((Stage) personManagementHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, PersonManagementConstants.OPTION_NOT_IMPLEMENTED_YET);
            loadInitialStateDataInterface();

            return;
        }

        personManagementData.setDisable(false);
        personManagementAction.getOkButton().setDisable(false);
    }

    private void onOkButton(MouseEvent event){
        if(!validateEntryAllData()){

            return;
        }

        if(!validateNieNif(personManagementData.getPersonNIF().getText().toUpperCase())){

            return;
        }

        if(!validateNASS(personManagementData.getPersonNASS().getText())){

            return;
        }

        normalizeDataEntry();
        personManagementAction.getSaveButton().setDisable(false);
    }

    private void onSaveButton(MouseEvent event){
        if(!validateEntryAllData()){
            Message.warningMessage((Stage) personManagementHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, PersonManagementConstants.INCOMPLETE_DATA_ENTRY);
            personManagementAction.getSaveButton().setDisable(true);

            return;
        }

        if(!validateNieNif(personManagementData.getPersonNIF().getText())){
            personManagementAction.getSaveButton().setDisable(true);

            return;
        }

        if(!validateNASS(personManagementData.getPersonNASS().getText())){
            personManagementAction.getSaveButton().setDisable(true);

            return;
        }

        personManagementData.setMouseTransparent(true);

        String direction = personManagementData.getPersonLocation().getText().equals(personManagementData.getPersonMunicipality().getText()) ?
                personManagementData.getPersonExtendedDirection().getText() :
                personManagementData.getPersonExtendedDirection().getText() + "   " + personManagementData.getPersonLocation().getText();

        if(personManagementSelector.getNewPerson().isSelected()) {
            personDTO = PersonDTO.create()
                    .withIdpersona(null)
                    .withApellidos(personManagementData.getPersonSurNames().getEditor().getText().replace(",", "").trim())
                    .withNom_rzsoc(personManagementData.getPersonName().getText())
                    .withNifcif(personManagementData.getPersonNIF().getText())
                    .withNifcifdup((short) 0)
                    .withNumafss(personManagementData.getPersonNASS().getText())
                    .withFechanacim(personManagementData.getPersonBirthDate().getValue())
                    .withEstciv(personManagementData.getPersonCivilStatus().getValue().toString())
                    .withDireccion(direction)
                    .withLocalidad(personManagementData.getPersonMunicipality().getText())
                    .withCodpostal(BigDecimal.valueOf(Long.parseLong(personManagementData.getPersonPostalCode().getText())))
                    .withNivestud(personManagementData.getPersonStudyLevel().getValue().getStudyId())
                    .withNacionalidad(personManagementData.getPersonNationality().getText())
                    .build();

        }else if(personManagementSelector.getModificationPerson().isSelected()){

            Integer personId = personManagementData.getPersonSurNames().getSelectionModel().getSelectedItem().getIdpersona();

            personDTO = PersonDTO.create()
                    .withIdpersona(personId)
                    .withApellidos(personManagementData.getPersonNewSurNames().getText())
                    .withNom_rzsoc(personManagementData.getPersonNewName().getText())
                    .withNifcif(personManagementData.getPersonNIF().getText())
                    .withNifcifdup((short) 0)
                    .withNumafss(personManagementData.getPersonNASS().getText())
                    .withFechanacim(personManagementData.getPersonBirthDate().getValue())
                    .withEstciv(personManagementData.getPersonCivilStatus().getValue().toString())
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
                Message.informationMessage((Stage) personManagementHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, PersonManagementConstants.PERSON_SAVED_OK);
                logger.info("Person management: new person saved ok.");

            }else{
                Message.errorMessage((Stage) personManagementHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, PersonManagementConstants.PERSON_NOT_SAVED_OK);
                personManagementData.setMouseTransparent(false);
                logger.info("Person management: new person failed.");


                return;
            }
        // Update person
        }else if(personManagementSelector.getModificationPerson().isSelected()){
            Integer personId = personUpdate();

            if(personId != null){
                Message.informationMessage((Stage) personManagementHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, PersonManagementConstants.PERSON_MODIFICATION_SAVED_OK);
                logger.info("Person management: person updated ok.");

            }else{
                Message.errorMessage((Stage) personManagementHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, PersonManagementConstants.PERSON_MODIFICATION_NOT_SAVED_OK);
                personManagementData.setMouseTransparent(false);
                logger.info("Person management: person updated failed.");

                return;
            }

        // Delete person
        }else{
            Integer personId = personDelete();
        }

        loadInitialStateDataInterface();
    }

    private void onExitButton(MouseEvent event){
        if(personManagementSelector.getNewPerson().isSelected() ||
        personManagementSelector.getModificationPerson().isSelected() ||
        personManagementSelector.getDeletePerson().isSelected()){
            loadInitialStateDataInterface();

            return;
        }

        logger.info("Person management: exiting program.");

        Stage stage = (Stage) personManagementHeader.getScene().getWindow();
        stage.close();
    }

    private Boolean validateEntryAllData(){
        if(personManagementData.getPersonSurNames().getEditor().getText().equals("") ||
                personManagementData.getPersonName().getText().equals("") ||
                personManagementData.getPersonNIF().getText().equals("") ||
                personManagementData.getPersonNASS().getText().equals("") ||
                personManagementData.getPersonBirthDate().getValue() == null ||
                personManagementData.getPersonCivilStatus().getSelectionModel().getSelectedItem() == null ||
                personManagementData.getPersonNationality().getText().equals("")||
                personManagementData.getPersonExtendedDirection().getText().equals("") ||
                personManagementData.getPersonPostalCode().getText().equals("") ||
                personManagementData.getPersonLocation().getText().equals("") ||
                personManagementData.getPersonMunicipality().getText().equals("") ||
                personManagementData.getPersonStudyLevel().getSelectionModel().getSelectedItem() == null){

            Message.errorMessage((Stage) personManagementHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, PersonManagementConstants.INCOMPLETE_DATA_ENTRY);
            personManagementAction.getSaveButton().setDisable(true);

            return false;
        }

        return true;
    }

    private Boolean validateNieNif(String nienif){
        NieNif introducedNieNif = new NieNif(personManagementData.getPersonNIF().getText());
        if(!introducedNieNif.validateNieNif()){
            Message.errorMessage((Stage) personManagementHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, PersonManagementConstants.NIE_NIF_IS_NOT_VALID);
            personManagementAction.getSaveButton().setDisable(true);

            return false;
        }
        else {
            List<PersonDTO> repeatedNieNifList = verifyIsRepeatedNieNif(personManagementData.getPersonNIF().getText(), personManagementData.getPersonSurNames().getValue().getIdpersona());
            String detailedMessage = PersonManagementConstants.QUESTION_IS_CORRECT_REPEATED_NIE_NIF;
            if (!repeatedNieNifList.isEmpty()) {
                for(PersonDTO personDTO : repeatedNieNifList){
                    detailedMessage = detailedMessage + "\t- " + personDTO.toAlphabeticalName() + "\n\n";
                }

                detailedMessage = detailedMessage + "¿ Desea mantener el NIE/NIF introducido ?" + "\n\n";

                if (!Message.confirmationMessage((Stage) personManagementHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, detailedMessage)) {
                    personManagementData.getPersonNIF().setText(null);
                    personManagementAction.getSaveButton().setDisable(true);

                    return false;
                }
            }
        }

        return true;
    }

    private Boolean validateNASS(String numberASS){

        Integer introducedProvinceCodeNASS = null;
        Long introducedAffiliateNumberNASS = null;
        Long introducedControlDigitNASS = null;

        Boolean isValidNASS = true;

        if(numberASS.length() != 12){
            isValidNASS = false;
        }
        else {

            introducedProvinceCodeNASS = Integer.parseInt(numberASS.substring(0, 2));
            introducedAffiliateNumberNASS = Long.parseLong(numberASS.substring(2, 10));
            introducedControlDigitNASS = Long.parseLong(numberASS.substring(10, 12));


            Long calculatedControlDigitNASS;

            if (introducedProvinceCodeNASS < 1 || (introducedProvinceCodeNASS > 53 && introducedProvinceCodeNASS != 66)) {
                isValidNASS = false;
            }

            if (introducedAffiliateNumberNASS < 10000000) {
                calculatedControlDigitNASS = (introducedAffiliateNumberNASS + introducedProvinceCodeNASS * 10000000) % 97;

            } else {
                Long numberNASSWithoutControlDigit = Long.parseLong(introducedProvinceCodeNASS.toString().concat(introducedAffiliateNumberNASS.toString()));

                calculatedControlDigitNASS = numberNASSWithoutControlDigit % 97;
            }

            if (!introducedControlDigitNASS.equals(calculatedControlDigitNASS)) {
                isValidNASS = false;
            }
        }

        if(!isValidNASS){
            Message.errorMessage((Stage) personManagementHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, PersonManagementConstants.NASS_IS_NOT_VALID);
        }else {
            List<PersonDTO> repeatedNassList = verifyIsRepeatedNass(personManagementData.getPersonNASS().getText(), personManagementData.getPersonSurNames().getValue().getIdpersona());
            String detailedMessage = PersonManagementConstants.IS_NOT_CORRECT_REPEATED_NASS;
            if (!repeatedNassList.isEmpty()) {
                for(PersonDTO personDTO : repeatedNassList){
                    detailedMessage = detailedMessage + "\t- " + personDTO.toAlphabeticalName() + "\n\n";
                }

                detailedMessage = detailedMessage + "\n\n";

                Message.errorMessage((Stage) personManagementHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, detailedMessage);
                personManagementAction.getSaveButton().setDisable(true);

                isValidNASS = false;
            }
        }

        return isValidNASS;
    }

    private List<PersonDTO> verifyIsRepeatedNieNif(String nieNif, Integer personId){

        return personController.findPersonByNieNif(nieNif, personId);
    }

    private List<PersonDTO> verifyIsRepeatedNass(String nieNif, Integer personId){

        return personController.findPersonByNass(nieNif, personId);
    }

    private void normalizeDataEntry(){
        if(personManagementData.getNormalizeText().isSelected()) {
            personManagementData.getPersonSurNames().getEditor().setText(WordUtils.capitalizeFully(personManagementData.getPersonSurNames().getEditor().getText()));
            personManagementData.getPersonName().setText(WordUtils.capitalizeFully(personManagementData.getPersonName().getText()));
            personManagementData.getPersonNIF().setText(personManagementData.getPersonNIF().getText().toUpperCase());
            personManagementData.getPersonExtendedDirection().setText(WordUtils.capitalizeFully(personManagementData.getPersonExtendedDirection().getText()));
//            personManagementData.getPersonCivilStatus().setText(WordUtils.capitalizeFully(personManagementData.getPersonCivilStatus().getText()));
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
