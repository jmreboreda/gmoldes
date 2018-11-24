package gmoldes.components.contract.new_contract.persistence.dao;


import gmoldes.components.contract.new_contract.persistence.vo.ContractTypeVO;
import gmoldes.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class ContractTypeNewDAO {

    private SessionFactory sessionFactory;
    private Session session;

    public ContractTypeNewDAO() {
    }

     public static class ContractTypeNewDAOFactory {

        private static ContractTypeNewDAO contractTypeNewDAO;

        public static ContractTypeNewDAO getInstance() {
            if(contractTypeNewDAO == null) {
                contractTypeNewDAO = new ContractTypeNewDAO(HibernateUtil.retrieveGlobalSession());
            }
            return contractTypeNewDAO;
        }
    }

    public ContractTypeNewDAO(Session session) {
        this.session = session;
    }

    public List<ContractTypeVO> findAllContractTypes(){

        Query query = session.createNamedQuery(ContractTypeVO.FIND_ALL_CONTRACT_TYPES_SELECTABLES, ContractTypeVO.class);

        return (List<ContractTypeVO>) query.getResultList();
    }

    public ContractTypeVO findContractTypeById(Integer contractTypeId){

        Query query = session.createNamedQuery(ContractTypeVO.FIND_CONTRACT_TYPE_BY_ID, ContractTypeVO.class);
        query.setParameter("contractTypeId", contractTypeId);

        return (ContractTypeVO) query.getSingleResult();
    }


}
