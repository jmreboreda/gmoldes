package gmoldes.components.contract.contract_variation.components;

import gmoldes.ApplicationConstants;
import gmoldes.components.ViewLoader;
import gmoldes.components.contract.controllers.TypesContractVariationsController;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.converter.LocalDateStringConverter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ContractVariationContractExtinction extends VBox {

    private static final String CONTRACT_VARIATION_CONTRACT_EXTINCTION_FXML = "/fxml/contract_variations/contractvariation_contract_extinction.fxml";

    private Parent parent;

    @FXML
    private Group contractExtinctionGroup;
    @FXML
    private ChoiceBox<TypesContractVariationsDTO> extinctionCauseSelector;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private ToggleGroup holidaysToggleGroup;
    @FXML
    private RadioButton rbHolidaysYes;
    @FXML
    private RadioButton rbHolidaysNo;
    @FXML
    private RadioButton rbHolidaysCalculate;
    @FXML
    private TextArea publicNotes;

    public ContractVariationContractExtinction() {
        this.parent = ViewLoader.load(this, CONTRACT_VARIATION_CONTRACT_EXTINCTION_FXML);

    }

    @FXML
    private void initialize(){

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);
        dateFrom.setConverter(new LocalDateStringConverter(dateFormatter, null));

        dateFrom.disableProperty().bind(this.extinctionCauseSelector.valueProperty().isNull());
        rbHolidaysYes.disableProperty().bind(this.extinctionCauseSelector.valueProperty().isNull());
        rbHolidaysNo.disableProperty().bind(this.extinctionCauseSelector.valueProperty().isNull());
        rbHolidaysCalculate.disableProperty().bind(this.extinctionCauseSelector.valueProperty().isNull());
        publicNotes.disableProperty().bind(this.extinctionCauseSelector.valueProperty().isNull());

    }

    public ToggleGroup getHolidaysToggleGroup() {
        return holidaysToggleGroup;
    }

    public void setHolidaysToggleGroup(ToggleGroup holidaysToggleGroup) {
        this.holidaysToggleGroup = holidaysToggleGroup;
    }

    public Group getContractExtinctionGroup() {
        return contractExtinctionGroup;
    }

    public void setContractExtinctionGroup(Group contractExtinctionGroup) {
        this.contractExtinctionGroup = contractExtinctionGroup;
    }

    public ChoiceBox<TypesContractVariationsDTO> getExtinctionCauseSelector() {
        return extinctionCauseSelector;
    }

    public void setExtinctionCauseSelector(ChoiceBox<TypesContractVariationsDTO> extinctionCauseSelector) {
        this.extinctionCauseSelector = extinctionCauseSelector;
    }

    public DatePicker getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(DatePicker dateFrom) {
        this.dateFrom = dateFrom;
    }

    public RadioButton getRbHolidaysYes() {
        return rbHolidaysYes;
    }

    public void setRbHolidaysYes(RadioButton rbHolidaysYes) {
        this.rbHolidaysYes = rbHolidaysYes;
    }

    public RadioButton getRbHolidaysNo() {
        return rbHolidaysNo;
    }

    public void setRbHolidaysNo(RadioButton rbHolidaysNo) {
        this.rbHolidaysNo = rbHolidaysNo;
    }

    public RadioButton getRbHolidaysCalculate() {
        return rbHolidaysCalculate;
    }

    public void setRbHolidaysCalculate(RadioButton rbHolidaysCalculate) {
        this.rbHolidaysCalculate = rbHolidaysCalculate;
    }

    public TextArea getPublicNotes() {
        return publicNotes;
    }

    public void setPublicNotes(TextArea publicNotes) {
        this.publicNotes = publicNotes;
    }

    public void cleanComponents(){

        getExtinctionCauseSelector().getSelectionModel().select(null);
        getDateFrom().setValue(null);
        getRbHolidaysYes().setSelected(false);
        getRbHolidaysNo().setSelected(false);
        getRbHolidaysCalculate().setSelected(false);
        getPublicNotes().setText("");
    }
}
