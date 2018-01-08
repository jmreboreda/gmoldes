package gmoldes.components.contract.new_contract;

import gmoldes.components.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class ContractPrivateNotes extends AnchorPane {

    private static final String PRIVATE_NOTES_FXML = "/fxml/new_contract/contract_private_notes.fxml";

    private Parent parent;

    @FXML
    private TextArea taPrivateNotes;

    public ContractPrivateNotes() {
        this.parent = ViewLoader.load(this, PRIVATE_NOTES_FXML);
    }

    @FXML
    private void initialize(){
        taPrivateNotes.setStyle("-fx-text-fill: #8b0000");
    }
}
