package gmoldes.components.contract_documentation_control.components;

import gmoldes.ApplicationConstants;
import gmoldes.components.ViewLoader;
import gmoldes.domain.contract_documentation_control.ContractDocumentationControlDataDTO;
import gmoldes.utilities.TableCell.ContractDocumentationControlDateCell;
import gmoldes.utilities.TableCell.ContractDocumentationControlStringCell;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ContractDocumentationControlData extends AnchorPane {

    private static final String CONTRACT_DOCUMENTATION_CONTROL_DATA_FXML = "/fxml/contract_documentation_control/contract_documentation_control_data.fxml";

    private Parent parent;
    private Stage stage;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);

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

        receptionDate.getStyleClass().add("tableDateStyle");
        deliveryDate.getStyleClass().add("tableDateStyle");

        documentType.setCellValueFactory(new PropertyValueFactory<>("documentType"));
        receptionDate.setCellValueFactory(new PropertyValueFactory<>("receptionDate"));
        deliveryDate.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));

        documentType.setCellFactory(param -> new ContractDocumentationControlStringCell());
        receptionDate.setCellFactory(param -> new ContractDocumentationControlDateCell());
        deliveryDate.setCellFactory(param -> new ContractDocumentationControlDateCell());

        receptionDate.setOnEditCommit(this::updateTableData);
        deliveryDate.setOnEditCommit(this::updateTableData);

//        List<ContractDocumentationControlDataDTO> contractDocumentationControlDataDTOList = new ArrayList<>();
//        for(int i = 0; i <= 2; i++){
//            contractDocumentationControlDataDTOList.add(
//                    new ContractDocumentationControlDataDTO(null, null, LocalDate.now()));
//        }
//
//        ObservableList<ContractDocumentationControlDataDTO> data = FXCollections.observableArrayList(contractDocumentationControlDataDTOList);
//        refreshContractDocumentationControlTable(data);
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

    public void refreshContractDocumentationControlTable(ObservableList<ContractDocumentationControlDataDTO> tableItemOL){
        contractDocumentControlTable.setItems(tableItemOL);
    }

    private void updateTableData(TableColumn.CellEditEvent cellEvent){
        int editedRow = cellEvent.getTablePosition().getRow();
        int editedColumn = cellEvent.getTablePosition().getColumn();

        ContractDocumentationControlDataDTO selectedItem = contractDocumentControlTable.getItems().get(editedRow);

        if(editedColumn == 1){
            selectedItem.setReceptionDate((LocalDate) cellEvent.getNewValue());
        }

        if(editedColumn == 2){
            selectedItem.setDeliveryDate((LocalDate) cellEvent.getNewValue());
        }

        refreshContractDocumentationControlTable(contractDocumentControlTable.getItems());
    }

    public String noDataInNonEditableCells() {
        Integer row = 0;
        if(deliveryDate.getCellData(row) != null) {

            return "Error en \"Informe de datos para la cotización (IDC)\". Fecha de entrega al cliente contiene datos: " + deliveryDate.getCellData(row).format(dateFormatter);
        }

        row++;
        if(receptionDate.getCellData(row) != null) {

            return "Error en \"Envío de la documentación al cliente para firma\". Fecha de recepción del gestor contiene datos: " + receptionDate.getCellData(row).format(dateFormatter);
        }

        row++;
        if(deliveryDate.getCellData(row) != null) {

            return "Error en \"Carta de preaviso de fin de contrato\". Fecha de entrega al cliente contiene datos: " + deliveryDate.getCellData(row).format(dateFormatter);
        }

        return null;
        }
}
