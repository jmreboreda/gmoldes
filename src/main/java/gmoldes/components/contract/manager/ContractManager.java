package gmoldes.components.contract.manager;


import gmoldes.components.contract.contract_variation.persistence.dao.ContractVariationDAO;
import gmoldes.components.contract.contract_variation.persistence.vo.ContractVariationVO;
import gmoldes.components.contract.new_contract.mapper.MapperOldContractToSaveDTOVO;
import gmoldes.components.contract.new_contract.persistence.dao.ContractDAO;
import gmoldes.components.contract.initial_contract.persistence.dao.InitialContractDAO;
import gmoldes.components.contract.new_contract.persistence.vo.ContractVO;
import gmoldes.components.contract.initial_contract.persistence.vo.InitialContractVO;
import gmoldes.domain.contract.dto.ContractDTO;
import gmoldes.domain.contract.dto.ContractVariationDTO;
import gmoldes.domain.contract.dto.InitialContractDTO;
import gmoldes.domain.contract.dto.OldContractToSaveDTO;
import gmoldes.domain.contract.mapper.MapperContractVODTO;
import gmoldes.domain.contract.mapper.MapperContractVariationVODTO;
import gmoldes.domain.contract.mapper.MapperInitialContractDTOVO;
import gmoldes.domain.contract.mapper.MapperInitialContractVODTO;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class ContractManager {

    public ContractManager() {
    }

    public Integer saveOldContract(OldContractToSaveDTO oldContractToSaveDTO){
        ContractDAO contractDAO = ContractDAO.ContractDAOFactory.getInstance();
        MapperOldContractToSaveDTOVO mapperOldContractToSaveDTOVO = new MapperOldContractToSaveDTOVO();
        Integer newContractNumber = contractDAO.findHighestContractNumber() + 1;
        oldContractToSaveDTO.setContractNumber(newContractNumber);
        ContractVO contractVO = mapperOldContractToSaveDTOVO.mapOldContractToSaveDTOVO((oldContractToSaveDTO));

        return contractDAO.create(contractVO);
    }

    public Integer saveInitialContract(InitialContractDTO initialContractDTO){
        InitialContractDAO initialContractDAO = InitialContractDAO.InitialContractDAOFactory.getInstance();
        MapperInitialContractDTOVO mapperInitialContractDTOVO = new MapperInitialContractDTOVO();
        Integer newContractNumber = initialContractDAO.findHighestContractNumber() + 1;
        initialContractDTO.setContractNumber(newContractNumber);
        InitialContractVO initialContractVO = mapperInitialContractDTOVO.mapContractDTOVO(initialContractDTO);

        return initialContractDAO.create(initialContractVO);
    }

    public List<ContractDTO> findAllContractsOrderedByContractNumberAndVariation(){
        List<ContractDTO> contractDTOList = new ArrayList<>();
        MapperContractVODTO mapper = new MapperContractVODTO();

        ContractDAO contractDAO = ContractDAO.ContractDAOFactory.getInstance();
        List<ContractVO> contractVOList = contractDAO.findAllContractsOrderedByContractNumberAndVariation();
        for (ContractVO contractVO : contractVOList) {
            LocalDate dateTo = (contractVO.getF_hasta() != null) ? contractVO.getF_hasta().toLocalDate() : null;
            Set<DayOfWeek> daysOfWeekToWork = mapper.mapDaysOfWeekToWorkVODTO(contractVO);

            ContractDTO contractDTO = ContractDTO.create()
                    .withLaborCategory(contractVO.getCategoria())
                    .withClientGMName(contractVO.getClientegm_name())
                    .withQuoteAccountCode(contractVO.getContrato_ccc())
                    .withIndefiniteOrTemporalContract(contractVO.getDuracion())
                    .withContractInForce(contractVO.getEnvigor())
                    .withDateFrom(contractVO.getF_desde().toLocalDate())
                    .withDateTo(dateTo)
                    .withId(contractVO.getId())
                    .withIdentificationContractNumberINEM(contractVO.getId_ctto_inem())
                    .withClientGMId(contractVO.getIdcliente_gm())
                    .withWorkerId(contractVO.getIdtrabajador())
                    .withFullPartialWorkday(contractVO.getJor_tipo())
                    .withWeeklyWorkHours(contractVO.getJor_trab())
                    .withDaysOfWeekToWork(daysOfWeekToWork)
                    .withContractType(contractVO.getTipoctto())
                    .withNotesForManager(contractVO.getNotas_gestor())
                    .withPrivateNotes(contractVO.getNotas_privadas())
                    .withContractNumber(contractVO.getNumcontrato())
                    .withVariationNumber(contractVO.getNumvariacion())
                    .withSurrogateContract(contractVO.getSubrogacion())
                    .withContractType(contractVO.getTipoctto())
                    .withVariationType(contractVO.getTipovariacion())
                    .withWorkerName(contractVO.getTrabajador_name())
                    .build();

            contractDTOList.add(contractDTO);
        }
        return contractDTOList;
    }

    public List<ContractDTO> findAllContractsByClientIdInPeriod(Integer clientId, Date referenceDate){

        List<ContractDTO> contractDTOList = new ArrayList<>();

        ContractDAO contractDAO = ContractDAO.ContractDAOFactory.getInstance();
        List<ContractVO> contractVOList = contractDAO.findAllContractsByClientIdInPeriod(clientId, referenceDate);
        for (ContractVO contractVO : contractVOList) {
            LocalDate dateTo = (contractVO.getF_hasta() != null) ? contractVO.getF_hasta().toLocalDate() : null;

            ContractDTO contractDTO = ContractDTO.create()
                    .withLaborCategory(contractVO.getCategoria())
                    .withClientGMName(contractVO.getClientegm_name())
                    .withQuoteAccountCode(contractVO.getContrato_ccc())
                    .withIndefiniteOrTemporalContract(contractVO.getDuracion())
                    .withContractInForce(contractVO.getEnvigor())
                    .withDateFrom(contractVO.getF_desde().toLocalDate())
                    .withDateTo(dateTo)
                    .withId(contractVO.getId())
                    .withIdentificationContractNumberINEM(contractVO.getId_ctto_inem())
                    .withClientGMId(contractVO.getIdcliente_gm())
                    .withWorkerId(contractVO.getIdtrabajador())
                    .withFullPartialWorkday(contractVO.getJor_tipo())
                    .withWeeklyWorkHours(contractVO.getJor_trab())
                    .withNotesForManager(contractVO.getNotas_gestor())
                    .withPrivateNotes(contractVO.getNotas_privadas())
                    .withContractNumber(contractVO.getNumcontrato())
                    .withVariationNumber(contractVO.getNumvariacion())
                    .withSurrogateContract(contractVO.getSubrogacion())
                    .withContractType(contractVO.getTipoctto())
                    .withVariationType(contractVO.getTipovariacion())
                    .withWorkerName(contractVO.getTrabajador_name())
                    .build();

            contractDTOList.add(contractDTO);
        }
        return contractDTOList;
    }

    public List<ContractDTO> findAllContractsWithTimeRecordByClientIdInPeriod(Integer clientId, String yearMonth){

        List<ContractDTO> contractDTOList = new ArrayList<>();

        ContractDAO contractDAO = ContractDAO.ContractDAOFactory.getInstance();
        List<ContractVO> contractVOList = contractDAO.findAllContractsWithTimeRecordByClientIdInPeriod(clientId, yearMonth);
        for (ContractVO contractVO : contractVOList) {
            LocalDate dateTo = (contractVO.getF_hasta() != null) ? contractVO.getF_hasta().toLocalDate() : null;

            ContractDTO contractDTO = ContractDTO.create()
                    .withLaborCategory(contractVO.getCategoria())
                    .withClientGMName(contractVO.getClientegm_name())
                    .withQuoteAccountCode(contractVO.getContrato_ccc())
                    .withIndefiniteOrTemporalContract(contractVO.getDuracion())
                    .withContractInForce(contractVO.getEnvigor())
                    .withDateFrom(contractVO.getF_desde().toLocalDate())
                    .withDateTo(dateTo)
                    .withId(contractVO.getId())
                    .withIdentificationContractNumberINEM(contractVO.getId_ctto_inem())
                    .withClientGMId(contractVO.getIdcliente_gm())
                    .withWorkerId(contractVO.getIdtrabajador())
                    .withFullPartialWorkday(contractVO.getJor_tipo())
                    .withWeeklyWorkHours(contractVO.getJor_trab())
                    .withNotesForManager(contractVO.getNotas_gestor())
                    .withPrivateNotes(contractVO.getNotas_privadas())
                    .withContractNumber(contractVO.getNumcontrato())
                    .withVariationNumber(contractVO.getNumvariacion())
                    .withSurrogateContract(contractVO.getSubrogacion())
                    .withContractType(contractVO.getTipoctto())
                    .withVariationType(contractVO.getTipovariacion())
                    .withWorkerName(contractVO.getTrabajador_name())
                    .build();

            contractDTOList.add(contractDTO);
        }
        return contractDTOList;
    }

    public List<ContractDTO> findAllActiveContractsByClientId(Integer clientId) {

        List<ContractDTO> contractDTOList = new ArrayList<>();

        ContractDAO contractDAO = ContractDAO.ContractDAOFactory.getInstance();
        List<ContractVO> contractVOList = contractDAO.findAllActiveContractsByClientId(clientId);
        for (ContractVO contractVO : contractVOList) {
            LocalDate dateTo = (contractVO.getF_hasta() != null) ? contractVO.getF_hasta().toLocalDate() : null;

            ContractDTO contractDTO = ContractDTO.create()
                    .withLaborCategory(contractVO.getCategoria())
                    .withClientGMName(contractVO.getClientegm_name())
                    .withQuoteAccountCode(contractVO.getContrato_ccc())
                    .withIndefiniteOrTemporalContract(contractVO.getDuracion())
                    .withContractInForce(contractVO.getEnvigor())
                    .withDateFrom(contractVO.getF_desde().toLocalDate())
                    .withDateTo(dateTo)
                    .withId(contractVO.getId())
                    .withIdentificationContractNumberINEM(contractVO.getId_ctto_inem())
                    .withClientGMId(contractVO.getIdcliente_gm())
                    .withWorkerId(contractVO.getIdtrabajador())
                    .withFullPartialWorkday(contractVO.getJor_tipo())
                    .withWeeklyWorkHours(contractVO.getJor_trab())
                    .withNotesForManager(contractVO.getNotas_gestor())
                    .withPrivateNotes(contractVO.getNotas_privadas())
                    .withContractNumber(contractVO.getNumcontrato())
                    .withVariationNumber(contractVO.getNumvariacion())
                    .withSurrogateContract(contractVO.getSubrogacion())
                    .withContractType(contractVO.getTipoctto())
                    .withVariationType(contractVO.getTipovariacion())
                    .withWorkerName(contractVO.getTrabajador_name())
                    .build();

            contractDTOList.add(contractDTO);
        }
        return contractDTOList;
    }

    public int establishContractInForce(){

        ContractDAO contractDAO = ContractDAO.ContractDAOFactory.getInstance();
        return contractDAO.establishContractInForce();

    }

    public int establishContractNotInForce(){

        ContractDAO contractDAO = ContractDAO.ContractDAOFactory.getInstance();
        return contractDAO.establishContractNotInForce();
    }

    public List<ContractDTO> findContractsExpiration(){
        ContractDAO contractDAO = ContractDAO.ContractDAOFactory.getInstance();
        List<ContractVO> contractVOList = contractDAO.findContractsExpiration();
        MapperContractVODTO mapper = new MapperContractVODTO();
        List<ContractDTO> contractDTOList = new ArrayList<>();
        for(ContractVO contractVO : contractVOList){
            ContractDTO contractDTO = mapper.mapContractVODTO(contractVO);
            contractDTOList.add(contractDTO);
        }

        return contractDTOList;
    }

    public List<ContractDTO> findPendingIDC(){
        ContractDAO contractDAO = ContractDAO.ContractDAOFactory.getInstance();
        List<ContractVO> contractVOList = contractDAO.findPendingIDC();
        MapperContractVODTO mapper = new MapperContractVODTO();
        List<ContractDTO> contractDTOList = new ArrayList<>();
        for(ContractVO contractVO : contractVOList){
            ContractDTO contractDTO = mapper.mapContractVODTO(contractVO);
            contractDTOList.add(contractDTO);
        }

        return contractDTOList;
    }

    public List<InitialContractDTO> findAllInitialContractSorted(){
        List<InitialContractDTO> initialContractDTOList = new ArrayList<>();
        MapperInitialContractVODTO mapper = new MapperInitialContractVODTO();

        InitialContractDAO initialContractDAO = InitialContractDAO.InitialContractDAOFactory.getInstance();
        List<InitialContractVO> initialContractVOList = initialContractDAO.findAllInitialContractSorted();
        for (InitialContractVO initialContractVO : initialContractVOList) {
            LocalDate expectedEndDate = initialContractVO.getExpectedEndDate() == null ? null : initialContractVO.getExpectedEndDate().toLocalDate();
            LocalDate endingDate = initialContractVO.getEndingDate() == null ? null : initialContractVO.getEndingDate().toLocalDate();

            InitialContractDTO initialContractDTO = InitialContractDTO.create()
                    .withContractNumber(initialContractVO.getContractNumber())
                    .withVariationType(initialContractVO.getVariationType())
                    .withStartDate(initialContractVO.getStartDate().toLocalDate())
                    .withExpectedEndDate(expectedEndDate)
                    .withEndingDate(endingDate)
                    .withClientGMId(initialContractVO.getInitialContractJSONData().getClientGMId())
                    .withWorkerId(initialContractVO.getInitialContractJSONData().getWorkerId())
                    .withContractType(initialContractVO.getInitialContractJSONData().getContractType())
                    .withLaborCategory(initialContractVO.getInitialContractJSONData().getLaborCategory())
                    .withWeeklyWorkHours(initialContractVO.getInitialContractJSONData().getWeeklyWorkHours())
                    .withDaysOfWeekToWork(mapper.mapDaysOfWeekToWorkVODTO(initialContractVO))
                    .withFullPartialWorkday(initialContractVO.getInitialContractJSONData().getFullPartialWorkday())
                    .withIdentificationContractNumberINEM(initialContractVO.getInitialContractJSONData().getIdentificationContractNumberINEM())
                    .withNotesForManager(initialContractVO.getInitialContractJSONData().getNotesForContractManager())
                    .withPrivateNotes(initialContractVO.getInitialContractJSONData().getPrivateNotes())
                    .build();

            initialContractDTOList.add(initialContractDTO);
        }

        return initialContractDTOList;
    }

    public InitialContractDTO findInitialContractByContractNumber(Integer contractNumber){
        InitialContractDAO initialContractDAO = InitialContractDAO.InitialContractDAOFactory.getInstance();
        InitialContractVO initialContractVO = initialContractDAO.findInitialContractByContractNumber(contractNumber);
        MapperInitialContractVODTO mapper = new MapperInitialContractVODTO();


        InitialContractDTO initialContractDTO = InitialContractDTO.create()
                .withContractNumber(initialContractVO.getContractNumber())
                .withVariationType(initialContractVO.getVariationType())
                .withStartDate(initialContractVO.getStartDate().toLocalDate())
                .withExpectedEndDate(initialContractVO.getExpectedEndDate().toLocalDate())
                .withEndingDate(initialContractVO.getEndingDate().toLocalDate())
                .withClientGMId(initialContractVO.getInitialContractJSONData().getClientGMId())
                .withWorkerId(initialContractVO.getInitialContractJSONData().getWorkerId())
                .withContractType(initialContractVO.getInitialContractJSONData().getContractType())
                .withLaborCategory(initialContractVO.getInitialContractJSONData().getLaborCategory())
                .withWeeklyWorkHours(initialContractVO.getInitialContractJSONData().getWeeklyWorkHours())
                .withDaysOfWeekToWork(mapper.mapDaysOfWeekToWorkVODTO(initialContractVO))
                .withFullPartialWorkday(initialContractVO.getInitialContractJSONData().getFullPartialWorkday())
                .withIdentificationContractNumberINEM(initialContractVO.getInitialContractJSONData().getIdentificationContractNumberINEM())
                .withNotesForManager(initialContractVO.getInitialContractJSONData().getNotesForContractManager())
                .withPrivateNotes(initialContractVO.getInitialContractJSONData().getPrivateNotes())
                .build();

        return initialContractDTO;
    }

    public List<ContractVariationDTO> findAllContractVariationByContractNumber(Integer contractNumber){
        List<ContractVariationDTO> contractVariationDTOList = new ArrayList<>();
        ContractVariationDAO contractVariationDAO = ContractVariationDAO.ContractVariationDAOFactory.getInstance();
        List<ContractVariationVO> contractVariationVOList = contractVariationDAO.findAllContractVariationByContractNumber(contractNumber);
        MapperContractVariationVODTO mapper = new MapperContractVariationVODTO();

        for(ContractVariationVO contractVariationVO : contractVariationVOList) {

            ContractVariationDTO contractVariationDTO = ContractVariationDTO.create()
                    .withContractNumber(contractVariationVO.getContractNumber())
                    .withVariationType(contractVariationVO.getVariationType())
                    .withStartDate(contractVariationVO.getStartDate().toLocalDate())
                    .withExpectedEndDate(contractVariationVO.getExpectedEndDate().toLocalDate())
                    .withEndingDate(contractVariationVO.getEndingDate().toLocalDate())
                    .withClientGMId(contractVariationVO.getContractVariationJSONData().getClientGMId())
                    .withWorkerId(contractVariationVO.getContractVariationJSONData().getWorkerId())
                    .withContractType(contractVariationVO.getContractVariationJSONData().getContractType())
                    .withLaborCategory(contractVariationVO.getContractVariationJSONData().getLaborCategory())
                    .withWeeklyWorkHours(contractVariationVO.getContractVariationJSONData().getWeeklyWorkHours())
                    .withDaysOfWeekToWork(mapper.mapDaysOfWeekToWorkVODTO(contractVariationVO))
                    .withFullPartialWorkday(contractVariationVO.getContractVariationJSONData().getFullPartialWorkDay())
                    .withIdentificationContractNumberINEM(contractVariationVO.getContractVariationJSONData().getIdentificationContractNumberINEM())
                    .withNotesForManager(contractVariationVO.getContractVariationJSONData().getNotesForContractManager())
                    .withPrivateNotes(contractVariationVO.getContractVariationJSONData().getPrivateNotes())
                    .build();

            contractVariationDTOList.add(contractVariationDTO);
        }

        return contractVariationDTOList;
    }
}
