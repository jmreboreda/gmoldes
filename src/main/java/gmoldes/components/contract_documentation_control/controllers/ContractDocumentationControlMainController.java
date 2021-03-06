package gmoldes.components.contract_documentation_control.controllers;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract_documentation_control.ContractDocumentationControlConstants;
import gmoldes.components.contract_documentation_control.components.ContractDocumentationControlAction;
import gmoldes.components.contract_documentation_control.components.ContractDocumentationControlData;
import gmoldes.components.contract_documentation_control.components.ContractDocumentationControlHeader;
import gmoldes.components.contract_documentation_control.components.ContractDocumentationControlSelector;
import gmoldes.components.contract_documentation_control.events.*;
import gmoldes.domain.client.ClientService;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.ContractService;
import gmoldes.domain.types_contract_variations.TypesContractVariationsService;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract.dto.InitialContractDTO;
import gmoldes.domain.contract_documentation_control.ContractDocumentationControlDataDTO;
import gmoldes.domain.person.PersonService;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.traceability_contract_documentation.TraceabilityService;
import gmoldes.domain.traceability_contract_documentation.controllers.TraceabilityContractDocumentationController;
import gmoldes.domain.traceability_contract_documentation.dto.TraceabilityContractDocumentationDTO;
import gmoldes.utilities.Message;
import gmoldes.utilities.Parameters;
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

    private Boolean contractNumberINEMChanged = false;
    private Boolean traceabilityCellsEdited = false;


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
        contractDocumentationControlSelector.setOnContractSelectorChange(this::onContractSelectorChange);
        contractDocumentationControlSelector.setOnContractVariationSelectorChange(this::onContractVariationSelectorChange);

        contractDocumentationControlSelector.getClientSelector().setStyle(ContractDocumentationControlConstants.BLUE_COLOR);
        contractDocumentationControlSelector.getEmployeeSelector().setStyle(ContractDocumentationControlConstants.BLUE_COLOR);
        contractDocumentationControlSelector.getContractSelector().setStyle(ContractDocumentationControlConstants.BLUE_COLOR);

        contractDocumentationControlData.setOnContractNumberINEMChanged(this::onContractNumberINEMChanged);
        contractDocumentationControlData.setOnUpdateTableObservableList(this::onCellTableOrINEMChange);

        contractDocumentationControlAction.setOnOkButton(this::onOkButton);
        contractDocumentationControlAction.setOnSaveButton(this::onSaveButton);
        contractDocumentationControlAction.setOnExitButton(this::onExitButton);
    }

    private void onChangeContractsInForceOnly(MouseEvent event){

        loadClientSelector();

        contractDocumentationControlSelector.getEmployeeSelector().getSelectionModel().clearSelection();
        contractDocumentationControlSelector.getEmployeeSelector().getItems().clear();

        contractDocumentationControlSelector.getContractSelector().getSelectionModel().clearSelection();
        contractDocumentationControlSelector.getContractSelector().getItems().clear();

        contractDocumentationControlSelector.getContractSelectedVariations().getSelectionModel().clearSelection();
        contractDocumentationControlSelector.getContractSelectedVariations().getItems().clear();

        contractDocumentationControlData.getContractDocumentControlTable().getItems().clear();
    }

    public Boolean getContractNumberINEMChanged() {
        return contractNumberINEMChanged;
    }

    public void setContractNumberINEMChanged(Boolean contractNumberINEMChanged) {
        this.contractNumberINEMChanged = contractNumberINEMChanged;
    }

    public Boolean getTraceabilityCellsEdited() {
        return traceabilityCellsEdited;
    }

    public void setTraceabilityCellsEdited(Boolean traceabilityCellsEdited) {
        this.traceabilityCellsEdited = traceabilityCellsEdited;
    }

    private void loadClientSelector(){
        List<ClientDTO> clientDTOToClientSelectorList = new ArrayList<>();
        List<ClientDTO> clientWithContractWithTraceability;

        ClientService clientService = ClientService.ClientServiceFactory.getInstance();

        // Clients with contracts with traceability
        clientWithContractWithTraceability = retrieveClientWithContractWithTraceabilityWithOutDuplicates();

        // Clients with contracts in force with traceability
        if(contractDocumentationControlSelector.getContractsInForceOnly().isSelected()){
            List<ClientDTO> clientDTOWithContractsInForceAtDate = clientService.findAllClientWithContractInForceAtDate(LocalDate.now());

            for(ClientDTO clientWithContractTraceabilityDTO : clientWithContractWithTraceability) {
                for (ClientDTO clientDTOWithContractInForceAtDate : clientDTOWithContractsInForceAtDate) {
                    if(clientWithContractTraceabilityDTO.getClientId().equals(clientDTOWithContractInForceAtDate.getClientId())){
                        clientDTOToClientSelectorList.add(clientDTOWithContractInForceAtDate);
                    }
                }
            }
        }else{
            // Clients with contracts (in force or not) with traceability
            List<ClientDTO> clientDTOWithContracts = clientService.findAllActiveClientWithContractHistory();
            for(ClientDTO clientWithContractTraceabilityDTO : clientWithContractWithTraceability) {
                for (ClientDTO clientDTOWithContractInForceAtDate : clientDTOWithContracts) {
                    if(clientWithContractTraceabilityDTO.getClientId().equals(clientDTOWithContractInForceAtDate.getClientId())){
                        clientDTOToClientSelectorList.add(clientDTOWithContractInForceAtDate);
                    }
                }
            }
        }

        Collator primaryCollator = Collator.getInstance(new Locale("es","ES"));
        primaryCollator.setStrength(Collator.PRIMARY);

        List<ClientDTO> sortedClientDTOList = clientDTOToClientSelectorList
                .stream()
                .sorted(Comparator.comparing(ClientDTO::toString, primaryCollator)).collect(Collectors.toList());

        ObservableList<ClientDTO> clientDTOOL = FXCollections.observableArrayList(sortedClientDTOList);
        contractDocumentationControlSelector.loadClientSelector(clientDTOOL);
    }

    private void onClientSelectorChange(SelectClientEmployerEvent employerEvent){

        contractDocumentationControlSelector.getContractSelector().getSelectionModel().clearSelection();
        contractDocumentationControlSelector.getContractSelector().getItems().clear();

        contractDocumentationControlSelector.getContractSelectedVariations().getSelectionModel().clearSelection();
        contractDocumentationControlSelector.getContractSelectedVariations().getItems().clear();

        contractDocumentationControlData.getContractDocumentControlTable().getItems().clear();

        contractDocumentationControlData.getIdentificationContractNumberINEM().clear();

        contractDocumentationControlAction.getSaveButton().setDisable(true);

        ClientDTO clientDTO = employerEvent.getSelectedClientEmployer();

        List<PersonDTO> employeesOfSelectedClientDTOWithContractWithTraceability = new ArrayList<>();

        TraceabilityService traceabilityService = TraceabilityService.TraceabilityServiceFactory.getInstance();
        ContractService contractService = ContractService.ContractServiceFactory.getInstance();
        PersonService personService = PersonService.PersonServiceFactory.getInstance();

        List<TraceabilityContractDocumentationDTO> traceabilityContractDocumentationDTOList = traceabilityService.findAllTraceabilityContractData();
        for(TraceabilityContractDocumentationDTO traceabilityContractDocumentationDTO : traceabilityContractDocumentationDTOList){
            InitialContractDTO initialContractDTO = contractService.findInitialContractByContractNumber(traceabilityContractDocumentationDTO.getContractNumber());
            if(initialContractDTO.getContractJsonData().getClientGMId().equals(clientDTO.getClientId())){
                PersonDTO personDTO = personService.findPersonById(initialContractDTO.getContractJsonData().getWorkerId());
                employeesOfSelectedClientDTOWithContractWithTraceability.add(personDTO);
            }
        }

        List<PersonDTO> employeesOfSelectedClientDTOWithContractWithTraceabilityWithOutDuplicates = new ArrayList<>();

        Map<Integer, PersonDTO> personDTOMap = new HashMap<>();

        for (PersonDTO personDTO : employeesOfSelectedClientDTOWithContractWithTraceability) {
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

        contractDocumentationControlSelector.getEmployeeSelector().setItems(sortedPersonDTOOLL);
        if(sortedPersonDTOOLL.size() == 1){
            contractDocumentationControlSelector.getEmployeeSelector().getSelectionModel().select(0);
        }
    }

    private void onEmployeeSelectorChange(SelectEmployerEmployeeEvent employerEmployeeEvent){

        contractDocumentationControlSelector.getContractSelector().getSelectionModel().clearSelection();
        contractDocumentationControlSelector.getContractSelector().getItems().clear();

        contractDocumentationControlSelector.getContractSelectedVariations().getSelectionModel().clearSelection();
        contractDocumentationControlSelector.getContractSelectedVariations().getItems().clear();

        contractDocumentationControlData.getIdentificationContractNumberINEM().clear();

        contractDocumentationControlAction.getSaveButton().setDisable(true);

        ClientDTO clientDTO = employerEmployeeEvent.getSelectedClientEmployer();
        PersonDTO personDTO = employerEmployeeEvent.getNewEmployeeSelected();
        List<Integer> contractsList = new ArrayList<>();

        TraceabilityService traceabilityService = TraceabilityService.TraceabilityServiceFactory.getInstance();
        List<TraceabilityContractDocumentationDTO> traceabilityContractDocumentationDTOList = traceabilityService.findAllTraceabilityContractData();

        ContractService contractService = ContractService.ContractServiceFactory.getInstance();

        for(TraceabilityContractDocumentationDTO traceabilityContractDocumentationDTO : traceabilityContractDocumentationDTOList){
            InitialContractDTO initialContractDTO = contractService.findInitialContractByContractNumber(traceabilityContractDocumentationDTO.getContractNumber());
            if(initialContractDTO.getContractJsonData().getClientGMId().equals(clientDTO.getClientId()) &&
            initialContractDTO.getContractJsonData().getWorkerId().equals(personDTO.getIdpersona())){
                contractsList.add(traceabilityContractDocumentationDTO.getContractNumber());
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
        contractDocumentationControlSelector.getContractSelector().setItems(contractsOL);
        if(contractsOL.size() == 1){
            contractDocumentationControlSelector.getContractSelector().getSelectionModel().select(0);
        }
    }

    private void onContractSelectorChange(ContractSelectedEvent event){
        contractDocumentationControlData.getContractDocumentControlTable().getItems().clear();

        contractDocumentationControlData.getIdentificationContractNumberINEM().clear();


        contractDocumentationControlAction.getSaveButton().setDisable(true);


        ContractService contractService = ContractService.ContractServiceFactory.getInstance();
        List<ContractNewVersionDTO> contractNewVersionDTOList = contractService.findHistoryOfContractByContractNumber(event.getSelectedContractNumber());

        Collator primaryCollator = Collator.getInstance(new Locale("es","ES"));
        primaryCollator.setStrength(Collator.PRIMARY);

        List<ContractNewVersionDTO> sortedContractVariationList = contractNewVersionDTOList
                .stream()
                .sorted(Comparator.comparing(ContractNewVersionDTO::getStartDateToString, primaryCollator)).collect(Collectors.toList());

        ObservableList<ContractNewVersionDTO> contractNewVersionDTOOL = FXCollections.observableArrayList(sortedContractVariationList);
        contractDocumentationControlSelector.getContractSelectedVariations().setItems(contractNewVersionDTOOL);
        if(contractNewVersionDTOOL.size() == 1){
            contractDocumentationControlSelector.getContractSelectedVariations().getSelectionModel().select(0);
            String identificationContractNumberINEM = contractDocumentationControlSelector.getContractSelectedVariations().getValue().getContractJsonData().getIdentificationContractNumberINEM();
            contractDocumentationControlData.getIdentificationContractNumberINEM().setText(identificationContractNumberINEM);
        }
    }

    private void onContractVariationSelectorChange(ContractVariationSelectedEvent event){
        contractDocumentationControlData.getContractDocumentControlTable().getItems().clear();

        contractDocumentationControlAction.getOkButton().setDisable(true);
        contractDocumentationControlAction.getSaveButton().setDisable(true);

        String identificationContractNumberINEM = contractDocumentationControlSelector.getContractSelectedVariations().getSelectionModel().getSelectedItem().getContractJsonData().getIdentificationContractNumberINEM();
        contractDocumentationControlData.getIdentificationContractNumberINEM().setText(identificationContractNumberINEM);
        contractDocumentationControlData.previousIdentificationNumberINEMFromDatabase = identificationContractNumberINEM;

        TraceabilityService traceabilityService = TraceabilityService.TraceabilityServiceFactory.getInstance();
        TypesContractVariationsService typesContractVariationsService = TypesContractVariationsService.TypesContractVariationServiceFactory.getInstance();
        List<TraceabilityContractDocumentationDTO> traceabilityContractDocumentationDTOList = traceabilityService.findAllTraceabilityContractData();
        for(TraceabilityContractDocumentationDTO traceabilityContractDocumentationDTO : traceabilityContractDocumentationDTOList){
            if(traceabilityContractDocumentationDTO.getContractNumber().equals(event.getContractNumber()) &&
                    traceabilityContractDocumentationDTO.getVariationType().equals(event.getVariationType()) &&
                            traceabilityContractDocumentationDTO.getStartDate().equals(event.getStartDate())){

                // IDC variation data filter
                if(typesContractVariationsService.findTypesContractVariationsById(traceabilityContractDocumentationDTO.getVariationType()).getIdcRequired()) {
                    contractDocumentationControlData.getContractDocumentControlTable().getItems().add(new ContractDocumentationControlDataDTO("Informe de datos para la cotización (IDC)", traceabilityContractDocumentationDTO.getIDCReceptionDate(), null));
                }
                else{
                    contractDocumentationControlData.getContractDocumentControlTable().getItems().add(new ContractDocumentationControlDataDTO(null,null, null));
                }

                // Delivery documentation without filter
                contractDocumentationControlData.getContractDocumentControlTable().getItems().add(new ContractDocumentationControlDataDTO("Envío de la documentación al cliente para firma", null, traceabilityContractDocumentationDTO.getDateDeliveryContractDocumentationToClient()));

                // Contract end notice data filter
                if(typesContractVariationsService.findTypesContractVariationsById(traceabilityContractDocumentationDTO.getVariationType()).getInitial() ||
                        typesContractVariationsService.findTypesContractVariationsById(traceabilityContractDocumentationDTO.getVariationType()).getExtension()) {
                    contractDocumentationControlData.getContractDocumentControlTable().getItems().add(new ContractDocumentationControlDataDTO("Carta de preaviso de fin de contrato", traceabilityContractDocumentationDTO.getContractEndNoticeReceptionDate(), null));
                }else{
                    contractDocumentationControlData.getContractDocumentControlTable().getItems().add(new ContractDocumentationControlDataDTO(null,null, null));
                }
            }
        }
    }

    private void onContractNumberINEMChanged(ContractNumberINEMChangedEvent event){
        if(event.getTextFieldContractNumberINEMChanged()){
            contractDocumentationControlAction.getSaveButton().setDisable(true);
            contractDocumentationControlAction.getOkButton().setDisable(false);
            this.setContractNumberINEMChanged(true);
        }else{
            contractDocumentationControlAction.getSaveButton().setDisable(false);
            contractDocumentationControlAction.getOkButton().setDisable(true);
            this.setContractNumberINEMChanged(false);
        }
    }

    private void onCellTableOrINEMChange(CellTableChangedEvent event){
        if(event.getCellsEdited()){
            contractDocumentationControlAction.getSaveButton().setDisable(true);
            contractDocumentationControlAction.getOkButton().setDisable(false);
            this.setTraceabilityCellsEdited(true);
        }else{
            contractDocumentationControlAction.getSaveButton().setDisable(false);
            contractDocumentationControlAction.getOkButton().setDisable(true);
            this.setTraceabilityCellsEdited(false);
        }
    }

    private void onOkButton(MouseEvent event){

        for(int i = 0; i < contractDocumentationControlData.getContractDocumentControlTable().getItems().size(); i++){
            if(contractDocumentationControlData.getContractDocumentControlTable().getItems().get(i).getDocumentType() == null){
                contractDocumentationControlData.getContractDocumentControlTable().getItems().get(i).setReceptionDate(null);
                contractDocumentationControlData.getContractDocumentControlTable().getItems().get(i).setDeliveryDate(null);
                contractDocumentationControlData.getContractDocumentControlTable().refresh();
            }

            contractDocumentationControlAction.getSaveButton().setDisable(false);
        }

    }

    private void onSaveButton(MouseEvent event){
        ContractService contractService = ContractService.ContractServiceFactory.getInstance();
        Integer contractNumber;
        Integer contractId;
        ObservableList<ContractDocumentationControlDataDTO> contractDocumentationControlDataDTOOL = contractDocumentationControlData.getContractDocumentControlTable().getItems();

        ContractNewVersionDTO contractNewVersionDTO = contractDocumentationControlSelector.getContractSelectedVariations().getSelectionModel().getSelectedItem();

        // Update identification contract number INEM
        if(this.getContractNumberINEMChanged()) {
            String identificationContractNumberINEM = contractDocumentationControlData.getIdentificationContractNumberINEM().getText();
            contractNewVersionDTO.getContractJsonData().setIdentificationContractNumberINEM(identificationContractNumberINEM);
            if (contractNewVersionDTO.getVariationType() < 200) {
                contractNumber = contractService.updateInitialContract(contractNewVersionDTO);
                if (contractNumber != null) {
                    Message.informationMessage((Stage) contractDocumentationControlHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractDocumentationControlConstants.INEM_NUMBER_MODIFICATION_SAVED_OK);
                    logger.info("El número de contrato INEM del contrato inicial se ha actualizado correctamente.");

                } else {
                    Message.errorMessage((Stage) contractDocumentationControlHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractDocumentationControlConstants.INEM_NUMBER_MODIFICATION_NOT_SAVED_OK);
                    logger.info("Problemas al actualizar el número de contrato INEM del contrato inicial.");
                }
            } else {
                contractId = contractService.updateContractVariation(contractNewVersionDTO);
                if (contractId != null) {
                    Message.informationMessage((Stage) contractDocumentationControlHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractDocumentationControlConstants.INEM_NUMBER_MODIFICATION_SAVED_OK);
                    logger.info("El número de contrato INEM de la variación del contrato se ha actualizado correctamente.");

                } else {
                    Message.errorMessage((Stage) contractDocumentationControlHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractDocumentationControlConstants.INEM_NUMBER_MODIFICATION_NOT_SAVED_OK);
                    logger.info("Problemas al actualizar el número de contrato INEM de la variación del contrato.");
                }
            }

            setContractNumberINEMChanged(false);
        }

        // Update traceability contract data
        if(getTraceabilityCellsEdited()) {
            TraceabilityService traceabilityService = TraceabilityService.TraceabilityServiceFactory.getInstance();
            List<TraceabilityContractDocumentationDTO> traceabilityContractDocumentationDTOList = traceabilityService.findAllTraceabilityContractData();
            for (TraceabilityContractDocumentationDTO traceabilityContractDocumentationDTO : traceabilityContractDocumentationDTOList) {
                if (traceabilityContractDocumentationDTO.getContractNumber().equals(contractNewVersionDTO.getContractNumber()) &&
                        traceabilityContractDocumentationDTO.getVariationType().equals(contractNewVersionDTO.getVariationType()) &&
                        traceabilityContractDocumentationDTO.getStartDate().equals(contractNewVersionDTO.getStartDate())) {

                    TraceabilityContractDocumentationDTO traceabilityContractDocumentationDTOToUpdate = TraceabilityContractDocumentationDTO.create()
                            .withId(traceabilityContractDocumentationDTO.getId())
                            .withContractNumber(traceabilityContractDocumentationDTO.getContractNumber())
                            .withVariationType(traceabilityContractDocumentationDTO.getVariationType())
                            .withStartDate(traceabilityContractDocumentationDTO.getStartDate())
                            .withExpectedEndDate(traceabilityContractDocumentationDTO.getExpectedEndDate())
                            .withIDCReceptionDate(contractDocumentationControlDataDTOOL.get(0).getReceptionDate())
                            .withDateDeliveryContractDocumentationToClient(contractDocumentationControlDataDTOOL.get(1).getDeliveryDate())
                            .withContractEndNoticeReceptionDate(contractDocumentationControlDataDTOOL.get(2).getReceptionDate())
                            .build();

                    TraceabilityContractDocumentationController traceabilityContractDocumentationController = new TraceabilityContractDocumentationController();
                    if (traceabilityContractDocumentationController.updateTraceabilityRecord(traceabilityContractDocumentationDTOToUpdate) != null) {
                        logger.info("Trazabilidad actualizada correctamente.");
                        Message.informationMessage((Stage) contractDocumentationControlHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractDocumentationControlConstants.TRACEABILITY_MODIFICATION_SAVED_OK);
                    } else {
                        Message.errorMessage((Stage) contractDocumentationControlHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractDocumentationControlConstants.TRACEABILITY_MODIFICATION_SAVED_NOT_OK);
                        logger.info("Problemas en la actualización de la trazabilidad.");
                    }
                }
            }

            setTraceabilityCellsEdited(false);
        }

        contractDocumentationControlAction.getSaveButton().setDisable(true);
    }

    private void onExitButton(MouseEvent event){
        logger.info("Contract documentation control: exiting program.");


        Stage stage = (Stage) contractDocumentationControlHeader.getScene().getWindow();
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
