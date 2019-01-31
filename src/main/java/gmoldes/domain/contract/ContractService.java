package gmoldes.domain.contract;

import gmoldes.components.contract.controllers.ContractController;
import gmoldes.domain.contract.dto.InitialContractDTO;

public class ContractService {

    public ContractService() {
    }

    private static ContractController contractController = new ContractController();

    public static InitialContractDTO findInitialContractByContractNumber(Integer contractNumber){

        return contractController.findInitialContractByContractNumber(contractNumber);
    }
}
