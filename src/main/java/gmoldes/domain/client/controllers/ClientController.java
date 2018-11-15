package gmoldes.domain.client.controllers;

import gmoldes.domain.client.dto.ClientDTO;
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
}
