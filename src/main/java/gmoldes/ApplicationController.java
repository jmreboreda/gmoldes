package gmoldes;

import gmoldes.domain.client.dto.ClientDTO;

import java.time.LocalDate;
import java.util.List;

public class ApplicationController {

    public List<ClientDTO> findAllClientWithContractInForceAtDate(LocalDate date){
        ApplicationManager applicationManager = new ApplicationManager();

        return applicationManager.findAllClientWithContractInForceAtDate(date);
    }
}
