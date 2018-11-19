package gmoldes.domain.client.controllers;

import gmoldes.domain.client.Client;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.client.persistence.vo.ClientVO;
import gmoldes.domain.timerecord.dto.TimeRecordClientDTO;
import gmoldes.domain.client.manager.ClientManager;

import java.time.LocalDate;
import java.util.List;

public class ClientController {

    ClientManager clientManager = new ClientManager();

    public List<ClientDTO> findAllActiveClientByNamePatternInAlphabeticalOrder(String pattern){

        return clientManager.findAllActiveClientByNamePatternInAlphabeticalOrder(pattern);
    }

    public List<ClientDTO> findAllClientWithContractNewVersionInMonth(LocalDate date){

        return clientManager.findAllClientWithContractNewVersionInMonth(date);
    }

    public ClientDTO findClientById(Integer clientId){

        ClientVO clientVO = clientManager.findClientById(clientId);

        return ClientDTO.create()
                .withId(clientVO.getId())
                .withClientId(clientVO.getIdcliente())
                .withPersonOrCompanyName(clientVO.getNom_rzsoc())
                .withClientType(clientVO.getTipoclte())
                .withNieNIF(clientVO.getNifcif())
                .build();
    }
}
