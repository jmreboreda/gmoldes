package gmoldes.domain.contract;

import gmoldes.components.contract.controllers.ContractController;
import gmoldes.domain.contract.dto.ContractFullDataDTO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract.dto.ContractVariationDTO;
import gmoldes.domain.contract.dto.InitialContractDTO;
import gmoldes.domain.contract.mapper.MapperContractVariationDTOtoContractNewVersionDTO;
import gmoldes.domain.person.dto.PersonDTO;

import java.time.LocalDate;
import java.util.List;

public class ContractService {

    private ContractService() {
    }

    public static class ContractServiceFactory {

        private static ContractService contractService;

        public static ContractService getInstance() {
            if(contractService == null) {
                contractService = new ContractService();
            }

            return contractService;
        }
    }

    private static ContractController contractController = new ContractController();

    public List<InitialContractDTO> findAllInitialContract(){

        return contractController.findAllInitialContract();
    }

    public InitialContractDTO findInitialContractByContractNumber(Integer contractNumber){

        return contractController.findInitialContractByContractNumber(contractNumber);
    }

    public List<ContractVariationDTO> findAllContractVariationByContractNumber(Integer contractNumber){

        return contractController.findAllContractVariationByContractNumber(contractNumber);
    }

    public List<ContractNewVersionDTO> findHistoryOfContractByContractNumber(Integer contractNumber){

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

    public List<ContractNewVersionDTO> findAllContractInForceInPeriod(LocalDate initialDate, LocalDate finalDate){

        return contractController.findAllContractInForceInPeriod(initialDate, finalDate);
    }

    public List<ContractFullDataDTO> findAllDataForContractInForceAtDateByClientId(Integer clientId, LocalDate date){

        return contractController.findAllDataForContractInForceAtDateByClientId(clientId, date);
    }

    public List<PersonDTO> findAllEmployeesByClientId(Integer clientId){

        return contractController.findAllEmployeesByClientId(clientId);
    }

    public static List<ContractVariationDTO> findAllContractVariationsAfterDateByContractNumber(Integer contractNumber, LocalDate date){

        return contractController.findAllContractVariationsAfterDateByContractNumber(contractNumber, date);
    }
}
