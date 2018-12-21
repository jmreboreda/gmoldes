package gmoldes.domain.client.mapper;

import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.client.persistence.vo.ClientVO;

import java.time.LocalDate;

public class MapperClientVODTO {

    public static ClientDTO map(ClientVO clientVO) {

        LocalDate dateFrom = clientVO.getDateFrom() != null ? clientVO.getDateFrom().toLocalDate() : null;
        LocalDate dateTo = clientVO.getDateTo() != null ? clientVO.getDateTo().toLocalDate() : null;
        LocalDate withoutActivityDate = clientVO.getWithoutActivity() != null ? clientVO.getWithoutActivity().toLocalDate() : null;

            ClientDTO clientDTO = ClientDTO.create()
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
                    .withWithOutActivity(withoutActivityDate)
                    .withServicesGM(clientVO.getServicesGM())
                    .withClientCCC(clientVO.getClientCCC())
                    .build();

        return clientDTO;
    }
}
