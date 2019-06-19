package gmoldes.components.contract_documentation_control.components;

import gmoldes.ApplicationConstants;
import gmoldes.components.ViewLoader;
import gmoldes.components.contract_documentation_control.ContractDocumentationControlConstants;
import gmoldes.components.contract_documentation_control.events.CellTableChangedEvent;
import gmoldes.domain.contract_documentation_control.ContractDocumentationControlDataDTO;
import gmoldes.utilities.TableCell.ContractDocumentationControlDateCell;
import gmoldes.utilities.TableCell.ContractDocumentationControlStringCell;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ContractDocumentationControlData extends AnchorPane {

    private static final String CONTRACT_DOCUMENTATION_CONTROL_DATA_FXML = "/fxml/contract_documentation_control/contract_documentation_control_data.fxml";

    private Parent parent;
    private Stage stage;

    public Boolean cellsEdited = false;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);

    private EventHandler<CellTableChangedEvent> cellTableChangedEventEventHandler;

    @FXML
    private TextField identificationContractNumberINEM;
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

        contractDocumentControlTable.setId("contract_documentation_control_table");

        contractDocumentControlTable.setEditable(true);
        documentType.setEditable(false);

        documentType.getStyleClass().add("blue-tableTextStyle");
        receptionDate.getStyleClass().add("green-tableDateStyle");
        deliveryDate.getStyleClass().add("green-tableDateStyle");

        documentType.setCellValueFactory(new PropertyValueFactory<>("documentType"));
        receptionDate.setCellValueFactory(new PropertyValueFactory<>("receptionDate"));
        deliveryDate.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));

        documentType.setCellFactory(param -> new ContractDocumentationControlStringCell());
        receptionDate.setCellFactory(param -> new ContractDocumentationControlDateCell());
        deliveryDate.setCellFactory(param -> new ContractDocumentationControlDateCell());

        receptionDate.setStyle(ContractDocumentationControlConstants.RED_COLOR);
        deliveryDate.setStyle(ContractDocumentationControlConstants.RED_COLOR);

        identificationContractNumberINEM.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(oldValue)){
                cellsEdited = true;
                cellTableChangedEventEventHandler.handle(new CellTableChangedEvent(cellsEdited));
            }
        });

        receptionDate.setOnEditCommit(this::updateTableDataObservableList);
        deliveryDate.setOnEditCommit(this::updateTableDataObservableList);
    }

    public TableView<ContractDocumentationControlDataDTO> getContractDocumentControlTable() {
        return contractDocumentControlTable;
    }

    public TextField getIdentificationContractNumberINEM() {
        return identificationContractNumberINEM;
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

    public void refreshContractDocumentationControlTable(ObservableList<ContractDocumentationControlDataDTO> tableItemOL){
        contractDocumentControlTable.setItems(tableItemOL);
    }

    private void updateTableDataObservableList(TableColumn.CellEditEvent cellEvent) {
        cellsEdited = false;

        LocalDate initialValue = (LocalDate) cellEvent.getOldValue();

        int editedRow = cellEvent.getTablePosition().getRow();
        int editedColumn = cellEvent.getTablePosition().getColumn();

        if ((initialValue != null && cellEvent.getNewValue() != null &&
                initialValue.compareTo((LocalDate) cellEvent.getNewValue()) != 0) ||
                initialValue != null || cellEvent.getNewValue() != null) {

            ContractDocumentationControlDataDTO selectedItem = contractDocumentControlTable.getItems().get(editedRow);

            if (editedColumn == ContractDocumentationControlConstants.RECEPTION_FROM_MANAGER_COLUMN) {
                if (editedRow == ContractDocumentationControlConstants.IDC_ROW || editedRow == ContractDocumentationControlConstants.CONTRACT_END_NOTICE_ROW) {
                    selectedItem.setReceptionDate((LocalDate) cellEvent.getNewValue());
                    cellsEdited = true;
                }
            } else {
                if (editedColumn == ContractDocumentationControlConstants.DELIVERY_TO_CLIENT_COLUMN) {
                    if (editedRow == ContractDocumentationControlConstants.DELIVERY_DOCUMENTS_ROW) {
                            selectedItem.setDeliveryDate((LocalDate) cellEvent.getNewValue());
                            cellsEdited = true;
                    }
                }
            }
        }

        contractDocumentControlTable.refresh();
        cellTableChangedEventEventHandler.handle(new CellTableChangedEvent(cellsEdited));
    }

    public void setOnUpdateTableObservableList(EventHandler<CellTableChangedEvent> cellTableChangedEventEventHandler){
        this.cellTableChangedEventEventHandler = cellTableChangedEventEventHandler;

    }

//    public String noDataInNonEditableCells() {
//        Integer row = 0;
//
//        if(deliveryDate.getCellData(row) != null) {
//
//            return "Error en \"Informe de datos para la cotización (IDC)\". Fecha de entrega al cliente contiene datos: " + deliveryDate.getCellData(row).format(dateFormatter);
//        }
//
//        row++;
//        if(receptionDate.getCellData(row) != null) {
//
//            return "Error en \"Envío de la documentación al cliente para firma\". Fecha de recepción del gestor contiene datos: " + receptionDate.getCellData(row).format(dateFormatter);
//        }
//
//        row++;
//        if(deliveryDate.getCellData(row) != null) {
//
//            return "Error en \"Carta de preaviso de fin de contrato\". Fecha de entrega al cliente contiene datos: " + deliveryDate.getCellData(row).format(dateFormatter);
//        }
//
//        return null;
//        }
}
