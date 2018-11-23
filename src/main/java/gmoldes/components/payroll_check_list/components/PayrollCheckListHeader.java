package gmoldes.components.payroll_check_list.components;

import gmoldes.components.ViewLoader;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;

public class PayrollCheckListHeader extends HBox {

    private static final String PAYROLL_CHECKLIST_HEADER_FXML = "/fxml/payroll_check_list/payrollchecklist_header.fxml";

    private Parent parent;

    public PayrollCheckListHeader() {
        this.parent = ViewLoader.load(this, PAYROLL_CHECKLIST_HEADER_FXML);
    }

}
