package gmoldes.components.contract.new_contract;

import gmoldes.components.ViewLoader;
import gmoldes.domain.dto.ProvisionalContractDataDTO;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

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
    private Set<DayOfWeek> daysWeekToWork;
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
    private Label laborCategory;


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
        this.Monday.setSelected(contractDataDTO.getDaysWeekToWork().contains(DayOfWeek.MONDAY));
        this.Tuesday.setSelected(contractDataDTO.getDaysWeekToWork().contains(DayOfWeek.TUESDAY));
        this.Wednesday.setSelected(contractDataDTO.getDaysWeekToWork().contains(DayOfWeek.WEDNESDAY));
        this.Thursday.setSelected(contractDataDTO.getDaysWeekToWork().contains(DayOfWeek.THURSDAY));
        this.Friday.setSelected(contractDataDTO.getDaysWeekToWork().contains(DayOfWeek.FRIDAY));
        this.Saturday.setSelected(contractDataDTO.getDaysWeekToWork().contains(DayOfWeek.SATURDAY));
        this.Sunday.setSelected(contractDataDTO.getDaysWeekToWork().contains(DayOfWeek.SUNDAY));
        this.laborCategory.setText(contractDataDTO.getLaboralCategory());
    }

    public ProvisionalContractDataDTO getAllProvisionalContractData() {

        Set<DayOfWeek> dayOfWeekSet = new HashSet<>();
        if(this.Monday.isSelected()){
            dayOfWeekSet.add(DayOfWeek.MONDAY);
        }
        if(this.Tuesday.isSelected()){
            dayOfWeekSet.add(DayOfWeek.TUESDAY);
        }
        if(this.Wednesday.isSelected()){
            dayOfWeekSet.add(DayOfWeek.WEDNESDAY);
        }
        if(this.Thursday.isSelected()){
            dayOfWeekSet.add(DayOfWeek.THURSDAY);
        }
        if(this.Friday.isSelected()){
            dayOfWeekSet.add(DayOfWeek.FRIDAY);
        }
        if(this.Saturday.isSelected()){
            dayOfWeekSet.add(DayOfWeek.SATURDAY);
        }
        if(this.Sunday.isSelected()){
            dayOfWeekSet.add(DayOfWeek.SUNDAY);
        }

        return ProvisionalContractDataDTO.create()
                .withEmployerFullName(this.employerName.getText())
                .withEmployeeFullName(this.employeeName.getText())
                .withQuoteAccountCode(this.quoteAccountCode.getText())
                .withContractType(this.contractType.getText())
                .withDateFrom(this.dateFrom.getText())
                .withDateTo(this.dateTo.getText())
                .withDurationDays(this.numDaysContract.getText())
                .withWorkDayType(this.workDayType.getText())
                .withNumberHoursPerWeek(this.numberHoursPerWeek.getText())
                .withDaysWeekToWork(dayOfWeekSet)
                .withLaboralCategory(this.laborCategory.getText())
                .build();
    }
}
