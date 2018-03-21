package gmoldes.controllers;

import gmoldes.components.contract.new_contract.ContractData;
import gmoldes.components.contract.new_contract.ContractParts;
import gmoldes.utilities.Message;
import gmoldes.utilities.Parameters;
import javafx.scene.control.TabPane;

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

    public static Boolean verifyContractData(ContractData contractData, TabPane tabPane){


        return true;
    }
}
