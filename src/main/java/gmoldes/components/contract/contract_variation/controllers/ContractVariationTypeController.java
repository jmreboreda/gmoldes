package gmoldes.components.contract.contract_variation.controllers;

import gmoldes.components.contract.manager.TypesContractVariationsManager;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;

import java.util.List;

public class ContractVariationTypeController {

    public ContractVariationTypeController() {
    }

    private TypesContractVariationsManager contractVariationTypeManager = new TypesContractVariationsManager();

    public List<TypesContractVariationsDTO> findAllTypesContractVariations(){

        return contractVariationTypeManager.findAllTypesContractVariations();
    }
}
