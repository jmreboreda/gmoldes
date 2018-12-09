package gmoldes.domain.contract.mapper;

import gmoldes.components.contract.initial_contract.persistence.vo.InitialContractVO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;

import java.sql.Date;


public class MapperInitialContractDTOVO {

    public static InitialContractVO map(ContractNewVersionDTO contractNewVersionDTO) {

        Date expectedEndDate = contractNewVersionDTO.getExpectedEndDate() != null ? Date.valueOf(contractNewVersionDTO.getExpectedEndDate()) : null;
        Date modificationDate = contractNewVersionDTO.getModificationDate() != null ? Date.valueOf(contractNewVersionDTO.getModificationDate()) : null;
        Date endingDate = contractNewVersionDTO.getEndingDate() != null ? Date.valueOf(contractNewVersionDTO.getEndingDate()) : null;

        InitialContractVO initialContractVO = new InitialContractVO();
        initialContractVO.setId(contractNewVersionDTO.getId());
        initialContractVO.setContractNumber(contractNewVersionDTO.getContractNumber());
        initialContractVO.setVariationType(contractNewVersionDTO.getVariationType());
        initialContractVO.setStartDate(java.sql.Date.valueOf(contractNewVersionDTO.getStartDate()));
        initialContractVO.setExpectedEndDate(expectedEndDate);
        initialContractVO.setModificationDate(modificationDate);
        initialContractVO.setEndingDate(endingDate);
        initialContractVO.setContractJsonData(contractNewVersionDTO.getContractJsonData());

        return initialContractVO;
    }
}
