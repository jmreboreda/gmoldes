package gmoldes.controllers;

import gmoldes.components.ViewLoader;
import gmoldes.components.timerecord.TimeRecordData;
import gmoldes.components.timerecord.TimeRecordHeader;
import gmoldes.domain.dto.ClientDTO;
import gmoldes.domain.dto.PersonDTO;
import gmoldes.manager.ClientManager;
import gmoldes.manager.PersonManager;
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
