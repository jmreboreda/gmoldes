package gmoldes.components.contract_variation.controllers;

import gmoldes.components.contract_variation.manager.ContractVariationManager;
import gmoldes.domain.contract.dto.ContractVariationDTO;

import java.util.List;

public class ContractVariationController {

    public List<ContractVariationDTO> findAllContractVariationById(Integer contractVariationId){

        ContractVariationManager contractVariationManager = new ContractVariationManager();

        return contractVariationManager.findAllContractVariationById(contractVariationId);
    }
}
