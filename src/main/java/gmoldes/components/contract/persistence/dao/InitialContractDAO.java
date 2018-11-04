package gmoldes.components.contract.persistence.dao;


import gmoldes.components.contract.persistence.vo.ContractVO;
import gmoldes.components.contract.persistence.vo.InitialContractVO;
import gmoldes.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
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

    public Integer create(InitialContractVO initialContractVO) {

        try {
            session.beginTransaction();
            session.saveOrUpdate(initialContractVO);
            session.getTransaction().commit();
        }
        catch (Exception e){
            System.err.println("INITIAL_CONTRACT_DA0 - No se ha podido guardar el nuevo contrato inicial: " + e.getMessage());
        }

        return initialContractVO.getContractNumber();
    }

    public List<InitialContractVO> findAllInitialContractSorted(){
        TypedQuery<InitialContractVO> query = session.createNamedQuery(InitialContractVO.FIND_ALL_INITIAL_CONTRACT_SORTED, InitialContractVO.class);

        return query.getResultList();
    }

    public Integer findHighestContractNumber(){
        Query query = session.createQuery(FIND_HIGHEST_INITIAL_CONTRACT_NUMBER);

        return (Integer) query.getSingleResult();
    }
}
