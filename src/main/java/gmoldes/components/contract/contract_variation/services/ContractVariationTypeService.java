package gmoldes.components.contract.contract_variation.services;

import gmoldes.components.contract.contract_variation.controllers.ContractVariationTypeController;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;

import java.util.List;

public class ContractVariationTypeService {

    private ContractVariationTypeService() {
    }

    public static class ContractVariationTypesServiceFactory {

        private static ContractVariationTypeService contractVariationTypeService;

        public static ContractVariationTypeService getInstance() {
            if(contractVariationTypeService == null) {
                contractVariationTypeService = new ContractVariationTypeService();
            }

            return contractVariationTypeService;
        }
    }

    private static ContractVariationTypeController contractVariationTypeController = new ContractVariationTypeController();

    public List<TypesContractVariationsDTO> findAllContractVariationType(){

        return contractVariationTypeController.findAllTypesContractVariations();
    }

//    public List<ContractNewVersionDTO> findHistoryOfContractByContractNumber(Integer contractNumber){
//
//        List<ContractNewVersionDTO> contractNewVersionDTOList = contractController.findHistoryOfContractByContractNumber(contractNumber);
//
//        InitialContractDTO initialContractDTO = contractController.findInitialContractByContractNumber(contractNumber);
//
//        List<ContractVariationDTO> contractVariationDTOList = contractController.findAllContractVariationByContractNumber(contractNumber);
//
//        ContractNewVersionDTO initialContractNewVersionDTO = ContractNewVersionDTO.create()
//                .withId(initialContractDTO.getId())
//                .withContractNumber(initialContractDTO.getContractNumber())
//                .withVariationType(initialContractDTO.getVariationType())
//                .withStartDate(initialContractDTO.getStartDate())
//                .withExpectedEndDate(initialContractDTO.getExpectedEndDate())
//                .withModificationDate(initialContractDTO.getModificationDate())
//                .withEndingDate(initialContractDTO.getEndingDate())
//                .withContractJsonData(initialContractDTO.getContractJsonData())
//                .withContractScheduleJsonData(initialContractDTO.getContractScheduleJsonData())
//                .build();
//
//        contractNewVersionDTOList.add(initialContractNewVersionDTO);
//
//        for(ContractVariationDTO contractVariationDTO : contractVariationDTOList){
//            contractNewVersionDTOList.add(MapperContractVariationDTOtoContractNewVersionDTO.map(contractVariationDTO));
//        }
//
//        return contractNewVersionDTOList;
//    }
//
//    public List<ContractNewVersionDTO> findAllContractInForceInPeriod(LocalDate initialDate, LocalDate finalDate){
//
//        return contractController.findAllContractInForceInPeriod(initialDate, finalDate);
//    }
//
//    public List<ContractFullDataDTO> findAllDataForContractInForceAtDateByClientId(Integer clientId, LocalDate date){
//
//        return contractController.findAllDataForContractInForceAtDateByClientId(clientId, date);
//    }
//
//    public static List<ContractVariationDTO> findAllContractVariationsAfterDateByContractNumber(Integer contractNumber, LocalDate date){
//
//        return contractController.findAllContractVariationsAfterDateByContractNumber(contractNumber, date);
//    }
}
