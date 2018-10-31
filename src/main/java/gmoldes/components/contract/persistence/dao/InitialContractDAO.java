package gmoldes.components.contract.persistence.dao;


import gmoldes.components.contract.persistence.vo.InitialContractVO;
import gmoldes.components.contract_variation.persistence.vo.ContractVariationVO;
import gmoldes.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class InitialContractDAO {

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

        return initialContractVO.getId();
    }
}
