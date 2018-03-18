package gmoldes.components.generic_components;

import gmoldes.components.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class DaysOfWeekSelector extends HBox{

    private static final String DAYS_OF_WEEK_SELECTOR = "/fxml/generic_components/days_of_week_selector.fxml";

    private Parent parent;

    @FXML
    private CheckBox mondayCheck;
    @FXML
    private CheckBox tuesdayCheck;
    @FXML
    private CheckBox wednesdayCheck;
    @FXML
    private CheckBox thursdayCheck;
    @FXML
    private CheckBox fridayCheck;
    @FXML
    private CheckBox saturdayCheck;
    @FXML
    private CheckBox sundayCheck;

    public DaysOfWeekSelector() {

        this.parent = ViewLoader.load(this, DAYS_OF_WEEK_SELECTOR);
    }

    @FXML
    private void initialize(){

        this.mondayCheck.setText(DayOfWeek.MONDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()));
        this.tuesdayCheck.setText(DayOfWeek.TUESDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()));
        this.wednesdayCheck.setText(DayOfWeek.WEDNESDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()));
        this.thursdayCheck.setText(DayOfWeek.THURSDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()));
        this.fridayCheck.setText(DayOfWeek.FRIDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()));
        this.saturdayCheck.setText(DayOfWeek.SATURDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()));
        this.sundayCheck.setText(DayOfWeek.SUNDAY.getDisplayName(TextStyle.FULL, Locale.getDefault()));

    }

    public Set<DayOfWeek> getDaysOfWeek(){
        Set<DayOfWeek> daysWeekToWork = new HashSet<>();

        if(mondayCheck.isSelected()){
            daysWeekToWork.add(DayOfWeek.MONDAY);
        }
        if(tuesdayCheck.isSelected()){
            daysWeekToWork.add(DayOfWeek.TUESDAY);
        }
        if(wednesdayCheck.isSelected()){
            daysWeekToWork.add(DayOfWeek.WEDNESDAY);
        }
        if(thursdayCheck.isSelected()){
            daysWeekToWork.add(DayOfWeek.THURSDAY);
        }
        if(fridayCheck.isSelected()){
            daysWeekToWork.add(DayOfWeek.FRIDAY);
        }
        if(saturdayCheck.isSelected()){
            daysWeekToWork.add(DayOfWeek.SATURDAY);
        }
        if(sundayCheck.isSelected()){
            daysWeekToWork.add(DayOfWeek.SUNDAY);
        }

        return daysWeekToWork;
    }

    public void setDaysOfWeek(Set<DayOfWeek> daysOfWeekSet){

        mondayCheck.setSelected(daysOfWeekSet.contains(DayOfWeek.MONDAY));
        tuesdayCheck.setSelected(daysOfWeekSet.contains(DayOfWeek.TUESDAY));
        wednesdayCheck.setSelected(daysOfWeekSet.contains(DayOfWeek.WEDNESDAY));
        thursdayCheck.setSelected(daysOfWeekSet.contains(DayOfWeek.THURSDAY));
        fridayCheck.setSelected(daysOfWeekSet.contains(DayOfWeek.FRIDAY));
        saturdayCheck.setSelected(daysOfWeekSet.contains(DayOfWeek.SATURDAY));
        sundayCheck.setSelected(daysOfWeekSet.contains(DayOfWeek.SUNDAY));

    }
}
