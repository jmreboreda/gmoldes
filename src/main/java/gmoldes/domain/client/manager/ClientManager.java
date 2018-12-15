package gmoldes.domain.client.manager;


import gmoldes.components.contract.manager.ContractManager;
import gmoldes.components.contract.new_contract.persistence.dao.ContractDAO;
import gmoldes.domain.client.dto.ClientDTOOk;
import gmoldes.domain.client.persistence.dao.ClientDAO;
import gmoldes.domain.client.persistence.vo.ClientVOOk;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientManager {

    private ClientDAO clientDAO;
    private ContractDAO contractDAO;

    public ClientManager() {
    }

    public List<ClientDTOOk> findAllActiveClientByNamePatternInAlphabeticalOrder(String namePattern) {

        List<ClientDTOOk> personDTOList = new ArrayList<>();
        clientDAO = ClientDAO.ClientDAOFactory.getInstance();
        List<ClientVOOk> clientVOList = clientDAO.findAllActiveClientsByNamePatternInAlphabeticalOrder(namePattern);
        for (ClientVOOk clientVO : clientVOList) {
            ClientDTOOk clientDTO = ClientDTOOk.create()
                    .withId(clientVO.getId())
                    .withActiveClient(clientVO.getActiveClient())
                    .withSg21Code(clientVO.getSg21Code())
                    .withDateFrom(clientVO.getDateFrom().toLocalDate())
                    .withDateTo(clientVO.getDateTo().toLocalDate())
                    .withNieNIF(clientVO.getNieNif())
                    .withSg21Code(clientVO.getSurNames())
                    .withName(clientVO.getName())
                    .withWithOutActivity(clientVO.getWithoutActivity().toLocalDate())
                    .withClientType(clientVO.getClientType())
                    .build();

            personDTOList.add(clientDTO);
        }
        return personDTOList;
    }

    public List<ClientDTOOk> findAllClientWithContractNewVersionInMonth(LocalDate dateReceived){
        List<ClientDTOOk> clientDTOList = new ArrayList<>();
        ContractManager contractManager = new ContractManager();
        contractDAO = ContractDAO.ContractDAOFactory.getInstance();
        List<ContractNewVersionDTO> contractNewVersionDTOList = contractManager.findAllContractNewVersionInMonthOfDate(dateReceived);
        for(ContractNewVersionDTO contractNewVersionDTO : contractNewVersionDTOList){
            if(!contractNewVersionDTO.getContractJsonData().getWeeklyWorkHours().equals("40:00")) {
                Integer clientId = contractNewVersionDTO.getContractJsonData().getClientGMId();
                clientDAO = ClientDAO.ClientDAOFactory.getInstance();
                ClientVOOk clientVO = clientDAO.findClientById(clientId);
                ClientDTOOk clientDTO = ClientDTOOk.create()
                        .withClientId(contractNewVersionDTO.getContractJsonData().getClientGMId())
                        .withIsNaturalPerson(clientVO.getNaturalPerson())
                        .withSurnames(clientVO.getSurNames())
                        .withName(clientVO.getName())
                        .withRzSocial(clientVO.getRzSocial())
                        .build();

                clientDTOList.add(clientDTO);
            }
        }
        return clientDTOList;
    }

    public ClientVOOk findClientById(Integer id){
        ClientDAO clientDAO = ClientDAO.ClientDAOFactory.getInstance();
        ClientVOOk clientVO = clientDAO.findClientById(id);

        return clientVO;

    }
}
