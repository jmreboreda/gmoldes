package gmoldes.components.contract_documentation_control.components;

import gmoldes.components.ViewLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class ContractDocumentationControlHeader extends VBox {

    private static final String CONTRACT_DOCUMENTATION_CONTROL_HEADER_FXML = "/fxml/contract_documentation_control/contract_documentation_control_header.fxml";

    private Parent parent;

    public ContractDocumentationControlHeader() {
        this.parent = ViewLoader.load(this, CONTRACT_DOCUMENTATION_CONTROL_HEADER_FXML);
    }
}
