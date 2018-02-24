package gmoldes.controllers;

import gmoldes.domain.dto.ContractTypeDTO;
import gmoldes.manager.ContractManager;
import gmoldes.manager.ContractTypeManager;

import java.util.List;

public class ContractTypeController {

    public ContractTypeController() {
    }

    public List<ContractTypeDTO> findAllContractTypes() {
        ContractTypeManager manager = new ContractTypeManager();

        return manager.findAllContractTypes();
    }
}
