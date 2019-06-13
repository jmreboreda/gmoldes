package gmoldes.components.contract_documentation_control.controllers;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract_documentation_control.ContractDocumentationControlConstants;
import gmoldes.components.contract_documentation_control.components.ContractDocumentationControlAction;
import gmoldes.components.contract_documentation_control.components.ContractDocumentationControlData;
import gmoldes.components.contract_documentation_control.components.ContractDocumentationControlHeader;
import gmoldes.components.contract_documentation_control.components.ContractDocumentationControlSelector;
import gmoldes.domain.client.ClientService;
import gmoldes.domain.client.dto.ClientDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.text.Collator;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ContractDocumentationControlMainController extends AnchorPane {

    private static final String CONTRACT_DOCUMENTATION_CONTROL_MAIN_FXML = "/fxml/contract_documentation_control/contract_documentation_control_main.fxml";

    private Parent parent;
    private Stage stage;

    @FXML
    ContractDocumentationControlHeader contractDocumentationControlHeader;
    @FXML
    ContractDocumentationControlSelector contractDocumentationControlSelector;
    @FXML
    ContractDocumentationControlData contractDocumentationControlData;
    @FXML
    ContractDocumentationControlAction contractDocumentationControlAction;


    public ContractDocumentationControlMainController() {
        this.parent = ViewLoader.load(this, CONTRACT_DOCUMENTATION_CONTROL_MAIN_FXML);

        loadClientSelector();
    }

    public void initialize(){
        contractDocumentationControlSelector.getClientSelector().setStyle(ContractDocumentationControlConstants.BLUE_COLOR);
        contractDocumentationControlSelector.getEmployerSelector().setStyle(ContractDocumentationControlConstants.BLUE_COLOR);
        contractDocumentationControlSelector.getContractSelector().setStyle(ContractDocumentationControlConstants.BLUE_COLOR);

        contractDocumentationControlSelector.setOnChangeContractsInForceOnly(this::onChangeContractsInForceOnly);

    }

    private void onChangeContractsInForceOnly(MouseEvent event){
        loadClientSelector();
    }

    private void loadClientSelector(){
        List<ClientDTO> clientDTOList;
        ClientService clientService = ClientService.ClientServiceFactory.getInstance();

        if(contractDocumentationControlSelector.getContractsInForceOnly().isSelected()) {
            clientDTOList = clientService.findAllClientWithContractInForceAtDate(LocalDate.now());
        }else{
            clientDTOList = clientService.findAllActiveClientWithContractHistory();
        }

        Collator primaryCollator = Collator.getInstance(new Locale("es","ES"));
        primaryCollator.setStrength(Collator.PRIMARY);

        List<ClientDTO> sortedClientDTOList = clientDTOList
                .stream()
                .sorted(Comparator.comparing(ClientDTO::toString, primaryCollator)).collect(Collectors.toList());

        ObservableList<ClientDTO> clientDTOOL = FXCollections.observableArrayList(sortedClientDTOList);
        contractDocumentationControlSelector.loadClientSelector(clientDTOOL);

    }
}
