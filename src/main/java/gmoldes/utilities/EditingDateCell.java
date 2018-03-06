package gmoldes.utilities;

import gmoldes.domain.dto.ContractScheduleDayDTO;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.regex.Pattern;

public class EditingDateCell extends TableCell<ContractScheduleDayDTO, LocalDate> {

    private TextField textField;

    public EditingDateCell() {}

    @Override
    public void startEdit() {

        super.startEdit();

        if (textField == null) {
            createTextField();
        }

        setGraphic(textField);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(String.valueOf(getItem()));
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    public void updateItem(LocalDate item, boolean empty) {
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

    private void createTextField() {
        Pattern datePattern = Pattern.compile("\\d{2}[-/]\\d{2}[-/]\\d{4}");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy").withLocale(Locale.ITALIAN);
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
        textField.setOnKeyReleased(t -> {
            if (t.getCode() == KeyCode.ENTER) {
                if(datePattern.matcher(textField.getText()).matches()){
                    String dateConverted = textField.getText().replace("/","-");
                    try {
                        commitEdit(LocalDate.parse(dateConverted, dateFormatter));
                    }
                    catch(DateTimeParseException e){
                        textField.setText("");
                        cancelEdit();
                    }
                }else{
                    cancelEdit();
                }
                //commitEdit(LocalDate.parse(textField.getText(), dateFormatter));
            } else if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}
