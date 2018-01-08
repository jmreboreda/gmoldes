package gmoldes.manager;


import gmoldes.domain.dto.ContractDTO;
import gmoldes.persistence.dao.ContractDAO;
import gmoldes.persistence.vo.ContractVO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContractManager {

    public ContractManager() {
    }

    public List<ContractDTO> findAllContractsByClientIdInPeriod(Integer clientId, Date referenceDate){

        List<ContractDTO> contractDTOList = new ArrayList<>();
        ContractDAO contractDAO = ContractDAO.ContractDAOFactory.getInstance();
        List<ContractVO> contractVOList = contractDAO.findAllContractsByClientIdInPeriod(clientId, referenceDate);
        for (ContractVO contractVO : contractVOList) {
            ContractDTO contractDTO = ContractDTO.create()
                    .withCategoria(contractVO.getCategoria())
                    .withClientegm_name(contractVO.getClientegm_name())
                    .withContrato_ccc(contractVO.getContrato_ccc())
                    .withDuracion(contractVO.getDuracion())
                    .withEnvigor(contractVO.getEnvigor())
                    .withF_desde(contractVO.getF_desde())
                    .withF_hasta(contractVO.getF_hasta())
                    .withId(contractVO.getId())
                    .withId_ctto_inem(contractVO.getId_ctto_inem())
                    .withIdcliente_gm(contractVO.getIdcliente_gm())
                    .withIdtrabajador(contractVO.getIdtrabajador())
                    .withJor_tipo(contractVO.getJor_tipo())
                    .withJor_trab(contractVO.getJor_trab())
                    .withNotas_gestor(contractVO.getNotas_gestor())
                    .withNotas_privadas(contractVO.getNotas_privadas())
                    .withNumcontrato(contractVO.getNumcontrato())
                    .withNumvariacion(contractVO.getNumvariacion())
                    .withSubrogacion(contractVO.getSubrogacion())
                    .withTipoctto(contractVO.getTipoctto())
                    .withTipovariacion(contractVO.getTipovariacion())
                    .withTrabajador_name(contractVO.getTrabajador_name())
                    .build();

            contractDTOList.add(contractDTO);
        }
        return contractDTOList;
    }

    public List<ContractDTO> findAllActiveContractsByClientId(Integer clientId) {

        List<ContractDTO> contractDTOList = new ArrayList<>();
        ContractDAO contractDAO = ContractDAO.ContractDAOFactory.getInstance();
        List<ContractVO> contractVOList = contractDAO.findAllActiveContractsByClientId(clientId);
        for (ContractVO contractVO : contractVOList) {
            ContractDTO contractDTO = ContractDTO.create()
                    .withCategoria(contractVO.getCategoria())
                    .withClientegm_name(contractVO.getClientegm_name())
                    .withContrato_ccc(contractVO.getContrato_ccc())
                    .withDuracion(contractVO.getDuracion())
                    .withEnvigor(contractVO.getEnvigor())
                    .withF_desde(contractVO.getF_desde())
                    .withF_hasta(contractVO.getF_hasta())
                    .withId(contractVO.getId())
                    .withId_ctto_inem(contractVO.getId_ctto_inem())
                    .withIdcliente_gm(contractVO.getIdcliente_gm())
                    .withIdtrabajador(contractVO.getIdtrabajador())
                    .withJor_tipo(contractVO.getJor_tipo())
                    .withJor_trab(contractVO.getJor_trab())
                    .withNotas_gestor(contractVO.getNotas_gestor())
                    .withNotas_privadas(contractVO.getNotas_privadas())
                    .withNumcontrato(contractVO.getNumcontrato())
                    .withNumvariacion(contractVO.getNumvariacion())
                    .withSubrogacion(contractVO.getSubrogacion())
                    .withTipoctto(contractVO.getTipoctto())
                    .withTipovariacion(contractVO.getTipovariacion())
                    .withTrabajador_name(contractVO.getTrabajador_name())
                    .build();

            contractDTOList.add(contractDTO);
        }
        return contractDTOList;
    }
}
