package gmoldes.domain.contract;

import gmoldes.components.contract.controllers.ContractController;
import gmoldes.domain.contract.dto.ContractFullDataDTO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract.dto.ContractVariationDTO;
import gmoldes.domain.contract.dto.InitialContractDTO;
import gmoldes.domain.contract.mapper.MapperContractVariationDTOtoContractNewVersionDTO;

import java.time.LocalDate;
import java.util.List;

public class ContractService {

    private ContractService() {
    }

    private static ContractController contractController = new ContractController();

    public static InitialContractDTO findInitialContractByContractNumber(Integer contractNumber){

        return contractController.findInitialContractByContractNumber(contractNumber);
    }

    public static List<ContractVariationDTO> findAllContractVariationByContractNumber(Integer contractNumber){

        return contractController.findAllContractVariationByContractNumber(contractNumber);
    }

    public static List<ContractNewVersionDTO> findHistoryOfContractByContractNumber(Integer contractNumber){

        List<ContractNewVersionDTO> contractNewVersionDTOList = contractController.findHistoryOfContractByContractNumber(contractNumber);

        InitialContractDTO initialContractDTO = contractController.findInitialContractByContractNumber(contractNumber);

        List<ContractVariationDTO> contractVariationDTOList = contractController.findAllContractVariationByContractNumber(contractNumber);

        ContractNewVersionDTO initialContractNewVersionDTO = ContractNewVersionDTO.create()
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

        contractNewVersionDTOList.add(initialContractNewVersionDTO);

        for(ContractVariationDTO contractVariationDTO : contractVariationDTOList){
            contractNewVersionDTOList.add(MapperContractVariationDTOtoContractNewVersionDTO.map(contractVariationDTO));
        }

        return contractNewVersionDTOList;
    }

    public static List<ContractNewVersionDTO> findAllContractInForceInPeriod(LocalDate initialDate, LocalDate finalDate){

        return contractController.findAllContractInForceInPeriod(initialDate, finalDate);
    }

    public static List<ContractFullDataDTO> findAllDataForContractInForceAtDateByClientId(Integer clientId, LocalDate date){

        return contractController.findAllDataForContractInForceAtDateByClientId(clientId, date);
    }
}
