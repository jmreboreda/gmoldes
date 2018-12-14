package gmoldes.domain.traceability_contract_documentation.mapper;

import gmoldes.domain.traceability_contract_documentation.dto.TraceabilityContractDocumentationDTO;
import gmoldes.domain.traceability_contract_documentation.persistence.vo.TraceabilityContractDocumentationVO;

import java.sql.Date;
import java.time.LocalDate;

public class MapperTraceabilityContractDocumentationVODTO {

    public static TraceabilityContractDocumentationDTO map(TraceabilityContractDocumentationVO traceabilityVO){

        LocalDate expectedEndDate = traceabilityVO.getExpectedEndDate() != null ? traceabilityVO.getExpectedEndDate().toLocalDate() : null;
        LocalDate IDCReceptionDate = traceabilityVO.getIDCReceptionDate() != null ? traceabilityVO.getIDCReceptionDate().toLocalDate() : null;
        LocalDate dateDeliveryContractDocumentationToClient = traceabilityVO.getDateDeliveryContractDocumentationToClient() != null ? traceabilityVO.getDateDeliveryContractDocumentationToClient().toLocalDate() : null;
        LocalDate contractEndNoticeReceptionDate = traceabilityVO.getContractEndNoticeReceptionDate() != null ? traceabilityVO.getContractEndNoticeReceptionDate().toLocalDate() : null;


        TraceabilityContractDocumentationDTO traceabilityDTO = TraceabilityContractDocumentationDTO.create()
                .withId(traceabilityVO.getId())
                .withContractNumber(traceabilityVO.getContractNumber())
                .withVariationType(traceabilityVO.getVariationType())
                .withStartDate(traceabilityVO.getStartDate().toLocalDate())
                .withExpectedEndDate(expectedEndDate)
                .withIDCReceptionDate(IDCReceptionDate)
                .withDateDeliveryContractDocumentationToClient(dateDeliveryContractDocumentationToClient)
                .withContractEndNoticeReceptionDate(contractEndNoticeReceptionDate)
                .build();

        return traceabilityDTO;
    }
}
