package gmoldes.domain.client.manager;


import gmoldes.components.contract.manager.ContractManager;
import gmoldes.components.contract.new_contract.persistence.dao.ContractDAO;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.client.persistence.dao.ClientDAO;
import gmoldes.domain.client.persistence.vo.ClientVO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;

import java.text.Collator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ClientManager {

    private ClientDAO clientDAO;
    private ContractDAO contractDAO;

    public ClientManager() {
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
                    .withIsNaturalPerson(clientVO.getNaturalPerson())
                    .withActiveClient(clientVO.getActiveClient())
                    .withSg21Code(clientVO.getSg21Code())
                    .withDateFrom(dateFrom)
                    .withDateTo(dateTo)
                    .withNieNIF(clientVO.getNieNif())
                    .withSurnames(clientVO.getSurNames())
                    .withSg21Code(clientVO.getSg21Code())
                    .withName(clientVO.getName())
                    .withRzSocial(clientVO.getRzSocial())
                    .withWithOutActivity(withoutActivityDate)
                    .withClientType(clientVO.getClientType())
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
            if(!contractNewVersionDTO.getContractJsonData().getWeeklyWorkHours().equals("40:00")) {
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
            }
        }
        return clientDTOList;
    }

    public ClientVO findClientById(Integer id){
        ClientDAO clientDAO = ClientDAO.ClientDAOFactory.getInstance();
        ClientVO clientVO = clientDAO.findClientById(id);

        return clientVO;

    }
}
