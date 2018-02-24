package gmoldes.persistence.dao;


import gmoldes.persistence.vo.ContractTypeVO;
import gmoldes.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

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

    public List<ContractTypeVO> findAllContractTypes(){

        Query query = session.createNamedQuery(ContractTypeVO.FIND_ALL_CONTRACT_TYPES, ContractTypeVO.class);

        return (List<ContractTypeVO>) query.getResultList();
    }
}
