package gmoldes;

import gmoldes.components.contract.manager.TypesContractVariationsManager;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;
import gmoldes.domain.traceability_contract_documentation.dto.TraceabilityContractDocumentationDTO;

import java.util.List;

public class ApplicationMainController {

    private ApplicationMainManager applicationMainManager = new ApplicationMainManager();

    public List<TypesContractVariationsDTO> findAllTypesContractVariations(){
        TypesContractVariationsManager manager= new TypesContractVariationsManager();

        return manager.findAllTypesContractVariations();
    }


    public TypesContractVariationsDTO findTypeContractVariationById(Integer typeContractVariationId){

        return applicationMainManager.retrieveTypesContractVariations(typeContractVariationId);
    }

    public List<TraceabilityContractDocumentationDTO> findTraceabilityForAllContractWithPendingContractEndNotice(){

        return applicationMainManager.findTraceabilityForAllContractWithPendingContractEndNotice();
    }

    public List<TraceabilityContractDocumentationDTO> findTraceabilityForAllContractWithWorkingDayScheduleWithEndDate(){
        return applicationMainManager.findTraceabilityForAllContractWithWorkingDayScheduleWithEndDate();
    }

    public List<TraceabilityContractDocumentationDTO> findTraceabilityForAllContractWithPendingIDC(){

        return applicationMainManager.findTraceabilityForAllContractWithPendingIDC();
    }

    public List<TraceabilityContractDocumentationDTO> findTraceabilityForAllContractWithPendingLaborDocumentation(){

        return applicationMainManager.findTraceabilityForAllContractWithPendingLaborDocumentation();

    }

}
