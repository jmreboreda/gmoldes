package gmoldes.persistence.dao;


import gmoldes.persistence.vo.ContractVO;
import gmoldes.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class ContractDAO {

    private SessionFactory sessionFactory;
    private Session session;

    public ContractDAO() {
    }

     public static class ContractDAOFactory {

        private static ContractDAO contractDAO;

        public static ContractDAO getInstance() {
            if(contractDAO == null) {
                contractDAO = new ContractDAO(HibernateUtil.retrieveGlobalSession());
            }
            return contractDAO;
        }

    }

    public ContractDAO(Session session) {
        this.session = session;
    }

    public Integer create(ContractVO contractVO) {

        try {
            session.beginTransaction();
            session.saveOrUpdate(contractVO);
            session.getTransaction().commit();
        }
        catch (org.hibernate.exception.ConstraintViolationException cve){

        }
            return contractVO.getId();
    }

    public List<ContractVO> findAllClientWithActiveContractSorted(){
        TypedQuery<ContractVO> query = session.createNamedQuery(ContractVO.FIND_ALL_CLIENT_WITH_ACTIVE_CONTRACT_SORTED, ContractVO.class);

        return query.getResultList();
    }

    public List<ContractVO> findAllContractsByClientId(Integer clientId){

        TypedQuery<ContractVO> query = session.createNamedQuery(ContractVO.FIND_ALL_CONTRACTS_BY_CLIENT_ID, ContractVO.class);
        query.setParameter("code", clientId);

        return query.getResultList();
    }

    public List<ContractVO> findAllContractsByClientIdInPeriod(Integer clientId, Date referenceDate){
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy");
//        Date initialPeriod = null;
//        try {
//            initialPeriod = dateFormat.parse("01-".concat(period));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        TypedQuery<ContractVO> query = session.createNamedQuery(ContractVO.FIND_ALL_CONTRACTS_BY_CLIENT_ID_IN_PERIOD, ContractVO.class);
        query.setParameter("code", clientId);
        query.setParameter("initialperiod", referenceDate);

        return query.getResultList();
    }

    public List<ContractVO> findAllActiveContractsByClientId(Integer clientId){

        TypedQuery<ContractVO> query = session.createNamedQuery(ContractVO.FIND_ALL_ACTIVE_CONTRACTS_BY_CLIENT_ID, ContractVO.class);
        query.setParameter("code", clientId);

        return query.getResultList();
    }
}
