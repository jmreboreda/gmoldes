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
    private CheckBox monday;
    @FXML
    private CheckBox tuesday;
    @FXML
    private CheckBox wednesday;
    @FXML
    private CheckBox thursday;
    @FXML
    private CheckBox friday;
    @FXML
    private CheckBox saturday;
    @FXML
    private CheckBox sunday;
    @FXML
    private Label laborCategory;

    private ProvisionalContractData provisionalContractData;
    private Set<DayOfWeek> daysWeekToWork;

private static final String MODIFIED_DATA_WILL_NOT_BE_SAVED = "Los cambios hechos aquí directamente no se tendrán en cuenta en el contrato final.";

    public ProvisionalContractData() {

        this.parent = ViewLoader.load(this, CURRENT_CONTRACT_FXML);
        Tooltip tooltip = new Tooltip(MODIFIED_DATA_WILL_NOT_BE_SAVED);
        this.monday.setTooltip(tooltip);
        this.tuesday.setTooltip(tooltip);
        this.wednesday.setTooltip(tooltip);
        this.thursday.setTooltip(tooltip);
        this.friday.setTooltip(tooltip);
        this.saturday.setTooltip(tooltip);
        this.sunday.setTooltip(tooltip);
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
        this.monday.setSelected(contractDataDTO.getDaysWeekToWork().contains(DayOfWeek.MONDAY));
        this.tuesday.setSelected(contractDataDTO.getDaysWeekToWork().contains(DayOfWeek.TUESDAY));
        this.wednesday.setSelected(contractDataDTO.getDaysWeekToWork().contains(DayOfWeek.WEDNESDAY));
        this.thursday.setSelected(contractDataDTO.getDaysWeekToWork().contains(DayOfWeek.THURSDAY));
        this.friday.setSelected(contractDataDTO.getDaysWeekToWork().contains(DayOfWeek.FRIDAY));
        this.saturday.setSelected(contractDataDTO.getDaysWeekToWork().contains(DayOfWeek.SATURDAY));
        this.sunday.setSelected(contractDataDTO.getDaysWeekToWork().contains(DayOfWeek.SUNDAY));
        this.laborCategory.setText(contractDataDTO.getLaborCategory());
    }

    public ProvisionalContractDataDTO getAllProvisionalContractData() {

        Set<DayOfWeek> dayOfWeekSet = new HashSet<>();
        if(this.monday.isSelected()){
            dayOfWeekSet.add(DayOfWeek.MONDAY);
        }
        if(this.tuesday.isSelected()){
            dayOfWeekSet.add(DayOfWeek.TUESDAY);
        }
        if(this.wednesday.isSelected()){
            dayOfWeekSet.add(DayOfWeek.WEDNESDAY);
        }
        if(this.thursday.isSelected()){
            dayOfWeekSet.add(DayOfWeek.THURSDAY);
        }
        if(this.friday.isSelected()){
            dayOfWeekSet.add(DayOfWeek.FRIDAY);
        }
        if(this.saturday.isSelected()){
            dayOfWeekSet.add(DayOfWeek.SATURDAY);
        }
        if(this.sunday.isSelected()){
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
                .withLaborCategory(this.laborCategory.getText())
                .build();
    }
}
