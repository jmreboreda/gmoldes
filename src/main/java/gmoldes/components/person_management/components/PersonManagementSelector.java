package gmoldes.components.person_management.components;

import gmoldes.components.ViewLoader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class PersonManagementSelector extends VBox {

    private static final String NEW_PERSON_MANAGEMENT_SELECTOR_FXML = "/fxml/person_management/person_management_selector.fxml";

    private Parent parent;

    private EventHandler<ActionEvent> selectorActionEventHandler;

    @FXML
    private ToggleGroup personManagementGroup;
    @FXML
    private RadioButton newPerson;
    @FXML
    private RadioButton modificationPerson;
    @FXML
    private RadioButton deletePerson;

    public PersonManagementSelector() {
        this.parent = ViewLoader.load(this, NEW_PERSON_MANAGEMENT_SELECTOR_FXML);
    }

    @FXML
    public void initialize() {

        personManagementGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if(personManagementGroup.getSelectedToggle() == newPerson){
                    modificationPerson.setDisable(true);
                    deletePerson.setDisable(true);
                    onSelectorAction(new ActionEvent());
                }else if (personManagementGroup.getSelectedToggle() == modificationPerson){
                    newPerson.setDisable(true);
                    deletePerson.setDisable(true);
                    onSelectorAction(new ActionEvent());
                }else if(personManagementGroup.getSelectedToggle() == deletePerson){
                    newPerson.setDisable(true);
                    modificationPerson.setDisable(true);
                    onSelectorAction(new ActionEvent());
                }
            }
        });
    }

    public RadioButton getNewPerson() {
        return newPerson;
    }

    public RadioButton getModificationPerson() {
        return modificationPerson;
    }

    public RadioButton getDeletePerson() {
        return deletePerson;
    }

    public ToggleGroup getPersonManagementGroup() {
        return personManagementGroup;
    }

    public void setInitialState(){
        getNewPerson().setSelected(false);
        getModificationPerson().setSelected(false);
        getDeletePerson().setSelected(false);
        this.setDisable(false);
    }

    private void onSelectorAction(ActionEvent event){
        selectorActionEventHandler.handle(event);
    }

    public void setOnSelectorAction(EventHandler<ActionEvent> selectorActionEventHandler){
        this.selectorActionEventHandler = selectorActionEventHandler;
    }
}
