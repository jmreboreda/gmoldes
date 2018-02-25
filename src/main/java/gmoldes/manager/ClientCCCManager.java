package gmoldes.manager;

import gmoldes.domain.dto.ClientCCCDTO;
import gmoldes.domain.dto.ContractTypeDTO;
import gmoldes.persistence.dao.ClientCCCDAO;
import gmoldes.persistence.dao.ContractTypeDAO;
import gmoldes.persistence.vo.ClientCCCVO;
import gmoldes.persistence.vo.ContractTypeVO;

import java.util.ArrayList;
import java.util.List;

public class ClientCCCManager {

    public ClientCCCManager() {
    }

    public List<ClientCCCDTO> findAllCCCByClientId(Integer id){
        ClientCCCDAO clientCCCDAO = ClientCCCDAO.ClientCCCDAOFactory.getInstance();
        List<ClientCCCVO> clientCCCVOList = clientCCCDAO.findAllCCCByClientId(id);

        List<ClientCCCDTO> clientCCCDTOList = new ArrayList<>();
        for(ClientCCCVO clientCCCVO : clientCCCVOList){
            ClientCCCDTO clientCCCDTO = new ClientCCCDTO(
                    clientCCCVO.getId(),
                    clientCCCVO.getIdcliente(),
                    clientCCCVO.getCcc_inss());
            clientCCCDTOList.add(clientCCCDTO);
        }

        return clientCCCDTOList;
    }
}
