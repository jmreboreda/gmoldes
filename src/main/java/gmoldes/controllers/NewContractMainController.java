package gmoldes.controllers;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract.new_contract.*;
import gmoldes.components.contract.new_contract.events.SearchEmployeesEvent;
import gmoldes.components.contract.new_contract.events.SearchEmployersEvent;
import gmoldes.domain.dto.ClientDTO;
import gmoldes.domain.dto.PersonDTO;
import gmoldes.domain.dto.ProvisionalContractDataDTO;
import gmoldes.manager.ClientManager;
import gmoldes.manager.PersonManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.logging.Logger;

public class NewContractMainController extends VBox {


    private static final Logger logger = Logger.getLogger(NewContractMainController.class.getSimpleName());
    private static final String MAIN_FXML = "/fxml/new_contract/contract_main.fxml";

    private ClientManager clientManager = new ClientManager();
    private PersonManager personManager = new PersonManager();

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

        contractParts.setOnSearchEmployers(this::onSearchEmployers);
        contractParts.setOnSearchEmployees(this::onSearchEmployees);

    }

    private void refreshProvisionalContractData(){
        ProvisionalContractDataDTO contractDataDTO = retrieveProvisionalContractDataDTO();
        provisionalContractData.refreshData(contractDataDTO);
    }

    private void onOkButton(MouseEvent event){
        System.out.println(event.getSource() + " clicked!");
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
        String employersNameSelectedItem = searchEmployersEvent.getEmployersNameSelectedItem();
        if(pattern == null){
            pattern = "";
        }
        if(pattern.isEmpty()){
            contractParts.clearEmployersData();
            return;
        }
        if(pattern.equals(employersNameSelectedItem)){
            return;
        }
        List<ClientDTO> employers = findClientsByNamePattern(pattern);
        contractParts.refreshEmployers(employers);
    }

    private void onSearchEmployees(SearchEmployeesEvent searchEmployeesEvent){
        String pattern = searchEmployeesEvent.getPattern();
        String employeesNameSelectedItem = searchEmployeesEvent.getEmployeesNameSelectedItem();
        if(pattern == null){
            pattern = "";
        }
        if(pattern.isEmpty()){
            contractParts.clearEmployeesData();
            return;
        }
        if(pattern.equals(employeesNameSelectedItem)){
            return;
        }
        List<PersonDTO> employees = findPersonsByNamePattern(pattern);
        contractParts.refreshEmployees(employees);
    }

    private List<ClientDTO> findClientsByNamePattern(String pattern){
        return clientManager.findAllActiveClientByNamePatternInAlphabeticalOrder(pattern);
    }

    private List<PersonDTO> findPersonsByNamePattern(String pattern){
        return personManager.findAllPersonsByNamePatternInAlphabeticalOrder(pattern);
    }
}
