package gmoldes.domain.servicegm;

import gmoldes.domain.servicegm.controllers.ServiceGMController;
import gmoldes.domain.servicegm.dto.ServiceGMDTO;

import java.time.LocalDate;
import java.util.List;

public class ServiceGMService {

    public ServiceGMService() {
    }

    public static List<ServiceGMDTO> findAllClientGMWithInvoicesToClaimInPeriod(LocalDate periodInitialDate, LocalDate periodFinalDate){

        ServiceGMController serviceGMController = new ServiceGMController();

        return serviceGMController.findAllClientGMWithInvoicesToClaimInPeriod(periodInitialDate, periodFinalDate);
    }
}
