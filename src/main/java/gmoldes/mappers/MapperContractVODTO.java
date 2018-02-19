package gmoldes.mappers;

import gmoldes.domain.dto.ClientDTO;
import gmoldes.domain.dto.ContractDTO;
import gmoldes.persistence.vo.ClientVO;
import gmoldes.persistence.vo.ContractVO;

import java.util.Date;

public class MapperContractVODTO {

    public ContractDTO mapContractVODTO(ContractVO contractVO) {

            ContractDTO contractDTO = ContractDTO.create()
                    .withId(contractVO.getId())
                    .withNumcontrato(contractVO.getNumcontrato())
                    .withNumvariacion(contractVO.getNumvariacion())
                    .withTipovariacion(contractVO.getTipovariacion())
                    .withIdcliente_gm(contractVO.getIdcliente_gm())
                    .withClientegm_name(contractVO.getClientegm_name())
                    .withContrato_ccc(contractVO.getContrato_ccc())
                    .withIdtrabajador(contractVO.getIdtrabajador())
                    .withTrabajador_name(contractVO.getTrabajador_name())
                    .withCategoria(contractVO.getCategoria())
                    .withJor_trab(contractVO.getJor_trab())
                    .withJor_trab_dias(contractVO.getJor_trab_dias())
                    .withJor_tipo(contractVO.getJor_tipo())
                    .withTipoctto(contractVO.getTipoctto())
                    .withF_desde(contractVO.getF_desde())
                    .withF_hasta(contractVO.getF_hasta())
                    .withId_ctto_inem(contractVO.getId_ctto_inem())
                    .withEnvigor(contractVO.getEnvigor())
                    .withNotas_gestor(contractVO.getNotas_gestor())
                    .withNotas_privadas(contractVO.getNotas_privadas())
                    .withDuracion(contractVO.getDuracion())
                    .withSubrogacion(contractVO.getSubrogacion())
                    .withIdc(contractVO.getIdc())
                    .withPreavisofin(contractVO.getPreavisofin())
                    .build();
        return contractDTO;
    }
}
