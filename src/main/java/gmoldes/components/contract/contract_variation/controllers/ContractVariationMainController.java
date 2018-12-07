package gmoldes.components.contract.contract_variation.controllers;

import gmoldes.ApplicationMainController;
import gmoldes.components.ViewLoader;
import gmoldes.components.contract.contract_variation.components.*;
import gmoldes.components.contract.contract_variation.events.ClientChangeEvent;
import gmoldes.components.contract.contract_variation.events.CompatibleVariationEvent;
import gmoldes.components.contract.contract_variation.events.MessageEvent;
import gmoldes.components.contract.manager.ContractManager;
import gmoldes.components.contract.new_contract.components.ContractConstants;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.dto.ContractFullDataDTO;
import gmoldes.utilities.Message;
import gmoldes.utilities.Parameters;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.Period;
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

        //contractVariationTypes.setDisable(true);

        contractVariationParts.setOnClientSelectorAction(this::onChangeEmployer);
        contractVariationParts.setOnContractSelectorAction(this::onContractSelectorAction);

        contractVariationTypes.setOnContractExtinction(this::onContractExtinctionSelected);
        contractVariationTypes.setOnContractExtension(this::onContractExtensionSelected);
        contractVariationTypes.setOnContractConversion(this::onContractConversionSelected);

        holidaysToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
            }
        });

        contractVariationContractVariations.getContractVariationContractExtinction().getRbHolidaysYes().setToggleGroup(holidaysToggleGroup);
        contractVariationContractVariations.getContractVariationContractExtinction().getRbHolidaysNo().setToggleGroup(holidaysToggleGroup);


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

        contractVariationTypes.disableProperty().bind(this.contractVariationParts.getContractSelector().valueProperty().isNull());
        contractVariationContractVariations.getContractVariationStackPane().disableProperty().bind(this.contractVariationParts.getContractSelector().valueProperty().isNull());
        contractVariationContractVariations.getContractVariationStackPane().visibleProperty().bind(this.contractVariationParts.getContractSelector().valueProperty().isNotNull());


        contractVariationContractVariations.getContractVariationContractExtinction().getContractExtinctionGroup().disableProperty().bind(this.contractVariationTypes.getRbContractExtinction().selectedProperty().not());
        contractVariationContractVariations.getContractVariationContractExtinction().getContractExtinctionGroup().visibleProperty().bind(this.contractVariationTypes.getRbContractExtinction().selectedProperty());

        contractVariationContractVariations.getContractVariationContractExtension().getContractExtensionGroup().disableProperty().bind(this.contractVariationTypes.getRbContractExtension().selectedProperty().not());
        contractVariationContractVariations.getContractVariationContractExtension().getContractExtensionGroup().visibleProperty().bind(this.contractVariationTypes.getRbContractExtension().selectedProperty());

        contractVariationContractVariations.getContractVariationContractConversion().getContractConversionGroup().disableProperty().bind(this.contractVariationTypes.getRbContractConversion().selectedProperty().not());
        contractVariationContractVariations.getContractVariationContractConversion().getContractConversionGroup().visibleProperty().bind(this.contractVariationTypes.getRbContractConversion().selectedProperty());


        contractVariationContractVariations.getContractVariationContractExtension().getDateTo().valueProperty().addListener((ov, oldValue, newValue) -> setContractExtensionDuration(newValue));

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

            CompatibleVariationEvent event = contractExtinctionController.checkExistenceIncompatibleVariations();
            if(!event.getErrorContractVariationMessage().isEmpty()){
                Message.warningMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, event.getErrorContractVariationMessage());

                return;
            }



        }


//            ContractExtinctionController contractExtinctionController = new ContractExtinctionController(
//                    this.getScene(),
//                    contractVariationParts,
//                    contractVariationContractVariations);
//
//            CompatibleVariationEvent event = contractExtinctionController.verifyIsCorrectContractExtinctionData();
//            String errorMessage = event.getErrorContractVariationMessage();
//            if(!errorMessage.isEmpty()) {
//                Message.warningMessage(this.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, errorMessage);
//
//                return;
//            }
//
//            contractExtinctionController.checkExistenceIncompatibleVariations();
//
//
//
//
//                Integer contractNumber = contractVariationParts.getContractSelector()
//                        .getSelectionModel().getSelectedItem().getContractNewVersion().getContractNumber();
//                LocalDate dateFrom = contractVariationContractVariations.getContractVariationContractExtinction().getDateFrom().getValue();
//
//                ApplicationMainManager applicationMainManager = new ApplicationMainManager();
//                List <ContractNewVersionDTO> contractNewVersionDTOList = applicationMainManager.findAllContractsInForceAtDate(dateFrom);
//
////               patata compatibleVariationEvent = checkExistenceIncompatibleVariations();
//
//
//
//                Boolean bol = contractExtinctionController.manageContractExtinction();
//
//
//            return;
//        }

        // Contract extension is selected -----------------------------------------------
        if(contractVariationTypes.getRbContractExtension().isSelected()) {
            contractVariationContractExtensionSelected();
        }


        // Contract conversion is selected -----------------------------------------------
        if(contractVariationTypes.getRbContractConversion().isSelected()){
            contractVariationContractConversionSelected();
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

    private Boolean dateToNotifyContractVariationToAdministrationIsCorrect(){

        LocalDate limitDatePreviuosOfNotifyToAdministration = contractVariationParts.getInForceDate().getValue().minusDays(3L);

        if(Period.between(contractVariationParts.getInForceDate().getValue(), LocalDate.now().minusDays(3L)).getDays() <= 0){
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
