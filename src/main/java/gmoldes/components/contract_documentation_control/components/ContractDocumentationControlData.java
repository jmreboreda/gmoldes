package gmoldes.components.contract_documentation_control.components;

import gmoldes.components.ViewLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ContractDocumentationControlData extends AnchorPane {

    private static final String CONTRACT_DOCUMENTATION_CONTROL_DATA_FXML = "/fxml/contract_documentation_control/contract_documentation_control_data.fxml";

    private Parent parent;
    private Stage stage;



    public ContractDocumentationControlData() {
        this.parent = ViewLoader.load(this, CONTRACT_DOCUMENTATION_CONTROL_DATA_FXML);
    }
}
