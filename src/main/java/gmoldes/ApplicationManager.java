package gmoldes;

import gmoldes.components.contract.contract_variation.persistence.dao.ContractVariationDAO;
import gmoldes.components.contract.contract_variation.persistence.vo.ContractVariationVO;
import gmoldes.components.contract.initial_contract.persistence.dao.InitialContractDAO;
import gmoldes.components.contract.initial_contract.persistence.vo.InitialContractVO;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.client.manager.ClientManager;
import gmoldes.domain.client.persistence.vo.ClientVO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationManager {

    public List<ClientDTO> findAllClientWithContractInForceAtDate(LocalDate date){

        List<ClientDTO> clientDTOList = new ArrayList<>();

        InitialContractDAO initialContractDAO = InitialContractDAO.InitialContractDAOFactory.getInstance();
        List<InitialContractVO> initialContractVOList = initialContractDAO.findAllInitialContractsInForceAtDate(date);

        ContractVariationDAO contractVariationDAO = ContractVariationDAO.ContractVariationDAOFactory.getInstance();
        List<ContractVariationVO> contractVariationVOList = contractVariationDAO.findAllContractVariationsInForceAtDate(date);

        Map<Integer, String> clientIdMap = new HashMap();
        for (InitialContractVO initialContractVO : initialContractVOList){
            clientIdMap.put(initialContractVO.getContractJsonData().getClientGMId(), "");
        }

        for(ContractVariationVO contractVariationVO : contractVariationVOList){
            clientIdMap.put(contractVariationVO.getContractJsonData().getClientGMId(), "");
        }

        ClientManager clientManager = new ClientManager();
        for (Map.Entry<Integer, String> entry : clientIdMap.entrySet()) {
            ClientVO clientVO = clientManager.findClientById(entry.getKey());

            ClientDTO clientDTO = ClientDTO.create()
                    .withId(clientVO.getId())
                    .withPersonOrCompanyName(clientVO.getNom_rzsoc())
                    .build();
            clientDTOList.add(clientDTO);
        }

        return clientDTOList;

    }
}
