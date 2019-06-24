package gmoldes.components.contract_variation.components;

import gmoldes.components.ViewLoader;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;

public class ContractVariationHeader extends HBox {

    private static final String CONTRACT_VARIATION_HEADER_FXML = "/fxml/contract_variations/contractvariation_header.fxml";

    private Parent parent;

    public ContractVariationHeader() {
        this.parent = ViewLoader.load(this, CONTRACT_VARIATION_HEADER_FXML);
    }

}
