package gmoldes.components.new_person.components;

import gmoldes.components.ViewLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class NewPersonHeader extends VBox {

    private static final String NEW_PERSON_HEADER_FXML = "/fxml/new_person/new_person_header.fxml";

    private Parent parent;

    public NewPersonHeader() {
        this.parent = ViewLoader.load(this, NEW_PERSON_HEADER_FXML);
    }
}
