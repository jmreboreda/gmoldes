package gmoldes.controllers;

import gmoldes.domain.dto.ClientDTO;
import gmoldes.domain.dto.TimeRecordClientDTO;
import gmoldes.manager.ClientManager;

import java.util.List;

public class ClientController {

    ClientManager clientManager = new ClientManager();

    public List<TimeRecordClientDTO> findAllClientWithActiveContractSorted(){

        return clientManager.findAllClientWithActiveContractSorted();
    }
}
