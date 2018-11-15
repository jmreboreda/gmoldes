package gmoldes.components.contract.new_contract.persistence.dao;


import gmoldes.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ContractTypeDAO {

    private SessionFactory sessionFactory;
    private Session session;

    public ContractTypeDAO() {
    }

     public static class ContractTypeDAOFactory {

        private static ContractTypeDAO contractTypeDAO;

        public static ContractTypeDAO getInstance() {
            if(contractTypeDAO == null) {
                contractTypeDAO = new ContractTypeDAO(HibernateUtil.retrieveGlobalSession());
            }
            return contractTypeDAO;
        }
    }

    public ContractTypeDAO(Session session) {
        this.session = session;
    }

//    public List<ContractTypeVO> findAllContractTypes(){
//
//        Query query = session.createNamedQuery(ContractTypeVO.FIND_ALL_CONTRACT_TYPES, ContractTypeVO.class);
//
//        return (List<ContractTypeVO>) query.getResultList();
//    }
}
