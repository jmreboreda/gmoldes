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
import gmoldes.domain.document_for_print.ContractExtinctionDataDocumentCreator;
import gmoldes.domain.email.EmailDataCreationDTO;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.person.dto.StudyDTO;
import gmoldes.domain.person.manager.StudyManager;
import gmoldes.domain.traceability_contract_documentation.dto.TraceabilityContractDocumentationDTO;
import gmoldes.services.AgentNotificator;
import gmoldes.services.Printer;
import gmoldes.services.email.EmailParameters;
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

public class ContractExtinctionController{


    private ContractManager contractManager = new ContractManager();
    private ContractVariationMainController contractVariationMainController;

    private static final Integer EXTINCTION_CODE_BY_CONTRACT_SUBROGATION = 910;

    public ContractExtinctionController(ContractVariationMainController contractVariationMainController){

            this.contractVariationMainController = contractVariationMainController;
    }


    public MessageEvent executeContractExtinctionOperations(){

        // 1. Verify correct contract extinction data
        MessageEvent messageEvent = verifyIsCorrectContractExtinctionData();
        if(!messageEvent.getMessageText().equals(ContractConstants.NECESSARY_DATA_FOR_VARIATION_CONTRACT_HAVE_BEEN_INTRODUCED)){

            return messageEvent;
        }

        // 2.Verify the notification period to the Labor Administration
        LocalDate effectDateRequestedForContractVariation = contractVariationMainController.getContractVariationContractVariations()
                .getContractVariationContractExtinction().getDateFrom().getValue();

        CompatibleVariationEvent dateAdministrationCompatibleEvent = dateToNotifyContractVariationToAdministrationIsCorrect(effectDateRequestedForContractVariation);
        if(dateAdministrationCompatibleEvent.getErrorContractVariationMessage().equals(ContractConstants.VERIFY_IS_VALID_DATE_TO_NOTIFY_CONTRACT_VARIATION_TO_ADMINISTRATION)){
            Boolean isCorrectDate = Message.confirmationMessage(contractVariationMainController.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT,
                    dateAdministrationCompatibleEvent.getErrorContractVariationMessage());
            if(!isCorrectDate){

                return new MessageEvent("");
            }
        }

        // 3. Check exist incompatible operations
        CompatibleVariationEvent compatibleVariationEvent = checkExistenceIncompatibleVariationsForContractExtinction();
        if(compatibleVariationEvent.getErrorContractVariationMessage() != null){

            return new MessageEvent(compatibleVariationEvent.getErrorContractVariationMessage());
        }

        // 4. Persistence question
        if (!Message.confirmationMessage(contractVariationMainController.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.PERSIST_CONTRACT_VARIATION_QUESTION)) {

            return new MessageEvent(ContractConstants.CONTRACT_EXTINCTION_OPERATION_ABANDONED);
        }

        // 5. Persist contract extinction
        ContractVariationPersistenceEvent persistenceEvent = persistContractExtinction();
        if(!persistenceEvent.getPersistenceIsOk()) {

            return new MessageEvent(persistenceEvent.getPersistenceMessage());
        }

        Message.warningMessage(this.contractVariationMainController.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.CONTRACT_EXTINCTION_PERSISTENCE_OK);


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

    private MessageEvent verifyIsCorrectContractExtinctionData(){

            if(contractVariationMainController.getContractVariationTypes().getDateNotification().getDate() == null){

                return new MessageEvent(ContractConstants.DATE_NOTIFICATION_NOT_ESTABLISHED);
            }

            if(contractVariationMainController.getContractVariationTypes().getHourNotification().getText() == null){

                return new MessageEvent(ContractConstants.HOUR_NOTIFICATION_NOT_ESTABLISHED);
            }

            if(contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction().getExtinctionCauseSelector().getSelectionModel().getSelectedItem() == null){

                return new MessageEvent(ContractConstants.EXTINCTION_CAUSE_NOT_ESTABLISHED);
            }

            if(contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction().getDateFrom().getValue() == null){

                return new MessageEvent(ContractConstants.ERROR_IN_EXTINCTION_DATA);
            }

            if(!contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction().getRbHolidaysYes().isSelected() &&
                    !contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction().getRbHolidaysNo().isSelected() &&
                    !contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction().getRbHolidaysCalculate().isSelected()){

                return new MessageEvent(ContractConstants.HOLIDAYS_SITUATION_NOT_ESTABLISHED);
            }

        return new MessageEvent(ContractConstants.NECESSARY_DATA_FOR_VARIATION_CONTRACT_HAVE_BEEN_INTRODUCED);
    }


    private CompatibleVariationEvent dateToNotifyContractVariationToAdministrationIsCorrect(LocalDate date){

        LocalDate limitDatePreviousOfNotifyToAdministration = contractVariationMainController.getContractVariationParts().getInForceDate().getValue()
                .minusDays(ContractParameters.MAXIMUM_NUMBER_DAYS_OF_DELAY_IN_NOTIFICATIONS_TO_THE_LABOR_ADMINISTRACION);

        if(ChronoUnit.DAYS.between(limitDatePreviousOfNotifyToAdministration, date) >= 0){
            return new CompatibleVariationEvent(
                    null,
                    null,
                    null,
                    "");
        }

        return new CompatibleVariationEvent(
                null,
                null,
                null,
                ContractConstants.VERIFY_IS_VALID_DATE_TO_NOTIFY_CONTRACT_VARIATION_TO_ADMINISTRATION);
    }

    public CompatibleVariationEvent checkExistenceIncompatibleVariationsForContractExtinction() {

        ApplicationMainController applicationMainController = new ApplicationMainController();

        Integer selectedContractNumber = contractVariationMainController.getContractVariationParts().getContractSelector().getSelectionModel().getSelectedItem().getContractNewVersion().getContractNumber();

        // 1. Expiration date requested for the contract is higher than the expected termination date
        LocalDate expectedEndDate = contractVariationMainController.getContractVariationParts().getContractSelector().getValue().getContractNewVersion().getExpectedEndDate();
        LocalDate extinctionDate = contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction().getDateFrom().getValue();

        if (expectedEndDate != null && extinctionDate.isAfter(expectedEndDate)) {

            return new CompatibleVariationEvent(
                    true,
                    null,
                    null,
                    ContractConstants.EXTINCTION_DATE_EXCEEDED_BY_DATE_REQUESTED);
        }

        // 2. The extinction of the contract already exists and is not by prior subrogation of the contract
        List<ContractVariationDTO> contractVariationDTOList = applicationMainController.findAllContractVariationByContractNumber(selectedContractNumber);
        List<TypesContractVariationsDTO> typesContractVariationsDTOList = applicationMainController.findAllTypesContractVariations();
        for (ContractVariationDTO contractVariationDTO : contractVariationDTOList) {
            for (TypesContractVariationsDTO typesContractVariationsDTO : typesContractVariationsDTOList) {
                if (typesContractVariationsDTO.getId_variation().equals(contractVariationDTO.getVariationType()) &&
                        typesContractVariationsDTO.getExtinction() && !typesContractVariationsDTO.getId_variation().equals(EXTINCTION_CODE_BY_CONTRACT_SUBROGATION)) {

                    return new CompatibleVariationEvent(
                            true,
                            null,
                            null,
                            ContractConstants.EXIST_PREVIOUS_CONTRACT_VARIATION_EXTINCTION);
                }
            }
        }

        // 3. The termination date is prior to the contract start date
        InitialContractDTO initialContractDTO = applicationMainController.findInitialContractByContractNumber(selectedContractNumber);
        if(initialContractDTO.getStartDate().isAfter(extinctionDate)){

            return new CompatibleVariationEvent(
                    true,
                    null,
                    null,
                    ContractConstants.EXTINCTION_DATE_PRIOR_CONTRACT_START_DATE);
        }

        // 4. Registered transactions with date after the requested start date do not allow the termination of the contract on the requested date
        List<ContractVariationDTO> contractVariationDTOList1 = applicationMainController.findAllContractVariationByContractNumber(selectedContractNumber);
        for(ContractVariationDTO contractVariationDTO : contractVariationDTOList1) {
            if(contractVariationDTO.getStartDate().isAfter(extinctionDate) ||
                    (contractVariationDTO.getModificationDate() != null && contractVariationDTO.getModificationDate().isAfter(extinctionDate))){

                return new CompatibleVariationEvent(
                    true,
                    null,
                    null,
                    ContractConstants.CONTRACT_VARIATIONS_IN_THE_FUTURE_NOT_ALLOW_EXTINCTION_ON_REQUESTED_DATE);
            }

        }

        return new CompatibleVariationEvent(true, false, false, null);
    }

    public ContractVariationPersistenceEvent persistContractExtinction(){

        ContractNewVersionDTO contractNewVersionToBeExtinguished = contractVariationMainController.getContractVariationParts()
                .getContractSelector().getSelectionModel().getSelectedItem().getContractNewVersion();

        if(updateLastVariationOfContractToBeExtinguished(contractNewVersionToBeExtinguished) == null) {

            return new ContractVariationPersistenceEvent(false, ContractConstants.ERROR_UPDATING_LAST_CONTRACT_VARIATION_RECORD);
        }

        if(persistNewContractExtinctionVariation(contractNewVersionToBeExtinguished) == null) {

            return new ContractVariationPersistenceEvent(false, ContractConstants.ERROR_INSERTING_NEW_EXTINCTION_RECORD_IN_CONTRACT_VARIATION);
        }

        if(updateInitialContractOfContractToBeExtinguished(contractNewVersionToBeExtinguished) == null) {

            return new ContractVariationPersistenceEvent(false, ContractConstants.ERROR_UPDATING_EXTINCTION_DATE_IN_INITIAL_CONTRACT);
        }

        if(persistTraceabilityControlData() == null){

            return new ContractVariationPersistenceEvent(false, ContractConstants.ERROR_PERSISTING_TRACEABILITY_CONTROL_DATA);
        }

        return new ContractVariationPersistenceEvent(true, ContractConstants.CONTRACT_EXTINCTION_PERSISTENCE_OK);
    }

    private Integer persistTraceabilityControlData(){

        // In a contract extinction the date for the notice of termination of contract is set at 31-12-9999
        LocalDate contractEndNoticeToSave = LocalDate.of(9999, 12, 31);

        Integer contractVariationType = contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction()
                .getExtinctionCauseSelector().getSelectionModel().getSelectedItem().getId_variation();
        Integer contractNumber = contractVariationMainController.getContractVariationParts().getContractSelector().getSelectionModel().getSelectedItem().getContractNewVersion().getContractNumber();
        LocalDate dateFrom = contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction().getDateFrom().getValue();

        TraceabilityContractDocumentationDTO traceabilityContractExtinctionDTO = TraceabilityContractDocumentationDTO.create()
                .withContractNumber(contractNumber)
                .withVariationType(contractVariationType)
                .withStartDate(dateFrom)
                .withExpectedEndDate(dateFrom)
                .withIDCReceptionDate(null)
                .withDateDeliveryContractDocumentationToClient(null)
                .withContractEndNoticeReceptionDate(contractEndNoticeToSave)
                .build();

        ContractManager contractManager = new ContractManager();

        return contractManager.saveContractTraceability(traceabilityContractExtinctionDTO);
    }

    private Integer updateLastVariationOfContractToBeExtinguished(ContractNewVersionDTO contractNewVersionExtinctedDTO){

        ApplicationMainController applicationMainController = new ApplicationMainController();

        Integer contractNumber = contractNewVersionExtinctedDTO.getContractNumber();
        List<ContractVariationDTO> contractVariationDTOList = applicationMainController.findAllContractVariationByContractNumber(contractNumber);
        if(contractVariationDTOList.isEmpty())
        {
            return 0;
        }

        LocalDate dateOfExtinction = contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction()
                .getDateFrom().getValue();

        contractNewVersionExtinctedDTO.setModificationDate(dateOfExtinction);
        contractNewVersionExtinctedDTO.setEndingDate(dateOfExtinction);

        return contractManager.updateContractVariation(contractNewVersionExtinctedDTO);
    }

    private Integer persistNewContractExtinctionVariation(ContractNewVersionDTO contractNewVersionExtinctedDTO){

        Integer contractVariationExtinctionCause = contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction()
                .getExtinctionCauseSelector().getSelectionModel().getSelectedItem().getId_variation();

        LocalDate dateOfExtinction = contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction()
                .getDateFrom().getValue();

        contractNewVersionExtinctedDTO.setId(null);
        contractNewVersionExtinctedDTO.setVariationType(contractVariationExtinctionCause);
        contractNewVersionExtinctedDTO.setStartDate(dateOfExtinction);
        contractNewVersionExtinctedDTO.setModificationDate(dateOfExtinction);
        contractNewVersionExtinctedDTO.setEndingDate(dateOfExtinction);

        return contractManager.saveContractVariation(contractNewVersionExtinctedDTO);
    }

    private Integer updateInitialContractOfContractToBeExtinguished(ContractNewVersionDTO contractNewVersionExtinctedDTO){

        LocalDate dateOfExtinction = contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction()
                .getDateFrom().getValue();

        InitialContractDTO initialContractToUpdateDTO = contractManager.findLastTuplaOfInitialContractByContractNumber(contractNewVersionExtinctedDTO.getContractNumber());
        initialContractToUpdateDTO.setEndingDate(dateOfExtinction);

        ContractNewVersionDTO contractNewVersionToUpdateDTO = ContractNewVersionDTO.create()
                .withId(initialContractToUpdateDTO.getId())
                .withContractNumber(initialContractToUpdateDTO.getContractNumber())
                .withVariationType(initialContractToUpdateDTO.getVariationType())
                .withStartDate(initialContractToUpdateDTO.getStartDate())
                .withExpectedEndDate(initialContractToUpdateDTO.getExpectedEndDate())
                .withModificationDate(initialContractToUpdateDTO.getModificationDate())
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

    public Boolean sendsMailContractVariationExtinction(){

        Boolean isSendOk = false;
        Path pathOut;

        ContractExtinctionDataDocumentCreator contractExtinctionDocumentCreator = new ContractExtinctionDataDocumentCreator(contractVariationMainController);

        String publicNotes = retrievePublicNotes();

        ContractDataToContractsAgent contractExtinctionDataToContractAgent = contractExtinctionDocumentCreator.createContractExtinctionDataDocumentForContractsAgent(publicNotes);

        pathOut = contractExtinctionDocumentCreator.retrievePathToContractDataToContractAgentPDF(contractExtinctionDataToContractAgent);


        String attachedFileName = contractExtinctionDataToContractAgent.toFileName().concat(".pdf");

        AgentNotificator agentNotificator = new AgentNotificator();

        EmailDataCreationDTO emailDataCreationDTO = retrieveDateForEmailCreation(pathOut, attachedFileName, EmailParameters.STANDARD_CONTRACT_EXTINCTION_TEXT);

        try {
            isSendOk = agentNotificator.sendEmailToContractAgent(emailDataCreationDTO);
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
