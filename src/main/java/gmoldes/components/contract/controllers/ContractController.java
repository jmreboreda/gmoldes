package gmoldes.components.contract.controllers;

import gmoldes.components.contract.manager.ContractManager;
import gmoldes.domain.contract.dto.*;
import gmoldes.domain.contract.mapper.MapperContractNewVersionDTOtoInitialContractDTO;
import gmoldes.domain.person.dto.PersonDTO;

import java.time.LocalDate;
import java.util.List;

public class ContractController {

    private ContractManager contractManager = new ContractManager();

    public Integer saveContract(ContractDTO contractDTO){

        return contractManager.saveContract(contractDTO);
    }

    public List<InitialContractDTO>findAllInitialContract(){

        return contractManager.findAllInitialContract();
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

    public List<ContractVariationDTO> findAllContractVariationsAfterDateByContractNumber(Integer contractNumber, LocalDate date){

        return contractManager.findAllContractVariationsAfterDateByContractNumber(contractNumber, date);
    }

    public List<PersonDTO> findAllEmployeesByClientId(Integer clientId){

        return contractManager.findAllEmployeesByClientId(clientId);
    }
}
