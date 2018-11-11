package gmoldes.domain.contract.mapper;

import gmoldes.components.contract.initial_contract.persistence.vo.InitialContractVO;
import gmoldes.domain.contract.dto.InitialContractDTO;
import gmoldes.domain.contractjsondata.InitialContractJSONData;

import java.sql.Date;

public class MapperInitialContractDTOVO {

    public InitialContractVO mapContractDTOVO(InitialContractDTO initialContractDTO) {

        InitialContractJSONData initialContractJSONData = InitialContractJSONData.create()
                .withContractType(initialContractDTO.getContractType())
                .withClientGMId(initialContractDTO.getClientGMId())
                .withDaysOfWeekToWork(initialContractDTO.getDaysOfWeekToWork().toString())
                .withFullPartialWorkday(initialContractDTO.getFullPartialWorkday())
                .withIdentificationContractNumberINEM(initialContractDTO.getIdentificationContractNumberINEM())
                .withLaborCategory(initialContractDTO.getLaborCategory())
                .withNotesForContractManager(initialContractDTO.getNotesForManager())
                .withPrivateNotes(initialContractDTO.getPrivateNotes())
                .withQuoteAccountCode(initialContractDTO.getQuoteAccountCode())
                .withWeeklyWorkHours(initialContractDTO.getWeeklyWorkHours())
                .withWorkerId(initialContractDTO.getWorkerId())
                .build();

        InitialContractVO initialContractVO = new InitialContractVO();
        initialContractVO.setContractNumber(initialContractDTO.getContractNumber());
        initialContractVO.setVariationType(initialContractDTO.getVariationType());
        initialContractVO.setStartDate(Date.valueOf(initialContractDTO.getStartDate()));
        initialContractVO.setExpectedEndDate(Date.valueOf(initialContractDTO.getExpectedEndDate()));
        java.sql.Date endingDate = initialContractDTO.getEndingDate() == null ? null : Date.valueOf(initialContractDTO.getEndingDate());
        initialContractVO.setEndingDate(endingDate);
        initialContractVO.setInitialContractJSONData(initialContractJSONData);

        return initialContractVO;
    }
}
