package gmoldes.components.contract.contract_variation.controllers;

import gmoldes.ApplicationMainController;
import gmoldes.components.ViewLoader;
import gmoldes.components.contract.contract_variation.components.*;
import gmoldes.components.contract.contract_variation.events.ClientChangeEvent;
import gmoldes.components.contract.contract_variation.events.DateChangeEvent;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.dto.ContractFullDataDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ContractVariationMainController extends VBox {

    private static final Logger logger = Logger.getLogger(ContractVariationMainController.class.getSimpleName());
    private static final String CONTRACTVARIATION_MAIN_FXML = "/fxml/contract_variations/contractvariation_main.fxml";

    private Parent parent;

    private ApplicationMainController applicationMainController = new ApplicationMainController();

    @FXML
    private ContractVariationHeader contractVariationHeader;
    @FXML
    private ContractVariationParts contractVariationParts;
    @FXML
    private ContractVariationContractData contractVariationContractData;
    @FXML
    private ContractVariationContractExtinction contractVariationContractExtinction;
    @FXML
    private ContractVariationActionComponents contractVariationActionComponents;

    @FXML
    public void initialize() {

        contractVariationParts.setOnInForceDateChanged(this::onInForceDateChanged);
        contractVariationParts.setOnLoadDataInClientSelector(this::clientSelectorLoadData);
        contractVariationParts.setOnClientSelectorAction(this::onChangeEmployer);
        contractVariationParts.setOnContractSelectorAction(this::onContractSelectorAction);
        contractVariationParts.loadDataInClientSelector();

        contractVariationActionComponents.setOnExitButton(this::onExitButton);

    }

    public ContractVariationMainController() {
        logger.info("Initializing ContractVariation Main fxml");
        this.parent = ViewLoader.load(this, CONTRACTVARIATION_MAIN_FXML);
    }

    private void clientSelectorLoadData(ActionEvent event) {
        contractVariationParts.getClientSelector().getItems().clear();
        List<ClientDTO> clientDTOList = applicationMainController.findAllClientWithContractInForceAtDate(LocalDate.now());

        List<ClientDTO> clientDTOListSorted = clientDTOList
                .stream()
                .sorted(Comparator.comparing(ClientDTO::getPersonOrCompanyName)).collect(Collectors.toList());

        ObservableList<ClientDTO> clientDTOS = FXCollections.observableArrayList(clientDTOListSorted);
        contractVariationParts.getClientSelector().setItems(clientDTOS);
    }

    private void onInForceDateChanged(DateChangeEvent event){

        LocalDate selectedDate = event.getDate();
        ClientDTO selectedClient = event.getClient();
        refreshContractSelectorData(selectedClient, selectedDate);

    }

    private void onChangeEmployer(ClientChangeEvent event){

        if(contractVariationParts.getClientSelector().getSelectionModel().getSelectedItem() == null){
            return;
        }

        LocalDate selectedDate = event.getDate();
        ClientDTO selectedClient = event.getClient();
        refreshContractSelectorData(selectedClient, selectedDate);
    }

    private void onContractSelectorAction(ActionEvent event){

        if(contractVariationParts.getContractSelector().getSelectionModel().getSelectedItem() == null){
            return;
        }

        ContractFullDataDTO selectedContract = contractVariationParts.getContractSelector().getSelectionModel().getSelectedItem();
        contractVariationContractData.setAllContractData(selectedContract);

    }

    private void onExitButton(MouseEvent event){

        Stage stage = (Stage) contractVariationParts.getScene().getWindow();
        stage.close();
    }

    private void refreshContractSelectorData(ClientDTO client, LocalDate selectedDate){

        contractVariationParts.getContractSelector().getItems().clear();
        contractVariationContractData.clearAllContractData();

        List<ContractFullDataDTO> contractFullDataDTOList = applicationMainController.findAllDataForContractInForceByClientId(client.getClientId(), selectedDate);

        ObservableList<ContractFullDataDTO> contractFullDataDTOS = FXCollections.observableArrayList(contractFullDataDTOList);
        contractVariationParts.getContractSelector().setItems(contractFullDataDTOS);

        if(contractFullDataDTOS.size() == 1){
            contractVariationParts.getContractSelector().getSelectionModel().select(0);
        }
    }
}
