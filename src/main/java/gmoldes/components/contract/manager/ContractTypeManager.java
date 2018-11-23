package gmoldes.components.contract.manager;

import gmoldes.components.contract.new_contract.persistence.dao.ContractTypeDAO;
import gmoldes.components.contract.new_contract.persistence.vo.ContractTypeVO;
import gmoldes.domain.contract.dto.ContractTypeDTO;

import java.util.ArrayList;
import java.util.List;

public class ContractTypeManager {

    public ContractTypeManager() {
    }

    public List<ContractTypeDTO> findAllSelectableContractTypes(){
        ContractTypeDAO contractTypeDAO = ContractTypeDAO.ContractTypeDAOFactory.getInstance();
        List<ContractTypeVO> contractTypeVOList = contractTypeDAO.findAllContractTypes();

        List<ContractTypeDTO> contractTypeDTOList = new ArrayList<>();
        for(ContractTypeVO contractTypeVO : contractTypeVOList){
            ContractTypeDTO contractTypeDTO = ContractTypeDTO.create()
                    .withId(contractTypeVO.getId())
                    .withContractCode(contractTypeVO.getContractCode())
                    .withContractDescription(contractTypeVO.getContractDescription())
                    .withColloquial(contractTypeVO.getColloquial())
                    .withIsInitialContract(contractTypeVO.getIsInitialContract())
                    .withIsTemporal(contractTypeVO.getIsTemporal())
                    .withIsUndefined(contractTypeVO.getIsUndefined())
                    .withIsPartialTime(contractTypeVO.getIsPartialTime())
                    .withIsFullTime(contractTypeVO.getIsFullTime())
                    .withIsMenuSelectable(contractTypeVO.getIsMenuSelectable())
                    .build();

            contractTypeDTOList.add(contractTypeDTO);
        }

        return contractTypeDTOList;
    }

    public ContractTypeDTO findContractTypeById(Integer contractTypeId){
        ContractTypeDAO contractTypeDAO = ContractTypeDAO.ContractTypeDAOFactory.getInstance();
        ContractTypeVO contractTypeVO = contractTypeDAO.findContractTypeById(contractTypeId);

        return ContractTypeDTO.create()
                .withId(contractTypeVO.getId())
                .withContractCode(contractTypeVO.getContractCode())
                .withContractDescription(contractTypeVO.getContractDescription())
                .withColloquial(contractTypeVO.getColloquial())
                .withIsInitialContract(contractTypeVO.getIsInitialContract())
                .withIsTemporal(contractTypeVO.getIsTemporal())
                .withIsUndefined(contractTypeVO.getIsUndefined())
                .withIsPartialTime(contractTypeVO.getIsPartialTime())
                .withIsFullTime(contractTypeVO.getIsFullTime())
                .withIsMenuSelectable(contractTypeVO.getIsMenuSelectable())
                .build();
    }
}
