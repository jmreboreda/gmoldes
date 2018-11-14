package gmoldes.components.contract.new_contract.persistence.dao;


import gmoldes.components.contract.new_contract.persistence.vo.ContractTypeNewVO;
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

     public static class ContractTypeDAOFactory {

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

    public List<ContractTypeNewVO> findAllContractTypes(){

        Query query = session.createNamedQuery(ContractTypeNewVO.FIND_ALL_CONTRACT_TYPES, ContractTypeNewVO.class);

        return (List<ContractTypeNewVO>) query.getResultList();
    }

    public ContractTypeNewVO findContractTypeById(Integer contractTypeId){

        Query query = session.createNamedQuery(ContractTypeNewVO.FIND_CONTRACT_TYPE_BY_ID, ContractTypeNewVO.class);
        query.setParameter("contractTypeId", contractTypeId);

        return (ContractTypeNewVO) query.getSingleResult();
    }


}
