package gmoldes.components.contract.contract_variation.controllers;

import com.lowagie.text.DocumentException;
import gmoldes.components.contract.contract_variation.components.ContractVariationContractVariations;
import gmoldes.components.contract.contract_variation.components.ContractVariationParts;
import gmoldes.components.contract.contract_variation.components.ContractVariationTypes;
import gmoldes.components.contract.controllers.ContractTypeController;
import gmoldes.components.contract.manager.ContractManager;
import gmoldes.components.contract.new_contract.components.ContractConstants;
import gmoldes.components.contract.new_contract.forms.ContractDataSubfolder;
import gmoldes.components.contract.new_contract.services.NewContractDataSubfolderPDFCreator;
import gmoldes.domain.contract.dto.*;
import gmoldes.domain.person.dto.StudyDTO;
import gmoldes.domain.person.manager.StudyManager;
import gmoldes.services.Printer;
import gmoldes.utilities.Message;
import gmoldes.utilities.OSUtils;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.Utilities;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

public class ContractExtensionController {

    private ContractVariationParts contractVariationParts;
    private ContractVariationTypes contractVariationTypes;
    private ContractVariationContractVariations contractVariationContractVariations;
    private ContractManager contractManager = new ContractManager();
    private Scene scene;

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

        String errorIsCorrectContractExtensionData = isCorrectContractExtensionData();

        if (errorIsCorrectContractExtensionData != null) {
            Message.warningMessage(scene.getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, errorIsCorrectContractExtensionData);

            return false;
        }

//        String errorPersistingContractExtension = persistContractExtension();
//
//        if (errorPersistingContractExtension != null) {
//
//            System.out.println(errorPersistingContractExtension);
//            Message.warningMessage(scene.getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, errorPersistingContractExtension);
//            Message.warningMessage(scene.getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.CONTRACT_EXTENSION_PERSISTENCE_NOT_OK);
//
//            return false;
//        }
//
//        String extinctionContractCause = contractVariationContractVariations.getContractVariationContractExtinction()
//                .getExtinctionCauseSelector().getSelectionModel().getSelectedItem().getVariation_description();
//
//        String holidaysText = contractVariationContractVariations.getContractVariationContractExtinction()
//                .getRbHolidaysYes().isSelected() ? "disfrutadas." : "no disfrutadas.";
//
//        StringBuilder sb = new StringBuilder();
//        sb.append(extinctionContractCause);
//        sb.append(". Vacaciones ");
//        sb.append(holidaysText);
//
//        ContractDataSubfolder contractDataSubfolder = createContractDataSubfolder(sb.toString());
//
//        printContractDataSubfolder(contractDataSubfolder);

        return true;
    }

    public String isCorrectContractExtensionData(){

        if(contractVariationParts.getContractSelector().getSelectionModel().getSelectedItem().getContractNewVersion().getExpectedEndDate() == null){

            return ContractConstants.SELECTED_CONTRACT_IS_NOT_EXTENDABLE;
        }

        if(Period.between(contractVariationContractVariations.getContractVariationContractExtension().getDateFrom().getValue(),
        contractVariationParts.getContractSelector().getValue().getContractNewVersion().getExpectedEndDate().plusDays(1)).getDays() > 0){

            return ContractConstants.INCORRECT_CONTRACT_EXTENSION_DATE_FROM;
        }

        if(contractVariationTypes.getDateNotification().getDate() == null){

            return ContractConstants.DATE_NOTIFICATION_NOT_ESTABLISHED;
        }

        if(contractVariationTypes.getHourNotification().getText() == null){

            return ContractConstants.HOUR_NOTIFICATION_NOT_ESTABLISHED;
        }

        if(contractVariationContractVariations.getContractVariationContractExtension().getDateFrom().getValue() == null
                || Period.between(contractVariationContractVariations.getContractVariationContractExtension().getDateFrom().getValue(),
                LocalDate.now()).getDays() > 3){

            return ContractConstants.ERROR_EXTENSION_CONTRACT_DATE_FROM;
        }

        if(contractVariationContractVariations.getContractVariationContractExtension().getDateTo().getValue() == null){

            return ContractConstants.ERROR_EXTENSION_CONTRACT_DATE_TO;
        }

        if(Period.between(contractVariationContractVariations.getContractVariationContractExtension().getDateFrom().getValue(),
                contractVariationContractVariations.getContractVariationContractExtension().getDateTo().getValue()).getDays() < 0){

            return ContractConstants.ERROR_EXTENSION_CONTRACT_INCOHERENT_DATES;

        }

        Boolean existFutureVariations = verifyExistenceFutureVariationsOfSelectedContract();
        if(existFutureVariations) {

            return ContractConstants.EXIST_FUTURE_VARIATION_OF_SELECTED_CONTRACT;
        }

        System.out.println("Aparentemente está todo correcto.\n");

        return null;
        }

    private Boolean verifyExistenceFutureVariationsOfSelectedContract(){

        Integer contractNumber = contractVariationParts.getContractSelector().getSelectionModel().getSelectedItem().getContractNewVersion().getContractNumber();
        LocalDate dateFromSearch = contractVariationContractVariations.getContractVariationContractExtension().getDateFrom().getValue();

        List<ContractVariationDTO> contractVariationDTOList = contractManager.findAllContractVariationsAfterDateByContractNumber(contractNumber, dateFromSearch);
        if(!contractVariationDTOList.isEmpty()){
            return true;
        }

        return false;
    }

    public String persistContractExtension(){

        ContractNewVersionDTO contractNewVersionExtinctedDTO = contractVariationParts
                .getContractSelector().getSelectionModel().getSelectedItem().getContractNewVersion();

        if(updateLastContractVariation(contractNewVersionExtinctedDTO) == null) {

            return ContractConstants.ERROR_UPDATING_LAST_CONTRACT_VARIATION_RECORD;
        }

        if(persistNewContractVariation(contractNewVersionExtinctedDTO) == null) {

            return ContractConstants.ERROR_INSERTING_NEW_EXTINCTION_RECORD_IN_CONTRACT_VARIATION;
        }

        if(updateInitialContractOfContractExtinction(contractNewVersionExtinctedDTO) == null) {

            return ContractConstants.ERROR_UPDATING_EXTINCTION_DATE_IN_INITIAL_CONTRACT;
        }

        Message.warningMessage(scene.getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.CONTRACT_EXTINCTION_PERSISTENCE_OK);

        return null;
    }

    private Integer updateLastContractVariation(ContractNewVersionDTO contractNewVersionExtinctedDTO){

        LocalDate dateOfExtinction = contractVariationContractVariations.getContractVariationContractExtinction()
                .getDateFrom().getValue();

        contractNewVersionExtinctedDTO.setModificationDate(dateOfExtinction);
        contractNewVersionExtinctedDTO.setEndingDate(dateOfExtinction);

        return contractManager.updateContractVariation(contractNewVersionExtinctedDTO);
    }

    private Integer persistNewContractVariation(ContractNewVersionDTO contractNewVersionExtinctedDTO){

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

    private Integer updateInitialContractOfContractExtinction(ContractNewVersionDTO contractNewVersionExtinctedDTO){

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

    private ContractDataSubfolder createContractDataSubfolder(String additionalData){

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
                .withStartDate(null)
                .withEndDate(startDate)
                .withDayOfWeekSet(dayOfWeekSet)


                .withDurationDays(Duration.ZERO)
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
