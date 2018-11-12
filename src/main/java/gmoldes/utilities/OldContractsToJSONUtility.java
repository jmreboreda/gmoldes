package gmoldes.utilities;

import gmoldes.components.contract.contract_variation.persistence.dao.ContractVariationDAO;
import gmoldes.components.contract.contract_variation.persistence.vo.ContractVariationVO;
import gmoldes.components.contract.initial_contract.persistence.dao.InitialContractDAO;
import gmoldes.components.contract.initial_contract.persistence.vo.InitialContractVO;
import gmoldes.components.contract.manager.ContractManager;
import gmoldes.domain.contract.dto.ContractDTO;
import gmoldes.domain.contractjsondata.ContractJsonData;

import java.sql.Date;
import java.util.List;

public class OldContractsToJSONUtility {

    private static final Integer INITIAL_CONTRACT_VARIATION_CODE = 0;
    private static final Integer CONTRACT_SUBROGATION_CODE = 109;

    private ContractManager contractManager = new ContractManager();

    public void oldContractToJsonGenerator() {
        List<ContractDTO> contractDTOList = contractManager.findAllContractsOrderedByContractNumberAndVariation();
        initialOldContractToJSONGenerator(contractDTOList);
        oldContractVariationToJSONGenerator(contractDTOList);
    }

    private void initialOldContractToJSONGenerator(List<ContractDTO> contractDTOList){
        Boolean isContractInForce = false;
        for (ContractDTO contractDTO : contractDTOList) {
            if(contractDTO.getVariationNumber().equals(INITIAL_CONTRACT_VARIATION_CODE) ||
            contractDTO.getVariationType().equals(CONTRACT_SUBROGATION_CODE)){
                ContractJsonData contractJsonData = ContractJsonData.create()
                        .withClientGMId(contractDTO.getClientGMId())
                        .withWorkerId(contractDTO.getWorkerId())
                        .withQuoteAccountCode(contractDTO.getQuoteAccountCode())
                        .withLaborCategory(contractDTO.getLaborCategory())
                        .withContractType(mapContractTypeStringToInteger(contractDTO.getContractType()))
                        .withWeeklyWorkHours(contractDTO.getWeeklyWorkHours())
                        .withDaysOfWeekToWork(contractDTO.getDaysOfWeekToWork().toString())
                        .withFullPartialWorkDay(contractDTO.getFullPartialWorkDay())
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
                initialContractVO.setContractJsonData(contractJsonData);

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

                Date endingDate = contractDTO.getDateTo() == null ? null : Date.valueOf(contractDTO.getDateTo());
                Date expectedEndDate = contractDTO.getDateTo() == null ? null : Date.valueOf(contractDTO.getDateTo());

                ContractJsonData contractJsonData = ContractJsonData.create()
                        .withClientGMId(contractDTO.getClientGMId())
                        .withWorkerId(contractDTO.getWorkerId())
                        .withQuoteAccountCode(contractDTO.getQuoteAccountCode())
                        .withLaborCategory(contractDTO.getLaborCategory())
                        .withWeeklyWorkHours(contractDTO.getWeeklyWorkHours())
                        .withDaysOfWeekToWork(contractDTO.getDaysOfWeekToWork().toString())
                        .withFullPartialWorkDay(contractDTO.getFullPartialWorkDay())
                        .withContractType(mapContractTypeStringToInteger(contractDTO.getContractType()))
                        .withIdentificationContractNumberINEM(contractDTO.getIdentificationContractNumberINEM())
                        .withNotesForContractManager(contractDTO.getNotesForManager())
                        .withPrivateNotes(contractDTO.getPrivateNotes())
                        .build();

                ContractVariationDAO contractVariationDAO = ContractVariationDAO.ContractVariationDAOFactory.getInstance();
                ContractVariationVO contractVariationVO = new ContractVariationVO();

                contractVariationVO.setContractNumber(contractDTO.getContractNumber());
                contractVariationVO.setVariationType(contractDTO.getVariationType());
                contractVariationVO.setStartDate(Date.valueOf(contractDTO.getDateFrom()));
                contractVariationVO.setExpectedEndDate(expectedEndDate);
                contractVariationVO.setEndingDate(endingDate);
                contractVariationVO.setContractVariationJSONData(contractJsonData);

                Integer id = contractVariationDAO.create(contractVariationVO);

                System.out.println("Registro de variación de datos: " + id);
            }
        }
    }

    private Integer mapContractTypeStringToInteger(String contractType){
        Integer thisContractType = 999;

        if(contractType.contains("Normal")){
            thisContractType = 1;
        }
        if(contractType.contains("Eventual")){
            thisContractType = 3;
        }
        if(contractType.contains("Obra")){
            thisContractType = 4;
        }
        if(contractType.contains("Formación")){
            thisContractType = 5;
        }
        if(contractType.contains("Prácticas")){
            thisContractType = 6;
        }
        if(contractType.contains("Subrogación")){
            thisContractType = 7;
        }
        if(contractType.contains("Socio")){
            thisContractType = 8;
        }
        if(contractType.contains("Administrador")){
            thisContractType = 9;
        }
        if(contractType.contains("relevo")){
            thisContractType = 10;
        }
        if(contractType.contains("embarazo")){
            thisContractType = 11;
        }
        if(contractType.contains("maternidad")){
            thisContractType = 12;
        }
        if(contractType.contains("Conversión")){
            thisContractType = 13;
        }
        if(contractType.contains("baja laboral")){
            thisContractType = 14;
        }
        if(contractType.contains("vacaciones")){
            thisContractType = 15;
        }
        if(contractType.contains("discontínuo")){
            thisContractType = 16;
        }
        if(contractType.contains("excedencia")){
            thisContractType = 17;
        }

        return thisContractType;
    }
}

