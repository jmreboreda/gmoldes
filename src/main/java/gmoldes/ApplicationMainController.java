package gmoldes;

import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.dto.ContractFullDataDTO;

import java.time.LocalDate;
import java.util.List;

public class ApplicationMainController {

    private ApplicationMainManager applicationMainManager = new ApplicationMainManager();


    public List<ClientDTO> findAllClientWithContractInForceAtDate(LocalDate date){

        return applicationMainManager.findAllClientWithContractInForceAtDate(date);
    }

    public List<ContractFullDataDTO> findAllContractInForceByClientId(Integer clientId){

        return applicationMainManager.findAllContractInForceByClientId(clientId);
    }
}
