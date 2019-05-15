package gmoldes.components.new_person.components;

import gmoldes.components.ViewLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class NewPersonData extends VBox {

    private static final String NEW_PERSON_DATA_FXML = "/fxml/new_person/new_person_data.fxml";

    private Parent parent;






    public NewPersonData() {

        this.parent = ViewLoader.load(this, NEW_PERSON_DATA_FXML);
    }
}
