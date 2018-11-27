package gmoldes.components.contract.contract_variation.components;

import gmoldes.components.ViewLoader;
import gmoldes.domain.contract.dto.ContractFullDataDTO;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;

public class ContractVariationContractVariations extends VBox {

    private static final String CONTRACT_VARIATION_CONTRACT_DATA_FXML = "/fxml/contract_variations/contractvariation_contractvariations.fxml";

    private Parent parent;

    private EventHandler<MouseEvent> actionEventEventHandlerContractExtinction;

    @FXML
    private ToggleGroup contractVariationToggleGroup;
    @FXML
    private ContractVariationContractExtinction contractVariationContractExtinction;
    @FXML
    private ContractVariationContractExtension contractVariationContractExtension;
    @FXML
    private ContractVariationContractConversion contractVariationContractConversion;



    public ContractVariationContractVariations() {
        this.parent = ViewLoader.load(this, CONTRACT_VARIATION_CONTRACT_DATA_FXML);
    }

    @FXML
    private void initialize(){

        contractVariationContractExtinction.setOnExtinctionButton(this::onExtinctionButton);
    }

    public ContractVariationContractExtinction getContractVariationContractExtinction() {
        return contractVariationContractExtinction;
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

    public ContractVariationContractConversion getContractVariationContractConversion() {
        return contractVariationContractConversion;
    }

    public void setContractVariationContractConversion(ContractVariationContractConversion contractVariationContractConversion) {
        this.contractVariationContractConversion = contractVariationContractConversion;
    }

    private void onExtinctionButton(MouseEvent event){
        this.actionEventEventHandlerContractExtinction.handle(event);
    }

    public void setOnContractExtinction(EventHandler<MouseEvent> actionEventEventHandlerContractExtinction){
        this.actionEventEventHandlerContractExtinction = actionEventEventHandlerContractExtinction;
    }
}
