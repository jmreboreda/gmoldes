package gmoldes.check;

import gmoldes.controllers.ContractController;

import gmoldes.domain.dto.ContractDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InitialChecks {

    private static final Logger logger = LoggerFactory.getLogger(InitialChecks.class.getSimpleName());

    public static void UpdateCurrentContracts(){
        ContractController controller = new ContractController();

        int result = controller.establishCurrentContracts();
        logger.info("Current contract update to TRUE: " + result);
        int result1 = controller.establishNotCurrentContracts();
        logger.info("Current contract update to FALSE: " + result1);
    }

    public static List<ContractDTO> contractExpirationControl(){
        ContractController controller = new ContractController();

        return controller.findContractsExpiration();
    }
}
