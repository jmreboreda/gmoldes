package gmoldes.components.person_management.components;

import gmoldes.ApplicationConstants;
import gmoldes.components.ViewLoader;
import gmoldes.components.person_management.events.PersonSurNamesPatternChangedEvent;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.study.dto.StudyDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.converter.LocalDateStringConverter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

public class PersonManagementData extends VBox {

    private static final String NEW_PERSON_DATA_FXML = "/fxml/person_management/person_management_data.fxml";

    private Parent parent;

    private EventHandler<PersonSurNamesPatternChangedEvent> surNamesPatternChangedEventHandler;

    @FXML
    private ComboBox <PersonDTO> personSurNames;
    @FXML
    private TextField personName;
    @FXML
    private CheckBox normalizeText;
    @FXML
    private TextField personNIF;
    @FXML
    private TextField personNASS;
    @FXML
    private DatePicker personBirthDate;
    @FXML
    private TextField personCivilStatus;
    @FXML
    private TextField personNationality;
    @FXML
    private TextField personExtendedDirection;
    @FXML
    private TextField personPostalCode;
    @FXML
    private TextField personLocation;
    @FXML
    private TextField personMunicipality;
    @FXML
    private ChoiceBox<StudyDTO> personStudyLevel;

    private final Pattern letterPattern = Pattern.compile("[A-Za-zÑñÁÉÍÓÚáéíóú]");
    private final Pattern numberPattern = Pattern.compile("[0-9]");


    public PersonManagementData() {
        this.parent = ViewLoader.load(this, NEW_PERSON_DATA_FXML);
    }

    public void initialize(){
        personSurNames.setOnKeyReleased(this::onPersonSurNamesPatternChanged);
        personNIF.setOnKeyReleased(this::onPersonNIFVerifyOnlyLetterAndNumber);
        personNASS.setOnKeyReleased(this::onPersonNASSVerifyOnlyNumber);
        personCivilStatus.setOnKeyReleased(this::onPersonCivilStatusVerifyOnlyLetter);
        personPostalCode.setOnKeyReleased(this::onPersonPostalCodeVerifyOnlyNumber);
        personPostalCode.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(t1.length() > 5){
                    personPostalCode.setText(t);
                }
            }
        });

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);
        personBirthDate.setConverter(new LocalDateStringConverter(dateFormatter, null));
    }

    public ComboBox getPersonSurNames() {
        return personSurNames;
    }

    public TextField getPersonName() {
        return personName;
    }

    public CheckBox getNormalizeText() {
        return normalizeText;
    }

    public TextField getPersonNIF() {
        return personNIF;
    }

    public TextField getPersonNASS() {
        return personNASS;
    }

    public DatePicker getPersonBirthDate() {
        return personBirthDate;
    }

    public TextField getPersonCivilStatus() {return personCivilStatus;}

    public TextField getPersonNationality() {
        return personNationality;
    }

    public TextField getPersonExtendedDirection() {
        return personExtendedDirection;
    }

    public TextField getPersonPostalCode() {
        return personPostalCode;
    }

    public TextField getPersonLocation() {
        return personLocation;
    }

    public TextField getPersonMunicipality() {
        return personMunicipality;
    }

    public ChoiceBox<StudyDTO> getPersonStudyLevel() {
        return personStudyLevel;
    }

    public void loadPersonStudyLevel(List<StudyDTO> studyDTOList) {
        ObservableList<StudyDTO> studyDTOObservableList = FXCollections.observableArrayList(studyDTOList);
        personStudyLevel.setItems(studyDTOObservableList);
    }

    public void refreshPersonSurNames(ObservableList<PersonDTO> personDTOObservableList){
        personSurNames.setItems(personDTOObservableList);
        if(personDTOObservableList.size() > 0){
            personSurNames.show();
        }
    }

    private void onPersonSurNamesPatternChanged(KeyEvent keyEvent){
        String pattern = personSurNames.getEditor().getText();
        PersonSurNamesPatternChangedEvent personSurNamesPatternChangedEvent = new PersonSurNamesPatternChangedEvent(pattern);
        surNamesPatternChangedEventHandler.handle(personSurNamesPatternChangedEvent);
    }

    private void onPersonNIFVerifyOnlyLetterAndNumber(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.TAB){

            return;
        }

        if(!numberPattern.matcher(keyEvent.getText()).matches() &&
        !letterPattern.matcher(keyEvent.getText()).matches()){
            getPersonNIF().setText(getPersonNIF().getText().replace(keyEvent.getText(), ""));
        }
    }

    private void onPersonNASSVerifyOnlyNumber(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.TAB){

            return;
        }

        if(!numberPattern.matcher(keyEvent.getText()).matches()){
            getPersonNASS().setText(getPersonNASS().getText().replace(keyEvent.getText(), ""));
        }
    }

    private void onPersonCivilStatusVerifyOnlyLetter(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.TAB){

            return;
        }

        if(!letterPattern.matcher(keyEvent.getText()).matches()){
            getPersonCivilStatus().setText(getPersonCivilStatus().getText().replace(keyEvent.getText(), ""));
        }
    }

    private void onPersonPostalCodeVerifyOnlyNumber(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.TAB){

            return;
        }

        if(!numberPattern.matcher(keyEvent.getText()).matches()){
            getPersonPostalCode().setText(getPersonPostalCode().getText().replace(keyEvent.getText(), ""));
        }
    }
    public void setOnSurNamesPatternChanged(EventHandler<PersonSurNamesPatternChangedEvent> surNamesPatternChangedEventHandler){
        this.surNamesPatternChangedEventHandler = surNamesPatternChangedEventHandler;
    }
}
