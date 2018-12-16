package gmoldes.domain.servicegm.persistence.dao;


import gmoldes.domain.client.persistence.vo.ClientVO;
import gmoldes.domain.servicegm.persistence.vo.ServiceGMVO;
import gmoldes.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.TypedQuery;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class ServiceGMDAO {

    private SessionFactory sessionFactory;
    private Session session;

    public ServiceGMDAO() {
    }

     public static class ServiceGMDAOFactory {

        private static ServiceGMDAO clientDAO;

        public static ServiceGMDAO getInstance() {
            if(clientDAO == null) {
                clientDAO = new ServiceGMDAO(HibernateUtil.retrieveGlobalSession());
            }
            return clientDAO;
        }

    }

    public ServiceGMDAO(Session session) {
        this.session = session;
    }

    public Integer createServiceGM(ServiceGMVO serviceGMVO) {

        try {
            session.beginTransaction();
            session.saveOrUpdate(serviceGMVO);
            session.getTransaction().commit();
        }
        catch (org.hibernate.exception.ConstraintViolationException cve){

        }
            return serviceGMVO.getId();
    }

    public List<ServiceGMVO> findAllClientGMWithInvoicesToClaimInPeriod(LocalDate periodInitialDate,LocalDate periodFinalDate){

        TypedQuery<ServiceGMVO> query = session.createNamedQuery(ServiceGMVO.FIND_ALL_CLIENT_WITH_INVOICES_TO_CLAIM_IN_PERIOD, ServiceGMVO.class);
        query.setParameter("periodFinalDate", Date.valueOf(periodFinalDate));

        return query.getResultList();

    }

    public ServiceGMVO findServiceGMByClientId(Integer clientId){

        TypedQuery<ServiceGMVO> query = session.createNamedQuery(ServiceGMVO.FIND_SERVICE_GM_BY_CLIENT_ID, ServiceGMVO.class);
        query.setParameter("clientId", clientId);

        return query.getSingleResult();
    }
}
