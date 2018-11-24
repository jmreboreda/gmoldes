package gmoldes;

import gmoldes.components.contract.contract_variation.persistence.vo.ContractVariationVO;
import gmoldes.components.contract.initial_contract.persistence.dao.ContractVariationDAO;
import gmoldes.components.contract.initial_contract.persistence.dao.InitialContractDAO;
import gmoldes.components.contract.initial_contract.persistence.vo.InitialContractVO;
import gmoldes.components.contract.new_contract.mapper.MapperContractTypeVODTO;
import gmoldes.components.contract.new_contract.persistence.dao.ContractTypeDAO;
import gmoldes.components.contract.new_contract.persistence.vo.ContractTypeVO;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.client.manager.ClientManager;
import gmoldes.domain.client.mapper.MapperClientVODTO;
import gmoldes.domain.client.persistence.dao.ClientDAO;
import gmoldes.domain.client.persistence.vo.ClientVO;
import gmoldes.domain.contract.dto.ContractFullDataDTO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract.dto.ContractTypeDTO;
import gmoldes.domain.contract.dto.InitialContractDTO;
import gmoldes.domain.contract.mapper.MapperInitialContractDTOToContractNewVersionDTO;
import gmoldes.domain.contract.mapper.MapperInitialContractVODTO;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.person.mapper.MapperPersonVODTO;
import gmoldes.domain.person.persistence.dao.PersonDAO;
import gmoldes.domain.person.persistence.vo.PersonVO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationMainManager {

    public List<ClientDTO> findAllClientWithContractInForceAtDate(LocalDate date){

        List<ClientDTO> clientDTOList = new ArrayList<>();

        // Initial contract
        InitialContractDAO initialContractDAO = InitialContractDAO.InitialContractDAOFactory.getInstance();
        List<InitialContractVO> initialContractVOList = initialContractDAO.findAllInitialContractInForceAtDate(date);

        // Contract variation
        ContractVariationDAO contractVariationDAO = ContractVariationDAO.ContractVariationDAOFactory.getInstance();
        List<ContractVariationVO> contractVariationVOList = contractVariationDAO.findAllDataForContractVariationsInForceAtDate(date);

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
            ClientDTO clientDTO = ClientDTO.create()
                    .withClientId(clientVO.getIdcliente())
                    .withPersonOrCompanyName(clientVO.getNom_rzsoc())
                    .build();
            clientDTOList.add(clientDTO);
        }

        return clientDTOList;

    }

    public List<ContractFullDataDTO> findAllContractInForceByClientId(Integer clientId){

        List<ContractFullDataDTO> contractFullDataDTOList = new ArrayList<>();

        ClientDTO clientDTO = retrieveClientByClientId(clientId);

        // Initial contract
        InitialContractDAO initialContractDAO = InitialContractDAO.InitialContractDAOFactory.getInstance();
        List<InitialContractVO> initialContractVOList = initialContractDAO.findAllInitialContractInForceAtDate(LocalDate.now());
        for(InitialContractVO initialContractVO : initialContractVOList){
            if(initialContractVO.getContractJsonData().getClientGMId().equals(clientDTO.getClientId())){
                LocalDate expectedEndDate = initialContractVO.getExpectedEndDate() != null ? initialContractVO.getExpectedEndDate().toLocalDate() : null;
                LocalDate modificationDate = initialContractVO.getModificationDate() != null ? initialContractVO.getModificationDate().toLocalDate() : null;
                LocalDate endingDate = initialContractVO.getEndingDate() != null ? initialContractVO.getEndingDate().toLocalDate() : null;
                ContractNewVersionDTO contractNewVersionDTO = ContractNewVersionDTO.create()
                        .withId(initialContractVO.getId())
                        .withContractNumber(initialContractVO.getContractNumber())
                        .withVariationType(initialContractVO.getVariationType())
                        .withStartDate(initialContractVO.getStartDate().toLocalDate())
                        .withExpectedEndDate(expectedEndDate)
                        .withModificationDate(modificationDate)
                        .withEndingDate(endingDate)
                        .withContractJsonData(initialContractVO.getContractJsonData())
                        .build();

                PersonDTO employeeDTO = retrievePersonByPersonID(initialContractVO.getContractJsonData().getWorkerId());

                ContractTypeDTO contractTypeDTO = retrieveContractTypeById(initialContractVO.getContractJsonData().getContractType());

                ContractFullDataDTO initialContractFullDataDTO = ContractFullDataDTO.create()
                        .withEmployer(clientDTO)
                        .withEmployee(employeeDTO)
                        .withContractNewVersionDTO(contractNewVersionDTO)
                        .withContractType(contractTypeDTO)
                        .build();

                contractFullDataDTOList.add(initialContractFullDataDTO);
            }
        }

        // Contract variation
        ContractVariationDAO contractVariationDAO = ContractVariationDAO.ContractVariationDAOFactory.getInstance();
        List<ContractVariationVO> contractVariationVOList = contractVariationDAO.findAllDataForContractVariationsInForceAtDate(LocalDate.now());
        for(ContractVariationVO contractVariationVO : contractVariationVOList){
            if(contractVariationVO.getContractJsonData().getClientGMId().equals(clientDTO.getClientId())){
                LocalDate expectedEndDate = contractVariationVO.getExpectedEndDate() != null ? contractVariationVO.getExpectedEndDate().toLocalDate() : null;
                LocalDate modificationDate = contractVariationVO.getModificationDate() != null ? contractVariationVO.getModificationDate().toLocalDate() : null;
                LocalDate endingDate = contractVariationVO.getEndingDate() != null ? contractVariationVO.getEndingDate().toLocalDate() : null;
                ContractNewVersionDTO contractNewVersionDTO = ContractNewVersionDTO.create()
                        .withId(contractVariationVO.getId())
                        .withContractNumber(contractVariationVO.getContractNumber())
                        .withVariationType(contractVariationVO.getVariationType())
                        .withStartDate(contractVariationVO.getStartDate().toLocalDate())
                        .withExpectedEndDate(expectedEndDate)
                        .withModificationDate(modificationDate)
                        .withEndingDate(endingDate)
                        .withContractJsonData(contractVariationVO.getContractJsonData())
                        .build();

                PersonDTO employeeDTO = retrievePersonByPersonID(contractVariationVO.getContractJsonData().getWorkerId());

                ContractTypeDTO contractTypeDTO = retrieveContractTypeById(contractVariationVO.getContractJsonData().getContractType());

                ContractFullDataDTO contractVariationFullDataDTO = ContractFullDataDTO.create()
                        .withEmployer(clientDTO)
                        .withEmployee(employeeDTO)
                        .withContractNewVersionDTO(contractNewVersionDTO)
                        .withContractType(contractTypeDTO)
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

        List<InitialContractDTO> initialContractDTOList = MapperInitialContractVODTO.mapInitialContractVODTO(initialContractVOList);

        return MapperInitialContractDTOToContractNewVersionDTO.mapInitialContractDTOToContractNewVersionDTO(initialContractDTOList);

    }

    public ClientDTO retrieveClientByClientId(Integer clientId){
        ClientDAO clientDAO = ClientDAO.ClientDAOFactory.getInstance();
        ClientVO clientVO = clientDAO.findClientById(clientId);

        return MapperClientVODTO.mapClientVODTO(clientVO);
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
}
