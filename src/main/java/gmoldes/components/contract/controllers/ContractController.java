package gmoldes.components.contract.controllers;

import gmoldes.domain.contract.dto.ContractDTO;
import gmoldes.components.contract.manager.ContractManager;

import java.util.Date;
import java.util.List;

public class ContractController {

    private ContractManager contractManager = new ContractManager();

    public List<ContractDTO> findAllContractsByClientIdInPeriod(Integer clientId, Date referenceDate){

        return contractManager.findAllContractsByClientIdInPeriod(clientId, referenceDate);
    }

    public List<ContractDTO> findAllContractsWithTimeRecordByClientIdInPeriod(Integer clientId, String yearMonth){

        return contractManager.findAllContractsWithTimeRecordByClientIdInPeriod(clientId, yearMonth);
    }

    public List<ContractDTO> findAllActiveContractsByClientId(Integer id){

        return contractManager.findAllActiveContractsByClientId(id);
    }

    public int establishContractsInForce(){

        ContractManager manager = new ContractManager();
        return manager.establishContractInForce();
    }

    public int establishContractsNotInForce(){

        ContractManager manager = new ContractManager();
        return manager.establishContractNotInForce();
    }

    public List<ContractDTO> findContractsExpiration(){
        ContractManager manager = new ContractManager();
        return manager.findContractsExpiration();
    }

    public List<ContractDTO> findPendingIDC(){
        ContractManager manager = new ContractManager();
        return manager.findPendingIDC();
    }
}
