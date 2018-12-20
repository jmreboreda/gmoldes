package gmoldes.domain.client.mapper;

import gmoldes.domain.client.dto.ClientCCCDTO;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.client.persistence.vo.ClientCCCVO;
import gmoldes.domain.client.persistence.vo.ClientVO;

import java.time.LocalDate;

public class MapperClientCCCVODTO {

    public static ClientCCCDTO map(ClientCCCVO clientCCCVO) {

            return ClientCCCDTO.create()
                    .withId(clientCCCVO.getId())
                    .withClientId(clientCCCVO.getClientVO().getClientId())
                    .withCccInss(clientCCCVO.getCcc_inss())
                    .build();
    }
}
