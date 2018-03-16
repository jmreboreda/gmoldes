package gmoldes.components.contract.new_contract;

import gmoldes.components.ViewLoader;
import gmoldes.components.generic_components.ContractDuration;
import gmoldes.components.generic_components.DateInput;
import gmoldes.components.generic_components.DaysOfWeekSelector;
import gmoldes.components.generic_components.TextInput;
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
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Logger;


public class ContractData extends AnchorPane {

    private static final Logger logger = Logger.getLogger(ContractData.class.getSimpleName());
    private static final String CONTRACT_DATA_FXML = "/fxml/new_contract/contract_data.fxml";

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parameters.DEFAULT_DATE_FORMAT);
    private DecimalFormat decimalFormatter = new DecimalFormat("0.00##");

    private Parent parent;

    @FXML
    private DateInput dateNotification;
    @FXML
    private TextField hourNotification;
    @FXML
    private ChoiceBox<ContractTypeDTO> contractType;
    @FXML
    private ContractDuration contractDuration;
    @FXML
    private ToggleGroup grWorkDay;
    @FXML
    private TextInput hoursWorkWeek;
    @FXML
    private RadioButton radioButtonFullWorkDay;
    @FXML
    private RadioButton radioButtonPartialWorkDay;
    @FXML
    private DaysOfWeekSelector daysOfWeekToWorkSelector;
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

        init();

    }

    private void init(){
        hourNotificationControlSetup();
        workDayDataControlSetup();
        loadContractType();
    }

    public ProvisionalContractDataDTO getAllData(){

        String contractType = null;
        if(this.contractType.getSelectionModel().getSelectedItem() != null){
            contractType = this.contractType.getSelectionModel().getSelectedItem().toString();
        }

        String dateFrom = null;
        if(this.contractDuration.getDateFrom() != null){
            dateFrom = this.contractDuration.getDateFrom().format(dateFormatter);
        }

        String dateTo = null;
        if(this.contractDuration.getDateTo() != null){
            dateTo = this.contractDuration.getDateTo().format(dateFormatter);
        }

        String durationContract = "";
        if(this.contractDuration.radioButtonUndefinedIsSelected()) {
            if (this.contractDuration.getDateFrom() != null) {
                durationContract = Parameters.UNDEFINED_DURATION;
            }
        }

        if(this.contractDuration.radioButtonTemporalIsSelected()) {
            if (this.contractDuration.getDateFrom() != null && this.contractDuration.getDateTo() != null) {
                Long durationContractCalc = (this.contractDuration.getDateTo().toEpochDay() - this.contractDuration.getDateFrom().toEpochDay() + 1);
                durationContract = durationContractCalc.toString();
            }
        }

        String workDayType = "";
        String numberHoursPerWeek = "";
        if(radioButtonFullWorkDay.isSelected()){
            hoursWorkWeek.setText(Utilities.converterDurationToTimeString(Parameters.LEGAL_MAXIMUM_HOURS_OF_WORK_PER_WEEK));
            workDayType = Parameters.FULL_WORKDAY;
            numberHoursPerWeek = Utilities.converterDurationToTimeString(Parameters.LEGAL_MAXIMUM_HOURS_OF_WORK_PER_WEEK);
        }
        if(radioButtonPartialWorkDay.isSelected()){
            workDayType = Parameters.PARTIAL_WORKDAY;
            if(hoursWorkWeek.getText() != null){
                numberHoursPerWeek = hoursWorkWeek.getText();
            }
        }

        Set<DayOfWeek> daysWeekToWork = daysOfWeekToWorkSelector.getDaysOfWeek();

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
        return this.hoursWorkWeek.getText();
    }
}
