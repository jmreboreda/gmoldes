package gmoldes.utilities.TableCell;

import gmoldes.domain.dto.ContractScheduleDayDTO;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class DurationCell extends TableCell<ContractScheduleDayDTO, Duration> {

    private TextField textField;

    public DurationCell() {}

    @Override
    public void startEdit() {
        super.startEdit();

    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
    }

    @Override
    public void updateItem(Duration item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setGraphic(textField);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            } else {
                setText(getString());
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }
    }

    private String getString() {
        Duration duration = getItem();
        if(duration == Duration.ZERO){
            return "0:00";
        }

        String durationToString = duration.toString();
        durationToString = durationToString.replace("PT","");
        durationToString = durationToString.replace("H",":");
        durationToString = durationToString.replace("M","");

        Long durationHours = duration.toHours();
        Long durationMinutes = duration.toMinutes();
        Long minutes = durationMinutes - durationHours * 60;

        if(minutes == 0 ){
            durationToString = durationToString + "00";
        }

        return getItem() == null ? "" : durationToString;
    }
}
