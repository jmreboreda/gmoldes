package gmoldes.domain.document_for_print;

import com.lowagie.text.DocumentException;
import gmoldes.components.contract.controllers.ContractTypeController;
import gmoldes.components.contract.controllers.TypesContractVariationsController;
import gmoldes.components.contract.new_contract.components.ContractConstants;
import gmoldes.components.contract.new_contract.components.ContractParameters;
import gmoldes.components.contract.new_contract.components.WorkDaySchedule;
import gmoldes.components.contract.new_contract.controllers.NewContractMainController;
import gmoldes.components.contract.new_contract.forms.ContractDataSubfolder;
import gmoldes.components.contract.new_contract.forms.ContractDataToContractAgent;
import gmoldes.components.contract.new_contract.services.NewContractDataSubfolderPDFCreator;
import gmoldes.components.contract.new_contract.services.NewContractDataToContractAgentPDFCreator;
import gmoldes.components.contract.new_contract.services.NewContractRecordHistorySubfolderPDFCreator;
import gmoldes.domain.contract.dto.ContractFullDataDTO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract.dto.ContractTypeDTO;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;
import gmoldes.domain.contractjsondata.ContractJsonData;
import gmoldes.domain.person.dto.StudyDTO;
import gmoldes.domain.person.manager.StudyManager;
import gmoldes.services.Printer;
import gmoldes.utilities.Message;
import gmoldes.utilities.OSUtils;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.Utilities;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ContractDataDocumentCreator {

    private NewContractMainController mainController;

    public ContractDataDocumentCreator(NewContractMainController mainController) {
        this.mainController = mainController;
    }

    public ContractDataToContractAgent createInitialContractDataDocumentForContractsAgent(){

        SimpleDateFormat dateFormatter = new SimpleDateFormat(Parameters.DEFAULT_DATE_FORMAT);

        String quoteAccountCode = this.mainController.getContractParts().getSelectedCCC() == null ? "" : mainController.getContractParts().getSelectedCCC().getCccInss();

        Integer studyId = Integer.parseInt(this.mainController.getContractParts().getSelectedEmployee().getNivestud().toString());
        StudyManager studyManager = new StudyManager();
        StudyDTO studyDTO = studyManager.findStudyById(studyId);
        String employeeMaximumStudyLevel = studyDTO.getStudyDescription();


        ContractFullDataDTO contractFullDataDTO = retrieveContractFullData();

        String contractTypeDescription = contractFullDataDTO.getContractType().getColloquial() + ", " + contractFullDataDTO.getContractType().getContractDescription();

        String fullPartialWorkDays = contractFullDataDTO.getContractNewVersion().getContractJsonData().getFullPartialWorkDay();
        if(fullPartialWorkDays.equals(ContractConstants.PARTIAL_WORKDAY)){
            contractTypeDescription = contractTypeDescription + " [" + this.mainController.getContractData().getHoursWorkWeek() + ContractConstants.HOURS_WORK_WEEK_TEXT.toLowerCase() +  "]";
        }

        Duration contractDurationDays = Duration.ZERO;
        if(this.mainController.getContractData().getContractDurationDays() != null){
            contractDurationDays = Duration.parse("P" + this.mainController.getContractData().getContractDurationDays() + "D");
        }

        Set<WorkDaySchedule> schedule = this.mainController.getContractSchedule().retrieveScheduleWithScheduleDays();

        return ContractDataToContractAgent.create()
                .withNotificationType(Parameters.NEW_CONTRACT_TEXT)
                .withOfficialContractNumber(null)
                .withEmployerFullName(this.mainController.getContractParts().getSelectedEmployer().toString())
                .withEmployerQuoteAccountCode(quoteAccountCode)
                .withNotificationDate(this.mainController.getContractData().getDateNotification().getDate())
                .withNotificationHour(LocalTime.parse(this.mainController.getContractData().getHourNotification().getText()))
                .withEmployeeFullName(this.mainController.getContractParts().getSelectedEmployee().toString())
                .withEmployeeNif(Utilities.formatAsNIF(this.mainController.getContractParts().getSelectedEmployee().getNifcif()))
                .withEmployeeNASS(this.mainController.getContractParts().getSelectedEmployee().getNumafss())
                .withEmployeeBirthDate(dateFormatter.format(this.mainController.getContractParts().getSelectedEmployee().getFechanacim()))
                .withEmployeeCivilState(this.mainController.getContractParts().getSelectedEmployee().getEstciv())
                .withEmployeeNationality(this.mainController.getContractParts().getSelectedEmployee().getNacionalidad())
                .withEmployeeFullAddress(this.mainController.getContractParts().getSelectedEmployee().getDireccion() + "  " + this.mainController.getContractParts().getSelectedEmployee().getCodpostal()
                        + " " +this.mainController.getContractParts().getSelectedEmployee().getLocalidad())
                .withEmployeeMaxStudyLevel(employeeMaximumStudyLevel)
                .withDayOfWeekSet(this.mainController.getContractData().getDaysOfWeekToWork())
                .withContractTypeDescription(contractTypeDescription)
                .withStartDate(this.mainController.getContractData().getDateFrom())
                .withEndDate(this.mainController.getContractData().getDateTo())
                .withDurationDays(contractDurationDays)
                .withSchedule(schedule)
                .withAdditionalData(this.mainController.getContractPublicNotes().getPublicNotes())
                .withLaborCategory(this.mainController.getContractData().getLaborCategory())
                .build();
    }


    public void printSubfoldersOfTheInitialContract(){

        ContractFullDataDTO contractFullDataDTO = retrieveContractFullData();
        Integer contractNumber = contractFullDataDTO.getContractNewVersion().getContractNumber();

        /** Contract data subfolder */
        ContractDataSubfolder contractDataSubfolder = createInitialContractDataSubfolder();
        Path pathToContractDataSubfolder = retrievePathToContractDataSubfolderPDF(contractDataSubfolder);

        Map<String, String> attributes = new HashMap<>();
        attributes.put("papersize","A3");
        attributes.put("sides", "ONE_SIDED");
        attributes.put("chromacity","MONOCHROME");
        attributes.put("orientation","LANDSCAPE");

        try {
            String printOk = Printer.printPDF(pathToContractDataSubfolder.toString(), attributes);
            Message.warningMessage(this.mainController.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.CONTRACT_DATA_SUBFOLFER_TO_PRINTER_OK);
            if(!printOk.equals("ok")){
                Message.warningMessage(this.mainController.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, Parameters.NO_PRINTER_FOR_THESE_ATTRIBUTES);
            }
        } catch (IOException | PrinterException e) {
            e.printStackTrace();
        }

        /** Subfolder record of contract history */
        String contractTypeDescription = this.mainController.getContractData().getContractType().getColloquial() + ", " + contractFullDataDTO.getContractType().getContractDescription();

        ContractDataSubfolder contractHistoryDataSubfolder = ContractDataSubfolder.create()
                .withNotificationType(contractDataSubfolder.getNotificationType())
                .withOfficialContractNumber(contractDataSubfolder.getOfficialContractNumber())
                .withEmployerFullName(contractDataSubfolder.getEmployerFullName())
                .withEmployerQuoteAccountCode(contractDataSubfolder.getEmployerQuoteAccountCode())
                .withNotificationDate(contractDataSubfolder.getNotificationDate())
                .withNotificationHour(contractDataSubfolder.getNotificationHour())
                .withEmployeeFullName(contractDataSubfolder.getEmployeeFullName())
                .withEmployeeNif(contractDataSubfolder.getEmployeeNif())
                .withEmployeeNASS(contractDataSubfolder.getEmployeeNASS())
                .withEmployeeBirthDate(contractDataSubfolder.getEmployeeBirthDate())
                .withEmployeeCivilState(contractDataSubfolder.getEmployeeCivilState())
                .withEmployeeNationality(contractDataSubfolder.getEmployeeNationality())
                .withEmployeeFullAddress(contractDataSubfolder.getEmployeeFullAddress())
                .withEmployeeMaxStudyLevel(contractDataSubfolder.getEmployeeMaxStudyLevel())
                .withDayOfWeekSet(contractDataSubfolder.getDayOfWeekSet())
                .withHoursWorkWeek(contractDataSubfolder.getHoursWorkWeek())
                .withContractTypeDescription(contractTypeDescription)
                .withStartDate(contractDataSubfolder.getStartDate())
                .withEndDate(contractDataSubfolder.getEndDate())
                .withDurationDays(contractDataSubfolder.getDurationDays())
                .withSchedule(contractDataSubfolder.getSchedule())
                .withAdditionalData(contractDataSubfolder.getAdditionalData())
                .withLaborCategory(contractDataSubfolder.getLaborCategory())
                .withGmContractNumber(contractDataSubfolder.getGmContractNumber())
                .build();

        Path pathToContractRecordHistorySubfolder = retrievePathToContractRecordHistorySubfolderPDF(contractHistoryDataSubfolder);

        try {
            String printOk = Printer.printPDF(pathToContractRecordHistorySubfolder.toString(), attributes);
            Message.warningMessage(this.mainController.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.SUBFOLFER_RECORD_OF_CONTRACT_HISTORY_TO_PRINTER_OK);
            if(!printOk.equals("ok")){
                Message.warningMessage(this.mainController.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, Parameters.NO_PRINTER_FOR_THESE_ATTRIBUTES);
            }
        } catch (IOException | PrinterException e) {
            e.printStackTrace();
        }
    }

    private ContractDataSubfolder createInitialContractDataSubfolder(){

        ContractFullDataDTO contractFullDataDTO = retrieveContractFullData();

        Integer contractNumber = Integer.parseInt(this.mainController.getProvisionalContractData().getContractNumber());
        SimpleDateFormat dateFormatter = new SimpleDateFormat(Parameters.DEFAULT_DATE_FORMAT);

        String quoteAccountCode = !contractFullDataDTO.getContractNewVersion().getContractJsonData().getQuoteAccountCode().isEmpty() ?
                contractFullDataDTO.getContractNewVersion().getContractJsonData().getQuoteAccountCode() : "";

        Integer studyId = contractFullDataDTO.getEmployee().getNivestud();
        StudyManager studyManager = new StudyManager();
        StudyDTO studyDTO = studyManager.findStudyById(studyId);
        String employeeMaximumStudyLevel = studyDTO.getStudyDescription();

        Integer contractTypeCode = contractFullDataDTO.getContractNewVersion().getContractJsonData().getContractType();
        ContractTypeController contractTypeController = new ContractTypeController();
        ContractTypeDTO contractTypeDTO = contractTypeController.findContractTypeById(contractTypeCode);

        String contractTypeDescription = contractTypeDTO.getColloquial() + ", " + contractFullDataDTO.getContractType().getContractDescription();

        String fullPartialWorkDays = contractFullDataDTO.getContractNewVersion().getContractJsonData().getFullPartialWorkDay();
        if(fullPartialWorkDays.equals(ContractConstants.PARTIAL_WORKDAY)){
            contractTypeDescription = contractTypeDescription + " [" + this.mainController.getContractData().getHoursWorkWeek() + ContractConstants.HOURS_WORK_WEEK_TEXT.toLowerCase() +  "]";
        }

        Duration contractDurationDays = Duration.ZERO;
        if(this.mainController.getContractData().getContractDurationDays() != null){
            contractDurationDays = Duration.parse("P" + this.mainController.getContractData().getContractDurationDays() + "D");
        }

        Set<WorkDaySchedule> schedule = this.mainController.getContractSchedule().retrieveScheduleWithScheduleDays();

        return ContractDataSubfolder.create()
                .withNotificationType(Parameters.NEW_CONTRACT_TEXT)
                .withOfficialContractNumber(null)
                .withEmployerFullName(this.mainController.getContractParts().getSelectedEmployer().toString())
                .withEmployerQuoteAccountCode(quoteAccountCode)
                .withNotificationDate(this.mainController.getContractData().getDateNotification().getDate())
                .withNotificationHour(LocalTime.parse(this.mainController.getContractData().getHourNotification().getText()))
                .withEmployeeFullName(this.mainController.getContractParts().getSelectedEmployee().toString())
                .withEmployeeNif(Utilities.formatAsNIF(this.mainController.getContractParts().getSelectedEmployee().getNifcif()))
                .withEmployeeNASS(this.mainController.getContractParts().getSelectedEmployee().getNumafss())
                .withEmployeeBirthDate(dateFormatter.format(this.mainController.getContractParts().getSelectedEmployee().getFechanacim()))
                .withEmployeeCivilState(this.mainController.getContractParts().getSelectedEmployee().getEstciv())
                .withEmployeeNationality(this.mainController.getContractParts().getSelectedEmployee().getNacionalidad())
                .withEmployeeFullAddress(this.mainController.getContractParts().getSelectedEmployee().getDireccion() + "  " + this.mainController.getContractParts().getSelectedEmployee().getCodpostal()
                        + " " + this.mainController.getContractParts().getSelectedEmployee().getLocalidad())
                .withEmployeeMaxStudyLevel(employeeMaximumStudyLevel)
                .withDayOfWeekSet(this.mainController.getContractData().getDaysOfWeekToWork())
                .withHoursWorkWeek(Utilities.converterTimeStringToDuration(this.mainController.getContractData().getHoursWorkWeek()))
                .withContractTypeDescription(contractTypeDescription)
                .withStartDate(this.mainController.getContractData().getDateFrom())
                .withEndDate(this.mainController.getContractData().getDateTo())
                .withDurationDays(contractDurationDays)
                .withSchedule(schedule)
                .withAdditionalData(this.mainController.getContractPublicNotes().getPublicNotes())
                .withLaborCategory(this.mainController.getContractData().getLaborCategory())
                .withGmContractNumber(contractNumber.toString())
                .build();
    }

    private ContractFullDataDTO retrieveContractFullData(){

        String quoteAccountCode = this.mainController.getContractParts().getSelectedCCC() == null ? "" : this.mainController.getContractParts().getSelectedCCC().toString();

        ContractJsonData contractJsonData = ContractJsonData.create()
                .withIdentificationContractNumberINEM(null)
                .withDaysOfWeekToWork(this.mainController.getContractData().getDaysOfWeekToWork().toString())
                .withWeeklyWorkHours(this.mainController.getContractData().getHoursWorkWeek())
                .withNotesForContractManager(this.mainController.getContractPublicNotes().getPublicNotes())
                .withPrivateNotes(this.mainController.getContractPrivateNotes().getPrivateNotes())
                .withLaborCategory(this.mainController.getContractData().getLaborCategory())
                .withContractType(this.mainController.getContractData().getContractType().getContractCode())
                .withFullPartialWorkDay(this.mainController.getContractData().getFullPartialWorkDay())
                .withWorkerId(this.mainController.getContractParts().getSelectedEmployee().getIdpersona())
                .withQuoteAccountCode(quoteAccountCode)
                .withClientGMId(this.mainController.getContractParts().getSelectedEmployer().getId())
                .build();

        Integer variationTypeId;
        ContractTypeDTO contractTypeDTO = this.mainController.getContractData().getContractType();
        if(contractTypeDTO.getAdminPartnerSimilar()){
            variationTypeId = ContractParameters.INITIAL_CONTRACT_ADMIN_PARTNER_SIMILAR;

        }else if(contractTypeDTO.getSurrogate()){
            variationTypeId = ContractParameters.INITIAL_CONTRACT_SURROGATE_CONTRACT;
        }else{
            variationTypeId = ContractParameters.ORDINARY_INITIAL_CONTRACT;
        }

        ContractNewVersionDTO initialContractDTO = ContractNewVersionDTO.create()
                .withVariationType(variationTypeId)
                .withStartDate(this.mainController.getContractData().getDateFrom())
                .withExpectedEndDate(this.mainController.getContractData().getDateTo())
                .withModificationDate(null)
                .withEndingDate(null)
                .withContractJsonData(contractJsonData)
                .build();

        TypesContractVariationsController typesContractVariationsController = new TypesContractVariationsController();
        TypesContractVariationsDTO typesContractVariationsDTO = typesContractVariationsController.findTypesContractVariationsById(variationTypeId);

        ContractFullDataDTO contractFullDataDTO = ContractFullDataDTO.create()
                .withEmployer(this.mainController.getContractParts().getSelectedEmployer())
                .withEmployee(this.mainController.getContractParts().getSelectedEmployee())
                .withInitialContractDate(this.mainController.getContractData().getDateFrom())
                .withContractNewVersionDTO(initialContractDTO)
                .withContractType(this.mainController.getContractData().getContractType())
                .withTypesContractVariationsDTO(typesContractVariationsDTO)
                .build();

        return contractFullDataDTO;
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

    public Path retrievePathToContractDataToContractAgentPDF(ContractDataToContractAgent contractDataToContractAgent){
        Path pathOut = null;

        final Optional<Path> maybePath = OSUtils.TemporalFolderUtils.tempFolder();
        String temporalDir = maybePath.get().toString();

        Path pathToContractDataToContractAgent = Paths.get(Parameters.USER_HOME, temporalDir, contractDataToContractAgent.toFileName().concat("_gst.pdf"));
        try {
            Files.createDirectories(pathToContractDataToContractAgent.getParent());
            pathOut = NewContractDataToContractAgentPDFCreator.createContractDataToContractAgentPDF(contractDataToContractAgent, pathToContractDataToContractAgent);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }

        return pathOut;
    }
}
