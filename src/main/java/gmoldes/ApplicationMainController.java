package gmoldes;

import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.dto.ContractFullDataDTO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.person.dto.PersonDTO;

import java.time.LocalDate;
import java.util.List;

public class ApplicationMainController {

    private ApplicationMainManager applicationMainManager = new ApplicationMainManager();


    public List<ClientDTO> findAllClientWithContractInForceAtDate(LocalDate date){

        return applicationMainManager.findAllClientWithContractInForceAtDate(date);
    }

    public List<ContractFullDataDTO> findAllDataForContractInForceByClientId(Integer clientId, LocalDate date){

        return applicationMainManager.findAllContractForContractInForceByClientId(clientId, date);
    }

    public List<ContractNewVersionDTO> findAllContractInForcerInPeriod(LocalDate initialDate, LocalDate finalDate){

        return applicationMainManager.findAllContractInForceInPeriod(initialDate, finalDate);

    }

    public ClientDTO findClientById(Integer clientId){

        return applicationMainManager.retrieveClientByClientId(clientId);
    }

    public PersonDTO findPersonById(Integer personId){

        return applicationMainManager.retrievePersonByPersonID(personId);

    }

    public List<ContractNewVersionDTO> findAllTemporalContractInForceNow() {

        return applicationMainManager.findAllTemporalContractInForceNow();
    }
}
