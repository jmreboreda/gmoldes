package gmoldes;

import gmoldes.components.contract.manager.TypesContractVariationsManager;
import gmoldes.domain.contract.dto.ContractVariationDTO;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;
import gmoldes.domain.traceability_contract_documentation.manager.TraceabilityContractDocumentationManager;

import java.util.List;

public class ApplicationMainController {

    private ApplicationMainManager applicationMainManager = new ApplicationMainManager();
    private TraceabilityContractDocumentationManager traceabilityManager = new TraceabilityContractDocumentationManager();

    public List<TypesContractVariationsDTO> findAllTypesContractVariations(){
        TypesContractVariationsManager manager= new TypesContractVariationsManager();

        return manager.findAllTypesContractVariations();
    }

    public List<ContractVariationDTO> findAllContractVariationById(Integer contractVariationId){

        return applicationMainManager.findAllContractVariationById(contractVariationId);
    }

    public TypesContractVariationsDTO findTypeContractVariationById(Integer typeContractVariationId){

        return applicationMainManager.retrieveTypesContractVariations(typeContractVariationId);
    }
}
