package gmoldes.components.contract.contract_variation.controllers;

import gmoldes.ApplicationMainController;
import gmoldes.components.contract.contract_variation.events.CompatibleVariationEvent;
import gmoldes.components.contract.contract_variation.events.MessageEvent;
import gmoldes.components.contract.contract_variation.events.ContractVariationPersistenceEvent;
import gmoldes.components.contract.contract_variation.forms.ContractExtinctionDataSubfolder;
import gmoldes.components.contract.controllers.ContractTypeController;
import gmoldes.components.contract.manager.ContractManager;
import gmoldes.components.contract.new_contract.components.ContractConstants;
import gmoldes.components.contract.new_contract.components.ContractParameters;
import gmoldes.components.contract.new_contract.forms.ContractDataToContractsAgent;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.dto.*;
import gmoldes.domain.contractjsondata.ContractJsonData;
import gmoldes.domain.document_for_print.ContractExtinctionDataDocumentCreator;
import gmoldes.domain.email.EmailDataCreationDTO;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.person.dto.StudyDTO;
import gmoldes.domain.person.manager.StudyManager;
import gmoldes.domain.traceability_contract_documentation.dto.TraceabilityContractDocumentationDTO;
import gmoldes.services.AgentNotificator;
import gmoldes.services.Printer;
import gmoldes.services.email.EmailConstants;
import gmoldes.utilities.Message;
import gmoldes.utilities.Parameters;
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

public class ContractExtensionController{


    private ContractManager contractManager = new ContractManager();
    private ContractVariationMainController contractVariationMainController;

    private static final Integer VARIATION_TYPE_ID_FOR_CONTRACT_EXTENSION = 220;

    public ContractExtensionController(ContractVariationMainController contractVariationMainController){

        this.contractVariationMainController = contractVariationMainController;
    }


    public MessageEvent executeContractExtensionOperations(){

        // 1. Verify correct contract extension data
        MessageEvent messageEvent = verifyIsCorrectContractExtensionData();
        if(!messageEvent.getMessageText().equals(ContractConstants.NECESSARY_DATA_FOR_VARIATION_CONTRACT_HAVE_BEEN_INTRODUCED)){

            return messageEvent;
        }

        // 2.Verify the notification period to the Labor Administration
        LocalDate effectDateRequestedForContractVariation = contractVariationMainController.getContractVariationContractVariations()
                .getContractVariationContractExtension().getDateFrom().getValue();

        CompatibleVariationEvent dateAdministrationCompatibleEvent = dateToNotifyContractVariationToAdministrationIsCorrect(effectDateRequestedForContractVariation);
        if(dateAdministrationCompatibleEvent.getErrorContractVariationMessage().equals(ContractConstants.VERIFY_IS_VALID_DATE_TO_NOTIFY_CONTRACT_VARIATION_TO_ADMINISTRATION)){
            Boolean isCorrectDate = Message.confirmationMessage(contractVariationMainController.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT,
                    dateAdministrationCompatibleEvent.getErrorContractVariationMessage());
            if(!isCorrectDate){

                return new MessageEvent("");
            }
        }

        // 3. Check exist incompatible operations
        CompatibleVariationEvent compatibleVariationEvent = checkExistenceIncompatibleVariationsForContractExtension();
        if(compatibleVariationEvent.getErrorContractVariationMessage() != null){

            return new MessageEvent(compatibleVariationEvent.getErrorContractVariationMessage());
        }

        // 4. Persistence question
        if (!Message.confirmationMessage(contractVariationMainController.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.PERSIST_CONTRACT_VARIATION_QUESTION)) {

            return new MessageEvent(ContractConstants.CONTRACT_EXTENSION_OPERATION_ABANDONED);
        }

        // 5. Persist contract extension
        ContractVariationPersistenceEvent persistenceEvent = persistContractExtension();
        if(!persistenceEvent.getPersistenceIsOk()) {

            return new MessageEvent(persistenceEvent.getPersistenceMessage());
        }

        Message.warningMessage(this.contractVariationMainController.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.CONTRACT_EXTENSION_PERSISTENCE_OK);


        // 6. Persist traceability
        Integer traceabilityId = persistTraceabilityControlData();
        if(traceabilityId == null){

            return new MessageEvent("Ha petado la persistencia de la trazabilidad de la extinción del contrato.");
        }

        // 7. Print documentation
        StringBuilder sb = new StringBuilder();

        String publicNotes = retrievePublicNotes();
        sb.append(publicNotes);

        ContractExtinctionDataSubfolder contractExtinctionDataSubfolder = createContractExtinctionDataSubfolder(sb.toString(), null);

        printContractExtinctionDataSubfolder(contractExtinctionDataSubfolder);

        return new MessageEvent(ContractConstants.CONTRACT_EXTINCTION_PERSISTENCE_OK);
    }

    private MessageEvent verifyIsCorrectContractExtensionData(){

        if(contractVariationMainController.getContractVariationTypes().getDateNotification().getDate() == null){

            return new MessageEvent(ContractConstants.DATE_NOTIFICATION_NOT_ESTABLISHED);
        }

        if(contractVariationMainController.getContractVariationTypes().getHourNotification().getText() == null){

            return new MessageEvent(ContractConstants.HOUR_NOTIFICATION_NOT_ESTABLISHED);
        }

        if(contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtension().getDateFrom().getValue() == null){

            return new MessageEvent(ContractConstants.ERROR_EXTENSION_CONTRACT_DATE_FROM);
        }

        if(contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtension().getDateTo().getValue() == null){

            return new MessageEvent(ContractConstants.ERROR_EXTENSION_CONTRACT_DATE_TO);
        }

        if(ChronoUnit.DAYS.between(contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtension().getDateFrom().getValue(),
                contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtension().getDateTo().getValue()) <= 0){

            return new MessageEvent(ContractConstants.ERROR_EXTENSION_CONTRACT_INCOHERENT_DATES);
        }

        return new MessageEvent(ContractConstants.NECESSARY_DATA_FOR_VARIATION_CONTRACT_HAVE_BEEN_INTRODUCED);
    }


    private CompatibleVariationEvent dateToNotifyContractVariationToAdministrationIsCorrect(LocalDate date){

        LocalDate limitDatePreviousOfNotifyToAdministration = contractVariationMainController.getContractVariationParts().getInForceDate().getValue()
                .minusDays(ContractParameters.MAXIMUM_NUMBER_DAYS_OF_DELAY_IN_NOTIFICATIONS_TO_THE_LABOR_ADMINISTRACION);

        if(ChronoUnit.DAYS.between(limitDatePreviousOfNotifyToAdministration, date) >= 0){
            return new CompatibleVariationEvent(
                    false,
                    true,
                    false,
                    "");
        }

        return new CompatibleVariationEvent(
                false,
                true,
                false,
                ContractConstants.VERIFY_IS_VALID_DATE_TO_NOTIFY_CONTRACT_VARIATION_TO_ADMINISTRATION);
    }

    public CompatibleVariationEvent checkExistenceIncompatibleVariationsForContractExtension() {

        ApplicationMainController applicationMainController = new ApplicationMainController();

        Integer contractNumber = contractVariationMainController.getContractVariationParts().getContractSelector().getSelectionModel().getSelectedItem().getContractNewVersion().getContractNumber();
        LocalDate contractExtensionDateFrom = contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtension().getDateFrom().getValue();
        LocalDate contractExtensionDateTo = contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtension().getDateTo().getValue();

        // 1. The maximum number of legally permitted extensions is already registered
        Integer counter = 0;
        List<ContractVariationDTO> contractVariationDTOList =  applicationMainController.findAllContractVariationByContractNumber(contractNumber);
        for(ContractVariationDTO contractVariationDTO : contractVariationDTOList){
            if(contractVariationDTO.getVariationType().equals(VARIATION_TYPE_ID_FOR_CONTRACT_EXTENSION)){
                counter++;
            }

            if(counter >= ContractParameters.MAXIMUM_NUMBER_OF_EXTENSIONS_OF_A_CONTRACT) {

                return new CompatibleVariationEvent(
                        false,
                        true,
                        false,
                        ContractConstants.MAXIMUM_NUMBER_LEGALLY_PERMITTED_EXTENSIONS_IS_ALREADY_REGISTERED);
            }
        }

        // 2. Exceeded the number of months of maximum duration of the initial contract plus its extensions
        List<ContractNewVersionDTO> contractNewVersionDTOList = applicationMainController.findHistoryOfContractByContractNumber(contractNumber);

        Long numberDaysOfContractDuration = 0L;

        if(contractNewVersionDTOList.size() == 1){
            numberDaysOfContractDuration = ChronoUnit.DAYS.between(contractNewVersionDTOList.get(0).getStartDate(), contractNewVersionDTOList.get(0).getExpectedEndDate()) + 1;
            numberDaysOfContractDuration = numberDaysOfContractDuration + ChronoUnit.DAYS.between(contractExtensionDateFrom, contractExtensionDateTo) + 1;
        }
        else {

            Integer whileCounter = 0;
            while (whileCounter + 1 < contractNewVersionDTOList.size()) {

                LocalDate previousDate = contractNewVersionDTOList.get(whileCounter).getStartDate();
                LocalDate nextDate = contractNewVersionDTOList.get(whileCounter + 1).getStartDate();

                numberDaysOfContractDuration = numberDaysOfContractDuration + ChronoUnit.DAYS.between(previousDate, nextDate) + 1;

                whileCounter++;
            }
        }

        if(numberDaysOfContractDuration/ContractParameters.AVERAGE_OF_DAYS_IN_A_MONTH > ContractParameters.NUMBER_MONTHS_MAXIMUM_DURATION_INITIAL_CONTRACT_PLUS_ITS_EXTENSIONS){

            System.out.println("Número meses más prórroga (en meses promedio): " + numberDaysOfContractDuration/ContractParameters.AVERAGE_OF_DAYS_IN_A_MONTH);

            return new CompatibleVariationEvent(
                    false,
                    true,
                    false,
                    ContractConstants.MAXIMUM_LEGAL_NUMBER_OF_MONTHS_OF_CONTRACT_IS_EXCEEDED);
        }

        System.out.println("Número meses más prórroga (en meses promedio): " + numberDaysOfContractDuration/ContractParameters.AVERAGE_OF_DAYS_IN_A_MONTH);

        // 3. The initial date of the extension of a contract can not be earlier than the end date established for it
        LocalDate contractExpectedEndDate = contractVariationMainController.getContractVariationParts().getContractSelector().getSelectionModel().getSelectedItem().getContractNewVersion().getExpectedEndDate();
        if(contractExtensionDateFrom.isBefore(contractExpectedEndDate) ||
                contractExtensionDateFrom.equals(contractExpectedEndDate)){
            return new CompatibleVariationEvent(
                    false,
                    true,
                    false,
                    ContractConstants.INITIAL_DATE_EXTENSION_MUST_BE_IMMEDIATELY_AFTER_CONTRACT_EXPECTED_END_DATE);
        }

        // 4. An extension of the contract incompatible with the requested one is already registered
        List<ContractVariationDTO> contractVariationDTOList_2 =  applicationMainController.findAllContractVariationByContractNumber(contractNumber);
        List<TypesContractVariationsDTO> typesContractVariationsDTOList = applicationMainController.findAllTypesContractVariations();
        for(ContractVariationDTO contractVariationDTO : contractVariationDTOList_2) {
            for (TypesContractVariationsDTO typesContractVariationsDTO : typesContractVariationsDTOList) {
                if (typesContractVariationsDTO.getId_variation().equals(contractVariationDTO.getVariationType()) &&
                        typesContractVariationsDTO.getExtension() &&
                        (contractExtensionDateFrom.isBefore(contractVariationDTO.getExpectedEndDate())) ||
                        contractExtensionDateFrom.equals(contractVariationDTO.getExpectedEndDate())){
                    return new CompatibleVariationEvent(
                            false,
                            true,
                            false,
                            ContractConstants.EXIST_PREVIOUS_INCOMPATIBLE_CONTRACT_VARIATION_EXTENSION);
                }
            }
        }

        return new CompatibleVariationEvent(false, true, false, "");
    }


    private ContractVariationPersistenceEvent persistContractExtension(){

        final ContractNewVersionDTO contractNewVersionExtendedDTO = contractVariationMainController.getContractVariationParts().getContractSelector()
                .getSelectionModel().getSelectedItem().getContractNewVersion();

        if(updateLastContractVariation(contractNewVersionExtendedDTO.getContractNumber()) == null) {

            return new ContractVariationPersistenceEvent(false, ContractConstants.ERROR_UPDATING_LAST_CONTRACT_VARIATION_RECORD);
        }

        if(persistNewContractVariation(contractNewVersionExtendedDTO) == null) {

            return new ContractVariationPersistenceEvent(false, ContractConstants.ERROR_INSERTING_NEW_EXTINCTION_RECORD_IN_CONTRACT_VARIATION);
        }

        if(updateInitialContractOfContractExtension(contractNewVersionExtendedDTO.getContractNumber()) == null) {

            return new ContractVariationPersistenceEvent(false, ContractConstants.ERROR_UPDATING_EXTINCTION_DATE_IN_INITIAL_CONTRACT);
        }

        if(persistTraceabilityControlData() == null){

            return new ContractVariationPersistenceEvent(false, ContractConstants.ERROR_PERSISTING_TRACEABILITY_CONTROL_DATA);
        }

        return new ContractVariationPersistenceEvent(true, ContractConstants.CONTRACT_EXTENSION_PERSISTENCE_OK);
    }

    private Integer persistTraceabilityControlData(){

        // The extension of the contract does not generate IDC, so the reception date of this is set at 31 -12-9999
        LocalDate IDCReceptionDate = LocalDate.of(9999,12,31);

        Integer contractNumber = contractVariationMainController.getContractVariationParts().getContractSelector().getSelectionModel()
                .getSelectedItem().getContractNewVersion().getContractNumber();
        LocalDate dateFrom = contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtension().getDateFrom().getValue();
        LocalDate dateTo =  contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtension().getDateTo().getValue();

        TraceabilityContractDocumentationDTO traceabilityDTO = TraceabilityContractDocumentationDTO.create()
                .withContractNumber(contractNumber)
                .withVariationType(VARIATION_TYPE_ID_FOR_CONTRACT_EXTENSION)
                .withStartDate(dateFrom)
                .withExpectedEndDate(dateTo)
                .withIDCReceptionDate(IDCReceptionDate)
                .build();

        ContractManager contractManager = new ContractManager();

        return contractManager.saveContractTraceability(traceabilityDTO);
    }

    private Integer updateLastContractVariation(Integer contractNumber){

        ApplicationMainController applicationMainController = new ApplicationMainController();

        List<ContractVariationDTO> contractVariationDTOList = applicationMainController.findAllContractVariationByContractNumber(contractNumber);
        if(contractVariationDTOList.isEmpty())
        {
            return 0;
        }

        LocalDate initialDateOfExtension = contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtension()
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
                        .withModificationDate(initialDateOfExtension)
                        .withEndingDate(initialDateOfExtension)
                        .withContractJsonData(contractVariationDTO.getContractJsonData())
                        .build();
            }
        }

        return contractManager.updateContractVariation(contractNewVersionDTOtoUpdate);
    }

    private Integer persistNewContractVariation(ContractNewVersionDTO contractNewVersionExtendedDTO){

        LocalDate initialDateOfExtension = contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtension()
                .getDateFrom().getValue();

        LocalDate finalDateOfExtension = contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtension()
                .getDateTo().getValue();

        String newIdentificationContractNumberINEM = contractNewVersionExtendedDTO.getContractJsonData().getIdentificationContractNumberINEM() != null ?
                contractNewVersionExtendedDTO.getContractJsonData().getIdentificationContractNumberINEM().concat("-1") : "";

        ContractJsonData newContractJsonData = ContractJsonData.create()
                .withClientGMId(contractNewVersionExtendedDTO.getContractJsonData().getClientGMId())
                .withWorkerId(contractNewVersionExtendedDTO.getContractJsonData().getWorkerId())
                .withQuoteAccountCode(contractNewVersionExtendedDTO.getContractJsonData().getQuoteAccountCode())
                .withContractType(contractNewVersionExtendedDTO.getContractJsonData().getContractType())
                .withLaborCategory(contractNewVersionExtendedDTO.getContractJsonData().getLaborCategory())
                .withWeeklyWorkHours(contractNewVersionExtendedDTO.getContractJsonData().getWeeklyWorkHours())
                .withDaysOfWeekToWork(contractNewVersionExtendedDTO.getContractJsonData().getDaysOfWeekToWork())
                .withFullPartialWorkDay(contractNewVersionExtendedDTO.getContractJsonData().getFullPartialWorkDay())
                .withIdentificationContractNumberINEM(newIdentificationContractNumberINEM)
                .withNotesForContractManager(contractNewVersionExtendedDTO.getContractJsonData().getNotesForContractManager())
                .withPrivateNotes(contractNewVersionExtendedDTO.getContractJsonData().getPrivateNotes())
                .build();

        ContractNewVersionDTO newContractExtensionToPersist = ContractNewVersionDTO.create()
                .withId(null)
                .withContractNumber(contractNewVersionExtendedDTO.getContractNumber())
                .withVariationType(VARIATION_TYPE_ID_FOR_CONTRACT_EXTENSION)
                .withStartDate(initialDateOfExtension)
                .withExpectedEndDate(finalDateOfExtension)
                .withModificationDate(null)
                .withEndingDate(null)
                .withContractJsonData(newContractJsonData)
                .build();

        return contractManager.saveContractVariation(newContractExtensionToPersist);
    }

    private Integer updateInitialContractOfContractExtension(Integer contractNumber){

        LocalDate initialDateOfExtension = contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtension()
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
                .withModificationDate(initialDateOfExtension)
                .withEndingDate(initialContractToUpdateDTO.getEndingDate())
                .withContractJsonData(initialContractToUpdateDTO.getContractJsonData())
                .build();

        return contractManager.updateInitialContract(contractNewVersionToUpdateDTO);
    }

    private String retrievePublicNotes(){

        String extinctionContractCause = this.contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction()
                .getExtinctionCauseSelector().getSelectionModel().getSelectedItem().getVariation_description();

        String holidaysUsedText;
        if(this.contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction()
                .getRbHolidaysYes().isSelected()){
            holidaysUsedText = "disfrutadas.";
        } else if(this.contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction()
                .getRbHolidaysNo().isSelected()){
            holidaysUsedText = "no disfrutadas.";
        }else{
            holidaysUsedText = "a calcular.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(extinctionContractCause);
        sb.append(". Vacaciones ");
        sb.append(holidaysUsedText);
        sb.append("\n");
        sb.append(this.contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction().getPublicNotes().getText());

        return sb.toString();
    }

    private ContractExtinctionDataSubfolder createContractExtinctionDataSubfolder(String additionalData, Duration duration){

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parameters.DEFAULT_DATE_FORMAT);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(Parameters.DEFAULT_TIME_FORMAT);


        ContractFullDataDTO allContractData = contractVariationMainController.getContractVariationParts().getContractSelector().getSelectionModel().getSelectedItem();

        String notificationType = Parameters.CONTRACT_EXTINCTION_TEXT;

        String clientNotificationDate = dateFormatter.format(contractVariationMainController.getContractVariationTypes().getDateNotification().getDate());
        String clientNotificationHour = contractVariationMainController.getContractVariationTypes().getHourNotification().getTime().format(timeFormatter);

        String birthDate = allContractData.getEmployee().getFechanacim() != null ? dateFormatter.format(allContractData.getEmployee().getFechanacim()) : null;

        String startDate = null;
        String endDate = dateFormatter.format(contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction().getDateFrom().getValue());

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

        String durationDays = duration != null ? Long.toString(duration.toDays()) : "";

        return ContractExtinctionDataSubfolder.create()
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
                .withSchedule(new HashSet<>())
                .withAdditionalData(additionalData)
                .withLaborCategory(allContractData.getContractNewVersion().getContractJsonData().getLaborCategory())
                .build();
    }

    private void printContractExtinctionDataSubfolder(ContractExtinctionDataSubfolder contractExtinctionDataSubfolder){

        ContractExtinctionDataDocumentCreator contractExtinctionDataDocumentCreator = new ContractExtinctionDataDocumentCreator(contractVariationMainController);
        Path pathToContractExtinctionDataSubfolder = contractExtinctionDataDocumentCreator.retrievePathToContractExtinctionDataSubfolderPDF(contractExtinctionDataSubfolder);

        Map<String, String> attributes = new HashMap<>();
        attributes.put("papersize","A3");
        attributes.put("sides", "ONE_SIDED");
        attributes.put("chromacity","MONOCHROME");
        attributes.put("orientation","LANDSCAPE");

        try {
            String printOk = Printer.printPDF(pathToContractExtinctionDataSubfolder.toString(), attributes);
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

        ContractExtinctionDataDocumentCreator contractExtinctionDocumentCreator = new ContractExtinctionDataDocumentCreator(contractVariationMainController);

        String publicNotes = retrievePublicNotes();

        ContractDataToContractsAgent contractExtinctionDataToContractAgent = contractExtinctionDocumentCreator.createContractExtinctionDataDocumentForContractsAgent(publicNotes);

        pathOut = contractExtinctionDocumentCreator.retrievePathToContractDataToContractAgentPDF(contractExtinctionDataToContractAgent);


        String attachedFileName = contractExtinctionDataToContractAgent.toFileName().concat(".pdf");

        AgentNotificator agentNotificator = new AgentNotificator();

        EmailDataCreationDTO emailDataCreationDTO = retrieveDateForEmailCreation(pathOut, attachedFileName, EmailConstants.STANDARD_CONTRACT_EXTINCTION_TEXT);

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
}
