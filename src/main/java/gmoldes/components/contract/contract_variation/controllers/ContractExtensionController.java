package gmoldes.components.contract.contract_variation.controllers;

import com.lowagie.text.DocumentException;
import gmoldes.ApplicationMainController;
import gmoldes.ApplicationMainManager;
import gmoldes.components.contract.contract_variation.components.ContractVariationContractVariations;
import gmoldes.components.contract.contract_variation.components.ContractVariationParts;
import gmoldes.components.contract.contract_variation.components.ContractVariationTypes;
import gmoldes.components.contract.contract_variation.events.CompatibleVariationEvent;
import gmoldes.components.contract.contract_variation.events.MessageEvent;
import gmoldes.components.contract.controllers.ContractTypeController;
import gmoldes.components.contract.manager.ContractManager;
import gmoldes.components.contract.new_contract.components.ContractConstants;
import gmoldes.components.contract.new_contract.components.ContractParameters;
import gmoldes.components.contract.new_contract.forms.ContractDataSubfolder;
import gmoldes.components.contract.new_contract.services.NewContractDataSubfolderPDFCreator;
import gmoldes.domain.contract.Contract;
import gmoldes.domain.contract.dto.*;
import gmoldes.domain.contractjsondata.ContractJsonData;
import gmoldes.domain.person.dto.StudyDTO;
import gmoldes.domain.person.manager.StudyManager;
import gmoldes.services.Printer;
import gmoldes.utilities.Message;
import gmoldes.utilities.OSUtils;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.Utilities;
import javafx.scene.Scene;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ContractExtensionController {

    private ContractVariationParts contractVariationParts;
    private ContractVariationTypes contractVariationTypes;
    private ContractVariationContractVariations contractVariationContractVariations;
    private ContractManager contractManager = new ContractManager();
    private Scene scene;

    private static final Integer VARIATION_TYPE_ID_FOR_CONTRACT_EXTENSION = 220;
    private static final String HOURS_WORK_WEEK_TEXT = " horas de trabajo por semana ";

    public ContractExtensionController(
            Scene scene,
            ContractVariationParts contractVariationParts,
            ContractVariationTypes contractVariationTypes,
            ContractVariationContractVariations contractVariationContractVariations) {
        this.scene = scene;
        this.contractVariationParts = contractVariationParts;
        this.contractVariationTypes = contractVariationTypes;
        this.contractVariationContractVariations = contractVariationContractVariations;
    }

     public Boolean manageContractExtension() {

        String errorPersistingContractExtension = persistContractExtension();

        if (errorPersistingContractExtension != null) {

            System.out.println(errorPersistingContractExtension);
            Message.warningMessage(scene.getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, errorPersistingContractExtension);
            Message.warningMessage(scene.getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.CONTRACT_EXTENSION_PERSISTENCE_NOT_OK);

            return false;
        }

        String durationContractExtensionText = contractVariationContractVariations.getContractVariationContractExtension().getContractExtensionDuration().getText();
        Duration durationDays = Utilities.convertIntegerToDuration(Integer.parseInt(durationContractExtensionText));

        StringBuilder sb = new StringBuilder();
        sb.append(" ");

        ContractDataSubfolder contractDataSubfolder = createContractDataSubfolder(sb.toString(), durationDays);

        printContractDataSubfolder(contractDataSubfolder);

        return true;
    }

    public MessageEvent verifyIsCorrectContractExtensionData(){

        if(contractVariationTypes.getDateNotification().getDate() == null){

            return new MessageEvent(ContractConstants.DATE_NOTIFICATION_NOT_ESTABLISHED);
        }

        if(contractVariationTypes.getHourNotification().getText() == null){

            return new MessageEvent(ContractConstants.HOUR_NOTIFICATION_NOT_ESTABLISHED);
        }

        if(contractVariationContractVariations.getContractVariationContractExtension().getDateFrom().getValue() == null){

            return new MessageEvent(ContractConstants.ERROR_EXTENSION_CONTRACT_DATE_FROM);
        }

        if(contractVariationContractVariations.getContractVariationContractExtension().getDateTo().getValue() == null){

            return new MessageEvent(ContractConstants.ERROR_EXTENSION_CONTRACT_DATE_TO);
        }

        if(Period.between(contractVariationContractVariations.getContractVariationContractExtension().getDateFrom().getValue(),
                contractVariationContractVariations.getContractVariationContractExtension().getDateTo().getValue()).getDays() < 0){

            return new MessageEvent(ContractConstants.ERROR_EXTENSION_CONTRACT_INCOHERENT_DATES);
        }

        return new MessageEvent(ContractConstants.NECESSARY_DATA_FOR_VARIATION_CONTRACT_HAVE_BEEN_INTRODUCED);
    }

    public CompatibleVariationEvent checkExistenceIncompatibleVariationsForContractExtension() {

        ApplicationMainController applicationMainController = new ApplicationMainController();

        Integer contractNumber = contractVariationParts.getContractSelector().getSelectionModel().getSelectedItem().getContractNewVersion().getContractNumber();
        LocalDate contractExtensionDateFrom = contractVariationContractVariations.getContractVariationContractExtension().getDateFrom().getValue();
        LocalDate contractExtensionDateTo = contractVariationContractVariations.getContractVariationContractExtension().getDateTo().getValue();

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
            while (whileCounter < contractNewVersionDTOList.size()) {

                LocalDate previousDate = contractNewVersionDTOList.get(whileCounter).getStartDate();
                LocalDate nextDate = contractNewVersionDTOList.get(whileCounter + 1).getStartDate();

                numberDaysOfContractDuration = numberDaysOfContractDuration + ChronoUnit.DAYS.between(previousDate, nextDate) + 1;

                whileCounter++;
            }
        }

        if(numberDaysOfContractDuration/30.4375 > ContractParameters.NUMBER_MONTHS_MAXIMUM_DURATION_INITIAL_CONTRACT_PLUS_ITS_EXTENSIONS){

            System.out.println("Número meses más prórroga: " + numberDaysOfContractDuration/30.4375);

            return new CompatibleVariationEvent(
                    true,
                    null,
                    null,
                    ContractConstants.MAXIMUM_LEGAL_NUMBER_OF_MONTHS_OF_CONTRACT_IS_EXCEEDED);
        }

        System.out.println("Número meses más prórroga: " + numberDaysOfContractDuration/30.4375);

        // 3. The initial date of the extension of a contract can not be earlier than the end date established for it
        LocalDate contractExpectedEndDate = contractVariationParts.getContractSelector().getSelectionModel().getSelectedItem().getContractNewVersion().getExpectedEndDate();
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

    public String persistContractExtension(){

        ContractNewVersionDTO contractNewVersionExtendedDTO = contractVariationParts
                .getContractSelector().getSelectionModel().getSelectedItem().getContractNewVersion();

        if(updateLastContractVariation(contractNewVersionExtendedDTO) == null) {

            return ContractConstants.ERROR_UPDATING_LAST_CONTRACT_VARIATION_RECORD;
        }

        if(persistNewContractVariation(contractNewVersionExtendedDTO) == null) {

            return ContractConstants.ERROR_INSERTING_NEW_EXTINCTION_RECORD_IN_CONTRACT_VARIATION;
        }

        if(updateInitialContractOfContractExtension(contractNewVersionExtendedDTO) == null) {

            return ContractConstants.ERROR_UPDATING_EXTINCTION_DATE_IN_INITIAL_CONTRACT;
        }

        Message.warningMessage(scene.getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.CONTRACT_EXTENSION_PERSISTENCE_OK);

        return null;
    }

    private Integer updateLastContractVariation(ContractNewVersionDTO contractNewVersionExtendedDTO){

        ApplicationMainController applicationMainController = new ApplicationMainController();

        Integer contractNumber = contractNewVersionExtendedDTO.getContractNumber();
        List<ContractVariationDTO> contractVariationDTOList = applicationMainController.findAllContractVariationByContractNumber(contractNumber);
        if(contractVariationDTOList.isEmpty())
        {
            return 0;
        }

        LocalDate initialDateOfExtension = contractVariationContractVariations.getContractVariationContractExtension()
                .getDateFrom().getValue();

        ContractNewVersionDTO contractNewVersionDTOtoUpdate = new ContractNewVersionDTO();
        for(ContractVariationDTO contractVariationDTO : contractVariationDTOList){
            if(contractVariationDTO.getModificationDate() == null){

                contractNewVersionDTOtoUpdate = ContractNewVersionDTO.create()
                        .withId(contractVariationDTO.getId())
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

        LocalDate initialDateOfExtension = contractVariationContractVariations.getContractVariationContractExtension()
                .getDateFrom().getValue();

        LocalDate finalDateOfExtension = contractVariationContractVariations.getContractVariationContractExtension()
                .getDateTo().getValue();

        String identificationContractNumberINEM = contractNewVersionExtendedDTO.getContractJsonData().getIdentificationContractNumberINEM();
        String newIdentificationContractNumberINEM = identificationContractNumberINEM + "-1";

        ContractJsonData contractJsonData = contractNewVersionExtendedDTO.getContractJsonData();
        contractJsonData.setIdentificationContractNumberINEM(newIdentificationContractNumberINEM);

        contractNewVersionExtendedDTO.setId(null);
        contractNewVersionExtendedDTO.setVariationType(VARIATION_TYPE_ID_FOR_CONTRACT_EXTENSION);
        contractNewVersionExtendedDTO.setStartDate(initialDateOfExtension);
        contractNewVersionExtendedDTO.setExpectedEndDate(finalDateOfExtension);
        contractNewVersionExtendedDTO.setModificationDate(null);
        contractNewVersionExtendedDTO.setEndingDate(null);
        contractNewVersionExtendedDTO.setContractJsonData(contractJsonData);

        return contractManager.saveContractVariation(contractNewVersionExtendedDTO);
    }

    private Integer updateInitialContractOfContractExtension(ContractNewVersionDTO contractNewVersionExtendedDTO){

        LocalDate initialDateOfExtension = contractVariationContractVariations.getContractVariationContractExtension()
                .getDateFrom().getValue();

        InitialContractDTO initialContractToUpdateDTO = contractManager.findLastTuplaOfInitialContractByContractNumber(contractNewVersionExtendedDTO.getContractNumber());
        if(initialContractToUpdateDTO.getModificationDate() != null) {

            return 0;
        }

        initialContractToUpdateDTO.setModificationDate(initialDateOfExtension);

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

    private ContractDataSubfolder createContractDataSubfolder(String additionalData, Duration duration){

        SimpleDateFormat dateFormatter = new SimpleDateFormat(Parameters.DEFAULT_DATE_FORMAT);

        ContractFullDataDTO allContractData = contractVariationParts.getContractSelector().getSelectionModel().getSelectedItem();

        String notificationType = Parameters.CONTRACT_EXTENSION_TEXT;

        LocalDate clientNotificationDate = contractVariationTypes.getDateNotification().getDate();
        LocalTime clientNotificationHour = LocalTime.parse(contractVariationTypes.getHourNotification().getText());

        String birthDate = allContractData.getEmployee().getFechanacim() != null ? dateFormatter.format(allContractData.getEmployee().getFechanacim()) : null;

        LocalDate startDate = contractVariationContractVariations.getContractVariationContractExtension().getDateFrom().getValue();
        LocalDate endDate = contractVariationContractVariations.getContractVariationContractExtension().getDateTo().getValue();

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

        return ContractDataSubfolder.create()
                .withNotificationType(notificationType)
                .withOfficialContractNumber(allContractData.getContractNewVersion().getContractJsonData().getIdentificationContractNumberINEM())
                .withEmployerFullName(allContractData.getEmployer().getPersonOrCompanyName())
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
                .withDurationDays(duration)


                .withSchedule(new HashSet<>())
                .withAdditionalData(additionalData)
                .withLaborCategory(allContractData.getContractNewVersion().getContractJsonData().getLaborCategory())
                .build();
    }

    private void printContractDataSubfolder(ContractDataSubfolder contractDataSubfolder){
        Path pathToContractDataSubfolder = retrievePathToContractDataSubfolderPDF(contractDataSubfolder);

        Map<String, String> attributes = new HashMap<>();
        attributes.put("papersize","A3");
        attributes.put("sides", "ONE_SIDED");
        attributes.put("chromacity","MONOCHROME");
        attributes.put("orientation","LANDSCAPE");

        try {
            String printOk = Printer.printPDF(pathToContractDataSubfolder.toString(), attributes);
            Message.warningMessage(scene.getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.CONTRACT_DATA_SUBFOLFER_TO_PRINTER_OK);
            if(!printOk.equals("ok")){
                Message.warningMessage(scene.getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, Parameters.NO_PRINTER_FOR_THESE_ATTRIBUTES);
            }
        } catch (IOException | PrinterException e) {
            e.printStackTrace();
        }
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
