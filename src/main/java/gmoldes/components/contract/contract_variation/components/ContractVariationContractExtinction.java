package gmoldes.components.contract.contract_variation.components;

import gmoldes.components.ViewLoader;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;
import gmoldes.utilities.Utilities;
import javafx.fxml.FXML;
import javafx.scene.Group;
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
    private RadioButton rbContractExtinction;
    @FXML
    private Group contractExtinctionGroup;
    @FXML
    private ChoiceBox<TypesContractVariationsDTO> extinctionCauseSelector;
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

    public RadioButton getRbContractExtinction() {
        return rbContractExtinction;
    }

    public void setRbContractExtinction(RadioButton rbContractExtinction) {
        this.rbContractExtinction = rbContractExtinction;
    }

    public Group getContractExtinctionGroup() {
        return contractExtinctionGroup;
    }

    public void setContractExtinctionGroup(Group contractExtintionGroup) {
        this.contractExtinctionGroup = contractExtintionGroup;
    }

    public ChoiceBox<TypesContractVariationsDTO> getExtinctionCauseSelector() {
        return extinctionCauseSelector;
    }

    public void setExtinctionCauseSelector(ChoiceBox<TypesContractVariationsDTO> extinctionCauseSelector) {
        this.extinctionCauseSelector = extinctionCauseSelector;
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
