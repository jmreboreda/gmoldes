package gmoldes.domain.servicegm.mapper;

import gmoldes.domain.servicegm.dto.ServiceGMDTO;
import gmoldes.domain.servicegm.persistence.vo.ServiceGMVO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MapperServiceGMVODTO {

    public MapperServiceGMVODTO() {
    }

    public static List<ServiceGMDTO> map(List<ServiceGMVO> serviceGMVOList){

        List<ServiceGMDTO> serviceGMDTOList = new ArrayList<>();

        for(ServiceGMVO serviceGMVO : serviceGMVOList){

            LocalDate dateFrom = serviceGMVO.getDateFrom() != null ? serviceGMVO.getDateFrom().toLocalDate() : null;
            LocalDate dateTo = serviceGMVO.getDateTo() != null ? serviceGMVO.getDateTo().toLocalDate() : null;

            ServiceGMDTO serviceGMDTO = ServiceGMDTO.create()
                    .withId(serviceGMVO.getId())
                    .withClientId(serviceGMVO.getClientVO().getClientId())
                    .withDateFrom(dateFrom)
                    .withDateTo(dateTo)
                    .withService(serviceGMVO.getService())
                    .withClaimInvoices(serviceGMVO.getClaimInvoices())
                    .build();

            serviceGMDTOList.add(serviceGMDTO);
        }

        return serviceGMDTOList;
    }
}
