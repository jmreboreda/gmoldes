package gmoldes.domain.traceability_contract_documentation.mapper;

import gmoldes.domain.traceability_contract_documentation.dto.TraceabilityContractDocumentationDTO;
import gmoldes.domain.traceability_contract_documentation.persistence.vo.TraceabilityContractDocumentationVO;

import java.sql.Date;

public class MapperTraceabilityContractDocumentationDTOVO {

    public static TraceabilityContractDocumentationVO map(TraceabilityContractDocumentationDTO traceabilityDTO){

        Date expectedEndDate = traceabilityDTO.getExpectedEndDate() != null ? Date.valueOf(traceabilityDTO.getExpectedEndDate()) : null;

        TraceabilityContractDocumentationVO traceabilityVO = new TraceabilityContractDocumentationVO();
        traceabilityVO.setContractNumber(traceabilityDTO.getContractNumber());
        traceabilityVO.setVariationType(traceabilityDTO.getVariationType());
        traceabilityVO.setStartDate(Date.valueOf(traceabilityDTO.getStartDate()));
        traceabilityVO.setExpectedEndDate(expectedEndDate);

        return traceabilityVO;
    }
}
