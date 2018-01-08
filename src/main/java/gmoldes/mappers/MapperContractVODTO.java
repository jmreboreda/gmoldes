package gmoldes.mappers;

import gmoldes.domain.dto.ClientDTO;
import gmoldes.domain.dto.ContractDTO;
import gmoldes.persistence.vo.ClientVO;
import gmoldes.persistence.vo.ContractVO;

public class MapperContractVODTO {

    public ContractDTO mapContractVODTO(ContractVO contractVO) {

            ContractDTO contractDTO = ContractDTO.create()
                    .withId(contractVO.getId())
                    .withIdcliente_gm(contractVO.getIdcliente_gm())
                    .withClientegm_name(contractVO.getClientegm_name())
                    .build();
        return contractDTO;
    }
}
