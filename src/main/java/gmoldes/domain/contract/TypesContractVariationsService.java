package gmoldes.domain.contract;

import gmoldes.components.contract.controllers.TypesContractVariationsController;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;

public class TypesContractVariationsService {

    private TypesContractVariationsService() {
    }

    public static class TypesContractVariationServiceFactory {

        private static TypesContractVariationsService studyService;

        public static TypesContractVariationsService getInstance() {
            if(studyService == null) {
                studyService = new TypesContractVariationsService();
            }
            return studyService;
        }
    }

    public TypesContractVariationsDTO findTypeContractVariationByVariationId(Integer variationId){
        TypesContractVariationsController typesContractVariationsController = new TypesContractVariationsController();

        return typesContractVariationsController.findTypeContractVariationByVariationId(variationId);
    }
}
