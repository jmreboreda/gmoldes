package gmoldes.components.contract.controllers;

import gmoldes.domain.contract.dto.ContractTypeDTO;
import gmoldes.components.contract.manager.ContractTypeManager;

import java.util.List;

public class ContractTypeController {

    public ContractTypeController() {
    }

    public List<ContractTypeDTO> findAllContractTypes() {
        ContractTypeManager manager = new ContractTypeManager();

        return manager.findAllContractTypes();
    }

    public ContractTypeDTO findContractTypeById(Integer contractTypeId) {
        ContractTypeManager manager = new ContractTypeManager();

        return manager.findContractTypeById(contractTypeId);
    }
}
