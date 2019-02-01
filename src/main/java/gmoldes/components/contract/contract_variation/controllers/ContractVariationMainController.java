package gmoldes.components.contract.contract_variation.controllers;

import gmoldes.App;
import gmoldes.ApplicationConstants;
import gmoldes.ApplicationMainController;
import gmoldes.components.ViewLoader;
import gmoldes.components.contract.contract_variation.components.*;
import gmoldes.components.contract.contract_variation.events.*;
import gmoldes.components.contract.contract_variation.forms.ContractVariationDataSubfolder;
import gmoldes.components.contract.controllers.TypesContractVariationsController;
import gmoldes.components.contract.ContractConstants;
import gmoldes.components.contract.new_contract.components.ContractParameters;
import gmoldes.components.contract.new_contract.components.WorkDaySchedule;
import gmoldes.components.contract.new_contract.controllers.ContractMainControllerConstants;
import gmoldes.components.contract.new_contract.forms.ContractDataToContractsAgent;
import gmoldes.domain.client.ClientService;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.ContractService;
import gmoldes.domain.contract.dto.ContractFullDataDTO;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;
import gmoldes.domain.document_for_print.ContractExtensionDataDocumentCreator;
import gmoldes.domain.document_for_print.ContractExtinctionDataDocumentCreator;
import gmoldes.services.email.EmailConstants;
import gmoldes.utilities.Message;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.Utilities;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.text.Collator;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

public class ContractVariationMainController extends VBox {

    private static final Logger logger = Logger.getLogger(ContractVariationMainController.class.getSimpleName());
    private static final String CONTRACT_VARIATION_MAIN_FXML = "/fxml/contract_variations/contractvariation_main.fxml";

    private static final String myColorRED = "-fx-text-fill: #971E11";
    private static final String myColorBLACK = "-fx-text-fill: #000000";

    private Process pdfViewerProcess = null;

    private Set<WorkDaySchedule> weeklyWorkScheduleVariation;


    private Parent parent;

    private ApplicationMainController applicationMainController = new ApplicationMainController();
    private ContractExtinctionController contractExtinctionController = new ContractExtinctionController(this);
    private ContractExtensionController contractExtensionController = new ContractExtensionController(this);
    private WeeklyWorkScheduleVariationControllerOfContract weeklyWorkScheduleVariationControllerOfContract = new WeeklyWorkScheduleVariationControllerOfContract(this);
    private ContractVariationDataSubfolder contractVariationDataSubfolder;


    private Boolean contractHasBeenSavedInDatabase = false;
    private Boolean contractHasBeenSentToContractAgent = false;

    @FXML
    private StackPane contractVariationStackPane;
    @FXML
    private Group contractVariationsGroup;
    @FXML
    private ToggleGroup contractVariationToggleGroup;
    @FXML
    private ToggleGroup holidaysToggleGroup;
    @FXML
    private ContractVariationHeader contractVariationHeader;
    @FXML
    private ContractVariationParts contractVariationParts;
    @FXML
    private ContractVariationContractData contractVariationContractData;
    @FXML
    private ContractVariationTypes contractVariationTypes;
    @FXML
    private ContractVariationContractVariations contractVariationContractVariations;
    @FXML
    private ContractVariationWeeklyWorkScheduleDuration contractVariationWeeklyWorkScheduleDuration;
    @FXML
    private ContractVariationActionComponents contractVariationActionComponents;


    @FXML
    private void initialize() {
        
        contractVariationParts.setOnClientSelectorAction(this::onChangeEmployer);
        contractVariationParts.setOnContractSelectorAction(this::onContractSelectorAction);

        contractVariationTypes.setOnDateNotification(this::onDateNotification);
        contractVariationTypes.setOnContractExtinction(this::onContractExtinctionSelected);
        contractVariationTypes.setOnContractExtension(this::onContractExtensionSelected);
        contractVariationTypes.setOnContractVariationWorkingDay(this::onVariationWorkingDaySelected);
        contractVariationTypes.setOnContractConversion(this::onContractConversionSelected);

        // Contract variations types toggleGroup
        contractVariationToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
            }
        });

        contractVariationTypes.getRbContractExtinction().setToggleGroup(contractVariationToggleGroup);
        contractVariationTypes.getRbContractExtension().setToggleGroup(contractVariationToggleGroup);
        contractVariationTypes.getRbWeeklyWorkHoursVariation().setToggleGroup(contractVariationToggleGroup);
        contractVariationTypes.getRbContractConversion().setToggleGroup(contractVariationToggleGroup);
        contractVariationTypes.getRbOtherVariations().setToggleGroup(contractVariationToggleGroup);

        // Contract extinction holidays toggleGroup
        holidaysToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
            }
        });

        contractVariationContractVariations.getContractVariationContractExtinction().getRbHolidaysYes().setToggleGroup(holidaysToggleGroup);
        contractVariationContractVariations.getContractVariationContractExtinction().getRbHolidaysNo().setToggleGroup(holidaysToggleGroup);
        contractVariationContractVariations.getContractVariationContractExtinction().getRbHolidaysCalculate().setToggleGroup(holidaysToggleGroup);

        //Binding
        contractVariationTypes.disableProperty().bind(this.contractVariationParts.getContractSelector().valueProperty().isNull());

        contractVariationContractVariations.getContractVariationContractExtinction().getContractExtinctionGroup().visibleProperty().bind(this.contractVariationTypes.getRbContractExtinction().selectedProperty());
        contractVariationContractVariations.getContractVariationContractExtension().getContractExtensionGroup().visibleProperty().bind(this.contractVariationTypes.getRbContractExtension().selectedProperty());
        contractVariationContractVariations.getContractVariationWeeklyWorkScheduleDuration().getWeeklyWorkScheduleGroup().visibleProperty().bind(this.contractVariationTypes.getRbWeeklyWorkHoursVariation().selectedProperty());
        contractVariationContractVariations.getContractVariationContractConversion().getContractConversionGroup().visibleProperty().bind(this.contractVariationTypes.getRbContractConversion().selectedProperty());

        contractVariationContractVariations.getContractVariationContractExtension().getDateTo().valueProperty().addListener((ov, oldValue, newValue) -> setContractExtensionDuration(newValue));
        contractVariationContractVariations.getContractVariationWeeklyWorkScheduleDuration().getDateTo().valueProperty().addListener((ov, oldValue, newValue) -> setContractWeeklyWorkScheduleDuration(newValue));


        // Button actions
        contractVariationActionComponents.setOnOkButton(this::onOkButton);
        contractVariationActionComponents.setOnExitButton(this::onExitButton);
        contractVariationActionComponents.setOnViewPDFButton(this::onViewPDFButton);
        contractVariationActionComponents.setOnSendMailButton(this::onSendMailButton);

        clientSelectorLoadData();
    }

    public ContractVariationParts getContractVariationParts() {
        return contractVariationParts;
    }

    public ContractVariationContractData getContractVariationContractData() {
        return contractVariationContractData;
    }

    public ContractVariationTypes getContractVariationTypes() {
        return contractVariationTypes;
    }

    public ContractVariationContractVariations getContractVariationContractVariations() {
        return contractVariationContractVariations;
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
        List<ClientDTO> clientDTOList = ClientService.findAllClientWithContractInForceAtDate(workDate);

        Collator primaryCollator = Collator.getInstance(new Locale("es","ES"));
        primaryCollator.setStrength(Collator.PRIMARY);

        List<ClientDTO> clientDTOListSorted = clientDTOList
                .stream()
                .sorted(Comparator.comparing(ClientDTO::toString, primaryCollator)).collect(Collectors.toList());

        ObservableList<ClientDTO> clientDTOS = FXCollections.observableArrayList(clientDTOListSorted);
        contractVariationParts.loadDataInClientSelector(clientDTOS);
        contractVariationParts.getClientSelector().setItems(clientDTOS);
    }

    private void onDateNotification(DateChangeEvent event){
        LocalDate dateNotification = event.getDate();
        Long difference = ChronoUnit.DAYS.between(contractVariationParts.getInForceDate().getValue(), dateNotification);
        System.out.println("ContractVariations: Ha cambiado la fecha de notificación. La diferencia con hoy es de " + difference + " días.");
    }

    private void onChangeEmployer(ClientChangeEvent event){

        LocalDate selectedDate = event.getDate();
        ClientDTO selectedClient = event.getClient();
        refreshContractSelectorData(selectedClient, selectedDate);

        cleanDataForAllSelectableComponents();

    }

    private void onContractSelectorAction(ActionEvent event){

        if(contractVariationParts.getContractSelector().getItems().isEmpty()){
            return;
        }

        cleanDataForAllSelectableComponents();

        ContractFullDataDTO selectedContract = contractVariationParts.getContractSelector().getSelectionModel().getSelectedItem();
        contractVariationContractData.setAllContractData(selectedContract);
    }

    private void onContractExtinctionSelected(MouseEvent event){

        contractVariationContractVariations.getContractVariationContractExtinction().cleanComponents();
        loadContractExtinctionCauseSelector();
        contractVariationContractVariations.getContractVariationContractExtinction().toFront();
        contractVariationTypes.getHourNotification().requestFocus();

        contractVariationActionComponents.getOkButton().setDisable(false);
    }

    private void onContractExtensionSelected(MouseEvent event){

        if(contractVariationParts.getContractSelector().getSelectionModel().getSelectedItem().getContractNewVersion().getExpectedEndDate() == null){
            Message.warningMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.UNDEFINED_DURATION_CONTRACTS_ARE_NOT_EXTENDABLE);
            cleanDataForAllSelectableComponents();
            contractVariationActionComponents.getOkButton().setDisable(true);

            return ;
        }

        if(contractVariationParts.getContractSelector().getSelectionModel().getSelectedItem().getContractType().getIsDeterminedDuration()){
            Message.warningMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.THIS_TYPE_OF_DETERMINED_DURATION_CONTRACT_IS_NOT_EXTENDABLE);
            cleanDataForAllSelectableComponents();
            contractVariationActionComponents.getOkButton().setDisable(true);

            return ;
        }

        contractVariationContractVariations.getContractVariationContractExtension().cleanComponents();
        contractVariationContractVariations.getContractVariationContractExtension().toFront();
        LocalDate expectedEndDate = contractVariationParts.getContractSelector().getValue().getContractNewVersion().getExpectedEndDate();
        contractVariationContractVariations.getContractVariationContractExtension().getDateFrom().setValue(expectedEndDate.plusDays(1L));
        contractVariationContractVariations.getContractVariationContractExtension().getPublicNotes().setText(ContractConstants.NOT_VARIATION_OTHER_CONDITIONS_OF_CONTRACT);

        contractVariationActionComponents.getOkButton().setDisable(false);
    }

    private void onVariationWorkingDaySelected(MouseEvent event){
        ContractVariationWeeklyWorkSchedule contractVariationWeeklyWorkSchedule = new ContractVariationWeeklyWorkSchedule();
        contractVariationWeeklyWorkSchedule.setOnOkButton(this::onChangeWeeklyWorkDuration);
        Scene scene = new Scene(contractVariationWeeklyWorkSchedule);
        scene.getStylesheets().add(App.class.getResource("/css_stylesheet/application.css").toExternalForm());
        Stage contractVariationStage = new Stage();
        contractVariationStage.setResizable(false);
        contractVariationStage.setTitle("Variación de la jornada de trabajo");
        contractVariationStage.setScene(scene);
        contractVariationStage.initOwner(this.getScene().getWindow());
        contractVariationStage.initModality(Modality.APPLICATION_MODAL);
        contractVariationStage.show();
    }

    private void onContractConversionSelected(MouseEvent event){

        contractVariationContractVariations.getContractVariationContractConversion().cleanComponents();
        contractVariationContractVariations.getContractVariationContractConversion().toFront();
        contractVariationTypes.getHourNotification().requestFocus();

        contractVariationActionComponents.getOkButton().setDisable(false);
    }

    private void onOkButton(MouseEvent evet){

        // Contract extinction
        RadioButton rbContractExtinction = contractVariationTypes.getRbContractExtinction();
        if(rbContractExtinction.isSelected()) {
            ContractExtinctionController contractExtinctionController = new ContractExtinctionController(this);

            MessageContractVariationEvent messageContractVariationEvent = contractExtinctionController.executeContractExtinctionOperations();
            if (!messageContractVariationEvent.getMessageText().equals(ContractConstants.CONTRACT_EXTINCTION_PERSISTENCE_OK) &&
                    !messageContractVariationEvent.getMessageText().isEmpty()) {
                Message.warningMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, messageContractVariationEvent.getMessageText());

                return;
            }

            this.contractVariationDataSubfolder = messageContractVariationEvent.getContractVariationDataSubfolder();
            contractVariationActionComponents.enablePDFButton(true);
            setDataEditionBlockedAndEnableSendMail();

            return;
        }

        // Contract extension
        RadioButton rbContractExtension = contractVariationTypes.getRbContractExtension();
        if(rbContractExtension.isSelected()) {
            ContractExtensionController contractExtensionController = new ContractExtensionController(this);

            MessageContractVariationEvent messageContractVariationEvent = contractExtensionController.executeContractExtensionOperations();
            if (!messageContractVariationEvent.getMessageText().equals(ContractConstants.CONTRACT_EXTENSION_PERSISTENCE_OK) &&
                    !messageContractVariationEvent.getMessageText().isEmpty()) {
                Message.warningMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, messageContractVariationEvent.getMessageText());

                return;
            }

            contractVariationActionComponents.enablePDFButton(true);
            setDataEditionBlockedAndEnableSendMail();

            return;

        }

        // Change of weekly work duration
        RadioButton rbWeeklyWorkHoursVariation = contractVariationTypes.getRbWeeklyWorkHoursVariation();
        if(rbWeeklyWorkHoursVariation.isSelected()){
            WeeklyWorkScheduleVariationControllerOfContract weeklyWorkScheduleVariationControllerOfContract = new WeeklyWorkScheduleVariationControllerOfContract(this);
            MessageContractVariationEvent messageContractVariationEvent = weeklyWorkScheduleVariationControllerOfContract.executeWeeklyWorkDurationVariationOperations(weeklyWorkScheduleVariation);
            if (!messageContractVariationEvent.getMessageText().equals(ContractConstants.WEEKLY_WORK_DURATION_VARIATION_PERSISTENCE_OK) &&
                    !messageContractVariationEvent.getMessageText().isEmpty()) {
                Message.warningMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, messageContractVariationEvent.getMessageText());

                return;
            }



            System.out.println("Variación de jornada de trabajo.");
        }



//        // Contract conversion
//        RadioButton rbContractConversion = contractVariationTypes.getRbContractConversion();
//        if(rbContractConversion.isSelected()) {
//            Boolean contractConversionIsCorrect = contractVariationContractExtensionSelected();
//            if(contractConversionIsCorrect) {
//                VariationTypeEvent event = persistenceOfContractVariation(new CompatibleVariationEvent(false, false,true, ""));
//                if(event != null){
//                    isContractVariationExtinction = event.getContractVariationExtinction();
//                    isContractVariationExtension = event.getContractVariationExtension();
//                    isContractVariationConversion = event.getContractVariationConversion();
//                }
//            }
//
//            return;
//        }
    }

    private void onExitButton(MouseEvent event){

        if(!contractVariationActionComponents.getSendMailButton().isDisable()){

            if(!Message.confirmationMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.CONTRACT_EXTINCTION_SAVED_BUT_NOT_SENDED_TO_CONTRACT_AGENT)){
                return;
            }
        }

        Stage stage = (Stage) contractVariationParts.getScene().getWindow();
        stage.close();
    }

    private void onViewPDFButton(MouseEvent event){

        Path pathOut;

        // Contract extinction
        RadioButton rbContractExtinction = contractVariationTypes.getRbContractExtinction();
        if(rbContractExtinction.isSelected()) {

            String publicNotes = retrievePublicNotes();
            ContractExtinctionDataDocumentCreator contractDocumentCreator = new ContractExtinctionDataDocumentCreator(this);
            ContractDataToContractsAgent contractExtinctionDataToContractAgent = contractDocumentCreator.createContractExtinctionDataDocumentForContractsAgent(publicNotes);

            pathOut = contractDocumentCreator.retrievePathToContractDataToContractAgentPDF(contractExtinctionDataToContractAgent);

            if (ApplicationConstants.OPERATING_SYSTEM.toLowerCase().contains(ApplicationConstants.OS_LINUX)) {
                try {
                    String[] command = {"sh", "-c", "xdg-open " + pathOut};
                    pdfViewerProcess = Runtime.getRuntime().exec(command);
                } catch (IOException e) {
                    System.out.println("No se ha podido abrir el documento \"" + contractExtinctionDataToContractAgent.toFileName().concat(ApplicationConstants.PDF_EXTENSION) + "\"");
                    e.printStackTrace();
                }
            } else if (ApplicationConstants.OPERATING_SYSTEM.toLowerCase().contains(ApplicationConstants.OS_WINDOWS)) {
                String[] command = {"cmd", "/c", "start", "\"visor\"", "\"" + pathOut + "\""};
                try {
                    pdfViewerProcess = Runtime.getRuntime().exec(command);
                } catch (IOException e) {
                    System.out.println("No se ha podido abrir el documento \"" + contractExtinctionDataToContractAgent.toFileName().concat(ApplicationConstants.PDF_EXTENSION) + "\"");
                    e.printStackTrace();
                }
            }

            return;
        }

        // Contract extension
        RadioButton rbContractExtension = contractVariationTypes.getRbContractExtension();
        if(rbContractExtension.isSelected()) {

            String publicNotes = retrievePublicNotes();
            ContractExtensionDataDocumentCreator contractDocumentCreator = new ContractExtensionDataDocumentCreator(this);
            ContractDataToContractsAgent contractExtensionDataToContractAgent = contractDocumentCreator.createContractExtensionDataDocumentForContractsAgent(publicNotes);

            pathOut = contractDocumentCreator.retrievePathToContractDataToContractAgentPDF(contractExtensionDataToContractAgent);

            if (ApplicationConstants.OPERATING_SYSTEM.toLowerCase().contains(ApplicationConstants.OS_LINUX)) {
                try {
                    String[] command = {"sh", "-c", "xdg-open " + pathOut};
                    pdfViewerProcess = Runtime.getRuntime().exec(command);
                } catch (IOException e) {
                    System.out.println("No se ha podido abrir el documento \"" + contractExtensionDataToContractAgent.toFileName().concat(ApplicationConstants.PDF_EXTENSION) + "\"");
                    e.printStackTrace();
                }
            } else if (ApplicationConstants.OPERATING_SYSTEM.toLowerCase().contains(ApplicationConstants.OS_WINDOWS)) {
                String[] command = {"cmd", "/c", "start", "\"visor\"", "\"" + pathOut + "\""};
                try {
                    pdfViewerProcess = Runtime.getRuntime().exec(command);
                } catch (IOException e) {
                    System.out.println("No se ha podido abrir el documento \"" + contractExtensionDataToContractAgent.toFileName().concat(ApplicationConstants.PDF_EXTENSION) + "\"");
                    e.printStackTrace();
                }
            }

            return;
        }

        // Contract conversion
        RadioButton rbContractConversion = contractVariationTypes.getRbContractConversion();
//        if(rbContractConversion.isSelected()) {
//
//            String publicNotes = retrievePublicNotes();
//            ContractConversionExtensionDataDocumentCreator contractDocumentCreator = new ContractExtensionDataDocumentCreator(this);
//            ContractDataToContractsAgent contractExtensionDataToContractAgent = contractDocumentCreator.createContractExtensionDataDocumentForContractsAgent(publicNotes);
//
//            pathOut = contractDocumentCreator.retrievePathToContractDataToContractAgentPDF(contractExtensionDataToContractAgent);
//
//            if (ApplicationConstants.OPERATING_SYSTEM.toLowerCase().contains(ApplicationConstants.OS_LINUX)) {
//                try {
//                    String[] command = {"sh", "-c", "xdg-open " + pathOut};
//                    pdfViewerProcess = Runtime.getRuntime().exec(command);
//                } catch (IOException e) {
//                    System.out.println("No se ha podido abrir el documento \"" + contractExtensionDataToContractAgent.toFileName().concat(ApplicationConstants.PDF_EXTENSION) + "\"");
//                    e.printStackTrace();
//                }
//            } else if (ApplicationConstants.OPERATING_SYSTEM.toLowerCase().contains(ApplicationConstants.OS_WINDOWS)) {
//                String[] command = {"cmd", "/c", "start", "\"visor\"", "\"" + pathOut + "\""};
//                try {
//                    pdfViewerProcess = Runtime.getRuntime().exec(command);
//                } catch (IOException e) {
//                    System.out.println("No se ha podido abrir el documento \"" + contractExtensionDataToContractAgent.toFileName().concat(ApplicationConstants.PDF_EXTENSION) + "\"");
//                    e.printStackTrace();
//                }
//            }
//
//            return;
//        }


    }

    private void onSendMailButton(MouseEvent event){
        Boolean isSendOk = false;

        if (Message.confirmationMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractMainControllerConstants.QUESTION_SEND_MAIL_TO_CONTRACT_AGENT)) {

            // Contract extinction
            RadioButton rbContractExtinction = contractVariationTypes.getRbContractExtinction();
            if(rbContractExtinction.isSelected()) {
                isSendOk = contractExtinctionController.sendsMailContractVariationExtinction(this.contractVariationDataSubfolder);
            }


            // Contract extension
            RadioButton rbContractExtension = contractVariationTypes.getRbContractExtension();
            if(rbContractExtension.isSelected()) {
                isSendOk = contractExtensionController.sendsMailContractVariationExtension();
            }

            if(isSendOk){

                Message.warningMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, EmailConstants.MAIL_SEND_OK);
                contractHasBeenSentToContractAgent = true;
                this.contractVariationActionComponents.enableSendMailButton(false);

            }else{
                Message.warningMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, EmailConstants.MAIL_NOT_SEND_OK);
            }
        }
    }

    private void onChangeWeeklyWorkDuration(WorkScheduleEvent workScheduleEvent){

        if(workScheduleEvent.getSchedule() == null){
            this.contractVariationTypes.getRbWeeklyWorkHoursVariation().setSelected(false);
            contractVariationActionComponents.getOkButton().setDisable(true);

            return;
        }

        Set<WorkDaySchedule> schedule = workScheduleEvent.getSchedule();
        weeklyWorkScheduleVariation = schedule;

        ContractFullDataDTO contractFullDataDTO = contractVariationParts.getContractSelector().getValue();
        String previousWeeklyWorkHours = contractFullDataDTO.getContractNewVersion().getContractJsonData().getWeeklyWorkHours();
        Duration previousWeeklyWorkDuration = Utilities.converterTimeStringToDuration(previousWeeklyWorkHours);

        Duration subsequentWeeklyWorkDuration = Duration.ZERO;
        for (WorkDaySchedule workDaySchedule : schedule) {
            if(workDaySchedule.getDurationHours() != null) {
                subsequentWeeklyWorkDuration = subsequentWeeklyWorkDuration.plus(workDaySchedule.getDurationHours());
            }
        }

        String weeklyWorkHoursVariationText;
        if(subsequentWeeklyWorkDuration.compareTo(previousWeeklyWorkDuration) > 0 ){
            weeklyWorkHoursVariationText = "Ampliación";
        }else{
            weeklyWorkHoursVariationText = "Reducción";
        }

        String publicNotes = weeklyWorkHoursVariationText + " de jornada de trabajo semanal a " + Utilities.converterDurationToTimeString(subsequentWeeklyWorkDuration) + " horas de trabajo por semana [desde " +
                Utilities.converterDurationToTimeString(previousWeeklyWorkDuration) + " horas de trabajo por semana]." ;

        contractVariationContractVariations.getContractVariationWeeklyWorkScheduleDuration().getPublicNotes().setText(publicNotes);

        contractVariationActionComponents.getOkButton().setDisable(false);
    }

    private void loadContractExtinctionCauseSelector(){
        TypesContractVariationsController typesContractVariationsController = new TypesContractVariationsController();
        List<TypesContractVariationsDTO> typesContractVariationsDTOList = typesContractVariationsController.findAllTypesContractVariations();

        List<TypesContractVariationsDTO> typesContractVariationsExtinctionDTOList = new ArrayList<>();
        for(TypesContractVariationsDTO typesContractVariationsDTO : typesContractVariationsDTOList){
            if(typesContractVariationsDTO.getExtinction()){
                typesContractVariationsExtinctionDTOList.add(typesContractVariationsDTO);
            }
        }

        ObservableList<TypesContractVariationsDTO> typesContractVariationsDTOS = FXCollections.observableArrayList(typesContractVariationsExtinctionDTOList);
        contractVariationContractVariations.getContractVariationContractExtinction().getExtinctionCauseSelector().setItems(typesContractVariationsDTOS);
    }

    private void setContractExtensionDuration(LocalDate newValueDateTo){

        if(contractVariationContractVariations.getContractVariationContractExtension().getDateFrom().getValue() != null &&
                newValueDateTo != null) {

            Long contractExtensionDuration = DAYS.between(contractVariationContractVariations.getContractVariationContractExtension().getDateFrom().getValue(), newValueDateTo) + 1L;

            contractVariationContractVariations.getContractVariationContractExtension().getContractExtensionDuration().setText(contractExtensionDuration.toString());
            if(contractExtensionDuration <= 0){
                contractVariationContractVariations.getContractVariationContractExtension().getContractExtensionDuration().setStyle(myColorRED);
            }else{
                contractVariationContractVariations.getContractVariationContractExtension().getContractExtensionDuration().setStyle(myColorBLACK);
            }
        }
    }

    private void setContractWeeklyWorkScheduleDuration(LocalDate newValueDateTo){
        if(contractVariationContractVariations.getContractVariationWeeklyWorkScheduleDuration().getDateFrom().getValue() != null &&
                newValueDateTo != null) {

            Long contractWeeklyWorkScheduleDuration = DAYS.between(contractVariationContractVariations.getContractVariationWeeklyWorkScheduleDuration().getDateFrom().getValue(), newValueDateTo) + 1L;

            contractVariationContractVariations.getContractVariationWeeklyWorkScheduleDuration().getWeeklyWorkSchduleDuration().setText(contractWeeklyWorkScheduleDuration.toString());
            if(contractWeeklyWorkScheduleDuration <= 0){
                contractVariationContractVariations.getContractVariationWeeklyWorkScheduleDuration().getWeeklyWorkSchduleDuration().setStyle(myColorRED);
            }else{
                contractVariationContractVariations.getContractVariationWeeklyWorkScheduleDuration().getWeeklyWorkSchduleDuration().setStyle(myColorBLACK);
            }
        }
    }

    private void refreshContractSelectorData(ClientDTO client, LocalDate selectedDate){

        if(client == null){
            return;
        }

        if(contractVariationParts.getContractSelector().getItems().size() > 0) {
            contractVariationParts.getContractSelector().getItems().clear();
        }
        contractVariationContractData.clearAllContractData();

        List<ContractFullDataDTO> contractFullDataDTOList = ContractService.findAllDataForContractInForceAtDateByClientId(client.getClientId(), selectedDate);

        ObservableList<ContractFullDataDTO> contractFullDataDTOS = FXCollections.observableArrayList(contractFullDataDTOList);
        contractVariationParts.getContractSelector().setItems(contractFullDataDTOS);

        if(contractFullDataDTOS.size() == 1){
            contractVariationParts.getContractSelector().getSelectionModel().select(0);
        }
    }

    private CompatibleVariationEvent dateToNotifyContractVariationToAdministrationIsCorrect(LocalDate date){

        LocalDate limitDatePreviousOfNotifyToAdministration = contractVariationParts.getInForceDate().getValue()
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

    private void cleanDataForAllSelectableComponents(){

        contractVariationTypes.getDateNotification().setDate(LocalDate.now());
        contractVariationTypes.getHourNotification().setText(null);

        contractVariationTypes.getRbContractExtinction().setSelected(false);
        contractVariationContractVariations.getContractVariationContractExtinction().getExtinctionCauseSelector().getSelectionModel().select(null);
        contractVariationContractVariations.getContractVariationContractExtinction().getDateFrom().setValue(null);
        contractVariationContractVariations.getContractVariationContractExtinction().getRbHolidaysYes().setSelected(false);
        contractVariationContractVariations.getContractVariationContractExtinction().getRbHolidaysNo().setSelected(false);

        contractVariationTypes.getRbContractExtension().setSelected(false);
        contractVariationContractVariations.getContractVariationContractExtension().getDateFrom().setValue(null);
        contractVariationContractVariations.getContractVariationContractExtension().getDateTo().setValue(null);

        contractVariationTypes.getRbContractConversion().setSelected(false);
        contractVariationContractVariations.getContractVariationContractConversion().getContractConversionSelector().getSelectionModel().select(null);
        contractVariationContractVariations.getContractVariationContractConversion().getDateFrom().setValue(null);
        contractVariationContractVariations.getContractVariationContractConversion().getDateTo().setValue(null);
    }

//    private EmailDataCreationDTO retrieveDataForEmailCreation(Path path, String attachedFileName, String variationTypeText){
//
//        ClientDTO employerDTO = this.contractVariationParts.getClientSelector().getValue();
//        PersonDTO employeeDTO = this.contractVariationParts.getContractSelector().getValue().getEmployee();
//        return EmailDataCreationDTO.create()
//                .withPath(path)
//                .withFileName(attachedFileName)
//                .withEmployer(employerDTO)
//                .withEmployee(employeeDTO)
//                .withVariationTypeText(variationTypeText)
//                .build();
//    }

    private void setDataEditionBlockedAndEnableSendMail() {

        contractVariationParts.setMouseTransparent(true);
        contractVariationTypes.setMouseTransparent(true);
        contractVariationContractVariations.setMouseTransparent(true);
        contractVariationActionComponents.getOkButton().setDisable(true);
        contractVariationActionComponents.getSendMailButton().setDisable(false);
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

    private String retrievePublicNotes(){

        // Contract extinction
        RadioButton rbContractExtinction = contractVariationTypes.getRbContractExtinction();
        if(rbContractExtinction.isSelected()) {
            String extinctionContractCause = this.getContractVariationContractVariations().getContractVariationContractExtinction()
                    .getExtinctionCauseSelector().getSelectionModel().getSelectedItem().getVariation_description();

            String holidaysUsedText;
            if (this.getContractVariationContractVariations().getContractVariationContractExtinction()
                    .getRbHolidaysYes().isSelected()) {
                holidaysUsedText = "disfrutadas.";
            } else if (this.getContractVariationContractVariations().getContractVariationContractExtinction()
                    .getRbHolidaysNo().isSelected()) {
                holidaysUsedText = "no disfrutadas.";
            } else {
                holidaysUsedText = "a calcular.";
            }

            StringBuilder sb = new StringBuilder();
            sb.append(extinctionContractCause);
            sb.append(". Vacaciones ");
            sb.append(holidaysUsedText);
            sb.append("\n");
            sb.append(this.getContractVariationContractVariations().getContractVariationContractExtinction().getPublicNotes().getText());

            return sb.toString();
        }

        // Contract extension
        RadioButton rbContractExtension = contractVariationTypes.getRbContractExtension();
        if(rbContractExtension.isSelected()) {

            return this.getContractVariationContractVariations().getContractVariationContractExtension().getPublicNotes().getText();
        }

        // Contract conversion
        RadioButton rbContractConversion = contractVariationTypes.getRbContractConversion();
        if(rbContractConversion.isSelected()) {

            return this.getContractVariationContractVariations().getContractVariationContractConversion().getPublicNotes().getText();
        }

        return null;
    }
}
