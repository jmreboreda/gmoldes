package gmoldes.components.contract.contract_variation.components;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract.controllers.TypesContractVariationsController;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;
import gmoldes.utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class ContractVariationContractConversion extends VBox {

    private static final String CONTRACT_VARIATION_CONTRACT_CONVERSION_FXML = "/fxml/contract_variations/contractvariation_contract_conversion.fxml";

    private Parent parent;

    @FXML
    private Group contractConversionGroup;
    @FXML
    private ChoiceBox<TypesContractVariationsDTO> contractConversionSelector;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;

    public ContractVariationContractConversion() {
        this.parent = ViewLoader.load(this, CONTRACT_VARIATION_CONTRACT_CONVERSION_FXML);

        loadContractConversionExtinctionCausesSelector();
    }

    @FXML
    private void initialize(){

        dateFrom.setConverter(Utilities.dateConverter);
        dateTo.setConverter(Utilities.dateConverter);
    }

    public Group getContractConversionGroup() {
        return contractConversionGroup;
    }

    public void setContractConversionGroup(Group contractConversionGroup) {
        this.contractConversionGroup = contractConversionGroup;
    }

    public ChoiceBox<TypesContractVariationsDTO> getContractConversionSelector() {
        return contractConversionSelector;
    }

    public void setContractConversionSelector(ChoiceBox<TypesContractVariationsDTO> contractConversionSelector) {
        this.contractConversionSelector = contractConversionSelector;
    }

    public DatePicker getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(DatePicker dateFrom) {
        this.dateFrom = dateFrom;
    }

    public DatePicker getDateTo() {
        return dateTo;
    }

    public void setDateTo(DatePicker dateTo) {
        this.dateTo = dateTo;
    }

    private void loadContractConversionExtinctionCausesSelector(){
        TypesContractVariationsController typesContractVariationsController = new TypesContractVariationsController();
        List<TypesContractVariationsDTO> typesContractVariationsDTOList = typesContractVariationsController.findAllTypesContractVariations();

        List<TypesContractVariationsDTO> typesContractVariationsExtinctionDTOList = new ArrayList<>();
        for(TypesContractVariationsDTO typesContractVariationsDTO : typesContractVariationsDTOList){
            if(typesContractVariationsDTO.getConversion()){
                typesContractVariationsExtinctionDTOList.add(typesContractVariationsDTO);
            }
        }

        ObservableList<TypesContractVariationsDTO> typesContractVariationsDTOS = FXCollections.observableArrayList(typesContractVariationsExtinctionDTOList);
        contractConversionSelector.setItems(typesContractVariationsDTOS);
    }

    public void cleanComponents(){

        getContractConversionSelector().getSelectionModel().select(null);
        getDateFrom().setValue(null);
        getDateTo().setValue(null);
    }
}
