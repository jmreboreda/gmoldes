package gmoldes.components.contract.new_contract.mapper;

import gmoldes.components.contract.new_contract.persistence.vo.ContractTypeVO;
import gmoldes.domain.contract.dto.ContractTypeDTO;

public class MapperContractTypeVODTO {

    public static ContractTypeDTO mapContractTypeVODTO(ContractTypeVO contractTypeVO){

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
                .withIsDeterminedDuration(contractTypeVO.getIsDeterminedDuration())
                .build();
    }
}
