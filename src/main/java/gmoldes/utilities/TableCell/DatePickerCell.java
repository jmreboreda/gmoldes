package gmoldes.utilities.TableCell;

import gmoldes.ApplicationConstants;
import gmoldes.domain.contract.dto.ContractScheduleDayDTO;
import gmoldes.utilities.Parameters;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DatePickerCell<S, T> extends TableCell<ContractScheduleDayDTO, LocalDate> {

    private DatePicker datePicker;
    private ObservableList<ContractScheduleDayDTO> date;

    public DatePickerCell(ObservableList<ContractScheduleDayDTO> date) {

        super();

        this.date = date;

        if (datePicker == null) {
            createDatePicker();
        }
        setGraphic(datePicker);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                datePicker.requestFocus();
            }
        });
    }

    @Override
    public void updateItem(LocalDate item, boolean empty) {

        super.updateItem(item, empty);

        DateTimeFormatter smp = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);

        if (null == this.datePicker) {
            System.out.println("datePicker is NULL");
        }

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {

            if (isEditing()) {
                setContentDisplay(ContentDisplay.TEXT_ONLY);

            } else {
                setDatePickerDate(smp.format(item));
                setText(smp.format(item));
                setGraphic(this.datePicker);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }
        }
    }

    private void setDatePickerDate(String dateAsStr) {

        LocalDate ld = null;
        int day, month, year;

        day = month = year = 0;
        try {
            day = Integer.parseInt(dateAsStr.substring(0, 2));
            month = Integer.parseInt(dateAsStr.substring(3, 5));
            year = Integer.parseInt(dateAsStr.substring(6, dateAsStr.length()));
        } catch (NumberFormatException e) {
            System.out.println("setDate / unexpected error " + e);
        }

        ld = LocalDate.of(year, month, day);
        datePicker.setValue(ld);
    }

    private void createDatePicker() {
        this.datePicker = new DatePicker();
        datePicker.setPromptText("jj/mm/aaaa");
        datePicker.setEditable(true);

        datePicker.setOnAction(this::setDate);

        setAlignment(Pos.CENTER);
    }

    @Override
    public void startEdit() {
        super.startEdit();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    public ObservableList<ContractScheduleDayDTO> getDatePickerData() {
        return date;
    }

    public void setBirthdayData(ObservableList<ContractScheduleDayDTO> date) {
        this.date = date;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public void setDatePicker(DatePicker datePicker) {
        this.datePicker = datePicker;
    }

    private void setDate(Event event){
        LocalDate date = datePicker.getValue();
        int index = getIndex();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);
        setText(dateFormatter.format(date));
        commitEdit(date);

        if (null != getDatePickerData()) {
            getDatePickerData().get(index).setDate(date);
        }
    }
}