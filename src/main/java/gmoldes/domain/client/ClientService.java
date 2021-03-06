package gmoldes.domain.client;

import gmoldes.domain.client.controllers.ClientController;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.servicegm.ServiceGMService;
import gmoldes.domain.servicegm.dto.ServiceGMDTO;
import gmoldes.domain.servicegm.persistence.vo.ServiceGMVO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClientService {

    private ClientService() {
    }

    public static class ClientServiceFactory {

        private static ClientService clientService;

        public static ClientService getInstance() {
            if(clientService == null) {
                clientService = new ClientService();
            }
            return clientService;
        }
    }

    private static final String SERVICE_FOR_WORK_CONTRACTS = "Asesoría";

    private ClientController clientController = new ClientController();

    public ClientDTO findClientById(Integer clientId){

        return clientController.findClientById(clientId);
    }

    public List<ClientDTO> findAllActiveClient(){

        return clientController.findAllActiveClient();
    }

    public List<ClientDTO> findAllActiveClientWithContractHistory(){

        return clientController.findAllActiveClientWithContractHistory();
    }

    public  List<ClientDTO> findAllClientWithContractInForceAtDate(LocalDate date){

        return clientController.findAllClientWithContractInForceAtDate(date);
    }

    public List<ClientDTO> findAllClientGMWithInvoicesToClaimInPeriod(LocalDate periodInitialDate, LocalDate periodFinalDate){
        List<ServiceGMDTO> serviceGMDTOList = ServiceGMService.findAllClientGMWithInvoicesToClaimInPeriod(periodInitialDate, periodFinalDate);

        List<ClientDTO> clientDTOList = new ArrayList<>();
        for(ServiceGMDTO serviceGMDTO : serviceGMDTOList){
            Integer clientId = serviceGMDTO.getClientId();
            ClientDTO clientDTO = findClientById(clientId);
            if(clientDTO.getWithoutActivity() == null){
                clientDTOList.add(clientDTO);
            }
        }

        return clientDTOList;
    }

    public List<ClientDTO> findAllActiveClientWithAdvisoryServicesByNamePatternInAlphabeticalOrder(String pattern){

        List<ClientDTO> clientDTOListWithAdvisoryServicesByNamePattern = new ArrayList<>();

        ClientController clientController = new ClientController();
        List<ClientDTO> clientDTOList = clientController.findAllActiveClientByNamePatternInAlphabeticalOrder(pattern);
        for(ClientDTO clientDTO : clientDTOList) {
            Set<ServiceGMVO> serviceGMDTOSet = clientDTO.getServicesGM();
            for (ServiceGMVO serviceGMVO : serviceGMDTOSet) {
                if (serviceGMVO.getService().contains(SERVICE_FOR_WORK_CONTRACTS)) {
                    clientDTOListWithAdvisoryServicesByNamePattern.add(clientDTO);
                }
            }
        }

        return clientDTOListWithAdvisoryServicesByNamePattern;
    }
}
