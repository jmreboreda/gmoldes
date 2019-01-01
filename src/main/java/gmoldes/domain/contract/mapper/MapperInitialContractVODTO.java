package gmoldes.domain.contract.mapper;

import gmoldes.components.contract.initial_contract.persistence.vo.InitialContractVO;
import gmoldes.domain.contract.dto.InitialContractDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MapperInitialContractVODTO {

    public static InitialContractDTO map(InitialContractVO initialContractVO){

        LocalDate expectedEndDate = initialContractVO.getExpectedEndDate() != null ? initialContractVO.getExpectedEndDate().toLocalDate() : null;
        LocalDate modificationDate = initialContractVO.getModificationDate() != null ? initialContractVO.getModificationDate().toLocalDate() : null;
        LocalDate endingDate = initialContractVO.getEndingDate() != null ? initialContractVO.getEndingDate().toLocalDate() : null;
        return  InitialContractDTO.create()
                .withId(initialContractVO.getId())
                .withContractNumber(initialContractVO.getContractNumber())
                .withVariationType(initialContractVO.getVariationType())
                .withStartDate(initialContractVO.getStartDate().toLocalDate())
                .withExpectedEndDate(expectedEndDate)
                .withModificationDate(modificationDate)
                .withEndingDate(endingDate)
                .withContractJsonData(initialContractVO.getContractJsonData())
                .withContractScheduleJsonData(initialContractVO.getContractScheduleJsonData())
                .build();
    }
}
