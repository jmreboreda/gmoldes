package gmoldes.components.contract.new_contract;

import gmoldes.components.ViewLoader;
import gmoldes.domain.dto.ProvisionalContractDataDTO;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class ProvisionalContractData extends AnchorPane {

    private static final String CURRENT_CONTRACT_FXML = "/fxml/new_contract/provisional_contract_data.fxml";

    private Parent parent;

    @FXML
    private Label employerName;
    @FXML
    private Label employeeName;
    @FXML
    private Label quoteAccountCode;
    @FXML
    private Label contractType;
    @FXML
    private Label dateFrom;
    @FXML
    private Label dateTo;
    @FXML
    private Label numDaysContract;
    @FXML
    private Label workDayType;
    @FXML
    private Label numberHoursPerWeek;
    @FXML
    private HBox hBoxDaysWeekToWork;
    @FXML
    private CheckBox Monday;
    @FXML
    private CheckBox Tuesday;
    @FXML
    private CheckBox Wednesday;
    @FXML
    private CheckBox Thursday;
    @FXML
    private CheckBox Friday;
    @FXML
    private CheckBox Saturday;
    @FXML
    private CheckBox Sunday;
    @FXML
    private Label laboralCategory;


    @FXML
    private ProvisionalContractData provisionalContractData;


    public ProvisionalContractData() {

        this.parent = ViewLoader.load(this, CURRENT_CONTRACT_FXML);
        Tooltip tooltip = new Tooltip("Los cambios hechos aquí directamente no se tendrán en cuenta en el contrato final.");
        this.Monday.setTooltip(tooltip);
        this.Tuesday.setTooltip(tooltip);
        this.Wednesday.setTooltip(tooltip);
        this.Thursday.setTooltip(tooltip);
        this.Friday.setTooltip(tooltip);
        this.Saturday.setTooltip(tooltip);
        this.Sunday.setTooltip(tooltip);
    }

    public void refreshData(ProvisionalContractDataDTO contractDataDTO){
        this.employerName.setText(contractDataDTO.getEmployerFullName());
        this.employeeName.setText(contractDataDTO.getEmployeeFullName());
        this.quoteAccountCode.setText(contractDataDTO.getQuoteAccountCode());
        this.contractType.setText(contractDataDTO.getContractType());
        this.dateFrom.setText(contractDataDTO.getDateFrom());
        this.dateTo.setText(contractDataDTO.getDateTo());
        this.numDaysContract.setText(contractDataDTO.getDurationDays());
        this.workDayType.setText(contractDataDTO.getWorkDayType());
        this.numberHoursPerWeek.setText(contractDataDTO.getNumberHoursPerWeek());
        this.Monday.setSelected(contractDataDTO.getDaysWeekToWork().get("Monday"));
        this.Tuesday.setSelected(contractDataDTO.getDaysWeekToWork().get("Tuesday"));
        this.Wednesday.setSelected(contractDataDTO.getDaysWeekToWork().get("Wednesday"));
        this.Thursday.setSelected(contractDataDTO.getDaysWeekToWork().get("Thursday"));
        this.Friday.setSelected(contractDataDTO.getDaysWeekToWork().get("Friday"));
        this.Saturday.setSelected(contractDataDTO.getDaysWeekToWork().get("Saturday"));
        this.Sunday.setSelected(contractDataDTO.getDaysWeekToWork().get("Sunday"));
        this.laboralCategory.setText(contractDataDTO.getLaboralCategory());
    }
}
