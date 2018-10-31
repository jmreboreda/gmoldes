package gmoldes.utilities;

import gmoldes.components.contract.manager.ContractManager;
import gmoldes.components.contract.persistence.dao.InitialContractDAO;
import gmoldes.components.contract.persistence.vo.InitialContractVO;
import gmoldes.domain.contract.dto.ContractDTO;
import gmoldes.domain.initialcontractdata.InitialContractData;
import java.sql.Date;
import java.util.List;

public class ContractsToJSON {

    private static final Integer INITIAL_CONTRACT_VARIATION_CODE = 0;

    private ContractManager contractManager = new ContractManager();

    public void contractToJsonGenerator() {
        List<ContractDTO> contractDTOList = contractManager.findAllContractsSorted();
        initialContractGenerator(contractDTOList);
    }

    private void initialContractGenerator(List<ContractDTO> contractDTOList){
        Boolean isContractInForce = false;
        for (ContractDTO contractDTO : contractDTOList) {
            if(contractDTO.getVariationNumber().equals(INITIAL_CONTRACT_VARIATION_CODE)){
                InitialContractData initialContractData = new InitialContractData(
                        contractDTO.getClientGMId(), contractDTO.getWorkerId(), contractDTO.getQuoteAccountCode(), contractDTO.getLaborCategory(),
                        contractDTO.getWeeklyWorkHours(), contractDTO.getDaysOfWeekToWork().toString(), contractDTO.getFullPartialWorkday(),
                        contractDTO.getIdentificationContractNumberINEM());

                for (ContractDTO contractDTOInForce : contractDTOList) {
                    if(contractDTOInForce.getContractNumber().equals(contractDTO.getContractNumber())){
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
                initialContractVO.setInitialContractData(initialContractData);

                Integer id = initialContractDAO.create(initialContractVO);

                isContractInForce = false;
                System.out.println("Registro de contrato inicial: " + id);
            }
        }
    }
}

