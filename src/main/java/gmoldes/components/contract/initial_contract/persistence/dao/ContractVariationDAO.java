package gmoldes.components.contract.initial_contract.persistence.dao;

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

        private static ContractVariationDAO initialContractDAO;

        public static ContractVariationDAO getInstance() {
            if(initialContractDAO == null) {
                initialContractDAO = new ContractVariationDAO(HibernateUtil.retrieveGlobalSession());
            }
            return initialContractDAO;
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
            System.err.println("CONTRACT_VARIATION_DA0 - No se ha podido guardar la variación del contrato: " + e.getMessage());
        }

        return contractVariationVO.getContractNumber();
    }

    public List<ContractVariationVO> findAllContractVariationsOrderedByContractNumberAndStartDate(){
        TypedQuery<ContractVariationVO> query = session.createNamedQuery(ContractVariationVO.FIND_ALL_CONTRACT_VARIATIONS_ORDERED_BY_CONTRACTNUMBER_AND_STARTDATE, ContractVariationVO.class);

        return query.getResultList();
    }

    public List<ContractVariationVO> findAllContractVariationsByContractNumber(Integer contractNumber){
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

    public List<ContractVariationVO> findAllContractVariationInForceInPeriod(LocalDate initialDate, LocalDate finalDate){
        TypedQuery<ContractVariationVO> query = session.createNamedQuery(ContractVariationVO.FIND_ALL_CONTRACT_VARIATION_IN_FORCE_IN_PERIOD, ContractVariationVO.class);

        java.util.Date initialUtilDate = Date.from(initialDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.util.Date finallUtilDate = Date.from(finalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        query.setParameter("codeInitialDate", initialUtilDate);
        query.setParameter("codeFinalDate", finallUtilDate);

        return  query.getResultList();
    }

    public List<ContractVariationVO> findAllDataForContractVariationsInForceAtDate(LocalDate date){
        TypedQuery<ContractVariationVO> query = session.createNamedQuery(ContractVariationVO.FIND_ALL_DATA_FOR_CONTRACT_VARIATIONS_IN_FORCE_AT_DATE, ContractVariationVO.class);

        java.util.Date atDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        query.setParameter("date", atDate);

        return  query.getResultList();

    }

    public List<ContractVariationVO> findAllContractVariationsInForceNowByClient(Integer clientId){
        TypedQuery<ContractVariationVO> query = session.createNamedQuery(ContractVariationVO.FIND_ALL_CONTRACT_VARIATIONS_IN_FORCE_NOW_BY_CLIENT_ID, ContractVariationVO.class);
        query.setParameter("clientId", clientId);

        return  query.getResultList();

    }
}
