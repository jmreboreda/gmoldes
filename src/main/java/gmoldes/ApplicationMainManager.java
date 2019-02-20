package gmoldes;

import gmoldes.components.contract.new_contract.persistence.dao.TypesContractVariationsDAO;
import gmoldes.components.contract.new_contract.persistence.vo.TypesContractVariationsVO;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;
import gmoldes.domain.contract.mapper.MapperTypesContractVariationsVODTO;
import gmoldes.domain.traceability_contract_documentation.dto.TraceabilityContractDocumentationDTO;
import gmoldes.domain.traceability_contract_documentation.mapper.MapperTraceabilityContractDocumentationVODTO;
import gmoldes.domain.traceability_contract_documentation.persistence.dao.TraceabilityContractDocumentationDAO;
import gmoldes.domain.traceability_contract_documentation.persistence.vo.TraceabilityContractDocumentationVO;

import java.util.ArrayList;
import java.util.List;

public class ApplicationMainManager {

    public List<TraceabilityContractDocumentationDTO> findTraceabilityForAllContractWithPendingContractEndNotice(){

        List<TraceabilityContractDocumentationDTO> traceabilityContractDocumentationDTOList = new ArrayList<>();

        TraceabilityContractDocumentationDAO traceabilityContractDocumentationDAO = TraceabilityContractDocumentationDAO.TraceabilityContractDocumentationDAOFactory.getInstance();
        List<TraceabilityContractDocumentationVO> traceabilityContractDocumentationVOList = traceabilityContractDocumentationDAO.findTraceabilityForAllContractWithPendingContractEndNotice();
        for(TraceabilityContractDocumentationVO traceabilityContractDocumentationVO : traceabilityContractDocumentationVOList){
            traceabilityContractDocumentationDTOList.add(MapperTraceabilityContractDocumentationVODTO.map(traceabilityContractDocumentationVO));
        }

        return traceabilityContractDocumentationDTOList;
    }

    public List<TraceabilityContractDocumentationDTO> findTraceabilityForAllContractWithWorkingDayScheduleWithEndDate(){

        List<TraceabilityContractDocumentationDTO> traceabilityContractDocumentationDTOList = new ArrayList<>();

        TraceabilityContractDocumentationDAO traceabilityContractDocumentationDAO = TraceabilityContractDocumentationDAO.TraceabilityContractDocumentationDAOFactory.getInstance();
        List<TraceabilityContractDocumentationVO> traceabilityContractDocumentationVOList = traceabilityContractDocumentationDAO.findTraceabilityForAllContractWithWorkingDayScheduleWithEndDate();

        for(TraceabilityContractDocumentationVO traceabilityContractDocumentationVO : traceabilityContractDocumentationVOList){
            traceabilityContractDocumentationDTOList.add(MapperTraceabilityContractDocumentationVODTO.map(traceabilityContractDocumentationVO));
        }

        return traceabilityContractDocumentationDTOList;
    }

    public List<TraceabilityContractDocumentationDTO> findTraceabilityForAllContractWithPendingIDC(){

        List<TraceabilityContractDocumentationDTO> traceabilityContractDocumentationDTOList = new ArrayList<>();

        TraceabilityContractDocumentationDAO traceabilityContractDocumentationDAO = TraceabilityContractDocumentationDAO.TraceabilityContractDocumentationDAOFactory.getInstance();
        List<TraceabilityContractDocumentationVO> traceabilityContractDocumentationVOList = traceabilityContractDocumentationDAO.findTraceabilityForAllContractWithPendingIDC();
        for(TraceabilityContractDocumentationVO traceabilityContractDocumentationVO : traceabilityContractDocumentationVOList){
            traceabilityContractDocumentationDTOList.add(MapperTraceabilityContractDocumentationVODTO.map(traceabilityContractDocumentationVO));
        }

        return traceabilityContractDocumentationDTOList;
    }

    public List<TraceabilityContractDocumentationDTO> findTraceabilityForAllContractWithPendingLaborDocumentation(){
        List<TraceabilityContractDocumentationDTO> traceabilityContractDocumentationDTOList = new ArrayList<>();

        TraceabilityContractDocumentationDAO traceabilityContractDocumentationDAO = TraceabilityContractDocumentationDAO.TraceabilityContractDocumentationDAOFactory.getInstance();
        List<TraceabilityContractDocumentationVO> traceabilityContractDocumentationVOList = traceabilityContractDocumentationDAO.findTraceabilityForAllContractWithPendingLaborDocumentation();

        for(TraceabilityContractDocumentationVO traceabilityContractDocumentationVO : traceabilityContractDocumentationVOList){
            TraceabilityContractDocumentationDTO traceabilityContractDocumentationDTO = MapperTraceabilityContractDocumentationVODTO.map(traceabilityContractDocumentationVO);
            traceabilityContractDocumentationDTOList.add(traceabilityContractDocumentationDTO)
;        }

        return traceabilityContractDocumentationDTOList;
    }

    public TypesContractVariationsDTO retrieveTypesContractVariations(Integer variationType){
        TypesContractVariationsDAO typesContractVariationsDAO = TypesContractVariationsDAO.TypesContractVariationsDAOFactory.getInstance();
        TypesContractVariationsVO typesContractVariationsDTO = typesContractVariationsDAO.findTypesContractVariationsById(variationType);

        return MapperTypesContractVariationsVODTO.mapTypesContractVariationsVODTO(typesContractVariationsDTO);
    }
}
