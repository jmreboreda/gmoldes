package gmoldes.components.contract.manager;


import gmoldes.components.contract.contract_variation.persistence.dao.ContractVariationDAO;
import gmoldes.components.contract.contract_variation.persistence.vo.ContractVariationVO;
import gmoldes.components.contract.initial_contract.persistence.dao.InitialContractDAO;
import gmoldes.components.contract.initial_contract.persistence.vo.InitialContractVO;
import gmoldes.components.contract.new_contract.mapper.MapperContractTypeVODTO;
import gmoldes.components.contract.new_contract.persistence.dao.ContractTypeDAO;
import gmoldes.components.contract.new_contract.persistence.dao.TypesContractVariationsDAO;
import gmoldes.components.contract.new_contract.persistence.vo.ContractTypeVO;
import gmoldes.components.contract.new_contract.persistence.vo.TypesContractVariationsVO;
import gmoldes.domain.client.ClientService;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.dto.*;
import gmoldes.domain.contract.mapper.*;
import gmoldes.domain.contract.persistence.dao.ContractDAO;
import gmoldes.domain.contract.persistence.vo.ContractVO;
import gmoldes.domain.contract_schedule.dto.ContractScheduleDTO;
import gmoldes.domain.contract_schedule.mappers.MapperContractScheduleDTOVO;
import gmoldes.domain.contract_schedule.persistence.dao.ContractScheduleDAO;
import gmoldes.domain.contract_schedule.persistence.vo.ContractScheduleVO;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.person.mapper.MapperPersonVODTO;
import gmoldes.domain.person.persistence.dao.PersonDAO;
import gmoldes.domain.person.persistence.vo.PersonVO;
import gmoldes.domain.traceability_contract_documentation.dto.TraceabilityContractDocumentationDTO;
import gmoldes.domain.traceability_contract_documentation.mapper.MapperTraceabilityContractDocumentationDTOVO;
import gmoldes.domain.traceability_contract_documentation.persistence.dao.TraceabilityContractDocumentationDAO;
import gmoldes.domain.traceability_contract_documentation.persistence.vo.TraceabilityContractDocumentationVO;

import java.time.LocalDate;
import java.util.*;

public class ContractManager {

    public ContractManager() {
    }

    public Integer saveInitialContract(ContractNewVersionDTO contractNewVersionDTO){
        InitialContractDAO initialContractDAO = InitialContractDAO.InitialContractDAOFactory.getInstance();
        Integer newContractNumber = initialContractDAO.findHighestContractNumber() + 1;
        contractNewVersionDTO.setContractNumber(newContractNumber);
        InitialContractVO initialContractVO = MapperInitialContractDTOVO.map(contractNewVersionDTO);

        return initialContractDAO.create(initialContractVO);
    }

    public Integer saveContract(ContractDTO contractDTO){
        ContractDAO contractDAO = ContractDAO.ContractDAOFactory.getInstance();
        Integer newContractNumber = contractDAO.findHighestContractNumber() + 1;
        contractDTO.setGmContractNumber(newContractNumber);
        ContractVO contractVO = MapperContractDTOVO.map(contractDTO);

        return contractDAO.create(contractVO);
    }




    public Integer saveContractTraceability(TraceabilityContractDocumentationDTO traceabilityDTO){
        TraceabilityContractDocumentationDAO traceabilityContractDocumentationDAO = TraceabilityContractDocumentationDAO.TraceabilityContractDocumentationDAOFactory.getInstance();
        TraceabilityContractDocumentationVO traceabilityVO = MapperTraceabilityContractDocumentationDTOVO.map(traceabilityDTO);

        return traceabilityContractDocumentationDAO.create(traceabilityVO);
    }

    public Integer updateInitialContract(ContractNewVersionDTO contractNewVersionDTO ){
        InitialContractDAO initialContractDAO = InitialContractDAO.InitialContractDAOFactory.getInstance();
        InitialContractVO initialContractVO = MapperInitialContractDTOVO.map(contractNewVersionDTO);

        return initialContractDAO.update(initialContractVO);
    }

    public Integer saveContractVariation(ContractNewVersionDTO contractNewVersionDTO){
        ContractVariationDAO contractVariationDAO = ContractVariationDAO.ContractVariationDAOFactory.getInstance();
        ContractVariationVO contractVariationVO = MapperContractNewVersionDTOtoContractVariationVO.map(contractNewVersionDTO);

        return contractVariationDAO.create(contractVariationVO);
    }

    public Integer updateContractVariation(ContractNewVersionDTO contractNewVersionDTO){
        ContractVariationDAO contractVariationDAO = ContractVariationDAO.ContractVariationDAOFactory.getInstance();
        ContractVariationVO contractVariationVO = MapperContractNewVersionDTOtoContractVariationVO.map(contractNewVersionDTO);

        return contractVariationDAO.update(contractVariationVO);
    }

    public Integer saveContractSchedule(ContractScheduleDTO contractScheduleDTO){
        ContractScheduleDAO contractScheduleDAO = ContractScheduleDAO.ContractScheduleDAOFactory.getInstance();
        ContractScheduleVO contractScheduleVO = MapperContractScheduleDTOVO.map(contractScheduleDTO);

        return contractScheduleDAO.create(contractScheduleVO);
    }

    public List<ContractNewVersionDTO> findHistoryOfContractByContractNumber(Integer contractNumber){

        List<ContractNewVersionDTO> contractNewVersionDTOList = new ArrayList<>();

        InitialContractDAO initialContractDAO =  InitialContractDAO.InitialContractDAOFactory.getInstance();
        InitialContractVO initialContractVO = initialContractDAO.findInitialContractByContractNumber(contractNumber);

        ContractVariationDAO contractVariationDAO = ContractVariationDAO.ContractVariationDAOFactory.getInstance();
        List<ContractVariationVO> contractVariationVOList = contractVariationDAO.findAllContractVariationByContractNumber(contractNumber);

        contractNewVersionDTOList.add(MapperInitialContractVOtoContractNewVersionDTO.map(initialContractVO));
        for(ContractVariationVO contractVariationVO : contractVariationVOList){
            contractNewVersionDTOList.add(MapperContractVariationVOtoContractNewVersionDTO.map(contractVariationVO));
        }

        return contractNewVersionDTOList;
    }

    public List<ContractNewVersionDTO> findAllContractNewVersionByClientIdInMonthOfDate(Integer clientId, LocalDate dateReceived){

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
//        List<InitialContractVO> initialContractVOList = initialContractDAO.findAllInitialContractForTimeRecordInPeriod(initialDate, finalDate);
        List<InitialContractVO> initialContractVOList = initialContractDAO.findAllActiveInitialContractInForceInPeriod(initialDate, finalDate);

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
        List<InitialContractVO> initialContractVOList = initialContractDAO.findAllInitialContractForTimeRecordInPeriod(initialDate, finalDate);
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

    public InitialContractDTO findLastTuplaOfInitialContractByContractNumber(Integer contractNumber){

        InitialContractDAO initialContractDAO = InitialContractDAO.InitialContractDAOFactory.getInstance();
        InitialContractVO initialContractVO = initialContractDAO.findLastTuplaOfInitialContractByContractNumber(contractNumber);

        return MapperInitialContractVODTO.map(initialContractVO);
    }

    public List<ContractVariationDTO> findAllContractVariationsAfterDateByContractNumber(Integer contractNumber, LocalDate dateFromSearch){
        ContractVariationDAO contractVariationDAO = ContractVariationDAO.ContractVariationDAOFactory.getInstance();
        List<ContractVariationVO> contractVariationVOList = contractVariationDAO.findAllContractVariationsAfterDateByContractNumber(contractNumber, dateFromSearch);

        List<ContractVariationDTO> contractVariationDTOList = new ArrayList<>();
        for(ContractVariationVO contractVariationVO : contractVariationVOList){
            ContractVariationDTO contractVariationDTO = MapperContractVariationVODTO.map(contractVariationVO);
            contractVariationDTOList.add(contractVariationDTO);
        }

        return contractVariationDTOList;
    }

    public List<ContractFullDataDTO> findAllDataForContractInForceAtDateByClientId(Integer clientId, LocalDate date){

        InitialContractDAO initialContractDAO = InitialContractDAO.InitialContractDAOFactory.getInstance();
        ContractVariationDAO contractVariationDAO = ContractVariationDAO.ContractVariationDAOFactory.getInstance();
        Map<Integer, LocalDate> initialContractStartDateMap = new HashMap();

        // Get initial contract date of all contract in force at date
        List<InitialContractVO> initialContractInForceAtDate = initialContractDAO.findAllContractInForceAtDate(date);
        for(InitialContractVO initialContractVO : initialContractInForceAtDate){
            initialContractStartDateMap.put(initialContractVO.getContractNumber(), initialContractVO.getStartDate().toLocalDate());
        }

        List<ContractFullDataDTO> contractFullDataDTOList = new ArrayList<>();

        ClientService clientService = ClientService.ClientServiceFactory.getInstance();

        ClientDTO clientDTO = clientService.findClientById(clientId);

        // Initial contract
        List<InitialContractVO> initialContractVOList = initialContractDAO.findAllInitialContractsInForceAtDate(date);
        for(InitialContractVO initialContractVO : initialContractVOList){
            if(initialContractVO.getContractJsonData().getClientGMId().equals(clientDTO.getClientId())){
                LocalDate initialContractDate = initialContractVO.getStartDate().toLocalDate();
                LocalDate expectedEndDate = initialContractVO.getExpectedEndDate() != null ? initialContractVO.getExpectedEndDate().toLocalDate() : null;
                LocalDate modificationDate = initialContractVO.getModificationDate() != null ? initialContractVO.getModificationDate().toLocalDate() : null;
                LocalDate endingDate = initialContractVO.getEndingDate() != null ? initialContractVO.getEndingDate().toLocalDate() : null;
                ContractNewVersionDTO contractNewVersionDTO = ContractNewVersionDTO.create()
                        .withId(initialContractVO.getId())
                        .withContractNumber(initialContractVO.getContractNumber())
                        .withVariationType(initialContractVO.getVariationType())
                        .withInitialContractDate(initialContractDate)
                        .withStartDate(initialContractDate)
                        .withExpectedEndDate(expectedEndDate)
                        .withModificationDate(modificationDate)
                        .withEndingDate(endingDate)
                        .withContractJsonData(initialContractVO.getContractJsonData())
                        .withContractScheduleJsonData(initialContractVO.getContractScheduleJsonData())
                        .build();

                PersonDTO employeeDTO = retrievePersonByPersonID(initialContractVO.getContractJsonData().getWorkerId());

                ContractTypeDTO contractTypeDTO = retrieveContractTypeById(initialContractVO.getContractJsonData().getContractType());

                TypesContractVariationsDTO typesContractVariationsDTO = retrieveTypesContractVariations(initialContractVO.getVariationType());

                ContractFullDataDTO initialContractFullDataDTO = ContractFullDataDTO.create()
                        .withEmployer(clientDTO)
                        .withEmployee(employeeDTO)
                        .withInitialContractDate(initialContractDate)
                        .withContractNewVersionDTO(contractNewVersionDTO)
                        .withContractType(contractTypeDTO)
                        .withTypesContractVariationsDTO(typesContractVariationsDTO)
                        .build();

                contractFullDataDTOList.add(initialContractFullDataDTO);
            }
        }

        // Contract variation
        List<ContractVariationVO> contractVariationVOList = contractVariationDAO.findAllContractVariationsInForceAtDate(date);
        for(ContractVariationVO contractVariationVO : contractVariationVOList){
            if(contractVariationVO.getContractJsonData().getClientGMId().equals(clientDTO.getClientId())){
                LocalDate initialContractDate = initialContractStartDateMap.get(contractVariationVO.getContractNumber());
                LocalDate expectedEndDate = contractVariationVO.getExpectedEndDate() != null ? contractVariationVO.getExpectedEndDate().toLocalDate() : null;
                LocalDate modificationDate = contractVariationVO.getModificationDate() != null ? contractVariationVO.getModificationDate().toLocalDate() : null;
                LocalDate endingDate = contractVariationVO.getEndingDate() != null ? contractVariationVO.getEndingDate().toLocalDate() : null;

                ContractNewVersionDTO contractNewVersionDTO = ContractNewVersionDTO.create()
                        .withId(contractVariationVO.getId())
                        .withContractNumber(contractVariationVO.getContractNumber())
                        .withVariationType(contractVariationVO.getVariationType())
                        .withInitialContractDate(initialContractDate)
                        .withStartDate(contractVariationVO.getStartDate().toLocalDate())
                        .withExpectedEndDate(expectedEndDate)
                        .withModificationDate(modificationDate)
                        .withEndingDate(endingDate)
                        .withContractJsonData(contractVariationVO.getContractJsonData())
                        .withContractScheduleJsonData(contractVariationVO.getContractScheduleJsonData())
                        .build();

                PersonDTO employeeDTO = retrievePersonByPersonID(contractVariationVO.getContractJsonData().getWorkerId());

                ContractTypeDTO contractTypeDTO = retrieveContractTypeById(contractVariationVO.getContractJsonData().getContractType());

                TypesContractVariationsDTO typesContractVariationsDTO = retrieveTypesContractVariations(contractVariationVO.getVariationType());

                ContractFullDataDTO contractVariationFullDataDTO = ContractFullDataDTO.create()
                        .withEmployer(clientDTO)
                        .withEmployee(employeeDTO)
                        .withInitialContractDate(initialContractDate)
                        .withContractNewVersionDTO(contractNewVersionDTO)
                        .withContractType(contractTypeDTO)
                        .withTypesContractVariationsDTO(typesContractVariationsDTO)
                        .build();

                contractFullDataDTOList.add(contractVariationFullDataDTO);
            }
        }

        return contractFullDataDTOList;
    }

//    public ClientDTO retrieveClientByClientId(Integer clientId){
//
//        List<ClientDTO> clientDTOList = new ArrayList<>();
//        ClientDAO clientDAO = ClientDAO.ClientDAOFactory.getInstance();
//
//        return MapperClientVODTO.map(clientDAO.findClientById(clientId));
//
//    }

    public PersonDTO retrievePersonByPersonID(Integer personId){
        PersonDAO personDAO = PersonDAO.PersonDAOFactory.getInstance();
        PersonVO personVO = personDAO.findPersonById(personId);

        return MapperPersonVODTO.mapPersonVODTO(personVO);
    }

    private ContractTypeDTO retrieveContractTypeById(Integer contractTypeId){
        ContractTypeDAO contractTypeDAO = ContractTypeDAO.ContractTypeDAOFactory.getInstance();
        ContractTypeVO contractTypeVO = contractTypeDAO.findContractTypeById(contractTypeId);

        return MapperContractTypeVODTO.mapContractTypeVODTO(contractTypeVO);
    }

    public TypesContractVariationsDTO retrieveTypesContractVariations(Integer variationType){
        TypesContractVariationsDAO typesContractVariationsDAO = TypesContractVariationsDAO.TypesContractVariationsDAOFactory.getInstance();
        TypesContractVariationsVO typesContractVariationsDTO = typesContractVariationsDAO.findTypesContractVariationsById(variationType);

        return MapperTypesContractVariationsVODTO.mapTypesContractVariationsVODTO(typesContractVariationsDTO);
    }

    public List<ContractNewVersionDTO> findAllInitialContractSorted(){
        List<ContractNewVersionDTO> initialContractDTOList = new ArrayList<>();
        MapperInitialContractVODTO mapper = new MapperInitialContractVODTO();

        InitialContractDAO initialContractDAO = InitialContractDAO.InitialContractDAOFactory.getInstance();
        List<InitialContractVO> initialContractVOList = initialContractDAO.findAllInitialContractSorted();
        for (InitialContractVO initialContractVO : initialContractVOList) {
            LocalDate expectedEndDate = initialContractVO.getExpectedEndDate() != null ? initialContractVO.getExpectedEndDate().toLocalDate() : null;
            LocalDate modificationEndDate = initialContractVO.getModificationDate() != null ? initialContractVO.getModificationDate().toLocalDate() : null;
            LocalDate endingDate = initialContractVO.getEndingDate() != null ? initialContractVO.getEndingDate().toLocalDate() : null;

            ContractNewVersionDTO contractNewVersionDTO = ContractNewVersionDTO.create()
                    .withContractNumber(initialContractVO.getContractNumber())
                    .withVariationType(initialContractVO.getVariationType())
                    .withStartDate(initialContractVO.getStartDate().toLocalDate())
                    .withExpectedEndDate(expectedEndDate)
                    .withModificationDate(modificationEndDate)
                    .withEndingDate(endingDate)
                    .build();

            initialContractDTOList.add(contractNewVersionDTO);
        }

        return initialContractDTOList;
    }

    public ContractNewVersionDTO findInitialContractByContractNumber(Integer contractNumber){
        InitialContractDAO initialContractDAO = InitialContractDAO.InitialContractDAOFactory.getInstance();
        InitialContractVO initialContractVO = initialContractDAO.findInitialContractByContractNumber(contractNumber);

        LocalDate expectedEndDate = initialContractVO.getExpectedEndDate() != null ? initialContractVO.getExpectedEndDate().toLocalDate() : null;
        LocalDate modificationDate = initialContractVO.getModificationDate() != null ? initialContractVO.getModificationDate().toLocalDate() : null;
        LocalDate endingDate = initialContractVO.getEndingDate()!= null ? initialContractVO.getEndingDate().toLocalDate() : null;

        ContractNewVersionDTO contractNewVersionDTO = ContractNewVersionDTO.create()
                .withContractNumber(initialContractVO.getContractNumber())
                .withVariationType(initialContractVO.getVariationType())
                .withStartDate(initialContractVO.getStartDate().toLocalDate())
                .withExpectedEndDate(expectedEndDate)
                .withModificationDate(modificationDate)
                .withEndingDate(endingDate)
                .withContractJsonData(initialContractVO.getContractJsonData())
                .build();

        return contractNewVersionDTO;
    }

    public List<ContractVariationDTO> findAllContractVariationByContractNumber(Integer contractNumber){
        List<ContractVariationDTO> contractVariationDTOList = new ArrayList<>();
        ContractVariationDAO contractVariationDAO = ContractVariationDAO.ContractVariationDAOFactory.getInstance();
        List<ContractVariationVO> contractVariationVOList = contractVariationDAO.findAllContractVariationByContractNumber(contractNumber);

        for(ContractVariationVO contractVariationVO : contractVariationVOList) {

            ContractVariationDTO contractVariationDTO = MapperContractVariationVODTO.map(contractVariationVO);

            contractVariationDTOList.add(contractVariationDTO);
        }

        return contractVariationDTOList;
    }

    public List<ContractNewVersionDTO> findAllContractsInForceNow(){

        // Initial contract
        List<ContractNewVersionDTO> contractNewVersionDTOList = new ArrayList<>();
        InitialContractDAO initialContractDAO = InitialContractDAO.InitialContractDAOFactory.getInstance();
        List<InitialContractVO> initialContractVOList = initialContractDAO.findAllInitialContractsInForceAtDate(LocalDate.now());
        for(InitialContractVO initialContractVO : initialContractVOList){
            ContractNewVersionDTO contractNewVersionDTO = ContractNewVersionDTO.create()
                    .withStartDate(initialContractVO.getStartDate().toLocalDate())
                    .withExpectedEndDate(initialContractVO.getExpectedEndDate().toLocalDate())
                    .withModificationDate(initialContractVO.getModificationDate().toLocalDate())
                    .withEndingDate(initialContractVO.getEndingDate().toLocalDate())
                    .withContractJsonData(initialContractVO.getContractJsonData())
                    .build();

            contractNewVersionDTOList.add(contractNewVersionDTO);
        }

        //Contract variation
        ContractVariationDAO contractVariationDAO = ContractVariationDAO.ContractVariationDAOFactory.getInstance();
        List<ContractVariationVO> contractVariationVOList = contractVariationDAO.findAllContractVariationsInForceAtDate(LocalDate.now());
        for(ContractVariationVO contractVariationVO : contractVariationVOList){
            ContractNewVersionDTO contractNewVersionDTO = ContractNewVersionDTO.create()
                    .withStartDate(contractVariationVO.getStartDate().toLocalDate())
                    .withExpectedEndDate(contractVariationVO.getExpectedEndDate().toLocalDate())
                    .withModificationDate(contractVariationVO.getModificationDate().toLocalDate())
                    .withEndingDate(contractVariationVO.getEndingDate().toLocalDate())
                    .withContractJsonData(contractVariationVO.getContractJsonData())
                    .build();

            contractNewVersionDTOList.add(contractNewVersionDTO);
        }

        return contractNewVersionDTOList;
    }

    public List<ContractNewVersionDTO> findAllContractInForceInPeriod(LocalDate initialDate, LocalDate finalDate){

        // Initial contract
        InitialContractDAO initialContractDAO = InitialContractDAO.InitialContractDAOFactory.getInstance();
        List<InitialContractVO> initialContractVOList = initialContractDAO.findAllInitialContractInForceInPeriod(initialDate, finalDate);

        List<InitialContractDTO> initialContractDTOList = new ArrayList<>();
        for(InitialContractVO initialContractVO : initialContractVOList){
            InitialContractDTO initialContractDTO = MapperInitialContractVODTO.map(initialContractVO);
            initialContractDTOList.add(initialContractDTO);
        }

        // Contract variation
        ContractVariationDAO contractVariationDAO = ContractVariationDAO.ContractVariationDAOFactory.getInstance();
        List<ContractVariationVO> contractVariationVOList = contractVariationDAO.findAllContractVariationInForceInPeriod(initialDate, finalDate);

        for(ContractVariationVO contractVariationVO : contractVariationVOList){
            LocalDate expectedEndDate = contractVariationVO.getExpectedEndDate() != null ? contractVariationVO.getExpectedEndDate().toLocalDate() : null;
            LocalDate modificationDate = contractVariationVO.getModificationDate() != null ? contractVariationVO.getModificationDate().toLocalDate() : null;
            LocalDate endingDate = contractVariationVO.getEndingDate() != null ? contractVariationVO.getEndingDate().toLocalDate() : null;
            InitialContractDTO initialContractDTO = InitialContractDTO.create()
                    .withId(contractVariationVO.getId())
                    .withContractNumber(contractVariationVO.getContractNumber())
                    .withVariationType(contractVariationVO.getVariationType())
                    .withStartDate(contractVariationVO.getStartDate().toLocalDate())
                    .withExpectedEndDate(expectedEndDate)
                    .withModificationDate(modificationDate)
                    .withEndingDate(endingDate)
                    .withContractJsonData(contractVariationVO.getContractJsonData())
                    .withContractScheduleJsonData(contractVariationVO.getContractScheduleJsonData())
                    .build();
            initialContractDTOList.add(initialContractDTO);
        }

        return MapperInitialContractDTOToContractNewVersionDTO.mapInitialContractDTOToContractNewVersionDTO(initialContractDTOList);
    }
}
