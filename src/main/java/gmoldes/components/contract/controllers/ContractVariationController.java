package gmoldes.components.contract.controllers;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract.contract_variation.components.ContractVariationHeader;
import gmoldes.components.contract.contract_variation.components.ContractVariationParts;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import java.util.logging.Logger;

public class ContractVariationController extends VBox {

    private static final Logger logger = Logger.getLogger(ContractVariationController.class.getSimpleName());
    private static final String CONTRACTVARIATION_MAIN_FXML = "/fxml/contract_variations/contractvariation_main.fxml";

    private Parent parent;

    @FXML
    private ContractVariationHeader contractVariationHeader;
    @FXML
    private ContractVariationParts contractVariationParts;

    @FXML
    public void initialize() {

    }

    public ContractVariationController() {
        logger.info("Initializing ContractVariation Main fxml");
        this.parent = ViewLoader.load(this, CONTRACTVARIATION_MAIN_FXML);
    }
}
