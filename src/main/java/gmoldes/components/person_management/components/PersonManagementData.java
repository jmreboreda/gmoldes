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
    private TextField personExtendDirection;
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
}
