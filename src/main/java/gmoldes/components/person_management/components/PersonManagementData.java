package gmoldes.components.person_management.components;

import gmoldes.components.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PersonManagementData extends VBox {

    private static final String NEW_PERSON_DATA_FXML = "/fxml/person_management/person_management_data.fxml";

    private Parent parent;

    @FXML
    private TextField personSurName;
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
    private ChoiceBox personStudy;

    public PersonManagementData() {

        this.parent = ViewLoader.load(this, NEW_PERSON_DATA_FXML);
    }

    public TextField getPersonSurName() {
        return personSurName;
    }

    public void setPersonSurName(TextField personSurName) {
        this.personSurName = personSurName;
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
}
