package gmoldes.components.new_person.controllers;

import gmoldes.components.ViewLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.util.logging.Logger;

public class NewPersonMainController extends VBox {

    private static final Logger logger = Logger.getLogger(NewPersonMainController.class.getSimpleName());
    private static final String NEW_PERSON_MAIN_FXML = "/fxml/new_person/new_person_main.fxml";

    private Parent parent;


    public NewPersonMainController() {
        logger.info("Initializing new person main fxml");
        this.parent = ViewLoader.load(this, NEW_PERSON_MAIN_FXML);
    }

}
