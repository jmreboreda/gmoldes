package gmoldes.components.contract.initial_contract.persistence.dao;

import gmoldes.components.contract.initial_contract.persistence.vo.InitialContractVO;
import gmoldes.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class InitialContractDAO {

    private static final String FIND_HIGHEST_INITIAL_CONTRACT_NUMBER = "select max(contractNumber) from InitialContractVO";

    private SessionFactory sessionFactory;
    private Session session;

    public InitialContractDAO() {
    }

     public static class InitialContractDAOFactory {

        private static InitialContractDAO initialContractDAO;

        public static InitialContractDAO getInstance() {
            if(initialContractDAO == null) {
                initialContractDAO = new InitialContractDAO(HibernateUtil.retrieveGlobalSession());
            }
            return initialContractDAO;
        }
    }

    public InitialContractDAO(Session session) {
        this.session = session;
    }

    public Integer createInitialContract(InitialContractVO initialContractVO) {

        try {
            session.beginTransaction();
            session.saveOrUpdate(initialContractVO);
            session.getTransaction().commit();
        }
        catch (Exception e){
            System.err.println("INITIAL_CONTRACT_DA0 - No se ha podido guardar el nuevo contrato inicial en \"initial_contract\": " + e.getMessage());
        }

        return initialContractVO.getContractNumber();
    }

    public Integer updateInitialContract(InitialContractVO initialContractVO){
        InitialContractVO initialContractReadVO = null;
        try {
            session.beginTransaction();
            initialContractReadVO = session.get(InitialContractVO.class, initialContractVO.getId());
            initialContractReadVO.setId(initialContractVO.getId());
            initialContractReadVO.setContractNumber(initialContractVO.getContractNumber());
            initialContractReadVO.setVariationType(initialContractVO.getVariationType());
            initialContractReadVO.setStartDate(initialContractVO.getStartDate());
            initialContractReadVO.setExpectedEndDate(initialContractVO.getExpectedEndDate());
            initialContractReadVO.setModificationDate(initialContractVO.getModificationDate());
            initialContractReadVO.setEndingDate(initialContractVO.getEndingDate());
            initialContractReadVO.setContractJsonData(initialContractVO.getContractJsonData());
            session.update(initialContractReadVO);
            session.getTransaction().commit();
        }
        catch (Exception e){
            System.err.println("No se ha podido actualizar el contrato inicial en \"initial_contract\": " + e.getMessage());
        }

        return initialContractVO.getId();
    }

    public InitialContractVO findLastTuplaOfInitialContractByContractNumber(Integer contractNumber){
        TypedQuery<InitialContractVO> query = session.createNamedQuery(InitialContractVO.FIND_LAST_TUPLA_INITIAL_CONTRACT_BY_CONTRACT_NUMBER, InitialContractVO.class);
        query.setParameter("contractNumber", contractNumber);
        query.setMaxResults(1);

        return  query.getSingleResult();


    }

    public List<InitialContractVO> findAllContractInForceAtDate(LocalDate date){
        TypedQuery<InitialContractVO> query = session.createNamedQuery(InitialContractVO.FIND_ALL_CONTRACT_IN_FORCE_AT_DATE, InitialContractVO.class);

        java.util.Date utilDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        query.setParameter("date", utilDate);

        return  query.getResultList();
    }

    public List<InitialContractVO> findAllInitialContractSorted(){
        TypedQuery<InitialContractVO> query = session.createNamedQuery(InitialContractVO.FIND_ALL_CONTRACTS_ORDERED_BY_CONTRACTNUMBER_AND_STARTDATE, InitialContractVO.class);

        return query.getResultList();
    }

    public Integer findHighestContractNumber(){
        Query query = session.createQuery(FIND_HIGHEST_INITIAL_CONTRACT_NUMBER);

        return (Integer) query.getSingleResult();
    }

    public InitialContractVO findInitialContractByContractNumber(Integer contractNumber){
        TypedQuery<InitialContractVO> query = session.createNamedQuery(InitialContractVO.FIND_INITIAL_CONTRACT_BY_CONTRACT_NUMBER, InitialContractVO.class);
        query.setParameter("code", contractNumber);

        return  query.getSingleResult();
    }

    public List<InitialContractVO> findAllInitialContractForTimeRecordInPeriod(LocalDate initialDate, LocalDate finalDate){
        TypedQuery<InitialContractVO> query = session.createNamedQuery(InitialContractVO.FIND_ALL_ACTIVE_INITIAL_CONTRACT_IN_PERIOD, InitialContractVO.class);

        java.util.Date initialUtilDate = Date.from(initialDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.util.Date finallUtilDate = Date.from(finalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        query.setParameter("codeInitialDate", initialUtilDate);
        query.setParameter("codeFinalDate", finallUtilDate);

        return  query.getResultList();
    }

    public List<InitialContractVO> findAllActiveInitialContractInForceInPeriod(LocalDate initialDate, LocalDate finalDate){
        TypedQuery<InitialContractVO> query = session.createNamedQuery(InitialContractVO.FIND_ALL_ACTIVE_INITIAL_CONTRACT_IN_PERIOD, InitialContractVO.class);

        java.util.Date initialUtilDate = Date.from(initialDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.util.Date finalUtilDate = Date.from(finalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        query.setParameter("codeInitialDate", initialUtilDate);
        query.setParameter("codeFinalDate", finalUtilDate);

        return  query.getResultList();
    }

    public List<InitialContractVO> findAllInitialContractInForceInPeriod(LocalDate initialDate, LocalDate finalDate){
        TypedQuery<InitialContractVO> query = session.createNamedQuery(InitialContractVO.FIND_ALL_INITIAL_CONTRACT_IN_FORCE_IN_PERIOD, InitialContractVO.class);

        java.util.Date initialUtilDate = Date.from(initialDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.util.Date finalUtilDate = Date.from(finalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        query.setParameter("codeInitialDate", initialUtilDate);
        query.setParameter("codeFinalDate", finalUtilDate);

        return  query.getResultList();
    }

    public List<InitialContractVO> findAllInitialContractsInForceAtDate(LocalDate date){
        TypedQuery<InitialContractVO> query = session.createNamedQuery(InitialContractVO.FIND_ALL_INITIAL_CONTRACTS_IN_FORCE_AT_DATE, InitialContractVO.class);

//        java.util.Date atDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date atDate = Date.valueOf(date);
        query.setParameter("date", atDate);

        return  query.getResultList();

    }

    public List<InitialContractVO> findAllInitialContractTemporalInForceNow(){
        TypedQuery<InitialContractVO> query = session.createNamedQuery(InitialContractVO.FIND_ALL_INITIAL_CONTRACT_TEMPORAL_IN_FORCE_NOW, InitialContractVO.class);

        return  query.getResultList();

    }

    public List<InitialContractVO> findAllInitialContractsInForceNowByClient(Integer clientId){
        TypedQuery<InitialContractVO> query = session.createNamedQuery(InitialContractVO.FIND_ALL_INITIAL_CONTRACT_IN_FORCE_NOW_BY_CLIENT_ID, InitialContractVO.class);
        query.setParameter("clientId", clientId);

        return  query.getResultList();
    }
}
