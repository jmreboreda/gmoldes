package gmoldes.mappers;

import gmoldes.domain.dto.ClientDTO;
import gmoldes.persistence.vo.ClientVO;

public class MapperClientVODTO {

    public ClientDTO mapClientVODTO(ClientVO clientVO) {

            ClientDTO clientDTO = ClientDTO.create()
                    .withId(clientVO.getId())
                    .withIdcliente(clientVO.getIdcliente())
                    .withNifcif(clientVO.getNifcif())
                    .withNifcif_dup(clientVO.getNifcif_dup())
                    .withNom_rzsoc(clientVO.getNom_rzsoc())
                    .withNumvez(clientVO.getNumvez())
                    .withCltsg21(clientVO.getCltsg21())
                    .withFdesde(clientVO.getFdesde())
                    .withFhasta(clientVO.getFhasta())
                    .withCltactivo(clientVO.getCltactivo())
                    .withSinactividad(clientVO.getSinactividad())
                    .withTipoclte(clientVO.getTipoclte())
                    .build();
        return clientDTO;
    }
}
