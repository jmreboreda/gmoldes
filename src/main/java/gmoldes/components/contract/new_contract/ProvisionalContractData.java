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

import java.time.DayOfWeek;

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

private static final String MODIFIED_DATA_WILL_NOT_BE_SAVED = "Los cambios hechos aquí directamente no se tendrán en cuenta en el contrato final.";

    public ProvisionalContractData() {

        this.parent = ViewLoader.load(this, CURRENT_CONTRACT_FXML);
        Tooltip tooltip = new Tooltip(MODIFIED_DATA_WILL_NOT_BE_SAVED);
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
        this.Monday.setSelected(contractDataDTO.getDaysWeekToWork().get(DayOfWeek.MONDAY));
        this.Tuesday.setSelected(contractDataDTO.getDaysWeekToWork().get(DayOfWeek.TUESDAY));
        this.Wednesday.setSelected(contractDataDTO.getDaysWeekToWork().get(DayOfWeek.WEDNESDAY));
        this.Thursday.setSelected(contractDataDTO.getDaysWeekToWork().get(DayOfWeek.THURSDAY));
        this.Friday.setSelected(contractDataDTO.getDaysWeekToWork().get(DayOfWeek.FRIDAY));
        this.Saturday.setSelected(contractDataDTO.getDaysWeekToWork().get(DayOfWeek.SATURDAY));
        this.Sunday.setSelected(contractDataDTO.getDaysWeekToWork().get(DayOfWeek.SUNDAY));
        this.laboralCategory.setText(contractDataDTO.getLaboralCategory());
    }
}
