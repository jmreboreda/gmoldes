package gmoldes.controllers;

import gmoldes.domain.dto.ContractDTO;
import gmoldes.manager.ContractManager;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ContractController {

    private ContractManager contractManager = new ContractManager();

    public List<ContractDTO> findAllContractsByClientIdInPeriod(Integer clientId, Date referenceDate){

        return contractManager.findAllContractsByClientIdInPeriod(clientId, referenceDate);
    }

    public List<ContractDTO> findAllActiveContractsByClientId(Integer id){

        return contractManager.findAllActiveContractsByClientId(id);

    }
}
