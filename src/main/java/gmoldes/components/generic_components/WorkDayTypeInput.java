package gmoldes.components.generic_components;

import gmoldes.components.ViewLoader;
import gmoldes.components.contract.events.ChangeContractDataHoursWorkWeekEvent;
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
import java.util.regex.Pattern;

public class WorkDayTypeInput extends HBox {

    private static final String WORK_DAY_TYPE = "/fxml/generic_components/work_day_type.fxml";
    private EventHandler<ChangeContractDataHoursWorkWeekEvent> changeContractDataHoursWorkWeekEventHandler;


    private Parent parent;

    @FXML
    private RadioButton radioButtonFullWorkDay;
    @FXML
    private RadioButton radioButtonPartialWorkDay;
    @FXML
    private ToggleGroup grWorkDay;
    @FXML
    private TextInput hoursWorkWeek;

    public WorkDayTypeInput() {

        this.parent = ViewLoader.load(this, WORK_DAY_TYPE);
    }

    @FXML
    private void initialize(){

        this.radioButtonFullWorkDay.setMaxWidth(180);
        this.radioButtonFullWorkDay.setMinHeight(25);
        this.radioButtonFullWorkDay.setSelected(true);

        this.radioButtonPartialWorkDay.setMaxWidth(180);
        this.radioButtonPartialWorkDay.setMinHeight(25);

        this.hoursWorkWeek.setText(null);
        this.hoursWorkWeek.setTextLabel(Parameters.HOURS_WORK_WEEK_TEXT);
        this.hoursWorkWeek.setLabelPreferredWidth(215D);
        this.hoursWorkWeek.setInputMinWidth(75D);
        this.hoursWorkWeek.setDisable(true);
        this.hoursWorkWeek.setOnAction(this::onChangeDurationContractDays);


        grWorkDay.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if(grWorkDay.getSelectedToggle() == radioButtonFullWorkDay){
                    hoursWorkWeek.setText(Utilities.converterDurationToTimeString(Parameters.LEGAL_MAXIMUM_HOURS_OF_WORK_PER_WEEK));
                    hoursWorkWeek.setDisable(true);
                }else{
                    hoursWorkWeek.setDisable(false);
                    hoursWorkWeek.setText("00:00");
                    hoursWorkWeek.inputRequestFocus();
                }
            }
        });
    }

    private void onChangeDurationContractDays(ActionEvent actionEvent){
        if(!verifyHoursWorkWeekChangeIsValid()){

            return;

        }else {
            Duration duration = Utilities.converterTimeStringToDuration(hoursWorkWeek.getText());
            ChangeContractDataHoursWorkWeekEvent changeContractDataHoursWorkWeekEvent = new ChangeContractDataHoursWorkWeekEvent(duration);
            this.changeContractDataHoursWorkWeekEventHandler.handle(changeContractDataHoursWorkWeekEvent);
        }
    }

    public String getHoursWorkWeek() {
        return hoursWorkWeek.getText();
    }

    public void setHoursWorkWeek(String hoursWorkWeek) {
        this.hoursWorkWeek.setText(hoursWorkWeek);
    }

    public void setRadioButtonFullWorkDay(Boolean bol){
        this.radioButtonFullWorkDay.setSelected(bol);
    }

    public void setRadioButtonPartialWorkDay(Boolean bol){
        this.radioButtonPartialWorkDay.setSelected(bol);
    }

    public Boolean radioButtonFullWorkDayIsSelected(){
        return this.radioButtonFullWorkDay.isSelected();
    }

    public Boolean radioButtonPartialWorkDayIsSelected(){
        return this.radioButtonPartialWorkDay.isSelected();
    }

    private Boolean verifyHoursWorkWeekChangeIsValid(){
        Pattern timePattern = Pattern.compile("\\d{2}[:]\\d{2}");
        if(!timePattern.matcher(hoursWorkWeek.getText()).matches()) {
            this.hoursWorkWeek.setText("00:00");

            return false;
        }

        String hoursWorkPerWeek = hoursWorkWeek.getText();
        if((Utilities.converterTimeStringToDuration(hoursWorkPerWeek) == null)) {
            hoursWorkWeek.setText("00:00");

            return false;
        }

        Duration durationEnteredByUser = Utilities.converterTimeStringToDuration(hoursWorkPerWeek);
        assert durationEnteredByUser != null;
        if(durationEnteredByUser.compareTo(Parameters.LEGAL_MAXIMUM_HOURS_OF_WORK_PER_WEEK ) > 0 ){
            hoursWorkWeek.setText("00:00");

            return false;
        }

        return true;
    }

    public void setOnChangeContractDataHoursWorkWeek(EventHandler<ChangeContractDataHoursWorkWeekEvent> handler){
        this.changeContractDataHoursWorkWeekEventHandler = handler;
    }
}
