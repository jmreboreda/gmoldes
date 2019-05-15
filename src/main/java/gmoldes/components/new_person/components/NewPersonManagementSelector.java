package gmoldes.components.new_person.components;

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

public class NewPersonManagementSelector extends VBox {

    private static final String NEW_PERSON_MANAGEMENT_SELECTOR_FXML = "/fxml/new_person/new_person_management_selector.fxml";

    private Parent parent;

    private EventHandler<ActionEvent> newPersonEventHandler;
    private EventHandler<ActionEvent> modificationPersonEventHandler;
    private EventHandler<ActionEvent> deletePersonEventHandler;

    @FXML
    private ToggleGroup personManagementGroup;
    @FXML
    private RadioButton newPerson;
    @FXML
    private RadioButton modificationPerson;
    @FXML
    private RadioButton deletePerson;

    public NewPersonManagementSelector() {
        this.parent = ViewLoader.load(this, NEW_PERSON_MANAGEMENT_SELECTOR_FXML);
    }

    @FXML
    public void initialize() {

        personManagementGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if(personManagementGroup.getSelectedToggle() == newPerson){
                   onNewPerson(new ActionEvent());
                }else if (personManagementGroup.getSelectedToggle() == modificationPerson){
                    onModificationPerson(new ActionEvent());
                }else if(personManagementGroup.getSelectedToggle() == deletePerson){
                    onDeletePerson(new ActionEvent());
                }
            }
        });
    }

    private void onNewPerson(ActionEvent event){
        newPersonEventHandler.handle(event);
    }

    private void onModificationPerson(ActionEvent event){
        modificationPersonEventHandler.handle(event);
    }

    private void onDeletePerson(ActionEvent event){
        deletePersonEventHandler.handle(event);
    }

    public void setOnNewPerson(EventHandler<ActionEvent> newPersonEventHandler){
        this.newPersonEventHandler = newPersonEventHandler;
    }

    public void setOnModificationPerson(EventHandler<ActionEvent> modificationPersonEventHandler){
        this.modificationPersonEventHandler = modificationPersonEventHandler;
    }

    public void setOnDeletePerson(EventHandler<ActionEvent> deletePersonEventHandler){
        this.deletePersonEventHandler = deletePersonEventHandler;
    }
}
