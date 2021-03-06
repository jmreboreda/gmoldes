package gmoldes.domain.client.manager;


import gmoldes.components.contract_variation.persistence.dao.ContractVariationDAO;
import gmoldes.components.contract_variation.persistence.vo.ContractVariationVO;
import gmoldes.components.contract.initial_contract.persistence.dao.InitialContractDAO;
import gmoldes.components.contract.initial_contract.persistence.vo.InitialContractVO;
import gmoldes.components.contract.manager.ContractManager;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.client.mapper.MapperClientVODTO;
import gmoldes.domain.client.persistence.dao.ClientDAO;
import gmoldes.domain.client.persistence.vo.ClientVO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract.persistence.dao.ContractDAO;

import java.text.Collator;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ClientManager {

    private ClientDAO clientDAO;
    private ContractDAO contractDAO;

    public ClientManager() {
    }

    public List<ClientDTO> findAllActiveClient(){
        List<ClientDTO> clientDTOList = new ArrayList<>();

        clientDAO = ClientDAO.ClientDAOFactory.getInstance();
        List<ClientVO> activeClientVO = clientDAO.findAllActiveClient();
        for(ClientVO clientVO: activeClientVO){
            clientDTOList.add(MapperClientVODTO.map(clientVO));
        }

        return clientDTOList;
    }

    public List<ClientDTO> findAllActiveClientByNamePatternInAlphabeticalOrder(String namePattern) {

        List<ClientDTO> clientDTOList = new ArrayList<>();
        clientDAO = ClientDAO.ClientDAOFactory.getInstance();
        List<ClientVO> clientVOList = clientDAO.findAllActiveClientsByNamePattern(namePattern);
        for (ClientVO clientVO : clientVOList) {
            LocalDate dateFrom = clientVO.getDateFrom() != null ? clientVO.getDateFrom().toLocalDate() : null;
            LocalDate dateTo = clientVO.getDateTo() != null ? clientVO.getDateTo().toLocalDate() : null;
            LocalDate withoutActivityDate = clientVO.getWithoutActivity() != null ? clientVO.getWithoutActivity().toLocalDate() : null;

            ClientDTO clientDTO = ClientDTO.create()
                    .withId(clientVO.getId())
                    .withClientId(clientVO.getClientId())
                    .withIsNaturalPerson(clientVO.getNaturalPerson())
                    .withSg21Code(clientVO.getSg21Code())
                    .withDateFrom(dateFrom)
                    .withDateTo(dateTo)
                    .withNieNIF(clientVO.getNieNif())
                    .withSurnames(clientVO.getSurNames())
                    .withSg21Code(clientVO.getSg21Code())
                    .withName(clientVO.getName())
                    .withRzSocial(clientVO.getRzSocial())
                    .withWithOutActivity(withoutActivityDate)
                    .withServicesGM(clientVO.getServicesGM())
                    .withClientCCC(clientVO.getClientCCC())
                    .build();

            clientDTOList.add(clientDTO);
        }

        Collator primaryCollator = Collator.getInstance(new Locale("es","ES"));
        primaryCollator.setStrength(Collator.PRIMARY);

        return clientDTOList
                .stream()
                .sorted(Comparator.comparing(ClientDTO::toString, primaryCollator)).collect(Collectors.toList());
    }

    public List<ClientDTO> findAllClientWithContractNewVersionInMonth(LocalDate dateReceived){
        List<ClientDTO> clientDTOList = new ArrayList<>();
        ContractManager contractManager = new ContractManager();
        contractDAO = ContractDAO.ContractDAOFactory.getInstance();
        List<ContractNewVersionDTO> contractNewVersionDTOList = contractManager.findAllContractNewVersionInMonthOfDate(dateReceived);
        for(ContractNewVersionDTO contractNewVersionDTO : contractNewVersionDTOList){
//            if(!contractNewVersionDTO.getContractJsonData().getWeeklyWorkHours().equals("40:00")) {
                Integer clientId = contractNewVersionDTO.getContractJsonData().getClientGMId();
                clientDAO = ClientDAO.ClientDAOFactory.getInstance();
                ClientVO clientVO = clientDAO.findClientById(clientId);
                ClientDTO clientDTO = ClientDTO.create()
                        .withClientId(contractNewVersionDTO.getContractJsonData().getClientGMId())
                        .withIsNaturalPerson(clientVO.getNaturalPerson())
                        .withSurnames(clientVO.getSurNames())
                        .withName(clientVO.getName())
                        .withRzSocial(clientVO.getRzSocial())
                        .build();

                clientDTOList.add(clientDTO);
//            }
        }
        return clientDTOList;
    }

    public List<ClientDTO> findAllActiveClientWithContractHistory(){
        List<ClientDTO> clientDTOList = new ArrayList<>();

        clientDAO = ClientDAO.ClientDAOFactory.getInstance();
        List<ClientVO> activeClientVOList = clientDAO.findAllActiveClientsInAlphabeticalOrder();
        for(ClientVO clientVO : activeClientVOList){
            if(!clientVO.getClientCCC().isEmpty()){
                ClientDTO clientDTO = ClientDTO.create()
                        .withIsNaturalPerson(clientVO.getNaturalPerson())
                        .withClientId(clientVO.getClientId())
                        .withSurnames(clientVO.getSurNames())
                        .withName(clientVO.getName())
                        .withRzSocial(clientVO.getRzSocial())
                        .build();

                clientDTOList.add(clientDTO);
            }
        }

        return clientDTOList;
    }

    public List<ClientDTO> findAllClientWithContractInForceAtDate(LocalDate date){

        List<ClientDTO> clientDTOList = new ArrayList<>();

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
            ClientDTO clientDTO = clientManager.findClientById(entry.getKey());
            clientDTOList.add(clientDTO);
        }

        return clientDTOList;
    }

    public ClientDTO findClientById(Integer id){
        ClientDAO clientDAO = ClientDAO.ClientDAOFactory.getInstance();
        ClientVO clientVO = clientDAO.findClientById(id);

        LocalDate withOutActivityDate = clientVO.getWithoutActivity() != null ? clientVO.getWithoutActivity().toLocalDate() : null;

        return ClientDTO.create()
                .withId(clientVO.getId())
                .withClientId(clientVO.getClientId())
                .withIsNaturalPerson(clientVO.getNaturalPerson())
                .withSurnames(clientVO.getSurNames())
                .withName(clientVO.getName())
                .withRzSocial(clientVO.getRzSocial())
                .withNieNIF(clientVO.getNieNif())
                .withSg21Code(clientVO.getSg21Code())
                .withServicesGM(clientVO.getServicesGM())
                .withClientCCC(clientVO.getClientCCC())
//                .withContracts(clientVO.getContracts())
                .withWithOutActivity(withOutActivityDate)
                .build();
    }
}
