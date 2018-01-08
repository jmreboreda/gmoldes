package gmoldes.manager;


import gmoldes.domain.dto.ClientDTO;
import gmoldes.domain.dto.TimeRecordClientDTO;
import gmoldes.mappers.MapperClientVODTO;
import gmoldes.mappers.MapperContractVODTO;
import gmoldes.persistence.dao.ClientDAO;
import gmoldes.persistence.dao.ContractDAO;
import gmoldes.persistence.vo.ClientVO;
import gmoldes.persistence.vo.ContractVO;

import java.util.ArrayList;
import java.util.List;

public class ClientManager {

    private ClientDAO clientDAO;
    private ContractDAO contractDAO;

    public ClientManager() {
    }

    public List<ClientDTO> findAllActiveClientInAlphabeticalOrder() {

        List<ClientDTO> personDTOList = new ArrayList<>();
        clientDAO = ClientDAO.ClientDAOFactory.getInstance();
        List<ClientVO> clientVOList = clientDAO.findAllActiveClientsInAlphabeticalOrder();
        for (ClientVO clientVO : clientVOList) {
            ClientDTO clientDTO = ClientDTO.create()
                    .withId(clientVO.getId())
                    .withCltactivo(clientVO.getCltactivo())
                    .withCltsg21(clientVO.getCltsg21())
                    .withFdesde(clientVO.getFdesde())
                    .withFhasta(clientVO.getFhasta())
                    .withNifcif(clientVO.getNifcif())
                    .withNifcif_dup(clientVO.getNifcif_dup())
                    .withNom_rzsoc(clientVO.getNom_rzsoc())
                    .withNumvez(clientVO.getNumvez())
                    .withSinactividad(clientVO.getSinactividad())
                    .withTipoclte(clientVO.getTipoclte())
                    .build();

            personDTOList.add(clientDTO);
        }
        return personDTOList;
    }

    public List<ClientDTO> findAllActiveClientByNamePatternInAlphabeticalOrder(String namePattern) {

        List<ClientDTO> personDTOList = new ArrayList<>();
        clientDAO = ClientDAO.ClientDAOFactory.getInstance();
        List<ClientVO> clientVOList = clientDAO.findAllActiveClientsByNamePatternInAlphabeticalOrder(namePattern);
        for (ClientVO clientVO : clientVOList) {
            ClientDTO clientDTO = ClientDTO.create()
                    .withId(clientVO.getId())
                    .withCltactivo(clientVO.getCltactivo())
                    .withCltsg21(clientVO.getCltsg21())
                    .withFdesde(clientVO.getFdesde())
                    .withFhasta(clientVO.getFhasta())
                    .withNifcif(clientVO.getNifcif())
                    .withNifcif_dup(clientVO.getNifcif_dup())
                    .withNom_rzsoc(clientVO.getNom_rzsoc())
                    .withNumvez(clientVO.getNumvez())
                    .withSinactividad(clientVO.getSinactividad())
                    .withTipoclte(clientVO.getTipoclte())
                    .build();

            personDTOList.add(clientDTO);
        }
        return personDTOList;
    }

    public List<TimeRecordClientDTO> findAllClientWithActiveContractSorted(){
        List<TimeRecordClientDTO> clientDTOList = new ArrayList<>();
        contractDAO = ContractDAO.ContractDAOFactory.getInstance();
        MapperContractVODTO mapper = new MapperContractVODTO();
        List<ContractVO> contractVOList = contractDAO.findAllClientWithActiveContractSorted();
        for(ContractVO contractVO : contractVOList){
            TimeRecordClientDTO clientDTO = new TimeRecordClientDTO();
            clientDTO.setIdcliente(contractVO.getIdcliente_gm());
            clientDTO.setNom_rzsoc(contractVO.getClientegm_name());
            clientDTOList.add(clientDTO);
        }
        return clientDTOList;
    }

//    public ClientDTO findPersonById(Integer id){
//
//        clientDAO = ClientDAO.ClientDAOFactory.getInstance();
//        ClientVO clientVO = clientDAO.findClientById(id);
//
//
//        return ClientDTO.create()
//                .withApellido1(clientVO.getApellido1())
//                .withApellido2(clientVO.getApellido2())
//                .withNombre(clientVO.getNombre())
//                .build();

//    public Integer createPerson(ClientDTO personDTO){
//
//        ClientVO clientVO = PersonMapper.mapToVO(personDTO);
//        ClientDAO clientDAO = ClientDAO.ClientDAOFactory.getInstance();
//        Integer id = clientDAO.createClient(clientVO);
//
//        return id;
//    }
}
