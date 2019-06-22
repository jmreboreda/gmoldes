package gmoldes.components.contract.consultation_contract.controllers;

import gmoldes.ApplicationConstants;
import gmoldes.components.ViewLoader;
import gmoldes.components.contract.consultation_contract.components.ConsultationContractAction;
import gmoldes.components.contract.consultation_contract.components.ConsultationContractData;
import gmoldes.components.contract.consultation_contract.components.ConsultationContractHeader;
import gmoldes.components.contract.consultation_contract.components.ConsultationContractSelector;
import gmoldes.components.contract_documentation_control.ContractDocumentationControlConstants;
import gmoldes.components.contract_documentation_control.events.ContractSelectedEvent;
import gmoldes.components.contract_documentation_control.events.SelectClientEmployerEvent;
import gmoldes.components.contract_documentation_control.events.SelectEmployerEmployeeEvent;
import gmoldes.domain.client.ClientService;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.consultation_contract.dto.ConsultationContractDataTableDTO;
import gmoldes.domain.contract.ContractService;
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
import java.time.format.DateTimeFormatter;
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

//        contractDocumentationControlSelector.setOnChangeContractsInForceOnly(this::onChangeContractsInForceOnly);
//
        consultationContractSelector.setOnClientSelectorChange(this::onClientSelectorChange);
        consultationContractSelector.setOnEmployeeSelectorChange(this::onEmployeeSelectorChange);
        consultationContractSelector.setOnContractSelectorChange(this::onContractSelectorChange);

        consultationContractSelector.getClientSelector().setStyle(ContractDocumentationControlConstants.BLUE_COLOR);
        consultationContractSelector.getEmployeeSelector().setStyle(ContractDocumentationControlConstants.BLUE_COLOR);
        consultationContractSelector.getContractSelector().setStyle(ContractDocumentationControlConstants.BLUE_COLOR);
//
//        consultationContractData.setOnUpdateTableObservableList(this::onCellTableOrINEMChange);

        consultationContractAction.setOnOkButton(this::onOkButton);
        consultationContractAction.setOnSaveButton(this::onSaveButton);
        consultationContractAction.setOnExitButton(this::onExitButton);
    }

    private void onChangeContractsInForceOnly(MouseEvent event){

//        loadClientSelector();
//
//        contractDocumentationControlSelector.getEmployeeSelector().getSelectionModel().clearSelection();
//        contractDocumentationControlSelector.getEmployeeSelector().getItems().clear();
//
//        contractDocumentationControlSelector.getContractSelector().getSelectionModel().clearSelection();
//        contractDocumentationControlSelector.getContractSelector().getItems().clear();
//
//        contractDocumentationControlData.getContractDocumentControlTable().getItems().clear();
    }

    private void loadClientSelector(){
        List<ClientDTO> clientDTOToClientSelectorList = new ArrayList<>();
        List<ClientDTO> clientWithContractWithTraceability;

        ClientService clientService = ClientService.ClientServiceFactory.getInstance();

        // Clients with contracts with traceability
        clientWithContractWithTraceability = retrieveClientWithContractWithTraceabilityWithOutDuplicates();

        // Clients with contracts in force with traceability
//        if(contractDocumentationControlSelector.getContractsInForceOnly().isSelected()){
            List<ClientDTO> clientDTOWithContractsInForceAtDate = clientService.findAllClientWithContractInForceAtDate(LocalDate.now());

            for(ClientDTO clientWithContractTraceabilityDTO : clientWithContractWithTraceability) {
                for (ClientDTO clientDTOWithContractInForceAtDate : clientDTOWithContractsInForceAtDate) {
                    if(clientWithContractTraceabilityDTO.getClientId().equals(clientDTOWithContractInForceAtDate.getClientId())){
                        clientDTOToClientSelectorList.add(clientDTOWithContractInForceAtDate);
                    }
                }
            }
//        }else{
//            // Clients with contracts (in force or not) with traceability
//            List<ClientDTO> clientDTOWithContracts = clientService.findAllActiveClientWithContractHistory();
//            for(ClientDTO clientWithContractTraceabilityDTO : clientWithContractWithTraceability) {
//                for (ClientDTO clientDTOWithContractInForceAtDate : clientDTOWithContracts) {
//                    if(clientWithContractTraceabilityDTO.getClientId().equals(clientDTOWithContractInForceAtDate.getClientId())){
//                        clientDTOToClientSelectorList.add(clientDTOWithContractInForceAtDate);
//                    }
//                }
//            }
//        }

        Collator primaryCollator = Collator.getInstance(new Locale("es","ES"));
        primaryCollator.setStrength(Collator.PRIMARY);

        List<ClientDTO> sortedClientDTOList = clientDTOToClientSelectorList
                .stream()
                .sorted(Comparator.comparing(ClientDTO::toString, primaryCollator)).collect(Collectors.toList());

        ObservableList<ClientDTO> clientDTOOL = FXCollections.observableArrayList(sortedClientDTOList);
        consultationContractSelector.loadClientSelector(clientDTOOL);

    }

    private void onClientSelectorChange(SelectClientEmployerEvent employerEvent){

        consultationContractSelector.getContractSelector().getSelectionModel().clearSelection();
        consultationContractSelector.getContractSelector().getItems().clear();

        consultationContractData.getConsultationContractDataTableDTOTable().getItems().clear();

        consultationContractData.getIdentificationContractNumberINEM().clear();

        consultationContractAction.getSaveButton().setDisable(true);


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

        consultationContractSelector.getEmployeeSelector().setItems(sortedPersonDTOOLL);
        if(sortedPersonDTOOLL.size() == 1){
            consultationContractSelector.getEmployeeSelector().getSelectionModel().select(0);
        }
    }

    private void onEmployeeSelectorChange(SelectEmployerEmployeeEvent employerEmployeeEvent){

        consultationContractSelector.getContractSelector().getSelectionModel().clearSelection();
        consultationContractSelector.getContractSelector().getItems().clear();

        consultationContractAction.getSaveButton().setDisable(true);


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
        consultationContractSelector.getContractSelector().setItems(contractsOL);
        if(contractsOL.size() == 1){
            consultationContractSelector.getContractSelector().getSelectionModel().select(0);
        }
    }

    private void onContractSelectorChange(ContractSelectedEvent event) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_TIME_FORMAT);


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

            System.out.println(typesContractVariationsDTO.getVariation_description());
//            consultationContractData.getConsultationContractDataTableDTOTable().getItems()
//                    .add(new ConsultationContractDataTableDTO(typesContractVariationsDTO.getId_variation(),
//                            typesContractVariationsDTO.getVariation_description(),
//                            contractNewVersionDTO.getStartDate(),
//                            contractNewVersionDTO.getExpectedEndDate(),
//                            contractNewVersionDTO.getModificationDate(),
//                            contractNewVersionDTO.getEndingDate(),
//                            contractNewVersionDTO.getContractJsonData().getWeeklyWorkHours()));
        }

        List<ConsultationContractDataTableDTO> sortedConsultationContractDataTableDTOList = consultationContractDataTableDTOList
                .stream()
                .sorted(Comparator.comparing(ConsultationContractDataTableDTO::getEnglishStartDateToString, primaryCollator)).collect(Collectors.toList());

        ObservableList<ConsultationContractDataTableDTO> sortedConsultationContractDataTableDTOOL = FXCollections.observableArrayList(sortedConsultationContractDataTableDTOList);
        consultationContractData.refreshContractDocumentationControlTable(sortedConsultationContractDataTableDTOOL);
        consultationContractData.getConsultationContractDataTableDTOTable().getSelectionModel().select(0);
    }


//        consultationContractData.getConsultationContractDataTableDTOTable().setItems(sortedConsultationContractDataTableDTOOL);
//        if(contractNewVersionDTOOL.size() == 1){
//            contractDocumentationControlSelector.getContractSelectedVariations().getSelectionModel().select(0);
//            String identificationContractNumberINEM = contractDocumentationControlSelector.getContractSelectedVariations().getValue().getContractJsonData().getIdentificationContractNumberINEM();
//            contractDocumentationControlData.getIdentificationContractNumberINEM().setText(identificationContractNumberINEM);
//        }


//    private void onContractVariationSelectorChange(ContractVariationSelectedEvent event){
//        consultationContractData.getContractDocumentControlTable().getItems().clear();
//
//        consultationContractAction.getOkButton().setDisable(true);
//        consultationContractAction.getSaveButton().setDisable(true);
//
////        String identificationContractNumberINEM = contractDocumentationControlSelector.getContractSelectedVariations().getSelectionModel().getSelectedItem().getContractJsonData().getIdentificationContractNumberINEM();
////        contractDocumentationControlData.getIdentificationContractNumberINEM().setText(identificationContractNumberINEM);
////        contractDocumentationControlData.previousIdentificationNumberINEMFromDatabase = identificationContractNumberINEM;
//
//        TraceabilityService traceabilityService = TraceabilityService.TraceabilityServiceFactory.getInstance();
//        List<TraceabilityContractDocumentationDTO> traceabilityContractDocumentationDTOList = traceabilityService.findAllTraceabilityContractData();
//        for(TraceabilityContractDocumentationDTO traceabilityContractDocumentationDTO : traceabilityContractDocumentationDTOList){
//            if(traceabilityContractDocumentationDTO.getContractNumber().equals(event.getContractNumber()) &&
//                    traceabilityContractDocumentationDTO.getVariationType().equals(event.getVariationType()) &&
//                            traceabilityContractDocumentationDTO.getStartDate().equals(event.getStartDate())){
//                consultationContractData.getContractDocumentControlTable().getItems().add(new ContractDocumentationControlDataDTO("Informe de datos para la cotización (IDC)", traceabilityContractDocumentationDTO.getIDCReceptionDate(), null));
//                consultationContractData.getContractDocumentControlTable().getItems().add(new ContractDocumentationControlDataDTO("Envío de la documentación al cliente para firma", null, traceabilityContractDocumentationDTO.getDateDeliveryContractDocumentationToClient()));
//                consultationContractData.getContractDocumentControlTable().getItems().add(new ContractDocumentationControlDataDTO("Carta de preaviso de fin de contrato", traceabilityContractDocumentationDTO.getContractEndNoticeReceptionDate(), null));
//
////                List<ContractDocumentationControlDataDTO> itemList = new ArrayList<>();
////
////                itemList.add(new ContractDocumentationControlDataDTO("Informe de datos para la cotización (IDC)", traceabilityContractDocumentationDTO.getIDCReceptionDate(), null));
////                itemList.add(new ContractDocumentationControlDataDTO("Envío de la documentación al cliente para firma", null, traceabilityContractDocumentationDTO.getDateDeliveryContractDocumentationToClient()));
////                itemList.add(new ContractDocumentationControlDataDTO("Carta de preaviso de fin de contrato", traceabilityContractDocumentationDTO.getContractEndNoticeReceptionDate(), null));
////
////                ObservableList<ContractDocumentationControlDataDTO> tableItemOL = FXCollections.observableArrayList(itemList);
////                contractDocumentationControlData.refreshContractDocumentationControlTable(tableItemOL);
//            }
//        }
//    }

//    private void onCellTableOrINEMChange(CellTableChangedEvent event){
//        if(event.getCellsEdited()){
//            contractDocumentationControlAction.getSaveButton().setDisable(true);
//            contractDocumentationControlAction.getOkButton().setDisable(false);
//        }else{
//            contractDocumentationControlAction.getSaveButton().setDisable(false);
//            contractDocumentationControlAction.getOkButton().setDisable(true);
//        }
//    }

    private void onOkButton(MouseEvent event){
//        if(contractDocumentationControlData.cellsEdited) {
            consultationContractAction.getSaveButton().setDisable(false);
//        }
    }

    private void onSaveButton(MouseEvent event){
//        ContractService contractService = ContractService.ContractServiceFactory.getInstance();
//        Integer contractNumber;
//        Integer contractId;
//        ObservableList<ContractDocumentationControlDataDTO> contractDocumentationControlDataDTOOL = contractDocumentationControlData.getContractDocumentControlTable().getItems();
//
////        ContractNewVersionDTO contractNewVersionDTO = contractDocumentationControlSelector.getContractSelectedVariations().getSelectionModel().getSelectedItem();
//
//        // Update identification contract number INEM
//        String identificationContractNumberINEM = contractDocumentationControlData.getIdentificationContractNumberINEM().getText();
//        contractNewVersionDTO.getContractJsonData().setIdentificationContractNumberINEM(identificationContractNumberINEM);
//        if(contractNewVersionDTO.getVariationType() < 200){
//            contractNumber = contractService.updateInitialContract(contractNewVersionDTO);
//            if(contractNumber != null){
//                Message.informationMessage((Stage) contractDocumentationControlHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractDocumentationControlConstants.INEM_NUMBER_MODIFICATION_SAVED_OK);
//                logger.info("El número de contrato INEM del contrato inicial se ha actualizado correctamente.");
//
//            }else{
//                Message.errorMessage((Stage) contractDocumentationControlHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractDocumentationControlConstants.INEM_NUMBER_MODIFICATION_NOT_SAVED_OK);
//                logger.info("Problemas al actualizar el número de contrato INEM del contrato inicial.");
//            }
//        }else{
//            contractId = contractService.updateContractVariation(contractNewVersionDTO);
//            if(contractId != null){
//                Message.informationMessage((Stage) contractDocumentationControlHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractDocumentationControlConstants.INEM_NUMBER_MODIFICATION_SAVED_OK);
//                logger.info("El número de contrato INEM de la variación del contrato se ha actualizado correctamente.");
//
//            }else{
//                Message.errorMessage((Stage) contractDocumentationControlHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractDocumentationControlConstants.INEM_NUMBER_MODIFICATION_NOT_SAVED_OK);
//                logger.info("Problemas al actualizar el número de contrato INEM de la variación del contrato.");
//            }
//        }

        // Update traceability contract data
//        TraceabilityService traceabilityService = TraceabilityService.TraceabilityServiceFactory.getInstance();
//        List<TraceabilityContractDocumentationDTO> traceabilityContractDocumentationDTOList = traceabilityService.findAllTraceabilityContractData();
//        for(TraceabilityContractDocumentationDTO traceabilityContractDocumentationDTO : traceabilityContractDocumentationDTOList){
//            if(traceabilityContractDocumentationDTO.getContractNumber().equals(contractNewVersionDTO.getContractNumber()) &&
//            traceabilityContractDocumentationDTO.getVariationType().equals(contractNewVersionDTO.getVariationType()) &&
//                    traceabilityContractDocumentationDTO.getStartDate().equals(contractNewVersionDTO.getStartDate())){
//
//                TraceabilityContractDocumentationDTO traceabilityContractDocumentationDTOToUpdate = TraceabilityContractDocumentationDTO.create()
//                        .withId(traceabilityContractDocumentationDTO.getId())
//                        .withContractNumber(traceabilityContractDocumentationDTO.getContractNumber())
//                        .withVariationType(traceabilityContractDocumentationDTO.getVariationType())
//                        .withStartDate(traceabilityContractDocumentationDTO.getStartDate())
//                        .withExpectedEndDate(traceabilityContractDocumentationDTO.getExpectedEndDate())
//                        .withIDCReceptionDate(contractDocumentationControlDataDTOOL.get(0).getReceptionDate())
//                        .withDateDeliveryContractDocumentationToClient(contractDocumentationControlDataDTOOL.get(1).getDeliveryDate())
//                        .withContractEndNoticeReceptionDate(contractDocumentationControlDataDTOOL.get(2).getReceptionDate())
//                        .build();
//
//                TraceabilityContractDocumentationController traceabilityContractDocumentationController = new TraceabilityContractDocumentationController();
//                if(traceabilityContractDocumentationController.updateTraceabilityRecord(traceabilityContractDocumentationDTOToUpdate) != null){
//                    logger.info("Trazabilidad actualizada correctamente.");
//                    Message.informationMessage((Stage) contractDocumentationControlHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractDocumentationControlConstants.TRACEABILITY_MODIFICATION_SAVED_OK);
//                }else{
//                    Message.errorMessage((Stage) contractDocumentationControlHeader.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, ContractDocumentationControlConstants.TRACEABILITY_MODIFICATION_SAVED_NOT_OK);
//                    logger.info("Problemas en la actualización de la trazabilidad.");
//                }
//            }
//        }

        consultationContractAction.getSaveButton().setDisable(true);
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
