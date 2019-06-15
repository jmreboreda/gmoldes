package gmoldes.domain.traceability_contract_documentation;

import gmoldes.domain.traceability_contract_documentation.controllers.TraceabilityContractDocumentationController;
import gmoldes.domain.traceability_contract_documentation.dto.TraceabilityContractDocumentationDTO;

import java.util.List;

public class TraceabilityService {

    private TraceabilityService() {
    }

    public static class TraceabilityServiceFactory {

        private static TraceabilityService traceabilityService;

        public static TraceabilityService getInstance() {
            if(traceabilityService == null) {
                traceabilityService = new TraceabilityService();
            }

            return traceabilityService;
        }
    }

    private static TraceabilityContractDocumentationController traceabilityContractDocumentationController = new TraceabilityContractDocumentationController();

    public List<TraceabilityContractDocumentationDTO> findAllTraceabilityContractData(){

        return traceabilityContractDocumentationController.findAllTraceabilityContractData();
    }

//    public InitialContractDTO findInitialContractByContractNumber(Integer contractNumber){
//
//        return traceabilityContractDocumentationController.findInitialContractByContractNumber(contractNumber);
//    }
//
//    public List<ContractVariationDTO> findAllContractVariationByContractNumber(Integer contractNumber){
//
//        return traceabilityContractDocumentationController.findAllContractVariationByContractNumber(contractNumber);
//    }
//
//    public List<ContractNewVersionDTO> findHistoryOfContractByContractNumber(Integer contractNumber){
//
//        List<ContractNewVersionDTO> contractNewVersionDTOList = traceabilityContractDocumentationController.findHistoryOfContractByContractNumber(contractNumber);
//
//        InitialContractDTO initialContractDTO = traceabilityContractDocumentationController.findInitialContractByContractNumber(contractNumber);
//
//        List<ContractVariationDTO> contractVariationDTOList = traceabilityContractDocumentationController.findAllContractVariationByContractNumber(contractNumber);
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
//        return traceabilityContractDocumentationController.findAllContractInForceInPeriod(initialDate, finalDate);
//    }
//
//    public List<ContractFullDataDTO> findAllDataForContractInForceAtDateByClientId(Integer clientId, LocalDate date){
//
//        return traceabilityContractDocumentationController.findAllDataForContractInForceAtDateByClientId(clientId, date);
//    }
//
//    public List<PersonDTO> findAllEmployeesByClientId(Integer clientId){
//
//        return traceabilityContractDocumentationController.findAllEmployeesByClientId(clientId);
//    }
//
//    public static List<ContractVariationDTO> findAllContractVariationsAfterDateByContractNumber(Integer contractNumber, LocalDate date){
//
//        return traceabilityContractDocumentationController.findAllContractVariationsAfterDateByContractNumber(contractNumber, date);
//    }
}
