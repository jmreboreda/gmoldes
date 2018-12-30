package gmoldes.domain.contract_schedule.mappers;

import gmoldes.components.contract.initial_contract.persistence.vo.InitialContractVO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract_schedule.dto.ContractScheduleDTO;
import gmoldes.domain.contract_schedule.persistence.vo.ContractScheduleVO;

import java.sql.Date;


public class MapperContractScheduleDTOVO {

    public static ContractScheduleVO map(ContractScheduleDTO contractScheduleDTO) {

        Date expectedEndDate = contractScheduleDTO.getExpectedEndDate() != null ? Date.valueOf(contractScheduleDTO.getExpectedEndDate()) : null;
        Date modificationDate = contractScheduleDTO.getModificationDate() != null ? Date.valueOf(contractScheduleDTO.getModificationDate()) : null;
        Date endingDate = contractScheduleDTO.getEndingDate() != null ? Date.valueOf(contractScheduleDTO.getEndingDate()) : null;

        ContractScheduleVO contractScheduleVO = new ContractScheduleVO();
        contractScheduleVO.setId(null);
        contractScheduleVO.setContractNumber(contractScheduleDTO.getContractNumber());
        contractScheduleVO.setVariationType(contractScheduleDTO.getVariationType());
        contractScheduleVO.setStartDate(Date.valueOf(contractScheduleDTO.getStartDate()));
        contractScheduleVO.setExpectedEndDate(expectedEndDate);
        contractScheduleVO.setModificationDate(modificationDate);
        contractScheduleVO.setEndingDate(endingDate);
        contractScheduleVO.setContractScheduleJsonData(contractScheduleDTO.getContractScheduleJsonData());
        contractScheduleVO.setInitialContract(contractScheduleDTO.getInitialContract());
        contractScheduleVO.setVariationId(contractScheduleDTO.getVariationId());

        return contractScheduleVO;
    }
}
