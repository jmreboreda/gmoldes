package gmoldes.components.contract.controllers;

import gmoldes.components.contract.manager.ContractManager;
import gmoldes.domain.contract.dto.*;
import gmoldes.domain.contract.mapper.MapperContractNewVersionDTOtoInitialContractDTO;

import java.time.LocalDate;
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

    public InitialContractDTO findInitialContractByContractNumber(Integer selectedContractNumber){

        ContractNewVersionDTO initialContractDTO = contractManager.findInitialContractByContractNumber(selectedContractNumber);

        return MapperContractNewVersionDTOtoInitialContractDTO.map(initialContractDTO);
    }

    public List<ContractNewVersionDTO> findHistoryOfContractByContractNumber(Integer contractNumber){

        return contractManager.findHistoryOfContractByContractNumber(contractNumber);
    }

    public List<ContractFullDataDTO> findAllDataForContractInForceAtDateByClientId(Integer clientId, LocalDate date){

        return contractManager.findAllDataForContractInForceAtDateByClientId(clientId, date);
    }

    public List<ContractNewVersionDTO> findAllContractInForceInPeriod(LocalDate initialDate, LocalDate finalDate){

        return contractManager.findAllContractInForceInPeriod(initialDate, finalDate);
    }

    public List<ContractVariationDTO> findAllContractVariationByContractNumber(Integer contractNumber){

        return contractManager.findAllContractVariationByContractNumber(contractNumber);

    }
}
