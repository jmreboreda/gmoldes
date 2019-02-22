package gmoldes.components.contract.contract_variation.controllers;

import gmoldes.ApplicationConstants;
import gmoldes.ApplicationMainController;
import gmoldes.components.contract.contract_variation.events.CompatibleVariationEvent;
import gmoldes.components.contract.contract_variation.events.MessageContractVariationEvent;
import gmoldes.components.contract.contract_variation.events.ContractVariationPersistenceEvent;
import gmoldes.components.contract.contract_variation.forms.ContractVariationDataSubfolder;
import gmoldes.components.contract.controllers.ContractTypeController;
import gmoldes.components.contract.manager.ContractManager;
import gmoldes.components.contract.ContractConstants;
import gmoldes.components.contract.new_contract.components.ContractParameters;
import gmoldes.components.contract.new_contract.forms.ContractDataToContractsAgent;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.ContractService;
import gmoldes.domain.contract.dto.*;
import gmoldes.domain.document_for_print.ContractVariationDataDocumentCreator;
import gmoldes.domain.email.EmailDataCreationDTO;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.study.dto.StudyDTO;
import gmoldes.domain.study.StudyManager;
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

public class ContractExtinctionController{


    private ContractManager contractManager = new ContractManager();
    private ContractVariationMainController contractVariationMainController;

    private static final Integer EXTINCTION_CODE_BY_CONTRACT_SUBROGATION = 910;

    public ContractExtinctionController(ContractVariationMainController contractVariationMainController){

            this.contractVariationMainController = contractVariationMainController;
    }


    public MessageContractVariationEvent executeContractExtinctionOperations(){

        // 1. Verify correct contract extinction data
        MessageContractVariationEvent messageEvent = verifyIsCorrectContractExtinctionData();
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

                return new MessageContractVariationEvent("", null);
            }
        }

        // 3. Check exist incompatible operations
        CompatibleVariationEvent compatibleVariationEvent = checkExistenceIncompatibleVariationsForContractExtinction();
        if(compatibleVariationEvent.getErrorContractVariationMessage() != null){

            return new MessageContractVariationEvent(compatibleVariationEvent.getErrorContractVariationMessage(), null);
        }

        // 4. Persistence question
        if (!Message.confirmationMessage(contractVariationMainController.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.PERSIST_CONTRACT_VARIATION_QUESTION)) {

            return new MessageContractVariationEvent(ContractConstants.CONTRACT_EXTINCTION_OPERATION_ABANDONED, null);
        }

        // 5. Persist contract extinction
        ContractVariationPersistenceEvent persistenceEvent = persistContractExtinction();
        if(!persistenceEvent.getPersistenceIsOk()) {

            return new MessageContractVariationEvent(persistenceEvent.getPersistenceMessage(),null);
        }

        // 6. Persist traceability
        Integer traceabilityContractExtinctionId = persistTraceabilityControlData();
        if(traceabilityContractExtinctionId == null){

            return new MessageContractVariationEvent(ContractConstants.ERROR_PERSISTING_TRACEABILITY_CONTROL_DATA, null);
        }

        Message.warningMessage(this.contractVariationMainController.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.CONTRACT_EXTINCTION_PERSISTENCE_OK);

        // 7. Print documentation
        StringBuilder sb = new StringBuilder();

        String publicNotes = retrievePublicNotes();
        sb.append(publicNotes);

        ContractVariationDataSubfolder contractExtinctionDataSubfolder = createContractExtinctionDataSubfolder(sb.toString(), null);

        printContractExtinctionDataSubfolder(contractExtinctionDataSubfolder);

        return new MessageContractVariationEvent(ContractConstants.CONTRACT_EXTINCTION_PERSISTENCE_OK, contractExtinctionDataSubfolder);
    }

    private MessageContractVariationEvent verifyIsCorrectContractExtinctionData(){

            if(contractVariationMainController.getContractVariationTypes().getDateNotification().getDate() == null){

                return new MessageContractVariationEvent(ContractConstants.DATE_NOTIFICATION_NOT_ESTABLISHED, null);
            }

            if(contractVariationMainController.getContractVariationTypes().getHourNotification().getText() == null){

                return new MessageContractVariationEvent(ContractConstants.HOUR_NOTIFICATION_NOT_ESTABLISHED, null);
            }

            if(contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction().getExtinctionCauseSelector().getSelectionModel().getSelectedItem() == null){

                return new MessageContractVariationEvent(ContractConstants.EXTINCTION_CAUSE_NOT_ESTABLISHED, null);
            }

            if(contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction().getDateFrom().getValue() == null){

                return new MessageContractVariationEvent(ContractConstants.ERROR_IN_EXTINCTION_DATA, null);
            }

            if(!contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction().getRbHolidaysYes().isSelected() &&
                    !contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction().getRbHolidaysNo().isSelected() &&
                    !contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction().getRbHolidaysCalculate().isSelected()){

                return new MessageContractVariationEvent(ContractConstants.HOLIDAYS_SITUATION_NOT_ESTABLISHED, null);
            }

        return new MessageContractVariationEvent(ContractConstants.NECESSARY_DATA_FOR_VARIATION_CONTRACT_HAVE_BEEN_INTRODUCED, null);
    }


    private CompatibleVariationEvent dateToNotifyContractVariationToAdministrationIsCorrect(LocalDate date){

        LocalDate limitDatePreviousOfNotifyToAdministration = contractVariationMainController.getContractVariationParts().getInForceDate().getValue()
                .minusDays(ContractParameters.MAXIMUM_NUMBER_DAYS_OF_DELAY_IN_NOTIFICATIONS_TO_THE_LABOR_ADMINISTRACION);

        if(ChronoUnit.DAYS.between(limitDatePreviousOfNotifyToAdministration, date) >= 0){
            return new CompatibleVariationEvent(
                    null,
                    null,
                    null,
                    null,
                    "");
        }

        return new CompatibleVariationEvent(
                null,
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
                    null,
                    ContractConstants.EXTINCTION_DATE_EXCEEDED_BY_DATE_REQUESTED);
        }

        // 2. The extinction of the contract already exists and is not by prior subrogation of the contract
        ContractService contractService = ContractService.ContractServiceFactory.getInstance();
        List<ContractVariationDTO> contractVariationDTOList = contractService.findAllContractVariationByContractNumber(selectedContractNumber);
        List<TypesContractVariationsDTO> typesContractVariationsDTOList = applicationMainController.findAllTypesContractVariations();
        for (ContractVariationDTO contractVariationDTO : contractVariationDTOList) {
            for (TypesContractVariationsDTO typesContractVariationsDTO : typesContractVariationsDTOList) {
                if (typesContractVariationsDTO.getId_variation().equals(contractVariationDTO.getVariationType()) &&
                        typesContractVariationsDTO.getExtinction() && !typesContractVariationsDTO.getId_variation().equals(EXTINCTION_CODE_BY_CONTRACT_SUBROGATION)) {

                    return new CompatibleVariationEvent(
                            true,
                            null,
                            null,
                            null,
                            ContractConstants.EXIST_PREVIOUS_CONTRACT_VARIATION_EXTINCTION);
                }
            }
        }

        // 3. The termination date is prior to the contract start date
        InitialContractDTO initialContractDTO = contractService.findInitialContractByContractNumber(selectedContractNumber);
        if(initialContractDTO.getStartDate().isAfter(extinctionDate)){

            return new CompatibleVariationEvent(
                    true,
                    null,
                    null,
                    null,
                    ContractConstants.EXTINCTION_DATE_PRIOR_CONTRACT_START_DATE);
        }

        // 4. Registered transactions with date after the requested start date do not allow the termination of the contract on the requested date
        List<ContractVariationDTO> contractVariationDTOList1 = contractService.findAllContractVariationByContractNumber(selectedContractNumber);
        for(ContractVariationDTO contractVariationDTO : contractVariationDTOList1) {
            if(contractVariationDTO.getStartDate().isAfter(extinctionDate) ||
                    (contractVariationDTO.getModificationDate() != null && contractVariationDTO.getModificationDate().isAfter(extinctionDate))){

                return new CompatibleVariationEvent(
                    true,
                    null,
                    null,
                    null,
                    ContractConstants.CONTRACT_VARIATIONS_IN_THE_FUTURE_NOT_ALLOW_EXTINCTION_ON_REQUESTED_DATE);
            }

        }

        return new CompatibleVariationEvent(true, false, false,false, null);
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

        if(updateInitialContractToBeExtinguished(contractNewVersionToBeExtinguished) == null) {

            return new ContractVariationPersistenceEvent(false, ContractConstants.ERROR_UPDATING_EXTINCTION_DATE_IN_INITIAL_CONTRACT);
        }

        // Update ending date in table "contract" in database
        ContractFullDataDTO contractFullDataDTO = contractVariationMainController.getContractVariationParts()
                .getContractSelector().getSelectionModel().getSelectedItem();

        ContractDTO contractDTO = ContractDTO.create()
                .withId(contractFullDataDTO.getContractNewVersion().getId())
                .withEmployer(contractFullDataDTO.getEmployer().getClientId())
                .withEmployee(contractFullDataDTO.getEmployee().getIdpersona())
                .withContractType(contractFullDataDTO.getContractType().getContractCode().toString())
                .withGmContractNumber(contractFullDataDTO.getContractNewVersion().getContractNumber())
                .withVariationType(contractFullDataDTO.getContractNewVersion().getVariationType())
                .withStartDate(contractFullDataDTO.getContractNewVersion().getStartDate())
                .withExpectedEndDate(contractFullDataDTO.getContractNewVersion().getExpectedEndDate())
                .withModificationDate(contractFullDataDTO.getContractNewVersion().getModificationDate())
                .withEndingDate(contractFullDataDTO.getContractNewVersion().getEndingDate())
                .withContractScheduleJsonData(contractFullDataDTO.getContractNewVersion().getContractScheduleJsonData())
                .withLaborCategory(contractFullDataDTO.getContractNewVersion().getContractJsonData().getLaborCategory())
                .withQuoteAccountCode(contractFullDataDTO.getContractNewVersion().getContractJsonData().getQuoteAccountCode())
                .withIdentificationContractNumberINEM(contractFullDataDTO.getContractNewVersion().getContractJsonData().getIdentificationContractNumberINEM())
                .withPublicNotes(contractFullDataDTO.getContractNewVersion().getContractJsonData().getNotesForContractManager())
                .withPrivateNotes(contractFullDataDTO.getContractNewVersion().getContractJsonData().getPrivateNotes())
                .build();

        if(updateContractToBeExtinguished(contractDTO) == null){

            return new ContractVariationPersistenceEvent(false, ContractConstants.ERROR_UPDATING_EXTINCTION_DATE_IN_CONTRACT);
        }

        return new ContractVariationPersistenceEvent(true, ContractConstants.CONTRACT_EXTINCTION_PERSISTENCE_OK);
    }

    private Integer persistTraceabilityControlData(){

        // In a contract extinction the date for the notice of termination of contract is with at 31-12-9999
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
        ContractService contractService = ContractService.ContractServiceFactory.getInstance();
        List<ContractVariationDTO> contractVariationDTOList = contractService.findAllContractVariationByContractNumber(contractNumber);
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

    private Integer updateInitialContractToBeExtinguished(ContractNewVersionDTO contractNewVersionExtinctedDTO){

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

    private Integer updateContractToBeExtinguished(ContractDTO contractExtinctedDTO){

        LocalDate dateOfExtinction = contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtinction()
                .getDateFrom().getValue();

        ContractDTO contractToUpdateDTO = contractManager.findLastTuplaOfContractByContractNumber(contractExtinctedDTO.getGmContractNumber());
        contractToUpdateDTO.setEndingDate(dateOfExtinction);

        ContractDTO contractExtinctToUpdateDTO = ContractDTO.create()
                .withId(contractToUpdateDTO.getId())
                .withEmployer(contractToUpdateDTO.getEmployer())
                .withEmployee(contractToUpdateDTO.getEmployee())
                .withContractType(contractToUpdateDTO.getContractType())
                .withGmContractNumber(contractToUpdateDTO.getGmContractNumber())
                .withVariationType(contractToUpdateDTO.getVariationType())
                .withStartDate(contractToUpdateDTO.getStartDate())
                .withExpectedEndDate(contractToUpdateDTO.getExpectedEndDate())
                .withModificationDate(contractToUpdateDTO.getModificationDate())
                .withEndingDate(contractToUpdateDTO.getEndingDate())
                .withContractScheduleJsonData(contractToUpdateDTO.getContractScheduleJsonData())
                .withLaborCategory(contractToUpdateDTO.getLaborCategory())
                .withQuoteAccountCode(contractToUpdateDTO.getQuoteAccountCode())
                .withIdentificationContractNumberINEM(contractToUpdateDTO.getIdentificationContractNumberINEM())
                .withPublicNotes(contractToUpdateDTO.getPublicNotes())
                .withPrivateNotes(contractToUpdateDTO.getPrivateNotes())
                .build();

        return contractManager.updateContract(contractExtinctToUpdateDTO);
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

    private ContractVariationDataSubfolder createContractExtinctionDataSubfolder(String additionalData, Duration duration){

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_TIME_FORMAT);


        ContractFullDataDTO allContractData = contractVariationMainController.getContractVariationParts().getContractSelector().getSelectionModel().getSelectedItem();

        String notificationType = ContractConstants.STANDARD_CONTRACT_EXTINCTION_TEXT;

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

        String gmContractNumber = allContractData.getContractNewVersion().getContractNumber() != null ? allContractData.getContractNewVersion().getContractNumber().toString() : null;

        return ContractVariationDataSubfolder.create()
                .withContractExtinction(true)
                .withContractExtension(false)
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
                .withSchedule(new HashSet<>())
                .withAdditionalData(additionalData)
                .withLaborCategory(allContractData.getContractNewVersion().getContractJsonData().getLaborCategory())
                .withGmContractNumber(gmContractNumber)
                .build();
    }

    private void printContractExtinctionDataSubfolder(ContractVariationDataSubfolder contractVariationDataSubfolder){

        ContractVariationDataDocumentCreator contractVariationDataDocumentCreator = new ContractVariationDataDocumentCreator(contractVariationMainController, contractVariationDataSubfolder);
        Path pathToContractVariationDataSubfolder = contractVariationDataDocumentCreator.retrievePathToContractVariationDataSubfolderPDF(contractVariationDataSubfolder);

        Map<String, String> attributes = new HashMap<>();
        attributes.put("papersize","A3");
        attributes.put("sides", "ONE_SIDED");
        attributes.put("chromacity","MONOCHROME");
        attributes.put("orientation","LANDSCAPE");

        try {
            String printOk = Printer.printPDF(pathToContractVariationDataSubfolder.toString(), attributes);
            Message.warningMessage(contractVariationMainController.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.CONTRACT_DATA_SUBFOLFER_TO_PRINTER_OK);
            if(!printOk.equals("ok")){
                Message.warningMessage(contractVariationMainController.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, Parameters.NO_PRINTER_FOR_THESE_ATTRIBUTES);
            }
        } catch (IOException | PrinterException e) {
            e.printStackTrace();
        }
    }

    public Boolean sendsMailContractVariationExtinction(ContractVariationDataSubfolder contractVariationDataSubfolder){

        Boolean isSendOk = false;
        Path pathOut;

        ContractVariationDataDocumentCreator contractVariationDocumentCreator = new ContractVariationDataDocumentCreator(contractVariationMainController, contractVariationDataSubfolder);

        String publicNotes = retrievePublicNotes();

        ContractDataToContractsAgent contractExtinctionDataToContractAgent = contractVariationDocumentCreator.createContractVariationDataDocumentForContractsAgent(publicNotes);

        pathOut = contractVariationDocumentCreator.retrievePathToContractDataToContractAgentPDF(contractExtinctionDataToContractAgent);

        String attachedFileName = contractExtinctionDataToContractAgent.toFileName().concat(ApplicationConstants.PDF_EXTENSION);

        Boolean documentToSendIsOpen = verifyDocumentStatus(attachedFileName);
        if(documentToSendIsOpen){
            Message.warningMessage(contractVariationMainController.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, EmailConstants.CLOSE_DOCUMENT_TO_SEND);

            return isSendOk;
        }

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

    private Boolean verifyDocumentStatus(String attachedFileName) {

        if (ApplicationConstants.OPERATING_SYSTEM.contains(ApplicationConstants.OS_LINUX)) {

            return SystemProcesses.isRunningInLinuxAndContains(attachedFileName.substring(0, 40), attachedFileName.substring(41, 60));
        }

        else return SystemProcesses.isRunningInWindowsAndContains(attachedFileName.substring(0, 40), attachedFileName.substring(41, 60));
    }
}
