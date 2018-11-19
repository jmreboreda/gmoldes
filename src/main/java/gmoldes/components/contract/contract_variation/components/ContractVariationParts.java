package gmoldes.components.contract.contract_variation.components;

import gmoldes.ApplicationMainController;
import gmoldes.components.ViewLoader;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.dto.ContractFullDataDTO;
import gmoldes.domain.contract.dto.ContractInForceNowDataDTO;
import gmoldes.utilities.Parameters;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ContractVariationParts extends VBox {

    private static final String CONTRACT_VARIATION_PARTS_FXML = "/fxml/contract_variations/contractvariation_parts.fxml";

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parameters.DEFAULT_DATE_FORMAT);
    private ApplicationMainController applicationMainController = new ApplicationMainController();

    private Parent parent;

    @FXML
    private ChoiceBox<ClientDTO> client;
    @FXML
    private ChoiceBox<ContractFullDataDTO> contract;

    public ContractVariationParts() {
        this.parent = ViewLoader.load(this, CONTRACT_VARIATION_PARTS_FXML);
    }

    @FXML
    public void initialize() {

        client.setOnAction(this::onChangeEmployer);
//        contract.setOnAction(this::loadDataForContractInForceSelected);

        loadClientForContractVariation(new ActionEvent());
    }

    private void onChangeEmployer(ActionEvent event){
        contract.getItems().clear();
        if(client.getSelectionModel().getSelectedItem() == null){
            return;
        }

        ClientDTO selectedClient = client.getSelectionModel().getSelectedItem();
        List<ContractFullDataDTO> contractFullDataDTOList = applicationMainController.findAllContractInForceByClientId(selectedClient.getClientId());

        ObservableList<ContractFullDataDTO> contractFullDataDTOS = FXCollections.observableArrayList(contractFullDataDTOList);
        contract.setItems(contractFullDataDTOS);
    }

    private void loadClientForContractVariation(ActionEvent event) {
        client.getItems().clear();
        List<ClientDTO> clientDTOList = applicationMainController.findAllClientWithContractInForceAtDate(LocalDate.now());

        List<ClientDTO> clientDTOListSorted = clientDTOList
                .stream()
                .sorted(Comparator.comparing(ClientDTO::getPersonOrCompanyName)).collect(Collectors.toList());

        ObservableList<ClientDTO> clientDTOS = FXCollections.observableArrayList(clientDTOListSorted);
        client.setItems(clientDTOS);
    }
}
