package gmoldes.components.contract.controllers;

import gmoldes.components.contract.manager.TypesContractVariationsManager;
import gmoldes.domain.types_contract_variations.dto.TypesContractVariationsDTO;

import java.util.List;

public class TypesContractVariationsController {

    private TypesContractVariationsManager typesContractVariationsManager = new TypesContractVariationsManager();


    public TypesContractVariationsController() {
    }

    public String findVariationDescriptionById(int variationId){

        return typesContractVariationsManager.findVariationDescriptionById(variationId);
    }

    public TypesContractVariationsDTO findTypesContractVariationsById(Integer typesContractVariationsId){

        return typesContractVariationsManager.findTypesContractVariationsById(typesContractVariationsId);
    }

    public List<TypesContractVariationsDTO> findAllTypesContractVariations(){

        return typesContractVariationsManager.findAllTypesContractVariations();
    }
}
