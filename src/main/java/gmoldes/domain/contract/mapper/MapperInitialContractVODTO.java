package gmoldes.domain.contract.mapper;

import gmoldes.components.contract.initial_contract.persistence.vo.InitialContractVO;
import gmoldes.domain.contract.dto.InitialContractDTO;
import gmoldes.domain.contractjsondata.ContractJsonData;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapperInitialContractVODTO {

    public static List<InitialContractDTO> mapInitialContractVODTO(List<InitialContractVO> initialContractVOList){

        List<InitialContractDTO> initialContractDTOList = new ArrayList<>();

        for(InitialContractVO initialContractVO : initialContractVOList){
            LocalDate expectedEndDate = initialContractVO.getExpectedEndDate() != null ? initialContractVO.getExpectedEndDate().toLocalDate() : null;
            LocalDate modificationDate = initialContractVO.getModificationDate() != null ? initialContractVO.getModificationDate().toLocalDate() : null;
            LocalDate endingDate = initialContractVO.getEndingDate() != null ? initialContractVO.getEndingDate().toLocalDate() : null;
            InitialContractDTO initialContractDTO = InitialContractDTO.create()
                    .withId(initialContractVO.getId())
                    .withContractNumber(initialContractVO.getContractNumber())
                    .withVariationType(initialContractVO.getVariationType())
                    .withStartDate(initialContractVO.getStartDate().toLocalDate())
                    .withExpectedEndDate(expectedEndDate)
                    .withModificationDate(modificationDate)
                    .withEndingDate(endingDate)
                    .withContractJsonData(initialContractVO.getContractJsonData())
                    .build();

            initialContractDTOList.add(initialContractDTO);
        }

        return initialContractDTOList;
    }

}
