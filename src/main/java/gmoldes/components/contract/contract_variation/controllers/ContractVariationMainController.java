package gmoldes.components.contract.contract_variation.controllers;

import gmoldes.ApplicationMainController;
import gmoldes.components.ViewLoader;
import gmoldes.components.contract.contract_variation.components.*;
import gmoldes.components.contract.contract_variation.events.ClientChangeEvent;
import gmoldes.components.contract.contract_variation.events.DateChangeEvent;
import gmoldes.components.contract.controllers.TypesContractVariationsController;
import gmoldes.components.contract.manager.ContractManager;
import gmoldes.components.contract.new_contract.components.ContractConstants;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.dto.ContractFullDataDTO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract.dto.ContractVariationDTO;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;
import gmoldes.utilities.Message;
import gmoldes.utilities.Parameters;
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

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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

        contractVariationParts.setOnInForceDateChanged(this::onInForceDateChanged);
        contractVariationParts.setOnLoadDataInClientSelector(this::clientSelectorLoadDataAtDate);
        contractVariationParts.setOnClientSelectorAction(this::onChangeEmployer);
        contractVariationParts.setOnContractSelectorAction(this::onContractSelectorAction);

        contractVariationContractVariations.setOnContractExtinction(this::onContractExtinction);

        contractVariationToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
            }
        });
        contractVariationParts.loadDataInClientSelector();

        contractVariationContractVariations.getContractVariationContractExtinction().getRbContractExtinction().setToggleGroup(contractVariationToggleGroup);
        contractVariationContractVariations.getContractVariationContractExtension().getRbContractExtension().setToggleGroup(contractVariationToggleGroup);
        contractVariationContractVariations.getContractVariationContractConversion().getRbContractConversion().setToggleGroup(contractVariationToggleGroup);

        contractVariationContractVariations.setDisable(true);

        contractVariationActionComponents.setOnOkButton(this::onOkButton);
        contractVariationActionComponents.setOnExitButton(this::onExitButton);

    }

    public ContractVariationMainController() {
        logger.info("Initializing ContractVariation Main fxml");
        this.parent = ViewLoader.load(this, CONTRACT_VARIATION_MAIN_FXML);
    }

    private void clientSelectorLoadDataAtDate(DateChangeEvent event) {
        contractVariationParts.getClientSelector().getItems().clear();
        contractVariationParts.getContractSelector().getItems().clear();
        contractVariationContractData.clearAllContractData();
        List<ClientDTO> clientDTOList = applicationMainController.findAllClientWithContractInForceAtDate(event.getDate());

        List<ClientDTO> clientDTOListSorted = clientDTOList
                .stream()
                .sorted(Comparator.comparing(ClientDTO::getPersonOrCompanyName)).collect(Collectors.toList());

        ObservableList<ClientDTO> clientDTOS = FXCollections.observableArrayList(clientDTOListSorted);
        contractVariationParts.getClientSelector().setItems(clientDTOS);

        for(ClientDTO clientDTO : clientDTOS){
            if(event.getClient() != null &&
                    clientDTO.getClientId().equals(event.getClient().getClientId())){
                contractVariationParts.getClientSelector().getSelectionModel().select(clientDTO);
            }
        }
    }

    private void onInForceDateChanged(DateChangeEvent event){

        clientSelectorLoadDataAtDate(event);
        contractVariationContractVariations.getContractVariationContractExtinction().getRbContractExtinction().setSelected(false);
        contractVariationContractVariations.getContractVariationContractExtension().getRbContractExtension().setSelected(false);
        contractVariationContractVariations.getContractVariationContractConversion().getRbContractConversion().setSelected(false);

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
        }
    }

    private void onOkButton(MouseEvent evet){

        if(contractVariationContractVariations.getContractVariationContractExtinction().getRbContractExtinction().isSelected()){
            if(isCorrectContractExtinctionData()){

                Integer contractVariationUpdatedId = updateLastContractVariation();
                System.out.println("Actualizado contractVariationId: " + contractVariationUpdatedId);

                // Persist new contract variation (extinction)
                //ContractNewVersionDTO newContractVariationToPersistDTO = contractNewVersionExtinctedDTO;

                //TypesContractVariationsDTO typesContractVariationsExtinctionSelectedDTO = contractVariationContractVariations
                //        .getContractVariationContractExtinction().getExtinctionCauseSelector().getValue();

                //newContractVariationToPersistDTO.setId(null);
                //newContractVariationToPersistDTO.setVariationType(typesContractVariationsExtinctionSelectedDTO.getId_variation());
                //newContractVariationToPersistDTO.setModificationDate(dateOfExtinction);
                //newContractVariationToPersistDTO.setEndingDate(dateOfExtinction);
                //persistNewContractVariation(newContractVariationToPersistDTO);

                // Update initial contract to extinction
                //ContractNewVersionDTO initialContractToUpdateDTO = contractNewVersionExtinctedDTO;



                //updateInitialContract();
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

    private Boolean isCorrectDateToContractVariation(){

        if(Period.between(contractVariationParts.getInForceDate().getValue(), LocalDate.now().minusDays(3L)).getDays() <= 0){
            return true;
        }

        Message.warningMessage(contractVariationParts.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractConstants.IS_NOT_VALID_DATE_FOR_CONTRACT_VARIATION);
        return false;
    }

    private Boolean isCorrectContractExtinctionData(){

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
                !contractVariationContractVariations.getContractVariationContractExtinction().getRbHolidaysNo().isSelected()
        ){
            System.out.println("No se ha seleccionado la situacion de las vacaciones.");
            return false;
        }
        System.out.println("Aparentemente esta todo correcto.");
        return true;
    }

    private Integer updateLastContractVariation(){
        ContractNewVersionDTO contractNewVersionExtinctedDTO = contractVariationParts
                .getContractSelector().getSelectionModel().getSelectedItem().getContractNewVersion();

        LocalDate dateOfExtinction = contractVariationContractVariations.getContractVariationContractExtinction()
                .getDateFrom().getValue();

        ContractNewVersionDTO contractVariationToUpdateDTO = contractNewVersionExtinctedDTO;
        contractVariationToUpdateDTO.setModificationDate(dateOfExtinction);
        contractVariationToUpdateDTO.setEndingDate(dateOfExtinction);

        return contractManager.updateContractVariation(contractVariationToUpdateDTO);
    }
}
