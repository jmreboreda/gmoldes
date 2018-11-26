package gmoldes.components.contract.contract_variation.components;

import gmoldes.components.ViewLoader;
import gmoldes.utilities.Utilities;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class ContractVariationContractExtension extends VBox {

    private static final String CONTRACT_VARIATION_CONTRACT_EXTENSION_FXML = "/fxml/contract_variations/contractvariation_contract_extension.fxml";

    private Parent parent;

    @FXML
    private ToggleGroup contractVariation;
    @FXML
    private RadioButton rbContractExtension;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;

    public ContractVariationContractExtension() {
        this.parent = ViewLoader.load(this, CONTRACT_VARIATION_CONTRACT_EXTENSION_FXML);
    }

    @FXML
    private void initialize(){

        dateFrom.setConverter(Utilities.converter);
        dateTo.setConverter(Utilities.converter);
    }

    public ToggleGroup getContractVariation() {
        return contractVariation;
    }

    public void setContractVariation(ToggleGroup contractVariation) {
        this.contractVariation = contractVariation;
    }

    public RadioButton getRbContractExtension() {
        return rbContractExtension;
    }

    public void setRbContractExtension(RadioButton rbContractExtension) {
        this.rbContractExtension = rbContractExtension;
    }

    public DatePicker getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(DatePicker dateFrom) {
        this.dateFrom = dateFrom;
    }

    public DatePicker getDateTo() {
        return dateTo;
    }

    public void setDateTo(DatePicker dateTo) {
        this.dateTo = dateTo;
    }
}
