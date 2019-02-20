package gmoldes.domain.contract.mapper;

import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract.dto.InitialContractDTO;


public class MapperContractNewVersionDTOtoInitialContractDTO {

    public static InitialContractDTO map(ContractNewVersionDTO contractNewVersionDTO) {

        return InitialContractDTO.create()
                .withId(contractNewVersionDTO.getId())
                .withContractNumber(contractNewVersionDTO.getContractNumber())
                .withVariationType(contractNewVersionDTO.getVariationType())
                .withStartDate(contractNewVersionDTO.getStartDate())
                .withExpectedEndDate(contractNewVersionDTO.getExpectedEndDate())
                .withModificationDate(contractNewVersionDTO.getModificationDate())
                .withEndingDate(contractNewVersionDTO.getEndingDate())
                .withContractJsonData(contractNewVersionDTO.getContractJsonData())
                .withContractScheduleJsonData(contractNewVersionDTO.getContractScheduleJsonData())
                .build();
    }
}
