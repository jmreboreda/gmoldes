package gmoldes.domain.client.controllers;

import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.client.persistence.vo.ClientVO;
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
