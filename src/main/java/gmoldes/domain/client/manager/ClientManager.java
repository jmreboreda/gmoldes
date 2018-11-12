package gmoldes.domain.client.manager;


import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.timerecord.dto.TimeRecordClientDTO;
import gmoldes.domain.client.persistence.dao.ClientDAO;
import gmoldes.components.contract.new_contract.persistence.dao.ContractDAO;
import gmoldes.domain.client.persistence.vo.ClientVO;
import gmoldes.components.contract.new_contract.persistence.vo.ContractVO;

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
                    .withIsActive(clientVO.getCltactivo())
                    .withCodeInSigaProgram(clientVO.getCltsg21())
                    .withDateFrom(clientVO.getFdesde())
                    .withDateTo(clientVO.getFhasta())
                    .withNieNIF(clientVO.getNifcif())
                    .withNieNIF_dup(clientVO.getNifcif_dup())
                    .withPersonOrCompanyName(clientVO.getNom_rzsoc())
                    .withNumberOfTimes(clientVO.getNumvez())
                    .withWithOutActivity(clientVO.getSinactividad())
                    .withClientType(clientVO.getTipoclte())
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
                    .withIsActive(clientVO.getCltactivo())
                    .withCodeInSigaProgram(clientVO.getCltsg21())
                    .withDateFrom(clientVO.getFdesde())
                    .withDateTo(clientVO.getFhasta())
                    .withNieNIF(clientVO.getNifcif())
                    .withNieNIF_dup(clientVO.getNifcif_dup())
                    .withPersonOrCompanyName(clientVO.getNom_rzsoc())
                    .withNumberOfTimes(clientVO.getNumvez())
                    .withWithOutActivity(clientVO.getSinactividad())
                    .withClientType(clientVO.getTipoclte())
                    .build();

            personDTOList.add(clientDTO);
        }
        return personDTOList;
    }

    public List<TimeRecordClientDTO> findAllClientWithActiveContractSorted(){
        List<TimeRecordClientDTO> clientDTOList = new ArrayList<>();
        contractDAO = ContractDAO.ContractDAOFactory.getInstance();
        List<ContractVO> contractVOList = contractDAO.findAllClientWithActiveContractWithTimeRecordSorted();
        for(ContractVO contractVO : contractVOList){
            TimeRecordClientDTO clientDTO = new TimeRecordClientDTO();
            clientDTO.setIdcliente(contractVO.getIdcliente_gm());
            clientDTO.setNom_rzsoc(contractVO.getClientegm_name());
            clientDTOList.add(clientDTO);
        }
        return clientDTOList;
    }

    public List<TimeRecordClientDTO> findAllClientWithActiveContractWithTimeRecordSorted(){
        List<TimeRecordClientDTO> clientDTOList = new ArrayList<>();
        contractDAO = ContractDAO.ContractDAOFactory.getInstance();
        List<ContractVO> contractVOList = contractDAO.findAllClientWithActiveContractWithTimeRecordSorted();
        for(ContractVO contractVO : contractVOList){
            TimeRecordClientDTO clientDTO = new TimeRecordClientDTO();
            clientDTO.setIdcliente(contractVO.getIdcliente_gm());
            clientDTO.setNom_rzsoc(contractVO.getClientegm_name());
            clientDTOList.add(clientDTO);
        }
        return clientDTOList;
    }

    public List<TimeRecordClientDTO> findAllClientWithContractWithTimeRecordInPeriodSorted(String yearMonth){
        List<TimeRecordClientDTO> clientDTOList = new ArrayList<>();
        contractDAO = ContractDAO.ContractDAOFactory.getInstance();
        List<ContractVO> contractVOList = contractDAO.findAllClientWithContractWithTimeRecordInPeriodSorted(yearMonth);
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
