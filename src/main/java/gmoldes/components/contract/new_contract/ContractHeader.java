package gmoldes.components.contract.new_contract;

import gmoldes.components.ViewLoader;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;

public class ContractHeader extends HBox {

    private static final String CONTRACT_HEADER_FXML = "/fxml/new_contract/contract_header.fxml";

    private Parent parent;

    public ContractHeader() {
        this.parent = ViewLoader.load(this, CONTRACT_HEADER_FXML);
    }

}
