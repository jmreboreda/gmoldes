package gmoldes.components.person_management.components;

import gmoldes.ApplicationConstants;
import gmoldes.components.ViewLoader;
import gmoldes.components.person_management.events.PersonSurNamesPatternChangedEvent;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.study.StudyController;
import gmoldes.domain.study.dto.StudyDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
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

    private EventHandler<PersonSurNamesPatternChangedEvent> surNamesPatternChangedEventHandler;

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

        loadStudy();
    }

    public void initialize(){
        personSurNames.setOnKeyReleased(this::onSurNamesPatternChanged);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);
        personBirthDate.setConverter(new LocalDateStringConverter(dateFormatter, null));
    }

    public TextField getPersonSurName() {
        return personSurName;
    }

    public ComboBox getPersonSurNames() {
        return personSurNames;
    }

    public TextField getPersonName() {
        return personName;
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

    public TextField getCivilStatus() {
        return civilStatus;
    }

    public TextField getNationality() {
        return Nationality;
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

    public ChoiceBox getPersonStudy() {
        return personStudy;
    }

    private void loadStudy(){
        StudyController studyController = new StudyController();

        List<StudyDTO> studyDTOList = studyController.findAllStudy();
        ObservableList<StudyDTO> studyDTOObservableList = FXCollections.observableArrayList(studyDTOList);
        personStudy.setItems(studyDTOObservableList);
    }

    public void refreshSurNames(ObservableList<PersonDTO> personDTOObservableList){
        personSurNames.setItems(personDTOObservableList);
        if(personDTOObservableList.size() > 0){
            personSurNames.show();
        }
    }

    private void onSurNamesPatternChanged(KeyEvent keyEvent){
        String pattern = personSurNames.getEditor().getText();
        PersonSurNamesPatternChangedEvent personSurNamesPatternChangedEvent = new PersonSurNamesPatternChangedEvent(pattern);
        surNamesPatternChangedEventHandler.handle(personSurNamesPatternChangedEvent);
    }

    public void setOnSurNamesPatternChanged(EventHandler<PersonSurNamesPatternChangedEvent> surNamesPatternChangedEventHandler){
        this.surNamesPatternChangedEventHandler = surNamesPatternChangedEventHandler;
    }
}
