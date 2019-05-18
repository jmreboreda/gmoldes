package gmoldes.components.person_management.components;

import gmoldes.ApplicationConstants;
import gmoldes.components.ViewLoader;
import gmoldes.domain.person.controllers.PersonController;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.study.StudyController;
import gmoldes.domain.study.dto.StudyDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.converter.LocalDateStringConverter;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class PersonManagementData extends VBox {

    private static final String NEW_PERSON_DATA_FXML = "/fxml/person_management/person_management_data.fxml";

    private Parent parent;

    @FXML
    private TextField personSurName;
    @FXML
    private ComboBox <PersonDTO> personSurNames;
    @FXML
    private TextField personName;
    @FXML
    private TextField personNIF;
    @FXML
    private TextField personNASS;
    @FXML
    private DatePicker personBirthDate;
    @FXML
    private TextField civilStatus;
    @FXML
    private TextField Nationality;
    @FXML
    private TextField personExtendedDirection;
    @FXML
    private TextField personPostalCode;
    @FXML
    private TextField personLocation;
    @FXML
    private TextField personMunicipality;
    @FXML
    private ChoiceBox<StudyDTO> personStudy;

    public PersonManagementData() {
        this.parent = ViewLoader.load(this, NEW_PERSON_DATA_FXML);

        loadPerson();
        loadStudy();
    }

    public void initialize(){

        personSurNames.setOnKeyReleased(this::onSurNamesPatternChanged);

//        personSurNames.getSelectionModel().selectedItemProperty()
//                .addListener((observable, oldValue, newEmployerSelected) -> onSelectEmployer(newEmployerSelected));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);
        personBirthDate.setConverter(new LocalDateStringConverter(dateFormatter, null));
    }

    public TextField getPersonSurName() {
        return personSurName;
    }

    public void setPersonSurName(TextField personSurName) {
        this.personSurName = personSurName;
    }

    public ComboBox getPersonSurNames() {
        return personSurNames;
    }

    public void setPersonSurNames(ComboBox personSurNames) {
        this.personSurNames = personSurNames;
    }

    public TextField getPersonName() {
        return personName;
    }

    public void setPersonName(TextField personName) {
        this.personName = personName;
    }

    public TextField getPersonNIF() {
        return personNIF;
    }

    public void setPersonNIF(TextField personNIF) {
        this.personNIF = personNIF;
    }

    public TextField getPersonNASS() {
        return personNASS;
    }

    public void setPersonNASS(TextField personNASS) {
        this.personNASS = personNASS;
    }

    public DatePicker getPersonBirthDate() {
        return personBirthDate;
    }

    public void setPersonBirthDate(DatePicker personBirthDate) {
        this.personBirthDate = personBirthDate;
    }

    public TextField getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(TextField civilStatus) {
        this.civilStatus = civilStatus;
    }

    public TextField getNationality() {
        return Nationality;
    }

    public void setNationality(TextField nationality) {
        Nationality = nationality;
    }

    public TextField getPersonExtendedDirection() {
        return personExtendedDirection;
    }

    public void setPersonExtendDirection(TextField personExtendDirection) {
        this.personExtendedDirection = personExtendDirection;
    }

    public TextField getPersonPostalCode() {
        return personPostalCode;
    }

    public void setPersonPostalCode(TextField personPostalCode) {
        this.personPostalCode = personPostalCode;
    }

    public TextField getPersonLocation() {
        return personLocation;
    }

    public void setPersonLocation(TextField personLocation) {
        this.personLocation = personLocation;
    }

    public TextField getPersonMunicipality() {
        return personMunicipality;
    }

    public void setPersonMunicipality(TextField personMunicipality) {
        this.personMunicipality = personMunicipality;
    }

    public ChoiceBox getPersonStudy() {
        return personStudy;
    }

    public void setPersonStudy(ChoiceBox personStudy) {
        this.personStudy = personStudy;
    }

    private void loadPerson(){
        PersonController personController = new PersonController();

        List<PersonDTO> personDTOList = personController.findAllPersonInAlphabeticalOrder();
        ObservableList<PersonDTO> personDTOObservableList = FXCollections.observableList(personDTOList);
        personSurNames.setItems(personDTOObservableList);
    }

    private void loadStudy(){
        StudyController studyController = new StudyController();

        List<StudyDTO> studyDTOList = studyController.findAllStudy();
        ObservableList<StudyDTO> studyDTOObservableList = FXCollections.observableArrayList(studyDTOList);
        personStudy.setItems(studyDTOObservableList);
    }

    private void onSurNamesPatternChanged(KeyEvent keyEvent){
        String personSurNamesPattern = personSurNames.getEditor().getText();
        PersonController personController = new PersonController();

        List<PersonDTO> personDTOList = personController.findAllPersonsByNamePatternInAlphabeticalOrder(personSurNamesPattern);
        ObservableList<PersonDTO> personDTOObservableList = FXCollections.observableList(personDTOList);
        personSurNames.setItems(personDTOObservableList);
        if(personDTOObservableList.size() > 0){
            personSurNames.show();
        }
    }
}
