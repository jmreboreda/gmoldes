package gmoldes.components.contract_variation.components;

import gmoldes.ApplicationConstants;
import gmoldes.components.ViewLoader;
import gmoldes.components.contract_variation.ContractVariationConstants;
import gmoldes.components.contract.controllers.TypesContractVariationsController;
import gmoldes.domain.contract.ContractService;
import gmoldes.domain.contract.dto.ContractFullDataDTO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.types_contract_variations.dto.TypesContractVariationsDTO;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ContractVariationContractData extends TitledPane {

    private static final String CONTRACT_VARIATION_CONTRACT_DATA_FXML = "/fxml/contract_variations/contractvariation_data.fxml";

    private Parent parent;

    @FXML
    private TextField contractNumber;
    @FXML
    private TextField dateFrom;
    @FXML
    private TextField dateTo;
    @FXML
    private TextField contractType;
    @FXML
    private TextField contractDuration;
    @FXML
    private TextField contractPartialFullTime;
    @FXML
    private TextField lastVariationAtDateDescription;
    @FXML
    private TextField lastVariationAtDateDate;
    @FXML
    private TextField lastVariationDescription;
    @FXML
    private TextField lastVariationDate;

    public ContractVariationContractData() {
        this.parent = ViewLoader.load(this, CONTRACT_VARIATION_CONTRACT_DATA_FXML);
    }

    public TextField getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(TextField contractNumber) {
        this.contractNumber = contractNumber;
    }

    public static String getContractVariationContractDataFxml() {
        return CONTRACT_VARIATION_CONTRACT_DATA_FXML;
    }

    public TextField getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(TextField dateFrom) {
        this.dateFrom = dateFrom;
    }

    public TextField getDateTo() {
        return dateTo;
    }

    public void setDateTo(TextField dateTo) {
        this.dateTo = dateTo;
    }

    public TextField getContractType() {
        return contractType;
    }

    public void setContractType(TextField contractType) {
        this.contractType = contractType;
    }

    public TextField getContractDuration() {
        return contractDuration;
    }

    public void setContractDuration(TextField contractDuration) {
        this.contractDuration = contractDuration;
    }

    public TextField getContractPartialFullTime() {
        return contractPartialFullTime;
    }

    public void setContractPartialFullTime(TextField contractPartialFullTime) {
        this.contractPartialFullTime = contractPartialFullTime;
    }

    public TextField getLastVariationAtDateDescription() {
        return lastVariationAtDateDescription;
    }

    public void setLastVariationAtDateDescription(TextField lastVariationAtDateDescription) {
        this.lastVariationAtDateDescription = lastVariationAtDateDescription;
    }

    public TextField getLastVariationAtDateDate() {
        return lastVariationAtDateDate;
    }

    public void setLastVariationAtDateDate(TextField lastVariationAtDateDate) {
        this.lastVariationAtDateDate = lastVariationAtDateDate;
    }

    public TextField getLastVariationDescription() {
        return lastVariationDescription;
    }

    public void setLastVariationDescription(TextField lastVariationDescription) {
        this.lastVariationDescription = lastVariationDescription;
    }

    public TextField getLastVariationDate() {
        return lastVariationDate;
    }

    public void setLastVariationDate(TextField lastVariationDate) {
        this.lastVariationAtDateDate = lastVariationDate;
    }

    public void clearAllContractData(){

        getContractNumber().setText("");
        getDateFrom().setText("");
        getDateTo().setText("");
        getContractType().setText("");
        getContractDuration().setText("");
        getContractPartialFullTime().setText("");
        getLastVariationAtDateDescription().setText("");
        getLastVariationAtDateDate().setText("");
        getLastVariationDescription().setText("");
        getLastVariationDate().setText("");
    }

    public void setAllContractData(ContractFullDataDTO contractFullDataDTO){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);

        this.getContractNumber().setText(contractFullDataDTO.getContractNewVersion().getContractNumber().toString());

        String dateFrom = contractFullDataDTO.getInitialContractDate() != null ? contractFullDataDTO.getInitialContractDate().format(formatter) : null;
        this.getDateFrom().setText(dateFrom);

        String dateTo;
        if(contractFullDataDTO.getContractNewVersion().getEndingDate() != null &&
                (contractFullDataDTO.getContractNewVersion().getEndingDate().isBefore(LocalDate.now()) ||
                contractFullDataDTO.getContractNewVersion().getEndingDate().equals(LocalDate.now()))){
            dateTo = contractFullDataDTO.getContractNewVersion().getEndingDate().format(formatter);
        }else {
            dateTo = contractFullDataDTO.getContractNewVersion().getExpectedEndDate() != null ? contractFullDataDTO.getContractNewVersion().getExpectedEndDate().format(formatter) : null;
        }
        this.getDateTo().setText(dateTo);

        this.getContractType().setText(contractFullDataDTO.getContractType().getColloquial());

        String contractDuration = contractFullDataDTO.getContractNewVersion().getExpectedEndDate() != null ? "Temporal" : "Indefinido";
        this.getContractDuration().setText(contractDuration);

        String partialFull = contractFullDataDTO.getContractNewVersion().getContractJsonData().getFullPartialWorkDay() + " [" +
                contractFullDataDTO.getContractNewVersion().getContractJsonData().getWeeklyWorkHours() + "]";
        this.getContractPartialFullTime().setText(partialFull);

        this.getLastVariationAtDateDescription().setText(contractFullDataDTO.getTypesContractVariationsDTO().getVariation_description());

        this.getLastVariationAtDateDate().setText(contractFullDataDTO.getContractNewVersion().getStartDate().format(formatter));

        ContractService contractService = ContractService.ContractServiceFactory.getInstance();
        List<ContractNewVersionDTO> contractNewVersionDTOList = contractService.findHistoryOfContractByContractNumber(contractFullDataDTO.getContractNewVersion().getContractNumber());
        LocalDate maximumDate = LocalDate.of(1900,01,01);
        Integer index = 0;
        for(ContractNewVersionDTO contractNewVersionDTO : contractNewVersionDTOList){
            if(maximumDate.compareTo(contractNewVersionDTO.getStartDate()) <= 0){
                maximumDate = contractNewVersionDTO.getStartDate();
                index = contractNewVersionDTOList.indexOf(contractNewVersionDTO);
            }
        }

        // Last variation for the contract
        TypesContractVariationsController typesContractVariationsController = new TypesContractVariationsController();
        TypesContractVariationsDTO typesContractVariationsDTO = typesContractVariationsController.findTypesContractVariationsById(contractNewVersionDTOList.get(index).getVariationType());
        this.getLastVariationDescription().setText(typesContractVariationsDTO.getVariation_description());
        this.getLastVariationDate().setText(maximumDate.format(formatter));
        if(maximumDate.compareTo(LocalDate.now()) > 0){
            getLastVariationDescription().setStyle(ContractVariationConstants.COLOR_OLIVE);
            getLastVariationDate().setStyle(ContractVariationConstants.COLOR_OLIVE);
        }
        else{
            getLastVariationDescription().setStyle(ContractVariationConstants.COLOR_RED);
            getLastVariationDate().setStyle(ContractVariationConstants.COLOR_RED);
        }
    }
}
