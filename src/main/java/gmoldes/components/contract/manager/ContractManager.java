package gmoldes.components.contract.manager;


import gmoldes.components.contract.contract_variation.persistence.dao.ContractVariationDAO;
import gmoldes.components.contract.contract_variation.persistence.vo.ContractVariationVO;
import gmoldes.components.contract.initial_contract.persistence.dao.InitialContractDAO;
import gmoldes.components.contract.initial_contract.persistence.vo.InitialContractVO;
import gmoldes.components.contract.new_contract.mapper.MapperOldContractToSaveDTOVO;
import gmoldes.components.contract.new_contract.persistence.dao.ContractDAO;
import gmoldes.components.contract.new_contract.persistence.vo.ContractVO;
import gmoldes.domain.contract.dto.ContractDTO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract.dto.ContractVariationDTO;
import gmoldes.domain.contract.dto.OldContractToSaveDTO;
import gmoldes.domain.contract.mapper.MapperContractVODTO;
import gmoldes.domain.contract.mapper.MapperContractVariationVODTO;
import gmoldes.domain.contract.mapper.MapperInitialContractDTOVO;
import gmoldes.domain.contract.mapper.MapperInitialContractVODTO;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
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

    public Integer saveInitialContract(ContractNewVersionDTO initialContractDTO){
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

    public List<ContractNewVersionDTO> findAllContractNewVersionByClientIdInMonthOfDate(Integer clientId, LocalDate dateReceived){

        List<ContractNewVersionDTO> contractNewVersionDTOList = new ArrayList<>();

//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(Date.from(dateReceived.atStartOfDay(ZoneId.systemDefault()).toInstant()));
//
//        Integer firstDayOfMonth = calendar.getMinimum(Calendar.DAY_OF_MONTH);
//        Integer lastDayOfMonth = calendar.getMaximum(Calendar.DAY_OF_MONTH);
//
//
//        System.out.println("Desde ByClient: \n" +
//                "---------------------------------------------------\n" +
//                "calendar.getTime(): " + calendar.getTime() + "\n" +
//                "dateReceived: " + dateReceived + "\n" +
//                "firstDayOfMonth: " + firstDayOfMonth + " :: lastDayOfMonth: " + lastDayOfMonth + "\n");
//
//
//        Integer monthReceived = dateReceived.getMonthValue();
//        Integer yearReceived = dateReceived.getYear();
//
//        LocalDate initialDate = LocalDate.of(yearReceived, monthReceived, firstDayOfMonth);
//        LocalDate finalDate = LocalDate.of(yearReceived, monthReceived, lastDayOfMonth);

        Integer yearReceived = dateReceived.getYear();
        Integer monthReceived = dateReceived.getMonth().getValue();
        Integer dayReceived = dateReceived.getDayOfMonth();

        Calendar calendar = Calendar.getInstance();
        calendar.set(yearReceived, monthReceived - 1, dayReceived);

        Integer firstDayOfMonth = calendar.getMinimum(Calendar.DAY_OF_MONTH);
        LocalDate initialDate = LocalDate.of(yearReceived, monthReceived, firstDayOfMonth);
        LocalDate finalDate =  LocalDate.of(yearReceived, monthReceived, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        InitialContractDAO initialContractDAO = InitialContractDAO.InitialContractDAOFactory.getInstance();
        List<InitialContractVO> initialContractVOList = initialContractDAO.findAllInitialContractInPeriod(initialDate, finalDate);
        for (InitialContractVO initialContractVO : initialContractVOList) {
            LocalDate notNullExpectedEndDate = initialContractVO.getExpectedEndDate() != null ? initialContractVO.getExpectedEndDate().toLocalDate() : null;
            LocalDate notNullModificationDate = initialContractVO.getModificationDate() != null ? initialContractVO.getModificationDate().toLocalDate() : null;
            LocalDate notNUllEndingDate = (initialContractVO.getEndingDate() != null) ? initialContractVO.getEndingDate().toLocalDate() : null;
            if(initialContractVO.getContractJsonData().getClientGMId().equals(clientId)) {
                ContractNewVersionDTO contractNewVersionDTO = ContractNewVersionDTO.create()
                        .withId(initialContractVO.getId())
                        .withContractNumber(initialContractVO.getContractNumber())
                        .withStartDate(initialContractVO.getStartDate().toLocalDate())
                        .withExpectedEndDate(notNullExpectedEndDate)
                        .withModificationDate(notNullModificationDate)
                        .withEndingDate(notNUllEndingDate)
                        .withContractJsonData(initialContractVO.getContractJsonData())
                        .build();

                contractNewVersionDTOList.add(contractNewVersionDTO);
            }
        }

        ContractVariationDAO contractVariationDAO = ContractVariationDAO.ContractVariationDAOFactory.getInstance();
        List<ContractVariationVO> contractVariationVOList = contractVariationDAO.findAllContractVariationInPeriod(initialDate, finalDate);
        for (ContractVariationVO contractVariationVO : contractVariationVOList) {
            LocalDate notNullExpectedEndDate = contractVariationVO.getExpectedEndDate() != null ? contractVariationVO.getExpectedEndDate().toLocalDate(): null;
            LocalDate notNullModificationDate = contractVariationVO.getModificationDate() != null ? contractVariationVO.getModificationDate().toLocalDate() : null;
            LocalDate notNullEndingDate = (contractVariationVO.getEndingDate() != null) ? contractVariationVO.getEndingDate().toLocalDate() : null;
            if(contractVariationVO.getContractJsonData().getClientGMId().equals(clientId)) {
                ContractNewVersionDTO contractNewVersionDTO = ContractNewVersionDTO.create()
                        .withId(contractVariationVO.getId())
                        .withContractNumber(contractVariationVO.getContractNumber())
                        .withStartDate(contractVariationVO.getStartDate().toLocalDate())
                        .withExpectedEndDate(notNullExpectedEndDate)
                        .withModificationDate(notNullModificationDate)
                        .withEndingDate(notNullEndingDate)
                        .withContractJsonData(contractVariationVO.getContractJsonData())
                        .build();

                contractNewVersionDTOList.add(contractNewVersionDTO);
            }
        }

        return contractNewVersionDTOList;
    }

    public List<ContractNewVersionDTO> findAllContractNewVersionInMonthOfDate(LocalDate dateReceived){

        List<ContractNewVersionDTO> contractNewVersionDTOList = new ArrayList<>();

        Integer yearReceived = dateReceived.getYear();
        Integer monthReceived = dateReceived.getMonth().getValue();
        Integer dayReceived = dateReceived.getDayOfMonth();

        Calendar calendar = Calendar.getInstance();
        calendar.set(yearReceived, monthReceived - 1, dayReceived);

        Integer firstDayOfMonth = calendar.getMinimum(Calendar.DAY_OF_MONTH);
        LocalDate initialDate = LocalDate.of(yearReceived, monthReceived, firstDayOfMonth);
        LocalDate finalDate =  LocalDate.of(yearReceived, monthReceived, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        InitialContractDAO initialContractDAO = InitialContractDAO.InitialContractDAOFactory.getInstance();
        List<InitialContractVO> initialContractVOList = initialContractDAO.findAllInitialContractInPeriod(initialDate, finalDate);
        for (InitialContractVO initialContractVO : initialContractVOList) {
            LocalDate notNullExpectedEndDate = initialContractVO.getExpectedEndDate() != null ? initialContractVO.getExpectedEndDate().toLocalDate() : null;
            LocalDate notNullModificationDate = initialContractVO.getModificationDate() != null ? initialContractVO.getModificationDate().toLocalDate() : null;
            LocalDate notNullEndingDate = (initialContractVO.getEndingDate() != null) ? initialContractVO.getEndingDate().toLocalDate() : null;
                ContractNewVersionDTO contractNewVersionDTO = ContractNewVersionDTO.create()
                        .withId(initialContractVO.getId())
                        .withContractNumber(initialContractVO.getContractNumber())
                        .withStartDate(initialContractVO.getStartDate().toLocalDate())
                        .withExpectedEndDate(notNullExpectedEndDate)
                        .withModificationDate(notNullModificationDate)
                        .withEndingDate(notNullEndingDate)
                        .withContractJsonData(initialContractVO.getContractJsonData())
                        .build();

                contractNewVersionDTOList.add(contractNewVersionDTO);
        }

        ContractVariationDAO contractVariationDAO = ContractVariationDAO.ContractVariationDAOFactory.getInstance();
        List<ContractVariationVO> contractVariationVOList = contractVariationDAO.findAllContractVariationInPeriod(initialDate, finalDate);
        for (ContractVariationVO contractVariationVO : contractVariationVOList) {
            LocalDate notNullExpectedEndDate = contractVariationVO.getExpectedEndDate() != null ? contractVariationVO.getExpectedEndDate().toLocalDate(): null;
            LocalDate notNullModificationDate = contractVariationVO.getModificationDate() != null ? contractVariationVO.getModificationDate().toLocalDate(): null;
            LocalDate notNullEndingDate = (contractVariationVO.getEndingDate() != null) ? contractVariationVO.getEndingDate().toLocalDate() : null;
                ContractNewVersionDTO contractNewVersionDTO = ContractNewVersionDTO.create()
                        .withId(contractVariationVO.getId())
                        .withContractNumber(contractVariationVO.getContractNumber())
                        .withStartDate(contractVariationVO.getStartDate().toLocalDate())
                        .withExpectedEndDate(notNullExpectedEndDate)
                        .withModificationDate(notNullModificationDate)
                        .withEndingDate(notNullEndingDate)
                        .withContractJsonData(contractVariationVO.getContractJsonData())
                        .build();

                contractNewVersionDTOList.add(contractNewVersionDTO);
        }

        return contractNewVersionDTOList;
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

    public List<ContractNewVersionDTO> findAllInitialContractSorted(){
        List<ContractNewVersionDTO> initialContractDTOList = new ArrayList<>();
        MapperInitialContractVODTO mapper = new MapperInitialContractVODTO();

        InitialContractDAO initialContractDAO = InitialContractDAO.InitialContractDAOFactory.getInstance();
        List<InitialContractVO> initialContractVOList = initialContractDAO.findAllInitialContractSorted();
        for (InitialContractVO initialContractVO : initialContractVOList) {
            ContractNewVersionDTO contractNewVersionDTO = ContractNewVersionDTO.create()
                    .withContractNumber(initialContractVO.getContractNumber())
                    .withVariationType(initialContractVO.getVariationType())
                    .withStartDate(initialContractVO.getStartDate().toLocalDate())
                    .withExpectedEndDate(initialContractVO.getExpectedEndDate().toLocalDate())
                    .withModificationDate(initialContractVO.getModificationDate().toLocalDate())
                    .withEndingDate(initialContractVO.getEndingDate().toLocalDate())
                    .build();

            initialContractDTOList.add(contractNewVersionDTO);
        }

        return initialContractDTOList;
    }

    public ContractNewVersionDTO findInitialContractByContractNumber(Integer contractNumber){
        InitialContractDAO initialContractDAO = InitialContractDAO.InitialContractDAOFactory.getInstance();
        InitialContractVO initialContractVO = initialContractDAO.findInitialContractByContractNumber(contractNumber);
        MapperInitialContractVODTO mapper = new MapperInitialContractVODTO();


        ContractNewVersionDTO contractNewVersionDTO = ContractNewVersionDTO.create()
                .withContractNumber(initialContractVO.getContractNumber())
                .withVariationType(initialContractVO.getVariationType())
                .withStartDate(initialContractVO.getStartDate().toLocalDate())
                .withExpectedEndDate(initialContractVO.getExpectedEndDate().toLocalDate())
                .withModificationDate(initialContractVO.getModificationDate().toLocalDate())
                .withEndingDate(initialContractVO.getEndingDate().toLocalDate())
                .withContractJsonData(initialContractVO.getContractJsonData())
                .build();

        return contractNewVersionDTO;
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
                    .withModificationDate(contractVariationVO.getModificationDate().toLocalDate())
                    .withEndingDate(contractVariationVO.getEndingDate().toLocalDate())
                    .withContractJsonData(contractVariationVO.getContractJsonData())

                    .build();

            contractVariationDTOList.add(contractVariationDTO);
        }

        return contractVariationDTOList;
    }
}
