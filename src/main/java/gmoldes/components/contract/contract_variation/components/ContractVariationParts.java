package gmoldes.components.contract.contract_variation.components;

import gmoldes.ApplicationController;
import gmoldes.components.ViewLoader;
import gmoldes.domain.client.dto.ClientDTO;
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

    private Parent parent;

    @FXML
    private ChoiceBox<ClientDTO> client;
    @FXML
    private ChoiceBox<ContractInForceNowDataDTO> contract;

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
    }

    private void loadClientForContractVariation(ActionEvent event) {
        client.getItems().clear();
        ApplicationController applicationController = new ApplicationController();
        List<ClientDTO> clientDTOList = applicationController.findAllClientWithContractInForceAtDate(LocalDate.now());

        List<ClientDTO> clientDTOListSorted = clientDTOList
                .stream()
                .sorted(Comparator.comparing(ClientDTO::getPersonOrCompanyName)).collect(Collectors.toList());

        ObservableList<ClientDTO> clientDTOS = FXCollections.observableArrayList(clientDTOListSorted);
        client.setItems(clientDTOS);
    }
}
