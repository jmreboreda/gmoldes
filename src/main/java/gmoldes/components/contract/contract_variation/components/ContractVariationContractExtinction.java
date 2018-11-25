package gmoldes.components.contract.contract_variation.components;

import gmoldes.components.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;

public class ContractVariationContractExtinction extends VBox {

    private static final String CONTRACT_VARIATION_CONTRACT_EXTINCTION_FXML = "/fxml/contract_variations/contractvariation_contract_extinction.fxml";

    private Parent parent;

    @FXML
    private RadioButton rbContractExtinction;

    public ContractVariationContractExtinction() {
        this.parent = ViewLoader.load(this, CONTRACT_VARIATION_CONTRACT_EXTINCTION_FXML);
    }
}
