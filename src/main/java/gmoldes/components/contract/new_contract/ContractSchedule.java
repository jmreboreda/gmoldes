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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

public class ContractSchedule extends AnchorPane {

    private static final String SCHEDULE_FXML = "/fxml/new_contract/contract_schedule_table.fxml";
    private static final Float MINUTES_IN_HOUR = 60.0f;
    private static final Integer DAY_OF_WEEK_COLUMN = 0;
    private static final Integer DATE_COLUMN = 1;
    private static final Integer AM_FROM_COLUMN = 2;
    private static final Integer AM_TO_COLUMN = 3;
    private static final Integer PM_FROM_COLUMN = 4;
    private static final Integer PM_TO_COLUMN = 5;
    private static final Integer HOURS_COLUMN = 6;
    private static final KeyCode DUPLICATE_REQUEST = KeyCode.F10;

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
    private TableColumn<ContractScheduleDayDTO, Long> totalDayHours;

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

        amFrom.setOnEditCommit(this::updateTableItemList);
        amTo.setOnEditCommit(this::updateTableItemList);
        pmFrom.setOnEditCommit(this::updateTableItemList);
        pmTo.setOnEditCommit(this::updateTableItemList);

        contract_schedule_table.setOnKeyPressed(this::verifyDuplicateRequest);

        List<ContractScheduleDayDTO> contractScheduleDayDTOList = new ArrayList<>();
        for(int i = 0; i < 7; i++){
            contractScheduleDayDTOList.add(
                    new ContractScheduleDayDTO("", null, null, null, null, null, "0,00"));
        }
        ObservableList<ContractScheduleDayDTO> data = FXCollections.observableArrayList(contractScheduleDayDTOList);
        refreshTable(data);
    }

    private void updateTableItemList(TableColumn.CellEditEvent event){
        int editedRow = event.getTablePosition().getRow();
        int editedColumn = event.getTablePosition().getColumn();

        ContractScheduleDayDTO selectedItemRow = contract_schedule_table.getItems().get(editedRow);

        if(editedColumn == DAY_OF_WEEK_COLUMN){
            selectedItemRow.setDayOfWeek((String) event.getNewValue());
        }
        if(editedColumn == DATE_COLUMN){
            selectedItemRow.setDate((LocalDate) event.getNewValue());
        }
        if(editedColumn == AM_FROM_COLUMN){
            selectedItemRow.setAmFrom((LocalTime) event.getNewValue());
        }
        if(editedColumn == AM_TO_COLUMN){
            selectedItemRow.setAmTo((LocalTime) event.getNewValue());
        }
        if(editedColumn == PM_FROM_COLUMN){
            selectedItemRow.setPmFrom((LocalTime) event.getNewValue());
        }
        if(editedColumn == PM_TO_COLUMN){
            selectedItemRow.setPmTo((LocalTime) event.getNewValue());
        }
        String numberHours = retrieveFormattedHours(selectedItemRow);
        selectedItemRow.setTotalDayHours(numberHours);

        refreshTable(contract_schedule_table.getItems());
    }

    public void refreshTable(ObservableList<ContractScheduleDayDTO> tableItemList){
        contract_schedule_table.setItems(tableItemList);
    }

    public String retrieveFormattedHours(ContractScheduleDayDTO selectedItemRow){
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        Long numberMinutesAM = 0L;
        Long numberMinutesPM = 0L;

        if(selectedItemRow.getAmFrom() != null && selectedItemRow.getAmTo() != null){
            numberMinutesAM = MINUTES.between(selectedItemRow.getAmFrom(), selectedItemRow.getAmTo());
        }
        if(selectedItemRow.getPmFrom() != null && selectedItemRow.getPmTo() != null){
            numberMinutesPM = MINUTES.between(selectedItemRow.getPmFrom(), selectedItemRow.getPmTo());
        }

        Float numberHours = (numberMinutesAM.floatValue() + numberMinutesPM.floatValue())/MINUTES_IN_HOUR;

        return decimalFormat.format(numberHours);
    }

    private void verifyDuplicateRequest(KeyEvent event){
        ContractScheduleDayDTO selectedItem = contract_schedule_table.getSelectionModel().getSelectedItem();
        Integer selectedRow = contract_schedule_table.getSelectionModel().getSelectedIndex();

        if(event.getCode() == DUPLICATE_REQUEST && selectedRow >= 0){
            System.out.println("Pulsada F10: petición de duplicado. \n" +
            "Fila: " + selectedRow + "   Item: " + selectedItem);
        }
    }
}
