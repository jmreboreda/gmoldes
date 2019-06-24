package gmoldes;

import gmoldes.components.contract.manager.TypesContractVariationsManager;
import gmoldes.domain.types_contract_variations.dto.TypesContractVariationsDTO;
import gmoldes.domain.traceability_contract_documentation.manager.TraceabilityContractDocumentationManager;

import java.util.List;

public class ApplicationMainController {

    private ApplicationMainManager applicationMainManager = new ApplicationMainManager();
    private TraceabilityContractDocumentationManager traceabilityManager = new TraceabilityContractDocumentationManager();

    public List<TypesContractVariationsDTO> findAllTypesContractVariations(){
        TypesContractVariationsManager manager= new TypesContractVariationsManager();

        return manager.findAllTypesContractVariations();
    }
}
