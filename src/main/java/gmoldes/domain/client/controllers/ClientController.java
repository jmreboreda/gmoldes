package gmoldes.domain.client.controllers;

import gmoldes.domain.client.dto.ClientDTOOk;
import gmoldes.domain.client.persistence.vo.ClientVO;
import gmoldes.domain.client.manager.ClientManager;

import java.time.LocalDate;
import java.util.List;

public class ClientController {

    ClientManager clientManager = new ClientManager();

    public List<ClientDTOOk> findAllActiveClientByNamePatternInAlphabeticalOrder(String pattern){

        return clientManager.findAllActiveClientByNamePatternInAlphabeticalOrder(pattern);
    }

    public List<ClientDTOOk> findAllClientWithContractNewVersionInMonth(LocalDate date){

        return clientManager.findAllClientWithContractNewVersionInMonth(date);
    }

    public ClientDTOOk findClientById(Integer clientId){

        ClientVO clientVO = clientManager.findClientById(clientId);

        return ClientDTOOk.create()
                .withId(clientVO.getId())
                .withClientId(clientVO.getClientId())
                .withIsNaturalPerson(clientVO.getNaturalPerson())
                .withIsNaturalPerson(clientVO.getNaturalPerson())
                .withSurnames(clientVO.getSurNames())
                .withName(clientVO.getName())
                .withRzSocial(clientVO.getRzSocial())
                .withClientType(clientVO.getClientType())
                .withNieNIF(clientVO.getNieNif())
                .build();
    }
}
