package gmoldes.components.client_invoice_check_list.components;

import gmoldes.components.ViewLoader;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;

public class ClientInvoiceCheckListHeader extends HBox {

    private static final String CLIENT_INVOICE_CHECKLIST_HEADER_FXML = "/fxml/client_invoice_check_list/client_invoice_checklist_header.fxml";

    private Parent parent;

    public ClientInvoiceCheckListHeader() {
        this.parent = ViewLoader.load(this, CLIENT_INVOICE_CHECKLIST_HEADER_FXML);
    }

}
