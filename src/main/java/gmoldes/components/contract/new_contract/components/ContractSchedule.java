package gmoldes.components.contract.new_contract.components;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract.events.ChangeScheduleDurationEvent;
import gmoldes.components.contract.ContractConstants;
import gmoldes.components.generic_components.DaysOfWeekSelector;
import gmoldes.components.generic_components.TextInput;
import gmoldes.domain.contract.dto.ContractScheduleDayDTO;
import gmoldes.utilities.*;
import gmoldes.utilities.TableCell.DateCell;
import gmoldes.utilities.TableCell.DurationCell;
import gmoldes.utilities.TableCell.TimeCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.*;

public class ContractSchedule extends AnchorPane {

    private static final String SCHEDULE_FXML = "/fxml/new_contract/contract_schedule_table.fxml";
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
    private TableColumn<ContractScheduleDayDTO, DayOfWeek> dayOfWeek;
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

        contract_schedule_table.setId("contract_schedule_table");
        dayOfWeek.setText(ContractConstants.DAY_OF_WEEK_COLUMN_TITLE);
        date.setText(Parameters.DATE_LABEL_TEXT);
        amFrom.setText(ContractConstants.TIME_AM_FROM_COLUMN_TITLE);
        amTo.setText(ContractConstants.TIME_AM_TO_COLUMN_TITLE);
        pmFrom.setText(ContractConstants.TIME_PM_FROM_COLUMN_TITLE);
        pmTo.setText(ContractConstants.TIME_PM_TO_COLUMN_TITLE);
        totalDayHours.setText(ContractConstants.TIME_DURATION_WORK_DAY_COLUMN_TITLE);

        contract_schedule_table.setEditable(true);

        final ObservableList<DayOfWeek> daysOfWeek = FXCollections.observableArrayList();
        daysOfWeek.add(DayOfWeek.MONDAY);
        daysOfWeek.add(DayOfWeek.TUESDAY);
        daysOfWeek.add(DayOfWeek.WEDNESDAY);
        daysOfWeek.add(DayOfWeek.THURSDAY);
        daysOfWeek.add(DayOfWeek.FRIDAY);
        daysOfWeek.add(DayOfWeek.SATURDAY);
        daysOfWeek.add(DayOfWeek.SUNDAY);

        dayOfWeek.setCellFactory(param -> {
                    ComboBoxTableCell<ContractScheduleDayDTO, DayOfWeek> comboBoxTableCell = new ComboBoxTableCell<>(daysOfWeek);
                    comboBoxTableCell.setPickOnBounds(true);
                    comboBoxTableCell.updateSelected(true);
                    comboBoxTableCell.setConverter(new StringConverter<DayOfWeek>() {
                        @Override
                        public String toString(DayOfWeek object) {
                            if(object != null) {
                                return object.getDisplayName(TextStyle.FULL, Locale.getDefault());
                            }
                            return null;
                        }

                        @Override
                        public DayOfWeek fromString(String string) {
                            return null;
                        }
                    });

                    return comboBoxTableCell;
                });

        date.setCellFactory(param -> new DateCell());
        amFrom.setCellFactory(param -> new TimeCell());
        amTo.setCellFactory(param -> new TimeCell());
        pmFrom.setCellFactory(param -> new TimeCell());
        pmTo.setCellFactory(param -> new TimeCell());
        totalDayHours.setCellFactory(param -> new DurationCell());

        dayOfWeek.setCellValueFactory(new PropertyValueFactory<>("dayOfWeek"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        amFrom.setCellValueFactory(new PropertyValueFactory<>("amFrom"));
        amTo.setCellValueFactory(new PropertyValueFactory<>("amTo"));
        pmFrom.setCellValueFactory(new PropertyValueFactory<>("pmFrom"));
        pmTo.setCellValueFactory(new PropertyValueFactory<>("pmTo"));
        totalDayHours.setCellValueFactory(new PropertyValueFactory<>("totalDayHours"));
        hoursWorkWeek.setInputMinWidth(75D);

        date.getStyleClass().add("tableDateStyle");
        amFrom.getStyleClass().add("tableTimeStyle");
        amTo.getStyleClass().add("tableTimeStyle");
        pmFrom.getStyleClass().add("tableTimeStyle");
        pmTo.getStyleClass().add("tableTimeStyle");
        totalDayHours.getStyleClass().add("tableTotalDayHoursStyle");

        amFrom.setOnEditCommit(this::updateTotalWeekHours);
        amTo.setOnEditCommit(this::updateTotalWeekHours);
        pmFrom.setOnEditCommit(this::updateTotalWeekHours);
        pmTo.setOnEditCommit(this::updateTotalWeekHours);

        contract_schedule_table.setOnKeyPressed(this::verifyRequestWithSpecialKeyCode);

        List<ContractScheduleDayDTO> contractScheduleDayDTOList = new ArrayList<>();
        for(int i = 0; i <= ContractConstants.LAST_ROW_SCHEDULE_TABLE; i++){
            contractScheduleDayDTOList.add(
                    ContractScheduleDayDTO.create()
                            .withTotalDayHours(Duration.ZERO)
                            .build());
        }
        ObservableList<ContractScheduleDayDTO> data = FXCollections.observableArrayList(contractScheduleDayDTOList);
        refreshTable(data);
    }

    public TableColumn<ContractScheduleDayDTO, DayOfWeek> getDayOfWeek() {
        return dayOfWeek;
    }

    public TableColumn<ContractScheduleDayDTO, LocalDate> getDate() {
        return date;
    }

    public TableColumn<ContractScheduleDayDTO, LocalTime> getAmFrom() {
        return amFrom;
    }

    public TableColumn<ContractScheduleDayDTO, LocalTime> getAmTo() {
        return amTo;
    }

    public TableColumn<ContractScheduleDayDTO, LocalTime> getPmFrom() {
        return pmFrom;
    }

    public TableColumn<ContractScheduleDayDTO, LocalTime> getPmTo() {
        return pmTo;
    }

    private void updateTotalWeekHours(TableColumn.CellEditEvent event){
        int editedRow = event.getTablePosition().getRow();
        int editedColumn = event.getTablePosition().getColumn();

        ContractScheduleDayDTO selectedItemDay = contract_schedule_table.getItems().get(editedRow);

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
        if(selectedRow >= ContractConstants.FIRST_ROW_SCHEDULE_TABLE){
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
        if(selectedRow >= ContractConstants.FIRST_ROW_SCHEDULE_TABLE &&
                selectedRow <= ContractConstants.LAST_ROW_SCHEDULE_TABLE &&
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

        ContractScheduleDayDTO previousRowToTargetRow = null;

        Integer firstEmptyRow = findFirstEmptyRow();

        if(firstEmptyRow == null){

            return;
        }

        ContractScheduleDayDTO selectedItemRow = contract_schedule_table.getItems().get(selectedRow);
        ContractScheduleDayDTO firstEmptyRowTarget = contract_schedule_table.getItems().get(firstEmptyRow);
        if(firstEmptyRow > 0) {
            previousRowToTargetRow = contract_schedule_table.getItems().get(firstEmptyRow - 1);
        }
        else{
            previousRowToTargetRow = selectedItemRow;
        }

        if(previousRowToTargetRow != null) {
            if (!selectedItemRow.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                firstEmptyRowTarget.setDayOfWeek(previousRowToTargetRow.getDayOfWeek().plus(1));
            } else {
                firstEmptyRowTarget.setDayOfWeek(DayOfWeek.MONDAY);
            }
        }else{
            firstEmptyRowTarget.setDayOfWeek(selectedItemRow.getDayOfWeek().plus(1));
        }

        firstEmptyRowTarget.setAmFrom(selectedItemRow.getAmFrom());
        firstEmptyRowTarget.setAmTo(selectedItemRow.getAmTo());
        firstEmptyRowTarget.setPmFrom(selectedItemRow.getPmFrom());
        firstEmptyRowTarget.setPmTo(selectedItemRow.getPmTo());
        firstEmptyRowTarget.setTotalDayHours(selectedItemRow.getTotalDayHours());
        refreshTable(contract_schedule_table.getItems());
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

    public Set<DayOfWeek> getTableColumnDayOfWeekData(){

        Set<DayOfWeek> dayOfWeekSet = new HashSet<>();
        for(Integer i = ContractConstants.FIRST_ROW_SCHEDULE_TABLE; i<= ContractConstants.LAST_ROW_SCHEDULE_TABLE; i++) {
            if(dayOfWeek.getCellData(i) != null) {
                dayOfWeekSet.add(dayOfWeek.getCellData(i));
            }
        }

        return dayOfWeekSet;
    }

    public Set<WorkDaySchedule> retrieveScheduleWithScheduleDays(){
        ObservableList<ContractScheduleDayDTO> tableItemList = contract_schedule_table.getItems();
        Set<WorkDaySchedule> schedule = new HashSet<>();
        for(Integer i = ContractConstants.FIRST_ROW_SCHEDULE_TABLE; i <= ContractConstants.LAST_ROW_SCHEDULE_TABLE; i++){
            String dayOfWeek = "";
            LocalDate date = null;
            LocalTime amFrom = null;
            LocalTime amTo = null;
            LocalTime pmFrom = null;
            LocalTime pmTo = null;
            Duration durationHours = null;
            ContractScheduleDayDTO selectedItemRow = tableItemList.get(i);
            if (selectedItemRow.getDayOfWeek() != null) {
                dayOfWeek = selectedItemRow.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
            }
            if (selectedItemRow.getDate() != null) {
                date = selectedItemRow.getDate();
            }
            if (selectedItemRow.getAmFrom() != null) {
                amFrom = selectedItemRow.getAmFrom();
            }
            if (selectedItemRow.getAmTo() != null) {
                amTo = selectedItemRow.getAmTo();
            }
            if (selectedItemRow.getPmFrom() != null) {
                pmFrom = selectedItemRow.getPmFrom();
            }
            if (selectedItemRow.getPmTo() != null) {
                pmTo = selectedItemRow.getPmTo();
            }
            if (selectedItemRow.getTotalDayHours() != Duration.ZERO) {
                durationHours = selectedItemRow.getTotalDayHours();
            }

            WorkDaySchedule scheduleDay = WorkDaySchedule.create()
                    .withDayOfWeek(dayOfWeek)
                    .withDate(date)
                    .withAmFrom(amFrom)
                    .withAmTo(amTo)
                    .withPmFrom(pmFrom)
                    .withPmTo(pmTo)
                    .withDurationHours(durationHours)
                    .build();
            schedule.add(scheduleDay);
        }

        return schedule;
    }

    public void setOnChangeScheduleDuration(EventHandler<ChangeScheduleDurationEvent> handler){
       this.onChangeScheduleDurationEventHandler = handler;
    }
}
