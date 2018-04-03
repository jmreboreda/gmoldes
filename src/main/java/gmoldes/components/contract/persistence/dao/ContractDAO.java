package gmoldes.components.contract.persistence.dao;


import gmoldes.components.contract.persistence.vo.ContractVO;
import gmoldes.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class ContractDAO {

    private static final String ESTABLISH_CURRENT_CONTRACT =
            "update ContractVO as p set p.envigor = TRUE where p.envigor = FALSE and (p.f_desde <= date(now()) and (p.f_hasta > date(now()) or p.f_hasta is null))";
    private static final String ESTABLISH_NOT_CURRENT_CONTRACT =
            "update ContractVO as p set p.envigor = FALSE where p.envigor = TRUE and (p.f_desde > date(now()) or p.f_hasta < date(now()))";
    private static final String FIND_HIGHEST_CONTRACT_NUMBER = "select max(numcontrato)from ContractVO";

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
        catch (Exception e){
            System.err.println("No se ha podido guardar el contrato: " + e.getMessage());
        }

        return contractVO.getNumcontrato();
    }

    public int establishCurrentContract(){
        int result = 0;
        try {
            session.beginTransaction();
            Query query = session.createQuery(ESTABLISH_CURRENT_CONTRACT);
            result = query.executeUpdate();
            session.getTransaction().commit();
        }catch(Exception e){

        }

        return result;
    }

    public int establishNotCurrentContract(){
        int result = 0;
        try {
            session.beginTransaction();
            Query query = session.createQuery(ESTABLISH_NOT_CURRENT_CONTRACT);
            result = query.executeUpdate();
            session.getTransaction().commit();
        }catch(Exception e){

        }

        return result;
    }

    public List<ContractVO> findContractsExpiration(){
        TypedQuery<ContractVO> query = session.createNamedQuery(ContractVO.FIND_CONTRACTS_EXPIRATION, ContractVO.class);

        return query.getResultList();
    }

    public List<ContractVO> findPendingIDC(){
        TypedQuery<ContractVO> query = session.createNamedQuery(ContractVO.IDC_CONTROL, ContractVO.class);

        return query.getResultList();
    }

    public Integer findHighestContractNumber(){
        Query query = session.createQuery(FIND_HIGHEST_CONTRACT_NUMBER);

        return (Integer) query.getSingleResult();
    }

    public List<ContractVO> findAllClientWithActiveContractSorted(){
        TypedQuery<ContractVO> query = session.createNamedQuery(ContractVO.FIND_ALL_CLIENT_WITH_ACTIVE_CONTRACT_SORTED, ContractVO.class);

        return query.getResultList();
    }

    public List<ContractVO> findAllClientWithActiveContractWithTimeRecordSorted(){
        TypedQuery<ContractVO> query = session.createNamedQuery(ContractVO.FIND_ALL_CLIENT_WITH_ACTIVE_CONTRACT_WITH_TIMERECORD_SORTED, ContractVO.class);

        return query.getResultList();
    }

    public List<ContractVO> findAllClientWithContractWithTimeRecordInPeriodSorted(String yearMonth){
        TypedQuery<ContractVO> query = session.createNamedQuery(ContractVO.FIND_ALL_CLIENT_WITH_CONTRACT_WITH_TIMERECORD_IN_PERIOD_SORTED, ContractVO.class);
        query.setParameter("yearMonth", yearMonth);

        return query.getResultList();
    }

    public List<ContractVO> findAllContractsByClientId(Integer clientId){

        TypedQuery<ContractVO> query = session.createNamedQuery(ContractVO.FIND_ALL_CONTRACTS_BY_CLIENT_ID, ContractVO.class);
        query.setParameter("code", clientId);

        return query.getResultList();
    }

    public List<ContractVO> findAllContractsWithTimeRecordByClientIdInPeriod(Integer clientId, String yearMonth){
        TypedQuery<ContractVO> query = session.createNamedQuery(ContractVO.FIND_ALL_CONTRACTS_WITH_TIMERECORD_BY_CLIENT_ID_IN_PERIOD, ContractVO.class);
        query.setParameter("code", clientId);
        query.setParameter("initialperiod", yearMonth);

        return query.getResultList();
    }

    public List<ContractVO> findAllContractsByClientIdInPeriod(Integer clientId, Date referenceDate){
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
