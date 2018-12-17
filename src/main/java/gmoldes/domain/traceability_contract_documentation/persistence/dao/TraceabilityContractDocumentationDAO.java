package gmoldes.domain.traceability_contract_documentation.persistence.dao;


import gmoldes.domain.traceability_contract_documentation.persistence.vo.TraceabilityContractDocumentationVO;
import gmoldes.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class TraceabilityContractDocumentationDAO {

    private SessionFactory sessionFactory;
    private Session session;

    public TraceabilityContractDocumentationDAO() {
    }

     public static class TraceabilityContractDocumentationDAOFactory {

        private static TraceabilityContractDocumentationDAO traceabilityContractDocumentationDAO;

        public static TraceabilityContractDocumentationDAO getInstance() {
            if(traceabilityContractDocumentationDAO == null) {
                traceabilityContractDocumentationDAO = new TraceabilityContractDocumentationDAO(HibernateUtil.retrieveGlobalSession());
            }
            return traceabilityContractDocumentationDAO;
        }
    }

    public TraceabilityContractDocumentationDAO(Session session) {
        this.session = session;
    }

    public Integer create(TraceabilityContractDocumentationVO traceabilityContractDocumentationVO) {

        try {
            session.beginTransaction();
            session.saveOrUpdate(traceabilityContractDocumentationVO);
            session.getTransaction().commit();
        }
        catch (Exception e){
            System.err.println("No se han podido guardar los datos de trazabilidad para el contrato: " + e.getMessage());
        }

        return traceabilityContractDocumentationVO.getId();
    }

    public List<TraceabilityContractDocumentationVO> findTraceabilityForAllContractWithPendingContractDocumentationToClient(){
        TypedQuery<TraceabilityContractDocumentationVO> query = session.createNamedQuery(TraceabilityContractDocumentationVO.FIND_ALL_CONTRACT_WITH_PENDING_CONTRACT_DOCUMENTATION_TO_CLIENT, TraceabilityContractDocumentationVO.class);

        return query.getResultList();
    }

    public List<TraceabilityContractDocumentationVO> findTraceabilityForAllContractWithPendingIDC(){
        TypedQuery<TraceabilityContractDocumentationVO> query = session.createNamedQuery(TraceabilityContractDocumentationVO.FIND_ALL_CONTRACT_WITH_PENDING_IDC, TraceabilityContractDocumentationVO.class);

        return query.getResultList();
    }

    public List<TraceabilityContractDocumentationVO> findTraceabilityForAllContractWithPendingContractEndNotice(){
        TypedQuery<TraceabilityContractDocumentationVO> query = session.createNamedQuery(TraceabilityContractDocumentationVO.FIND_ALL_CONTRACT_WITH_PENDING_CONTRACT_END_NOTICE, TraceabilityContractDocumentationVO.class);

        return query.getResultList();
    }

    public List<TraceabilityContractDocumentationVO> findTraceabilityForAllContractWithPendingLaborDocumentation(){
        TypedQuery<TraceabilityContractDocumentationVO> query = session.createNamedQuery(TraceabilityContractDocumentationVO.FIND_ALL_CONTRACT_WITH_PENDING_CONTRACT_DOCUMENTATION_TO_CLIENT, TraceabilityContractDocumentationVO.class);

        return query.getResultList();
    }
}
