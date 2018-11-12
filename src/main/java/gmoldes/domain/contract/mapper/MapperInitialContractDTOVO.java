package gmoldes.domain.contract.mapper;

import gmoldes.components.contract.initial_contract.persistence.vo.InitialContractVO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contractjsondata.ContractJsonData;

import java.sql.Date;
import java.time.LocalDate;

public class MapperInitialContractDTOVO {

    public InitialContractVO mapContractDTOVO(ContractNewVersionDTO initialContractDTO) {

        ContractJsonData initialContractJSONData = ContractJsonData.create()
                .withContractType(initialContractDTO.getContractJsonData().getContractType())
                .withClientGMId(initialContractDTO.getContractJsonData().getClientGMId())
                .withDaysOfWeekToWork(initialContractDTO.getContractJsonData().getDaysOfWeekToWork())
                .withFullPartialWorkday(initialContractDTO.getContractJsonData().getFullPartialWorkday())
                .withIdentificationContractNumberINEM(initialContractDTO.getContractJsonData().getIdentificationContractNumberINEM())
                .withLaborCategory(initialContractDTO.getContractJsonData().getLaborCategory())
                .withNotesForContractManager(initialContractDTO.getContractJsonData().getNotesForContractManager())
                .withPrivateNotes(initialContractDTO.getContractJsonData().getPrivateNotes())
                .withQuoteAccountCode(initialContractDTO.getContractJsonData().getQuoteAccountCode())
                .withWeeklyWorkHours(initialContractDTO.getContractJsonData().getWeeklyWorkHours())
                .withWorkerId(initialContractDTO.getContractJsonData().getWorkerId())
                .build();

        InitialContractVO initialContractVO = new InitialContractVO();
        initialContractVO.setContractNumber(initialContractDTO.getContractNumber());
        initialContractVO.setVariationType(initialContractDTO.getVariationType());
        initialContractVO.setStartDate(initialContractDTO.getStartDate());
        initialContractVO.setExpectedEndDate(initialContractDTO.getExpectedEndDate());
        LocalDate endingDate = initialContractDTO.getEndingDate().toLocalDate();
        initialContractVO.setEndingDate(Date.valueOf(endingDate));
        initialContractVO.setContractJsonData(initialContractJSONData);

        return initialContractVO;
    }
}
