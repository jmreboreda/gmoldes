package gmoldes.utilities;

import gmoldes.components.contract.contract_variation.persistence.dao.ContractVariationDAO;
import gmoldes.components.contract.contract_variation.persistence.vo.ContractVariationVO;
import gmoldes.components.contract.manager.ContractManager;
import gmoldes.components.contract.persistence.dao.InitialContractDAO;
import gmoldes.components.contract.persistence.vo.InitialContractVO;
import gmoldes.domain.contract.dto.ContractDTO;
import gmoldes.domain.initialcontractdata.ContractVariationJSONData;
import gmoldes.domain.initialcontractdata.InitialContractJSONData;
import java.sql.Date;
import java.util.List;

public class OldContractsToJSONUtility {

    private static final Integer INITIAL_CONTRACT_VARIATION_CODE = 0;
    private static final Integer CONTRACT_SUBROGATION_CODE = 109;


    private ContractManager contractManager = new ContractManager();

    public void oldContractToJsonGenerator() {
        List<ContractDTO> contractDTOList = contractManager.findAllContractsSorted();
        initialOldContractToJSONGenerator(contractDTOList);
        oldContractVariationToJSONGenerator(contractDTOList);
    }

    private void initialOldContractToJSONGenerator(List<ContractDTO> contractDTOList){
        Boolean isContractInForce = false;
        for (ContractDTO contractDTO : contractDTOList) {
            if(contractDTO.getVariationNumber().equals(INITIAL_CONTRACT_VARIATION_CODE) ||
            contractDTO.getVariationType().equals(CONTRACT_SUBROGATION_CODE)){

                InitialContractJSONData initialContractJSONData = InitialContractJSONData.create()
                        .withClientGMId(contractDTO.getClientGMId())
                        .withWorkerId(contractDTO.getWorkerId())
                        .withQuoteAccountCode(contractDTO.getQuoteAccountCode())
                        .withLaborCategory(contractDTO.getLaborCategory())
                        .withWeeklyWorkHours(contractDTO.getWeeklyWorkHours())
                        .withDaysOfWeekToWork(contractDTO.getDaysOfWeekToWork().toString())
                        .withFullPartialWorkday(contractDTO.getFullPartialWorkday())
                        .withIdentificationContractNumberINEM(contractDTO.getIdentificationContractNumberINEM())
                        .withNotesForContractManager(contractDTO.getNotesForManager())
                        .withPrivateNotes(contractDTO.getPrivateNotes())
                        .build();

                for (ContractDTO contractDTOInForce : contractDTOList) {
                    if(contractDTOInForce.getContractNumber().equals(contractDTO.getContractNumber()) &&
                            !contractDTOInForce.getVariationType().equals(CONTRACT_SUBROGATION_CODE)){
                        isContractInForce = contractDTOInForce.getContractInForce();
                    }
                }

                InitialContractDAO initialContractDAO = InitialContractDAO.InitialContractDAOFactory.getInstance();
                InitialContractVO initialContractVO = new InitialContractVO();

                initialContractVO.setContractNumber(contractDTO.getContractNumber());
                initialContractVO.setVariationType(contractDTO.getVariationType());
                initialContractVO.setStartDate(Date.valueOf(contractDTO.getDateFrom()));
                Date myExpectedEndDate = contractDTO.getDateTo() != null ? Date.valueOf(contractDTO.getDateTo()) : null;
                initialContractVO.setExpectedEndDate(myExpectedEndDate);
                Date myEndingDate = isContractInForce ? null : myExpectedEndDate;
                initialContractVO.setEndingDate(myEndingDate);
                initialContractVO.setInitialContractJSONData(initialContractJSONData);

                Integer id = initialContractDAO.create(initialContractVO);
                isContractInForce = false;

                System.out.println("Registro de contrato inicial: " + id);
            }
        }
    }

    private void oldContractVariationToJSONGenerator(List<ContractDTO> contractDTOList) {
        for (ContractDTO contractDTO : contractDTOList) {
            if (!contractDTO.getVariationNumber().equals(INITIAL_CONTRACT_VARIATION_CODE) &&
                    !contractDTO.getVariationType().equals(CONTRACT_SUBROGATION_CODE)) {

                Date dateFrom = contractDTO.getDateFrom() == null ? null : Date.valueOf(contractDTO.getDateFrom());
                Date dateTo = contractDTO.getDateTo() == null ? null : Date.valueOf(contractDTO.getDateTo());

                ContractVariationJSONData contractVariationJSONData = ContractVariationJSONData.create()
                        .withClientGMId(contractDTO.getClientGMId())
                        .withWorkerId(contractDTO.getWorkerId())
                        .withQuoteAccountCode(contractDTO.getQuoteAccountCode())
                        .withDateFrom(dateFrom)
                        .withDateTo(dateTo)
                        .withLaborCategory(contractDTO.getLaborCategory())
                        .withWeeklyWorkHours(contractDTO.getWeeklyWorkHours())
                        .withDaysOfWeekToWork(contractDTO.getDaysOfWeekToWork().toString())
                        .withFullPartialWorkday(contractDTO.getFullPartialWorkday())
                        .withIdentificationContractNumberINEM(contractDTO.getIdentificationContractNumberINEM())
                        .withNotesForContractManager(contractDTO.getNotesForManager())
                        .withPrivateNotes(contractDTO.getPrivateNotes())
                        .build();

                ContractVariationDAO contractVariationDAO = ContractVariationDAO.ContractVariationDAOFactory.getInstance();
                ContractVariationVO contractVariationVO = new ContractVariationVO();

                contractVariationVO.setContractNumber(contractDTO.getContractNumber());
                contractVariationVO.setVariationType(contractDTO.getVariationType());
                contractVariationVO.setContractVariationJSONData(contractVariationJSONData);

                Integer id = contractVariationDAO.create(contractVariationVO);

                System.out.println("Registro de variación de datos: " + id);
            }
        }
    }
}

