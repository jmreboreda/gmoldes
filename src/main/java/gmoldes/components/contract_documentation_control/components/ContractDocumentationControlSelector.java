package gmoldes.components.contract_documentation_control.components;

import gmoldes.components.ViewLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ContractDocumentationControlSelector extends AnchorPane {

    private static final String CONTRACT_DOCUMENTATION_CONTROL_SELECTOR_FXML = "/fxml/contract_documentation_control/contract_documentation_control_selector.fxml";

    private Parent parent;
    private Stage stage;


    public ContractDocumentationControlSelector() {
        this.parent = ViewLoader.load(this, CONTRACT_DOCUMENTATION_CONTROL_SELECTOR_FXML);
    }
}
