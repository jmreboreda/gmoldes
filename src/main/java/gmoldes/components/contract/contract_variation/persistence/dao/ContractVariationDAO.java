package gmoldes.components.contract.contract_variation.persistence.dao;


import gmoldes.components.contract.contract_variation.persistence.vo.ContractVariationVO;
import gmoldes.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.TypedQuery;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
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

    public List<ContractVariationVO> findAllContractVariationInPeriod(LocalDate initialDate, LocalDate finalDate){
        TypedQuery<ContractVariationVO> query = session.createNamedQuery(ContractVariationVO.FIND_ALL_CONTRACT_VARIATION_IN_PERIOD, ContractVariationVO.class);

        java.util.Date initialUtilDate = Date.from(initialDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.util.Date finallUtilDate = Date.from(finalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        query.setParameter("codeInitialDate", initialUtilDate);
        query.setParameter("codeFinalDate", finallUtilDate);

        return  query.getResultList();

    }

    public List<ContractVariationVO> findAllContractVariationsInForceAtDate(LocalDate date){
        TypedQuery<ContractVariationVO> query = session.createNamedQuery(ContractVariationVO.FIND_ALL_DATA_FOR_CONTRACT_VARIATIONS_IN_FORCE_AT_DATE, ContractVariationVO.class);

        java.util.Date atDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        query.setParameter("date", atDate);

        return  query.getResultList();

    }

}
