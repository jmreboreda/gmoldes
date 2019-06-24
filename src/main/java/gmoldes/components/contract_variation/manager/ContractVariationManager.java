package gmoldes.components.contract_variation.manager;

import gmoldes.components.contract_variation.persistence.dao.ContractVariationDAO;
import gmoldes.components.contract_variation.persistence.vo.ContractVariationVO;
import gmoldes.domain.contract.dto.ContractVariationDTO;
import gmoldes.domain.contract.mapper.MapperContractVariationVODTO;

import java.util.ArrayList;
import java.util.List;

public class ContractVariationManager {

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
