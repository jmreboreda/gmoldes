package gmoldes.domain.client.manager;


import gmoldes.components.contract.manager.ContractManager;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.timerecord.dto.TimeRecordClientDTO;
import gmoldes.domain.client.persistence.dao.ClientDAO;
import gmoldes.components.contract.new_contract.persistence.dao.ContractDAO;
import gmoldes.domain.client.persistence.vo.ClientVO;
import gmoldes.components.contract.new_contract.persistence.vo.ContractVO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientManager {

    private ClientDAO clientDAO;
    private ContractDAO contractDAO;

    public ClientManager() {
    }

    public List<ClientDTO> findAllActiveClientByNamePatternInAlphabeticalOrder(String namePattern) {

        List<ClientDTO> personDTOList = new ArrayList<>();
        clientDAO = ClientDAO.ClientDAOFactory.getInstance();
        List<ClientVO> clientVOList = clientDAO.findAllActiveClientsByNamePatternInAlphabeticalOrder(namePattern);
        for (ClientVO clientVO : clientVOList) {
            ClientDTO clientDTO = ClientDTO.create()
                    .withId(clientVO.getId())
                    .withIsActive(clientVO.getCltactivo())
                    .withCodeInSigaProgram(clientVO.getCltsg21())
                    .withDateFrom(clientVO.getFdesde())
                    .withDateTo(clientVO.getFhasta())
                    .withNieNIF(clientVO.getNifcif())
                    .withNieNIF_dup(clientVO.getNifcif_dup())
                    .withPersonOrCompanyName(clientVO.getNom_rzsoc())
                    .withNumberOfTimes(clientVO.getNumvez())
                    .withWithOutActivity(clientVO.getSinactividad())
                    .withClientType(clientVO.getTipoclte())
                    .build();

            personDTOList.add(clientDTO);
        }
        return personDTOList;
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
                ClientVO clientVO = clientDAO.findClientByClientId(clientId);
                ClientDTO clientDTO = ClientDTO.create()
                        .withClientId(contractNewVersionDTO.getContractJsonData().getClientGMId())
                        .withPersonOrCompanyName(clientVO.getNom_rzsoc())
                        .build();

                clientDTOList.add(clientDTO);
            }
        }
        return clientDTOList;
    }
}
