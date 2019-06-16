package gmoldes.components.contract_documentation_control.components;

import gmoldes.components.ViewLoader;
import gmoldes.domain.contract_documentation_control.ContractDocumentationControlDataDTO;
import gmoldes.utilities.TableCell.ContractDocumentationControlDateCell;
import gmoldes.utilities.TableCell.ContractDocumentationControlStringCell;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ContractDocumentationControlData extends AnchorPane {

    private static final String CONTRACT_DOCUMENTATION_CONTROL_DATA_FXML = "/fxml/contract_documentation_control/contract_documentation_control_data.fxml";

    private Parent parent;
    private Stage stage;

    @FXML
    private TableView<ContractDocumentationControlDataDTO> contractDocumentControlTable;
    @FXML
    private TableColumn<ContractDocumentationControlDataDTO, String> documentType;
    @FXML
    private TableColumn<ContractDocumentationControlDataDTO, LocalDate> receptionDate;
    @FXML
    private TableColumn<ContractDocumentationControlDataDTO, LocalDate> deliveryDate;


    public ContractDocumentationControlData() {
        this.parent = ViewLoader.load(this, CONTRACT_DOCUMENTATION_CONTROL_DATA_FXML);
    }

    @FXML
    public void initialize() {

        contractDocumentControlTable.setEditable(true);
        documentType.setEditable(false);

        receptionDate.getStyleClass().add("tableDateStyle");
        deliveryDate.getStyleClass().add("tableDateStyle");

        documentType.setCellValueFactory(new PropertyValueFactory<>("documentType"));
        receptionDate.setCellValueFactory(new PropertyValueFactory<>("receptionDate"));
        deliveryDate.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));

        documentType.setCellFactory(param -> new ContractDocumentationControlStringCell());
        receptionDate.setCellFactory(param -> new ContractDocumentationControlDateCell());
        deliveryDate.setCellFactory(param -> new ContractDocumentationControlDateCell());

        receptionDate.setOnEditCommit(this::verifyEditionNotEditableCell);
        deliveryDate.setOnEditCommit(this::verifyEditionNotEditableCell);
    }

    public TableView<ContractDocumentationControlDataDTO> getContractDocumentControlTable() {
        return contractDocumentControlTable;
    }

    public TableColumn<ContractDocumentationControlDataDTO, String> getDocumentType() {
        return documentType;
    }

    public TableColumn<ContractDocumentationControlDataDTO, LocalDate> getReceptionDate() {
        return receptionDate;
    }

    public TableColumn<ContractDocumentationControlDataDTO, LocalDate> getDeliveryDate() {
        return deliveryDate;
    }

    private void verifyEditionNotEditableCell(TableColumn.CellEditEvent event){
        TablePosition<ContractDocumentationControlDataDTO, LocalDate> badIDCDeliveryDate = new TablePosition<>(contractDocumentControlTable, 0, deliveryDate);
        TablePosition<ContractDocumentationControlDataDTO, LocalDate> badDocumentationReceptionDate = new TablePosition<>(contractDocumentControlTable, 1, receptionDate);
        TablePosition<ContractDocumentationControlDataDTO, LocalDate> badContractEndNoticeDeliveryDate = new TablePosition<>(contractDocumentControlTable, 2, deliveryDate);

        int editedRow = event.getTablePosition().getRow();
        int editedColumn = event.getTablePosition().getColumn();

        ContractDocumentationControlDataDTO selectedItem = contractDocumentControlTable.getItems().get(editedRow);

        if(editedColumn == badIDCDeliveryDate.getColumn()){
            selectedItem.setDeliveryDate(null);
        }

        if(editedColumn == badDocumentationReceptionDate.getColumn()){
            selectedItem.setReceptionDate(null);
        }

        if(editedColumn == badContractEndNoticeDeliveryDate.getColumn()){
            selectedItem.setDeliveryDate(null);
        }

        contractDocumentControlTable.setItems(contractDocumentControlTable.getItems());
    }
}
