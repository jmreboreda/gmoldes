package gmoldes.domain.contract.mapper;

import gmoldes.components.contract.initial_contract.persistence.vo.InitialContractVO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import java.sql.Date;
import java.time.LocalDate;

public class MapperInitialContractDTOVO {

    public InitialContractVO mapContractDTOVO(ContractNewVersionDTO contractNewVersionDTO) {

        InitialContractVO initialContractVO = new InitialContractVO();
        initialContractVO.setContractNumber(contractNewVersionDTO.getContractNumber());
        initialContractVO.setVariationType(contractNewVersionDTO.getVariationType());
        initialContractVO.setStartDate(contractNewVersionDTO.getStartDate());
        initialContractVO.setExpectedEndDate(contractNewVersionDTO.getExpectedEndDate());
        LocalDate endingDate = contractNewVersionDTO.getEndingDate().toLocalDate();
        initialContractVO.setEndingDate(Date.valueOf(endingDate));
        initialContractVO.setContractJsonData(contractNewVersionDTO.getContractJsonData());

        return initialContractVO;
    }
}
