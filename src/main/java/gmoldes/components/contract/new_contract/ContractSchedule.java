package gmoldes.components.contract.new_contract;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract.events.ChangeContractDataHoursWorkWeekEvent;
import gmoldes.components.contract.events.ChangeScheduleDurationEvent;
import gmoldes.components.generic_components.TextInput;
import gmoldes.domain.dto.ContractScheduleDayDTO;
import gmoldes.utilities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ContractSchedule extends AnchorPane {

    private static final String SCHEDULE_FXML = "/fxml/new_contract/contract_schedule_table.fxml";
    private static final Integer INITIAL_ROW_TABLE = 0;
    private static final Integer FINAL_ROW_TABLE = 6;
    private static final Integer DAY_OF_WEEK_COLUMN = 0;
    private static final Integer DATE_COLUMN = 1;
    private static final Integer AM_FROM_COLUMN = 2;
    private static final Integer AM_TO_COLUMN = 3;
    private static final Integer PM_FROM_COLUMN = 4;
    private static final Integer PM_TO_COLUMN = 5;
    private static final KeyCode REQUEST_DELETION_ALL_DATA = KeyCode.F8;
    private static final KeyCode REQUEST_FOR_DATA_DUPLICATION = KeyCode.F10;

    private EventHandler<ChangeScheduleDurationEvent> onChangeScheduleDurationEventHandler;

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
    private TableColumn<ContractScheduleDayDTO, Duration> totalDayHours;
    @FXML
    private TextInput hoursWorkWeek;


    public ContractSchedule() {
        this.parent = ViewLoader.load(this, SCHEDULE_FXML);
    }

    @FXML
    public void initialize() {

        dayOfWeek.setText(Parameters.DAY_OF_WEEK_TEXT);
        date.setText(Parameters.DATE_LABEL_TEXT);
        amFrom.setText(Parameters.TIME_AM_FROM_TEXT);
        amTo.setText(Parameters.TIME_AM_TO_TEXT);
        pmFrom.setText(Parameters.TIME_PM_FROM_TEXT);
        pmTo.setText(Parameters.TIME_PM_TO_TEXT);
        totalDayHours.setText(Parameters.TIME_DURATION_WORK_DAY_TEXT);

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

        Callback<TableColumn<ContractScheduleDayDTO, Duration>, TableCell<ContractScheduleDayDTO, Duration>> cellDurationFactory =
                p -> new EditingDurationCell();
        totalDayHours.setCellFactory(cellDurationFactory);

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
        hoursWorkWeek.setInputMinWidth(75D);
        hoursWorkWeek.setStyle("-fx-alignment: CENTER-RIGHT;");


        amFrom.setOnEditCommit(this::updateTableItemList);
        amTo.setOnEditCommit(this::updateTableItemList);
        pmFrom.setOnEditCommit(this::updateTableItemList);
        pmTo.setOnEditCommit(this::updateTableItemList);

        contract_schedule_table.setOnKeyPressed(this::verifyRequestWithSpecialKeyCode);

        contract_schedule_table.getStyleClass().clear();
        contract_schedule_table.getStyleClass().add("/css_stylesheet/modena");

        List<ContractScheduleDayDTO> contractScheduleDayDTOList = new ArrayList<>();
        for(int i = 0; i <= FINAL_ROW_TABLE; i++){
            contractScheduleDayDTOList.add(
                    ContractScheduleDayDTO.create()
                            .withTotalDayHours(Duration.ZERO)
                            .build());
        }
        ObservableList<ContractScheduleDayDTO> data = FXCollections.observableArrayList(contractScheduleDayDTOList);
        refreshTable(data);
    }

    private void updateTableItemList(TableColumn.CellEditEvent event){
        int editedRow = event.getTablePosition().getRow();
        int editedColumn = event.getTablePosition().getColumn();

        ContractScheduleDayDTO selectedItemDay = contract_schedule_table.getItems().get(editedRow);

        if(editedColumn == DAY_OF_WEEK_COLUMN){
            selectedItemDay.setDayOfWeek((String) event.getNewValue());
        }
        if(editedColumn == DATE_COLUMN){
            selectedItemDay.setDate((LocalDate) event.getNewValue());
        }
        if(editedColumn == AM_FROM_COLUMN){
            selectedItemDay.setAmFrom((LocalTime) event.getNewValue());
        }
        if(editedColumn == AM_TO_COLUMN){
            selectedItemDay.setAmTo((LocalTime) event.getNewValue());
        }
        if(editedColumn == PM_FROM_COLUMN){
            selectedItemDay.setPmFrom((LocalTime) event.getNewValue());
        }
        if(editedColumn == PM_TO_COLUMN){
            selectedItemDay.setPmTo((LocalTime) event.getNewValue());
        }
        Duration durationDay = retrieveDurationDay(selectedItemDay);
        selectedItemDay.setTotalDayHours(durationDay);

        refreshTable(contract_schedule_table.getItems());

        refreshTotalWeekHours();
    }

    private void refreshTable(ObservableList<ContractScheduleDayDTO> tableItemList){
        contract_schedule_table.setItems(tableItemList);
    }

    private Duration retrieveDurationDay(ContractScheduleDayDTO selectedItemRow){
        Duration durationAM = Duration.ZERO;
        Duration durationPM = Duration.ZERO;

        if(selectedItemRow.getAmFrom() != null && selectedItemRow.getAmTo() != null){
            durationAM = Duration.between(selectedItemRow.getAmFrom(), selectedItemRow.getAmTo());
        }
        if(selectedItemRow.getPmFrom() != null && selectedItemRow.getPmTo() != null){
            LocalTime pmFrom = selectedItemRow.getPmFrom();
            LocalTime pmTo =  selectedItemRow.getPmTo();
            if(pmTo == LocalTime.parse("00:00"))
            {
                durationPM = Duration.between(pmFrom, LocalTime.MIDNIGHT).plus(Duration.ofDays(1));
            }
            else {
                durationPM = Duration.between(pmFrom, pmTo);
            }

        }

        return durationAM.plus(durationPM);

    }

    private void verifyRequestWithSpecialKeyCode(KeyEvent event){
        if(event.getCode() != REQUEST_DELETION_ALL_DATA &&
                event.getCode() != REQUEST_FOR_DATA_DUPLICATION)
        {
            return;
        }

        if(event.getCode() == REQUEST_DELETION_ALL_DATA) {
            deleteAllDataForSelectedRow();
            refreshTotalWeekHours();
        }

        if(event.getCode() == REQUEST_FOR_DATA_DUPLICATION) {
            Integer selectedRow = contract_schedule_table.getSelectionModel().getSelectedIndex();
            if (verifyRowAndRowData(selectedRow)) {
                duplicateDataInFirstEmptyRow(selectedRow);
                refreshTotalWeekHours();
            }
        }
    }

    private void deleteAllDataForSelectedRow(){
        Integer selectedRow = contract_schedule_table.getSelectionModel().getSelectedIndex();
        if(selectedRow >= INITIAL_ROW_TABLE ){
            ContractScheduleDayDTO selectedItemRow = contract_schedule_table.getItems().get(selectedRow);

            selectedItemRow.setDayOfWeek(null);
            selectedItemRow.setDate(null);
            selectedItemRow.setAmFrom(null);
            selectedItemRow.setAmTo(null);
            selectedItemRow.setPmFrom(null);
            selectedItemRow.setPmTo(null);
            selectedItemRow.setTotalDayHours(Duration.ZERO);
            refreshTable(contract_schedule_table.getItems());
        }
    }

    private Boolean verifyRowAndRowData(Integer selectedRow){
        if(selectedRow >= INITIAL_ROW_TABLE &&
                selectedRow <= FINAL_ROW_TABLE &&
                verifySelectedRowContainsData(selectedRow)) {
            return true;
            }

            return false;
    }

    private Boolean verifySelectedRowContainsData(Integer selectedRow){
        ObservableList<ContractScheduleDayDTO> tableItemList = contract_schedule_table.getItems();
        if(!tableItemList.get(selectedRow).getTotalDayHours().equals(Duration.ZERO)){
            return true;
        }

        return false;
    }

    private void duplicateDataInFirstEmptyRow(Integer selectedRow){
        Integer firstEmptyRow = findFirstEmptyRow();
        if(firstEmptyRow != null) {
            ContractScheduleDayDTO selectedItemRow = contract_schedule_table.getItems().get(selectedRow);
            ContractScheduleDayDTO firstEmptyRowTarget = contract_schedule_table.getItems().get(firstEmptyRow);

            firstEmptyRowTarget.setAmFrom(selectedItemRow.getAmFrom());
            firstEmptyRowTarget.setAmTo(selectedItemRow.getAmTo());
            firstEmptyRowTarget.setPmFrom(selectedItemRow.getPmFrom());
            firstEmptyRowTarget.setPmTo(selectedItemRow.getPmTo());
            firstEmptyRowTarget.setTotalDayHours(selectedItemRow.getTotalDayHours());
            refreshTable(contract_schedule_table.getItems());
        }
    }

    private Integer findFirstEmptyRow(){
        ObservableList<ContractScheduleDayDTO> tableItemList = contract_schedule_table.getItems();
        for(ContractScheduleDayDTO contractScheduleDayDTO : tableItemList){
            if(contractScheduleDayDTO.getTotalDayHours().equals(Duration.ZERO)){
                return tableItemList.indexOf(contractScheduleDayDTO);
            }
        }

        return null;
    }

    private void refreshTotalWeekHours(){
        Duration totalHours = Duration.ZERO;
        for(ContractScheduleDayDTO contractScheduleDayDTO : contract_schedule_table.getItems()){
            totalHours = totalHours.plus(contractScheduleDayDTO.getTotalDayHours());
        }
        hoursWorkWeek.setText(Utilities.converterDurationToTimeString(totalHours));

        if(totalHours != Duration.ZERO) {
            final ChangeScheduleDurationEvent changeScheduleDurationEvent = new ChangeScheduleDurationEvent(totalHours);
            onChangeScheduleDurationEventHandler.handle(changeScheduleDurationEvent);
        }
    }

    public String getHoursWorkWeek(){
        return hoursWorkWeek.getText();
    }

    public void setOnChangeScheduleDuration(EventHandler<ChangeScheduleDurationEvent> handler){
       this.onChangeScheduleDurationEventHandler = handler;
    }
}
