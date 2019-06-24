package gmoldes.domain.contract.mapper;

import gmoldes.components.contract_variation.persistence.vo.ContractVariationVO;
import gmoldes.domain.contract.dto.ContractVariationDTO;

import java.time.LocalDate;

public class MapperContractVariationVODTO {

    public MapperContractVariationVODTO() {
    }

    public static ContractVariationDTO map (ContractVariationVO contractVariationVO){

        LocalDate expectedEndDate = contractVariationVO.getExpectedEndDate() != null ? contractVariationVO.getExpectedEndDate().toLocalDate() : null;
        LocalDate modificationDate = contractVariationVO.getModificationDate() != null ? contractVariationVO.getModificationDate().toLocalDate() : null;
        LocalDate endingDate = contractVariationVO.getEndingDate() != null ? contractVariationVO.getEndingDate().toLocalDate() : null;

        return ContractVariationDTO.create()
                .withId(contractVariationVO.getId())
                .withContractNumber(contractVariationVO.getContractNumber())
                .withVariationType(contractVariationVO.getVariationType())
                .withStartDate(contractVariationVO.getStartDate().toLocalDate())
                .withExpectedEndDate(expectedEndDate)
                .withModificationDate(modificationDate)
                .withEndingDate(endingDate)
                .withContractJsonData(contractVariationVO.getContractJsonData())
                .withContractScheduleJsonData(contractVariationVO.getContractScheduleJsonData())
                .build();
    }
}
