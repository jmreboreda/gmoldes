package gmoldes.domain.contract.persistence.dao;


import gmoldes.domain.contract.persistence.vo.ContractVO;
import gmoldes.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class ContractDAO {

    private static final String FIND_HIGHEST_CONTRACT_NUMBER = "select max(gmContractNumber) from ContractVO";

    private SessionFactory sessionFactory;
    private Session session;

    public ContractDAO() {
    }

     public static class ContractDAOFactory {

        private static ContractDAO contractDAO;

        public static ContractDAO getInstance() {
            if(contractDAO == null) {
                contractDAO = new ContractDAO(HibernateUtil.retrieveGlobalSession());
            }
            return contractDAO;
        }
    }

    public ContractDAO(Session session) {
        this.session = session;
    }

    public Integer createContract(ContractVO contractVO) {

        try {
            session.beginTransaction();
            session.saveOrUpdate(contractVO);
            session.getTransaction().commit();
        }
        catch (Exception e){
            System.err.println("No se ha podido guardar el contrato: " + e.getMessage());
        }

        return contractVO.getGmContractNumber();
    }

    public Integer updateContract(ContractVO contractVO){
        ContractVO contractReadVO = null;
        try {
            session.beginTransaction();
            contractReadVO = session.get(ContractVO.class, contractVO.getId());
            contractReadVO.setId(contractVO.getId());
            contractReadVO.setEmployer(contractVO.getClientId());
            contractReadVO.setEmployee(contractVO.getWorkerId());
            contractReadVO.setContractType(contractVO.getContractType());
            contractReadVO.setGmContractNumber(contractVO.getGmContractNumber());
            contractReadVO.setVariationType(contractVO.getVariationType());
            contractReadVO.setStartDate(contractVO.getStartDate());
            contractReadVO.setExpectedEndDate(contractVO.getExpectedEndDate());
            contractReadVO.setModificationDate(contractVO.getModificationDate());
            contractReadVO.setEndingDate(contractVO.getEndingDate());
            contractReadVO.setContractScheduleJsonData(contractVO.getContractScheduleJsonData());
            contractReadVO.setLaborCategory(contractVO.getLaborCategory());
            contractReadVO.setQuoteAccountCode(contractVO.getQuoteAccountCode());
            contractReadVO.setIdentificationContractNumberINEM(contractVO.getIdentificationContractNumberINEM());
            contractReadVO.setPublicNotes(contractVO.getPublicNotes());
            contractReadVO.setPrivateNotes(contractVO.getPrivateNotes());
            session.update(contractReadVO);
            session.getTransaction().commit();
        }
        catch (Exception e){
            System.err.println("No se ha podido actualizar el contrato inicial en \"Contract\": " + e.getMessage());
        }

        return contractReadVO.getId();
    }

    public Integer findHighestContractNumber(){
        Query query = session.createQuery(FIND_HIGHEST_CONTRACT_NUMBER);

        return (Integer) query.getSingleResult();
    }

    public List<ContractVO> findAllContractsOrderedByContractNumber(){
        TypedQuery<ContractVO> query = session.createNamedQuery(ContractVO.FIND_ALL_CONTRACTS_ORDERED_BY_CONTRACT_NUMBER, ContractVO.class);

        return query.getResultList();
    }
    
    public List<ContractVO> findAllContractsByClientId(Integer clientId){

        TypedQuery<ContractVO> query = session.createNamedQuery(ContractVO.FIND_ALL_CONTRACTS_BY_CLIENT_ID, ContractVO.class);
        query.setParameter("code", clientId);

        return query.getResultList();
    }

    public ContractVO findLastTuplaOfContractByContractNumber(Integer contractNumber){
        TypedQuery<ContractVO> query = session.createNamedQuery(ContractVO.FIND_LAST_TUPLA_CONTRACT_BY_CONTRACT_NUMBER, ContractVO.class);
        query.setParameter("contractNumber", contractNumber);
        query.setMaxResults(1);

        return  query.getSingleResult();
    }
}
