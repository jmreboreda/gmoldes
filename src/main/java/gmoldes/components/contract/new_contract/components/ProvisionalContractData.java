package gmoldes.components.contract.new_contract.components;

import gmoldes.components.ViewLoader;
import gmoldes.domain.contract.dto.ProvisionalContractDataDTO;
import gmoldes.utilities.Parameters;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

public class ProvisionalContractData extends AnchorPane {

    private static final String CONTRACT_IN_CONSTRUCTION_FXML = "/fxml/new_contract/provisional_contract_data.fxml";

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
    @FXML
    private Label status;
    @FXML
    private Label numContractText;
    @FXML
    private Label contractNumber;

    public ProvisionalContractData() {

        this.parent = ViewLoader.load(this, CONTRACT_IN_CONSTRUCTION_FXML);
        this.status.setText(ContractConstants.NOT_REVISED);
        this.status.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(status.getText().equals(ContractConstants.REVISION_WITHOUT_ERRORS)){
                    status.setStyle(Parameters.GREEN_COLOR);
                }else{
                    status.setStyle(Parameters.RED_COLOR);
                }
            }
        });

        numContractText.setStyle(Parameters.GREEN_COLOR);
        contractNumber.setStyle(Parameters.GREEN_COLOR);
    }

    public void setContractText(String text){
        this.numContractText.setText(text);
    }

    public void setContractNumber(Integer number){
        this.contractNumber.setText(number.toString());
    }

    public String getContractNumber(){
        return this.contractNumber.getText();
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
        this.status.setText(contractDataDTO.getStatus());
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
                .withStatus(this.status.getText())
                .build();
    }
}
