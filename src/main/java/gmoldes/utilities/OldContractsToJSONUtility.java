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

    private static final Integer LABOR_CATEGORY_CHANGE = 210;
    private static final Integer EXTENSION_CONTRACT = 220;
    private static final Integer WORK_DAY_VARIATION = 230;


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
                        .withTypeOfContract(mapContractTypeStringToInteger(contractDTO.getTypeOfContract()))
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
                        .withContractType(mapContractTypeStringToInteger(contractDTO.getTypeOfContract()))
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

    private Integer mapContractTypeStringToInteger(String contractType){
        Integer typeOfContract = 999;

        if(contractType.contains("Normal")){
            typeOfContract = 1;
        }
        if(contractType.contains("Eventual")){
            typeOfContract = 3;
        }
        if(contractType.contains("Obra")){
            typeOfContract = 4;
        }
        if(contractType.contains("Formación")){
            typeOfContract = 5;
        }
        if(contractType.contains("Prácticas")){
            typeOfContract = 6;
        }
        if(contractType.contains("Subrogación")){
            typeOfContract = 7;
        }
        if(contractType.contains("Socio")){
            typeOfContract = 8;
        }
        if(contractType.contains("Administrador")){
            typeOfContract = 9;
        }
        if(contractType.contains("relevo")){
            typeOfContract = 10;
        }
        if(contractType.contains("embarazo")){
            typeOfContract = 11;
        }
        if(contractType.contains("maternidad")){
            typeOfContract = 12;
        }
        if(contractType.contains("Conversión")){
            typeOfContract = 13;
        }
        if(contractType.contains("baja laboral")){
            typeOfContract = 14;
        }
        if(contractType.contains("vacaciones")){
            typeOfContract = 15;
        }
        if(contractType.contains("discontínuo")){
            typeOfContract = 16;
        }
        if(contractType.contains("excedencia")){
            typeOfContract = 17;
        }

        return typeOfContract;
    }
}

