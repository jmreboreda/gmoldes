package gmoldes.controllers;

import gmoldes.domain.dto.ClientCCCDTO;
import gmoldes.domain.dto.ContractTypeDTO;
import gmoldes.manager.ClientCCCManager;
import gmoldes.manager.ContractTypeManager;

import java.util.List;

public class ClientCCCController {

    public ClientCCCController() {
    }

    public List<ClientCCCDTO> findAllCCCByClientId(Integer id) {
        ClientCCCManager manager = new ClientCCCManager();

        return manager.findAllCCCByClientId(id);
    }
}
