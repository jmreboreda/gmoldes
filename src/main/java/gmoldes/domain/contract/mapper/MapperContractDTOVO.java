package gmoldes.domain.contract.mapper;

import gmoldes.domain.contract.dto.ContractDTO;
import gmoldes.domain.contract.persistence.vo.ContractVO;

import java.sql.Date;


public class MapperContractDTOVO {

    public static ContractVO map(ContractDTO contractDTO) {

        Date expectedEndDate = contractDTO.getExpectedEndDate() != null ? Date.valueOf(contractDTO.getExpectedEndDate()) : null;
        Date modificationDate = contractDTO.getModificationDate() != null ? Date.valueOf(contractDTO.getModificationDate()) : null;
        Date endingDate = contractDTO.getEndingDate() != null ? Date.valueOf(contractDTO.getEndingDate()) : null;

        ContractVO contractVO = new ContractVO();
        contractVO.setId(null);
        contractVO.setGmContractNumber(contractDTO.getGmContractNumber());
        contractVO.setVariationType(contractDTO.getVariationType());
        contractVO.setStartDate(Date.valueOf(contractDTO.getStartDate()));
        contractVO.setExpectedEndDate(expectedEndDate);
        contractVO.setModificationDate(modificationDate);
        contractVO.setEndingDate(endingDate);
        contractVO.setContractScheduleJsonData(contractDTO.getContractScheduleJsonData());

        return contractVO;
    }
}
