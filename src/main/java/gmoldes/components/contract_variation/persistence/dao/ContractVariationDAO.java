package gmoldes.components.contract_variation.persistence.dao;


import gmoldes.components.contract_variation.persistence.vo.ContractVariationVO;
import gmoldes.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.TypedQuery;
import java.sql.Date;
import java.time.LocalDate;
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

    public Integer update(ContractVariationVO contractVariationVO){

        ContractVariationVO contractVariationReadVO;

        try {
            session.beginTransaction();
            contractVariationReadVO = session.get(ContractVariationVO.class, contractVariationVO.getId());
                contractVariationReadVO.setId(contractVariationVO.getId());
                contractVariationReadVO.setContractNumber(contractVariationVO.getContractNumber());
                contractVariationReadVO.setVariationType(contractVariationVO.getVariationType());
                contractVariationReadVO.setStartDate(contractVariationVO.getStartDate());
                contractVariationReadVO.setExpectedEndDate(contractVariationVO.getExpectedEndDate());
                contractVariationReadVO.setModificationDate(contractVariationVO.getModificationDate());
                contractVariationReadVO.setEndingDate(contractVariationVO.getEndingDate());
                contractVariationReadVO.setContractJsonData(contractVariationVO.getContractJsonData());
            session.update(contractVariationReadVO);
            session.getTransaction().commit();
        }
        catch (Exception e){
            System.err.println("No se ha podido actualizar la variación del contrato: " + e.getMessage());
        }

        return contractVariationVO.getId();
    }

    public List<ContractVariationVO> findAllContractVariationByContractNumber(Integer contractNumber){
        TypedQuery<ContractVariationVO> query = session.createNamedQuery(ContractVariationVO.FIND_ALL_CONTRACT_VARIATION_BY_CONTRACT_NUMBER, ContractVariationVO.class);
        query.setParameter("code", contractNumber);

        return  query.getResultList();
    }

    public List<ContractVariationVO> findAllContractVariationById(Integer contractVariationId){
        TypedQuery<ContractVariationVO> query = session.createNamedQuery(ContractVariationVO.FIND_ALL_CONTRACT_VARIATION_BY_ID, ContractVariationVO.class);
        query.setParameter("code", contractVariationId);

        return  query.getResultList();
    }

    public List<ContractVariationVO> findAllContractVariationInPeriod(LocalDate initialDate, LocalDate finalDate){
        TypedQuery<ContractVariationVO> query = session.createNamedQuery(ContractVariationVO.FIND_ALL_ACTIVE_CONTRACT_VARIATION_IN_PERIOD, ContractVariationVO.class);

        Date initialSqlDate = Date.valueOf(initialDate);
        Date finalSqlDate = Date.valueOf(finalDate);

        query.setParameter("codeInitialDate", initialSqlDate);
        query.setParameter("codeFinalDate", finalSqlDate);

        return  query.getResultList();
    }

    public List<ContractVariationVO> findAllContractVariationInForceInPeriod(LocalDate initialDate, LocalDate finalDate){
        TypedQuery<ContractVariationVO> query = session.createNamedQuery(ContractVariationVO.FIND_ALL_CONTRACT_VARIATION_IN_FORCE_IN_PERIOD, ContractVariationVO.class);

        Date initialSqlDate = Date.valueOf(initialDate);
        Date finalSqlDate = Date.valueOf(finalDate);

        query.setParameter("codeInitialDate", initialSqlDate);
        query.setParameter("codeFinalDate", finalSqlDate);

        return  query.getResultList();
    }

    public List<ContractVariationVO> findAllContractVariationTemporalInForceNow(){
        TypedQuery<ContractVariationVO> query = session.createNamedQuery(ContractVariationVO.FIND_ALL_CONTRACT_VARIATION_TEMPORAL_IN_FORCE_NOW, ContractVariationVO.class);

        return  query.getResultList();
    }

    public List<ContractVariationVO> findAllContractVariationsInForceAtDate(LocalDate date){
        TypedQuery<ContractVariationVO> query = session.createNamedQuery(ContractVariationVO.FIND_ALL_CONTRACT_VARIATIONS_IN_FORCE_AT_DATE, ContractVariationVO.class);

        Date atDate = Date.valueOf(date);
        query.setParameter("date", atDate);

        return  query.getResultList();
    }

    public List<ContractVariationVO> findAllContractVariationsAfterDateByContractNumber(Integer contractNumber, LocalDate dateFromSearch){
        TypedQuery<ContractVariationVO> query = session.createNamedQuery(ContractVariationVO.FIND_ALL_CONTRACT_VARIATIONS_AFTER_DATE_BY_CONTRACT_NUMBER, ContractVariationVO.class);

        Date afterDate = Date.valueOf(dateFromSearch);
        query.setParameter("dateFromSearch", afterDate);
        query.setParameter("contractNumber", contractNumber);

        return  query.getResultList();
    }
}
