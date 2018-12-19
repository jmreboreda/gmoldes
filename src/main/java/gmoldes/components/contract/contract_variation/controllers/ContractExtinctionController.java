package gmoldes.components.contract.contract_variation.controllers;

import com.lowagie.text.DocumentException;
import gmoldes.ApplicationMainController;
import gmoldes.components.contract.contract_variation.components.ContractVariationContractVariations;
import gmoldes.components.contract.contract_variation.components.ContractVariationParts;
import gmoldes.components.contract.contract_variation.components.ContractVariationTypes;
import gmoldes.components.contract.contract_variation.events.CompatibleVariationEvent;
import gmoldes.components.contract.contract_variation.events.MessageEvent;
import gmoldes.components.contract.contract_variation.forms.ContractExtinctionDataSubfolder;
import gmoldes.components.contract.contract_variation.services.ContractExtinctionDataSubfolderPDFCreator;
import gmoldes.components.contract.controllers.ContractTypeController;
import gmoldes.components.contract.manager.ContractManager;
import gmoldes.components.contract.new_contract.components.ContractConstants;
import gmoldes.domain.contract.dto.*;
import gmoldes.domain.person.dto.StudyDTO;
import gmoldes.domain.person.manager.StudyManager;
import gmoldes.domain.traceability_contract_documentation.dto.TraceabilityContractDocumentationDTO;
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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class ContractExtinctionController{

    private ContractVariationParts contractVariationParts;
    private ContractVariationTypes contractVariationTypes;
    private ContractVariationContractVariations contractVariationContractVariations;
    private ContractManager contractManager = new ContractManager();
    private Scene scene;

    private static final Integer EXTINCTION_CODE_BY_CONTRACT_SUBROGATION = 910;

    public ContractExtinctionController(
            Scene scene,
            ContractVariationParts contractVariationParts,
            ContractVariationTypes contractVariationTypes,
            ContractVariationContractVariations contractVariationContractVariations) {
        this.scene = scene;
        this.contractVariationParts = contractVariationParts;
        this.contractVariationTypes = contractVariationTypes;
        this.contractVariationContractVariations = contractVariationContractVariations;
    }

    public Boolean manageContractExtinction() {

        String errorPersistingContractExtinction = persistContractExtinction();
        if (errorPersistingContractExtinction != null) {

            Message.warningMessage(scene.getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, errorPersistingContractExtinction);
            Message.warningMessage(scene.getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.CONTRACT_EXTINCTION_PERSISTENCE_NOT_OK);

            return false;
        }

        String extinctionContractCause = contractVariationContractVariations.getContractVariationContractExtinction()
                .getExtinctionCauseSelector().getSelectionModel().getSelectedItem().getVariation_description();

        String holidaysUsedText = contractVariationContractVariations.getContractVariationContractExtinction()
                .getRbHolidaysYes().isSelected() ? "disfrutadas." : "no disfrutadas.";

        StringBuilder sb = new StringBuilder();
        sb.append(extinctionContractCause);
        sb.append(". Vacaciones ");
        sb.append(holidaysUsedText);

        ContractExtinctionDataSubfolder contractExtinctionDataSubfolder = createContractExtinctionDataSubfolder(sb.toString());

        printContractExtinctionDataSubfolder(contractExtinctionDataSubfolder);

        return true;
    }

    public MessageEvent verifyIsCorrectContractExtinctionData(){

            if(contractVariationTypes.getDateNotification().getDate() == null){

                return new MessageEvent(ContractConstants.DATE_NOTIFICATION_NOT_ESTABLISHED);
            }

            if(contractVariationTypes.getHourNotification().getText() == null){

                return new MessageEvent(ContractConstants.HOUR_NOTIFICATION_NOT_ESTABLISHED);
            }

            if(contractVariationContractVariations.getContractVariationContractExtinction().getExtinctionCauseSelector().getSelectionModel().getSelectedItem() == null){

                return new MessageEvent(ContractConstants.EXTINCTION_CAUSE_NOT_ESTABLISHED);
            }

            if(contractVariationContractVariations.getContractVariationContractExtinction().getDateFrom().getValue() == null){

                return new MessageEvent(ContractConstants.ERROR_IN_EXTINCTION_DATA);
            }

            if(!contractVariationContractVariations.getContractVariationContractExtinction().getRbHolidaysYes().isSelected() &&
                    !contractVariationContractVariations.getContractVariationContractExtinction().getRbHolidaysNo().isSelected()){

                return new MessageEvent(ContractConstants.HOLIDAYS_SITUATION_NOT_ESTABLISHED);
            }

        return new MessageEvent(ContractConstants.NECESSARY_DATA_FOR_VARIATION_CONTRACT_HAVE_BEEN_INTRODUCED);
    }

    public CompatibleVariationEvent checkExistenceIncompatibleVariationsForContractExtinction() {

        ApplicationMainController applicationMainController = new ApplicationMainController();

        Integer selectedContractNumber = contractVariationParts.getContractSelector().getSelectionModel().getSelectedItem().getContractNewVersion().getContractNumber();

        // 1. Expiration date requested for the contract is higher than the expected termination date
        LocalDate expectedEndDate = contractVariationParts.getContractSelector().getValue().getContractNewVersion().getExpectedEndDate();
        LocalDate extinctionDate = contractVariationContractVariations.getContractVariationContractExtinction().getDateFrom().getValue();

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

        return new CompatibleVariationEvent(true, false, false, "");
    }

    public String persistContractExtinction(){

        ContractNewVersionDTO contractNewVersionToBeExtinguished = contractVariationParts
                .getContractSelector().getSelectionModel().getSelectedItem().getContractNewVersion();

        if(updateLastVariationOfContractToBeExtinguished(contractNewVersionToBeExtinguished) == null) {

            return ContractConstants.ERROR_UPDATING_LAST_CONTRACT_VARIATION_RECORD;
        }

        if(persistNewContractExtinctionVariation(contractNewVersionToBeExtinguished) == null) {

            return ContractConstants.ERROR_INSERTING_NEW_EXTINCTION_RECORD_IN_CONTRACT_VARIATION;
        }

        if(updateInitialContractOfContractToBeExtinguished(contractNewVersionToBeExtinguished) == null) {

            return ContractConstants.ERROR_UPDATING_EXTINCTION_DATE_IN_INITIAL_CONTRACT;
        }

        if(persistTraceabilityControlData() == null){

            return ContractConstants.ERROR_PERSISTING_TRACEABILITY_CONTROL_DATA;
        }

        Message.warningMessage(scene.getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.CONTRACT_EXTINCTION_PERSISTENCE_OK);

        return null;
    }

    private Integer persistTraceabilityControlData(){

        // In a contract extinction the date for the notice of termination of contract is set at 31-12-9999
        LocalDate contractEndNoticeToSave = LocalDate.of(9999, 12, 31);

        Integer contractVariationType = contractVariationContractVariations.getContractVariationContractExtinction()
                .getExtinctionCauseSelector().getSelectionModel().getSelectedItem().getId_variation();
        Integer contractNumber = contractVariationParts.getContractSelector().getSelectionModel().getSelectedItem().getContractNewVersion().getContractNumber();
        LocalDate dateFrom = contractVariationContractVariations.getContractVariationContractExtinction().getDateFrom().getValue();

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

        LocalDate dateOfExtinction = contractVariationContractVariations.getContractVariationContractExtinction()
                .getDateFrom().getValue();

        contractNewVersionExtinctedDTO.setModificationDate(dateOfExtinction);
        contractNewVersionExtinctedDTO.setEndingDate(dateOfExtinction);

        return contractManager.updateContractVariation(contractNewVersionExtinctedDTO);
    }

    private Integer persistNewContractExtinctionVariation(ContractNewVersionDTO contractNewVersionExtinctedDTO){

        Integer contractVariationExtinctionCause = contractVariationContractVariations.getContractVariationContractExtinction()
                .getExtinctionCauseSelector().getSelectionModel().getSelectedItem().getId_variation();

        LocalDate dateOfExtinction = contractVariationContractVariations.getContractVariationContractExtinction()
                .getDateFrom().getValue();

        contractNewVersionExtinctedDTO.setId(null);
        contractNewVersionExtinctedDTO.setVariationType(contractVariationExtinctionCause);
        contractNewVersionExtinctedDTO.setStartDate(dateOfExtinction);
        contractNewVersionExtinctedDTO.setModificationDate(dateOfExtinction);
        contractNewVersionExtinctedDTO.setEndingDate(dateOfExtinction);

        return contractManager.saveContractVariation(contractNewVersionExtinctedDTO);
    }

    private Integer updateInitialContractOfContractToBeExtinguished(ContractNewVersionDTO contractNewVersionExtinctedDTO){

        LocalDate dateOfExtinction = contractVariationContractVariations.getContractVariationContractExtinction()
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

    private ContractExtinctionDataSubfolder createContractExtinctionDataSubfolder(String additionalData){

        SimpleDateFormat dateFormatter = new SimpleDateFormat(Parameters.DEFAULT_DATE_FORMAT);

        ContractFullDataDTO allContractData = contractVariationParts.getContractSelector().getSelectionModel().getSelectedItem();

        String notificationType = "";
        if(contractVariationTypes.getRbContractExtinction().isSelected()){
            notificationType = Parameters.CONTRACT_EXTINCTION_TEXT;
        }
        if(contractVariationTypes.getRbContractConversion().isSelected()){
            notificationType = Parameters.CONTRACT_CONVERSION_TEXT;
        }
        if(contractVariationTypes.getRbContractExtension().isSelected()){
            notificationType = Parameters.CONTRACT_EXTENSION_TEXT;
        }

        LocalDate clientNotificationDate = contractVariationTypes.getDateNotification().getDate();
        LocalTime clientNotificationHour = LocalTime.parse(contractVariationTypes.getHourNotification().getText());

        String birthDate = allContractData.getEmployee().getFechanacim() != null ? dateFormatter.format(allContractData.getEmployee().getFechanacim()) : null;

        LocalDate startDate = contractVariationContractVariations.getContractVariationContractExtinction().getDateFrom().getValue();

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
                .withStartDate(null)
                .withEndDate(startDate)
                .withDayOfWeekSet(dayOfWeekSet)
                .withDurationDays("")
                .withSchedule(new HashSet<>())
                .withAdditionalData(additionalData)
                .withLaborCategory(allContractData.getContractNewVersion().getContractJsonData().getLaborCategory())
                .build();
    }

    private void printContractExtinctionDataSubfolder(ContractExtinctionDataSubfolder contractExtinctionDataSubfolder){
        Path pathToContractExtinctionDataSubfolder = retrievePathToContractExtinctionDataSubfolderPDF(contractExtinctionDataSubfolder);

        Map<String, String> attributes = new HashMap<>();
        attributes.put("papersize","A3");
        attributes.put("sides", "ONE_SIDED");
        attributes.put("chromacity","MONOCHROME");
        attributes.put("orientation","LANDSCAPE");

        try {
            String printOk = Printer.printPDF(pathToContractExtinctionDataSubfolder.toString(), attributes);
            Message.warningMessage(scene.getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.CONTRACT_DATA_SUBFOLFER_TO_PRINTER_OK);
            if(!printOk.equals("ok")){
                Message.warningMessage(scene.getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, Parameters.NO_PRINTER_FOR_THESE_ATTRIBUTES);
            }
        } catch (IOException | PrinterException e) {
            e.printStackTrace();
        }
    }

    private Path retrievePathToContractExtinctionDataSubfolderPDF(ContractExtinctionDataSubfolder contractExtinctionDataSubfolder){
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
