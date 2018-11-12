package gmoldes.components.timerecord.controllers;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract.manager.ContractManager;
import gmoldes.components.timerecord.components.TimeRecordData;
import gmoldes.components.timerecord.components.TimeRecordHeader;
import gmoldes.domain.contract.dto.ContractDTO;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.logging.Logger;

public class TimeRecordController extends VBox {


    private static final Logger logger = Logger.getLogger(TimeRecordController.class.getSimpleName());
    private static final String TIME_RECORD_MAIN_FXML = "/fxml/time_record/timerecord_main.fxml";

    private Parent parent;

    @FXML
    private TimeRecordHeader timeRecordHeader;
    @FXML
    private TimeRecordData timeRecord;


    public TimeRecordController() {
        logger.info("Initilizing Main fxml");
        this.parent = ViewLoader.load(this, TIME_RECORD_MAIN_FXML);
    }

    public List<ContractDTO> findAllContractsWithTimeRecordByClientIdInPeriod(Integer clientId, String yearMonth){

        ContractManager contractManager = new ContractManager();

        return contractManager.findAllContractsWithTimeRecordByClientIdInPeriod(clientId, yearMonth);
    }
}
