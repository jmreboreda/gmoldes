package gmoldes.components.contract.contract_variation.components;

import gmoldes.components.ViewLoader;
import gmoldes.utilities.Utilities;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;

public class ContractVariationContractConversion extends VBox {

    private static final String CONTRACT_VARIATION_CONTRACT_CONVERSION_FXML = "/fxml/contract_variations/contractvariation_contract_conversion.fxml";

    private Parent parent;

    @FXML
    private Group contractConversionGroup;
    @FXML
    private RadioButton rbContractConversion;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;

    public ContractVariationContractConversion() {
        this.parent = ViewLoader.load(this, CONTRACT_VARIATION_CONTRACT_CONVERSION_FXML);
    }

    @FXML
    private void initialize(){

        dateFrom.setConverter(Utilities.dateConverter);
        dateTo.setConverter(Utilities.dateConverter);

        contractConversionGroup.disableProperty().bind(this.rbContractConversion.selectedProperty().not());
    }

    public Group getContractConversionGroup() {
        return contractConversionGroup;
    }

    public void setContractConversionGroup(Group contractConversionGroup) {
        this.contractConversionGroup = contractConversionGroup;
    }

    public RadioButton getRbContractConversion() {
        return rbContractConversion;
    }

    public void setRbContractConversion(RadioButton rbContractConversion) {
        this.rbContractConversion = rbContractConversion;
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
