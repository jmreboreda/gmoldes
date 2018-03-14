package gmoldes.controllers;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract.events.*;
import gmoldes.components.contract.new_contract.*;
import gmoldes.domain.dto.ClientCCCDTO;
import gmoldes.domain.dto.ClientDTO;
import gmoldes.domain.dto.PersonDTO;
import gmoldes.domain.dto.ProvisionalContractDataDTO;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.Utilities;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class NewContractMainController extends VBox {

    private static final Logger logger = Logger.getLogger(NewContractMainController.class.getSimpleName());
    private static final String MAIN_FXML = "/fxml/new_contract/contract_main.fxml";

    private ClientController clientController = new ClientController();
    private PersonController personController = new PersonController();
    private ClientCCCController clientCCCController = new ClientCCCController();

    private Parent parent;

    @FXML
    private TabPane tabPane;
    @FXML
    private ContractHeader contractHeader;
    @FXML
    private ContractParts contractParts;
    @FXML
    private ContractData contractData;
    @FXML
    private ContractSchedule contractSchedule;
    @FXML
    private ContractPublicNotes contractPublicNotes;
    @FXML
    private ContractPrivateNotes contractPrivateNotes;
    @FXML
    private ProvisionalContractData provisionalContractData;
    @FXML
    private ContractActionComponents contractActionComponents;


    public NewContractMainController() {
        logger.info("Initilizing Main fxml");
        this.parent = ViewLoader.load(this, MAIN_FXML);
    }

    @FXML
    public void initialize() {
        contractActionComponents.setOnOkButton(this::onOkButton);
        tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                refreshProvisionalContractData();
            }
        });

        contractData.setOnChangeContractDataHoursWorkWeek(this::onChangeContractDataHoursWorkWeek);
        contractParts.setOnSearchEmployers(this::onSearchEmployers);
        contractParts.setOnSearchEmployees(this::onSearchEmployees);
        contractParts.setOnSelectEmployer(this::onSelectEmployer);
        contractSchedule.setOnChangeScheduleDuration(this::onChangeScheduleDuration);
    }

    private void onOkButton(MouseEvent event){
        System.out.println(event.getSource() + " clicked!");
        Boolean isAllDataOk = verifyAllContractData();
        System.out.println("Tutto ok!!!");
    }

    private void refreshProvisionalContractData(){
        ProvisionalContractDataDTO contractDataDTO = retrieveProvisionalContractDataDTO();
        provisionalContractData.refreshData(contractDataDTO);
    }

    private ProvisionalContractDataDTO retrieveProvisionalContractDataDTO(){
        ProvisionalContractDataDTO partsDTO = contractParts.getAllData();
        ProvisionalContractDataDTO dataDTO = contractData.getAllData();
        dataDTO.setEmployerFullName(partsDTO.getEmployerFullName());
        dataDTO.setEmployeeFullName(partsDTO.getEmployeeFullName());
        dataDTO.setQuoteAccountCode(partsDTO.getQuoteAccountCode());

        return dataDTO;
    }

    private void onSearchEmployers(SearchEmployersEvent searchEmployersEvent){
        String pattern = searchEmployersEvent.getPattern();
        if(pattern.isEmpty()){
            contractParts.clearEmployersData();
            contractParts.clearEmployerCCC();
            return;
        }
        List<ClientDTO> employers = findClientsByNamePattern(pattern);
        contractParts.refreshEmployers(employers);
    }

    private void onSearchEmployees(SearchEmployeesEvent searchEmployeesEvent){
        String pattern = searchEmployeesEvent.getPattern();
        if(pattern.isEmpty()){
            contractParts.clearEmployeesData();
            return;
        }
        List<PersonDTO> employees = findPersonsByNamePattern(pattern);
        contractParts.refreshEmployees(employees);
    }

    private void onSelectEmployer(SelectEmployerEvent selectEmployerEvent){
        ClientDTO selectedEmployer  = selectEmployerEvent.getSelectedEmployer();
        List<ClientDTO> clientDTOList = new ArrayList<>();
        clientDTOList.add(selectedEmployer);
        contractParts.refreshEmployers(clientDTOList);

        Integer selectedEmployerId = selectEmployerEvent.getSelectedEmployer().getId();
        List<ClientCCCDTO> clientCCCDTOList = retrieveClientCCCById(selectedEmployerId);
        contractParts.refreshEmployerCCC(clientCCCDTOList);
    }

    private void onChangeContractDataHoursWorkWeek(ChangeContractDataHoursWorkWeekEvent event){
        Duration scheduleHoursWorkWeekDuration = Utilities.converterTimeStringToDuration(contractSchedule.getHoursWorkWeek());
        if(scheduleHoursWorkWeekDuration != Duration.ZERO){
            if(event.getContractDataHoursWorkWeek().compareTo(scheduleHoursWorkWeekDuration) != 0){
                System.out.println("El total de horas de la pestaña \"Horario\" es distinto que el total de horas de la pestaña \"Contrato\".");
            }
        }
    }

    private void onChangeScheduleDuration(ChangeScheduleDurationEvent event){

        if(event.getContractScheduleTotalHoursDuration().compareTo(Parameters.LEGAL_MAXIMUM_HOURS_OF_WORK_PER_WEEK) > 0){
            System.out.println("Exceso de jornada laboral.");
            return;
        }

        Duration duration = Utilities.converterTimeStringToDuration(contractData.getHoursWorkWeek());
        assert duration != null;
        if(event.getContractScheduleTotalHoursDuration().compareTo(duration) > 0){
            System.out.println("El total de horas de la pestaña \"Horario\" es mayor que el total de horas de la pestaña \"Contrato\".");
        }
    }

    private List<ClientDTO> findClientsByNamePattern(String pattern){
        return clientController.findAllActiveClientByNamePatternInAlphabeticalOrder(pattern);
    }

    private List<PersonDTO> findPersonsByNamePattern(String pattern){
        return personController.findAllPersonsByNamePatternInAlphabeticalOrder(pattern);
    }

    private List<ClientCCCDTO> retrieveClientCCCById(Integer id){
        return clientCCCController.findAllCCCByClientId(id);
    }

    private Boolean verifyAllContractData(){
        ProvisionalContractData provisionalContractData = new ProvisionalContractData();
        ProvisionalContractDataDTO allContractData = provisionalContractData.getAllProvisionalContractData();

        return true;
    }
}
