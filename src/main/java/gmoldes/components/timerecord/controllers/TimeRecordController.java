package gmoldes.components.timerecord.controllers;

import gmoldes.components.ViewLoader;
import gmoldes.components.timerecord.components.TimeRecordData;
import gmoldes.components.timerecord.components.TimeRecordHeader;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.client.manager.ClientManager;
import gmoldes.domain.person.manager.PersonManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.logging.Logger;

public class TimeRecordController extends VBox {


    private static final Logger logger = Logger.getLogger(TimeRecordController.class.getSimpleName());
    private static final String TIME_RECORD_MAIN_FXML = "/fxml/time_record/timerecord_main.fxml";

    private ClientManager clientManager = new ClientManager();
    private PersonManager personManager = new PersonManager();

    private Parent parent;

    @FXML
    private TimeRecordHeader timeRecordHeader;
    @FXML
    private TimeRecordData timeRecord;


    public TimeRecordController() {
        logger.info("Initilizing Main fxml");
        this.parent = ViewLoader.load(this, TIME_RECORD_MAIN_FXML);
    }

    private void onOkButton(MouseEvent event) {
        System.out.println(event.getSource() + " clicked!");
    }

    private void onExit(MouseEvent event) {
        Platform.exit();
    }


    private List<ClientDTO> findClientsByNamePattern(String pattern) {
        return clientManager.findAllActiveClientByNamePatternInAlphabeticalOrder(pattern);
    }

    private List<PersonDTO> findPersonsByNamePattern(String pattern) {
        return personManager.findAllPersonsByNamePatternInAlphabeticalOrder(pattern);
    }
}
