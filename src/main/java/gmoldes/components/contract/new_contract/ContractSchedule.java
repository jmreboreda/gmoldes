package gmoldes.components.contract.new_contract;

import gmoldes.components.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ContractSchedule extends AnchorPane {

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy");
    private static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm");
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private String date = LocalDate.now().format(dateFormatter);

    private static final String SCHEDULE_FXML = "/fxml/new_contract/contract_schedule.fxml";

    private Parent parent;

    @FXML
    private ContractSchedule contractSchedule;
    @FXML
    private GridPane scheduleBoard;


    public ContractSchedule() {
        this.parent = ViewLoader.load(this, SCHEDULE_FXML);
    }
}
