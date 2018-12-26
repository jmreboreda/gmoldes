package gmoldes.domain.document_for_print;

import com.lowagie.text.DocumentException;
import gmoldes.components.contract.contract_variation.controllers.ContractVariationMainController;
import gmoldes.components.contract.contract_variation.forms.ContractExtinctionDataSubfolder;
import gmoldes.components.contract.contract_variation.services.ContractExtinctionDataSubfolderPDFCreator;
import gmoldes.components.contract.controllers.TypesContractVariationsController;
import gmoldes.components.contract.new_contract.components.ContractConstants;
import gmoldes.components.contract.new_contract.components.WorkDaySchedule;
import gmoldes.components.contract.new_contract.forms.ContractDataToContractsAgent;
import gmoldes.components.contract.new_contract.services.ContractDataToContractAgentPDFCreator;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.dto.ContractFullDataDTO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;
import gmoldes.domain.contractjsondata.ContractJsonData;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.person.dto.StudyDTO;
import gmoldes.domain.person.manager.StudyManager;
import gmoldes.utilities.OSUtils;
import gmoldes.utilities.Parameters;
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
import java.util.Optional;
import java.util.Set;

public class ContractExtinctionDataDocumentCreator {

    private ContractVariationMainController contractVariationMainController;

    public ContractExtinctionDataDocumentCreator(ContractVariationMainController contractVariationMainController) {

        this.contractVariationMainController = contractVariationMainController;
    }

    public ContractDataToContractsAgent createContractExtinctionDataDocumentForContractsAgent(String publicNotes){

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parameters.DEFAULT_DATE_FORMAT);

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

        Set<WorkDaySchedule> schedule = null;

        return ContractDataToContractsAgent.create()
                .withNotificationType(Parameters.CONTRACT_EXTINCTION_TEXT)
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
                .withStartDate(null)
                .withEndDate(this.contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction().getDateFrom().getValue())
                .withDurationDays(Duration.ZERO)
                .withSchedule(schedule)
                .withAdditionalData(publicNotes)
                .withLaborCategory(contractFullDataDTO.getContractNewVersion().getContractJsonData().getLaborCategory())
                .build();
    }

//    public ContractExtinctionDataSubfolder createContractExtinctionDataSubfolder(){
//
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parameters.DEFAULT_DATE_FORMAT);
//
//        ContractFullDataDTO contractFullDataDTO = this.contractVariationMainController.getContractVariationParts().getContractSelector().getSelectionModel().getSelectedItem();
//
//        String notificationType = Parameters.CONTRACT_EXTINCTION_TEXT;
//
//        String clientNotificationDate = dateFormatter.format(this.contractVariationMainController.getContractVariationTypes().getDateNotification().getDate());
//        String clientNotificationHour = this.contractVariationMainController.getContractVariationTypes().getHourNotification().getText();
//
//        String birthDate = contractFullDataDTO.getEmployee().getFechanacim() != null ? dateFormatter.format(contractFullDataDTO.getEmployee().getFechanacim()) : null;
//
//        String startDate = dateFormatter.format(this.contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction().getDateFrom().getValue());
//
//        String daysOfWeek = contractFullDataDTO.getContractNewVersion().getContractJsonData().getDaysOfWeekToWork();
//        Set<DayOfWeek> dayOfWeekSet = retrieveDayOfWeekSet(daysOfWeek);
//
//        String address = contractFullDataDTO.getEmployee().getDireccion() != null ?  contractFullDataDTO.getEmployee().getDireccion() : "";
//        String codPostal = contractFullDataDTO.getEmployee().getCodpostal() != null ? contractFullDataDTO.getEmployee().getCodpostal().toString() : "";
//        String location = contractFullDataDTO.getEmployee().getLocalidad() != null ? contractFullDataDTO.getEmployee().getLocalidad() : "";
//        String fullAddress = address + "   " + codPostal + "   " + location;
//
//        StudyManager studyManager = new StudyManager();
//        StudyDTO study = studyManager.findStudyById(contractFullDataDTO.getEmployee().getNivestud());
//
//        ContractTypeController contractTypeController = new ContractTypeController();
//        Integer contractTypeId = contractFullDataDTO.getContractNewVersion().getContractJsonData().getContractType();
//        ContractTypeDTO contractTypeDTO = contractTypeController.findContractTypeById(contractTypeId);
//
//        String contractTypeDescription = contractTypeDTO.getColloquial() + ", " + contractFullDataDTO.getContractType().getContractDescription();
//
//        String fullPartialWorkDays = contractFullDataDTO.getContractNewVersion().getContractJsonData().getFullPartialWorkDay();
//        if(fullPartialWorkDays.equals(ContractConstants.PARTIAL_WORKDAY)) {
//            contractTypeDescription = contractTypeDescription + " [" + this.contractVariationMainController.getContractVariationParts().getContractSelector().getValue()
//                    .getContractNewVersion().getContractJsonData().getWeeklyWorkHours() + ContractConstants.HOURS_WORK_WEEK_TEXT.toLowerCase() + "]";
//        }
//
//        String contractNumberGM = contractFullDataDTO.getContractNewVersion().getContractNumber().toString();
//
//        return ContractExtinctionDataSubfolder.create()
//                .withNotificationType(notificationType)
//                .withOfficialContractNumber(contractFullDataDTO.getContractNewVersion().getContractJsonData().getIdentificationContractNumberINEM())
//                .withEmployerFullName(contractFullDataDTO.getEmployer().toString())
//                .withEmployerQuoteAccountCode(contractFullDataDTO.getContractNewVersion().getContractJsonData().getQuoteAccountCode())
//                .withNotificationDate(clientNotificationDate)
//                .withNotificationHour(clientNotificationHour)
//                .withEmployeeFullName(contractFullDataDTO.getEmployee().getApellidos() + ", " + contractFullDataDTO.getEmployee().getNom_rzsoc())
//                .withEmployeeNif(Utilities.formatAsNIF(contractFullDataDTO.getEmployee().getNifcif()))
//                .withEmployeeNASS(contractFullDataDTO.getEmployee().getNumafss())
//                .withEmployeeBirthDate(birthDate)
//                .withEmployeeCivilState(contractFullDataDTO.getEmployee().getEstciv())
//                .withEmployeeNationality(contractFullDataDTO.getEmployee().getNacionalidad())
//                .withEmployeeFullAddress(fullAddress)
//                .withContractTypeDescription(contractTypeDescription)
//                .withEmployeeMaxStudyLevel(study.getStudyDescription())
//                .withStartDate(null)
//                .withEndDate(startDate)
//                .withDayOfWeekSet(dayOfWeekSet)
//                .withDurationDays("")
//                .withSchedule(new HashSet<>())
//                .withAdditionalData(retrievePublicNotes())
//                .withLaborCategory(contractFullDataDTO.getContractNewVersion().getContractJsonData().getLaborCategory())
//                .withGmContractNumber(contractNumberGM)
//                .build();
//    }

    private ContractFullDataDTO retrieveContractFullData(){

        String quoteAccountCode = this.contractVariationMainController.getContractVariationParts().getClientSelector().getValue().getClientCCC() == null ? "" : this.contractVariationMainController.getContractVariationParts().getClientSelector().getValue().getClientCCC().toString();

        String identificationContractNumberINEM =
                this.contractVariationMainController.getContractVariationParts().getContractSelector().getValue().getContractNewVersion()
                        .getContractJsonData().getIdentificationContractNumberINEM() == null ? "" :
                        this.contractVariationMainController.getContractVariationParts().getContractSelector().getValue().getContractNewVersion()
                                .getContractJsonData().getIdentificationContractNumberINEM();

        String daysOfWeekToWork = this.contractVariationMainController.getContractVariationParts().getContractSelector().getValue().getContractNewVersion().getContractJsonData().getDaysOfWeekToWork();
        String weeklyWorkHours = this.contractVariationMainController.getContractVariationParts().getContractSelector().getValue().getContractNewVersion().getContractJsonData().getWeeklyWorkHours();
        String publicNotes = this.contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction().getPublicNotes().getText();
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

        Integer variationTypeId = this.contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction()
                .getExtinctionCauseSelector().getValue().getId_variation();
//        if(contractTypeDTO.getAdminPartnerSimilar()){
//            variationTypeId = ContractParameters.INITIAL_CONTRACT_ADMIN_PARTNER_SIMILAR;
//
//        }else if(contractTypeDTO.getSurrogate()){
//            variationTypeId = ContractParameters.INITIAL_CONTRACT_SURROGATE_CONTRACT;
//        }else{
//            variationTypeId = ContractParameters.ORDINARY_INITIAL_CONTRACT;
//        }
        LocalDate startDate = this.contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction()
                .getDateFrom().getValue();



        ContractNewVersionDTO initialContractDTO = ContractNewVersionDTO.create()
                .withVariationType(variationTypeId)
                .withStartDate(startDate)
                .withExpectedEndDate(null)
                .withModificationDate(startDate)
                .withEndingDate(startDate)
                .withContractJsonData(contractJsonData)
                .build();

        TypesContractVariationsController typesContractVariationsController = new TypesContractVariationsController();
        TypesContractVariationsDTO typesContractVariationsDTO = typesContractVariationsController.findTypesContractVariationsById(variationTypeId);

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

//    private String retrievePublicNotes(){
//
//        String extinctionContractCause = this.contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction()
//                .getExtinctionCauseSelector().getSelectionModel().getSelectedItem().getVariation_description();
//
//        String holidaysUsedText;
//        if(this.contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction()
//                .getRbHolidaysYes().isSelected()){
//            holidaysUsedText = "disfrutadas.";
//        } else if(this.contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction()
//                .getRbHolidaysNo().isSelected()){
//            holidaysUsedText = "no disfrutadas.";
//        }else{
//            holidaysUsedText = "a calcular.";
//        }
//
//        StringBuilder sb = new StringBuilder();
//        sb.append(extinctionContractCause);
//        sb.append(". Vacaciones ");
//        sb.append(holidaysUsedText);
//        sb.append("\n");
//        sb.append(this.contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction().getPublicNotes().getText());
//
//        return sb.toString();
//    }

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

    public Path retrievePathToContractExtinctionDataSubfolderPDF(ContractExtinctionDataSubfolder contractExtinctionDataSubfolder){
        Path pathOut = null;

        final Optional<Path> maybePath = OSUtils.TemporalFolderUtils.tempFolder();
        String temporalDir = maybePath.get().toString();

        Path pathToContractDataSubfolder = Paths.get(Parameters.USER_HOME, temporalDir, contractExtinctionDataSubfolder.toFileName().concat(Parameters.PDF_EXTENSION));
        try {
            Files.createDirectories(pathToContractDataSubfolder.getParent());
            pathOut = ContractExtinctionDataSubfolderPDFCreator.createContractExtinctionDataSubfolderPDF(contractExtinctionDataSubfolder, pathToContractDataSubfolder);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }

        return pathOut;
    }

    public Path retrievePathToContractDataToContractAgentPDF(ContractDataToContractsAgent contractDataToContractsAgent){
        Path pathOut = null;

        final Optional<Path> maybePath = OSUtils.TemporalFolderUtils.tempFolder();
        String temporalDir = maybePath.get().toString();

        Path pathToContractDataToContractAgent = Paths.get(Parameters.USER_HOME, temporalDir, contractDataToContractsAgent.toFileName().concat("_gst.pdf"));
        try {
            Files.createDirectories(pathToContractDataToContractAgent.getParent());
            pathOut = ContractDataToContractAgentPDFCreator.createContractDataToContractAgentPDF(contractDataToContractsAgent, pathToContractDataToContractAgent);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }

        return pathOut;
    }
}
