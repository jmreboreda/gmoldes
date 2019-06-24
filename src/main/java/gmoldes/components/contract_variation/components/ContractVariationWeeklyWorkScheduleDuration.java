package gmoldes.components.contract_variation.components;

import gmoldes.ApplicationConstants;
import gmoldes.components.ViewLoader;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.converter.LocalDateStringConverter;

import java.time.format.DateTimeFormatter;

public class ContractVariationWeeklyWorkScheduleDuration extends VBox {

    private static final String CONTRACT_VARIATION_WEEKLY_WORK_SCHEDULE_DURATION_FXML = "/fxml/contract_variations/contractvariation_weeklyworkschedulevariation_duration.fxml";

    private EventHandler<MouseEvent> eventEventHandlerContractExtension;

    private Parent parent;

    @FXML
    private Group weeklyWorkScheduleGroup;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;
    @FXML
    private TextField weeklyWorkScheduleDuration;
    @FXML
    private TextArea publicNotes;

    public ContractVariationWeeklyWorkScheduleDuration() {
        this.parent = ViewLoader.load(this, CONTRACT_VARIATION_WEEKLY_WORK_SCHEDULE_DURATION_FXML);
    }

    @FXML
    private void initialize(){

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);
        dateFrom.setConverter(new LocalDateStringConverter(dateFormatter, null));
        dateTo.setConverter(new LocalDateStringConverter(dateFormatter, null));

    }

    public Group getWeeklyWorkScheduleGroup() {
        return weeklyWorkScheduleGroup;
    }

    public void setContractExtensionGroup(Group weeklyWorkScheduleGroup) {
        this.weeklyWorkScheduleGroup = weeklyWorkScheduleGroup;
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

    public TextField getWeeklyWorkSchduleDuration() {
        return weeklyWorkScheduleDuration;
    }

    public void setContractExtensionDuration(TextField weeklyWorkScheduleDuration) {
        this.weeklyWorkScheduleDuration = weeklyWorkScheduleDuration;
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
        getWeeklyWorkSchduleDuration().setText(null);
        getPublicNotes().setText("");
    }
}
