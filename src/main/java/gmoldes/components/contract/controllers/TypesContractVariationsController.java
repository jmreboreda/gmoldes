package gmoldes.components.contract.controllers;

import gmoldes.components.contract.manager.TypesContractVariationsManager;

public class TypesContractVariationsController {

    public TypesContractVariationsController() {
    }

    public String findVariationDescriptionById(int variationId){
        TypesContractVariationsManager manager = new TypesContractVariationsManager();

        return manager.findVariationDescriptionById(variationId);
    }
}
