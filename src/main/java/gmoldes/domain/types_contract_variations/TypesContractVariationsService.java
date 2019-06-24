package gmoldes.domain.types_contract_variations;

import gmoldes.components.contract.controllers.TypesContractVariationsController;
import gmoldes.domain.types_contract_variations.dto.TypesContractVariationsDTO;

import java.util.List;

public class TypesContractVariationsService {

    private TypesContractVariationsService() {
    }

    public static class TypesContractVariationServiceFactory {

        private static TypesContractVariationsService typesContractVariationsService;

        public static TypesContractVariationsService getInstance() {
            if(typesContractVariationsService == null) {
                typesContractVariationsService = new TypesContractVariationsService();
            }
            return typesContractVariationsService;
        }
    }

    private TypesContractVariationsController typesContractVariationsController = new TypesContractVariationsController();

    public List<TypesContractVariationsDTO> findAllTypesContractVariations(){

        return typesContractVariationsController.findAllTypesContractVariations();
    }

    public TypesContractVariationsDTO findTypesContractVariationsById(Integer variationId){

        return typesContractVariationsController.findTypesContractVariationsById(variationId);
    }
}
