package gmoldes.components.contract.new_contract.controllers;

import com.lowagie.text.DocumentException;
import gmoldes.components.ViewLoader;
import gmoldes.components.contract.events.*;
import gmoldes.components.contract.manager.ContractManager;
import gmoldes.components.contract.new_contract.components.*;
import gmoldes.components.contract.new_contract.forms.ContractDataSubfolder;
import gmoldes.components.contract.new_contract.forms.ContractDataToContractAgent;
import gmoldes.components.contract.new_contract.services.NewContractAgentNotificator;
import gmoldes.components.contract.new_contract.services.NewContractDataSubfolderPDFCreator;
import gmoldes.components.contract.new_contract.services.NewContractDataToContractAgentPDFCreator;
import gmoldes.components.contract.new_contract.services.NewContractRecordHistorySubfolderPDFCreator;
import gmoldes.components.timerecord.components.TimeRecordConstants;
import gmoldes.components.timerecord.forms.TimeRecord;
import gmoldes.domain.client.controllers.ClientCCCController;
import gmoldes.domain.client.controllers.ClientController;
import gmoldes.domain.client.dto.ClientCCCDTO;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract.dto.OldContractToSaveDTO;
import gmoldes.domain.contract.dto.ProvisionalContractDataDTO;
import gmoldes.domain.contractjsondata.ContractJsonData;
import gmoldes.domain.person.controllers.PersonController;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.person.dto.StudyDTO;
import gmoldes.domain.person.manager.StudyManager;
import gmoldes.domain.timerecord.service.TimeRecordPDFCreator;
import gmoldes.domain.traceability_contract_documentation.dto.TraceabilityContractDocumentationDTO;
import gmoldes.services.Email.EmailParameters;
import gmoldes.services.Printer;
import gmoldes.utilities.Message;
import gmoldes.utilities.OSUtils;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.Utilities;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.mail.internet.AddressException;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.logging.Logger;

public class NewContractMainController extends VBox {

    private static final Logger logger = Logger.getLogger(NewContractMainController.class.getSimpleName());
    private static final String MAIN_FXML = "/fxml/new_contract/contract_main.fxml";

    private ClientController clientController = new ClientController();
    private PersonController personController = new PersonController();
    private ClientCCCController clientCCCController = new ClientCCCController();

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

        contractParts.setOnSearchEmployers(this::onSearchEmployers);
        contractParts.setOnSearchEmployees(this::onSearchEmployees);
        contractParts.setOnSelectEmployer(this::onSelectEmployer);
        contractParts.setOnSelectEmployee(this::onSelectEmployee);
        contractData.setOnChangeContractDataHoursWorkWeek(this::onChangeContractDataHoursWorkWeek);
        contractSchedule.setOnChangeScheduleDuration(this::onChangeScheduleDuration);

        setTabPaneIcon();

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
            ContractDataToContractAgent contractDataToContractAgent = createContractDataToContractAgent();
            pathOut = retrievePathToContractDataToContractAgentPDF(contractDataToContractAgent);
            String attachedFileName = contractDataToContractAgent.toFileName().concat(".pdf");
            NewContractAgentNotificator agentNotificator = new NewContractAgentNotificator();
            try {
                isSendOk = agentNotificator.sendEmailToContractAgent(pathOut, attachedFileName, this.contractParts);
            } catch (AddressException e) {
                e.printStackTrace();
            }
            if(isSendOk){
                Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, EmailParameters.MAIL_SEND_OK);
                contractHasBeenSentToContractAgent = true;
                contractActionComponents.enableSendMailButton(false);
            }else{
                Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, EmailParameters.MAIL_NOT_SEND_OK);
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

        ContractDataToContractAgent contractDataToContractAgent = createContractDataToContractAgent();
        pathOut = retrievePathToContractDataToContractAgentPDF(contractDataToContractAgent);

        if(Parameters.OPERATING_SYSTEM.toLowerCase().contains(Parameters.OS_LINUX)) {
            try {
                String[] command = {"sh", "-c", "xdg-open " + pathOut};
                pdfViewerProcess = Runtime.getRuntime().exec(command);
            } catch (IOException e) {
                System.out.println("No se ha podido abrir el documento \"" + contractDataToContractAgent.toFileName().concat(".pdf") + "\"");
                e.printStackTrace();
            }
        }else if (Parameters.OPERATING_SYSTEM.toLowerCase().contains("windows")){
            String[] command = {"cmd","/c","start","\"visor\"","\"" + pathOut + "\""};
            try {
                pdfViewerProcess = Runtime.getRuntime().exec(command);
            } catch (IOException e) {
                System.out.println("No se ha podido abrir el documento \"" + contractDataToContractAgent.toFileName().concat(".pdf") + "\"");
                e.printStackTrace();
            }
        }
    }

    private void setStatusRevisionErrors(String statusText) {
        ProvisionalContractDataDTO dataDTO = retrieveProvisionalContractDataDTO();
        dataDTO.setStatus(statusText);
        provisionalContractData.refreshData(dataDTO);
        if (statusText.equals(ContractConstants.REVISION_WITHOUT_ERRORS)) {
            if (Message.confirmationMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractVerifierConstants.QUESTION_SAVE_NEW_CONTRACT)) {
                persistOldContractToSave();
                Integer contractNumber = persistInitialContract();
                persistTraceabilityControlData(contractNumber);
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
        List<ClientDTO> employers = findClientsByNamePattern(pattern);
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
        updateClientCCC(selectedEmployerId);
    }

    private void onSelectEmployee(SelectEmployeeEvent selectEmployeeEvent) {
        PersonDTO selectedEmployee = selectEmployeeEvent.getSelectedEmployee();
        List<PersonDTO> personDTOList = new ArrayList<>();
        personDTOList.add(selectedEmployee);
        contractParts.refreshEmployees(personDTOList);
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

    private List<ClientDTO> findClientsByNamePattern(String pattern) {
        return clientController.findAllActiveClientByNamePatternInAlphabeticalOrder(pattern);
    }

    private List<PersonDTO> findPersonsByNamePatternInAlphabeticalOrder(String pattern) {
        return personController.findAllPersonsByNamePatternInAlphabeticalOrder(pattern);
    }

    private void updateClientCCC(Integer id) {
        List<ClientCCCDTO> clientCCCDTOList = clientCCCController.findAllCCCByClientId(id);
        contractParts.refreshEmployerCCC(clientCCCDTOList);
    }

    private void persistOldContractToSave() {

        LocalDate endOfContractNotice = contractData.getDateTo() == null ? LocalDate.of(9999, 12, 31) : null;

        String quoteAccountCode = contractParts.getSelectedCCC() == null ? "" : contractParts.getSelectedCCC().getCcc_inss();

        OldContractToSaveDTO oldContractToSaveDTO = OldContractToSaveDTO.create()
                .withVariationType(ContractMainControllerConstants.ID_INITIAL_CONTRACT_TYPE_VARIATION)
                .withVariationNumber(0)
                .withClientGMId(contractParts.getSelectedEmployer().getId())
                .withClientGMName(contractParts.getSelectedEmployer().toString())
                .withQuoteAccountCode(quoteAccountCode)
                .withWorkerId(contractParts.getSelectedEmployee().getIdpersona())
                .withWorkerName(contractParts.getSelectedEmployee().toString())
                .withLaborCategory(contractData.getLaborCategory())
                .withWeeklyWorkHours(contractData.getHoursWorkWeek())
                .withDaysOfWeekToWork(contractData.getDaysOfWeekToWork())
                .withFullPartialWorkday(contractData.getFullPartialWorkDay())
                .withContractType(contractData.getContractType().getColloquial())
                .withDateFrom(contractData.getDateFrom())
                .withDateTo(contractData.getDateTo())
                .withContractInForce(contractData.isContractInForceAtDate(LocalDate.now()))
                .withNotesForManager(contractPublicNotes.getPublicNotes())
                .withPrivateNotes(contractPrivateNotes.getPrivateNotes())
                .withQuoteDataReportIDC(null)
                .withEndOfContractNotice(endOfContractNotice)
                .build();

        ContractManager contractManager = new ContractManager();
        Integer contractNumber = contractManager.saveOldContract(oldContractToSaveDTO);
        if (contractNumber != null) {
            blockingInterfaceAfterContractPersistence(contractNumber);
            verifyPrintTimeRecord();
            printSubfoldersOfTheContract(Integer.parseInt(provisionalContractData.getContractNumber()));

        }else{
            Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractMainControllerConstants.CONTRACT_NOT_SAVED_OK);
        }
    }

    private Integer persistInitialContract(){

        String quoteAccountCode = contractParts.getSelectedCCC() == null ? "" : contractParts.getSelectedCCC().getCcc_inss();

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
                .withClientGMId(contractParts.getSelectedEmployer().getId())
                .build();

        ContractNewVersionDTO initialContractDTO = ContractNewVersionDTO.create()
                .withVariationType(ContractMainControllerConstants.ID_INITIAL_CONTRACT_TYPE_VARIATION)
                .withStartDate(contractData.getDateFrom())
                .withExpectedEndDate(contractData.getDateTo())
                .withModificationDate(null)
                .withEndingDate(null)
                .withContractJsonData(contractJsonData)
                .build();

        ContractManager contractManager = new ContractManager();
        Integer contractNumber = contractManager.saveInitialContract(initialContractDTO);
        if (contractNumber != null) {
//            blockingInterfaceAfterContractPersistence(contractNumber);
//            verifyPrintTimeRecord();
//            printSubfoldersOfTheContract(Integer.parseInt(provisionalContractData.getContractNumber()));

        }else{
            Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractMainControllerConstants.CONTRACT_NOT_SAVED_OK);
        }

        return contractNumber;
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

        ContractManager contractManager = new ContractManager();
        Integer id = contractManager.saveContractTraceability(traceabilityDTO);

        if(id == null){

            Message.warningMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.ERROR_PERSISTING_TRACEABILITY_CONTROL_DATA);
        }

    }

    private void blockingInterfaceAfterContractPersistence(Integer contractNumber){
        contractHasBeenSavedInDatabase = true;
        provisionalContractData.setContractText(Parameters.NEW_CONTRACT_TEXT + " nº");
        provisionalContractData.setContractNumber(contractNumber);
        contractParts.setMouseTransparent(true);
        contractData.setMouseTransparent(true);
        contractSchedule.setMouseTransparent(true);
        contractPublicNotes.setMouseTransparent(true);
        contractPrivateNotes.setMouseTransparent(true);

        Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractMainControllerConstants.CONTRACT_SAVED_OK + contractNumber);

        contractActionComponents.enableOkButton(false);
        contractActionComponents.enableSendMailButton(true);

    }

    private ContractDataToContractAgent createContractDataToContractAgent(){

        SimpleDateFormat dateFormatter = new SimpleDateFormat(Parameters.DEFAULT_DATE_FORMAT);

        String quoteAccountCode = null;

        if(contractParts.getSelectedCCC() == null){
            quoteAccountCode = "";
        }else{
            quoteAccountCode = contractParts.getSelectedCCC().getCcc_inss();
        }

        Integer studyId = Integer.parseInt(this.contractParts.getSelectedEmployee().getNivestud().toString());
        StudyManager studyManager = new StudyManager();
        StudyDTO studyDTO = studyManager.findStudyById(studyId);
        String employeeMaximumStudyLevel = studyDTO.getStudyDescription();

        String contractTypeDescription = this.contractData.getContractType().getColloquial();
        if(this.contractData.getUndefinedTemporalContract().equals(ContractConstants.UNDEFINED_DURATION_TEXT)){
            contractTypeDescription = contractTypeDescription + ", " + ContractConstants.UNDEFINED_DURATION_TEXT;
        }else{
            contractTypeDescription = contractTypeDescription + ", " + ContractConstants.TEMPORAL_DURATION_TEXT;
        }

        if(this.contractData.getFullPartialWorkDay().equals(ContractConstants.FULL_WORKDAY)){
            contractTypeDescription = contractTypeDescription + ", " + ContractConstants.FULL_WORKDAY;
        }else{
            contractTypeDescription = contractTypeDescription + ", " + ContractConstants.PARTIAL_WORKDAY;
            contractTypeDescription = contractTypeDescription + " [" + contractData.getHoursWorkWeek() + ContractConstants.HOURS_WORK_WEEK_TEXT.toLowerCase() +  "]";
        }

        Duration contractDurationDays = Duration.ZERO;
        if(this.contractData.getContractDurationDays() != null){
            contractDurationDays = Duration.parse("P" + this.contractData.getContractDurationDays() + "D");
        }

        Set<WorkDaySchedule> schedule = this.contractSchedule.retrieveScheduleWithScheduleDays();

        return ContractDataToContractAgent.create()
                .withNotificationType(Parameters.NEW_CONTRACT_TEXT)
                .withOfficialContractNumber(null)
                .withEmployerFullName(this.contractParts.getSelectedEmployer().toString())
                .withEmployerQuoteAccountCode(quoteAccountCode)
                .withNotificationDate(this.contractData.getDateNotification().getDate())
                .withNotificationHour(LocalTime.parse(contractData.getHourNotification().getText()))
                .withEmployeeFullName(this.contractParts.getSelectedEmployee().toString())
                .withEmployeeNif(Utilities.formatAsNIF(this.contractParts.getSelectedEmployee().getNifcif()))
                .withEmployeeNASS(this.contractParts.getSelectedEmployee().getNumafss())
                .withEmployeeBirthDate(dateFormatter.format(this.contractParts.getSelectedEmployee().getFechanacim()))
                .withEmployeeCivilState(this.contractParts.getSelectedEmployee().getEstciv())
                .withEmployeeNationality(this.contractParts.getSelectedEmployee().getNacionalidad())
                .withEmployeeFullAddress(this.contractParts.getSelectedEmployee().getDireccion() + "  " + this.contractParts.getSelectedEmployee().getCodpostal()
                        + " " + this.contractParts.getSelectedEmployee().getLocalidad())
                .withEmployeeMaxStudyLevel(employeeMaximumStudyLevel)
                .withDayOfWeekSet(this.contractData.getDaysOfWeekToWork())
                .withContractTypeDescription(contractTypeDescription)
                .withStartDate(this.contractData.getDateFrom())
                .withEndDate(this.contractData.getDateTo())
                .withDurationDays(contractDurationDays)
                .withSchedule(schedule)
                .withAdditionalData(this.contractPublicNotes.getPublicNotes())
                .withLaborCategory(this.contractData.getLaborCategory())
                .build();
    }

    private ContractDataSubfolder createContractDataSubfolder(Integer contractNumber){

        SimpleDateFormat dateFormatter = new SimpleDateFormat(Parameters.DEFAULT_DATE_FORMAT);

        String quoteAccountCode = null;

        if(contractParts.getSelectedCCC() == null){
            quoteAccountCode = "";
        }else{
            quoteAccountCode = contractParts.getSelectedCCC().getCcc_inss();
        }

        Integer studyId = Integer.parseInt(this.contractParts.getSelectedEmployee().getNivestud().toString());
        StudyManager studyManager = new StudyManager();
        StudyDTO studyDTO = studyManager.findStudyById(studyId);
        String employeeMaximumStudyLevel = studyDTO.getStudyDescription();

        String contractTypeDescription = this.contractData.getContractType().getColloquial();
        if(this.contractData.getUndefinedTemporalContract().equals(ContractConstants.UNDEFINED_DURATION_TEXT)){
            contractTypeDescription = contractTypeDescription + ", " + ContractConstants.UNDEFINED_DURATION_TEXT;
        }else{
            contractTypeDescription = contractTypeDescription + ", " + ContractConstants.TEMPORAL_DURATION_TEXT;
        }

        if(this.contractData.getFullPartialWorkDay().equals(ContractConstants.FULL_WORKDAY)){
            contractTypeDescription = contractTypeDescription + ", " + ContractConstants.FULL_WORKDAY;
        }else{
            contractTypeDescription = contractTypeDescription + ", " + ContractConstants.PARTIAL_WORKDAY +
            " [" + contractData.getHoursWorkWeek() + ContractConstants.HOURS_WORK_WEEK_TEXT.toLowerCase() + "]";
        }

        Duration contractDurationDays = Duration.ZERO;
        if(this.contractData.getContractDurationDays() != null){
            contractDurationDays = Duration.parse("P" + this.contractData.getContractDurationDays() + "D");
        }

        Set<WorkDaySchedule> schedule = this.contractSchedule.retrieveScheduleWithScheduleDays();

        return ContractDataSubfolder.create()
                .withNotificationType(Parameters.NEW_CONTRACT_TEXT)
                .withOfficialContractNumber(null)
                .withEmployerFullName(this.contractParts.getSelectedEmployer().toString())
                .withEmployerQuoteAccountCode(quoteAccountCode)
                .withNotificationDate(this.contractData.getDateNotification().getDate())
                .withNotificationHour(LocalTime.parse(contractData.getHourNotification().getText()))
                .withEmployeeFullName(this.contractParts.getSelectedEmployee().toString())
                .withEmployeeNif(Utilities.formatAsNIF(this.contractParts.getSelectedEmployee().getNifcif()))
                .withEmployeeNASS(this.contractParts.getSelectedEmployee().getNumafss())
                .withEmployeeBirthDate(dateFormatter.format(this.contractParts.getSelectedEmployee().getFechanacim()))
                .withEmployeeCivilState(this.contractParts.getSelectedEmployee().getEstciv())
                .withEmployeeNationality(this.contractParts.getSelectedEmployee().getNacionalidad())
                .withEmployeeFullAddress(this.contractParts.getSelectedEmployee().getDireccion() + "  " + this.contractParts.getSelectedEmployee().getCodpostal()
                + " " + this.contractParts.getSelectedEmployee().getLocalidad())
                .withEmployeeMaxStudyLevel(employeeMaximumStudyLevel)
                .withDayOfWeekSet(this.contractData.getDaysOfWeekToWork())
                .withHoursWorkWeek(Utilities.converterTimeStringToDuration(this.contractData.getHoursWorkWeek()))
                .withContractTypeDescription(contractTypeDescription)
                .withStartDate(this.contractData.getDateFrom())
                .withEndDate(this.contractData.getDateTo())
                .withDurationDays(contractDurationDays)
                .withSchedule(schedule)
                .withAdditionalData(this.contractPublicNotes.getPublicNotes())
                .withLaborCategory(this.contractData.getLaborCategory())
                .withGmContractNumber(contractNumber.toString() + " - 0")
                .build();
    }

    private Path retrievePathToContractDataToContractAgentPDF(ContractDataToContractAgent contractDataToContractAgent){
        Path pathOut = null;

        final Optional<Path> maybePath = OSUtils.TemporalFolderUtils.tempFolder();
        String temporalDir = maybePath.get().toString();

        Path pathToContractDataToContractAgent = Paths.get(Parameters.USER_HOME, temporalDir, contractDataToContractAgent.toFileName().concat(".pdf"));
        try {
            Files.createDirectories(pathToContractDataToContractAgent.getParent());
            pathOut = NewContractDataToContractAgentPDFCreator.createContractDataToContractAgentPDF(contractDataToContractAgent, pathToContractDataToContractAgent);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }

        return pathOut;
    }

    private Path retrievePathToContractDataSubfolderPDF(ContractDataSubfolder contractDataSubfolder){
        Path pathOut = null;

        final Optional<Path> maybePath = OSUtils.TemporalFolderUtils.tempFolder();
        String temporalDir = maybePath.get().toString();

        Path pathToContractDataSubfolder = Paths.get(Parameters.USER_HOME, temporalDir, contractDataSubfolder.toFileName().concat(Parameters.PDF_EXTENSION));
        try {
            Files.createDirectories(pathToContractDataSubfolder.getParent());
            pathOut = NewContractDataSubfolderPDFCreator.createContractDataSubfolderPDF(contractDataSubfolder, pathToContractDataSubfolder);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }

        return pathOut;
    }

    private Path retrievePathToContractRecordHistorySubfolderPDF(ContractDataSubfolder contractDataSubfolder){
        Path pathOut = null;

        final Optional<Path> maybePath = OSUtils.TemporalFolderUtils.tempFolder();
        String temporalDir = maybePath.get().toString();

        String fileName = ContractConstants.CONTRACT_SUBFOLDER_RECORD_HISTORY_TEXT + Utilities.replaceWithUnderscore(contractDataSubfolder.getEmployeeFullName());

        Path pathToContractRecordHistorySubfolder = Paths.get(Parameters.USER_HOME, temporalDir, fileName.concat(Parameters.PDF_EXTENSION));
        try {
            Files.createDirectories(pathToContractRecordHistorySubfolder.getParent());
            pathOut = NewContractRecordHistorySubfolderPDFCreator.createContractRecordHistorySubfolderPDF(contractDataSubfolder, pathToContractRecordHistorySubfolder);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }

        return pathOut;
    }

    private void verifyPrintTimeRecord(){
        if(this.contractData.getFullPartialWorkDay().equals(ContractConstants.PARTIAL_WORKDAY)){

            String quoteAccountCode = contractParts.getSelectedCCC() == null ? "" : contractParts.getSelectedCCC().getCcc_inss();

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
        }
    }

    private void printSubfoldersOfTheContract(Integer contractNumber){
        /** Contract data subfolder */
        ContractDataSubfolder contractDataSubfolder = createContractDataSubfolder(contractNumber);
        Path pathToContractDataSubfolder = retrievePathToContractDataSubfolderPDF(contractDataSubfolder);

        Map<String, String> attributes = new HashMap<>();
        attributes.put("papersize","A3");
        attributes.put("sides", "ONE_SIDED");
        attributes.put("chromacity","MONOCHROME");
        attributes.put("orientation","LANDSCAPE");

        try {
            String printOk = Printer.printPDF(pathToContractDataSubfolder.toString(), attributes);
            Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.CONTRACT_DATA_SUBFOLFER_TO_PRINTER_OK);
            if(!printOk.equals("ok")){
                Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, Parameters.NO_PRINTER_FOR_THESE_ATTRIBUTES);
            }
        } catch (IOException | PrinterException e) {
            e.printStackTrace();
        }

        /** Subfolder record of contract history */
        Path pathToContractRecordHistorySubfolder = retrievePathToContractRecordHistorySubfolderPDF(contractDataSubfolder);

        try {
            String printOk = Printer.printPDF(pathToContractRecordHistorySubfolder.toString(), attributes);
            Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.SUBFOLFER_RECORD_OF_CONTRACT_HISTORY_TO_PRINTER_OK);
            if(!printOk.equals("ok")){
                Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, Parameters.NO_PRINTER_FOR_THESE_ATTRIBUTES);
            }
        } catch (IOException | PrinterException e) {
            e.printStackTrace();
        }
    }

//    private Integer mapContractTypeStringToInteger(String contractType){
//        Integer thisContractType = 999;
//
//        if(contractType.contains("Normal")){
//            thisContractType = 1;
//        }
//        if(contractType.contains("Eventual")){
//            thisContractType = 3;
//        }
//        if(contractType.contains("Obra")){
//            thisContractType = 4;
//        }
//        if(contractType.contains("Formación")){
//            thisContractType = 5;
//        }
//        if(contractType.contains("Prácticas")){
//            thisContractType = 6;
//        }
//        if(contractType.contains("Subrogación")){
//            thisContractType = 7;
//        }
//        if(contractType.contains("Socio")){
//            thisContractType = 8;
//        }
//        if(contractType.contains("Administrador")){
//            thisContractType = 9;
//        }
//        if(contractType.contains("relevo")){
//            thisContractType = 10;
//        }
//        if(contractType.contains("embarazo")){
//            thisContractType = 11;
//        }
//        if(contractType.contains("maternidad")){
//            thisContractType = 12;
//        }
//        if(contractType.contains("Conversión")){
//            thisContractType = 13;
//        }
//        if(contractType.contains("baja laboral")){
//            thisContractType = 14;
//        }
//        if(contractType.contains("vacaciones")){
//            thisContractType = 15;
//        }
//        if(contractType.contains("discontinuo")){
//            thisContractType = 16;
//        }
//        if(contractType.contains("excedencia")){
//            thisContractType = 17;
//        }
//
//        return thisContractType;
//    }
}
