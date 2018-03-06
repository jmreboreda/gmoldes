package gmoldes.components.contract.new_contract;

import gmoldes.components.ViewLoader;
import gmoldes.domain.dto.ContractScheduleDayDTO;
import gmoldes.utilities.EditingDateCell;
import gmoldes.utilities.EditingStringCell;
import gmoldes.utilities.EditingTimeCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContractSchedule extends AnchorPane {

    private static final String SCHEDULE_FXML = "/fxml/new_contract/contract_schedule_table.fxml";

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private Parent parent;

    @FXML
    private TableView <ContractScheduleDayDTO> contract_schedule_table;
    @FXML
    private TableColumn<ContractScheduleDayDTO, String> dayOfWeek;
    @FXML
    private TableColumn<ContractScheduleDayDTO, LocalDate> date;
    @FXML
    private TableColumn<ContractScheduleDayDTO, LocalTime> amFrom;
    @FXML
    private TableColumn<ContractScheduleDayDTO, LocalTime> amTo;
    @FXML
    private TableColumn<ContractScheduleDayDTO, LocalTime> pmFrom;
    @FXML
    private TableColumn<ContractScheduleDayDTO, LocalTime> pmTo;
    @FXML
    private TableColumn<ContractScheduleDayDTO, LocalTime> totalDayHours;

    public ContractSchedule() {
        this.parent = ViewLoader.load(this, SCHEDULE_FXML);
    }

    @FXML
    public void initialize() {

        contract_schedule_table.setEditable(true);

        Callback<TableColumn<ContractScheduleDayDTO, String>, TableCell<ContractScheduleDayDTO, String>> cellStringFactory =
                p -> new EditingStringCell();
        dayOfWeek.setCellFactory(cellStringFactory);

        Callback<TableColumn<ContractScheduleDayDTO, LocalDate>, TableCell<ContractScheduleDayDTO, LocalDate>> cellDateFactory =
                p -> new EditingDateCell();
        date.setCellFactory(cellDateFactory);

        Callback<TableColumn<ContractScheduleDayDTO, LocalTime>, TableCell<ContractScheduleDayDTO, LocalTime>> cellTimeFactory =
                p -> new EditingTimeCell();
        amFrom.setCellFactory(cellTimeFactory);
        amTo.setCellFactory(cellTimeFactory);
        pmFrom.setCellFactory(cellTimeFactory);
        pmTo.setCellFactory(cellTimeFactory);

        dayOfWeek.setCellValueFactory(new PropertyValueFactory<>("dayOfWeek"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        amFrom.setCellValueFactory(new PropertyValueFactory<>("amFrom"));
        amTo.setCellValueFactory(new PropertyValueFactory<>("amTo"));
        pmFrom.setCellValueFactory(new PropertyValueFactory<>("pmFrom"));
        pmTo.setCellValueFactory(new PropertyValueFactory<>("pmTo"));
        totalDayHours.setCellValueFactory(new PropertyValueFactory<>("totalDayHours"));
        date.setStyle("-fx-alignment: CENTER;");
        amFrom.setStyle("-fx-alignment: CENTER;");
        amTo.setStyle("-fx-alignment: CENTER;");
        pmFrom.setStyle("-fx-alignment: CENTER;");
        pmTo.setStyle("-fx-alignment: CENTER;");
        totalDayHours.setStyle("-fx-alignment: CENTER-RIGHT;");

        List<ContractScheduleDayDTO> contractScheduleDayDTOList = new ArrayList<>();
        for(int i = 0; i < 11; i++){
            contractScheduleDayDTOList.add(
                    new ContractScheduleDayDTO("", null, null, null, null, null, LocalTime.MIN));
        }
        ObservableList<ContractScheduleDayDTO> data = FXCollections.observableArrayList(contractScheduleDayDTOList);
        contract_schedule_table.setItems(data);


    }
}
