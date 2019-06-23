package gmoldes.components.contract.consultation_contract.controllers;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract.consultation_contract.components.ConsultationContractAction;
import gmoldes.components.contract.consultation_contract.components.ConsultationContractData;
import gmoldes.components.contract.consultation_contract.components.ConsultationContractHeader;
import gmoldes.components.contract.consultation_contract.components.ConsultationContractSelector;
import gmoldes.components.contract.consultation_contract.events.ContractDataTableSelectedRowEvent;
import gmoldes.components.contract_documentation_control.ContractDocumentationControlConstants;
import gmoldes.components.contract_documentation_control.events.ContractSelectedEvent;
import gmoldes.components.contract_documentation_control.events.SelectClientEmployerEvent;
import gmoldes.components.contract_documentation_control.events.SelectEmployerEmployeeEvent;
import gmoldes.domain.client.ClientService;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.consultation_contract.dto.ConsultationContractDataTableDTO;
import gmoldes.domain.contract.ContractService;
import gmoldes.domain.contract.ContractTypeService;
import gmoldes.domain.contract.TypesContractVariationsService;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract.dto.InitialContractDTO;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;
import gmoldes.domain.person.PersonService;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.traceability_contract_documentation.TraceabilityService;
import gmoldes.domain.traceability_contract_documentation.dto.TraceabilityContractDocumentationDTO;
import gmoldes.utilities.Utilities;
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

public class ConsultationContractMainController extends AnchorPane {

    private static final Logger logger = Logger.getLogger(ConsultationContractMainController.class.getSimpleName());

    private static final String CONSULTATION_CONTRACT_MAIN_FXML = "/fxml/consultation_contract/consultation_contract_main.fxml";

    private Parent parent;
    private Stage stage;

    @FXML
    ConsultationContractHeader consultationContractHeader;
    @FXML
    ConsultationContractSelector consultationContractSelector;
    @FXML
    ConsultationContractData consultationContractData;
    @FXML
    ConsultationContractAction consultationContractAction;


    public ConsultationContractMainController() {
        this.parent = ViewLoader.load(this, CONSULTATION_CONTRACT_MAIN_FXML);

        loadClientSelector();
    }

    public void initialize(){

        consultationContractSelector.setOnActiveClientsOnly(this::onActiveClientsOnly);
        consultationContractSelector.setOnContractInForceOnly(this::onContractInForceOnly);
        consultationContractSelector.setOnAllContract(this::onAllContract);


        consultationContractSelector.setOnClientSelectorChange(this::onClientSelectorChange);
        consultationContractSelector.setOnEmployeeSelectorChange(this::onEmployeeSelectorChange);
        consultationContractSelector.setOnContractSelectorChange(this::onContractSelectorChange);

        consultationContractData.setOnSelectedRowChange(this::onContractDataTableSelectedRowChange);

        consultationContractSelector.getClientSelector().setStyle(ContractDocumentationControlConstants.BLUE_COLOR);
        consultationContractSelector.getEmployeeSelector().setStyle(ContractDocumentationControlConstants.BLUE_COLOR);
        consultationContractSelector.getContractSelector().setStyle(ContractDocumentationControlConstants.BLUE_COLOR);

        consultationContractAction.setOnExitButton(this::onExitButton);
    }

    private void loadClientSelector(){

        consultationContractSelector.getEmployeeSelector().getSelectionModel().clearSelection();
        consultationContractSelector.getEmployeeSelector().getItems().clear();

        consultationContractSelector.getContractSelector().getSelectionModel().clearSelection();;
        consultationContractSelector.getContractSelector().getItems().clear();

        consultationContractData.getIdentificationContractNumberINEM().clear();
        consultationContractData.getContractTypeDescription().clear();

        consultationContractData.getConsultationContractDataTableDTOTable().getItems().clear();

        List<ClientDTO> clientDTOToClientSelectorList = new ArrayList<>();
        List<ClientDTO> clientDTOWithContractList = new ArrayList<>();

        ClientService clientService = ClientService.ClientServiceFactory.getInstance();
        ContractService contractService = ContractService.ContractServiceFactory.getInstance();

        // Active clients with contract
        if(consultationContractSelector.getActiveClientsOnly().isSelected()) {
            // Active clients with contract in force
            if(consultationContractSelector.getContractInForceOnly().isSelected()){
                List<ClientDTO> activeClientDTOWithContractsInForceAtDate = clientService.findAllClientWithContractInForceAtDate(LocalDate.now());
                clientDTOToClientSelectorList.addAll(activeClientDTOWithContractsInForceAtDate);
            }else{
                // Active clients contract history
                List<ClientDTO> activeClientDTOWithContracts = clientService.findAllActiveClientWithContractHistory();
                clientDTOToClientSelectorList.addAll(activeClientDTOWithContracts);
            }
        }else{
            //All clients with contract
            List<InitialContractDTO> initialContractDTOList = contractService.findAllInitialContract();
            for(InitialContractDTO initialContractDTO : initialContractDTOList){
                clientDTOWithContractList.add(clientService.findClientById(initialContractDTO.getContractJsonData().getClientGMId()));
            }

            Map<Integer, ClientDTO> clientDTOMap = new HashMap<>();

            for (ClientDTO clientDTO : clientDTOWithContractList) {
                clientDTOMap.put(clientDTO.getClientId(), clientDTO);
            }

            List<ClientDTO> withoutDuplicatesClientDTOWithContractList = new ArrayList<>();
            for (Map.Entry<Integer, ClientDTO> itemMap : clientDTOMap.entrySet()) {
                withoutDuplicatesClientDTOWithContractList.add(itemMap.getValue());
            }

            Collator primaryCollator = Collator.getInstance(new Locale("es","ES"));
            primaryCollator.setStrength(Collator.PRIMARY);

            List<ClientDTO>  orderedWithoutDuplicatesClientDTOWithContractList = withoutDuplicatesClientDTOWithContractList
                    .stream()
                    .sorted(Comparator.comparing(ClientDTO::toString, primaryCollator)).collect(Collectors.toList());

            clientDTOToClientSelectorList.addAll(orderedWithoutDuplicatesClientDTOWithContractList);
        }

        Collator primaryCollator = Collator.getInstance(new Locale("es","ES"));
        primaryCollator.setStrength(Collator.PRIMARY);

        List<ClientDTO> sortedClientDTOList = clientDTOToClientSelectorList
                .stream()
                .sorted(Comparator.comparing(ClientDTO::toString, primaryCollator)).collect(Collectors.toList());

        ObservableList<ClientDTO> clientDTOOL = FXCollections.observableArrayList(sortedClientDTOList);
        consultationContractSelector.loadClientSelector(clientDTOOL);
    }

    private void onActiveClientsOnly(MouseEvent event){
        if(consultationContractSelector.getActiveClientsOnly().isSelected()){

        }else{
            consultationContractSelector.getAllContract().setSelected(true);
            consultationContractSelector.getContractInForceOnly().setSelected(false);
        }

        loadClientSelector();
    }

    private void onContractInForceOnly(MouseEvent event){
        consultationContractSelector.getContractSelector().getSelectionModel().clearSelection();
        consultationContractSelector.getContractSelector().getItems().clear();

        consultationContractData.getConsultationContractDataTableDTOTable().getItems().clear();

        consultationContractData.getIdentificationContractNumberINEM().clear();
        consultationContractData.getContractTypeDescription().clear();

        if(consultationContractSelector.getActiveClientsOnly().isSelected()){
           consultationContractSelector.getAllContract().setSelected(false);
        }else{
            consultationContractSelector.getActiveClientsOnly().setSelected(true);
            consultationContractSelector.getAllContract().setSelected(false);
        }

        loadClientSelector();
    }

    private void onAllContract(MouseEvent event){
        consultationContractSelector.getContractSelector().getSelectionModel().clearSelection();
        consultationContractSelector.getContractSelector().getItems().clear();

        consultationContractData.getConsultationContractDataTableDTOTable().getItems().clear();

        consultationContractData.getIdentificationContractNumberINEM().clear();
        consultationContractData.getContractTypeDescription().clear();

        loadClientSelector();
    }

    private void onClientSelectorChange(SelectClientEmployerEvent employerEvent){

        consultationContractSelector.getContractSelector().getSelectionModel().clearSelection();
        consultationContractSelector.getContractSelector().getItems().clear();

        consultationContractData.getConsultationContractDataTableDTOTable().getItems().clear();

        consultationContractData.getIdentificationContractNumberINEM().clear();
        consultationContractData.getContractTypeDescription().clear();

        consultationContractAction.getSaveButton().setDisable(true);

        List<PersonDTO> employeesOfSelectedClientDTOWithContract = new ArrayList<>();

        ClientDTO clientDTO = employerEvent.getSelectedClientEmployer();

        ClientService clientService = ClientService.ClientServiceFactory.getInstance();
        ContractService contractService = ContractService.ContractServiceFactory.getInstance();
        PersonService personService = PersonService.PersonServiceFactory.getInstance();

        if(consultationContractSelector.getActiveClientsOnly().isSelected()){
            if(consultationContractSelector.getContractInForceOnly().isSelected()){
                List<InitialContractDTO> initialContractInForceDTOList = contractService.findAllInitialContractInForceAtDate(LocalDate.now());
                for(InitialContractDTO initialContractDTO : initialContractInForceDTOList) {
                    if (initialContractDTO.getContractJsonData().getClientGMId().equals(clientDTO.getClientId())) {
                        employeesOfSelectedClientDTOWithContract.add(personService.findPersonById(initialContractDTO.getContractJsonData().getWorkerId()));
                    }
                }
            }else if(consultationContractSelector.getAllContract().isSelected()){
                List<InitialContractDTO> initialContractDTOList = contractService.findAllInitialContract();
                for(InitialContractDTO initialContractDTO : initialContractDTOList) {
                    if (initialContractDTO.getContractJsonData().getClientGMId().equals(clientDTO.getClientId())) {
                        employeesOfSelectedClientDTOWithContract.add(personService.findPersonById(initialContractDTO.getContractJsonData().getWorkerId()));
                    }
                }
            }
        }else if(consultationContractSelector.getAllContract().isSelected()){
            List<InitialContractDTO> initialContractDTOList = contractService.findAllInitialContract();
            for(InitialContractDTO initialContractDTO : initialContractDTOList) {
                if (initialContractDTO.getContractJsonData().getClientGMId().equals(clientDTO.getClientId())) {
                    employeesOfSelectedClientDTOWithContract.add(personService.findPersonById(initialContractDTO.getContractJsonData().getWorkerId()));
                }
            }
        }

        List<PersonDTO> employeesOfSelectedClientDTOWithContractWithTraceabilityWithOutDuplicates = new ArrayList<>();

        Map<Integer, PersonDTO> personDTOMap = new HashMap<>();

        for (PersonDTO personDTO : employeesOfSelectedClientDTOWithContract) {
            personDTOMap.put(personDTO.getIdpersona(), personDTO);
        }

        for (Map.Entry<Integer, PersonDTO> itemMap : personDTOMap.entrySet()) {
            employeesOfSelectedClientDTOWithContractWithTraceabilityWithOutDuplicates.add(itemMap.getValue());
        }

        Collator primaryCollator = Collator.getInstance(new Locale("es","ES"));
        primaryCollator.setStrength(Collator.PRIMARY);

        List<PersonDTO> sortedPersonDTOList = employeesOfSelectedClientDTOWithContractWithTraceabilityWithOutDuplicates
                .stream()
                .sorted(Comparator.comparing(PersonDTO::toString, primaryCollator)).collect(Collectors.toList());

        ObservableList<PersonDTO> sortedPersonDTOOLL = FXCollections.observableArrayList(sortedPersonDTOList);

        consultationContractSelector.getEmployeeSelector().setItems(sortedPersonDTOOLL);
        if(sortedPersonDTOOLL.size() == 1){
            consultationContractSelector.getEmployeeSelector().getSelectionModel().select(0);
        }
    }

    private void onEmployeeSelectorChange(SelectEmployerEmployeeEvent employerEmployeeEvent){

        consultationContractData.getIdentificationContractNumberINEM().clear();
        consultationContractData.getContractTypeDescription().clear();

        consultationContractSelector.getContractSelector().getSelectionModel().clearSelection();
        consultationContractSelector.getContractSelector().getItems().clear();

        consultationContractData.getConsultationContractDataTableDTOTable().getItems().clear();

        consultationContractAction.getSaveButton().setDisable(true);

        ClientDTO clientDTO = employerEmployeeEvent.getSelectedClientEmployer();
        PersonDTO personDTO = employerEmployeeEvent.getNewEmployeeSelected();
        List<Integer> contractsList = new ArrayList<>();

        ContractService contractService = ContractService.ContractServiceFactory.getInstance();
        ClientService clientService = ClientService.ClientServiceFactory.getInstance();

        List<InitialContractDTO> initialContractDTOList = contractService.findAllInitialContract();

        if(consultationContractSelector.getActiveClientsOnly().isSelected()){
            if(clientService.findClientById(clientDTO.getClientId()).isActiveClient()) {
                if (consultationContractSelector.getContractInForceOnly().isSelected()) {
                    // Active client with contract in force
                    for (InitialContractDTO initialContractDTO : initialContractDTOList) {
                        if (initialContractDTO.getContractJsonData().getClientGMId().equals(clientDTO.getClientId()) &&
                                initialContractDTO.getContractJsonData().getWorkerId().equals(personDTO.getIdpersona()) &&
                                initialContractDTO.getEndingDate() == null) {
                            contractsList.add(initialContractDTO.getContractNumber());
                        }
                    }

                } else if (consultationContractSelector.getAllContract().isSelected()) {
                    // Active client all contract
                    for (InitialContractDTO initialContractDTO : initialContractDTOList) {
                        if (initialContractDTO.getContractJsonData().getClientGMId().equals(clientDTO.getClientId()) &&
                                initialContractDTO.getContractJsonData().getWorkerId().equals(personDTO.getIdpersona())) {
                            contractsList.add(initialContractDTO.getContractNumber());
                        }
                    }
                }
            }
        } else if(!consultationContractSelector.getActiveClientsOnly().isSelected()){
            if(consultationContractSelector.getAllContract().isSelected()){
                for(InitialContractDTO initialContractDTO : initialContractDTOList){
                    if(initialContractDTO.getContractJsonData().getWorkerId().equals(personDTO.getIdpersona()) &&
                            initialContractDTO.getContractJsonData().getClientGMId().equals(clientDTO.getClientId())){
                        contractsList.add(initialContractDTO.getContractNumber());
                    }
                }
            }
        }

        List<Integer> contractsListWithOutDuplicates = new ArrayList<>();

        Map<Integer, String> contractListMap = new HashMap<>();

        for (Integer contractNumber : contractsList) {
            contractListMap.put(contractNumber, contractNumber.toString());
        }

        for (Map.Entry<Integer, String> itemMap : contractListMap.entrySet()) {
            contractsListWithOutDuplicates.add(Integer.parseInt(itemMap.getValue()));
        }

        Collections.sort(contractsListWithOutDuplicates);

        ObservableList<Integer> contractsOL = FXCollections.observableArrayList(contractsListWithOutDuplicates);
        consultationContractSelector.getContractSelector().setItems(contractsOL);
        if(contractsOL.size() == 1){
            consultationContractSelector.getContractSelector().getSelectionModel().select(0);
        }
    }

    private void onContractSelectorChange(ContractSelectedEvent event) {

        consultationContractData.getConsultationContractDataTableDTOTable().getItems().clear();
        consultationContractData.getIdentificationContractNumberINEM().clear();

        ContractService contractService = ContractService.ContractServiceFactory.getInstance();
        List<ContractNewVersionDTO> contractNewVersionDTOList = contractService.findHistoryOfContractByContractNumber(event.getSelectedContractNumber());

        Collator primaryCollator = Collator.getInstance(new Locale("es", "ES"));
        primaryCollator.setStrength(Collator.PRIMARY);

        List<ConsultationContractDataTableDTO> consultationContractDataTableDTOList = new ArrayList<>();
        TypesContractVariationsService typesContractVariationsService = TypesContractVariationsService.TypesContractVariationServiceFactory.getInstance();
        for (ContractNewVersionDTO contractNewVersionDTO : contractNewVersionDTOList) {
            TypesContractVariationsDTO typesContractVariationsDTO = typesContractVariationsService.findTypeContractVariationByVariationId(contractNewVersionDTO.getVariationType());
            ConsultationContractDataTableDTO consultationContractDataTableDTO = ConsultationContractDataTableDTO.create()
                    .withVariationTypeCode(typesContractVariationsDTO.getId_variation())
                    .withVariationTypeDescription(typesContractVariationsDTO.getVariation_description())
                    .withStartDate(contractNewVersionDTO.getStartDate())
                    .withExpectedEndDate(contractNewVersionDTO.getExpectedEndDate())
                    .withModificationDate(contractNewVersionDTO.getModificationDate())
                    .withEndingDate(contractNewVersionDTO.getEndingDate())
                    .withHoursWorkWeek(Utilities.converterTimeStringToDuration(contractNewVersionDTO.getContractJsonData().getWeeklyWorkHours()))
                    .build();

            consultationContractDataTableDTOList.add(consultationContractDataTableDTO);
        }

        List<ConsultationContractDataTableDTO> sortedConsultationContractDataTableDTOList = consultationContractDataTableDTOList
                .stream()
                .sorted(Comparator.comparing(ConsultationContractDataTableDTO::getEnglishStartDateToString, primaryCollator)).collect(Collectors.toList());

        ObservableList<ConsultationContractDataTableDTO> sortedConsultationContractDataTableDTOOL = FXCollections.observableArrayList(sortedConsultationContractDataTableDTOList);
        consultationContractData.refreshContractDocumentationControlTable(sortedConsultationContractDataTableDTOOL);
        consultationContractData.getConsultationContractDataTableDTOTable().getSelectionModel().select(0);
    }

    private void onContractDataTableSelectedRowChange(ContractDataTableSelectedRowEvent event){
        ContractService contractService = ContractService.ContractServiceFactory.getInstance();
        ContractTypeService contractTypeService = ContractTypeService.ContractTypeServiceFactory.getInstance();

        List<ContractNewVersionDTO> contractNewVersionDTOList = contractService.findHistoryOfContractByContractNumber(consultationContractSelector.getContractSelector().getValue());
        for(ContractNewVersionDTO contractNewVersionDTO : contractNewVersionDTOList){
            if(contractNewVersionDTO.getVariationType().equals(event.getVariationTypeCode()) &&
                    contractNewVersionDTO.getStartDate().compareTo(event.getStartDate()) == 0){
                consultationContractData.getIdentificationContractNumberINEM().setText(contractNewVersionDTO.getContractJsonData().getIdentificationContractNumberINEM());
                String contractTypeText = contractTypeService.findContractTypeByContractTypeCode(contractNewVersionDTO.getContractJsonData().getContractType()).getColloquial();
                contractTypeText = contractTypeText + " [" + contractTypeService.findContractTypeByContractTypeCode(contractNewVersionDTO.getContractJsonData().getContractType()).getContractDescription() +"]";
                consultationContractData.getContractTypeDescription().setText(contractTypeText);
            }
        }
    }

    private void onExitButton(MouseEvent event){
        logger.info("Contract documentation control: exiting program.");

        Stage stage = (Stage) consultationContractHeader.getScene().getWindow();
        stage.close();
    }

    private List<ClientDTO> retrieveClientWithContractWithTraceabilityWithOutDuplicates(){
        List<ClientDTO> clientWithContractWithTraceability = new ArrayList<>();
        List<ClientDTO> clientWithContractWithTraceabilityWithOutDuplicates = new ArrayList<>();

        TraceabilityService traceabilityService = TraceabilityService.TraceabilityServiceFactory.getInstance();
        List<TraceabilityContractDocumentationDTO> traceabilityContractDocumentationDTOList = traceabilityService.findAllTraceabilityContractData();

        ContractService contractService = ContractService.ContractServiceFactory.getInstance();
        ClientService clientService = ClientService.ClientServiceFactory.getInstance();

        for(TraceabilityContractDocumentationDTO traceabilityContractDocumentationDTO : traceabilityContractDocumentationDTOList){
            InitialContractDTO initialContractDTO = contractService.findInitialContractByContractNumber(traceabilityContractDocumentationDTO.getContractNumber());
            ClientDTO clientDTO = clientService.findClientById(initialContractDTO.getContractJsonData().getClientGMId());

            clientWithContractWithTraceability.add(clientDTO);
        }

        Map<Integer, ClientDTO> clientDTOMap = new HashMap<>();

        for (ClientDTO clientDTO : clientWithContractWithTraceability) {
            clientDTOMap.put(clientDTO.getClientId(), clientDTO);
        }

        for (Map.Entry<Integer, ClientDTO> itemMap : clientDTOMap.entrySet()) {
            clientWithContractWithTraceabilityWithOutDuplicates.add(itemMap.getValue());
        }

        return clientWithContractWithTraceabilityWithOutDuplicates;
    }
}
