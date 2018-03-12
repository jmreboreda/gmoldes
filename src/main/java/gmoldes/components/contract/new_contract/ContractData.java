package gmoldes.components.contract.new_contract;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract.events.ChangeContractDataHoursWorkWeekEvent;
import gmoldes.controllers.ContractTypeController;
import gmoldes.domain.dto.ContractTypeDTO;
import gmoldes.domain.dto.ProvisionalContractDataDTO;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.Utilities;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;


public class ContractData extends AnchorPane {

    private static final Logger logger = Logger.getLogger(ContractData.class.getSimpleName());
    private static final String CONTRACT_DATA_FXML = "/fxml/new_contract/contract_data.fxml";
    private static final String FULL_WORKDAY = "A tiempo completo";
    private static final String PARTIAL_WORKDAY = "A tiempo parcial";
    private static final String UNDEFINED_DURATION = "Indefinido";

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private DecimalFormat decimalFormatter = new DecimalFormat("0.00##");

    private EventHandler<ChangeContractDataHoursWorkWeekEvent> changeContractDataHoursWorkWeekEventHandler;


    private Parent parent;

    @FXML
    private DatePicker dateNotification;
    @FXML
    private TextField hourNotification;
    @FXML
    private ChoiceBox<ContractTypeDTO> contractType;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;
    @FXML
    private ToggleGroup grContractDuration;
    @FXML
    private ToggleGroup grWorkDay;
    @FXML
    private TextField hoursWorkWeek;
    @FXML
    private RadioButton radioButtonUndefinedContractDuration;
    @FXML
    private RadioButton radioButtonTemporalContractDuration;
    @FXML
    private TextField durationDaysContract;
    @FXML
    private RadioButton radioButtonFullWorkDay;
    @FXML
    private RadioButton radioButtonPartialWorkDay;
    @FXML
    private CheckBox Monday;
    @FXML
    private CheckBox Tuesday;
    @FXML
    private CheckBox Wednesday;
    @FXML
    private CheckBox Thursday;
    @FXML
    private CheckBox Friday;
    @FXML
    private CheckBox Saturday;
    @FXML
    private CheckBox Sunday;
    @FXML
    private TextField laborCategoryDescriptionInput;

    public ContractData() {

        this.parent = ViewLoader.load(this, CONTRACT_DATA_FXML);
    }

    @FXML
    private void initialize(){
        logger.info("Initializing contract data fxml ...");
        dateFrom.setOnAction(this::onDateAction);
        dateTo.setOnAction(this::onDateAction);
        hoursWorkWeek.setOnAction(this::onHoursWorkWeekChanged);
        hoursWorkWeek.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue)   // Textfield out focus
            {
                if(!hoursWorkWeek.getText().isEmpty()){
                onHoursWorkWeekChanged(new ActionEvent());
                }
            }
        });
        init();

    }

    private void init(){
        notificationDateControlSetup();
        contractDurationControlSetup();
        workDayDataControlSetup();
        loadContractType();
    }

    private void onDateAction(ActionEvent actionEvent){
        if(this.dateTo.getValue() != null && this.dateFrom.getValue() != null) {
            Long durationContractCalc = (this.dateTo.getValue().toEpochDay() - this.dateFrom.getValue().toEpochDay() + 1);
            this.durationDaysContract.setText(durationContractCalc.toString());
        }else{
            this.durationDaysContract.setText("");
        }
    }

    private void onHoursWorkWeekChanged(ActionEvent event){
        Pattern timePattern = Pattern.compile("\\d{2}[:]\\d{2}");
        if(!timePattern.matcher(hoursWorkWeek.getText()).matches()) {
            hoursWorkWeek.setText(null);

            return;
        }

        String hoursWorkPerWeek = hoursWorkWeek.getText();
        if((Utilities.converterTimeStringToDuration(hoursWorkPerWeek) == null)){
            hoursWorkWeek.setText(null);

            return;
        }

        Duration durationEnteredByUser = Utilities.converterTimeStringToDuration(hoursWorkPerWeek);
        if(durationEnteredByUser.compareTo(Parameters.LEGAL_MAXIMUM_HOURS_OF_WORK_PER_WEEK ) > 0 ){
            hoursWorkWeek.setText(null);

            return;
        }

        Duration hoursWorkWeekDuration = Utilities.converterTimeStringToDuration(hoursWorkWeek.getText());
        ChangeContractDataHoursWorkWeekEvent contractDataHoursWorkWeekEvent = new ChangeContractDataHoursWorkWeekEvent(hoursWorkWeekDuration);
        changeContractDataHoursWorkWeekEventHandler.handle(contractDataHoursWorkWeekEvent);
    }

    public ProvisionalContractDataDTO getAllData(){

        if(!isValidDataInContractData()){

            return null;
        }

        String contractType = null;
        if(this.contractType.getSelectionModel().getSelectedItem() != null){
            contractType = this.contractType.getSelectionModel().getSelectedItem().toString();
        }

        String dateFrom = null;
        if(this.dateFrom.getValue() != null){
            dateFrom = this.dateFrom.getValue().format(dateFormatter);
        }

        String dateTo = null;
        if(this.dateTo.getValue() != null){
            dateTo = this.dateTo.getValue().format(dateFormatter);
        }

        String durationContract = "";
        if(this.radioButtonUndefinedContractDuration.isSelected()) {
            if (this.dateFrom.getValue() != null) {
                durationContract = UNDEFINED_DURATION;
            }
        }

        if(this.radioButtonTemporalContractDuration.isSelected()) {
            if (this.dateFrom.getValue() != null && this.dateTo.getValue() != null) {
                Long durationContractCalc = (this.dateTo.getValue().toEpochDay() - this.dateFrom.getValue().toEpochDay() + 1);
                durationContract = durationContractCalc.toString();
            }
        }

        String workDayType = "";
        String numberHoursPerWeek = "";
        if(radioButtonFullWorkDay.isSelected()){
            hoursWorkWeek.setText(Utilities.converterDurationToTimeString(Parameters.LEGAL_MAXIMUM_HOURS_OF_WORK_PER_WEEK));
            workDayType = FULL_WORKDAY;
            numberHoursPerWeek = Utilities.converterDurationToTimeString(Parameters.LEGAL_MAXIMUM_HOURS_OF_WORK_PER_WEEK);
        }
        if(radioButtonPartialWorkDay.isSelected()){
            workDayType = PARTIAL_WORKDAY;
            if(this.hoursWorkWeek.getText() != null){
                numberHoursPerWeek = this.hoursWorkWeek.getText();
            }
        }

        Map<String, Boolean> daysWeekToWork = new HashMap<>();
        daysWeekToWork.put("Monday", false);
        daysWeekToWork.put("Tuesday", false);
        daysWeekToWork.put("Wednesday", false);
        daysWeekToWork.put("Thursday", false);
        daysWeekToWork.put("Friday", false);
        daysWeekToWork.put("Saturday", false);
        daysWeekToWork.put("Sunday", false);

        if(Monday.isSelected()){
            daysWeekToWork.put("Monday", true);
        }
        if(Tuesday.isSelected()){
            daysWeekToWork.put("Tuesday", true);
        }
        if(Wednesday.isSelected()){
            daysWeekToWork.put("Wednesday", true);
        }
        if(Thursday.isSelected()){
            daysWeekToWork.put("Thursday", true);
        }
        if(Friday.isSelected()){
            daysWeekToWork.put("Friday", true);
        }
        if(Saturday.isSelected()){
            daysWeekToWork.put("Saturday", true);
        }
        if(Sunday.isSelected()){
            daysWeekToWork.put("Sunday", true);
        }

        String laborCategory = "";
        if(this.laborCategoryDescriptionInput.getText() != null){
            laborCategory = this.laborCategoryDescriptionInput.getText();
        }

        return ProvisionalContractDataDTO.create()
                .withContractType(contractType)
                .withDateFrom(dateFrom)
                .withDateTo(dateTo)
                .withDurationDays(durationContract)
                .withWorkDayType(workDayType)
                .withNumberHoursPerWeek(numberHoursPerWeek)
                .withDaysWeekToWork(daysWeekToWork)
                .withLaboralCategory(laborCategory)
                .build();
    }

    public Boolean isValidDataInContractData(){

        if(hourNotification.getText().isEmpty() ||
                contractType.getSelectionModel().getSelectedItem() == null ||
                dateFrom.getValue() == null ||
                radioButtonTemporalContractDuration.isSelected() && dateTo.getValue() == null ||
                radioButtonPartialWorkDay.isSelected() && hoursWorkWeek.getText() == null
                ){
            return false;
        }
        return true;
    }

    private void notificationDateControlSetup(){
        dateNotification.setConverter(Utilities.converter);
        dateNotification.showWeekNumbersProperty().set(false);
        dateNotification.setEditable(false);
        dateNotification.setValue(LocalDate.now());

        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH:mm");
        hourNotification.setText(hourFormatter.format(LocalTime.now()));

        hourNotificationControlSetup();
    }

    private void contractDurationControlSetup(){
        radioButtonUndefinedContractDuration.setSelected(true);
        dateFrom.setConverter(Utilities.converter);
        dateFrom.setValue(LocalDate.now());
        dateFrom.showWeekNumbersProperty().set(false);
        dateFrom.setEditable(false);

        dateTo.setConverter(Utilities.converter);
        dateTo.showWeekNumbersProperty().set(false);
        dateTo.setEditable(false);

        grContractDuration.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if(grContractDuration.getSelectedToggle() == radioButtonUndefinedContractDuration){
                    dateTo.setValue(null);
                    dateTo.setDisable(true);
                    durationDaysContract.setText(null);
                    durationDaysContract.setDisable(true);
                }else{
                    dateTo.setDisable(false);
                    durationDaysContract.setDisable(false);
                    dateTo.setValue(LocalDate.now());
                    dateTo.requestFocus();
                }
            }
        });
    }

    private void workDayDataControlSetup(){
        radioButtonFullWorkDay.setSelected(true);
        hoursWorkWeek.setText(Utilities.converterDurationToTimeString(Parameters.LEGAL_MAXIMUM_HOURS_OF_WORK_PER_WEEK));
        grWorkDay.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (radioButtonFullWorkDay.isSelected()) {
                    hoursWorkWeek.setText(Utilities.converterDurationToTimeString(Parameters.LEGAL_MAXIMUM_HOURS_OF_WORK_PER_WEEK));
                    hoursWorkWeek.setDisable(true);
                }else{
                    hoursWorkWeek.setDisable(false);
                    hoursWorkWeek.setText("00:00");
                    hoursWorkWeek.requestFocus();
                }
            }
        });
    }

    private void loadContractType(){

        ContractTypeController contractTypeController = new ContractTypeController();
        List<ContractTypeDTO> contractTypeDTOList = contractTypeController.findAllContractTypes();

        ObservableList<ContractTypeDTO> contractTypeDTOS = FXCollections.observableArrayList(contractTypeDTOList);
        contractType.setItems(contractTypeDTOS);
    }

    private void hourNotificationControlSetup(){
        hourNotification.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue)
            {
                Date hour = Utilities.validateStringAsTime(hourNotification.getText());
                if(hour == null){
                    hourNotification.setText("");
                }else{
                    SimpleDateFormat hourFormatter = new SimpleDateFormat("HH:mm");
                    hourNotification.setText(hourFormatter.format(hour));
                }
            }
        });
    }

    public String getHoursWorkWeek(){
        return hoursWorkWeek.getText();
    }

    public void setOnChangeContractDataHoursWorkWeek(EventHandler<ChangeContractDataHoursWorkWeekEvent> changeContractDataHoursWorkWeekEventHandler){
        this.changeContractDataHoursWorkWeekEventHandler = changeContractDataHoursWorkWeekEventHandler;
    }

    @Override
    public String toString(){
        return "Fecha notificación: " + this.dateNotification.getValue() + "\n"
                + "Hora notificación: " + this.hourNotification.getText() + "\n"
                + "Fecha desde: " + this.dateFrom.getValue() + "\n"
                + "Fecha hasta: " + this.dateTo.getValue() + "\n";
    }
}
