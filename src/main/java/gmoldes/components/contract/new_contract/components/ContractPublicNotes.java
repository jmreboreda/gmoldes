package gmoldes.components.contract.new_contract.components;

import gmoldes.components.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class ContractPublicNotes extends AnchorPane {

    private static final String PUBLIC_NOTES_FXML = "/fxml/new_contract/contract_public_notes.fxml";

    private static final String STANDARD_TEXT_OF_APPORTIONMENT_EXTRAORDINARY_PAYMENTS = "Pagas extraordinarias prorrateadas.";

    private Parent parent;

    @FXML
    private TextArea taPublicNotes;

    public ContractPublicNotes() {
        this.parent = ViewLoader.load(this, PUBLIC_NOTES_FXML);
    }

    @FXML
    private void initialize(){

        taPublicNotes.setStyle("-fx-text-fill: #000FFF"); //#1807ac
        taPublicNotes.setText(STANDARD_TEXT_OF_APPORTIONMENT_EXTRAORDINARY_PAYMENTS);
    }

    public String getPublicNotes(){
        return this.taPublicNotes.getText();
    }
}

