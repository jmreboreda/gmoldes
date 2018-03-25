package gmoldes.controllers;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract.events.*;
import gmoldes.components.contract.new_contract.*;
import gmoldes.domain.dto.*;
import gmoldes.manager.ContractManager;
import gmoldes.services.EmailSender;
import gmoldes.utilities.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
            final String PROGRAM = "okular" + " ";
            final String documentPath = USER_HOME + "/Intellij/gmoldes/src/main/resources/pdf_forms/DGM_003_Datos_Alta_o_Cambio_Contrato_Trabajo_A3_LO_Versión2.pdf";
            try {
//                Process p = Runtime.getRuntime().exec (PROGRAM + documentPath);
                String[] command = {"sh", "-c", "xdg-open " + documentPath};
                Process p = Runtime.getRuntime().exec(command);
            } catch (IOException e) {
                System.out.println("No se ha podido ejecutar \"" + PROGRAM + documentPath + "\"");
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
            Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT,
                    Parameters.CONTRACT_SAVED_OK + contractNumber);
            contractActionComponents.enableOkButton(false);
        }
        contractActionComponents.enablePDFButton(true);

        if (Message.confirmationMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, Parameters.QUESTION_SEND_MAIL_TO_CONTRACT_AGENT)) {
            Path path = Paths.get("/home/jmrb/Descargas/Calendar.zip");
            try {
                sendEmailToContractAgent(path);
            } catch (AddressException e) {
                e.printStackTrace();
            }
        }

        System.out.println(oldContractToSaveDTO.toString());
    }

    private void sendEmailToContractAgent(Path path) throws AddressException {
        Boolean isSendOk = false;

        EmailData emailData = EmailData.create()
                .withEmailFrom(new InternetAddress(EmailParameters.EMAIL_FROM_TO_SEND_CONTRACT))
                .withEmailTo(new InternetAddress(EmailParameters.EMAIL_TO_SEND_CONTRACT))
                .withEmailDeliveryNotification(new InternetAddress("jmreboreda@gmail.com"))
                .withEmailSubject(EmailParameters.TEXT_NEW_CONTRACT_IN_MAIL_SUBJECT + contractParts.getSelectedEmployee() + " [" + contractParts.getSelectedEmployer() + "]")
                .withEmailMessageText(EmailParameters.STANDARD_TEXT_SEND_CONTRACT)
                .withAttachedPath(path)
                .withAttachedName("Calendar.zip")
                .build();
        EmailSender emailSender = new EmailSender();
        try {
            isSendOk = emailSender.sendEmail(emailData);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        if(isSendOk){
            Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, EmailParameters.MAIL_SEND_OK);
        }else{
            Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, EmailParameters.MAIL_NOT_SEND_OK);
        }
    }
}
