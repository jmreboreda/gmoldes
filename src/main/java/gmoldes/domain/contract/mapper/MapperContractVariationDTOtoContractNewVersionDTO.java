package gmoldes.domain.contract.mapper;

import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract.dto.ContractVariationDTO;

public class MapperContractVariationDTOtoContractNewVersionDTO {

    public static ContractNewVersionDTO map(ContractVariationDTO contractVariationDTO){

        return ContractNewVersionDTO.create()
                .withId(contractVariationDTO.getId())
                .withContractNumber(contractVariationDTO.getContractNumber())
                .withVariationType(contractVariationDTO.getVariationType())
                .withStartDate(contractVariationDTO.getStartDate())
                .withExpectedEndDate(contractVariationDTO.getExpectedEndDate())
                .withModificationDate(contractVariationDTO.getModificationDate())
                .withEndingDate(contractVariationDTO.getEndingDate())
                .withContractJsonData(contractVariationDTO.getContractJsonData())
                .withContractScheduleJsonData(contractVariationDTO.getContractScheduleJsonData())
                .build();
    }
}
