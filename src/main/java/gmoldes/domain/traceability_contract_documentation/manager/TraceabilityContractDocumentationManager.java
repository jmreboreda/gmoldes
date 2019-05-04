package gmoldes.domain.traceability_contract_documentation.manager;

import gmoldes.domain.traceability_contract_documentation.dto.TraceabilityContractDocumentationDTO;
import gmoldes.domain.traceability_contract_documentation.mapper.MapperTraceabilityContractDocumentationDTOVO;
import gmoldes.domain.traceability_contract_documentation.persistence.dao.TraceabilityContractDocumentationDAO;
import gmoldes.domain.traceability_contract_documentation.persistence.vo.TraceabilityContractDocumentationVO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TraceabilityContractDocumentationManager {

    public Integer updateTraceabilityRecord (TraceabilityContractDocumentationDTO traceabilityContractDocumentationDTO){

        TraceabilityContractDocumentationVO traceabilityContractDocumentationVO = MapperTraceabilityContractDocumentationDTOVO.map(traceabilityContractDocumentationDTO);
        TraceabilityContractDocumentationDAO traceabilityContractDocumentationDAO = TraceabilityContractDocumentationDAO.TraceabilityContractDocumentationDAOFactory.getInstance();

        return traceabilityContractDocumentationDAO.updateTraceabilityRecord(traceabilityContractDocumentationVO);
    }

    public List<TraceabilityContractDocumentationDTO> findAllTraceabilityRecordByContractNumber(Integer contractNumber){
        List<TraceabilityContractDocumentationDTO> traceabilityContractDocumentationDTOList = new ArrayList<>();

        TraceabilityContractDocumentationDAO traceabilityContractDocumentationDAO = TraceabilityContractDocumentationDAO.TraceabilityContractDocumentationDAOFactory.getInstance();

        List<TraceabilityContractDocumentationVO> traceabilityContractDocumentationVOList = traceabilityContractDocumentationDAO.findAllTraceabilityRecordByContractNumber(contractNumber);
        for(TraceabilityContractDocumentationVO traceabilityContractDocumentationVO : traceabilityContractDocumentationVOList){

            LocalDate expectedEndDate = traceabilityContractDocumentationVO.getExpectedEndDate() != null ? traceabilityContractDocumentationVO.getExpectedEndDate().toLocalDate() : null;
            LocalDate idcReceptionDate = traceabilityContractDocumentationVO.getIDCReceptionDate() != null ? traceabilityContractDocumentationVO.getIDCReceptionDate().toLocalDate() : null;
            LocalDate dateDeliveryContractDocumentationToClient = traceabilityContractDocumentationVO.getDateDeliveryContractDocumentationToClient() != null ? traceabilityContractDocumentationVO.getDateDeliveryContractDocumentationToClient().toLocalDate() : null;
            LocalDate contractEndNoticeReceptionDate = traceabilityContractDocumentationVO.getContractEndNoticeReceptionDate() != null ? traceabilityContractDocumentationVO.getContractEndNoticeReceptionDate().toLocalDate() : null;

            TraceabilityContractDocumentationDTO traceabilityContractDocumentationDTO = TraceabilityContractDocumentationDTO.create()
                    .withId(traceabilityContractDocumentationVO.getId())
                    .withContractNumber(traceabilityContractDocumentationVO.getContractNumber())
                    .withVariationType(traceabilityContractDocumentationVO.getVariationType())
                    .withStartDate(traceabilityContractDocumentationVO.getStartDate().toLocalDate())
                    .withExpectedEndDate(expectedEndDate)
                    .withIDCReceptionDate(idcReceptionDate)
                    .withDateDeliveryContractDocumentationToClient(dateDeliveryContractDocumentationToClient)
                    .withContractEndNoticeReceptionDate(contractEndNoticeReceptionDate)
                    .build();

            traceabilityContractDocumentationDTOList.add(traceabilityContractDocumentationDTO);
        }

        return traceabilityContractDocumentationDTOList;
    }
}
