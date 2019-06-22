package gmoldes.components.contract.consultation_contract.components;

import gmoldes.ApplicationConstants;
import gmoldes.components.ViewLoader;
import gmoldes.components.contract.consultation_contract.utilities.ConsultationContractDayDateCell;
import gmoldes.components.contract.consultation_contract.utilities.ConsultationContractDurationCell;
import gmoldes.components.contract_documentation_control.events.CellTableChangedEvent;
import gmoldes.domain.consultation_contract.dto.ConsultationContractDataTableDTO;
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

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConsultationContractData extends AnchorPane {

    private static final String CONSULTATION_CONTRACT_DATA_FXML = "/fxml/consultation_contract/consultation_contract_data.fxml";

    private Parent parent;
    private Stage stage;

    public Boolean cellsEdited = false;
    public String previousIdentificationNumberINEMFromDatabase;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);

    private EventHandler<CellTableChangedEvent> cellTableChangedEventEventHandler;

    @FXML
    private TextField identificationContractNumberINEM;
    @FXML
    private TableView<ConsultationContractDataTableDTO> consultationContractDataTableDTO;
    @FXML
    private TableColumn<ConsultationContractDataTableDTO, Integer> variationTypeCode;
    @FXML
    private TableColumn<ConsultationContractDataTableDTO, String> variationTypeDescription;
    @FXML
    private TableColumn<ConsultationContractDataTableDTO, LocalDate> startDate;
    @FXML
    private TableColumn<ConsultationContractDataTableDTO, LocalDate> expectedEndDate;
    @FXML
    private TableColumn<ConsultationContractDataTableDTO, LocalDate> modificationDate;
    @FXML
    private TableColumn<ConsultationContractDataTableDTO, LocalDate> endingDate;
    @FXML
    private TableColumn<ConsultationContractDataTableDTO, Duration> weeklyWorkHours;

    public ConsultationContractData() {
        this.parent = ViewLoader.load(this, CONSULTATION_CONTRACT_DATA_FXML);
    }

    @FXML
    public void initialize() {

        consultationContractDataTableDTO.setId("consultation_contract_table");

        variationTypeCode.getStyleClass().add("blue-tableTextStyle");
        variationTypeDescription.getStyleClass().add("blue-tableTextStyle");

        startDate.getStyleClass().add("green-tableDateStyle");
        expectedEndDate.getStyleClass().add("green-tableDateStyle");
        modificationDate.getStyleClass().add("green-tableDateStyle");
        endingDate.getStyleClass().add("green-tableDateStyle");
        weeklyWorkHours.getStyleClass().add("green-tableDateStyle");



        variationTypeCode.setCellValueFactory(new PropertyValueFactory<>("variationTypeCode"));
        variationTypeDescription.setCellValueFactory(new PropertyValueFactory<>("variationTypeDescription"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        expectedEndDate.setCellValueFactory(new PropertyValueFactory<>("expectedEndDate"));
        modificationDate.setCellValueFactory(new PropertyValueFactory<>("modificationDate"));
        endingDate.setCellValueFactory(new PropertyValueFactory<>("endingDate"));
        weeklyWorkHours.setCellValueFactory((new PropertyValueFactory<>("weeklyWorkHours")));

//        variationTypeCode.setCellFactory(param -> new ConsultationContractStringCell());
//        variationTypeDescription.setCellFactory(param -> new ConsultationContractStringCell());
        startDate.setCellFactory(param -> new ConsultationContractDayDateCell());
        expectedEndDate.setCellFactory(param -> new ConsultationContractDayDateCell());
        modificationDate.setCellFactory(param -> new ConsultationContractDayDateCell());
        endingDate.setCellFactory(param -> new ConsultationContractDayDateCell());
        weeklyWorkHours.setCellFactory(param -> new ConsultationContractDurationCell());

//        receptionDate.setStyle(ContractDocumentationControlConstants.RED_COLOR);
//        deliveryDate.setStyle(ContractDocumentationControlConstants.RED_COLOR);

    }

    public TableView<ConsultationContractDataTableDTO> getConsultationContractDataTableDTOTable() {
        return consultationContractDataTableDTO;
    }

    public TextField getIdentificationContractNumberINEM() {
        return identificationContractNumberINEM;
    }

    public TableColumn<ConsultationContractDataTableDTO, Integer> getVariationTypeCode() {
        return variationTypeCode;
    }

    public TableColumn<ConsultationContractDataTableDTO, String> getVariationTypeDescription() {
        return variationTypeDescription;
    }

    public TableColumn<ConsultationContractDataTableDTO, LocalDate> getStartDate() {
        return startDate;
    }

    public TableColumn<ConsultationContractDataTableDTO, LocalDate> getExpectedEndDate() {
        return expectedEndDate;
    }

    public TableColumn<ConsultationContractDataTableDTO, LocalDate> getModificationDate() {
        return modificationDate;
    }

    public TableColumn<ConsultationContractDataTableDTO, LocalDate> getEndingDate() {
        return endingDate;
    }

    public TableColumn<ConsultationContractDataTableDTO, Duration> getWeeklyWorkHours() {
        return weeklyWorkHours;
    }

    public void refreshContractDocumentationControlTable(ObservableList<ConsultationContractDataTableDTO> tableItemOL){
        consultationContractDataTableDTO.setItems(tableItemOL);
    }
}
