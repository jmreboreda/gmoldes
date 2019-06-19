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

    public Integer updateTraceabilityRecord(TraceabilityContractDocumentationVO traceabilityContractDocumentationVO){
        TraceabilityContractDocumentationVO traceabilityContractDocumentationReadVO = null;
        try {
            session.beginTransaction();
            traceabilityContractDocumentationReadVO = session.get(TraceabilityContractDocumentationVO.class, traceabilityContractDocumentationVO.getId());
            traceabilityContractDocumentationReadVO.setId(traceabilityContractDocumentationVO.getId());
            traceabilityContractDocumentationReadVO.setContractNumber(traceabilityContractDocumentationVO.getContractNumber());
            traceabilityContractDocumentationReadVO.setVariationType(traceabilityContractDocumentationVO.getVariationType());
            traceabilityContractDocumentationReadVO.setStartDate(traceabilityContractDocumentationVO.getStartDate());
            traceabilityContractDocumentationReadVO.setExpectedEndDate(traceabilityContractDocumentationVO.getExpectedEndDate());
            traceabilityContractDocumentationReadVO.setIDCReceptionDate(traceabilityContractDocumentationVO.getIDCReceptionDate());
            traceabilityContractDocumentationReadVO.setDateDeliveryContractDocumentationToClient(traceabilityContractDocumentationVO.getDateDeliveryContractDocumentationToClient());
            traceabilityContractDocumentationReadVO.setContractEndNoticeReceptionDate(traceabilityContractDocumentationVO.getContractEndNoticeReceptionDate());
            session.update(traceabilityContractDocumentationReadVO);
            session.getTransaction().commit();
        }
        catch (Exception e){
            System.err.println("No se ha podido actualizar \"contractEndNoticeReceptionDate\" en la trazabilidad del contrato en \"traceability_contract_documentation\": " + e.getMessage());
        }

        return traceabilityContractDocumentationVO.getId();
    }

    public List<TraceabilityContractDocumentationVO> findAllTraceabilityContractData(){
        TypedQuery<TraceabilityContractDocumentationVO> query = session.createNamedQuery(TraceabilityContractDocumentationVO.FIND_ALL_RECORD, TraceabilityContractDocumentationVO.class);

        return query.getResultList();
    }

    public List<TraceabilityContractDocumentationVO> findAllTraceabilityRecordByContractNumber(Integer contractNumber){
        TypedQuery<TraceabilityContractDocumentationVO> query = session.createNamedQuery(TraceabilityContractDocumentationVO.FIND_ALL_RECORD_BY_CONTRACT_NUMBER, TraceabilityContractDocumentationVO.class);
        query.setParameter("contractNumber", contractNumber);

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

    public List<TraceabilityContractDocumentationVO> findTraceabilityForAllContractWithWorkingDayScheduleWithEndDate(){
        TypedQuery<TraceabilityContractDocumentationVO> query = session.createNamedQuery(TraceabilityContractDocumentationVO.FIND_ALL_CONTRACT_WITH_WORKING_DAY_SCHEDULE_WITH_END_DATE, TraceabilityContractDocumentationVO.class);

        return query.getResultList();
    }

    public List<TraceabilityContractDocumentationVO> findTraceabilityForAllContractWithPendingLaborDocumentation(){
        TypedQuery<TraceabilityContractDocumentationVO> query = session.createNamedQuery(TraceabilityContractDocumentationVO.FIND_ALL_CONTRACT_WITH_PENDING_CONTRACT_DOCUMENTATION_TO_CLIENT, TraceabilityContractDocumentationVO.class);

        return query.getResultList();
    }
}
