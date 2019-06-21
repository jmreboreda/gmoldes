package gmoldes.components.contract.consultation_contract.components;

import gmoldes.components.ViewLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class ConsultationContractHeader extends VBox {

    private static final String CONSULTATION_CONTRACT_HEADER_FXML = "/fxml/consultation_contract/consultation_contract_header.fxml";

    private Parent parent;

    public ConsultationContractHeader() {
        this.parent = ViewLoader.load(this, CONSULTATION_CONTRACT_HEADER_FXML);
    }
}
