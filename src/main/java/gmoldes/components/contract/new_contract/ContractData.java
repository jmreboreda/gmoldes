package gmoldes.components.contract.new_contract;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract.events.ChangeContractDataHoursWorkWeekEvent;
import gmoldes.components.generic_components.DaysOfWeekCheckboxGroup;
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
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Logger;


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
    private CustomTextFieldHoursWorkWeek hoursWorkWeek;
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
    private DaysOfWeekCheckboxGroup daysOfWeekCheckBoxGroup;
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

        this. hoursWorkWeek.getTextFieldComponent().setOnAction(this::onHoursWorkWeekChanged);
        hoursWorkWeek.getTextFieldComponent().focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue)   // Textfield out focus
            {
                if(!hoursWorkWeek.getTextFieldComponent().getText().isEmpty()){
                    onHoursWorkWeekChanged(new ActionEvent());
                }
                else{
                    hoursWorkWeek.getTextFieldComponent().setText("00:00");
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
        if(dateFrom.getValue() != null && dateTo.getValue() != null) {
            Long daysOfContractDuration = ChronoUnit.DAYS.between(dateFrom.getValue(), dateTo.getValue()) + 1;
            durationDaysContract.setText(daysOfContractDuration.toString());
        }
        else{
            durationDaysContract.setText("");
        }
    }

    private void onHoursWorkWeekChanged(ActionEvent event){

        if(!hoursWorkWeek.verifyHoursWorkWeekChangeIsValid()){

            return;

        }else {
            Duration hoursWorkWeekDuration = Utilities.converterTimeStringToDuration(hoursWorkWeek.getTextFieldComponent().getText());
            ChangeContractDataHoursWorkWeekEvent contractDataHoursWorkWeekEvent = new ChangeContractDataHoursWorkWeekEvent(hoursWorkWeekDuration);
            changeContractDataHoursWorkWeekEventHandler.handle(contractDataHoursWorkWeekEvent);
        }
    }

    public ProvisionalContractDataDTO getAllData(){

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
            hoursWorkWeek.getTextFieldComponent().setText(Utilities.converterDurationToTimeString(Parameters.LEGAL_MAXIMUM_HOURS_OF_WORK_PER_WEEK));
            workDayType = FULL_WORKDAY;
            numberHoursPerWeek = Utilities.converterDurationToTimeString(Parameters.LEGAL_MAXIMUM_HOURS_OF_WORK_PER_WEEK);
        }
        if(radioButtonPartialWorkDay.isSelected()){
            workDayType = PARTIAL_WORKDAY;
            if(hoursWorkWeek.getTextFieldComponent().getText() != null){
                numberHoursPerWeek = hoursWorkWeek.getTextFieldComponent().getText();
            }
        }

        Set<DayOfWeek> daysWeekToWork = daysOfWeekCheckBoxGroup.getWorkDaysOfTheWeek();

        String laborCategory = "";
        if(laborCategoryDescriptionInput.getText() != null){
            laborCategory = laborCategoryDescriptionInput.getText();
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
        hoursWorkWeek.getTextFieldComponent().setText(Utilities.converterDurationToTimeString(Parameters.LEGAL_MAXIMUM_HOURS_OF_WORK_PER_WEEK));
        grWorkDay.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (radioButtonFullWorkDay.isSelected()) {
                    hoursWorkWeek.getTextFieldComponent().setText(Utilities.converterDurationToTimeString(Parameters.LEGAL_MAXIMUM_HOURS_OF_WORK_PER_WEEK));
                    hoursWorkWeek.getTextFieldComponent().setDisable(true);
                }else{
                    hoursWorkWeek.getTextFieldComponent().setDisable(false);
                    hoursWorkWeek.getTextFieldComponent().setText("00:00");
                    hoursWorkWeek.getTextFieldComponent().requestFocus();
                }
            }
        });
    }

    private void loadContractType(){

        ContractTypeController contractTypeController = new ContractTypeController();
        List<ContractTypeDTO> contractTypeDTOList = contractTypeController.findAllContractTypes();

        ObservableList<ContractTypeDTO> contractTypeDTOS = FXCollections.observableArrayList(contractTypeDTOList);
        contractType.setItems(contractTypeDTOS);
        for(ContractTypeDTO contractTypeDTO : contractTypeDTOS){
            if(contractTypeDTO.getDescripctto().toUpperCase().contains(Parameters.NORMAL_CONTRACT_TYPE_DESCRIPTION) ||
                    contractTypeDTO.getDescripctto().toUpperCase().contains(Parameters.ORDINARY_CONTRACT_TYPE_DESCRIPTION)){
                contractType.getSelectionModel().select(contractTypeDTO);
            }
        }
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
        return hoursWorkWeek.getTextFieldComponent().getText();
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
