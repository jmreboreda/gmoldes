package gmoldes.components.contract.contract_variation.components;

import com.sun.javafx.scene.paint.GradientUtils;
import gmoldes.components.ViewLoader;
import gmoldes.components.contract.controllers.TypesContractVariationsController;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;
import gmoldes.utilities.Utilities;
import javafx.beans.Observable;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class ContractVariationContractExtension extends VBox {

    private static final String CONTRACT_VARIATION_CONTRACT_EXTENSION_FXML = "/fxml/contract_variations/contractvariation_contract_extension.fxml";

    private Parent parent;

    @FXML
    private Group contractExtensionGroup;
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

        contractExtensionGroup.disableProperty().bind(this.rbContractExtension.selectedProperty().not());
    }

    public Group getContractExtensionGroup() {
        return contractExtensionGroup;
    }

    public void setContractExtensionGroup(Group contractExtensionGoup) {
        this.contractExtensionGroup = contractExtensionGoup;
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
