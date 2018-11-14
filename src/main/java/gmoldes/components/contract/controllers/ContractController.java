package gmoldes.components.contract.controllers;

import gmoldes.components.contract.manager.ContractManager;
import gmoldes.domain.contract.dto.ContractDTO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;

import java.util.Date;
import java.util.List;

public class ContractController {

    private ContractManager contractManager = new ContractManager();

    public List<ContractDTO> findAllContractsByClientIdInPeriod(Integer clientId, Date referenceDate){

        return contractManager.findAllContractsByClientIdInPeriod(clientId, referenceDate);
    }

    public List<ContractNewVersionDTO> findAllInitialContractSorted(){

        return contractManager.findAllInitialContractSorted();
    }

    public ContractNewVersionDTO findInitialContractByContractNumber(Integer contractNumber){

        return contractManager.findInitialContractByContractNumber(contractNumber);
    }

    public List<ContractDTO> findAllContractsWithTimeRecordByClientIdInPeriod(Integer clientId, String yearMonth){

        return contractManager.findAllContractsWithTimeRecordByClientIdInPeriod(clientId, yearMonth);
    }

    public List<ContractDTO> findAllActiveContractsByClientId(Integer id){

        return contractManager.findAllActiveContractsByClientId(id);
    }

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
}
