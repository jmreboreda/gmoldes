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

    public List<ClientDTO> findAllActiveClientWithContractHistory(){

        return clientManager.findAllActiveClientWithContractHistory();

    }

    public List<ClientDTO> findAllClientWithContractInForceAtDate(LocalDate date){

        return clientManager.findAllClientWithContractInForceAtDate(date);
    }

    public ClientDTO findClientById(Integer clientId){

        return clientManager.findClientById(clientId);
    }
}
