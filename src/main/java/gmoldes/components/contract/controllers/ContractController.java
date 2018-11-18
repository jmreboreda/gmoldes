package gmoldes.components.contract.controllers;

import gmoldes.components.contract.manager.ContractManager;
import gmoldes.domain.contract.dto.ContractDTO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;

import java.util.List;

public class ContractController {

    private ContractManager contractManager = new ContractManager();

    public int establishContractsInForce(){

        return contractManager.establishContractInForce();
    }

    public int establishContractsNotInForce(){

        return contractManager.establishContractNotInForce();
    }

    public List<ContractDTO> findContractsExpiration(){

        return contractManager.findContractsExpiration();
    }

    public List<ContractDTO> findPendingIDC(){

        return contractManager.findPendingIDC();
    }

    public List<ContractNewVersionDTO> findAllContractsInForceNow(){

        return contractManager.findAllContractsInForceNow();
    }
}
