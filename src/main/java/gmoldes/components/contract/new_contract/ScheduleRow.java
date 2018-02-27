package gmoldes.components.contract.new_contract;

import gmoldes.components.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ScheduleRow {

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy");
    private static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm");
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private String today = LocalDate.now().format(dateFormatter);
    private String hour = LocalDate.now().format(timeFormatter);

    private static final String SCHEDULE_ROW_FXML = "/fxml/new_contract/contract_schedule.fxml";

    private Parent parent;

    @FXML
    private TextField dayOfWeek;
    @FXML
    private TextField date;
    @FXML
    private TextField amFrom;
    @FXML
    private TextField amTo;
    @FXML
    private TextField pmFrom;
    @FXML
    private TextField pmTo;
    @FXML
    private TextField hoursOfDay;


    public ScheduleRow() {
        this.parent = ViewLoader.load(this, SCHEDULE_ROW_FXML);
    }

    @FXML
    public void initialize() {

    }
}
