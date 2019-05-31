package gmoldes.components.contract.new_contract.controllers;

import com.lowagie.text.DocumentException;
import gmoldes.App;
import gmoldes.ApplicationConstants;
import gmoldes.components.ViewLoader;
import gmoldes.components.contract.ContractConstants;
import gmoldes.components.contract.controllers.ContractController;
import gmoldes.components.contract.controllers.TypesContractVariationsController;
import gmoldes.components.contract.events.*;
import gmoldes.components.contract.manager.ContractManager;
import gmoldes.components.contract.new_contract.components.*;
import gmoldes.components.contract.new_contract.forms.ContractDataToContractsAgent;
import gmoldes.components.person_management.controllers.PersonManagementMainController;
import gmoldes.components.timerecord.TimeRecord;
import gmoldes.components.timerecord.components.TimeRecordConstants;
import gmoldes.domain.client.ClientService;
import gmoldes.domain.client.dto.ClientCCCDTO;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.client.persistence.vo.ClientCCCVO;
import gmoldes.domain.contract.dto.*;
import gmoldes.domain.contractjsondata.ContractDayScheduleJsonData;
import gmoldes.domain.contractjsondata.ContractJsonData;
import gmoldes.domain.contractjsondata.ContractScheduleJsonData;
import gmoldes.domain.document_for_print.NewContractDataDocumentCreator;
import gmoldes.domain.email.EmailDataCreationDTO;
import gmoldes.domain.person.controllers.PersonController;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.timerecord.service.TimeRecordPDFCreator;
import gmoldes.domain.traceability_contract_documentation.dto.TraceabilityContractDocumentationDTO;
import gmoldes.domain.traceability_contract_documentation.manager.TraceabilityContractDocumentationManager;
import gmoldes.services.AgentNotificator;
import gmoldes.services.email.EmailConstants;
import gmoldes.utilities.Message;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.SystemProcesses;
import gmoldes.utilities.Utilities;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.mail.internet.AddressException;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.logging.Logger;

import static gmoldes.components.contract.ContractConstants.STANDARD_NEW_CONTRACT_TEXT;

public class NewContractMainController extends VBox {

    private static final Logger logger = Logger.getLogger(NewContractMainController.class.getSimpleName());
    private static final String MAIN_FXML = "/fxml/new_contract/contract_main.fxml";

    private PersonController personController = new PersonController();

    private Boolean contractHasBeenSavedInDatabase = false;
    private Boolean contractHasBeenSentToContractAgent = false;

    private Process pdfViewerProcess = null;

    private Parent parent;

    @FXML
    private TabPane tabPane;
    @FXML
    private ContractHeader contractHeader;
    @FXML
    private ContractParts contractParts;
    @FXML
    private ContractData contractData;
    @FXML
    private ContractSchedule contractSchedule;
    @FXML
    private ContractPublicNotes contractPublicNotes;
    @FXML
    private ContractPrivateNotes contractPrivateNotes;
    @FXML
    private ProvisionalContractData provisionalContractData;
    @FXML
    private ContractActionComponents contractActionComponents;


    public NewContractMainController() {
        logger.info("Initilizing Main fxml");
        this.parent = ViewLoader.load(this, MAIN_FXML);
    }

    @FXML
    public void initialize() {

        contractActionComponents.setOnSendMailButton(this::onSendMailButton);
        contractActionComponents.setOnOkButton(this::onOkButton);
        contractActionComponents.setOnViewPDFButton(this::onViewPDFButton);
        contractActionComponents.setOnExitButton(this::onExitButton);
        tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                refreshProvisionalContractData();
            }
        });

        contractParts.setOnNewPerson(this::onNewPerson);
        contractParts.setOnSearchEmployers(this::onSearchEmployers);
        contractParts.setOnSearchEmployees(this::onSearchEmployees);
        contractParts.setOnSelectEmployer(this::onSelectEmployer);
        contractParts.setOnSelectEmployee(this::onSelectEmployee);
        contractData.setOnChangeContractType(this::OnChangeContractType);
        contractData.setOnChangeContractDataHoursWorkWeek(this::onChangeContractDataHoursWorkWeek);
        contractSchedule.setOnChangeScheduleDuration(this::onChangeScheduleDuration);

        setTabPaneIcon();
    }

    public ContractParts getContractParts() {
        return contractParts;
    }

    public ContractData getContractData() {
        return contractData;
    }

    public ContractSchedule getContractSchedule() {
        return contractSchedule;
    }

    public ContractPublicNotes getContractPublicNotes() {
        return contractPublicNotes;
    }

    public ContractPrivateNotes getContractPrivateNotes() {
        return contractPrivateNotes;
    }

    public ProvisionalContractData getProvisionalContractData() {
        return provisionalContractData;
    }

    private void setTabPaneIcon(){

        Tab contractPartsPane = tabPane.getTabs().get(0);
        ImageView iconParts = new ImageView(new Image("/pics/new_contract_icon/contract_parts_icon.png"));
        iconParts.setFitWidth(20); iconParts.setFitHeight(20);
        contractPartsPane.setGraphic(iconParts);

        Tab contractDataPane = tabPane.getTabs().get(1);
        ImageView iconData = new ImageView(new Image("/pics/new_contract_icon/contract_data_icon.png"));
        iconData.setFitWidth(20); iconData.setFitHeight(20);
        contractDataPane.setGraphic(iconData);

        Tab contractSchedulePane = tabPane.getTabs().get(2);
        ImageView iconSchedule = new ImageView(new Image("/pics/new_contract_icon/contract_schedule_icon.png"));
        iconSchedule.setFitWidth(20); iconSchedule.setFitHeight(20);
        contractSchedulePane.setGraphic(iconSchedule);

        Tab publicNotesPane = tabPane.getTabs().get(3);
        ImageView iconPublicNotes = new ImageView(new Image("/pics/new_contract_icon/public_notes_icon.png"));
        iconPublicNotes.setFitWidth(20); iconPublicNotes.setFitHeight(20);
        publicNotesPane.setGraphic(iconPublicNotes);

        Tab privateNotesPane = tabPane.getTabs().get(4);
        ImageView iconPrivateNotes = new ImageView(new Image("/pics/new_contract_icon/private_notes_icon.png"));
        iconPrivateNotes.setFitWidth(20); iconPrivateNotes.setFitHeight(20);
        privateNotesPane.setGraphic(iconPrivateNotes);
    }

    private void onExitButton(MouseEvent event){
        if(contractHasBeenSavedInDatabase){
            if(!contractHasBeenSentToContractAgent){
                if(!Message.confirmationMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractMainControllerConstants.CONTRACT_SAVED_BUT_NOT_SENDED_TO_CONTRACT_AGENT)){
                    return;
                }
            }
        }
        Stage stage = (Stage) tabPane.getScene().getWindow();
        stage.close();
    }

    private void onSendMailButton(MouseEvent event){
        Boolean isSendOk = false;
        Path pathOut;

        if (Message.confirmationMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractMainControllerConstants.QUESTION_SEND_MAIL_TO_CONTRACT_AGENT)) {

            NewContractDataDocumentCreator contractDocumentCreator = new NewContractDataDocumentCreator(this);

            ContractDataToContractsAgent initialContractDataToContractAgent = contractDocumentCreator.createInitialContractDataDocumentForContractsAgent();

            pathOut = contractDocumentCreator.retrievePathToContractDataToContractAgentPDF(initialContractDataToContractAgent);

            String attachedFileName = initialContractDataToContractAgent.toFileName().concat(ApplicationConstants.PDF_EXTENSION);

            Boolean documentToSendIsOpen = verifyDocumentStatus(attachedFileName);
            if(documentToSendIsOpen){
                Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, EmailConstants.CLOSE_DOCUMENT_TO_SEND);

                return;
            }

            AgentNotificator agentNotificator = new AgentNotificator();

            EmailDataCreationDTO emailDataCreationDTO = retrieveDateForEmailCreation(pathOut, attachedFileName);

            try {
                isSendOk = agentNotificator.sendEmailToContractsAgent(emailDataCreationDTO);
            } catch (AddressException e) {
                e.printStackTrace();
            }
            if(isSendOk){

                Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, EmailConstants.MAIL_SEND_OK);
                contractHasBeenSentToContractAgent = true;
                contractActionComponents.enableSendMailButton(false);

            }else{
                Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, EmailConstants.MAIL_NOT_SEND_OK);
            }
        }
    }

    private void onOkButton(MouseEvent event) {
        if (!NewContractDataVerifier.verifyContractParts(contractParts, tabPane)) {
            setStatusRevisionErrors(ContractConstants.REVISION_WITH_ERRORS);
            return;
        }

        if (!NewContractDataVerifier.verifyContractData(contractData, contractSchedule, tabPane)) {
            setStatusRevisionErrors(ContractConstants.REVISION_WITH_ERRORS);
            return;
        }

        if (!NewContractDataVerifier.verifyContractSchedule(contractData, contractSchedule, tabPane)) {
            setStatusRevisionErrors(ContractConstants.REVISION_WITH_ERRORS);
            return;
        }

        setStatusRevisionErrors(ContractConstants.REVISION_WITHOUT_ERRORS);
    }

    private void onViewPDFButton(MouseEvent mouseEvent) {
        Path pathOut;

        NewContractDataDocumentCreator contractDocumentCreator = new NewContractDataDocumentCreator(this);
        ContractDataToContractsAgent initialContractDataToContractAgent = contractDocumentCreator.createInitialContractDataDocumentForContractsAgent();

        pathOut = contractDocumentCreator.retrievePathToContractDataToContractAgentPDF(initialContractDataToContractAgent);

        if(ApplicationConstants.OPERATING_SYSTEM.toLowerCase().contains(ApplicationConstants.OS_LINUX)) {
            try {
                String[] command = {"sh", "-c", "xdg-open " + pathOut};
                pdfViewerProcess = Runtime.getRuntime().exec(command);
            } catch (IOException e) {
                System.out.println("No se ha podido abrir el documento \"" + initialContractDataToContractAgent.toFileName().concat(ApplicationConstants.PDF_EXTENSION) + "\"");
                e.printStackTrace();
            }
        }else if (ApplicationConstants.OPERATING_SYSTEM.toLowerCase().contains(ApplicationConstants.OS_WINDOWS)){
            String[] command = {"cmd","/c","start","\"visor\"","\"" + pathOut + "\""};
            try {
                pdfViewerProcess = Runtime.getRuntime().exec(command);
            } catch (IOException e) {
                System.out.println("No se ha podido abrir el documento \"" + initialContractDataToContractAgent.toFileName().concat(ApplicationConstants.PDF_EXTENSION) + "\"");
                e.printStackTrace();
            }
        }
    }

    private void onNewPerson(MouseEvent event){
        PersonManagementMainController personManagementMainController = new PersonManagementMainController();
        Scene scene = new Scene(personManagementMainController);
        scene.getStylesheets().add(App.class.getResource("/css_stylesheet/application.css").toExternalForm());
        Stage personManagementStage = new Stage();
        personManagementStage.setResizable(false);
        personManagementStage.setTitle("Mantenimiento de personas");
        personManagementStage.setScene(scene);
        personManagementStage.initOwner(tabPane.getScene().getWindow());
        personManagementStage.initModality(Modality.APPLICATION_MODAL);
        personManagementStage.show();
    }

    private void setStatusRevisionErrors(String statusText) {
        ProvisionalContractDataDTO dataDTO = retrieveProvisionalContractDataDTO();
        dataDTO.setStatus(statusText);
        provisionalContractData.refreshData(dataDTO);
        if (statusText.equals(ContractConstants.REVISION_WITHOUT_ERRORS)) {
            if (Message.confirmationMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractVerifierConstants.QUESTION_SAVE_NEW_CONTRACT)) {
                Integer initialContractNumber = persistInitialContract();
                Integer contractNumber = persistContract();
                persistTraceabilityControlData(initialContractNumber);
                contractActionComponents.enablePDFButton(true);
                }
        }
    }

    private void refreshProvisionalContractData() {
        ProvisionalContractDataDTO contractDataDTO = retrieveProvisionalContractDataDTO();
        provisionalContractData.refreshData(contractDataDTO);
    }

    private ProvisionalContractDataDTO retrieveProvisionalContractDataDTO() {
        ProvisionalContractDataDTO partsDTO = contractParts.getAllData();
        ProvisionalContractDataDTO dataStatusDTO = provisionalContractData.getAllProvisionalContractData();
        ProvisionalContractDataDTO dataDTO = contractData.getAllProvisionalContractData();
        dataDTO.setEmployerFullName(partsDTO.getEmployerFullName());
        dataDTO.setEmployeeFullName(partsDTO.getEmployeeFullName());
        dataDTO.setQuoteAccountCode(partsDTO.getQuoteAccountCode());
        dataDTO.setStatus(dataStatusDTO.getStatus());

        return dataDTO;
    }

    private void onSearchEmployers(SearchEmployersEvent searchEmployersEvent) {
        String pattern = searchEmployersEvent.getPattern();
        if (pattern.isEmpty()) {
            contractParts.clearEmployersData();
            contractParts.clearEmployerCCC();
            return;
        }
        List<ClientDTO> employers = findClientsWithAdvisoryServicesByNamePattern(pattern);
        contractParts.refreshEmployers(employers);
    }

    private void onSearchEmployees(SearchEmployeesEvent searchEmployeesEvent) {
        String pattern = searchEmployeesEvent.getPattern();
        if (pattern.isEmpty()) {
            contractParts.clearEmployeesData();
            return;
        }
        List<PersonDTO> employees = findPersonsByNamePatternInAlphabeticalOrder(pattern);
        contractParts.refreshEmployees(employees);
    }

    private void onSelectEmployer(SelectEmployerEvent selectEmployerEvent) {
        ClientDTO selectedEmployer = selectEmployerEvent.getSelectedEmployer();
        List<ClientDTO> clientDTOList = new ArrayList<>();
        clientDTOList.add(selectedEmployer);
        contractParts.refreshEmployers(clientDTOList);

        Integer selectedEmployerId = selectEmployerEvent.getSelectedEmployer().getId();
        updateClientCCC(selectedEmployer);
    }

    private void onSelectEmployee(SelectEmployeeEvent selectEmployeeEvent) {
        PersonDTO selectedEmployee = selectEmployeeEvent.getSelectedEmployee();
        List<PersonDTO> personDTOList = new ArrayList<>();
        personDTOList.add(selectedEmployee);
        contractParts.refreshEmployees(personDTOList);
    }

    private void OnChangeContractType(ChangeContractTypeEvent event){

        if(event.getContractType().getIsTemporal() ||
        event.getContractType().getIsDeterminedDuration()){
            contractData.getContractDurationInput().getRadioButtonTemporal().setSelected(true);
            //contractData.getContractDurationInput().getRadioButtonUndefined().setSelected(false);
        }else{
            //contractData.getContractDurationInput().getRadioButtonTemporal().setSelected(false);
            contractData.getContractDurationInput().getRadioButtonUndefined().setSelected(true);
        }

        if(event.getContractType().getIsFullTime()){
            contractData.getWorkDayType().getRadioButtonFullWorkDay().setSelected(true);
            //contractData.getWorkDayType().getRadioButtonPartialWorkDay().setSelected(false);
        }else{
            //contractData.getWorkDayType().getRadioButtonFullWorkDay().setSelected(false);
            contractData.getWorkDayType().getRadioButtonPartialWorkDay().setSelected(true);
        }

    }

    private void onChangeContractDataHoursWorkWeek(ChangeContractDataHoursWorkWeekEvent event) {
        Duration scheduleHoursWorkWeekDuration = Utilities.converterTimeStringToDuration(contractSchedule.getHoursWorkWeek());
        Duration contractDataWorkWeekDuration = event.getContractDataHoursWorkWeek();
        if (scheduleHoursWorkWeekDuration != Duration.ZERO) {
            if (contractDataWorkWeekDuration.compareTo(scheduleHoursWorkWeekDuration) != 0) {
                Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT,
                        ContractMainControllerConstants.CONTRACT_HOURS_DIFFERENT_FROM_SCHEDULE_HOURS);
            }
        }
    }

    private void onChangeScheduleDuration(ChangeScheduleDurationEvent event) {
        if (event.getContractScheduleTotalHoursDuration().compareTo(ContractConstants.LEGAL_MAXIMUM_HOURS_OF_WORK_PER_WEEK) > 0) {
            Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT,
                    ContractMainControllerConstants.EXCEEDED_MAXIMUM_LEGAL_WEEKLY_HOURS);
            return;
        }

        Duration duration = Utilities.converterTimeStringToDuration(contractData.getHoursWorkWeek());
        assert duration != null;
        if (event.getContractScheduleTotalHoursDuration().compareTo(duration) > 0) {
            Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT,
                    ContractMainControllerConstants.SCHEDULE_HOURS_GREATER_THAN_CONTRACT_HOURS);
        }
    }

    private List<ClientDTO> findClientsWithAdvisoryServicesByNamePattern(String pattern) {

        ClientService clientService = ClientService.ClientServiceFactory.getInstance();
        return clientService.findAllActiveClientWithAdvisoryServicesByNamePatternInAlphabeticalOrder(pattern);
    }

    private List<PersonDTO> findPersonsByNamePatternInAlphabeticalOrder(String pattern) {
        return personController.findAllPersonsByNamePatternInAlphabeticalOrder(pattern);
    }

    private void updateClientCCC(ClientDTO selectedClient) {

        List<ClientCCCDTO> clientCCCDTOList = new ArrayList<>();

        for(ClientCCCVO clientCCCVO : selectedClient.getClientCCC()) {
            ClientCCCDTO clientCCCDTO = ClientCCCDTO.create()
                    .withId(clientCCCVO.getId())
                    .withClientId(clientCCCVO.getClientVO().getClientId())
                    .withCccInss(clientCCCVO.getCcc_inss())
                    .build();
            clientCCCDTOList.add(clientCCCDTO);
        }
        contractParts.refreshEmployerCCC(clientCCCDTOList);
    }

    private Integer persistInitialContract(){

        ContractFullDataDTO contractFullDataDTO = retrieveContractFullData();

        ContractNewVersionDTO initialContractDTO = contractFullDataDTO.getContractNewVersion();
        ContractManager contractManager = new ContractManager();
        Integer contractNumber = contractManager.saveInitialContract(initialContractDTO);
        if (contractNumber != null) {
            blockingInterfaceAfterContractPersistence(contractFullDataDTO);
            verifyPrintTimeRecord();

            NewContractDataDocumentCreator contractDocumentCreator = new NewContractDataDocumentCreator(this);
            contractDocumentCreator.printSubfoldersOfTheInitialContract();

            contractActionComponents.enableSendMailButton(true);

        }else{
            Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractMainControllerConstants.CONTRACT_NOT_SAVED_OK);
        }

        return contractNumber;
    }

    private Integer persistContract(){

        Integer variationType;
        if(contractData.getContractType().getAdminPartnerSimilar()){
            variationType = 101;
        } else if(contractData.getContractType().getSurrogate()){
            variationType = 109;
        }else{
            variationType = 100;
        }

        ContractDTO contractDTO = ContractDTO.create()
                .withEmployer(contractParts.getSelectedEmployer().getClientId())
                .withEmployee(contractParts.getSelectedEmployee().getIdpersona())
                .withContractType(contractData.getContractType().getContractCode().toString())
                .withGmContractNumber(null)
                .withVariationType(variationType)
                .withStartDate(contractData.getDateFrom())
                .withExpectedEndDate(contractData.getDateTo())
                .withModificationDate(null)
                .withEndingDate(null)
                .withContractScheduleJsonData(retrieveContractScheduleJsonData())
                .withLaborCategory(contractData.getLaborCategory())
                .withQuoteAccountCode(contractParts.getSelectedCCC().getCccInss())
                .withIdentificationContractNumberINEM(null)
                .withPublicNotes(contractPublicNotes.getPublicNotes())
                .withPrivateNotes(contractPrivateNotes.getPrivateNotes())
                .build();

        ContractController contractController = new ContractController();
        return contractController.saveContract(contractDTO);
    }

    private void persistTraceabilityControlData(Integer contractNumber){

        // In a new contract, the date for the notice of end of contract is set at 31-12-9999 if the contract is of indefinite duration
        LocalDate contractEndNoticeToSave = contractData.getDateTo() == null ?  LocalDate.of(9999, 12, 31) : null;

        LocalDate dateFrom = contractData.getDateFrom();
        LocalDate dateTo = contractData.getDateTo();

        TraceabilityContractDocumentationDTO traceabilityDTO = TraceabilityContractDocumentationDTO.create()
                .withContractNumber(contractNumber)
                .withVariationType(ContractMainControllerConstants.ID_INITIAL_CONTRACT_TYPE_VARIATION)
                .withStartDate(dateFrom)
                .withExpectedEndDate(dateTo)
                .withContractEndNoticeReceptionDate(contractEndNoticeToSave)
                .build();

        TraceabilityContractDocumentationManager traceabilityManager = new TraceabilityContractDocumentationManager();
        Integer id = traceabilityManager.saveContractTraceability(traceabilityDTO);

        if(id == null){

            Message.warningMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.ERROR_PERSISTING_TRACEABILITY_CONTROL_DATA);
        }

    }

    private void blockingInterfaceAfterContractPersistence(ContractFullDataDTO contractFullDataDTO){
        Integer contractNumber = contractFullDataDTO.getContractNewVersion().getContractNumber();
        contractHasBeenSavedInDatabase = true;
        provisionalContractData.setContractText(STANDARD_NEW_CONTRACT_TEXT + " nº");
        provisionalContractData.setContractNumber(contractNumber);
        contractParts.setMouseTransparent(true);
        contractData.setMouseTransparent(true);
        contractSchedule.setMouseTransparent(true);
        contractPublicNotes.setMouseTransparent(true);
        contractPrivateNotes.setMouseTransparent(true);

        Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractMainControllerConstants.CONTRACT_SAVED_OK + contractNumber);

        contractActionComponents.enableOkButton(false);
    }

    private ContractFullDataDTO retrieveContractFullData(){

        String quoteAccountCode = contractParts.getSelectedCCC() == null ? "" : contractParts.getSelectedCCC().getCccInss();

        ContractJsonData contractJsonData = ContractJsonData.create()
                .withIdentificationContractNumberINEM(null)
                .withDaysOfWeekToWork(contractData.getDaysOfWeekToWork().toString())
                .withWeeklyWorkHours(contractData.getHoursWorkWeek())
                .withNotesForContractManager(contractPublicNotes.getPublicNotes())
                .withPrivateNotes(contractPrivateNotes.getPrivateNotes())
                .withLaborCategory(contractData.getLaborCategory())
                .withContractType(contractData.getContractType().getContractCode())
                .withFullPartialWorkDay(contractData.getFullPartialWorkDay())
                .withWorkerId(contractParts.getSelectedEmployee().getIdpersona())
                .withQuoteAccountCode(quoteAccountCode)
                .withClientGMId(contractParts.getSelectedEmployer().getClientId())
                .build();

        ContractScheduleJsonData schedule = retrieveContractScheduleJsonData();

        Integer variationTypeId;
        ContractTypeDTO contractTypeDTO = contractData.getContractType();
        if(contractTypeDTO.getAdminPartnerSimilar()){
            variationTypeId = ContractParameters.INITIAL_CONTRACT_ADMIN_PARTNER_SIMILAR;

        }else if(contractTypeDTO.getSurrogate()){
            variationTypeId = ContractParameters.INITIAL_CONTRACT_SURROGATE_CONTRACT;
        }else{
            variationTypeId = ContractParameters.ORDINARY_INITIAL_CONTRACT;
        }

        ContractNewVersionDTO initialContractDTO = ContractNewVersionDTO.create()
                .withVariationType(variationTypeId)
                .withStartDate(contractData.getDateFrom())
                .withExpectedEndDate(contractData.getDateTo())
                .withModificationDate(null)
                .withEndingDate(null)
                .withContractJsonData(contractJsonData)
                .withContractScheduleJsonData(schedule)
                .build();

        TypesContractVariationsController typesContractVariationsController = new TypesContractVariationsController();
        TypesContractVariationsDTO typesContractVariationsDTO = typesContractVariationsController.findTypesContractVariationsById(variationTypeId);

        ContractFullDataDTO contractFullDataDTO = ContractFullDataDTO.create()
                .withEmployer(contractParts.getSelectedEmployer())
                .withEmployee(contractParts.getSelectedEmployee())
                .withInitialContractDate(contractData.getDateFrom())
                .withContractNewVersionDTO(initialContractDTO)
                .withContractType(contractData.getContractType())
                .withTypesContractVariationsDTO(typesContractVariationsDTO)
                .build();

        return contractFullDataDTO;
    }

    private ContractScheduleJsonData retrieveContractScheduleJsonData(){

        Integer counter = 0;

        Map<String, ContractDayScheduleJsonData> contractDayScheduleJsonDataSet = new HashMap<>();

        ContractScheduleJsonData schedule = new ContractScheduleJsonData();

        Set<WorkDaySchedule> scheduleSet = contractSchedule.retrieveScheduleWithScheduleDays();
        for(WorkDaySchedule workDaySchedule : scheduleSet){

            if(!workDaySchedule.getDayOfWeek().isEmpty()) {

                String dayOfWeek = workDaySchedule.getDayOfWeek() != null ? workDaySchedule.getDayOfWeek() : "";
                String date = workDaySchedule.getDate() != null ? workDaySchedule.getDate().toString() : "";
                String amFrom = workDaySchedule.getAmFrom() != null ? workDaySchedule.getAmFrom().toString() : "";
                String amTo = workDaySchedule.getAmTo() != null ? workDaySchedule.getAmTo().toString() : "";
                String pmFrom = workDaySchedule.getPmFrom() != null ? workDaySchedule.getPmFrom().toString() : "";
                String pmTo = workDaySchedule.getPmTo() != null ? workDaySchedule.getPmTo().toString() : "";
                String durationHours = Utilities.converterDurationToTimeString(workDaySchedule.getDurationHours());

                ContractDayScheduleJsonData contractDayScheduleJson = ContractDayScheduleJsonData.create()
                        .withDayOfWeek(dayOfWeek)
                        .withDate(date)
                        .withAmFrom(amFrom)
                        .withAmTo(amTo)
                        .withPmFrom(pmFrom)
                        .withPmTo(pmTo)
                        .withDurationHours(durationHours)
                        .build();

                contractDayScheduleJsonDataSet.put("workDay" + counter, contractDayScheduleJson);
                counter++;
            }

            schedule.setSchedule(contractDayScheduleJsonDataSet);
        }

        return schedule;
    }

    private void verifyPrintTimeRecord(){
//        if(this.contractData.getFullPartialWorkDay().equals(ContractConstants.PARTIAL_WORKDAY)){
            String quoteAccountCode = contractParts.getSelectedCCC() == null ? "" : contractParts.getSelectedCCC().getCccInss();

            String yearMonthReceiptCopyText;
            Month actualMonth = this.contractData.getDateFrom().getMonth();
            Month nextMonth = actualMonth.plus(1L);
            Integer actualYear = this.contractData.getDateFrom().getYear();
            Integer nextYear = actualYear + 1;
            if(actualMonth == Month.DECEMBER){
                yearMonthReceiptCopyText = "de " + Month.JANUARY.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " de " + nextYear;
            }
            else{
                yearMonthReceiptCopyText = "de " + nextMonth.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " de " + actualYear;
            }

            TimeRecord timeRecord = TimeRecord.create()
                    .withNameOfMonth(this.contractData.getDateFrom().getMonth())
                    .withYearNumber(Integer.toString(contractData.getDateFrom().getYear()))
                    .withEnterpriseName(this.contractParts.getSelectedEmployer().toString())
                    .withQuoteAccountCode(quoteAccountCode)
                    .withEmployeeName(this.contractParts.getSelectedEmployee().getApellidos() + ", " + this.contractParts.getSelectedEmployee().getNom_rzsoc())
                    .withEmployeeNIF(Utilities.formatAsNIF(this.contractParts.getSelectedEmployee().getNifcif()))
                    .withNumberHoursPerWeek(this.contractData.getHoursWorkWeek() + ContractConstants.HOURS_WORK_WEEK_TEXT.toLowerCase())
                    .withMonthYearReceiptCopyTetx(yearMonthReceiptCopyText)
                    .build();

            /* Create the TimeRecordPDF */
            Path pathToTimeRecordPDF = null;
            try {
                pathToTimeRecordPDF = TimeRecordPDFCreator.createTimeRecordPDF(timeRecord);
                if(pathToTimeRecordPDF == null){
                    Message.errorMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, TimeRecordConstants.TIME_RECORD_PDF_NOT_CREATED);
                    return;
                }
            } catch (IOException | DocumentException e) {
                Message.errorMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, TimeRecordConstants.TIME_RECORD_PDF_NOT_CREATED);
                e.printStackTrace();
            }
            Message.warningMessage(tabPane.getScene().getWindow(),Parameters.SYSTEM_INFORMATION_TEXT, TimeRecordConstants.TIME_RECORD_CREATED_IN + pathToTimeRecordPDF + "\n");

            /* Print the TimeRecord */
            String resultPrint = TimeRecordPDFCreator.printTimeRecord(pathToTimeRecordPDF);
            if(resultPrint.equals("ok")) {
                Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, TimeRecordConstants.TIME_RECORD_SENT_TO_PRINTER);
            }else{
                Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, TimeRecordConstants.NO_PRINTER_FOR_THESE_ATTRIBUTES);
            }
//        }
    }

    private Boolean verifyDocumentStatus(String attachedFileName) {

        if (ApplicationConstants.OPERATING_SYSTEM.contains(ApplicationConstants.OS_LINUX)) {

            return SystemProcesses.isRunningInLinuxAndContains(attachedFileName.substring(0, 40), attachedFileName.substring(41, 60));
        }

        else return SystemProcesses.isRunningInWindowsAndContains(attachedFileName.substring(0, 40), attachedFileName.substring(41, 60));
    }

    private EmailDataCreationDTO retrieveDateForEmailCreation(Path path, String attachedFileName){

        ClientDTO employerDTO = contractParts.getSelectedEmployer();
        PersonDTO employeeDTO = contractParts.getSelectedEmployee();
        String variationTypeText = EmailConstants.STANDARD_NEW_CONTRACT_TEXT;
        return EmailDataCreationDTO.create()
                .withPath(path)
                .withFileName(attachedFileName)
                .withEmployer(employerDTO)
                .withEmployee(employeeDTO)
                .withVariationTypeText(variationTypeText)
                .build();
    }
}
