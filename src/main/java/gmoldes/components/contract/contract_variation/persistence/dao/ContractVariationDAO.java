package gmoldes.components.contract.contract_variation.persistence.dao;


import gmoldes.components.contract.contract_variation.persistence.vo.ContractVariationVO;
import gmoldes.components.contract.initial_contract.persistence.vo.InitialContractVO;
import gmoldes.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.TypedQuery;
import java.util.List;

public class ContractVariationDAO {

    private SessionFactory sessionFactory;
    private Session session;

    public ContractVariationDAO() {
    }

     public static class ContractVariationDAOFactory {

        private static ContractVariationDAO contractVariationDAO;

        public static ContractVariationDAO getInstance() {
            if(contractVariationDAO == null) {
                contractVariationDAO = new ContractVariationDAO(HibernateUtil.retrieveGlobalSession());
            }
            return contractVariationDAO;
        }
    }

    public ContractVariationDAO(Session session) {
        this.session = session;
    }

    public Integer create(ContractVariationVO contractVariationVO) {

        try {
            session.beginTransaction();
            session.saveOrUpdate(contractVariationVO);
            session.getTransaction().commit();
        }
        catch (Exception e){
            System.err.println("No se ha podido guardar la variación del contrato: " + e.getMessage());
        }

        return contractVariationVO.getId();
    }

    public List<ContractVariationVO> findAllContractVariationByContractNumber(Integer contractNumber){
        TypedQuery<ContractVariationVO> query = session.createNamedQuery(ContractVariationVO.FIND_ALL_CONTRACT_VARIATION_BY_CONTRACT_NUMBER, ContractVariationVO.class);
        query.setParameter("code", contractNumber);

        return  query.getResultList();

    }
}
