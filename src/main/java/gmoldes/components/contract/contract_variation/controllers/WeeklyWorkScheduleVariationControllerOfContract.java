package gmoldes.components.contract.contract_variation.controllers;

import gmoldes.ApplicationConstants;
import gmoldes.ApplicationMainController;
import gmoldes.components.contract.ContractConstants;
import gmoldes.components.contract.contract_variation.events.CompatibleVariationEvent;
import gmoldes.components.contract.contract_variation.events.ContractVariationPersistenceEvent;
import gmoldes.components.contract.contract_variation.events.MessageContractVariationEvent;
import gmoldes.components.contract.contract_variation.forms.ContractVariationDataSubfolder;
import gmoldes.components.contract.controllers.ContractTypeController;
import gmoldes.components.contract.manager.ContractManager;
import gmoldes.components.contract.new_contract.components.ContractParameters;
import gmoldes.components.contract.new_contract.components.WorkDaySchedule;
import gmoldes.components.contract.new_contract.forms.ContractDataToContractsAgent;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.dto.*;
import gmoldes.domain.contract.mapper.MapperJsonScheduleToWorkDaySchedule;
import gmoldes.domain.contractjsondata.ContractDayScheduleJsonData;
import gmoldes.domain.contractjsondata.ContractJsonData;
import gmoldes.domain.contractjsondata.ContractScheduleJsonData;
import gmoldes.domain.document_for_print.ContractExtensionDataDocumentCreator;
import gmoldes.domain.email.EmailDataCreationDTO;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.study.StudyManager;
import gmoldes.domain.study.dto.StudyDTO;
import gmoldes.domain.traceability_contract_documentation.dto.TraceabilityContractDocumentationDTO;
import gmoldes.services.AgentNotificator;
import gmoldes.services.Printer;
import gmoldes.services.email.EmailConstants;
import gmoldes.utilities.Message;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.SystemProcesses;
import gmoldes.utilities.Utilities;

import javax.mail.internet.AddressException;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class WeeklyWorkScheduleVariationControllerOfContract {


    private ContractManager contractManager = new ContractManager();
    private ContractVariationMainController contractVariationMainController;

    private static final Integer VARIATION_TYPE_ID_FOR_CONTRACT_EXTENSION = 220;
    private static final Integer VARIATION_TYPE_ID_FOR_WEEKLY_WORK_SCHEDULE_VARIATION = 230;

    public WeeklyWorkScheduleVariationControllerOfContract(ContractVariationMainController contractVariationMainController){

        this.contractVariationMainController = contractVariationMainController;
    }


    public MessageContractVariationEvent executeWeeklyWorkDurationVariationOperations(Set<WorkDaySchedule> weeklyWorkScheduleVariation){

        // 1. Verify correct weekly work duration variation data
        MessageContractVariationEvent messageContractVariationEvent = verifyIsCorrectWeeklyWorkDurationVariationData();
        if(!messageContractVariationEvent.getMessageText().equals(ContractConstants.NECESSARY_DATA_FOR_VARIATION_CONTRACT_HAVE_BEEN_INTRODUCED)){

            return messageContractVariationEvent;
        }

        // 2.Verify the notification period to the Labor Administration
        LocalDate effectDateRequestedForContractVariation = contractVariationMainController.getContractVariationContractVariations()
                .getContractVariationWeeklyWorkScheduleDuration().getDateFrom().getValue();

        CompatibleVariationEvent dateAdministrationCompatibleEvent = dateToNotifyContractVariationToAdministrationIsCorrect(effectDateRequestedForContractVariation);
        if(dateAdministrationCompatibleEvent.getErrorContractVariationMessage().equals(ContractConstants.VERIFY_IS_VALID_DATE_TO_NOTIFY_CONTRACT_VARIATION_TO_ADMINISTRATION)){
            Boolean isCorrectDate = Message.confirmationMessage(contractVariationMainController.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT,
                    dateAdministrationCompatibleEvent.getErrorContractVariationMessage());
            if(!isCorrectDate){

                return new MessageContractVariationEvent("", null);
            }
        }

        // 3. Check exist incompatible operations
        CompatibleVariationEvent compatibleVariationEvent = checkExistenceIncompatibleVariationsForWeeklyWorkDurationVariation();
        if(compatibleVariationEvent.getErrorContractVariationMessage() != null){

            return new MessageContractVariationEvent(compatibleVariationEvent.getErrorContractVariationMessage(),null);
        }

        // 4. Persistence question
        if (!Message.confirmationMessage(contractVariationMainController.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.PERSIST_CONTRACT_VARIATION_QUESTION)) {

            return new MessageContractVariationEvent(ContractConstants.CONTRACT_WEEKLY_WORK_SCHEDULE_VARIATION_ABANDONED, null);
        }

        // 5. Persist weekly work schedule variation
        ContractVariationPersistenceEvent persistenceEvent = persistWeeklyWorkScheduleVariation(weeklyWorkScheduleVariation);
        if(!persistenceEvent.getPersistenceIsOk()) {

            return new MessageContractVariationEvent(persistenceEvent.getPersistenceMessage(),null);
        }


        // 6. Persist traceability
        Integer traceabilityContractExtensionId = persistTraceabilityControlData();
        if(traceabilityContractExtensionId == null){

            return new MessageContractVariationEvent(ContractConstants.ERROR_PERSISTING_TRACEABILITY_CONTROL_DATA, null);
        }

        Message.warningMessage(this.contractVariationMainController.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.CONTRACT_EXTENSION_PERSISTENCE_OK);

        // 7. Print documentation
        StringBuilder sb = new StringBuilder();

        String publicNotes = retrievePublicNotes();
        sb.append(publicNotes);

        ContractVariationDataSubfolder contractExtensionDataSubfolder = createContractExtensionDataSubfolder(sb.toString());

        printContractExtensionDataSubfolder(contractExtensionDataSubfolder);

        return new MessageContractVariationEvent(ContractConstants.CONTRACT_EXTENSION_PERSISTENCE_OK,null);
    }

    private MessageContractVariationEvent verifyIsCorrectWeeklyWorkDurationVariationData(){

        if(contractVariationMainController.getContractVariationTypes().getDateNotification().getDate() == null){

            return new MessageContractVariationEvent(ContractConstants.DATE_NOTIFICATION_NOT_ESTABLISHED,null);
        }

        if(contractVariationMainController.getContractVariationTypes().getHourNotification().getText() == null){

            return new MessageContractVariationEvent(ContractConstants.HOUR_NOTIFICATION_NOT_ESTABLISHED, null);
        }

        if(contractVariationMainController.getContractVariationContractVariations().getContractVariationWeeklyWorkScheduleDuration().getDateFrom().getValue() == null){

            return new MessageContractVariationEvent(ContractConstants.ERROR_WEEKLY_WORK_DURATION_VARIATION_DATE_FROM, null);
        }

        // Error. Weekly work schedule variation dateFrom < Date of the last contract variation at current date
        ContractFullDataDTO contractFullDataDTO = contractVariationMainController.getContractVariationParts().getContractSelector().getValue();
        LocalDate weeklyWorkScheduleVariationDateFrom = contractVariationMainController.getContractVariationContractVariations().getContractVariationWeeklyWorkScheduleDuration().getDateFrom().getValue();
        LocalDate dateLastContractVariationToCurrentDate = contractFullDataDTO.getContractNewVersion().getStartDate();
        if(weeklyWorkScheduleVariationDateFrom.compareTo(dateLastContractVariationToCurrentDate) < 0){

            return new MessageContractVariationEvent(ContractConstants.ERROR_WEEKLY_WORK_DURATION_VARIATION_DATE_FROM, null);
        }

        // Error. Weekly work schedule variation dateTo <= Weekly work schedule variation dateFrom
        LocalDate weeklyWorkScheduleVariationDateTo = contractVariationMainController.getContractVariationContractVariations().getContractVariationWeeklyWorkScheduleDuration().getDateTo().getValue();
        if(weeklyWorkScheduleVariationDateTo != null) {
            if (ChronoUnit.DAYS.between(weeklyWorkScheduleVariationDateFrom, weeklyWorkScheduleVariationDateTo) <= 0) {

                return new MessageContractVariationEvent(ContractConstants.ERROR_WEEKLY_WORK_DURATION_VARIATION_INCOHERENT_DATES, null);
            }
        }

        // Error. DateTo is null and the contract has an end date (ExpectedEndDate)
        LocalDate lastContractVariationExpectedEndDate = contractFullDataDTO.getContractNewVersion().getExpectedEndDate();
        if(weeklyWorkScheduleVariationDateTo == null && lastContractVariationExpectedEndDate != null){
            return new MessageContractVariationEvent(ContractConstants.ERROR_EXCEEDED_DURATION_CONTRACT, null);
        }

        // Error. Weekly work schedule variation dateTo > contract expected end date
        if(weeklyWorkScheduleVariationDateTo != null) {
            if(lastContractVariationExpectedEndDate != null){
                if (ChronoUnit.DAYS.between(weeklyWorkScheduleVariationDateTo, lastContractVariationExpectedEndDate) < 0) {
                    return new MessageContractVariationEvent(ContractConstants.ERROR_EXCEEDED_DURATION_CONTRACT, null);
                }
            }
        }

        return new MessageContractVariationEvent(ContractConstants.NECESSARY_DATA_FOR_VARIATION_CONTRACT_HAVE_BEEN_INTRODUCED,null);
    }


    private CompatibleVariationEvent dateToNotifyContractVariationToAdministrationIsCorrect(LocalDate date){

        LocalDate limitDatePreviousOfNotifyToAdministration = contractVariationMainController.getContractVariationParts().getInForceDate().getValue()
                .minusDays(ContractParameters.MAXIMUM_NUMBER_DAYS_OF_DELAY_IN_NOTIFICATIONS_TO_THE_LABOR_ADMINISTRACION);

        if(ChronoUnit.DAYS.between(limitDatePreviousOfNotifyToAdministration, date) >= 0){
            return new CompatibleVariationEvent(
                    false,
                    false,
                    true,
                    false,
                    "");
        }

        return new CompatibleVariationEvent(
                false,
                false,
                true,
                false,
                ContractConstants.VERIFY_IS_VALID_DATE_TO_NOTIFY_CONTRACT_VARIATION_TO_ADMINISTRATION);
    }

    public CompatibleVariationEvent checkExistenceIncompatibleVariationsForWeeklyWorkDurationVariation() {

        ApplicationMainController applicationMainController = new ApplicationMainController();

        Integer contractNumber = contractVariationMainController.getContractVariationParts().getContractSelector().getSelectionModel().getSelectedItem().getContractNewVersion().getContractNumber();
        LocalDate weeklyWorkDurationVariationDateFrom = contractVariationMainController.getContractVariationContractVariations().getContractVariationWeeklyWorkScheduleDuration().getDateFrom().getValue();
        LocalDate weeklyWorkDurationVariationDateTo = contractVariationMainController.getContractVariationContractVariations().getContractVariationWeeklyWorkScheduleDuration().getDateTo().getValue();

//        // 4. An extension of the contract incompatible with the requested one is already registered
//        List<ContractVariationDTO> contractVariationDTOList_2 =  applicationMainController.findAllContractVariationByContractNumber(contractNumber);
//        List<TypesContractVariationsDTO> typesContractVariationsDTOList = applicationMainController.findAllTypesContractVariations();
//        for(ContractVariationDTO contractVariationDTO : contractVariationDTOList_2) {
//            for (TypesContractVariationsDTO typesContractVariationsDTO : typesContractVariationsDTOList) {
//                if (typesContractVariationsDTO.getId_variation().equals(contractVariationDTO.getVariationType()) &&
//                        typesContractVariationsDTO.getExtension() &&
//                        (weeklyWorkDurationVariationDateFrom.isBefore(contractVariationDTO.getExpectedEndDate())) ||
//                        weeklyWorkDurationVariationDateFrom.equals(contractVariationDTO.getExpectedEndDate())){
//
//                    return new CompatibleVariationEvent(
//                            false,
//                            false,
//                            true,
//                            false,
//                            ContractConstants.EXIST_PREVIOUS_INCOMPATIBLE_CONTRACT_VARIATION_EXTENSION);
//                }
//            }
//        }

        return new CompatibleVariationEvent(false, false, true, false, null);
    }


    private ContractVariationPersistenceEvent persistWeeklyWorkScheduleVariation(Set<WorkDaySchedule> weeklyWorkScheduleVariation){

        final ContractNewVersionDTO contractNewVersionToWeeklyWorkScheduleVariationDTO = contractVariationMainController.getContractVariationParts().getContractSelector()
                .getSelectionModel().getSelectedItem().getContractNewVersion();

        if(updateLastContractVariation(contractNewVersionToWeeklyWorkScheduleVariationDTO.getContractNumber()) == null) {

            return new ContractVariationPersistenceEvent(false, ContractConstants.ERROR_UPDATING_LAST_CONTRACT_VARIATION_RECORD);
        }

        if(persistNewWeeklyWorkScheduleVariation(contractNewVersionToWeeklyWorkScheduleVariationDTO, weeklyWorkScheduleVariation) == null) {

            return new ContractVariationPersistenceEvent(false, ContractConstants.ERROR_INSERTING_NEW_WEEKLY_WORK_SCHEDULE_VARIATION);
        }

        if(updateInitialContractOfContractExtension(contractNewVersionToWeeklyWorkScheduleVariationDTO.getContractNumber()) == null) {

            return new ContractVariationPersistenceEvent(false, ContractConstants.ERROR_UPDATING_MODIFICATION_DATE_IN_INITIAL_CONTRACT);
        }

        return new ContractVariationPersistenceEvent(true, ContractConstants.CONTRACT_EXTENSION_PERSISTENCE_OK);
    }

    private Integer updateLastContractVariation(Integer contractNumber){

        ApplicationMainController applicationMainController = new ApplicationMainController();

        List<ContractVariationDTO> contractVariationDTOList = applicationMainController.findAllContractVariationByContractNumber(contractNumber);
        if(contractVariationDTOList.isEmpty())
        {
            return 0;
        }

        LocalDate initialDateOfWeeklyWorkScheduleVariation = contractVariationMainController.getContractVariationContractVariations().getContractVariationWeeklyWorkScheduleDuration()
                .getDateFrom().getValue();

        ContractNewVersionDTO contractNewVersionDTOtoUpdate = new ContractNewVersionDTO();
        for(ContractVariationDTO contractVariationDTO : contractVariationDTOList){
            if(contractVariationDTO.getModificationDate() == null){

                contractNewVersionDTOtoUpdate = ContractNewVersionDTO.create()
                        .withId(contractVariationDTO.getId())
                        .withContractNumber(contractVariationDTO.getContractNumber())
                        .withVariationType(contractVariationDTO.getVariationType())
                        .withStartDate(contractVariationDTO.getStartDate())
                        .withExpectedEndDate(contractVariationDTO.getExpectedEndDate())
                        .withModificationDate(initialDateOfWeeklyWorkScheduleVariation)
                        .withEndingDate(initialDateOfWeeklyWorkScheduleVariation)
                        .withContractJsonData(contractVariationDTO.getContractJsonData())
                        .withContractScheduleJsonData(contractVariationDTO.getContractScheduleJsonData())
                        .build();
            }
        }

        return contractManager.updateContractVariation(contractNewVersionDTOtoUpdate);
    }

    private Integer persistNewWeeklyWorkScheduleVariation(ContractNewVersionDTO contractNewVersionToWeeklyWorkScheduleVariationDTO, Set<WorkDaySchedule> weeklyWorkScheduleVariation){

        LocalDate initialDateOfWeeklyWorkScheduleVariation = contractVariationMainController.getContractVariationContractVariations().getContractVariationWeeklyWorkScheduleDuration()
                .getDateFrom().getValue();

        LocalDate finalDateOfWeeklyWorkScheduleVariation = contractVariationMainController.getContractVariationContractVariations().getContractVariationWeeklyWorkScheduleDuration()
                .getDateTo().getValue();

        Duration subsequentWeeklyWorkDuration = Duration.ZERO;
        for (WorkDaySchedule workDaySchedule : weeklyWorkScheduleVariation) {
            if(workDaySchedule.getDurationHours() != null) {
                subsequentWeeklyWorkDuration = subsequentWeeklyWorkDuration.plus(workDaySchedule.getDurationHours());
            }
        }
        String WeeklyWorkHoursOnVariationWorkSchedule = Utilities.converterDurationToTimeString(subsequentWeeklyWorkDuration);

        String legalMaximumHoursOfWorkPerWeek = Utilities.converterDurationToTimeString(ContractConstants.LEGAL_MAXIMUM_HOURS_OF_WORK_PER_WEEK);
        String fullPartialWorkDay = WeeklyWorkHoursOnVariationWorkSchedule.equals(legalMaximumHoursOfWorkPerWeek) ? "A tiempo completo" : "A tiempo parcial";

        ContractJsonData contractJsonData = ContractJsonData.create()
                .withClientGMId(contractNewVersionToWeeklyWorkScheduleVariationDTO.getContractJsonData().getClientGMId())
                .withWorkerId(contractNewVersionToWeeklyWorkScheduleVariationDTO.getContractJsonData().getWorkerId())
                .withQuoteAccountCode(contractNewVersionToWeeklyWorkScheduleVariationDTO.getContractJsonData().getQuoteAccountCode())
                .withContractType(contractNewVersionToWeeklyWorkScheduleVariationDTO.getContractJsonData().getContractType())
                .withLaborCategory(contractNewVersionToWeeklyWorkScheduleVariationDTO.getContractJsonData().getLaborCategory())
                .withWeeklyWorkHours(WeeklyWorkHoursOnVariationWorkSchedule)
                .withDaysOfWeekToWork(contractNewVersionToWeeklyWorkScheduleVariationDTO.getContractJsonData().getDaysOfWeekToWork())
                .withFullPartialWorkDay(fullPartialWorkDay)
                .withIdentificationContractNumberINEM(contractNewVersionToWeeklyWorkScheduleVariationDTO.getContractJsonData().getIdentificationContractNumberINEM())
                .withNotesForContractManager(contractNewVersionToWeeklyWorkScheduleVariationDTO.getContractJsonData().getNotesForContractManager())
                .withPrivateNotes(contractNewVersionToWeeklyWorkScheduleVariationDTO.getContractJsonData().getPrivateNotes())
                .build();

        // ContractScheduleJsonData
        Map<String, ContractDayScheduleJsonData> contractDayScheduleJsonDataSet = new HashMap<>();
        ContractScheduleJsonData schedule = new ContractScheduleJsonData();

        Integer counter = 0;
        for(WorkDaySchedule workDaySchedule : weeklyWorkScheduleVariation){
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

        ContractNewVersionDTO newWeeklyWorkScheduleVariationToPersist = ContractNewVersionDTO.create()
                .withId(null)
                .withContractNumber(contractNewVersionToWeeklyWorkScheduleVariationDTO.getContractNumber())
                .withVariationType(VARIATION_TYPE_ID_FOR_WEEKLY_WORK_SCHEDULE_VARIATION)
                .withStartDate(initialDateOfWeeklyWorkScheduleVariation)
                .withExpectedEndDate(finalDateOfWeeklyWorkScheduleVariation)
                .withModificationDate(null)
                .withEndingDate(null)
                .withContractJsonData(contractJsonData)
                .withContractScheduleJsonData(schedule)
                .build();

        return contractManager.saveContractVariation(newWeeklyWorkScheduleVariationToPersist);
    }

    private Integer updateInitialContractOfContractExtension(Integer contractNumber){

        LocalDate initialDateOfWeeklyWorkScheduleVariation = contractVariationMainController.getContractVariationContractVariations().getContractVariationWeeklyWorkScheduleDuration()
                .getDateFrom().getValue();

        InitialContractDTO initialContractToUpdateDTO = contractManager.findLastTuplaOfInitialContractByContractNumber(contractNumber);

        if(initialContractToUpdateDTO.getModificationDate() != null) {

            return 0;
        }

        ContractNewVersionDTO contractNewVersionToUpdateDTO = ContractNewVersionDTO.create()
                .withId(initialContractToUpdateDTO.getId())
                .withContractNumber(initialContractToUpdateDTO.getContractNumber())
                .withVariationType(initialContractToUpdateDTO.getVariationType())
                .withStartDate(initialContractToUpdateDTO.getStartDate())
                .withExpectedEndDate(initialContractToUpdateDTO.getExpectedEndDate())
                .withModificationDate(initialDateOfWeeklyWorkScheduleVariation)
                .withEndingDate(initialContractToUpdateDTO.getEndingDate())
                .withContractJsonData(initialContractToUpdateDTO.getContractJsonData())
                .withContractScheduleJsonData(initialContractToUpdateDTO.getContractScheduleJsonData())
                .build();

        return contractManager.updateInitialContract(contractNewVersionToUpdateDTO);
    }

    private Integer persistTraceabilityControlData(){

        LocalDate contractEndNoticeReceptionDate = LocalDate.of(9999,12,31);

        Integer contractNumber = contractVariationMainController.getContractVariationParts().getContractSelector().getSelectionModel()
                .getSelectedItem().getContractNewVersion().getContractNumber();
        LocalDate dateFrom = contractVariationMainController.getContractVariationContractVariations().getContractVariationWeeklyWorkScheduleDuration().getDateFrom().getValue();
        LocalDate dateTo =  contractVariationMainController.getContractVariationContractVariations().getContractVariationWeeklyWorkScheduleDuration().getDateTo().getValue();

        TraceabilityContractDocumentationDTO traceabilityDTO = TraceabilityContractDocumentationDTO.create()
                .withContractNumber(contractNumber)
                .withVariationType(VARIATION_TYPE_ID_FOR_WEEKLY_WORK_SCHEDULE_VARIATION)
                .withStartDate(dateFrom)
                .withExpectedEndDate(dateTo)
                .withContractEndNoticeReceptionDate(contractEndNoticeReceptionDate)
                .build();

        ContractManager contractManager = new ContractManager();

        System.out.println("Contract extension traceability Ok.");
        return contractManager.saveContractTraceability(traceabilityDTO);
    }

    private String retrievePublicNotes(){

        ApplicationMainController applicationMainController = new ApplicationMainController();

        TypesContractVariationsDTO typesContractVariationsDTO = applicationMainController.findTypeContractVariationById(VARIATION_TYPE_ID_FOR_CONTRACT_EXTENSION);
        String contractVariationType = typesContractVariationsDTO.getVariation_description();

        StringBuilder sb = new StringBuilder();
        sb.append(contractVariationType);
        sb.append(". ");
        sb.append(this.contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtension().getPublicNotes().getText());

        return sb.toString();
    }

    private ContractVariationDataSubfolder createContractExtensionDataSubfolder(String additionalData){

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_TIME_FORMAT);


        ContractFullDataDTO allContractData = contractVariationMainController.getContractVariationParts().getContractSelector().getValue();

        String notificationType = ContractConstants.STANDARD_CONTRACT_EXTENSION_TEXT;

        String clientNotificationDate = dateFormatter.format(contractVariationMainController.getContractVariationTypes().getDateNotification().getDate());
        String clientNotificationHour = contractVariationMainController.getContractVariationTypes().getHourNotification().getTime().format(timeFormatter);

        String birthDate = allContractData.getEmployee().getFechanacim() != null ? dateFormatter.format(allContractData.getEmployee().getFechanacim()) : null;

        LocalDate dateFrom = contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtension().getDateFrom().getValue();
        LocalDate dateTo = contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtension().getDateTo().getValue();

        String startDate = dateFormatter.format(dateFrom);
        String endDate = dateFormatter.format(dateTo);

        String daysOfWeek = allContractData.getContractNewVersion().getContractJsonData().getDaysOfWeekToWork();
        Set<DayOfWeek> dayOfWeekSet = retrieveDayOfWeekSet(daysOfWeek);

        String address = allContractData.getEmployee().getDireccion() != null ?  allContractData.getEmployee().getDireccion() : "";
        String codPostal = allContractData.getEmployee().getCodpostal() != null ? allContractData.getEmployee().getCodpostal().toString() : "";
        String location = allContractData.getEmployee().getLocalidad() != null ? allContractData.getEmployee().getLocalidad() : "";
        String fullAddress = address + "   " + codPostal + "   " + location;

        StudyManager studyManager = new StudyManager();
        StudyDTO study = studyManager.findStudyById(allContractData.getEmployee().getNivestud());

        ContractTypeController contractTypeController = new ContractTypeController();
        Integer contractTypeId = allContractData.getContractNewVersion().getContractJsonData().getContractType();
        ContractTypeDTO contractTypeDTO = contractTypeController.findContractTypeById(contractTypeId);

        String contractDescription = contractTypeDTO.getColloquial() + ", " + allContractData.getContractType().getContractDescription();

        String durationDays = Long.toString(ChronoUnit.DAYS.between(dateFrom, dateTo) + 1L);

        Map<String, ContractDayScheduleJsonData> schedule = allContractData.getContractNewVersion().getContractScheduleJsonData().getSchedule();
        Set<WorkDaySchedule> scheduleSet = new HashSet<>();
        if(schedule != null) {
            for (Map.Entry<String, ContractDayScheduleJsonData> entry : schedule.entrySet()) {
                scheduleSet.add(MapperJsonScheduleToWorkDaySchedule.map(entry.getValue()));
            }
        }

        String gmContractNumber = allContractData.getContractNewVersion().getContractNumber() != null ? allContractData.getContractNewVersion().getContractNumber().toString() : null;

        return ContractVariationDataSubfolder.create()
                .withContractExtinction(false)
                .withContractExtension(true)
                .withContractConversion(false)
                .withNotificationType(notificationType)
                .withOfficialContractNumber(allContractData.getContractNewVersion().getContractJsonData().getIdentificationContractNumberINEM())
                .withEmployerFullName(allContractData.getEmployer().toString())
                .withEmployerQuoteAccountCode(allContractData.getContractNewVersion().getContractJsonData().getQuoteAccountCode())
                .withNotificationDate(clientNotificationDate)
                .withNotificationHour(clientNotificationHour)
                .withEmployeeFullName(allContractData.getEmployee().getApellidos() + ", " + allContractData.getEmployee().getNom_rzsoc())
                .withEmployeeNif(Utilities.formatAsNIF(allContractData.getEmployee().getNifcif()))
                .withEmployeeNASS(allContractData.getEmployee().getNumafss())
                .withEmployeeBirthDate(birthDate)
                .withEmployeeCivilState(allContractData.getEmployee().getEstciv())
                .withEmployeeNationality(allContractData.getEmployee().getNacionalidad())
                .withEmployeeFullAddress(fullAddress)
                .withContractTypeDescription(contractDescription)
                .withEmployeeMaxStudyLevel(study.getStudyDescription())
                .withStartDate(startDate)
                .withEndDate(endDate)
                .withDayOfWeekSet(dayOfWeekSet)
                .withDurationDays(durationDays)
                .withSchedule(scheduleSet)
                .withAdditionalData(additionalData)
                .withLaborCategory(allContractData.getContractNewVersion().getContractJsonData().getLaborCategory())
                .withGmContractNumber(gmContractNumber)
                .build();
    }

    private void printContractExtensionDataSubfolder(ContractVariationDataSubfolder contractExtensionDataSubfolder){

        ContractExtensionDataDocumentCreator contractExtensionDataDocumentCreator = new ContractExtensionDataDocumentCreator(contractVariationMainController);
        Path pathToContractExtensionDataSubfolder = contractExtensionDataDocumentCreator.retrievePathToContractExtensionDataSubfolderPDF(contractExtensionDataSubfolder);

        Map<String, String> attributes = new HashMap<>();
        attributes.put("papersize","A3");
        attributes.put("sides", "ONE_SIDED");
        attributes.put("chromacity","MONOCHROME");
        attributes.put("orientation","LANDSCAPE");

        try {
            String printOk = Printer.printPDF(pathToContractExtensionDataSubfolder.toString(), attributes);
            Message.warningMessage(contractVariationMainController.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.CONTRACT_DATA_SUBFOLFER_TO_PRINTER_OK);
            if(!printOk.equals("ok")){
                Message.warningMessage(contractVariationMainController.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, Parameters.NO_PRINTER_FOR_THESE_ATTRIBUTES);
            }
        } catch (IOException | PrinterException e) {
            e.printStackTrace();
        }
    }

    public Boolean sendsMailContractVariationExtension(){

        Boolean isSendOk = false;
        Path pathOut;

        ContractExtensionDataDocumentCreator contractExtensionDocumentCreator = new ContractExtensionDataDocumentCreator(contractVariationMainController);

        String publicNotes = retrievePublicNotes();

        ContractDataToContractsAgent contractExtensionDataToContractAgent = contractExtensionDocumentCreator.createContractExtensionDataDocumentForContractsAgent(publicNotes);

        pathOut = contractExtensionDocumentCreator.retrievePathToContractDataToContractAgentPDF(contractExtensionDataToContractAgent);

        String attachedFileName = contractExtensionDataToContractAgent.toFileName().concat(ApplicationConstants.PDF_EXTENSION);

        Boolean documentToSendIsOpen = verifyDocumentStatus(attachedFileName);
        if(documentToSendIsOpen){
            Message.warningMessage(contractVariationMainController.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, EmailConstants.CLOSE_DOCUMENT_TO_SEND);

            return isSendOk;
        }

        AgentNotificator agentNotificator = new AgentNotificator();

        EmailDataCreationDTO emailDataCreationDTO = retrieveDateForEmailCreation(pathOut, attachedFileName, EmailConstants.STANDARD_CONTRACT_EXTENSION_TEXT);

        try {
            isSendOk = agentNotificator.sendEmailToContractsAgent(emailDataCreationDTO);
        } catch (AddressException e) {
            e.printStackTrace();
        }

        return isSendOk;
    }

    private EmailDataCreationDTO retrieveDateForEmailCreation(Path path, String attachedFileName, String variationTypeText){

        ClientDTO employerDTO = this.contractVariationMainController.getContractVariationParts().getClientSelector().getValue();
        PersonDTO employeeDTO = this.contractVariationMainController.getContractVariationParts().getContractSelector().getValue().getEmployee();
        return EmailDataCreationDTO.create()
                .withPath(path)
                .withFileName(attachedFileName)
                .withEmployer(employerDTO)
                .withEmployee(employeeDTO)
                .withVariationTypeText(variationTypeText)
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

    private Boolean verifyDocumentStatus(String attachedFileName) {

        if (ApplicationConstants.OPERATING_SYSTEM.contains(ApplicationConstants.OS_LINUX)) {

            return SystemProcesses.isRunningInLinuxAndContains(attachedFileName.substring(0, 40), attachedFileName.substring(41, 60));
        }

        else return SystemProcesses.isRunningInWindowsAndContains(attachedFileName.substring(0, 40), attachedFileName.substring(41, 60));
    }
}
