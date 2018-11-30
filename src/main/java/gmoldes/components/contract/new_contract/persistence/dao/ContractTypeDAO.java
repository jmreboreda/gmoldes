package gmoldes.components.contract.new_contract.persistence.dao;


import gmoldes.components.contract.new_contract.persistence.vo.ContractTypeVO;
import gmoldes.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
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

        Query query = session.createNamedQuery(ContractTypeVO.FIND_ALL_CONTRACT_TYPES_SELECTABLES, ContractTypeVO.class);

        return query.getResultList();
    }

    public ContractTypeVO findContractTypeById(Integer contractTypeId){
        TypedQuery<ContractTypeVO> query = session.createNamedQuery(ContractTypeVO.FIND_CONTRACT_TYPE_BY_ID, ContractTypeVO.class);
        query.setParameter("contractTypeId", contractTypeId);

        return query.getSingleResult();
    }
}
