package gmoldes.components.contract.new_contract.components;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract.controllers.ContractTypeController;
import gmoldes.components.contract.events.ChangeContractDataHoursWorkWeekEvent;
import gmoldes.components.generic_components.*;
import gmoldes.domain.contract.dto.ContractTypeDTO;
import gmoldes.domain.contract.dto.ProvisionalContractDataDTO;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.text.Collator;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class ContractData extends AnchorPane {

    private static final Logger logger = Logger.getLogger(ContractData.class.getSimpleName());
    private static final String CONTRACT_DATA_FXML = "/fxml/new_contract/contract_data.fxml";

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parameters.DEFAULT_DATE_FORMAT);
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(Parameters.DEFAULT_TIME_FORMAT);

    private Parent parent;

    @FXML
    private DateInput dateNotification;
    @FXML
    private TimeInput24HoursClock hourNotification;
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

        dateNotification.setOnAction(this::onDateNotification);
        this.hourNotification.setInputMinWidth(75D);
        loadContractType();

        //init();

    }

    private void init(){

    }

    public DateInput getDateNotification() {
        return dateNotification;
    }

    public void setDateNotification(DateInput dateNotification) {
        this.dateNotification = dateNotification;
    }

    public TimeInput24HoursClock getHourNotification() {
        return hourNotification;
    }

    public void setHourNotification(TimeInput24HoursClock hourNotification) {
        this.hourNotification = hourNotification;
    }

    private void onDateNotification(ActionEvent actionEvent){
        this.hourNotification.requestFocus();
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
                durationContract = ContractConstants.UNDEFINED_DURATION_TEXT;
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
            this.workDayType.setHoursWorkWeek(Utilities.converterDurationToTimeString(ContractConstants.LEGAL_MAXIMUM_HOURS_OF_WORK_PER_WEEK));
            workDayType = ContractConstants.FULL_WORKDAY;
            numberHoursPerWeek = Utilities.converterDurationToTimeString(ContractConstants.LEGAL_MAXIMUM_HOURS_OF_WORK_PER_WEEK);
        }
        if(this.workDayType.radioButtonPartialWorkDayIsSelected()){
            workDayType = ContractConstants.PARTIAL_WORKDAY;
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
        List<ContractTypeDTO> contractTypeDTOList = contractTypeController.findAllSelectableContractTypes();

        List<ContractTypeDTO> contractTypeListWithoutDuplicates = retrieveContractTypeWithoutDuplicatesSorted(contractTypeDTOList);

        ObservableList<ContractTypeDTO> contractTypeDTOS = FXCollections.observableArrayList(contractTypeListWithoutDuplicates);
        contractType.setItems(contractTypeDTOS);
    }

    public ContractTypeDTO getContractType(){
        return this.contractType.getValue();
    }

    public String getUndefinedTemporalContract(){
        if(this.contractDuration.radioButtonUndefinedIsSelected()){
            return ContractConstants.UNDEFINED_DURATION_TEXT;
        }
        else{
            return ContractConstants.TEMPORAL_DURATION_TEXT;
        }
    }

    public Boolean isUndefinedContract(){
        if(this.contractDuration.radioButtonUndefinedIsSelected()) {
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean isTemporalContract(){
        if(this.contractDuration.radioButtonTemporalIsSelected()) {
            return true;
        }
        else{
            return false;
        }
    }

    public ContractDurationInput getContractDurationInput(){
        return this.contractDuration;
    }

    public String getContractDurationDays(){
        return this.contractDuration.getDuration();
    }

    public String getFullPartialWorkDay(){
        if(this.workDayType.radioButtonFullWorkDayIsSelected()){
            return ContractConstants.FULL_WORKDAY;
        }

        return ContractConstants.PARTIAL_WORKDAY;
    }

    public WorkDayTypeInput getWorkDayType(){
        return this.workDayType;
    }

    public Boolean isFullWorkDay(){
        if(this.workDayType.radioButtonFullWorkDayIsSelected()){
            return true;
        }
        else{
        }
        return false;
    }

    public Boolean isPartialWorkDay(){
        if(this.workDayType.radioButtonFullWorkDayIsSelected()){
            return false;
            }
            else{
        }
            return true;
    }

    public LocalDate getDateFrom(){
        return this.contractDuration.getDateFrom();
    }

    public LocalDate getDateTo(){
        return this.contractDuration.getDateTo();
    }

    public Boolean isContractInForceAtDate(LocalDate date){
        if(getDateFrom().isAfter(date)){
            return false;
        }
        if(getDateTo() != null && getDateTo().isBefore(date)){
            return false;
        }

        return true;
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

    private List<ContractTypeDTO> retrieveContractTypeWithoutDuplicatesSorted(List<ContractTypeDTO> contractTypeDTOList ){

        List<ContractTypeDTO> contractTypeListWithoutDuplicates = new ArrayList<>();

        Map<Integer, ContractTypeDTO> contractTypeMap = new HashMap<>();

        for (ContractTypeDTO contractTypeDTO : contractTypeDTOList) {
            contractTypeMap.put(contractTypeDTO.getId(), contractTypeDTO);
        }

        for (Map.Entry<Integer, ContractTypeDTO> itemMap : contractTypeMap.entrySet()) {
            contractTypeListWithoutDuplicates.add(itemMap.getValue());
        }

        Collator primaryCollator = Collator.getInstance(new Locale("es","ES"));
        primaryCollator.setStrength(Collator.PRIMARY);

        return contractTypeListWithoutDuplicates
                .stream()
                .sorted(Comparator.comparing(ContractTypeDTO::getColloquial, primaryCollator)).collect(Collectors.toList());
    }
}
