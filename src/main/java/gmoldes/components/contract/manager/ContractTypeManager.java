package gmoldes.components.contract.manager;

import gmoldes.domain.contract_type.persistence.dao.ContractTypeDAO;
import gmoldes.domain.contract_type.persistence.vo.ContractTypeVO;
import gmoldes.domain.contract_type.dto.ContractTypeDTO;

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
                    .withIsDeterminedDuration(contractTypeVO.getIsDeterminedDuration())
                    .withIsSurrogate(contractTypeVO.getSurrogate())
                    .withIsAdminPartnerSimilar(contractTypeVO.getAdminPartnerSimilar())
                    .build();

            contractTypeDTOList.add(contractTypeDTO);
        }

        return contractTypeDTOList;
    }

    public ContractTypeDTO findContractTypeByContractTypeCode(Integer contractTypeCode){
        ContractTypeDAO contractTypeDAO = ContractTypeDAO.ContractTypeDAOFactory.getInstance();
        ContractTypeVO contractTypeVO = contractTypeDAO.findContractTypeByContractTypeCode(contractTypeCode);

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
                .withIsSurrogate(contractTypeVO.getSurrogate())
                .withIsAdminPartnerSimilar(contractTypeVO.getAdminPartnerSimilar())
                .build();
    }
}
