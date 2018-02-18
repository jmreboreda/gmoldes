package gmoldes.check;

import gmoldes.controllers.ContractController;

public class InitialChecks {

    public static void UpdateCurrentContracts(){
        ContractController controller = new ContractController();

        int result = controller.establishCurrentContract();
        System.out.println("Current contract update to TRUE: " + result);
        int result1 = controller.establishNotCurrentContract();
        System.out.println("Current contract update to FALSE: " + result1);
    }
}
