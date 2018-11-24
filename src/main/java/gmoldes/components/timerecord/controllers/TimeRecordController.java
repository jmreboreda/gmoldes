package gmoldes.components.timerecord.controllers;

import com.lowagie.text.DocumentException;
import gmoldes.components.ViewLoader;
import gmoldes.components.contract.manager.ContractManager;
import gmoldes.components.contract.new_contract.components.ContractConstants;
import gmoldes.components.timerecord.components.TimeRecordConstants;
import gmoldes.components.timerecord.components.TimeRecordData;
import gmoldes.components.timerecord.components.TimeRecordHeader;
import gmoldes.components.timerecord.forms.TimeRecord;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.timerecord.dto.TimeRecordCandidateDataDTO;
import gmoldes.domain.timerecord.service.TimeRecordPDFCreator;
import gmoldes.utilities.Message;
import gmoldes.utilities.Parameters;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class TimeRecordController extends VBox {


    private static final Logger logger = Logger.getLogger(TimeRecordController.class.getSimpleName());
    private static final String TIME_RECORD_MAIN_FXML = "/fxml/time_record/timerecord_main.fxml";

    private Parent parent;

    private ContractManager contractManager = new ContractManager();

    @FXML
    private TimeRecordHeader timeRecordHeader;
    @FXML
    private TimeRecordData timeRecord;


    public TimeRecordController() {
        logger.info("Initilizing Main fxml");
        this.parent = ViewLoader.load(this, TIME_RECORD_MAIN_FXML);
    }

    @FXML
    public void initialize(){

    }

    public List<ContractNewVersionDTO> findAllContractNewVersionByClientIdInMonthOfDate(Integer clientId, LocalDate date){

        return contractManager.findAllContractNewVersionByClientIdInMonthOfDate(clientId, date);
    }

}
