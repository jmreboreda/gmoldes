package gmoldes.components.contract.contract_variation.controllers;

import gmoldes.ApplicationMainController;
import gmoldes.components.ViewLoader;
import gmoldes.components.contract.contract_variation.components.*;
import gmoldes.components.contract.contract_variation.events.ClientChangeEvent;
import gmoldes.components.contract.contract_variation.events.CompatibleVariationEvent;
import gmoldes.components.contract.contract_variation.events.MessageEvent;
import gmoldes.components.contract.contract_variation.events.VariationTypeEvent;
import gmoldes.components.contract.controllers.TypesContractVariationsController;
import gmoldes.components.contract.manager.ContractManager;
import gmoldes.components.contract.new_contract.components.ContractConstants;
import gmoldes.components.contract.new_contract.components.ContractParameters;
import gmoldes.components.contract.new_contract.controllers.ContractMainControllerConstants;
import gmoldes.components.contract.new_contract.forms.ContractDataToContractsAgent;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.dto.ContractFullDataDTO;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;
import gmoldes.domain.document_for_print.ContractExtensionDataDocumentCreator;
import gmoldes.domain.document_for_print.ContractExtinctionDataDocumentCreator;
import gmoldes.domain.email.EmailDataCreationDTO;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.services.AgentNotificator;
import gmoldes.services.email.EmailParameters;
import gmoldes.utilities.Message;
import gmoldes.utilities.Parameters;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.mail.internet.AddressException;
import java.nio.file.Path;
import java.text.Collator;
import java.time.DayOfWeek;
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


    private Parent parent;

    private ApplicationMainController applicationMainController = new ApplicationMainController();

    private Boolean isContractVariationExtinction = false;
    private Boolean isContractVariationExtension = false;
    private Boolean isContractVariationConversion = false;


//    private ContractManager contractManager = new ContractManager();
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
    private ContractVariationActionComponents contractVariationActionComponents;


    @FXML
    private void initialize() {

        contractVariationParts.setOnClientSelectorAction(this::onChangeEmployer);
        contractVariationParts.setOnContractSelectorAction(this::onContractSelectorAction);

        contractVariationTypes.setOnContractExtinction(this::onContractExtinctionSelected);
        contractVariationTypes.setOnContractExtension(this::onContractExtensionSelected);
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
        contractVariationContractVariations.getContractVariationContractConversion().getContractConversionGroup().visibleProperty().bind(this.contractVariationTypes.getRbContractConversion().selectedProperty());

        contractVariationContractVariations.getContractVariationContractExtension().getDateTo().valueProperty().addListener((ov, oldValue, newValue) -> setContractExtensionDuration(newValue));

        // Button actions
        contractVariationActionComponents.setOnOkButton(this::onOkButton);
        contractVariationActionComponents.setOnExitButton(this::onExitButton);
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
        List<ClientDTO> clientDTOList = applicationMainController.findAllClientWithContractInForceAtDate(workDate);

        Collator primaryCollator = Collator.getInstance(new Locale("es","ES"));
        primaryCollator.setStrength(Collator.PRIMARY);

        List<ClientDTO> clientDTOListSorted = clientDTOList
                .stream()
                .sorted(Comparator.comparing(ClientDTO::toString, primaryCollator)).collect(Collectors.toList());

        ObservableList<ClientDTO> clientDTOS = FXCollections.observableArrayList(clientDTOListSorted);
        contractVariationParts.loadDataInClientSelector(clientDTOS);
        contractVariationParts.getClientSelector().setItems(clientDTOS);
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
        contractVariationTypes.getHourNotification().requestFocus();

        contractVariationActionComponents.getOkButton().setDisable(false);
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
            Boolean contractExtinctionIsCorrect = contractVariationContractExtinctionIsSelected();
            if(contractExtinctionIsCorrect) {
                VariationTypeEvent event = persistenceOfContractVariation(new CompatibleVariationEvent(true, false,false, ""));
                if(event != null){
                    isContractVariationExtinction = event.getContractVariationExtinction();
                    isContractVariationExtension = event.getContractVariationExtension();
                    isContractVariationConversion = event.getContractVariationConversion();
                }
            }

            return;
        }

        // Contract extension
        RadioButton rbContractExtension = contractVariationTypes.getRbContractExtension();
        if(rbContractExtension.isSelected()) {
            Boolean contractExtensionIsCorrect = contractVariationContractExtensionSelected();
            if(contractExtensionIsCorrect) {
                VariationTypeEvent event = persistenceOfContractVariation(new CompatibleVariationEvent(false, true,false, ""));
                if(event != null){
                    isContractVariationExtinction = event.getContractVariationExtinction();
                    isContractVariationExtension = event.getContractVariationExtension();
                    isContractVariationConversion = event.getContractVariationConversion();
                }
            }

            return;
        }

        // Contract conversion
        RadioButton rbContractConversion = contractVariationTypes.getRbContractConversion();
        if(rbContractConversion.isSelected()) {
            Boolean contractConversionIsCorrect = contractVariationContractExtensionSelected();
            if(contractConversionIsCorrect) {
                VariationTypeEvent event = persistenceOfContractVariation(new CompatibleVariationEvent(false, false,true, ""));
                if(event != null){
                    isContractVariationExtinction = event.getContractVariationExtinction();
                    isContractVariationExtension = event.getContractVariationExtension();
                    isContractVariationConversion = event.getContractVariationConversion();
                }
            }

            return;
        }
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

    private void onSendMailButton(MouseEvent event){
        Boolean isSendOk = false;

        if (Message.confirmationMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractMainControllerConstants.QUESTION_SEND_MAIL_TO_CONTRACT_AGENT)) {

            if(isContractVariationExtinction) {
                isSendOk = sendsMailContractVariationExtinction();
            }

            if(isContractVariationExtension) {
                isSendOk = sendsMailContractVariationExtension();
            }

            if(isSendOk){

                Message.warningMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, EmailParameters.MAIL_SEND_OK);
                contractHasBeenSentToContractAgent = true;
                this.contractVariationActionComponents.enableSendMailButton(false);

            }else{
                Message.warningMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, EmailParameters.MAIL_NOT_SEND_OK);
            }
        }
    }

    private Boolean contractVariationContractExtinctionIsSelected(){

        ContractExtinctionController contractExtinctionController = new ContractExtinctionController(this);

        MessageEvent messageEvent = contractExtinctionController.verifyIsCorrectContractExtinctionData();
        String messageText = messageEvent.getMessageText();
        if (!messageText.equals(ContractConstants.NECESSARY_DATA_FOR_VARIATION_CONTRACT_HAVE_BEEN_INTRODUCED)) {
            Message.warningMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, messageText);

            return false;
        }

        System.out.println(messageText);    // <- NECESSARY_DATA_FOR_VARIATION_CONTRACT_HAVE_BEEN_INTRODUCED

        LocalDate effectDateRequestedForContractVariation = contractVariationContractVariations.getContractVariationContractExtinction().getDateFrom().getValue();
        CompatibleVariationEvent isCorrectDataToNotifyAdministration = dateToNotifyContractVariationToAdministrationIsCorrect(effectDateRequestedForContractVariation);
        if(!isCorrectDataToNotifyAdministration.getErrorContractVariationMessage().isEmpty()){
            Boolean isCorrectDate = Message.confirmationMessage(contractVariationParts.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT,
                    ContractConstants.VERIFY_IS_VALID_DATE_TO_NOTIFY_CONTRACT_VARIATION_TO_ADMINISTRATION);
            if(!isCorrectDate){
                return false;
            }
        }

        CompatibleVariationEvent event = contractExtinctionController.checkExistenceIncompatibleVariationsForContractExtinction();
        if(!event.getErrorContractVariationMessage().isEmpty()){
            Message.warningMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, event.getErrorContractVariationMessage());

            return false;
        }

        return true;
    }

    private Boolean contractVariationContractExtensionSelected(){

        ContractExtensionController contractExtensionController = new ContractExtensionController(this);

        MessageEvent messageEvent = contractExtensionController.verifyIsCorrectContractExtensionData();
        String messageText = messageEvent.getMessageText();
        if (!messageText.equals(ContractConstants.NECESSARY_DATA_FOR_VARIATION_CONTRACT_HAVE_BEEN_INTRODUCED)) {
            Message.warningMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, messageText);

            return false;
        }

        System.out.println(messageText);    // <- NECESSARY_DATA_FOR_VARIATION_CONTRACT_HAVE_BEEN_INTRODUCED

        CompatibleVariationEvent event = contractExtensionController.checkExistenceIncompatibleVariationsForContractExtension();
        if(!event.getErrorContractVariationMessage().isEmpty()){
            Message.warningMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, event.getErrorContractVariationMessage());

            return false;
        }

        return true;
    }

    private VariationTypeEvent persistenceOfContractVariation(CompatibleVariationEvent compatibleVariationEvent) {

        if (!Message.confirmationMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.PERSIST_CONTRACT_VARIATION_QUESTION)) {
            return null;
        }

        Boolean isCorrectManagement = false;
        VariationTypeEvent variationTypeEvent = null;

        // Contract extinction persistence
        if (compatibleVariationEvent.getContractExtinctionEvent()) {

            ContractExtinctionController contractExtinctionController = new ContractExtinctionController(this);

            isCorrectManagement = contractExtinctionController.manageContractExtinction();
        }

        // Contract extension persistence
        if (compatibleVariationEvent.getContractExtensionEvent()) {

            ContractExtensionController contractExtensionController = new ContractExtensionController(this);

            isCorrectManagement = contractExtensionController.manageContractExtension();

        }

        // Contract conversion persistence(){




        //}

        if(isCorrectManagement){
            contractVariationParts.setMouseTransparent(true);
            contractVariationTypes.setMouseTransparent(true);
            contractVariationContractVariations.setMouseTransparent(true);
            contractVariationActionComponents.getOkButton().setDisable(true);
            contractVariationActionComponents.getSendMailButton().setDisable(false);
        }

        if(compatibleVariationEvent.getContractExtinctionEvent()){
            variationTypeEvent = new VariationTypeEvent(true, false, false);
        }

        if(compatibleVariationEvent.getContractExtensionEvent()){
            variationTypeEvent = new VariationTypeEvent(false, true, false);
        }

        if(compatibleVariationEvent.getContractConversionEvent()){
            variationTypeEvent = new VariationTypeEvent(false, false, true);
        }

        return variationTypeEvent;
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

    private Boolean sendsMailContractVariationExtinction(){

        Boolean isSendOk = false;
        Path pathOut;

        ContractExtinctionDataDocumentCreator contractExtinctionDocumentCreator = new ContractExtinctionDataDocumentCreator(this);

        ContractDataToContractsAgent contractExtinctionDataToContractAgent = contractExtinctionDocumentCreator.createContractExtinctionDataDocumentForContractsAgent();

        pathOut = contractExtinctionDocumentCreator.retrievePathToContractDataToContractAgentPDF(contractExtinctionDataToContractAgent);


        String attachedFileName = contractExtinctionDataToContractAgent.toFileName().concat(".pdf");

        AgentNotificator agentNotificator = new AgentNotificator();

        EmailDataCreationDTO emailDataCreationDTO = retrieveDateForEmailCreation(pathOut, attachedFileName);

        try {
            isSendOk = agentNotificator.sendEmailToContractAgent(emailDataCreationDTO);
        } catch (AddressException e) {
            e.printStackTrace();
        }

        return isSendOk;
    }

    private Boolean sendsMailContractVariationExtension(){

        Boolean isSendOk = false;
        Path pathOut;

        ContractExtensionDataDocumentCreator contractExtensionDocumentCreator = new ContractExtensionDataDocumentCreator(this);

        ContractDataToContractsAgent contractExtensionDataToContractAgent = contractExtensionDocumentCreator.createContractExtensionDataDocumentForContractsAgent();

        pathOut = contractExtensionDocumentCreator.retrievePathToContractDataToContractAgentPDF(contractExtensionDataToContractAgent);


        String attachedFileName = contractExtensionDataToContractAgent.toFileName().concat(".pdf");

        AgentNotificator agentNotificator = new AgentNotificator();

        EmailDataCreationDTO emailDataCreationDTO = retrieveDateForEmailCreation(pathOut, attachedFileName);

        try {
            isSendOk = agentNotificator.sendEmailToContractAgent(emailDataCreationDTO);
        } catch (AddressException e) {
            e.printStackTrace();
        }

        return isSendOk;
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

    private void refreshContractSelectorData(ClientDTO client, LocalDate selectedDate){

        if(client == null){
            return;
        }

        if(contractVariationParts.getContractSelector().getItems().size() > 0) {
            contractVariationParts.getContractSelector().getItems().clear();
        }
        contractVariationContractData.clearAllContractData();

        List<ContractFullDataDTO> contractFullDataDTOList = applicationMainController.findAllDataForContractInForceAtDateByClientId(client.getClientId(), selectedDate);

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
                    "");
        }

        return new CompatibleVariationEvent(
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

    private EmailDataCreationDTO retrieveDateForEmailCreation(Path path, String attachedFileName){

        ClientDTO employerDTO = this.contractVariationParts.getClientSelector().getValue();
        PersonDTO employeeDTO = this.contractVariationParts.getContractSelector().getValue().getEmployee();
        String variationTypeText = EmailParameters.CONTRACT_EXTENSION_TEXT;
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
