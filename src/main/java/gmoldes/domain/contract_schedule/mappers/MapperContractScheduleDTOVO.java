package gmoldes.domain.contract_schedule.mappers;

import gmoldes.domain.contract_schedule.dto.ContractScheduleDTO;
import gmoldes.domain.contract_schedule.persistence.vo.ContractScheduleVO;

import java.sql.Date;


public class MapperContractScheduleDTOVO {

    public static ContractScheduleVO map(ContractScheduleDTO contractScheduleDTO) {

        ContractScheduleVO contractScheduleVO = new ContractScheduleVO();
        contractScheduleVO.setId(null);
        contractScheduleVO.setContractNumber(contractScheduleDTO.getContractNumber());
        contractScheduleVO.setContractScheduleJsonData(contractScheduleDTO.getContractScheduleJsonData());

        return contractScheduleVO;
    }
}
