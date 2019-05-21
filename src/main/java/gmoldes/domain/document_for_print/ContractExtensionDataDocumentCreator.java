package gmoldes.domain.document_for_print;

import com.lowagie.text.DocumentException;
import gmoldes.ApplicationConstants;
import gmoldes.components.contract.ContractConstants;
import gmoldes.components.contract.contract_variation.controllers.ContractVariationMainController;
import gmoldes.components.contract.contract_variation.forms.ContractVariationDataSubfolder;
import gmoldes.components.contract.contract_variation.services.ContractVariationDataSubfolderPDFCreator;
import gmoldes.components.contract.controllers.TypesContractVariationsController;
import gmoldes.components.contract.new_contract.components.ContractParameters;
import gmoldes.components.contract.new_contract.components.WorkDaySchedule;
import gmoldes.components.contract.new_contract.forms.ContractDataToContractsAgent;
import gmoldes.components.contract.new_contract.services.ContractDataToContractAgentPDFCreator;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.client.persistence.vo.ClientCCCVO;
import gmoldes.domain.contract.dto.ContractFullDataDTO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;
import gmoldes.domain.contractjsondata.ContractJsonData;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.study.StudyManager;
import gmoldes.domain.study.dto.StudyDTO;
import gmoldes.utilities.OSUtils;
import gmoldes.utilities.Utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class ContractExtensionDataDocumentCreator {

    private ContractVariationMainController contractVariationMainController;

    public ContractExtensionDataDocumentCreator(ContractVariationMainController contractVariationMainController) {

        this.contractVariationMainController = contractVariationMainController;
    }

    public ContractDataToContractsAgent createContractExtensionDataDocumentForContractsAgent(String publicNotes){

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);
        ContractFullDataDTO contractFullDataDTO = retrieveContractFullData();

        String employeeBirthDate = contractFullDataDTO.getEmployee().getFechanacim() != null ? dateFormatter.format(contractFullDataDTO.getEmployee().getFechanacim()) : "";

        Integer studyId = this.contractVariationMainController.getContractVariationParts().getContractSelector().getValue().getEmployee().getNivestud();
        StudyManager studyManager = new StudyManager();
        StudyDTO studyDTO = studyManager.findStudyById(studyId);
        String employeeMaximumStudyLevel = studyDTO.getStudyDescription();

        String contractTypeDescription = contractFullDataDTO.getContractType().getColloquial() + ", " + contractFullDataDTO.getContractType().getContractDescription();

        String fullPartialWorkDays = contractFullDataDTO.getContractNewVersion().getContractJsonData().getFullPartialWorkDay();
        if(fullPartialWorkDays.equals(ContractConstants.PARTIAL_WORKDAY)){
            contractTypeDescription = contractTypeDescription + " [" + this.contractVariationMainController.getContractVariationParts().getContractSelector()
                    .getValue().getContractNewVersion().getContractJsonData().getWeeklyWorkHours() + ContractConstants.HOURS_WORK_WEEK_TEXT.toLowerCase() +  "]";
        }

        LocalDate startDate = this.contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtension().getDateFrom().getValue();
        LocalDate endDate = this.contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtension().getDateTo().getValue();

        Duration durationDays = Utilities.convertIntegerToDuration(Integer.parseInt(this.contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtension().getContractExtensionDuration().getText()));

        Set<WorkDaySchedule> schedule = null;

        publicNotes = ContractConstants.STANDARD_CONTRACT_EXTENSION_TEXT.concat(". ").concat(publicNotes);

        return ContractDataToContractsAgent.create()
                .withNotificationType(ContractConstants.STANDARD_CONTRACT_EXTENSION_TEXT)
                .withOfficialContractNumber(contractFullDataDTO.getContractNewVersion().getContractJsonData().getIdentificationContractNumberINEM())
                .withEmployerFullName(contractFullDataDTO.getEmployer().toString())
                .withEmployerQuoteAccountCode(contractFullDataDTO.getContractNewVersion().getContractJsonData().getQuoteAccountCode())
                .withNotificationDate(this.contractVariationMainController.getContractVariationTypes().getDateNotification().getDate())
                .withNotificationHour(LocalTime.parse(this.contractVariationMainController.getContractVariationTypes().getHourNotification().getText()))
                .withEmployeeFullName(contractFullDataDTO.getEmployee().toString())
                .withEmployeeNif(Utilities.formatAsNIF(contractFullDataDTO.getEmployee().getNifcif()))
                .withEmployeeNASS(contractFullDataDTO.getEmployee().getNumafss())
                .withEmployeeBirthDate(employeeBirthDate)
                .withEmployeeCivilState(contractFullDataDTO.getEmployee().getEstciv())
                .withEmployeeNationality(contractFullDataDTO.getEmployee().getNacionalidad())
                .withEmployeeFullAddress(contractFullDataDTO.getEmployee().getDireccion() + "  " + contractFullDataDTO.getEmployee().getCodpostal()
                        + " " + contractFullDataDTO.getEmployee().getLocalidad())
                .withEmployeeMaxStudyLevel(employeeMaximumStudyLevel)
                .withDayOfWeekSet(retrieveDayOfWeekSet(contractFullDataDTO.getContractNewVersion().getContractJsonData().getDaysOfWeekToWork()))
                .withContractTypeDescription(contractTypeDescription)
                .withStartDate(startDate)
                .withEndDate(endDate)
                .withDurationDays(durationDays)
                .withSchedule(schedule)
                .withAdditionalData(publicNotes)
                .withLaborCategory(contractFullDataDTO.getContractNewVersion().getContractJsonData().getLaborCategory())
                .build();
    }

    private ContractFullDataDTO retrieveContractFullData(){

        Set<ClientCCCVO> quoteAccountCodeSet = this.contractVariationMainController.getContractVariationParts().getClientSelector().getValue().getClientCCC();

        String quoteAccountCode = null;
        if(!quoteAccountCodeSet.isEmpty()){
            Iterator<ClientCCCVO> it = quoteAccountCodeSet.iterator();
            while(it.hasNext()){
                quoteAccountCode = it.next().getCcc_inss();
            }
        }

        String identificationContractNumberINEM =
                this.contractVariationMainController.getContractVariationParts().getContractSelector().getValue().getContractNewVersion()
                        .getContractJsonData().getIdentificationContractNumberINEM() == null ? "" :
                        this.contractVariationMainController.getContractVariationParts().getContractSelector().getValue().getContractNewVersion()
                                .getContractJsonData().getIdentificationContractNumberINEM();

        String daysOfWeekToWork = this.contractVariationMainController.getContractVariationParts().getContractSelector().getValue().getContractNewVersion().getContractJsonData().getDaysOfWeekToWork();
        String weeklyWorkHours = this.contractVariationMainController.getContractVariationParts().getContractSelector().getValue().getContractNewVersion().getContractJsonData().getWeeklyWorkHours();
        String publicNotes = this.contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtension().getPublicNotes().getText();
        String laborCategory = this.contractVariationMainController.getContractVariationParts().getContractSelector().getValue().getContractNewVersion().getContractJsonData().getLaborCategory();
        Integer contractType = this.contractVariationMainController.getContractVariationParts().getContractSelector().getValue().getContractNewVersion().getContractJsonData().getContractType();
        String fullPartialWorkDay = this.contractVariationMainController.getContractVariationParts().getContractSelector().getValue().getContractNewVersion().getContractJsonData().getFullPartialWorkDay();
        Integer workerId = this.contractVariationMainController.getContractVariationParts().getContractSelector().getValue().getContractNewVersion().getContractJsonData().getWorkerId();
        Integer clientGMId = this.contractVariationMainController.getContractVariationParts().getContractSelector().getValue().getContractNewVersion().getContractJsonData().getClientGMId();

        ContractJsonData contractJsonData = ContractJsonData.create()
                .withIdentificationContractNumberINEM(identificationContractNumberINEM)
                .withDaysOfWeekToWork(daysOfWeekToWork)
                .withWeeklyWorkHours(weeklyWorkHours)
                .withNotesForContractManager(publicNotes)
                .withPrivateNotes(null)
                .withLaborCategory(laborCategory)
                .withContractType(contractType)
                .withFullPartialWorkDay(fullPartialWorkDay)
                .withWorkerId(workerId)
                .withQuoteAccountCode(quoteAccountCode)
                .withClientGMId(clientGMId)
                .build();

        LocalDate startDate = this.contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtension()
                .getDateFrom().getValue();



        ContractNewVersionDTO initialContractDTO = ContractNewVersionDTO.create()
                .withVariationType(ContractParameters.CONTRACT_EXTENSION_ID)
                .withStartDate(startDate)
                .withExpectedEndDate(null)
                .withModificationDate(startDate)
                .withEndingDate(startDate)
                .withContractJsonData(contractJsonData)
                .build();

        TypesContractVariationsController typesContractVariationsController = new TypesContractVariationsController();
        TypesContractVariationsDTO typesContractVariationsDTO = typesContractVariationsController.findTypesContractVariationsById(ContractParameters.CONTRACT_EXTENSION_ID);

        ClientDTO employer = this.contractVariationMainController.getContractVariationParts().getClientSelector().getValue();
        PersonDTO employee = this.contractVariationMainController.getContractVariationParts().getContractSelector().getValue().getEmployee();
        LocalDate initialContractDate = this.contractVariationMainController.getContractVariationParts().getContractSelector().getValue().getInitialContractDate();

        return ContractFullDataDTO.create()
                .withEmployer(employer)
                .withEmployee(employee)
                .withInitialContractDate(initialContractDate)
                .withContractNewVersionDTO(initialContractDTO)
                .withContractType(this.contractVariationMainController.getContractVariationParts().getContractSelector().getValue().getContractType())
                .withTypesContractVariationsDTO(typesContractVariationsDTO)
                .build();
    }

    private Set<DayOfWeek> retrieveDayOfWeekSet(String daysOfWeek){

        Set<DayOfWeek> dayOfWeekSet = new HashSet<>();

        if(daysOfWeek.contains("MONDAY")){
            dayOfWeekSet.add(DayOfWeek.MONDAY);
        }

        if(daysOfWeek.contains("TUESDAY")){
            dayOfWeekSet.add(DayOfWeek.TUESDAY);
        }

        if(daysOfWeek.contains("WEDNESDAY")){
            dayOfWeekSet.add(DayOfWeek.WEDNESDAY);
        }


        if(daysOfWeek.contains("THURSDAY")){
            dayOfWeekSet.add(DayOfWeek.THURSDAY);
        }


        if(daysOfWeek.contains("FRIDAY")){
            dayOfWeekSet.add(DayOfWeek.FRIDAY);
        }

        if(daysOfWeek.contains("SATURDAY")){
            dayOfWeekSet.add(DayOfWeek.SATURDAY);
        }

        if(daysOfWeek.contains("SUNDAY")){
            dayOfWeekSet.add(DayOfWeek.SUNDAY);
        }

        return dayOfWeekSet;
    }

    public Path retrievePathToContractExtensionDataSubfolderPDF(ContractVariationDataSubfolder contractExtensionDataSubfolder){
        Path pathOut = null;

        final Optional<Path> maybePath = OSUtils.TemporalFolderUtils.tempFolder();
        String temporalDir = maybePath.get().toString();

        Path pathToContractDataSubfolder = Paths.get(ApplicationConstants.USER_HOME, temporalDir, contractExtensionDataSubfolder.toFileName().concat(ApplicationConstants.PDF_EXTENSION));
        try {
            Files.createDirectories(pathToContractDataSubfolder.getParent());
            pathOut = ContractVariationDataSubfolderPDFCreator.createContractVariationDataSubfolderPDF(contractExtensionDataSubfolder, pathToContractDataSubfolder);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }

        return pathOut;
    }

    public Path retrievePathToContractDataToContractAgentPDF(ContractDataToContractsAgent contractDataToContractsAgent){
        Path pathOut = null;

        final Optional<Path> maybePath = OSUtils.TemporalFolderUtils.tempFolder();
        String temporalDir = maybePath.get().toString();

        Path pathToContractDataToContractAgent = Paths.get(ApplicationConstants.USER_HOME, temporalDir, contractDataToContractsAgent.toFileName().concat("_gst.pdf"));
        try {
            Files.createDirectories(pathToContractDataToContractAgent.getParent());
            pathOut = ContractDataToContractAgentPDFCreator.createContractDataToContractAgentPDF(contractDataToContractsAgent, pathToContractDataToContractAgent);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }

        return pathOut;
    }
}
