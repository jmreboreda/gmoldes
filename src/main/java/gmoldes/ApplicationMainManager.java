package gmoldes;

import gmoldes.components.contract.contract_variation.persistence.vo.ContractVariationVO;
import gmoldes.components.contract.controllers.ContractTypeController;
import gmoldes.components.contract.initial_contract.persistence.dao.ContractVariationDAO;
import gmoldes.components.contract.initial_contract.persistence.dao.InitialContractDAO;
import gmoldes.components.contract.initial_contract.persistence.vo.InitialContractVO;
import gmoldes.domain.client.controllers.ClientController;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.client.manager.ClientManager;
import gmoldes.domain.client.persistence.vo.ClientVO;
import gmoldes.domain.contract.dto.ContractFullDataDTO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract.dto.ContractTypeDTO;
import gmoldes.domain.person.controllers.PersonController;
import gmoldes.domain.person.dto.PersonDTO;
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
        List<InitialContractVO> initialContractVOList = initialContractDAO.findAllDataForInitialContractsInForceAtDate(date);

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

    public List<ContractFullDataDTO> findAllDataForContractInForceByClientId(Integer clientId){

        List<ContractFullDataDTO> contractFullDataDTOList = new ArrayList<>();

        ClientDTO clientDTO = retrieveClientByClientId(clientId);

        // Initial contract
        InitialContractDAO initialContractDAO = InitialContractDAO.InitialContractDAOFactory.getInstance();
        List<InitialContractVO> initialContractVOList = initialContractDAO.findAllDataForInitialContractsInForceAtDate(LocalDate.now());
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

    private ClientDTO retrieveClientByClientId(Integer clientId){
        ClientController clientController = new ClientController();

        return clientController.findClientById(clientId);

    }

    private PersonDTO retrievePersonByPersonID(Integer personId){
        PersonController personController = new PersonController();

        return personController.findPersonById(personId);
    }

    private ContractTypeDTO retrieveContractTypeById(Integer contractTypeId){
        ContractTypeController contractTypeController = new ContractTypeController();

        return contractTypeController.findContractTypeById(contractTypeId);
    }
}
