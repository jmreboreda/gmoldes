package gmoldes.components.timerecord;

import com.lowagie.text.DocumentException;
import gmoldes.components.ViewLoader;
import gmoldes.controllers.ClientController;
import gmoldes.controllers.ContractController;
import gmoldes.controllers.PersonController;
import gmoldes.domain.dto.ContractDTO;
import gmoldes.domain.dto.PersonDTO;
import gmoldes.domain.dto.TimeRecordCandidateDataDTO;
import gmoldes.domain.dto.TimeRecordClientDTO;
import gmoldes.forms.TimeRecord;
import gmoldes.services.Printer;
import gmoldes.utilities.Enumeration;
import gmoldes.utilities.Message;
import gmoldes.utilities.Utilities;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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

import java.awt.print.PrinterException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class TimeRecordData extends VBox {

    private static final String TIME_RECORD_FXML = "/fxml/time_record/timerecord_data.fxml";
    private static final Integer FIRST_MONTH_INDEX_IN_MONTHNAME = 0;
    private static final Integer LAST_MONTH_INDEX_IN_MONTHNAME = 11;
    private final BooleanProperty activeButton = new SimpleBooleanProperty(false);
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

    private Parent parent;

    @FXML
    private ChoiceBox<Enumeration.Months> monthName;
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
                Enumeration.Months.ENERO,
                Enumeration.Months.FEBRERO,
                Enumeration.Months.MARZO,
                Enumeration.Months.ABRIL,
                Enumeration.Months.MAYO,
                Enumeration.Months.JUNIO,
                Enumeration.Months.JULIO,
                Enumeration.Months.AGOSTO,
                Enumeration.Months.SEPTIEMBRE,
                Enumeration.Months.OCTUBRE,
                Enumeration.Months.NOVIEMBRE,
                Enumeration.Months.DICIEMBRE
                )
        );

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
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
        dateFrom.setStyle("-fx-alignment: CENTER;");
        dateTo.setStyle("-fx-alignment: CENTER;");

        loadClientForTimeRecord(new ActionEvent());
    }

    private void onCreateTimeRecordPDF(MouseEvent event){
        String pathToTimeRecordPDF = createPDF();
        Message.warningMessage(createPDFButton.getScene().getWindow(),"Información del sistema", "Registro horario creado en:" + "\n" + pathToTimeRecordPDF + "\n");
    }

    private void onPrintTimeRecord(MouseEvent event){
        String pathToTimeRecordPDF = createPDF();

        Map<String, String> attributes = new HashMap<>();
        attributes.put("papersize","A4");
        attributes.put("sides", "DUPLEX");
        attributes.put("chromacity","MONOCHROME");
        attributes.put("orientation","LANDSCAPE");

        try {
            String resultPrint = Printer.printPDF(pathToTimeRecordPDF, attributes);
            Utilities.deleteFileFromPath(pathToTimeRecordPDF);
            if(resultPrint.equals("ok")) {
                Message.warningMessage(printButton.getScene().getWindow(), "Sistema", "Registro horario enviado a la impresora." + "\n");
            }else{
                Message.warningMessage(printButton.getScene().getWindow(), "Sistema", "No existe impresora para imprimir el registro horario." + "\n");
            }
        } catch (IOException | PrinterException e) {
            e.printStackTrace();
        }
    }

    private void onExit(MouseEvent event){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    private String createPDF(){
        TimeRecordCandidateDataDTO data = dataByTimeRecord.getSelectionModel().getSelectedItem();
        TimeRecord timeRecord = TimeRecord.create()
                .withNameOfMonth(this.monthName.getSelectionModel().getSelectedItem().getMonthName())
                .withYearNumber(this.yearNumber.getText())
                .withEnterpriseName(clientForTimeRecord.getSelectionModel().getSelectedItem().toString())
                .withQuoteAccountCode(data.getQuoteAccountCode())
                .withEmployeeName(data.getEmployeeFullName())
                .withEmployeeNIF(data.getEmployeeNif())
                .withNumberHoursPerWeek(data.getHoursByWeek())
                .build();

        String pathToTimeRecordPDF = "";
        try {
            pathToTimeRecordPDF = TimeRecord.createPDF(timeRecord);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }

        return pathToTimeRecordPDF;
    }

    private void onChangeEmployer(ActionEvent event){
        dataByTimeRecord.getItems().clear();
        if(clientForTimeRecord.getSelectionModel().getSelectedItem() == null){
            return;
        }
        List<TimeRecordCandidateDataDTO> candidates = new ArrayList<>();
        ContractController contractController = new ContractController();
        Integer idSelectedClient = clientForTimeRecord.getSelectionModel().getSelectedItem().getIdcliente();
        String yearMonth = retrievePeriodForTimeRecord();
        List<ContractDTO> contractDTOList = contractController.findAllContractsWithTimeRecordByClientIdInPeriod(idSelectedClient, yearMonth);
        if(!contractDTOList.isEmpty()) {
            Integer employeeId;
            String dateTo;
            for (ContractDTO contractDTO : contractDTOList) {
                employeeId = contractDTO.getIdtrabajador();
                String employeeNIF = retrieveNifByPersonId(employeeId);
                if(contractDTO.getF_hasta() != null){
                    dateTo = dateFormatter.format(contractDTO.getF_hasta());
                }else{
                    dateTo = "";
                }
                String dateFrom = dateFormatter.format(contractDTO.getF_desde());

                TimeRecordCandidateDataDTO dataCandidates = new TimeRecordCandidateDataDTO(
                        contractDTO.getTrabajador_name(),
                        employeeNIF,
                        contractDTO.getContrato_ccc(),
                        contractDTO.getJor_tipo(),
                        contractDTO.getJor_trab(),
                        contractDTO.getTipoctto(),
                        dateFrom,
                        dateTo
                );
                candidates.add(dataCandidates);
            }
        }
        refreshData(candidates);
    }

    private void loadClientForTimeRecord(ActionEvent event) {
        dataByTimeRecord.getItems().clear();
        ClientController clientController = new ClientController();
        List<TimeRecordClientDTO> activeClientList = clientController.findAllClientWithContractWithTimeRecordInPeriodSorted(retrievePeriodForTimeRecord());

        List<TimeRecordClientDTO> clientListWithActiveContractWithoutDuplicates = retrieveClientListWithActiveContractWithoutDuplicates(activeClientList);

        ObservableList<TimeRecordClientDTO> clientDTOS = FXCollections.observableArrayList(clientListWithActiveContractWithoutDuplicates);
        clientForTimeRecord.setItems(clientDTOS);
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

        return activeClientListWithoutDuplicates
                .stream()
                .sorted(Comparator.comparing(TimeRecordClientDTO::getNom_rzsoc)).collect(Collectors.toList());
    }

    private void refreshData(List<TimeRecordCandidateDataDTO> candidates){

        ObservableList<TimeRecordCandidateDataDTO> candidateObList = FXCollections.observableArrayList(candidates);
        dataByTimeRecord.setItems(candidateObList);
    }

    private String retrieveNifByPersonId(Integer employeeId){
        PersonController personController = new PersonController();
        PersonDTO employee = personController.findPersonById(employeeId);

        return Utilities.formatAsNIF(employee.getNifcif());
    }

    private String retrievePeriodForTimeRecord(){
        Integer numberMonth = (monthName.getSelectionModel().getSelectedIndex()) + 1;
        String numberMonthS = numberMonth.toString();
        if(numberMonthS.length() < 2){
            numberMonthS = "0" + numberMonthS;
        }

        return (yearNumber.getText()).concat(numberMonthS);
    }
}
