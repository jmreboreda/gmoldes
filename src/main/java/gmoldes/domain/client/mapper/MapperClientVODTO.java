package gmoldes.domain.client.mapper;

import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.client.persistence.vo.ClientVO;

public class MapperClientVODTO {

    public static ClientDTO mapClientVODTO(ClientVO clientVO) {

            ClientDTO clientDTO = ClientDTO.create()
                    .withId(clientVO.getId())
                    .withClientId(clientVO.getIdcliente())
                    .withNieNIF(clientVO.getNifcif())
                    .withNieNIF_dup(clientVO.getNifcif_dup())
                    .withPersonOrCompanyName(clientVO.getNom_rzsoc())
                    .withNumberOfTimes(clientVO.getNumvez())
                    .withCodeInSigaProgram(clientVO.getCltsg21())
                    .withDateFrom(clientVO.getFdesde())
                    .withDateTo(clientVO.getFhasta())
                    .withIsActive(clientVO.getCltactivo())
                    .withWithOutActivity(clientVO.getSinactividad())
                    .withClientType(clientVO.getTipoclte())
                    .build();

        return clientDTO;
    }
}
