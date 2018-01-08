package gmoldes.components.timerecord;

import gmoldes.components.ViewLoader;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;

public class TimeRecordHeader extends HBox {

    private static final String TIME_RECORD_HEADER_FXML = "/fxml/time_record/timerecord_header.fxml";

    private Parent parent;

    public TimeRecordHeader() {
        this.parent = ViewLoader.load(this, TIME_RECORD_HEADER_FXML);
    }

}
