package gmoldes.domain.client.mapper;

import gmoldes.domain.client.dto.ClientDTOOk;
import gmoldes.domain.client.persistence.vo.ClientVO;

import java.time.LocalDate;

public class MapperClientVODTO {

    public static ClientDTOOk map(ClientVO clientVO) {

        LocalDate dateFrom = clientVO.getDateFrom() != null ? clientVO.getDateFrom().toLocalDate() : null;
        LocalDate dateTo = clientVO.getDateTo() != null ? clientVO.getDateTo().toLocalDate() : null;
        LocalDate withoutActivityDate = clientVO.getWithoutActivity() != null ? clientVO.getWithoutActivity().toLocalDate() : null;

            ClientDTOOk clientDTO = ClientDTOOk.create()
                    .withId(clientVO.getId())
                    .withClientId(clientVO.getClientId())
                    .withIsNaturalPerson(clientVO.getNaturalPerson())
                    .withNieNIF(clientVO.getNieNif())
                    .withSurnames(clientVO.getSurNames())
                    .withName(clientVO.getName())
                    .withRzSocial(clientVO.getRzSocial())
                    .withSg21Code(clientVO.getSg21Code())
                    .withDateFrom(dateFrom)
                    .withDateTo(dateTo)
                    .withActiveClient(clientVO.getActiveClient())
                    .withWithOutActivity(withoutActivityDate)
                    .withClientType(clientVO.getClientType())
                    .build();

        return clientDTO;
    }
}
