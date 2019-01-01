package gmoldes.domain.contract.mapper;

import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract.dto.InitialContractDTO;

import java.util.ArrayList;
import java.util.List;

public class MapperInitialContractDTOToContractNewVersionDTO {

    public static List<ContractNewVersionDTO> mapInitialContractDTOToContractNewVersionDTO(List<InitialContractDTO> initialContractDTOList){

        List<ContractNewVersionDTO> contractNewVersionDTOList = new ArrayList<>();

        for(InitialContractDTO initialContractDTO : initialContractDTOList){
            ContractNewVersionDTO contractNewVersionDTO = ContractNewVersionDTO.create()
                    .withId(initialContractDTO.getId())
                    .withContractNumber(initialContractDTO.getContractNumber())
                    .withVariationType(initialContractDTO.getVariationType())
                    .withStartDate(initialContractDTO.getStartDate())
                    .withExpectedEndDate(initialContractDTO.getExpectedEndDate())
                    .withModificationDate(initialContractDTO.getModificationDate())
                    .withEndingDate(initialContractDTO.getEndingDate())
                    .withContractJsonData(initialContractDTO.getContractJsonData())
                    .withContractScheduleJsonData(initialContractDTO.getContractScheduleJsonData())
                    .build();

            contractNewVersionDTOList.add(contractNewVersionDTO);
        }

        return contractNewVersionDTOList;
    }
}
