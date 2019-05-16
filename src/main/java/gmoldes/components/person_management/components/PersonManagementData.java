package gmoldes.components.person_management.components;

import gmoldes.components.ViewLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class PersonManagementData extends VBox {

    private static final String NEW_PERSON_DATA_FXML = "/fxml/person_management/person_management_data.fxml";

    private Parent parent;






    public PersonManagementData() {

        this.parent = ViewLoader.load(this, NEW_PERSON_DATA_FXML);
    }
}
