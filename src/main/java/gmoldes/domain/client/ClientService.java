package gmoldes.domain.client;

import gmoldes.domain.client.controllers.ClientController;
import gmoldes.domain.client.dto.ClientDTO;

public class ClientService {

    public ClientService() {
    }

    private static ClientController clientController = new ClientController();


    public static ClientDTO findClientById(Integer clientId){

        return clientController.findClientById(clientId);
    }
}
