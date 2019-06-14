package gmoldes.components.contract_documentation_control.controllers;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract_documentation_control.ContractDocumentationControlConstants;
import gmoldes.components.contract_documentation_control.components.ContractDocumentationControlAction;
import gmoldes.components.contract_documentation_control.components.ContractDocumentationControlData;
import gmoldes.components.contract_documentation_control.components.ContractDocumentationControlHeader;
import gmoldes.components.contract_documentation_control.components.ContractDocumentationControlSelector;
import gmoldes.components.contract_documentation_control.events.SelectClientEmployerEvent;
import gmoldes.components.contract_documentation_control.events.SelectEmployerEmployeeEvent;
import gmoldes.domain.client.ClientService;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.ContractService;
import gmoldes.domain.contract.dto.InitialContractDTO;
import gmoldes.domain.person.dto.PersonDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.text.Collator;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ContractDocumentationControlMainController extends AnchorPane {

    private static final Logger logger = Logger.getLogger(ContractDocumentationControlMainController.class.getSimpleName());

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

        contractDocumentationControlSelector.setOnChangeContractsInForceOnly(this::onChangeContractsInForceOnly);

        contractDocumentationControlSelector.setOnClientSelectorChange(this::onClientSelectorChange);
        contractDocumentationControlSelector.setOnEmployeeSelectorChange(this::onEmployeeSelectorChange);


        contractDocumentationControlSelector.getClientSelector().setStyle(ContractDocumentationControlConstants.BLUE_COLOR);
        contractDocumentationControlSelector.getEmployeeSelector().setStyle(ContractDocumentationControlConstants.BLUE_COLOR);
        contractDocumentationControlSelector.getContractSelector().setStyle(ContractDocumentationControlConstants.BLUE_COLOR);


        contractDocumentationControlAction.setOnExitButton(this::onExitButton);
    }

    private void onChangeContractsInForceOnly(MouseEvent event){

        loadClientSelector();

        contractDocumentationControlSelector.getEmployeeSelector().getSelectionModel().clearSelection();
        contractDocumentationControlSelector.getEmployeeSelector().getItems().clear();

        contractDocumentationControlSelector.getContractSelector().getSelectionModel().clearSelection();
        contractDocumentationControlSelector.getContractSelector().getItems().clear();
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

    private void onClientSelectorChange(SelectClientEmployerEvent employerEvent){

        contractDocumentationControlSelector.getContractSelector().getSelectionModel().clearSelection();
        contractDocumentationControlSelector.getContractSelector().getItems().clear();

        ClientDTO clientDTO = employerEvent.getSelectedClientEmployer();

        ContractService contractService = ContractService.ContractServiceFactory.getInstance();
        List<PersonDTO> employeesOfSelectedClientDTO = contractService.findAllEmployeesByClientId(clientDTO.getClientId());

        List<PersonDTO> employeesOfSelectedClientDTOWithOutDuplicates = new ArrayList<>();

        Map<Integer, PersonDTO> personDTOMap = new HashMap<>();

        for (PersonDTO personDTO : employeesOfSelectedClientDTO) {
            personDTOMap.put(personDTO.getIdpersona(), personDTO);
        }

        for (Map.Entry<Integer, PersonDTO> itemMap : personDTOMap.entrySet()) {
            employeesOfSelectedClientDTOWithOutDuplicates.add(itemMap.getValue());
        }

        Collator primaryCollator = Collator.getInstance(new Locale("es","ES"));
        primaryCollator.setStrength(Collator.PRIMARY);

        List<PersonDTO> sortedPersonDTOList = employeesOfSelectedClientDTOWithOutDuplicates
                .stream()
                .sorted(Comparator.comparing(PersonDTO::toString, primaryCollator)).collect(Collectors.toList());

        ObservableList<PersonDTO> sortedPersonDTOOLL = FXCollections.observableArrayList(sortedPersonDTOList);

        contractDocumentationControlSelector.getEmployeeSelector().setItems(sortedPersonDTOOLL);
    }

    private void onEmployeeSelectorChange(SelectEmployerEmployeeEvent employerEmployeeEvent){
        ClientDTO clientDTO = employerEmployeeEvent.getSelectedClientEmployer();
        PersonDTO personDTO = employerEmployeeEvent.getNewEmployeeSelected();
        List<Integer> contractsList = new ArrayList<>();

        ContractService contractService = ContractService.ContractServiceFactory.getInstance();

        List<InitialContractDTO> initialContractDTOList = contractService.findAllInitialContract();
        for(InitialContractDTO initialContractDTO : initialContractDTOList){
            if(initialContractDTO.getContractJsonData().getClientGMId().equals(clientDTO.getClientId()) &&
                    initialContractDTO.getContractJsonData().getWorkerId().equals(personDTO.getIdpersona())){
                contractsList.add(initialContractDTO.getContractNumber());
            }
        }

        ObservableList<Integer> contractsOL = FXCollections.observableArrayList(contractsList);
        contractDocumentationControlSelector.getContractSelector().setItems(contractsOL);
        if(contractsOL.size() == 1){
            contractDocumentationControlSelector.getContractSelector().getSelectionModel().select(0);
        }

    }

    private void onExitButton(MouseEvent event){
        logger.info("Contract documentation control: exiting program.");

        Stage stage = (Stage) contractDocumentationControlHeader.getScene().getWindow();
        stage.close();
    }
}
