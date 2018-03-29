package gmoldes.controllers;

import com.lowagie.text.DocumentException;
import gmoldes.components.ViewLoader;
import gmoldes.components.contract.events.*;
import gmoldes.components.contract.new_contract.*;
import gmoldes.domain.dto.*;
import gmoldes.forms.ContractDataSubfolder;
import gmoldes.forms.WorkDaySchedule;
import gmoldes.manager.ContractManager;
import gmoldes.manager.StudyManager;
import gmoldes.services.ContractAgentNotificator;
import gmoldes.services.ContractDataSubfolderPDFCreator;
import gmoldes.utilities.EmailParameters;
import gmoldes.utilities.Message;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.Utilities;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import javax.mail.internet.AddressException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.logging.Logger;

public class NewContractMainController extends VBox {

    private static final Logger logger = Logger.getLogger(NewContractMainController.class.getSimpleName());
    private static final String MAIN_FXML = "/fxml/new_contract/contract_main.fxml";

    private static final String OPERATING_SYSTEM = System.getProperty("os.name");
    private static final String USER_HOME = System.getProperty("user.home");
    private static final String WINDOWS_TEMPORAL_DIR = "/AppData/Local/Temp/Borrame";
    private static final String LINUX_TEMPORAL_DIR = "/Temp/Borrame";

    private ClientController clientController = new ClientController();
    private PersonController personController = new PersonController();
    private ClientCCCController clientCCCController = new ClientCCCController();

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
    }

    private void onSendMailButton(MouseEvent event){
        Boolean isSendOk = false;
        String temporalDir = null;
        Path pathOut = null;

        if (Message.confirmationMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, Parameters.QUESTION_SEND_MAIL_TO_CONTRACT_AGENT)) {
            Integer contractNumber = Integer.parseInt(provisionalContractData.getContractNumber());
            ContractDataSubfolder contractDataSubfolder = createContractDataSubfolder(contractNumber);
            if(OPERATING_SYSTEM.toLowerCase().contains("linux")){
                temporalDir = LINUX_TEMPORAL_DIR;
            }
            else if(OPERATING_SYSTEM.toLowerCase().contains("windows")){
                temporalDir = WINDOWS_TEMPORAL_DIR;
            }

            Path pathToContractDataSubfolder = Paths.get(USER_HOME, temporalDir, contractDataSubfolder.toFileName().concat(".pdf"));
            try {
                Files.createDirectories(pathToContractDataSubfolder.getParent());
                pathOut = ContractDataSubfolderPDFCreator.createContractDataSubfolderPDF(contractDataSubfolder, pathToContractDataSubfolder);
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
            }

            String attachedFileName = contractDataSubfolder.toFileName().concat(".pdf");
            ContractAgentNotificator agentNotificator = new ContractAgentNotificator();
            try {
                isSendOk = agentNotificator.sendEmailToContractAgent(pathOut, attachedFileName, this.contractParts);
            } catch (AddressException e) {
                e.printStackTrace();
            }
            if(isSendOk){
                Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, EmailParameters.MAIL_SEND_OK);
                contractActionComponents.enableSendMailButton(false);
            }else{
                Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, EmailParameters.MAIL_NOT_SEND_OK);
            }
        }
    }

    private void onOkButton(MouseEvent event) {

        if (!NewContractDataVerifier.verifyContractParts(contractParts, tabPane)) {
            setStatusRevisionErrors(Parameters.REVISION_WITH_ERRORS);
            return;
        }

        if (!NewContractDataVerifier.verifyContractData(contractData, contractSchedule, tabPane)) {
            setStatusRevisionErrors(Parameters.REVISION_WITH_ERRORS);
            return;
        }

        if (!NewContractDataVerifier.verifyContractSchedule(contractData, contractSchedule, tabPane)) {
            setStatusRevisionErrors(Parameters.REVISION_WITH_ERRORS);
            return;
        }

        setStatusRevisionErrors(Parameters.REVISION_WITHOUT_ERRORS);
    }

    private void onViewPDFButton(MouseEvent mouseEvent) {
        if (OPERATING_SYSTEM.toLowerCase().contains("linux")) {
            final String documentPath = USER_HOME  + "/Intellij/gmoldes/src/main/resources/pdf_forms/DGM_003_Datos_Alta_o_Cambio_Contrato_Trabajo_A3_LO_Version2.pdf";
            try {
                String[] command = {"sh", "-c", "xdg-open " + documentPath};
                Process p = Runtime.getRuntime().exec(command);
            } catch (IOException e) {
                System.out.println("No se ha podido abrir el documento \"" + documentPath + "\"");
            }
        }
    }

    private void setStatusRevisionErrors(String statusText) {
        ProvisionalContractDataDTO dataDTO = retrieveProvisionalContractDataDTO();
        dataDTO.setStatus(statusText);
        provisionalContractData.refreshData(dataDTO);
        if (statusText.equals(Parameters.REVISION_WITHOUT_ERRORS)) {
            if (Message.confirmationMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, Parameters.QUESTION_SAVE_NEW_CONTRACT)) {
                persistOldContractToSave();
            } else {
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
        List<PersonDTO> employees = findPersonsByNamePattern(pattern);
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
                        Parameters.CONTRACT_HOURS_DIFFERENT_FROM_SCHEDULE_HOURS);
            }
        }
    }

    private void onChangeScheduleDuration(ChangeScheduleDurationEvent event) {
        if (event.getContractScheduleTotalHoursDuration().compareTo(Parameters.LEGAL_MAXIMUM_HOURS_OF_WORK_PER_WEEK) > 0) {
            Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT,
                    Parameters.EXCEEDED_MAXIMUM_LEGAL_WEEKLY_HOURS);
            return;
        }

        Duration duration = Utilities.converterTimeStringToDuration(contractData.getHoursWorkWeek());
        assert duration != null;
        if (event.getContractScheduleTotalHoursDuration().compareTo(duration) > 0) {
            Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT,
                    Parameters.SCHEDULE_HOURS_GREATER_THAN_CONTRACT_HOURS);
        }
    }

    private List<ClientDTO> findClientsByNamePattern(String pattern) {
        return clientController.findAllActiveClientByNamePatternInAlphabeticalOrder(pattern);
    }

    private List<PersonDTO> findPersonsByNamePattern(String pattern) {
        return personController.findAllPersonsByNamePatternInAlphabeticalOrder(pattern);
    }

    private void updateClientCCC(Integer id) {
        List<ClientCCCDTO> clientCCCDTOList = clientCCCController.findAllCCCByClientId(id);
        contractParts.refreshEmployerCCC(clientCCCDTOList);
    }

    private void persistOldContractToSave() {
        ContractDataSubfolder contractDataSubfolder = null;
        LocalDate endOfContractNotice = null;
        if (contractData.getDateTo() == null) {
            endOfContractNotice = LocalDate.of(9999, 12, 31);
        }

        OldContractToSaveDTO oldContractToSaveDTO = OldContractToSaveDTO.create()
                .withVariationType(Parameters.ID_INITIAL_CONTRACT_TYPE_VARIATION)
                .withVariationNumber(0)
                .withClientGMId(contractParts.getSelectedEmployer().getId())
                .withClientGMName(contractParts.getSelectedEmployer().getPersonOrCompanyName())
                .withQuoteAccountCode(contractParts.getSelectedCCC().toString())
                .withWorkerId(contractParts.getSelectedEmployee().getIdpersona())
                .withWorkerName(contractParts.getSelectedEmployee().toString())
                .withLaborCategory(contractData.getLaborCategory())
                .withWeeklyWorkHours(contractData.getHoursWorkWeek())
                .withDaysOfWeekToWork(contractData.getDaysOfWeekToWork())
                .withFullPartialWorkday(contractData.getFullPartialWorkDay())
                .withTypeOfContract(contractData.getContractType().getDescripctto())
                .withDateFrom(contractData.getDateFrom())
                .withDateTo(contractData.getDateTo())
                .withCurrentContract(true)
                .withNotesForManager(contractPublicNotes.getPublicNotes())
                .withPrivateNotes(contractPrivateNotes.getPrivateNotes())
                .withQuoteDataReportIDC(null)
                .withEndOfContractNotice(endOfContractNotice)
                .build();

        ContractManager contractManager = new ContractManager();
        Integer contractNumber = contractManager.saveOldContract(oldContractToSaveDTO);
        if (contractNumber != null) {
            provisionalContractData.setContractText(Parameters.NEW_CONTRACT_TEXT);
            provisionalContractData.setContractNumber(contractNumber);
            contractParts.setMouseTransparent(true);
            contractData.setMouseTransparent(true);
            contractSchedule.setMouseTransparent(true);
            contractPublicNotes.setMouseTransparent(true);
            contractPrivateNotes.setMouseTransparent(true);
            Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, Parameters.CONTRACT_SAVED_OK + contractNumber);
            contractActionComponents.enableOkButton(false);
            contractActionComponents.enableSendMailButton(true);
        }else{
            Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, Parameters.CONTRACT_NOT_SAVED_OK);
        }
        contractActionComponents.enablePDFButton(true);
    }

    private ContractDataSubfolder createContractDataSubfolder(Integer contractNumber){

        SimpleDateFormat dateFormatter = new SimpleDateFormat(Parameters.DEFAULT_DATE_FORMAT);

        Integer studyId = Integer.parseInt(this.contractParts.getSelectedEmployee().getNivestud().toString());
        StudyManager studyManager = new StudyManager();
        StudyDTO studyDTO = studyManager.findStudyById(studyId);
        String employeeMaximumStudyLevel = studyDTO.getStudyDescription();

        String contractTypeDescription = this.contractData.getContractType().getDescripctto();
        if(this.contractData.getUndefinedTemporalContract().equals(Parameters.UNDEFINED_DURATION_TEXT)){
            contractTypeDescription = contractTypeDescription + ", " + Parameters.UNDEFINED_DURATION_TEXT;
        }else{
            contractTypeDescription = contractTypeDescription + ", " + Parameters.TEMPORAL_DURATION_TEXT;
        }

        if(this.contractData.getFullPartialWorkDay().equals(Parameters.FULL_WORKDAY)){
            contractTypeDescription = contractTypeDescription + ", " + Parameters.FULL_WORKDAY;
        }else{
            contractTypeDescription = contractTypeDescription + ", " + Parameters.PARTIAL_WORKDAY;
            contractTypeDescription = contractTypeDescription + " [" + contractData.getHoursWorkWeek() + " horas/semana]";
        }

        Duration contractDurationDays = Duration.ZERO;
        if(this.contractData.getContractDurationDays() != null){
            contractDurationDays = Duration.parse("P" + this.contractData.getContractDurationDays() + "D");
        }



        ObservableList<ContractScheduleDayDTO> tableItemList = contractSchedule.getContractScheduleTableItems();
        Integer selectedRow = 0;
        String dayOfWeek = "";
        LocalDate date = null;
        LocalTime amFrom = null;
        LocalTime amTo = null;
        LocalTime pmFrom = null;
        LocalTime pmTo = null;
        Duration durationHours = null;
        Set<WorkDaySchedule> schedule = new HashSet<>();
        for (ContractScheduleDayDTO dayDTO : tableItemList){
            ContractScheduleDayDTO selectedItemRow = tableItemList.get(selectedRow);
            if(selectedItemRow.getDayOfWeek() != null) {
                dayOfWeek = selectedItemRow.getDayOfWeek();
            }
            if(selectedItemRow.getDate() != null){
                date = selectedItemRow.getDate();
            }
            if(selectedItemRow.getAmFrom() != null){
                amFrom = selectedItemRow.getAmFrom();
            }
            if(selectedItemRow.getAmTo() != null){
                amTo = selectedItemRow.getAmTo();
            }
            if(selectedItemRow.getPmFrom() != null){
                pmFrom = selectedItemRow.getPmFrom();
            }
            if(selectedItemRow.getPmTo() != null){
                pmTo = selectedItemRow.getPmTo();
            }
            if(selectedItemRow.getTotalDayHours() != null){
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
            selectedRow++;
        }





        return ContractDataSubfolder.create()
                .withNotificationType(Parameters.NEW_CONTRACT_TEXT)
                .withOfficialContractNumber(null)
                .withEmployerFullName(this.contractParts.getSelectedEmployer().getPersonOrCompanyName())
                .withEmployerQuoteAccountCode(this.contractParts.getSelectedCCC().getCcc_inss())
                .withNotificationDate(this.contractData.getDateNotification())
                .withNotificationHour(LocalTime.parse(contractData.getHourNotification()))
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
                .withGmContractNumber(contractNumber.toString() + " - 0")
                .build();
    }
}
