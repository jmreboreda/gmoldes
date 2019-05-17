package gmoldes.components.person_management.components;

import gmoldes.components.ViewLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class PersonManagementHeader extends VBox {

    private static final String NEW_PERSON_HEADER_FXML = "/fxml/person_management/person_management_header.fxml";

    private Parent parent;

    public PersonManagementHeader() {
        this.parent = ViewLoader.load(this, NEW_PERSON_HEADER_FXML);
    }
}
