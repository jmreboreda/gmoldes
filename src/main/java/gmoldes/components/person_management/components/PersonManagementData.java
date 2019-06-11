package gmoldes.components.person_management.components;

import gmoldes.ApplicationConstants;
import gmoldes.components.ViewLoader;
import gmoldes.components.person_management.PersonManagementConstants;
import gmoldes.components.person_management.events.PersonSurNamesItemSelectedEvent;
import gmoldes.components.person_management.events.PersonSurNamesPatternChangedEvent;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.study.dto.StudyDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

public class PersonManagementData extends VBox {

    private static final String NEW_PERSON_DATA_FXML = "/fxml/person_management/person_management_data.fxml";
    private static final String NOT_AVAILABLE_TEXT = "No disponible";

    private Parent parent;

    private EventHandler<PersonSurNamesPatternChangedEvent> surNamesPatternChangedEventHandler;
    private EventHandler<PersonSurNamesItemSelectedEvent> surNamesItemSelectedEventHandler;

    public enum CivilStatus {
        No_disponible("No disponible"),
        Soltero("Soltero"),
        Soltera("Soltera"),
        Casado("Casado"),
        Casada("Casada"),
        Separado_judicialmente("Separado judicialmente"),
        Separada_judicialmente("Separada judicialmente"),
        Divorciado("Divorciado"),
        Divorciada("Divorciada"),
        Viudo("Viudo"),
        Viuda("Viuda"),
        Pareja_de_hecho("Pareja de hecho");

        private final String value;

        CivilStatus(String value) {
            this.value = value.replace(" ","_");
        }

        public String getValue() {
            return value.replace(" ", "_");
        }

        public String toString(){
            return value.replace("_"," ");
        }
    }

    @FXML
    private Group newPersonGroup;
    @FXML
    private HBox newPersonHbox;
    @FXML
    private ComboBox <PersonDTO> personSurNames;
    @FXML
    private Label personNameLabel;
    @FXML
    private TextField personName;
    @FXML
    private CheckBox normalizeText;
    @FXML
    private Group modificationPersonGroup;
    @FXML
    private HBox modificationPersonHbox;
    @FXML
    private TextField personNewSurNames;
    @FXML
    private TextField personNewName;
    @FXML
    private TextField personNIF;
    @FXML
    private TextField personNASS;
    @FXML
    private DatePicker personBirthDate;
    @FXML
    private ChoiceBox<CivilStatus> personCivilStatus;
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

    private final Pattern letterPattern = Pattern.compile("[A-Za-zÑñÁÉÍÓÚáéíóú ]");
    private final Pattern numberPattern = Pattern.compile("[0-9]");

    public PersonManagementData() {
        this.parent = ViewLoader.load(this, NEW_PERSON_DATA_FXML);
    }

    public void initialize(){
        personSurNames.setOnKeyReleased(this::onPersonSurNamesPatternChanged);

        personSurNames.setConverter(new StringConverter<PersonDTO>() {
        PersonDTO copyPersonDTO;

            @Override
            public String toString(PersonDTO personDTO) {
                if (personDTO == null){
                    return null;
                }
                copyPersonDTO = personDTO;
                return personDTO.toString();
            }

            @Override
            public PersonDTO fromString(String string) {
                if(!string.equals(copyPersonDTO.getApellidos().concat(", ").concat(copyPersonDTO.getNom_rzsoc()))){

                    return PersonDTO.create()
                            .withApellidos(string)
                            .withNom_rzsoc("")
                            .build();
                }

                return copyPersonDTO;
            }
        });

        personSurNames.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, personDTOItemSelected) -> onPersonSurNamesItemSelected(personDTOItemSelected));

        normalizeText.setTooltip(new Tooltip(PersonManagementConstants.TOOLTIP_NORMALIZE_TEXT));

        personNIF.setOnKeyReleased(this::onPersonNIFVerifyOnlyLetterAndNumber);
        personNASS.setOnKeyReleased(this::onPersonNASSVerifyOnlyNumber);
        personNASS.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(t1 == null){

                    return;
                }

                if(t1.length() > 12){
                    personNASS.setText(t);
                }
            }
        });

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

    public Group getNewPersonGroup() {
        return newPersonGroup;
    }

    public HBox getNewPersonHbox() {
        return newPersonHbox;
    }

    public ComboBox<PersonDTO> getPersonSurNames() {
        return personSurNames;
    }

    public Label getPersonNameLabel() {
        return personNameLabel;
    }

    public TextField getPersonName() {
        return personName;
    }

    public CheckBox getNormalizeText() {
        return normalizeText;
    }

    public Group getModificationPersonGroup() {
        return modificationPersonGroup;
    }

    public HBox getModificationPersonHbox() {
        return modificationPersonHbox;
    }

    public TextField getPersonNewSurNames() {
        return personNewSurNames;
    }

    public TextField getPersonNewName() {
        return personNewName;
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

    public ChoiceBox<CivilStatus> getPersonCivilStatus() {return personCivilStatus;}

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

    public void loadPersonCivilStatus(){
        ObservableList<CivilStatus> civilStatusOL = FXCollections.observableArrayList(CivilStatus.values());
        personCivilStatus.setItems(civilStatusOL);
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

    public void completePersonData(PersonDTO personDTO, StudyDTO studyDTO){

        LocalDate birthDate = personDTO.getFechanacim() != null ? personDTO.getFechanacim() : LocalDate.of(9999, 12, 31);
        String civilState = personDTO.getEstciv() != null ? personDTO.getEstciv().replace(" ", "_") : NOT_AVAILABLE_TEXT.replace(" ", "_");
        String nationality = personDTO.getNacionalidad() != null ? personDTO.getNacionalidad() : NOT_AVAILABLE_TEXT;
        String address = personDTO.getDireccion() != null ? personDTO.getDireccion() : NOT_AVAILABLE_TEXT;
        BigDecimal postalCode = personDTO.getCodpostal() != null ? personDTO.getCodpostal() : new BigDecimal(99999);
        String location = personDTO.getLocalidad() != null ? personDTO.getLocalidad() : NOT_AVAILABLE_TEXT;

        personName.setText("$");
        personNewSurNames.setText(personDTO.getApellidos());
        personNewName.setText(personDTO.getNom_rzsoc());
        personNIF.setText(personDTO.getNifcif());
        personNASS.setText(personDTO.getNumafss());
        personBirthDate.setValue(birthDate);
        personCivilStatus.getSelectionModel().select(CivilStatus.valueOf(civilState));
        personNationality.setText(nationality);
        personExtendedDirection.setText(address);
        personPostalCode.setText(postalCode.toString());
        personMunicipality.setText(location);
        for (StudyDTO studyDTOItem : personStudyLevel.getItems()) {
            if (studyDTO.getStudyDescription().equals(studyDTOItem.getStudyDescription())) {
                personStudyLevel.getSelectionModel().select(studyDTOItem);
                break ;
            }else{
                personStudyLevel.getSelectionModel().select(0);
            }
        }
    }

    private void onPersonSurNamesPatternChanged(KeyEvent keyEvent){
        String pattern = personSurNames.getEditor().getText();

        PersonSurNamesPatternChangedEvent personSurNamesPatternChangedEvent = new PersonSurNamesPatternChangedEvent(pattern);
        surNamesPatternChangedEventHandler.handle(personSurNamesPatternChangedEvent);
    }

    private void onPersonSurNamesItemSelected(PersonDTO personDTOItemSelected){
        PersonSurNamesItemSelectedEvent personSurNamesItemSelectedEvent = new PersonSurNamesItemSelectedEvent(personDTOItemSelected);
        surNamesItemSelectedEventHandler.handle(personSurNamesItemSelectedEvent);
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

    public void setOnSurNamesItemSelected(EventHandler<PersonSurNamesItemSelectedEvent> surNamesItemSelectedEventHandler){
        this.surNamesItemSelectedEventHandler = surNamesItemSelectedEventHandler;
    }
}
