package gmoldes.components.contract.contract_variation.components;

import gmoldes.components.ViewLoader;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.Utilities;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.converter.LocalDateStringConverter;

import java.time.format.DateTimeFormatter;

public class ContractVariationContractExtension extends VBox {

    private static final String CONTRACT_VARIATION_CONTRACT_EXTENSION_FXML = "/fxml/contract_variations/contractvariation_contract_extension.fxml";

    private EventHandler<MouseEvent> eventEventHandlerContractExtension;

    private Parent parent;

    @FXML
    private Group contractExtensionGroup;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;
    @FXML
    private TextField contractExtensionDuration;
    @FXML
    private TextArea publicNotes;

    public ContractVariationContractExtension() {
        this.parent = ViewLoader.load(this, CONTRACT_VARIATION_CONTRACT_EXTENSION_FXML);
    }

    @FXML
    private void initialize(){

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parameters.DEFAULT_DATE_FORMAT);
        dateFrom.setConverter(new LocalDateStringConverter(dateFormatter, null));
        dateTo.setConverter(new LocalDateStringConverter(dateFormatter, null));

    }

    public Group getContractExtensionGroup() {
        return contractExtensionGroup;
    }

    public void setContractExtensionGroup(Group contractExtensionGoup) {
        this.contractExtensionGroup = contractExtensionGoup;
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

    public TextField getContractExtensionDuration() {
        return contractExtensionDuration;
    }

    public void setContractExtensionDuration(TextField contractExtensionDuration) {
        this.contractExtensionDuration = contractExtensionDuration;
    }

    public TextArea getPublicNotes() {
        return publicNotes;
    }

    public void setPublicNotes(TextArea publicNotes) {
        this.publicNotes = publicNotes;
    }

    public void cleanComponents(){

        getDateFrom().setValue(null);
        getDateTo().setValue(null);
        getContractExtensionDuration().setText(null);
        getPublicNotes().setText("");
    }
}
