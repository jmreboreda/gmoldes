package gmoldes.components.contract.new_contract;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract.events.ChangeContractDataHoursWorkWeekEvent;
import gmoldes.components.generic_components.ContractDurationInput;
import gmoldes.components.generic_components.DateInput;
import gmoldes.components.generic_components.DaysOfWeekSelector;
import gmoldes.components.generic_components.WorkDayTypeInput;
import gmoldes.controllers.ContractTypeController;
import gmoldes.domain.dto.ContractTypeDTO;
import gmoldes.domain.dto.ProvisionalContractDataDTO;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;


public class ContractData extends AnchorPane {

    private static final Logger logger = Logger.getLogger(ContractData.class.getSimpleName());
    private static final String CONTRACT_DATA_FXML = "/fxml/new_contract/contract_data.fxml";

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parameters.DEFAULT_DATE_FORMAT);
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(Parameters.DEFAULT_TIME_FORMAT);

    private Parent parent;

    @FXML
    private DateInput dateNotification;
    @FXML
    private TextField hourNotification;
    @FXML
    private ChoiceBox<ContractTypeDTO> contractType;
    @FXML
    private ContractDurationInput contractDuration;
    @FXML
    private WorkDayTypeInput workDayType;
    @FXML
    private DaysOfWeekSelector daysOfWeekToWork;
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
        loadContractType();
    }

    public ProvisionalContractDataDTO getAllProvisionalContractData(){

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
        if(this.workDayType.radioButtonFullWorkDayIsSelected()){
            this.workDayType.setHoursWorkWeek(Utilities.converterDurationToTimeString(Parameters.LEGAL_MAXIMUM_HOURS_OF_WORK_PER_WEEK));
            workDayType = Parameters.FULL_WORKDAY;
            numberHoursPerWeek = Utilities.converterDurationToTimeString(Parameters.LEGAL_MAXIMUM_HOURS_OF_WORK_PER_WEEK);
        }
        if(this.workDayType.radioButtonPartialWorkDayIsSelected()){
            workDayType = Parameters.PARTIAL_WORKDAY;
            if(this.workDayType.getHoursWorkWeek() != null){
                numberHoursPerWeek = this.workDayType.getHoursWorkWeek();
            }
        }

        Set<DayOfWeek> daysWeekToWork = daysOfWeekToWork.getDaysOfWeek();

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
                .withLaborCategory(laborCategory)
                .build();
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
        //hourNotification.setText(timeFormatter.format(LocalTime.now()));
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

    public String getHourNotification(){
        return this.hourNotification.getText();
    }

    public String getHoursWorkWeek(){
        return this.workDayType.getHoursWorkWeek();
    }

    public Set<DayOfWeek> getDaysOfWeekToWork(){
        return this.daysOfWeekToWork.getDaysOfWeek();
    }

    public String getLaborCategory(){
        return this.laborCategoryDescriptionInput.getText();
    }

    public void setOnChangeContractDataHoursWorkWeek(EventHandler<ChangeContractDataHoursWorkWeekEvent> handler){
        workDayType.setOnChangeContractDataHoursWorkWeek(handler);
    }
}
