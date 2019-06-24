package gmoldes.components.consultation_contract.utilities;

import gmoldes.ApplicationConstants;
import gmoldes.domain.consultation_contract.dto.ConsultationContractDataTableDTO;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class ConsultationContractTimeCell extends TableCell<ConsultationContractDataTableDTO, LocalTime> {

    private TextField textField;

    public ConsultationContractTimeCell() {}

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
    public void updateItem(LocalTime item, boolean empty) {
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
        Pattern timePatternHHmm = Pattern.compile("\\d{2}[:]\\d{2}");
        Pattern timePatternH = Pattern.compile("\\d{1}");
        Pattern timePatternHH = Pattern.compile("\\d{2}");

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_TIME_FORMAT);
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
        textField.setOnKeyReleased(t -> {

            if (t.getCode() == KeyCode.ENTER && textField.getText() != null) {

                if(timePatternH.matcher(textField.getText()).matches() || timePatternHH.matcher(textField.getText()).matches()){
                  if(Integer.parseInt(textField.getText()) >= 0 && Integer.parseInt(textField.getText()) <= 24){

                      if(textField.getLength() == 1){
                          commitEdit(LocalTime.parse("0".concat(textField.getText()).concat(":00"), timeFormatter));
                      }else{
                          commitEdit(LocalTime.parse(textField.getText().concat(":00"), timeFormatter));
                      }
                  }else{
                      textField.setText(null);
                      commitEdit(null);
                      cancelEdit();
                  }

                }else if(timePatternHHmm.matcher(textField.getText()).matches()) {
                    try {
                        if(LocalTime.parse(textField.getText(), timeFormatter).isBefore(LocalTime.MAX)){
                            try {
                                commitEdit(LocalTime.parse(textField.getText(), timeFormatter));
                            }
                            catch(DateTimeParseException e){
                                textField.setText(null);
                                commitEdit(null);
                                cancelEdit();
                            }
                        }
                    } catch (DateTimeParseException e) {
                        textField.setText(null);
                        commitEdit(null);
                        cancelEdit();
                    }
                }
                else{
                    textField.setText(null);
                    commitEdit(null);
                    cancelEdit();
                }
            } else if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }

    private String getString() {
        return getItem() == null ? null : getItem().toString();
    }
}
