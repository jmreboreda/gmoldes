package gmoldes.components.contract.new_contract;

import gmoldes.components.ViewLoader;
import gmoldes.utilities.Parameters;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ScheduleRow {

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(Parameters.DEFAULT_DATE_FORMAT);
    private static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm");
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parameters.DEFAULT_DATE_FORMAT);
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private String today = LocalDate.now().format(dateFormatter);
    private String hour = LocalDate.now().format(timeFormatter);

    private static final String SCHEDULE_ROW_FXML = "/fxml/new_contract/contract_schedule_table.fxml";

    private Parent parent;

    @FXML
    private DayOfWeek dayOfWeek;
    @FXML
    private LocalDate date;
    @FXML
    private LocalTime amFrom;
    @FXML
    private LocalTime amTo;
    @FXML
    private LocalTime pmFrom;
    @FXML
    private LocalTime pmTo;
    @FXML
    private Duration hoursOfDay;


    public ScheduleRow() {
        this.parent = ViewLoader.load(this, SCHEDULE_ROW_FXML);
    }

    @FXML
    public void initialize() {

    }


}
