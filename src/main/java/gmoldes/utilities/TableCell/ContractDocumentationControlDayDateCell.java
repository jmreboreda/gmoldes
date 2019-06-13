package gmoldes.utilities.TableCell;

import gmoldes.ApplicationConstants;
import gmoldes.domain.contract_documentation_control.ContractDocumentationControlDataDTO;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class ContractDocumentationControlDayDateCell extends TableCell<ContractDocumentationControlDataDTO, LocalDate> {

    private TextField textField;

    public ContractDocumentationControlDayDateCell() {}

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

        if(getItem() != null ) {
            setText(String.valueOf(getItem()));
        }
        else{
            setText(null);
        }
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
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
        textField.setOnKeyPressed(t -> {
            if (t.getCode() == KeyCode.ENTER) {
                if(datePattern.matcher(textField.getText()).matches()){
                    String dateConverted = textField.getText().replace("/","-");
                    try {
                        commitEdit(LocalDate.parse(dateConverted, dateFormatter));
                    }
                    catch(DateTimeParseException e){
                        textField.setText(null);
                        cancelEdit();
                    }
                }else{
                    textField.setText(null);
                    commitEdit(null);
                    cancelEdit();
                }
            } else if (t.getCode() == KeyCode.ESCAPE) {
                textField.setText(null);
                cancelEdit();
            }
        });
    }

    private String getString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);

        return getItem() == null ? null : dateFormatter.format(getItem());
    }
}
