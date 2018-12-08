package gmoldes.components.contract.contract_variation.controllers;

import gmoldes.ApplicationMainController;
import gmoldes.components.ViewLoader;
import gmoldes.components.contract.contract_variation.components.*;
import gmoldes.components.contract.contract_variation.events.ClientChangeEvent;
import gmoldes.components.contract.contract_variation.events.CompatibleVariationEvent;
import gmoldes.components.contract.contract_variation.events.MessageEvent;
import gmoldes.components.contract.controllers.TypesContractVariationsController;
import gmoldes.components.contract.manager.ContractManager;
import gmoldes.components.contract.new_contract.components.ContractConstants;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.dto.ContractFullDataDTO;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;
import gmoldes.utilities.Message;
import gmoldes.utilities.Parameters;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
    private ContractManager contractManager = new ContractManager();

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
    public void initialize() {

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

        //Binding
        contractVariationTypes.disableProperty().bind(this.contractVariationParts.getContractSelector().valueProperty().isNull());

        contractVariationContractVariations.getContractVariationContractExtinction().getContractExtinctionGroup().visibleProperty().bind(this.contractVariationTypes.getRbContractExtinction().selectedProperty());
        contractVariationContractVariations.getContractVariationContractExtension().getContractExtensionGroup().visibleProperty().bind(this.contractVariationTypes.getRbContractExtension().selectedProperty());
        contractVariationContractVariations.getContractVariationContractConversion().getContractConversionGroup().visibleProperty().bind(this.contractVariationTypes.getRbContractConversion().selectedProperty());

        contractVariationContractVariations.getContractVariationContractExtension().getDateTo().valueProperty().addListener((ov, oldValue, newValue) -> setContractExtensionDuration(newValue));

        // Button actions
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
        contractVariationTypes.getHourNotification().setText(LocalTime.now().toString());
        contractVariationTypes.getHourNotification().requestFocus();


        contractVariationActionComponents.getOkButton().setDisable(false);


//        if(!dateToNotifyContractVariationToAdministrationIsCorrect()){
//            contractVariationContractVariations.getContractVariationContractExtinction().getRbContractExtinction().setSelected(false);
//            return;
//        }
//
//        if(contractVariationContractVariations.getContractVariationContractExtinction().getRbContractExtinction().isSelected()) {
//
//        }
    }

    private void onContractExtensionSelected(MouseEvent event){

        contractVariationContractVariations.getContractVariationContractExtension().cleanComponents();
        contractVariationContractVariations.getContractVariationContractExtension().toFront();
        contractVariationTypes.getHourNotification().requestFocus();

        contractVariationActionComponents.getOkButton().setDisable(false);
//        if(!dateToNotifyContractVariationToAdministrationIsCorrect()){
//            contractVariationContractVariations.getContractVariationContractExtinction().getRbContractExtinction().setSelected(false);
//            return;
//        }
//
//        if(contractVariationContractVariations.getContractVariationContractExtension().getRbContractExtension().isSelected()) {
//
//        }
    }

    private void onContractConversionSelected(MouseEvent event){

        contractVariationContractVariations.getContractVariationContractConversion().cleanComponents();
        contractVariationContractVariations.getContractVariationContractConversion().toFront();
        contractVariationTypes.getHourNotification().requestFocus();

        contractVariationActionComponents.getOkButton().setDisable(false);
//        if(!dateToNotifyContractVariationToAdministrationIsCorrect()){
//            contractVariationContractVariations.getContractVariationContractExtinction().getRbContractExtinction().setSelected(false);
//            return;
//        }
//
//        if(contractVariationContractVariations.getContractVariationContractExtension().getRbContractExtension().isSelected()) {
//
//        }
    }
    private void onOkButton(MouseEvent evet){

        // Contract extinction is selected -----------------------------------------------
        RadioButton rbContractExtinction = contractVariationTypes.getRbContractExtinction();

        if(rbContractExtinction.isSelected()) {

            ContractExtinctionController contractExtinctionController = new ContractExtinctionController(
                    this.getScene(), contractVariationParts, contractVariationTypes, contractVariationContractVariations);

            MessageEvent messageEvent = contractExtinctionController.verifyIsCorrectContractExtinctionData();
            String messageText = messageEvent.getMessageText();
            if (!messageText.equals(ContractConstants.NECESSARY_DATA_FOR_VARIATION_CONTRACT_HAVE_BEEN_INTRODUCED)) {
                Message.warningMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, messageText);

                return;
            }

            System.out.println(messageText);    // <- NECESSARY_DATA_FOR_VARIATION_CONTRACT_HAVE_BEEN_INTRODUCED

            if(!dateToNotifyContractVariationToAdministrationIsCorrect(contractVariationContractVariations.getContractVariationContractExtinction().getDateFrom().getValue())){

                return;
            }

            CompatibleVariationEvent event = contractExtinctionController.checkExistenceIncompatibleVariations();
            if(!event.getErrorContractVariationMessage().isEmpty()){
                Message.warningMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, event.getErrorContractVariationMessage());

                return;
            }


        }

        // Contract extension is selected -----------------------------------------------
        RadioButton rbContractExtension = contractVariationTypes.getRbContractExtension();

        if(rbContractExtension.isSelected()) {

            ContractExtinctionController contractExtinctionController = new ContractExtinctionController(
                    this.getScene(), contractVariationParts, contractVariationTypes, contractVariationContractVariations);

            MessageEvent messageEvent = contractExtinctionController.verifyIsCorrectContractExtensionData();
            String messageText = messageEvent.getMessageText();
            if (!messageText.equals(ContractConstants.NECESSARY_DATA_FOR_VARIATION_CONTRACT_HAVE_BEEN_INTRODUCED)) {
                Message.warningMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, messageText);

                return;
            }

            System.out.println(messageText);    // <- NECESSARY_DATA_FOR_VARIATION_CONTRACT_HAVE_BEEN_INTRODUCED

            CompatibleVariationEvent event = contractExtinctionController.checkExistenceIncompatibleVariations();
            if(!event.getErrorContractVariationMessage().isEmpty()){
                Message.warningMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, event.getErrorContractVariationMessage());

                return;
            }
        }

        if(!Message.confirmationMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.PERSIST_CONTRACT_VARIATION_QUESTION)){
            return;
        }

            ContractExtinctionController contractExtinctionController = new ContractExtinctionController(
                    this.getScene(),
                    contractVariationParts,
                    contractVariationTypes,
                    contractVariationContractVariations);

            Boolean isCorrectManagement = contractExtinctionController.manageContractExtinction();

            if(isCorrectManagement){
                contractVariationParts.setMouseTransparent(true);
                contractVariationTypes.setMouseTransparent(true);
                contractVariationContractVariations.setMouseTransparent(true);
                contractVariationActionComponents.getOkButton().setDisable(true);
                contractVariationActionComponents.getSendMailButton().setDisable(false);
            }

            return;
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

/**    private CompatibleVariationEvent checkExistenceIncompatibleVariations(){

        CompatibleVariationEvent compatibleVariationEvent = new CompatibleVariationEvent(
                null,
                null,
                null,
                null);

        ContractController contractController = new ContractController();

        //contractController.find

        // Contract extension
        if(contractVariationContractVariations.getContractVariationContractExtension().getRbContractExtension().isSelected()) {
            ContractExtensionController contractExtensionController = new ContractExtensionController(
                    this.getScene(),
                    contractVariationParts,
                    contractVariationContractVariations);

//            compatibleVariationEvent = contractExtensionController.checkExistenceIncompatibleVariations();
        }

        return compatibleVariationEvent;
    }
**/

//    private CompatibleVariationEvent contractExtinctionVerifyCorrectData(){
//
//        ContractExtinctionController contractExtinctionController = new ContractExtinctionController(
//                this.getScene(),
//                contractVariationParts,
//                contractVariationContractVariations);
//
//        CompatibleVariationEvent compatibleVariationEvent = contractExtinctionController.verifyIsCorrectContractExtinctionData();
//        if(!compatibleVariationEvent.getErrorContractVariationMessage().isEmpty()) {
//
//            return compatibleVariationEvent;
//            }
//
//            contractVariationActionComponents.getOkButton().setDisable(true);
//            contractVariationActionComponents.getSendMailButton().setDisable(false);
//            contractVariationParts.setMouseTransparent(true);
//            contractVariationContractData.setMouseTransparent(true);
//            contractVariationContractVariations.setMouseTransparent(true);
//
//            return new CompatibleVariationEvent(
//                    true,
//                    null,
//                    null,
//                    null);
//    }

    private void contractVariationContractExtensionSelected(){

        ContractExtensionController contractExtensionController = new ContractExtensionController(
                this.getScene(),
                contractVariationParts,
                contractVariationTypes,
                contractVariationContractVariations);

        Boolean isOkManagementContractExtension = contractExtensionController.manageContractExtension();

        if(isOkManagementContractExtension) {

            contractVariationActionComponents.getOkButton().setDisable(true);
            contractVariationActionComponents.getSendMailButton().setDisable(false);
            contractVariationParts.setMouseTransparent(true);
            contractVariationContractData.setMouseTransparent(true);
            contractVariationContractVariations.setMouseTransparent(true);
        }
    }

    private void contractVariationContractConversionSelected(){



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

            contractVariationContractVariations.getContractVariationContractExtension().getContractExtensionDuration().setText(contractExtensionDuration + " días");
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

    private Boolean dateToNotifyContractVariationToAdministrationIsCorrect(LocalDate date){

        LocalDate limitDatePreviousOfNotifyToAdministration = contractVariationParts.getInForceDate().getValue().minusDays(3L);

        if(ChronoUnit.DAYS.between(limitDatePreviousOfNotifyToAdministration, date) >= 0){
            return true;
        }

        Boolean isCorrectDate = Message.confirmationMessage(contractVariationParts.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT,
                ContractConstants.VERIFY_IS_VALID_DATE_TO_NOTIFY_CONTRACT_VARIATION_TO_ADMINISTRATION);
        if(isCorrectDate){
            return true;
        }

        return false;
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
}
