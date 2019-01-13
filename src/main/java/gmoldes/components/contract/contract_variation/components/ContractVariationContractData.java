package gmoldes.components.contract.contract_variation.components;

import gmoldes.ApplicationConstants;
import gmoldes.components.ViewLoader;
import gmoldes.domain.contract.dto.ContractFullDataDTO;
import gmoldes.utilities.Parameters;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class ContractVariationContractData extends TitledPane {

    private static final String CONTRACT_VARIATION_CONTRACT_DATA_FXML = "/fxml/contract_variations/contractvariation_data_alternative.fxml";

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
        this.lastVariationDate = lastVariationDate;
    }

    public void clearAllContractData(){

        getContractNumber().setText("");
        getDateFrom().setText("");
        getDateTo().setText("");
        getContractType().setText("");
        getContractDuration().setText("");
        getContractPartialFullTime().setText("");
        getLastVariationDescription().setText("");
        getLastVariationDate().setText("");
    }

    public void setAllContractData(ContractFullDataDTO contractFullDataDTO){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);

        this.getContractNumber().setText(contractFullDataDTO.getContractNewVersion().getContractNumber().toString());

        this.getDateFrom().setText(contractFullDataDTO.getInitialContractDate().format(formatter));

        String dateTo = null;
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

        this.getLastVariationDescription().setText(contractFullDataDTO.getTypesContractVariationsDTO().getVariation_description());

        this.getLastVariationDate().setText(contractFullDataDTO.getContractNewVersion().getStartDate().format(formatter));
    }
}
