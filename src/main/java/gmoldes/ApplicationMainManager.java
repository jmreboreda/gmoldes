package gmoldes;

import gmoldes.components.contract.contract_variation.persistence.dao.ContractVariationDAO;
import gmoldes.components.contract.contract_variation.persistence.vo.ContractVariationVO;
import gmoldes.components.contract.initial_contract.persistence.dao.InitialContractDAO;
import gmoldes.components.contract.initial_contract.persistence.vo.InitialContractVO;
import gmoldes.components.contract.new_contract.mapper.MapperContractTypeVODTO;
import gmoldes.components.contract.new_contract.persistence.dao.ContractTypeDAO;
import gmoldes.components.contract.new_contract.persistence.dao.TypesContractVariationsDAO;
import gmoldes.components.contract.new_contract.persistence.vo.ContractTypeVO;
import gmoldes.components.contract.new_contract.persistence.vo.TypesContractVariationsVO;
import gmoldes.domain.client.dto.ClientDTOOk;
import gmoldes.domain.client.manager.ClientManager;
import gmoldes.domain.client.mapper.MapperClientVODTO;
import gmoldes.domain.client.persistence.dao.ClientDAO;
import gmoldes.domain.client.persistence.vo.ClientVO;
import gmoldes.domain.contract.dto.*;
import gmoldes.domain.contract.mapper.*;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.person.mapper.MapperPersonVODTO;
import gmoldes.domain.person.persistence.dao.PersonDAO;
import gmoldes.domain.person.persistence.vo.PersonVO;
import gmoldes.domain.traceability_contract_documentation.dto.TraceabilityContractDocumentationDTO;
import gmoldes.domain.traceability_contract_documentation.mapper.MapperTraceabilityContractDocumentationVODTO;
import gmoldes.domain.traceability_contract_documentation.persistence.dao.TraceabilityContractDocumentationDAO;
import gmoldes.domain.traceability_contract_documentation.persistence.vo.TraceabilityContractDocumentationVO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationMainManager {

    public List<ClientDTOOk> findAllClientWithContractInForceAtDate(LocalDate date){

        List<ClientDTOOk> clientDTOList = new ArrayList<>();

        // Initial contract
        InitialContractDAO initialContractDAO = InitialContractDAO.InitialContractDAOFactory.getInstance();
        List<InitialContractVO> initialContractVOList = initialContractDAO.findAllInitialContractsInForceAtDate(date);

        // Contract variation
        ContractVariationDAO contractVariationDAO = ContractVariationDAO.ContractVariationDAOFactory.getInstance();
        List<ContractVariationVO> contractVariationVOList = contractVariationDAO.findAllContractVariationsInForceAtDate(date);

        Map<Integer, String> clientIdMap = new HashMap();
        for (InitialContractVO initialContractVO : initialContractVOList){
            clientIdMap.put(initialContractVO.getContractJsonData().getClientGMId(), "");
        }

        for(ContractVariationVO contractVariationVO : contractVariationVOList){
            clientIdMap.put(contractVariationVO.getContractJsonData().getClientGMId(), "");
        }

        ClientManager clientManager = new ClientManager();
        for (Map.Entry<Integer, String> entry : clientIdMap.entrySet()) {
            ClientVO clientVO = clientManager.findClientById(entry.getKey());
            ClientDTOOk clientDTO = ClientDTOOk.create()
                    .withClientId(clientVO.getClientId())
                    .withIsNaturalPerson(clientVO.getNaturalPerson())
                    .withSurnames(clientVO.getSurNames())
                    .withName(clientVO.getName())
                    .withRzSocial(clientVO.getRzSocial())
                    .build();
            clientDTOList.add(clientDTO);
        }

        return clientDTOList;

    }

    public List<ClientDTOOk> findAllClientGMWithInvoiceInForceInPeriod(LocalDate initialDate, LocalDate finalDate){
        ClientDAO clientDAO = ClientDAO.ClientDAOFactory.getInstance();
        List<ClientVO> clientVOList = clientDAO.findAllClientGMWithInvoiceInForceInPeriod(initialDate, finalDate);

        List<ClientDTOOk> clientDTOList = new ArrayList<>();
        for(ClientVO clientVO : clientVOList){
            clientDTOList.add(MapperClientVODTO.map(clientVO));
        }

        return clientDTOList;
    }

    public List<ContractFullDataDTO> findAllDataForContractInForceAtDateByClientId(Integer clientId, LocalDate date){

        InitialContractDAO initialContractDAO = InitialContractDAO.InitialContractDAOFactory.getInstance();
        ContractVariationDAO contractVariationDAO = ContractVariationDAO.ContractVariationDAOFactory.getInstance();
        Map<Integer, LocalDate> initialContractStartDateMap = new HashMap();

        // Get all initial contract date of contract in force at date
        List<InitialContractVO> initialContractInForceAtDate = initialContractDAO.findAllContractInForceAtDate(date);
        for(InitialContractVO initialContractVO : initialContractInForceAtDate){
            initialContractStartDateMap.put(initialContractVO.getContractNumber(), initialContractVO.getStartDate().toLocalDate());
        }

        List<ContractFullDataDTO> contractFullDataDTOList = new ArrayList<>();

        ClientDTOOk clientDTO = retrieveClientByClientId(clientId);

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
                    .build();
            initialContractDTOList.add(initialContractDTO);
        }

        return MapperInitialContractDTOToContractNewVersionDTO.mapInitialContractDTOToContractNewVersionDTO(initialContractDTOList);
    }

    public List<ContractNewVersionDTO> findAllTemporalContractInForceNow() {

        List<ContractNewVersionDTO> contractNewVersionDTOList = new ArrayList<>();

        // Initial contract
        InitialContractDAO initialContractDAO = InitialContractDAO.InitialContractDAOFactory.getInstance();
        List<InitialContractVO> initialContractTemporalInForceNowList = initialContractDAO.findAllInitialContractTemporalInForceNow();

        for (InitialContractVO initialContractVO : initialContractTemporalInForceNowList) {
            ContractNewVersionDTO contractNewVersionDTO = ContractNewVersionDTO.create()
                    .withId(initialContractVO.getId())
                    .withContractNumber(initialContractVO.getContractNumber())
                    .withVariationType(initialContractVO.getVariationType())
                    .withStartDate(initialContractVO.getStartDate().toLocalDate())
                    .withExpectedEndDate(initialContractVO.getExpectedEndDate().toLocalDate())
//                    .withModificationDate(initialContractVO.getModificationDate().toLocalDate())
//                    .withEndingDate(initialContractVO.getEndingDate().toLocalDate())
                    .withContractJsonData(initialContractVO.getContractJsonData())
                    .build();

            contractNewVersionDTOList.add(contractNewVersionDTO);
        }

        // Contract variation
        ContractVariationDAO contractVariationDAO = ContractVariationDAO.ContractVariationDAOFactory.getInstance();
        List<ContractVariationVO> contractVariationTemporalInForceNowList = contractVariationDAO.findAllContractVariationTemporalInForceNow();

        for (ContractVariationVO contractVariationVO : contractVariationTemporalInForceNowList) {
            ContractNewVersionDTO contractNewVersionDTO = ContractNewVersionDTO.create()
                    .withId(contractVariationVO.getId())
                    .withContractNumber(contractVariationVO.getContractNumber())
                    .withVariationType(contractVariationVO.getVariationType())
                    .withStartDate(contractVariationVO.getStartDate().toLocalDate())
                    .withExpectedEndDate(contractVariationVO.getExpectedEndDate().toLocalDate())
//                    .withModificationDate(contractVariationVO.getModificationDate().toLocalDate())
//                    .withEndingDate(contractVariationVO.getEndingDate().toLocalDate())
                    .withContractJsonData(contractVariationVO.getContractJsonData())
                    .build();

            contractNewVersionDTOList.add(contractNewVersionDTO);
        }

        return contractNewVersionDTOList;
    }

    public List<TraceabilityContractDocumentationDTO> findTraceabilityForAllContractWithPendingContractEndNotice(){

        List<TraceabilityContractDocumentationDTO> traceabilityContractDocumentationDTOList = new ArrayList<>();

        TraceabilityContractDocumentationDAO traceabilityContractDocumentationDAO = TraceabilityContractDocumentationDAO.TraceabilityContractDocumentationDAOFactory.getInstance();
        List<TraceabilityContractDocumentationVO> traceabilityContractDocumentationVOList = traceabilityContractDocumentationDAO.findTraceabilityForAllContractWithPendingContractEndNotice();
        for(TraceabilityContractDocumentationVO traceabilityContractDocumentationVO : traceabilityContractDocumentationVOList){
            traceabilityContractDocumentationDTOList.add(MapperTraceabilityContractDocumentationVODTO.map(traceabilityContractDocumentationVO));
        }

        return traceabilityContractDocumentationDTOList;
    }

    public List<TraceabilityContractDocumentationDTO> findTraceabilityForAllContractWithPendingIDC(){

        List<TraceabilityContractDocumentationDTO> traceabilityContractDocumentationDTOList = new ArrayList<>();

        TraceabilityContractDocumentationDAO traceabilityContractDocumentationDAO = TraceabilityContractDocumentationDAO.TraceabilityContractDocumentationDAOFactory.getInstance();
        List<TraceabilityContractDocumentationVO> traceabilityContractDocumentationVOList = traceabilityContractDocumentationDAO.findTraceabilityForAllContractWithPendingIDC();
        for(TraceabilityContractDocumentationVO traceabilityContractDocumentationVO : traceabilityContractDocumentationVOList){
            traceabilityContractDocumentationDTOList.add(MapperTraceabilityContractDocumentationVODTO.map(traceabilityContractDocumentationVO));
        }

        return traceabilityContractDocumentationDTOList;
    }

    public List<ContractNewVersionDTO> findAllContractsInForceAtDate(LocalDate date){

        List<ContractNewVersionDTO> contractNewVersionDTOList = new ArrayList<>();

        // Initial contract
        InitialContractDAO initialContractDAO = InitialContractDAO.InitialContractDAOFactory.getInstance();
        List<InitialContractVO> initialContractVOList = initialContractDAO.findAllInitialContractsInForceAtDate(date);
        for(InitialContractVO initialContractVO : initialContractVOList){
            ContractNewVersionDTO contractNewVersionDTO = MapperInitialContractVOtoContractNewVersionDTO.map(initialContractVO);
            contractNewVersionDTOList.add(contractNewVersionDTO);
        }

        // Contract variation
        ContractVariationDAO contractVariationDAO = ContractVariationDAO.ContractVariationDAOFactory.getInstance();
        List<ContractVariationVO> contractVariationVOList = contractVariationDAO.findAllContractVariationsInForceAtDate(date);
        for(ContractVariationVO contractVariationVO : contractVariationVOList){
            ContractNewVersionDTO contractNewVersionDTO = MapperContractVariationVOtoContractNewVersionDTO.map(contractVariationVO);
            contractNewVersionDTOList.add(contractNewVersionDTO);
        }

        return contractNewVersionDTOList;
    }

    public List<ContractVariationDTO> findAllContractVariationAtDateByContractNumber(LocalDate dateFrom, Integer contractNumber){
        ContractVariationDAO contractVariationDAO = ContractVariationDAO.ContractVariationDAOFactory.getInstance();

        List<ContractVariationVO> contractVariationVOList = contractVariationDAO.findAllContractVariationsInForceAtDate(dateFrom);
        List<ContractVariationDTO> contractVariationDTOList = new ArrayList<>();
        for(ContractVariationVO contractVariationVO : contractVariationVOList){
            ContractVariationDTO contractVariationDTO = MapperContractVariationVODTO.map(contractVariationVO);
            contractVariationDTOList.add(contractVariationDTO);
        }

        return contractVariationDTOList;
    }

    public ClientDTOOk retrieveClientByClientId(Integer clientId){
        ClientDAO clientDAO = ClientDAO.ClientDAOFactory.getInstance();
        ClientVO clientVO = clientDAO.findClientById(clientId);

        return MapperClientVODTO.map(clientVO);
    }

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

    private TypesContractVariationsDTO retrieveTypesContractVariations(Integer variationType){
        TypesContractVariationsDAO typesContractVariationsDAO = TypesContractVariationsDAO.TypesContractVariationsDAOFactory.getInstance();
        TypesContractVariationsVO typesContractVariationsDTO = typesContractVariationsDAO.findTypesContractVariationsById(variationType);

        return MapperTypesContractVariationsVODTO.mapTypesContractVariationsVODTO(typesContractVariationsDTO);
    }
}
