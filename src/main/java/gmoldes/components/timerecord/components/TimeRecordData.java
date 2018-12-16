package gmoldes.components.timerecord.components;

import com.lowagie.text.DocumentException;
import gmoldes.components.ViewLoader;
import gmoldes.components.contract.controllers.ContractTypeController;
import gmoldes.components.contract.new_contract.components.ContractConstants;
import gmoldes.components.timerecord.controllers.TimeRecordController;
import gmoldes.components.timerecord.forms.TimeRecord;
import gmoldes.domain.client.controllers.ClientController;
import gmoldes.domain.client.dto.ClientDTOOk;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract.dto.ContractTypeDTO;
import gmoldes.domain.person.controllers.PersonController;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.timerecord.dto.TimeRecordCandidateDataDTO;
import gmoldes.domain.timerecord.dto.TimeRecordClientDTO;
import gmoldes.domain.timerecord.service.TimeRecordPDFCreator;
import gmoldes.utilities.Message;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.Utilities;
import javafx.beans.binding.BooleanExpression;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.text.Collator;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class TimeRecordData extends VBox {

    private static final String TIME_RECORD_FXML = "/fxml/time_record/timerecord_data.fxml";
    private static final Integer FIRST_MONTH_INDEX_IN_MONTHNAME = 0;
    private static final Integer LAST_MONTH_INDEX_IN_MONTHNAME = 11;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parameters.DEFAULT_DATE_FORMAT);

    private Parent parent;

    @FXML
    private ChoiceBox<TimeRecord> monthName;
    @FXML
    private TextField yearNumber;
    @FXML
    private ChoiceBox<TimeRecordClientDTO> clientForTimeRecord;
    @FXML
    private TableColumn<TimeRecordCandidateDataDTO, String> employeeFullName;
    @FXML
    private TableColumn<TimeRecordCandidateDataDTO, String> workDayType;
    @FXML
    private TableColumn<TimeRecordCandidateDataDTO, String> hoursByWeek;
    @FXML
    private TableColumn<TimeRecordCandidateDataDTO, String> contractType;
    @FXML
    private TableColumn<TimeRecordCandidateDataDTO, String> dateFrom;
    @FXML
    private TableColumn<TimeRecordCandidateDataDTO, String> dateTo;
    @FXML
    private TableView<TimeRecordCandidateDataDTO> dataByTimeRecord;
    @FXML
    private Button createPDFButton;
    @FXML
    private Button printButton;
    @FXML
    private Button exitButton;

    public TimeRecordData() {
        this.parent = ViewLoader.load(this, TIME_RECORD_FXML);
    }

    @FXML
    public void initialize() {

        clientForTimeRecord.setOnAction(this::onChangeEmployer);
        yearNumber.setOnAction(this::loadClientForTimeRecord);
        monthName.setOnAction(this::loadClientForTimeRecord);
        createPDFButton.disableProperty().bind(BooleanExpression.booleanExpression(this.dataByTimeRecord.getSelectionModel().selectedItemProperty().isNull()));
        createPDFButton.setOnMouseClicked(this::onCreateTimeRecordPDF);
        printButton.disableProperty().bind(BooleanExpression.booleanExpression(this.dataByTimeRecord.getSelectionModel().selectedItemProperty().isNull()));
        printButton.setOnMouseClicked(this::onPrintTimeRecord);
        exitButton.setOnMouseClicked(this::onExit);

        monthName.setItems(FXCollections.observableArrayList(
                TimeRecord.create().withNameOfMonth(Month.JANUARY).build(),
                TimeRecord.create().withNameOfMonth(Month.FEBRUARY).build(),
                TimeRecord.create().withNameOfMonth(Month.MARCH).build(),
                TimeRecord.create().withNameOfMonth(Month.APRIL).build(),
                TimeRecord.create().withNameOfMonth(Month.MAY).build(),
                TimeRecord.create().withNameOfMonth(Month.JUNE).build(),
                TimeRecord.create().withNameOfMonth(Month.JULY).build(),
                TimeRecord.create().withNameOfMonth(Month.AUGUST).build(),
                TimeRecord.create().withNameOfMonth(Month.SEPTEMBER).build(),
                TimeRecord.create().withNameOfMonth(Month.OCTOBER).build(),
                TimeRecord.create().withNameOfMonth(Month.NOVEMBER).build(),
                TimeRecord.create().withNameOfMonth(Month.DECEMBER).build())
        );

        LocalDate localDate = LocalDate.now();
        if(localDate.getMonthValue() > LAST_MONTH_INDEX_IN_MONTHNAME){
            monthName.getSelectionModel().select(FIRST_MONTH_INDEX_IN_MONTHNAME);
        }else {
            monthName.getSelectionModel().select(localDate.getMonthValue());
        }
        if(localDate.getMonthValue() > LAST_MONTH_INDEX_IN_MONTHNAME){
            yearNumber.setText(String.valueOf(localDate.getYear() + 1));
        }else {
            yearNumber.setText(String.valueOf(localDate.getYear()));
        }

        employeeFullName.setCellValueFactory(new PropertyValueFactory<TimeRecordCandidateDataDTO,String>("employeeFullName"));
        workDayType.setCellValueFactory(new PropertyValueFactory<TimeRecordCandidateDataDTO,String>("workDayType"));
        hoursByWeek.setCellValueFactory(new PropertyValueFactory<TimeRecordCandidateDataDTO,String>("hoursByWeek"));
        contractType.setCellValueFactory(new PropertyValueFactory<TimeRecordCandidateDataDTO,String>("contractType"));
        dateFrom.setCellValueFactory(new PropertyValueFactory<TimeRecordCandidateDataDTO,String>("dateFrom"));
        dateTo.setCellValueFactory(new PropertyValueFactory<TimeRecordCandidateDataDTO,String>("dateTo"));
        hoursByWeek.setStyle("-fx-alignment: CENTER;");
        dateFrom.setStyle("-fx-alignment: CENTER;");
        dateTo.setStyle("-fx-alignment: CENTER;");

        loadClientForTimeRecord(new ActionEvent());
    }

    private void onCreateTimeRecordPDF(MouseEvent event) {
        TimeRecord timeRecord = createTimeRecord();
        Path pathToTimeRecordPDF = null;
        try {
            pathToTimeRecordPDF = TimeRecordPDFCreator.createTimeRecordPDF(timeRecord);
            if(pathToTimeRecordPDF == null){
                Message.errorMessage(this.createPDFButton.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, TimeRecordConstants.TIME_RECORD_PDF_NOT_CREATED);
                return;
            }

        } catch (IOException | DocumentException e) {
            Message.errorMessage(this.createPDFButton.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, TimeRecordConstants.TIME_RECORD_PDF_NOT_CREATED);
            e.printStackTrace();
        }
        Message.warningMessage(createPDFButton.getScene().getWindow(),"Información del sistema", "Registro horario creado en:" + "\n" + pathToTimeRecordPDF + "\n");
    }

    private void onPrintTimeRecord(MouseEvent event){
        TimeRecord timeRecord = createTimeRecord();
        Path pathToTimeRecordPDF = null;
        try {
            pathToTimeRecordPDF = TimeRecordPDFCreator.createTimeRecordPDF(timeRecord);
            if(pathToTimeRecordPDF == null){
                Message.errorMessage(this.createPDFButton.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, TimeRecordConstants.TIME_RECORD_PDF_NOT_CREATED);
                return;
            }
        } catch (IOException | DocumentException e) {
            Message.errorMessage(this.createPDFButton.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, TimeRecordConstants.TIME_RECORD_PDF_NOT_CREATED);
            e.printStackTrace();
        }

        String resultPrint = TimeRecordPDFCreator.printTimeRecord(pathToTimeRecordPDF);
        if(resultPrint.equals("ok")) {
            Message.warningMessage(printButton.getScene().getWindow(), "Sistema", "Registro horario enviado a la impresora." + "\n");
        }else{
            Message.warningMessage(printButton.getScene().getWindow(), "Sistema", "No existe impresora para imprimir el registro horario" +
                    " con los atributos indicados." + "\n");
        }
    }

    private void onExit(MouseEvent event){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    private TimeRecord createTimeRecord(){
        TimeRecordCandidateDataDTO data = dataByTimeRecord.getSelectionModel().getSelectedItem();

        Month timeRecordMonth = this.monthName.getSelectionModel().getSelectedItem().getNameOfMonth();
        String yearMonthReceiptCopyText = "";
        if(this.monthName.getSelectionModel().getSelectedItem().getNameOfMonth() == Month.DECEMBER){
            Integer nextYear = Integer.parseInt(this.yearNumber.getText()) + 1;
            yearMonthReceiptCopyText = "de " + Month.JANUARY.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " de " + nextYear;
        }
        else{
            yearMonthReceiptCopyText = "de " + timeRecordMonth.plus(1L).getDisplayName(TextStyle.FULL, Locale.getDefault()) + " de " + this.yearNumber.getText();
        }

        return TimeRecord.create()
                .withNameOfMonth(this.monthName.getSelectionModel().getSelectedItem().getNameOfMonth())
                .withYearNumber(this.yearNumber.getText())
                .withEnterpriseName(clientForTimeRecord.getSelectionModel().getSelectedItem().toString())
                .withQuoteAccountCode(data.getQuoteAccountCode())
                .withEmployeeName(data.getEmployeeFullName())
                .withEmployeeNIF(data.getEmployeeNif())
                .withNumberHoursPerWeek(data.getHoursByWeek() + ContractConstants.HOURS_WORK_WEEK_TEXT.toLowerCase())
                .withMonthYearReceiptCopyTetx(yearMonthReceiptCopyText)
                .build();
    }

    private void onChangeEmployer(ActionEvent event){
        dataByTimeRecord.getItems().clear();
        if(clientForTimeRecord.getSelectionModel().getSelectedItem() == null){
            return;
        }

        TimeRecordController timeRecordController = new TimeRecordController();
        Integer idSelectedClient = clientForTimeRecord.getSelectionModel().getSelectedItem().getIdcliente();
        LocalDate date = retrieveDateForTimeRecordFromSelector();
        List<ContractNewVersionDTO> contractNewVersionDTOList = timeRecordController.findAllContractNewVersionByClientIdInMonthOfDate(idSelectedClient, date);
        List<TimeRecordCandidateDataDTO> candidates = loadCandidateDataForTimeRecord(contractNewVersionDTOList);
        refreshCandidatesData(candidates);
    }

    private List<TimeRecordCandidateDataDTO> loadCandidateDataForTimeRecord(List<ContractNewVersionDTO> contractNewVersionDTOList){
        List<TimeRecordCandidateDataDTO> candidates = new ArrayList<>();
        if(!contractNewVersionDTOList.isEmpty()) {
            for (ContractNewVersionDTO contractNewVersionDTO : contractNewVersionDTOList) {
                if(!contractNewVersionDTO.getContractJsonData().getWeeklyWorkHours().equals("40:00")) {
                    Integer employeeId = contractNewVersionDTO.getContractJsonData().getWorkerId();
                    PersonDTO employee = retrievePersonByPersonId(employeeId);
                    String employeeNIF = Utilities.formatAsNIF(employee.getNifcif());
                    String employeeName = employee.getApellidos() + ", " + employee.getNom_rzsoc();

                    String dateTo;
                    if(contractNewVersionDTO.getModificationDate() == null &&
                            contractNewVersionDTO.getExpectedEndDate() == null){
                        dateTo = null;

                    }else{
                        dateTo = contractNewVersionDTO.getModificationDate() == null ? dateFormatter.format(contractNewVersionDTO.getExpectedEndDate()) : dateFormatter.format(contractNewVersionDTO.getModificationDate());
                    }

                    String dateFrom = dateFormatter.format(contractNewVersionDTO.getStartDate());

                    ContractTypeController contractTypeController = new ContractTypeController();
                    ContractTypeDTO contractTypeDTO = contractTypeController.findContractTypeById(contractNewVersionDTO.getContractJsonData().getContractType());

                    TimeRecordCandidateDataDTO dataCandidates = new TimeRecordCandidateDataDTO(
                            employeeName,
                            employeeNIF,
                            contractNewVersionDTO.getContractJsonData().getQuoteAccountCode(),
                            contractNewVersionDTO.getContractJsonData().getFullPartialWorkDay(),
                            contractNewVersionDTO.getContractJsonData().getWeeklyWorkHours(),
                            contractTypeDTO.getColloquial(),
                            dateFrom,
                            dateTo
                    );

                    candidates.add(dataCandidates);
                }
            }
        }

        return candidates;
    }

    private void loadClientForTimeRecord(ActionEvent event) {
        dataByTimeRecord.getItems().clear();
        ClientController clientController = new ClientController();
        LocalDate yearMonthDayDate = retrieveDateForTimeRecordFromSelector();

        List<ClientDTOOk> clientWithTimeRecordContract = clientController.findAllClientWithContractNewVersionInMonth(yearMonthDayDate);

        List<TimeRecordClientDTO> activeClientList = new ArrayList<>();
        for(ClientDTOOk clientDTO: clientWithTimeRecordContract){
            TimeRecordClientDTO timeRecordClientDTO = TimeRecordClientDTO.create()
                    .withIdcliente(clientDTO.getClientId())
                    .withNom_rzsoc(clientDTO.toString())
                    .build();

            activeClientList.add(timeRecordClientDTO);
        }

        List<TimeRecordClientDTO> clientListWithActiveContractWithoutDuplicates = retrieveClientListWithActiveContractWithoutDuplicates(activeClientList);

        ObservableList<TimeRecordClientDTO> clientDTOS = FXCollections.observableArrayList(clientListWithActiveContractWithoutDuplicates);
        clientForTimeRecord.setItems(clientDTOS);
    }

    private void refreshCandidatesData(List<TimeRecordCandidateDataDTO> candidates){

        Collator primaryCollator = Collator.getInstance(new Locale("es","ES"));
        primaryCollator.setStrength(Collator.PRIMARY);

        List<TimeRecordCandidateDataDTO> sortedCandidatesByName = candidates
                .stream()
                .sorted(Comparator.comparing(TimeRecordCandidateDataDTO::getEmployeeFullName, primaryCollator)).collect(Collectors.toList());

        ObservableList<TimeRecordCandidateDataDTO> candidateObList = FXCollections.observableArrayList(sortedCandidatesByName);
        dataByTimeRecord.setItems(candidateObList);
    }

    private List<TimeRecordClientDTO> retrieveClientListWithActiveContractWithoutDuplicates(List<TimeRecordClientDTO> activeClientList ){

        List<TimeRecordClientDTO> activeClientListWithoutDuplicates = new ArrayList<>();

        Map<Integer, TimeRecordClientDTO> clientMap = new HashMap<>();

        for (TimeRecordClientDTO timeRecordClientDTO : activeClientList) {
            clientMap.put(timeRecordClientDTO.getIdcliente(), timeRecordClientDTO);
        }

        for (Map.Entry<Integer, TimeRecordClientDTO> itemMap : clientMap.entrySet()) {
            activeClientListWithoutDuplicates.add(itemMap.getValue());
        }

        Collator primaryCollator = Collator.getInstance(new Locale("es","ES"));
        primaryCollator.setStrength(Collator.PRIMARY);

        return activeClientListWithoutDuplicates
                .stream()
                .sorted(Comparator.comparing(TimeRecordClientDTO::getNom_rzsoc, primaryCollator)).collect(Collectors.toList());
    }

    private PersonDTO retrievePersonByPersonId(Integer employeeId){
        PersonController personController = new PersonController();
        PersonDTO employee = personController.findPersonById(employeeId);

        return employee;
    }

    private LocalDate retrieveDateForTimeRecordFromSelector(){

        Integer numberOfMonth = (monthName.getSelectionModel().getSelectedItem().getNameOfMonth().getValue());
        Integer numberOfYear = null;

        try{

            numberOfYear = Integer.parseInt(yearNumber.getText());

        }catch (NumberFormatException e){

            yearNumber.setText(String.valueOf(LocalDate.now().getYear()));
            clientForTimeRecord.getSelectionModel().clearSelection();
            return LocalDate.of(LocalDate.now().getYear(), numberOfMonth, 15);

        }

        return  LocalDate.of(numberOfYear, numberOfMonth, 15);
    }
}
