package gmoldes;

import gmoldes.components.contract.contract_variation.persistence.dao.ContractVariationDAO;
import gmoldes.components.contract.contract_variation.persistence.vo.ContractVariationVO;
import gmoldes.components.contract.new_contract.persistence.dao.TypesContractVariationsDAO;
import gmoldes.components.contract.new_contract.persistence.vo.TypesContractVariationsVO;
import gmoldes.domain.contract.dto.ContractVariationDTO;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;
import gmoldes.domain.contract.mapper.MapperContractVariationVODTO;
import gmoldes.domain.contract.mapper.MapperTypesContractVariationsVODTO;
import gmoldes.domain.traceability_contract_documentation.dto.TraceabilityContractDocumentationDTO;
import gmoldes.domain.traceability_contract_documentation.mapper.MapperTraceabilityContractDocumentationVODTO;
import gmoldes.domain.traceability_contract_documentation.persistence.dao.TraceabilityContractDocumentationDAO;
import gmoldes.domain.traceability_contract_documentation.persistence.vo.TraceabilityContractDocumentationVO;

import java.util.ArrayList;
import java.util.List;

public class ApplicationMainManager {

    public List<TraceabilityContractDocumentationDTO> findTraceabilityForAllContractWithWorkingDayScheduleWithEndDate(){

        List<TraceabilityContractDocumentationDTO> traceabilityContractDocumentationDTOList = new ArrayList<>();

        TraceabilityContractDocumentationDAO traceabilityContractDocumentationDAO = TraceabilityContractDocumentationDAO.TraceabilityContractDocumentationDAOFactory.getInstance();
        List<TraceabilityContractDocumentationVO> traceabilityContractDocumentationVOList = traceabilityContractDocumentationDAO.findTraceabilityForAllContractWithWorkingDayScheduleWithEndDate();

        for(TraceabilityContractDocumentationVO traceabilityContractDocumentationVO : traceabilityContractDocumentationVOList){
            traceabilityContractDocumentationDTOList.add(MapperTraceabilityContractDocumentationVODTO.map(traceabilityContractDocumentationVO));
        }

        return traceabilityContractDocumentationDTOList;
    }

    public TypesContractVariationsDTO retrieveTypesContractVariations(Integer variationType){
        TypesContractVariationsDAO typesContractVariationsDAO = TypesContractVariationsDAO.TypesContractVariationsDAOFactory.getInstance();
        TypesContractVariationsVO typesContractVariationsDTO = typesContractVariationsDAO.findTypesContractVariationsById(variationType);

        return MapperTypesContractVariationsVODTO.mapTypesContractVariationsVODTO(typesContractVariationsDTO);
    }

    public List<ContractVariationDTO> findAllContractVariationById(Integer contractVariationId){
        ContractVariationDAO contractVariationDAO = ContractVariationDAO.ContractVariationDAOFactory.getInstance();
        List<ContractVariationVO> contractVariationVOList = contractVariationDAO.findAllContractVariationById(contractVariationId);

        List<ContractVariationDTO> contractVariationDTOList = new ArrayList<>();
        for(ContractVariationVO contractVariationVO : contractVariationVOList){
            contractVariationDTOList.add(MapperContractVariationVODTO.map(contractVariationVO));
        }

        return contractVariationDTOList;
    }
}
