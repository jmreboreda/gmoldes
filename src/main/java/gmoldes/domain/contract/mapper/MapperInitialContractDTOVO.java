package gmoldes.domain.contract.mapper;

import gmoldes.components.contract.initial_contract.persistence.vo.InitialContractVO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;

import java.sql.Date;


public class MapperInitialContractDTOVO {

    public static InitialContractVO map(ContractNewVersionDTO contractNewVersionDTO) {

        InitialContractVO initialContractVO = new InitialContractVO();
        initialContractVO.setId(contractNewVersionDTO.getId());
        initialContractVO.setContractNumber(contractNewVersionDTO.getContractNumber());
        initialContractVO.setVariationType(contractNewVersionDTO.getVariationType());
        initialContractVO.setStartDate(java.sql.Date.valueOf(contractNewVersionDTO.getStartDate()));
        initialContractVO.setExpectedEndDate(java.sql.Date.valueOf(contractNewVersionDTO.getExpectedEndDate()));
        Date modificationDate = contractNewVersionDTO.getModificationDate() != null ? Date.valueOf(contractNewVersionDTO.getModificationDate()) : null;
        initialContractVO.setModificationDate(modificationDate);
        Date endingDate = contractNewVersionDTO.getEndingDate() != null ? Date.valueOf(contractNewVersionDTO.getEndingDate()) : null;
        initialContractVO.setEndingDate(endingDate);
        initialContractVO.setContractJsonData(contractNewVersionDTO.getContractJsonData());

        return initialContractVO;
    }
}
