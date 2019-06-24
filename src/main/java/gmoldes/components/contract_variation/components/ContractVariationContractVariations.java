package gmoldes.components.contract_variation.components;

import gmoldes.components.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ContractVariationContractVariations extends VBox {

    private static final String CONTRACT_VARIATION_CONTRACT_VARIATIONS_FXML = "/fxml/contract_variations/contractvariation_contractvariations.fxml";

    private Parent parent;

    @FXML
    private ToggleGroup contractVariationToggleGroup;
    @FXML
    private StackPane contractVariationStackPane;
    @FXML
    private Group contractVariationsGroup;
    @FXML
    private ContractVariationContractExtinction contractVariationContractExtinction;
    @FXML
    private ContractVariationContractExtension contractVariationContractExtension;
    @FXML
    private ContractVariationWeeklyWorkScheduleDuration contractVariationWeeklyWorkScheduleDuration;
    @FXML
    private ContractVariationContractConversion contractVariationContractConversion;



    public ContractVariationContractVariations() {
        this.parent = ViewLoader.load(this, CONTRACT_VARIATION_CONTRACT_VARIATIONS_FXML);
    }

    @FXML
    private void initialize(){

    }

    public StackPane getContractVariationStackPane() {
        return contractVariationStackPane;
    }

    public void setContractVariationStackPane(StackPane contractVariationStackPane) {
        this.contractVariationStackPane = contractVariationStackPane;
    }

    public ContractVariationContractExtinction getContractVariationContractExtinction() {
        return contractVariationContractExtinction;
    }

    public Group getContractVariationsGroup() {
        return contractVariationsGroup;
    }

    public void setContractVariationsGroup(Group contractVariationsGroup) {
        this.contractVariationsGroup = contractVariationsGroup;
    }

    public void setContractVariationContractExtinction(ContractVariationContractExtinction contractVariationContractExtinction) {
        this.contractVariationContractExtinction = contractVariationContractExtinction;
    }

    public ContractVariationContractExtension getContractVariationContractExtension() {
        return contractVariationContractExtension;
    }

    public void setContractVariationContractExtension(ContractVariationContractExtension contractVariationContractExtension) {
        this.contractVariationContractExtension = contractVariationContractExtension;
    }

    public ContractVariationWeeklyWorkScheduleDuration getContractVariationWeeklyWorkScheduleDuration() {
        return contractVariationWeeklyWorkScheduleDuration;
    }

    public void setContractVariationWeeklyWorkScheduleDuration(ContractVariationWeeklyWorkScheduleDuration contractVariationWeeklyWorkScheduleDuration) {
        this.contractVariationWeeklyWorkScheduleDuration = contractVariationWeeklyWorkScheduleDuration;
    }

    public ContractVariationContractConversion getContractVariationContractConversion() {
        return contractVariationContractConversion;
    }

    public void setContractVariationContractConversion(ContractVariationContractConversion contractVariationContractConversion) {
        this.contractVariationContractConversion = contractVariationContractConversion;
    }
}
