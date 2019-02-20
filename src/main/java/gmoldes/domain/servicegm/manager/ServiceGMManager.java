package gmoldes.domain.servicegm.manager;

import gmoldes.domain.servicegm.persistence.dao.ServiceGMDAO;
import gmoldes.domain.servicegm.persistence.vo.ServiceGMVO;

import java.time.LocalDate;
import java.util.List;

public class ServiceGMManager {

    public ServiceGMManager() {
    }

    public List<ServiceGMVO> findAllClientGMWithInvoicesToClaimInPeriod(LocalDate periodInitialDate, LocalDate periodFinalDate){

        ServiceGMDAO serviceGMDAO = ServiceGMDAO.ServiceGMDAOFactory.getInstance();

        return serviceGMDAO.findAllClientGMWithInvoicesToClaimInPeriod(periodInitialDate, periodFinalDate);
    }
}
