package gmoldes.controllers;

import gmoldes.components.contract.new_contract.ContractData;
import gmoldes.components.contract.new_contract.ContractParts;
import gmoldes.components.contract.new_contract.ContractSchedule;
import gmoldes.utilities.Message;
import gmoldes.utilities.Parameters;
import javafx.scene.control.TabPane;

import java.time.DayOfWeek;
import java.util.Set;

public class NewContractDataVerifier {

    public NewContractDataVerifier() {
    }

    public static Boolean verifyContractParts(ContractParts contractParts, TabPane tabPane){

        if(contractParts.getSelectedEmployer() == null){
            Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT,
                    Parameters.EMPLOYER_IS_NOT_SELECTED);
            return false;
        }

        if(contractParts.getSelectedCCC() == null){
            if(!Message.confirmationMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT, Parameters.QUESTION_NULL_CCC_CODE_IS_CORRECT)){
                return false;
            }
        }

        if(contractParts.getSelectedEmployee() == null){
            Message.warningMessage(tabPane.getScene().getWindow(),Parameters.SYSTEM_INFORMATION_TEXT,
                    Parameters.EMPLOYEE_IS_NOT_SELECTED);
            return false;
        }

        return true;
    }

    public static Boolean verifyContractData(ContractData contractData, ContractSchedule contractSchedule, TabPane tabPane){

        if(contractData.getHourNotification().isEmpty()){
            Message.warningMessage(tabPane.getScene().getWindow(),Parameters.SYSTEM_INFORMATION_TEXT,
                    Parameters.HOUR_NOTIFICATION_IS_NOT_ESTABLISHED);
            return false;
        }

        if(contractData.getContractType() == null){
            Message.warningMessage(tabPane.getScene().getWindow(),Parameters.SYSTEM_INFORMATION_TEXT,
                    Parameters.CONTRACT_TYPE_NOT_SELECTED);
            return false;
        }

        if(contractData.getContractDurationDays() != null) {
            Integer contractDurationDays = Integer.parseInt(contractData.getContractDurationDays());
            if (contractDurationDays <= 0) {
                Message.warningMessage(tabPane.getScene().getWindow(), Parameters.SYSTEM_INFORMATION_TEXT,
                        Parameters.INVALID_CONTRACT_DURATION);
                return false;
            }
        }

        if(!contractData.getHoursWorkWeek().equals(contractSchedule.getHoursWorkWeek())){
            Message.warningMessage(tabPane.getScene().getWindow(),Parameters.SYSTEM_INFORMATION_TEXT,
                    Parameters.DIFFERENT_NUMBER_HOURS_CONTRACT_DATA_AND_CONTRACT_SCHEDULE);
            return false;
        }
        if(contractData.getDaysOfWeekToWork().size() == 0){
            Message.warningMessage(tabPane.getScene().getWindow(),Parameters.SYSTEM_INFORMATION_TEXT,
                    Parameters.DAYS_TO_WORK_ARE_NOT_SELECTED);
            return false;
        }
        if(contractData.getLaborCategory().length() == 0){
            Message.warningMessage(tabPane.getScene().getWindow(),Parameters.SYSTEM_INFORMATION_TEXT,
                    Parameters.LABOR_CATEGORY_IS_NOT_ESTABLISHED);
            return false;
        }

        return true;
    }

    public static Boolean verifyContractSchedule(ContractData contractData, ContractSchedule contractSchedule, TabPane tabPane){

        Set<DayOfWeek> contractDataDaysOfWeekToWork = contractData.getDaysOfWeekToWork();
        Set<DayOfWeek> contractScheduleDayOfWeekToWork = contractSchedule.getTableColumnDayOfWeekData();

       if(!contractDataDaysOfWeekToWork.equals(contractScheduleDayOfWeekToWork)){
           Message.warningMessage(tabPane.getScene().getWindow(),Parameters.SYSTEM_INFORMATION_TEXT,
                   Parameters.WORKING_DAYS_ARE_DIFFERENT_IN_CONTRACTDATA_AND_CONTRACTSCHEDULE);
           return false;
       }

        return true;
    }
}
