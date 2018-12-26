package gmoldes.components.contract.contract_variation.controllers;

import com.lowagie.text.DocumentException;
import gmoldes.ApplicationMainController;
import gmoldes.components.contract.contract_variation.events.CompatibleVariationEvent;
import gmoldes.components.contract.contract_variation.events.ContractVariationPersistenceEvent;
import gmoldes.components.contract.contract_variation.events.MessageEvent;
import gmoldes.components.contract.contract_variation.forms.ContractExtensionDataSubfolder;
import gmoldes.components.contract.contract_variation.services.ContractExtensionDataSubfolderPDFCreator;
import gmoldes.components.contract.controllers.ContractTypeController;
import gmoldes.components.contract.manager.ContractManager;
import gmoldes.components.contract.new_contract.components.ContractConstants;
import gmoldes.components.contract.new_contract.components.ContractParameters;
import gmoldes.domain.contract.dto.*;
import gmoldes.domain.contractjsondata.ContractJsonData;
import gmoldes.domain.person.dto.StudyDTO;
import gmoldes.domain.person.manager.StudyManager;
import gmoldes.domain.traceability_contract_documentation.dto.TraceabilityContractDocumentationDTO;
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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ContractExtensionController {

    private ContractVariationMainController contractVariationMainController;

    private ContractManager contractManager = new ContractManager();

    private static final Integer VARIATION_TYPE_ID_FOR_CONTRACT_EXTENSION = 220;
    private static final String HOURS_WORK_WEEK_TEXT = " horas de trabajo por semana ";

    public ContractExtensionController(ContractVariationMainController contractVariationMainController){

        this.contractVariationMainController = contractVariationMainController;
    }

     public Boolean manageContractExtension() {

        ContractVariationPersistenceEvent persistenceEvent = persistContractExtension();

        if(persistenceEvent.getPersistenceIsOk()){

            Message.warningMessage(contractVariationMainController.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, persistenceEvent.getPersistenceMessage());

            String durationContractExtensionText = contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtension().getContractExtensionDuration().getText();
            Duration durationDays = Utilities.convertIntegerToDuration(Integer.parseInt(durationContractExtensionText));

            StringBuilder sb = new StringBuilder();

            String publicNotes = contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtension().getPublicNotes().getText();
            sb.append(publicNotes);

            ContractExtensionDataSubfolder contractExtensionDataSubfolder = createContractExtensionDataSubfolder(sb.toString(), durationDays);

            printContractExtensionDataSubfolder(contractExtensionDataSubfolder);

            return true;
        }


        System.out.println(persistenceEvent.getPersistenceMessage());
        Message.warningMessage(contractVariationMainController.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, persistenceEvent.getPersistenceMessage());
        Message.warningMessage(contractVariationMainController.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.CONTRACT_EXTENSION_PERSISTENCE_NOT_OK);

        return false;
    }

    public MessageEvent verifyIsCorrectContractExtensionData(){

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

        if(Period.between(contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtension().getDateFrom().getValue(),
                contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtension().getDateTo().getValue()).getDays() < 0){

            return new MessageEvent(ContractConstants.ERROR_EXTENSION_CONTRACT_INCOHERENT_DATES);
        }

        return new MessageEvent(ContractConstants.NECESSARY_DATA_FOR_VARIATION_CONTRACT_HAVE_BEEN_INTRODUCED);
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
                        true,
                        null,
                        null,
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
                    true,
                    null,
                    null,
                    ContractConstants.MAXIMUM_LEGAL_NUMBER_OF_MONTHS_OF_CONTRACT_IS_EXCEEDED);
        }

        System.out.println("Número meses más prórroga (en meses promedio): " + numberDaysOfContractDuration/ContractParameters.AVERAGE_OF_DAYS_IN_A_MONTH);

        // 3. The initial date of the extension of a contract can not be earlier than the end date established for it
        LocalDate contractExpectedEndDate = contractVariationMainController.getContractVariationParts().getContractSelector().getSelectionModel().getSelectedItem().getContractNewVersion().getExpectedEndDate();
        if(contractExtensionDateFrom.isBefore(contractExpectedEndDate) ||
                contractExtensionDateFrom.equals(contractExpectedEndDate)){
            return new CompatibleVariationEvent(
                    true,
                    null,
                    null,
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
                            true,
                            null,
                            null,
                            ContractConstants.EXIST_PREVIOUS_INCOMPATIBLE_CONTRACT_VARIATION_EXTENSION);
                }
            }
        }

        return new CompatibleVariationEvent(true, false, false, "");
    }

    public ContractVariationPersistenceEvent persistContractExtension(){

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

    private ContractExtensionDataSubfolder createContractExtensionDataSubfolder(String additionalData, Duration duration){

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parameters.DEFAULT_DATE_FORMAT);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(Parameters.DEFAULT_TIME_FORMAT);


        ContractFullDataDTO allContractData = contractVariationMainController.getContractVariationParts().getContractSelector().getSelectionModel().getSelectedItem();

        String notificationType = Parameters.CONTRACT_EXTENSION_TEXT;

        String clientNotificationDate = dateFormatter.format(contractVariationMainController.getContractVariationTypes().getDateNotification().getDate());
        String clientNotificationHour = contractVariationMainController.getContractVariationTypes().getHourNotification().getTime().format(timeFormatter);

        String birthDate = allContractData.getEmployee().getFechanacim() != null ? dateFormatter.format(allContractData.getEmployee().getFechanacim()) : null;

        String startDate = dateFormatter.format(contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtension().getDateFrom().getValue());
        String endDate = dateFormatter.format(contractVariationMainController.getContractVariationContractVariations().getContractVariationContractExtension().getDateTo().getValue());

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

        String contractDescription = contractTypeDTO.getColloquial() + ", " + allContractData.getContractType().getContractDescription() +
                " [" + allContractData.getContractNewVersion().getContractJsonData().getWeeklyWorkHours() + HOURS_WORK_WEEK_TEXT + "]";

        String durationDays = duration != null ? Long.toString(duration.toDays()) : "";


        return ContractExtensionDataSubfolder.create()
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

    private void printContractExtensionDataSubfolder(ContractExtensionDataSubfolder contractDataSubfolder){
        Path pathToContractExtensionDataSubfolder = retrievePathToContractExtensionDataSubfolderPDF(contractDataSubfolder);

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

    private Path retrievePathToContractExtensionDataSubfolderPDF(ContractExtensionDataSubfolder contractDataSubfolder){
        Path pathOut = null;

        final Optional<Path> maybePath = OSUtils.TemporalFolderUtils.tempFolder();
        String temporalDir = maybePath.get().toString();

        Path pathToContractExtensionDataSubfolder = Paths.get(Parameters.USER_HOME, temporalDir, contractDataSubfolder.toFileName().concat(Parameters.PDF_EXTENSION));
        try {
            Files.createDirectories(pathToContractExtensionDataSubfolder.getParent());
            pathOut = ContractExtensionDataSubfolderPDFCreator.createContractExtensionDataSubfolderPDF(contractDataSubfolder, pathToContractExtensionDataSubfolder);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }

        return pathOut;
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
