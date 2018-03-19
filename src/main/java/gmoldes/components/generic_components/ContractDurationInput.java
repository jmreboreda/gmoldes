package gmoldes.components.generic_components;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract.events.ChangeContractDataHoursWorkWeekEvent;
import gmoldes.components.contract.events.ChangeTextInputEvent;
import gmoldes.utilities.Parameters;
import gmoldes.utilities.Utilities;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

public class ContractDurationInput extends HBox {

    private static final String CONTRACT_DURATION = "/fxml/generic_components/contract_duration.fxml";

    private Parent parent;

    @FXML
    private RadioButton radioButtonUndefined;
    @FXML
    private RadioButton radioButtonTemporal;
    @FXML
    private ToggleGroup grContractDuration;
    @FXML
    private DateInput dateFrom;
    @FXML
    private DateInput dateTo;
    @FXML
    private TextInput durationContractDays;

    public ContractDurationInput() {

        this.parent = ViewLoader.load(this, CONTRACT_DURATION);
    }

    @FXML
    private void initialize(){

        this.radioButtonUndefined.setMaxWidth(105);
        this.radioButtonUndefined.setMinHeight(25);
        this.radioButtonUndefined.setSelected(true);

        this.radioButtonTemporal.setMaxWidth(105);
        this.radioButtonTemporal.setMinHeight(25);

        this.dateFrom.setLabelText(Parameters.DATE_FROM_TEXT);
        dateFrom.setDate(LocalDate.now());
        this.dateTo.setLabelText(Parameters.DATE_TO_TEXT);
        this.dateTo.setDate(null);
        this.dateTo.setDisable(true);

        this.durationContractDays.setText(null);
        this.durationContractDays.setLabelPreferredWidth(40D);
        this.durationContractDays.setTextLabel("Días");
        this.durationContractDays.setDisable(true);

        grContractDuration.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if(grContractDuration.getSelectedToggle() == radioButtonUndefined){
                    dateTo.setDate(null);
                    dateTo.setDisable(true);
                    durationContractDays.setText(null);
                    durationContractDays.setDisable(true);
                }else{
                    dateTo.setDisable(false);
                    durationContractDays.setDisable(false);
                    dateTo.setDate(LocalDate.now());
                    dateTo.requestFocus();
                }
            }
        });

        this.dateFrom.setOnAction(this::onDateAction);
        this.dateTo.setOnAction(this::onDateAction);
    }

    private void onDateAction(ActionEvent actionEvent){
        if(dateFrom.getDate() != null && dateTo.getDate() != null) {
            Long daysOfContractDuration = ChronoUnit.DAYS.between(dateFrom.getDate(), dateTo.getDate()) + 1;
            this.durationContractDays.setText(daysOfContractDuration.toString());
        }
        else{
            this.durationContractDays.setText(null);
        }
    }

    public LocalDate getDateFrom() {
        return dateFrom.getDate();
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom.setDate(dateFrom);
    }

    public LocalDate getDateTo() {
        return dateTo.getDate();
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo.setDate(dateTo);
    }

    public Boolean radioButtonUndefinedIsSelected(){
        return this.radioButtonUndefined.isSelected();
    }

    public Boolean radioButtonTemporalIsSelected(){
        return this.radioButtonTemporal.isSelected();
    }
}
