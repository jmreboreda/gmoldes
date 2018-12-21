package gmoldes.domain.client.manager;

import gmoldes.domain.client.dto.ClientCCCDTO;
import gmoldes.domain.client.persistence.dao.ClientCCCDAO;
import gmoldes.domain.client.persistence.vo.ClientCCCVO;

import java.util.ArrayList;
import java.util.List;

public class ClientCCCManager {

    public ClientCCCManager() {
    }

//    public List<ClientCCCDTO> findAllCCCByClientId(Integer id){
//        ClientCCCDAO clientCCCDAO = ClientCCCDAO.ClientCCCDAOFactory.getInstance();
//        List<ClientCCCVO> clientCCCVOList = clientCCCDAO.findAllCCCByClientId(id);
//
//        List<ClientCCCDTO> clientCCCDTOList = new ArrayList<>();
//        for(ClientCCCVO clientCCCVO : clientCCCVOList){
//            ClientCCCDTO clientCCCDTO = new ClientCCCDTO(
//                    clientCCCVO.getId(),
//                    clientCCCVO.getIdcliente(),
//                    clientCCCVO.getCcc_inss());
//            clientCCCDTOList.add(clientCCCDTO);
//        }
//
//        return clientCCCDTOList;
//    }
}
