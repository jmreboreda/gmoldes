package gmoldes.components.contract.contract_variation.components;

import gmoldes.components.ViewLoader;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;
import gmoldes.utilities.Utilities;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class ContractVariationContractExtinction extends VBox {

    private static final String CONTRACT_VARIATION_CONTRACT_EXTINCTION_FXML = "/fxml/contract_variations/contractvariation_contract_extinction.fxml";

    private Parent parent;

    @FXML
    private ToggleGroup contractVariation;
    @FXML
    private RadioButton rbContractExtinction;
    @FXML
    private ChoiceBox<TypesContractVariationsDTO> extintionCauseSelector;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private RadioButton rbHolidaysYes;
    @FXML
    private RadioButton rbHolidaysNo;

    public ContractVariationContractExtinction() {
        this.parent = ViewLoader.load(this, CONTRACT_VARIATION_CONTRACT_EXTINCTION_FXML);
    }

    @FXML
    private void initialize(){

        dateFrom.setConverter(Utilities.converter);

    }

    public ToggleGroup getContractVariation() {
        return contractVariation;
    }

    public void setContractVariation(ToggleGroup contractVariation) {
        this.contractVariation = contractVariation;
    }

    public RadioButton getRbContractExtinction() {
        return rbContractExtinction;
    }

    public void setRbContractExtinction(RadioButton rbContractExtinction) {
        this.rbContractExtinction = rbContractExtinction;
    }

    public ChoiceBox<TypesContractVariationsDTO> getExtintionCauseSelector() {
        return extintionCauseSelector;
    }

    public void setExtintionCauseSelector(ChoiceBox<TypesContractVariationsDTO> extintionCauseSelector) {
        this.extintionCauseSelector = extintionCauseSelector;
    }

    public DatePicker getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(DatePicker dateFrom) {
        this.dateFrom = dateFrom;
    }

    public RadioButton getRbHolidaysYes() {
        return rbHolidaysYes;
    }

    public void setRbHolidaysYes(RadioButton rbHolidaysYes) {
        this.rbHolidaysYes = rbHolidaysYes;
    }

    public RadioButton getRbHolidaysNo() {
        return rbHolidaysNo;
    }

    public void setRbHolidaysNo(RadioButton rbHolidaysNo) {
        this.rbHolidaysNo = rbHolidaysNo;
    }
}
