package gmoldes.components.contract_documentation_control.controllers;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract_documentation_control.components.ContractDocumentationControlAction;
import gmoldes.components.contract_documentation_control.components.ContractDocumentationControlData;
import gmoldes.components.contract_documentation_control.components.ContractDocumentationControlHeader;
import gmoldes.components.contract_documentation_control.components.ContractDocumentationControlSelector;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ContractDocumentationControlMainController extends AnchorPane {

    private static final String CONTRACT_DOCUMENTATION_CONTROL_MAIN_FXML = "/fxml/contract_documentation_control/contract_documentation_control_main.fxml";

    private Parent parent;
    private Stage stage;

    @FXML
    ContractDocumentationControlHeader contractDocumentationControlHeader;
    @FXML
    ContractDocumentationControlSelector contractDocumentationControlSelector;
    @FXML
    ContractDocumentationControlData contractDocumentationControlData;
    @FXML
    ContractDocumentationControlAction contractDocumentationControlAction;


    public ContractDocumentationControlMainController() {
        this.parent = ViewLoader.load(this, CONTRACT_DOCUMENTATION_CONTROL_MAIN_FXML);
    }
}
