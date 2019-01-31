package gmoldes.components.contract.controllers;

import gmoldes.components.contract.initial_contract.persistence.dao.InitialContractDAO;
import gmoldes.components.contract.initial_contract.persistence.vo.InitialContractVO;
import gmoldes.components.contract.manager.ContractManager;
import gmoldes.domain.contract.dto.ContractDTO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract.dto.InitialContractDTO;
import gmoldes.domain.contract.mapper.MapperInitialContractVODTO;

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

        InitialContractDAO initialContractDAO = InitialContractDAO.InitialContractDAOFactory.getInstance();
        InitialContractVO initialContractVO = initialContractDAO.findInitialContractByContractNumber(selectedContractNumber);

        return MapperInitialContractVODTO.map(initialContractVO);
    }

    public List<ContractNewVersionDTO> findHistoryOfContractByContractNumber(Integer contractNumber){

        return contractManager.findHistoryOfContractByContractNumber(contractNumber);
    }
}
