package gmoldes.domain.client.controllers;

import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.timerecord.dto.TimeRecordClientDTO;
import gmoldes.domain.client.manager.ClientManager;

import java.util.List;

public class ClientController {

    ClientManager clientManager = new ClientManager();

    public List<TimeRecordClientDTO> findAllClientWithActiveContractSorted(){

        return clientManager.findAllClientWithActiveContractSorted();
    }

    public List<TimeRecordClientDTO> findAllClientWithActiveContractWithTimeRecordSorted(){

        return clientManager.findAllClientWithActiveContractWithTimeRecordSorted();
    }

    public List<TimeRecordClientDTO> findAllClientWithContractWithTimeRecordInPeriodSorted(String yearMonth){

        return clientManager.findAllClientWithContractWithTimeRecordInPeriodSorted(yearMonth);
    }

    public List<ClientDTO> findAllActiveClientByNamePatternInAlphabeticalOrder(String pattern){
        return clientManager.findAllActiveClientByNamePatternInAlphabeticalOrder(pattern);
    }
}
