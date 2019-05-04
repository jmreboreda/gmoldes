package gmoldes.domain.traceability_contract_documentation.controllers;

import gmoldes.domain.traceability_contract_documentation.dto.TraceabilityContractDocumentationDTO;
import gmoldes.domain.traceability_contract_documentation.manager.TraceabilityContractDocumentationManager;

import java.util.List;

public class TraceabilityContractDocumentationController {

    public Integer updateTraceabilityRecord (TraceabilityContractDocumentationDTO traceabilityContractDocumentationDTO){

        TraceabilityContractDocumentationManager traceabilityManager = new TraceabilityContractDocumentationManager();

        return traceabilityManager.updateTraceabilityRecord(traceabilityContractDocumentationDTO);
    }

    public List<TraceabilityContractDocumentationDTO> findAllTraceabilityRecordByContractNumber(Integer contractNumber){

        TraceabilityContractDocumentationManager traceabilityManger = new TraceabilityContractDocumentationManager();

        return traceabilityManger.findAllTraceabilityRecordByContractNumber(contractNumber);
    }
}
