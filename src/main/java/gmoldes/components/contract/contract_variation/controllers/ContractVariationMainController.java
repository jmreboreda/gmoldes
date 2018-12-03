package gmoldes.components.contract.contract_variation.controllers;

import com.lowagie.text.DocumentException;
import gmoldes.ApplicationMainController;
import gmoldes.components.ViewLoader;
import gmoldes.components.contract.contract_variation.components.*;
import gmoldes.components.contract.contract_variation.events.ClientChangeEvent;
import gmoldes.components.contract.controllers.ContractTypeController;
import gmoldes.components.contract.manager.ContractManager;
import gmoldes.components.contract.new_contract.components.ContractConstants;
import gmoldes.components.contract.new_contract.forms.ContractDataSubfolder;
import gmoldes.components.contract.new_contract.services.NewContractDataSubfolderPDFCreator;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.dto.ContractFullDataDTO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract.dto.ContractTypeDTO;
import gmoldes.domain.contract.dto.InitialContractDTO;
import gmoldes.domain.person.dto.StudyDTO;
import gmoldes.domain.person.manager.StudyManager;
import gmoldes.services.Printer;
import gmoldes.utilities.Message;
import gmoldes.utilities.OSUtils;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.Utilities;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ContractVariationMainController extends VBox {

    private static final Logger logger = Logger.getLogger(ContractVariationMainController.class.getSimpleName());
    private static final String CONTRACT_VARIATION_MAIN_FXML = "/fxml/contract_variations/contractvariation_main.fxml";

    private Parent parent;

    private ApplicationMainController applicationMainController = new ApplicationMainController();
    private ContractManager contractManager = new ContractManager();

    @FXML
    private ToggleGroup contractVariationToggleGroup;
    @FXML
    private ContractVariationHeader contractVariationHeader;
    @FXML
    private ContractVariationParts contractVariationParts;
    @FXML
    private ContractVariationContractData contractVariationContractData;
    @FXML
    private ContractVariationContractVariations contractVariationContractVariations;
    @FXML
    private ContractVariationActionComponents contractVariationActionComponents;


    @FXML
    public void initialize() {

        contractVariationParts.setOnClientSelectorAction(this::onChangeEmployer);
        contractVariationParts.setOnContractSelectorAction(this::onContractSelectorAction);

        contractVariationContractVariations.setOnContractExtinction(this::onContractExtinction);

        contractVariationToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
            }
        });

        contractVariationContractVariations.getContractVariationContractExtinction().getRbContractExtinction().setToggleGroup(contractVariationToggleGroup);
        contractVariationContractVariations.getContractVariationContractExtension().getRbContractExtension().setToggleGroup(contractVariationToggleGroup);
        contractVariationContractVariations.getContractVariationContractConversion().getRbContractConversion().setToggleGroup(contractVariationToggleGroup);

        contractVariationContractVariations.setDisable(true);

        contractVariationActionComponents.setOnOkButton(this::onOkButton);
        contractVariationActionComponents.setOnExitButton(this::onExitButton);

        clientSelectorLoadData();
    }

    public ContractVariationMainController() {
        logger.info("Initializing ContractVariation Main fxml");
        this.parent = ViewLoader.load(this, CONTRACT_VARIATION_MAIN_FXML);
    }

    private void clientSelectorLoadData() {
        contractVariationParts.getClientSelector().getItems().clear();
        contractVariationParts.getContractSelector().getItems().clear();
        LocalDate workDate = contractVariationParts.getInForceDate().getValue();
        contractVariationContractData.clearAllContractData();
        List<ClientDTO> clientDTOList = applicationMainController.findAllClientWithContractInForceAtDate(workDate);

        List<ClientDTO> clientDTOListSorted = clientDTOList
                .stream()
                .sorted(Comparator.comparing(ClientDTO::getPersonOrCompanyName)).collect(Collectors.toList());

        ObservableList<ClientDTO> clientDTOS = FXCollections.observableArrayList(clientDTOListSorted);
        contractVariationParts.loadDataInClientSelector(clientDTOS);
        contractVariationParts.getClientSelector().setItems(clientDTOS);
    }

    private void onChangeEmployer(ClientChangeEvent event){

        LocalDate selectedDate = event.getDate();
        ClientDTO selectedClient = event.getClient();
        refreshContractSelectorData(selectedClient, selectedDate);
    }

    private void onContractSelectorAction(ActionEvent event){

        if(contractVariationParts.getContractSelector().getSelectionModel().getSelectedItem() == null){
            contractVariationContractVariations.setDisable(true);
            return;
        }

        ContractFullDataDTO selectedContract = contractVariationParts.getContractSelector().getSelectionModel().getSelectedItem();
        contractVariationContractData.setAllContractData(selectedContract);

        contractVariationContractVariations.setDisable(false);
    }

    private void onContractExtinction(MouseEvent event){

        contractVariationContractVariations.getContractVariationContractExtinction().componentsClear();

        if(!isCorrectDateToContractVariation()){
            contractVariationContractVariations.getContractVariationContractExtinction().getRbContractExtinction().setSelected(false);
            return;
        }

        if(contractVariationContractVariations.getContractVariationContractExtinction().getRbContractExtinction().isSelected()) {
            contractVariationActionComponents.getOkButton().setDisable(false);
        }
    }

    private void onOkButton(MouseEvent evet){

        if(contractVariationContractVariations.getContractVariationContractExtinction().getRbContractExtinction().isSelected()){
            if(isCorrectContractExtinctionData()) {
                //persistContractExtinction();

                String extinctionContractCause = contractVariationContractVariations.getContractVariationContractExtinction()
                        .getExtinctionCauseSelector().getSelectionModel().getSelectedItem().getVariation_description();

                String holidaysText =  contractVariationContractVariations.getContractVariationContractExtinction()
                        .getRbHolidaysYes().isSelected() ? "disfrutadas." : "no disfrutadas.";

                StringBuilder sb = new StringBuilder();
                sb.append(extinctionContractCause);
                sb.append(". Vacaciones ");
                sb.append(holidaysText);

                ContractDataSubfolder contractDataSubfolder = createContractDataSubfolder(sb.toString());

                printContractDataSubfolder(contractDataSubfolder);
            }
        }
    }

    private void onExitButton(MouseEvent event){

        Stage stage = (Stage) contractVariationParts.getScene().getWindow();
        stage.close();
    }

    private void refreshContractSelectorData(ClientDTO client, LocalDate selectedDate){

        if(client == null){
            contractVariationContractVariations.setDisable(true);

            return;
        }

        contractVariationParts.getContractSelector().getItems().clear();
        contractVariationContractData.clearAllContractData();

        List<ContractFullDataDTO> contractFullDataDTOList = applicationMainController.findAllDataForContractInForceAtDateByClientId(client.getClientId(), selectedDate);

        ObservableList<ContractFullDataDTO> contractFullDataDTOS = FXCollections.observableArrayList(contractFullDataDTOList);
        contractVariationParts.getContractSelector().setItems(contractFullDataDTOS);

        if(contractFullDataDTOS.size() == 1){
            contractVariationParts.getContractSelector().getSelectionModel().select(0);
        }
    }

    private void persistContractExtinction(){

        contractVariationActionComponents.getOkButton().setDisable(true);

        Boolean errorWhenPersistingData = false;

        ContractNewVersionDTO contractNewVersionExtinctedDTO = contractVariationParts
                .getContractSelector().getSelectionModel().getSelectedItem().getContractNewVersion();

        if(updateLastContractVariation(contractNewVersionExtinctedDTO) == null) {
            System.out.println("Error actualizando el ultimo registro de contractvariation.");
            errorWhenPersistingData = true;
            return;
        }

        if(persistNewContractVariation(contractNewVersionExtinctedDTO) == null) {
            System.out.println("Error creando nuevo registro de extincion en contractvariation.");
            errorWhenPersistingData = true;
            return;
        }

        if(updateInitialContractOfContractExtinction(contractNewVersionExtinctedDTO) == null) {
            System.out.println("Error actualizando la fecha de extincion en initialcontract.");
            errorWhenPersistingData = true;
            return;
        }

        Message.warningMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.CONTRACT_EXTINCTION_OK);

    }

    private Boolean isCorrectDateToContractVariation(){

        if(Period.between(contractVariationParts.getInForceDate().getValue(), LocalDate.now().minusDays(3L)).getDays() <= 0){
            return true;
        }

        Message.warningMessage(contractVariationParts.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.IS_NOT_VALID_DATE_FOR_CONTRACT_VARIATION);
        return false;
    }

    private Boolean isCorrectContractExtinctionData(){

        if(contractVariationContractVariations.getDateNotification().getDate() == null){
            System.out.println("Falta la fecha de notificación del cliente.");

            return false;
        }

        if(contractVariationContractVariations.getHourNotification().getText() == null){
            System.out.println("Falta la hora de notificación del cliente.");

            return false;

        }

        if(contractVariationContractVariations.getContractVariationContractExtinction().getExtinctionCauseSelector().getSelectionModel().getSelectedItem() == null){
            System.out.println("Falta causa de la extincion.");
            return false;
        }

        if( contractVariationContractVariations.getContractVariationContractExtinction().getDateFrom().getValue() == null
                || Period.between(contractVariationContractVariations.getContractVariationContractExtinction().getDateFrom().getValue(),
                LocalDate.now()).getDays() > 3){
            System.out.println("Fecha de la extincion es erronea.");
            return false;
        }

        if(!contractVariationContractVariations.getContractVariationContractExtinction().getRbHolidaysYes().isSelected() &&
                !contractVariationContractVariations.getContractVariationContractExtinction().getRbHolidaysNo().isSelected()){
            System.out.println("No se ha seleccionado la situacion de las vacaciones.");
            return false;
        }

        System.out.println("Aparentemente esta todo correcto.\n");
        return true;
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
        if(contractVariationContractVariations.getContractVariationContractExtinction().getRbContractExtinction().isSelected()){
            notificationType = Parameters.CONTRACT_EXTINCTION_TEXT;
        }
        if(contractVariationContractVariations.getContractVariationContractConversion().getRbContractConversion().isSelected()){
            notificationType = Parameters.CONTRACT_CONVERSION_TEXT;
        }
        if(contractVariationContractVariations.getContractVariationContractExtension().getRbContractExtension().isSelected()){
            notificationType = Parameters.CONTRACT_EXTENSION_TEXT;
        }

        LocalDate clientNotificationDate = contractVariationContractVariations.getDateNotification().getDate();
        LocalTime clientNotificationHour = LocalTime.parse(contractVariationContractVariations.getHourNotification().getText());

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
            Message.warningMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.CONTRACT_DATA_SUBFOLFER_TO_PRINTER_OK);
            if(!printOk.equals("ok")){
                Message.warningMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, Parameters.NO_PRINTER_FOR_THESE_ATTRIBUTES);
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
