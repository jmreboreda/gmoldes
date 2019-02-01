package gmoldes.domain.servicegm.controllers;

import gmoldes.domain.servicegm.mapper.MapperServiceGMVODTO;
import gmoldes.domain.servicegm.dto.ServiceGMDTO;
import gmoldes.domain.servicegm.manager.ServiceGMManager;
import gmoldes.domain.servicegm.persistence.vo.ServiceGMVO;

import java.time.LocalDate;
import java.util.List;

public class ServiceGMController {

    public ServiceGMController() {
    }

    public List<ServiceGMDTO> findAllClientGMWithInvoicesToClaimInPeriod(LocalDate periodInitialDate, LocalDate periodFinalDate){

        ServiceGMManager serviceGMManager = new ServiceGMManager();

        List<ServiceGMVO> serviceGMVOList = serviceGMManager.findAllClientGMWithInvoicesToClaimInPeriod(periodInitialDate, periodFinalDate);

        return MapperServiceGMVODTO.map(serviceGMVOList);
    }
}
