package gmoldes.domain.traceability_contract_documentation.persistence.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "traceability_contract_documentation")
@NamedQueries(value = {
        @NamedQuery(
                name = gmoldes.domain.traceability_contract_documentation.persistence.vo.TraceabilityContractDocumentationVO.FIND_ALL_RECORD_BY_CONTRACT_NUMBER,
                query = "select p from TraceabilityContractDocumentationVO p where contractNumber = :contractNumber"
        ),
        @NamedQuery(
                name = gmoldes.domain.traceability_contract_documentation.persistence.vo.TraceabilityContractDocumentationVO.FIND_ALL_CONTRACT_WITH_PENDING_IDC,
                query = "select p from TraceabilityContractDocumentationVO p where IDCReceptionDate is null order by contractNumber, variationType"
        ),
        @NamedQuery(
                name = gmoldes.domain.traceability_contract_documentation.persistence.vo.TraceabilityContractDocumentationVO.FIND_ALL_CONTRACT_WITH_PENDING_CONTRACT_END_NOTICE,
                query = "select p from TraceabilityContractDocumentationVO p where contractEndNoticeReceptionDate is null order by contractNumber, variationType"
        ),
        @NamedQuery(
                name = gmoldes.domain.traceability_contract_documentation.persistence.vo.TraceabilityContractDocumentationVO.FIND_ALL_CONTRACT_WITH_WORKING_DAY_SCHEDULE_WITH_END_DATE,
                query = "select p from TraceabilityContractDocumentationVO p where variationType = 230 and expectedEndDate >= now() order by contractNumber"
        )
        ,
        @NamedQuery(
                name = gmoldes.domain.traceability_contract_documentation.persistence.vo.TraceabilityContractDocumentationVO.FIND_ALL_CONTRACT_WITH_PENDING_CONTRACT_DOCUMENTATION_TO_CLIENT,
                query = "select p from TraceabilityContractDocumentationVO p where dateDeliveryContractDocumentationToClient is null order by contractNumber, variationType"
        )
})

public class TraceabilityContractDocumentationVO implements Serializable{

    public static final String FIND_ALL_RECORD_BY_CONTRACT_NUMBER = "TraceabilityContractDocumentationVO.FIND_ALL_RECORD_BY_CONTRACT_NUMBER";
    public static final String FIND_ALL_CONTRACT_WITH_PENDING_IDC = "TraceabilityContractDocumentationVO.FIND_ALL_CONTRACT_WITH_PENDING_IDC";
    public static final String FIND_ALL_CONTRACT_WITH_PENDING_CONTRACT_END_NOTICE = "TraceabilityContractDocumentationVO.FIND_ALL_CONTRACT_WITH_PENDING_CONTRACT_END_NOTICE";
    public static final String FIND_ALL_CONTRACT_WITH_PENDING_CONTRACT_DOCUMENTATION_TO_CLIENT = "TraceabilityContractDocumentationVO.FIND_ALL_CONTRACT_WITH_PENDING_CONTRACT_DOCUMENTATION_TO_CLIENT";
    public static final String FIND_ALL_CONTRACT_WITH_WORKING_DAY_SCHEDULE_WITH_END_DATE = "TraceabilityContractDocumentationVO.FIND_ALL_CONTRACT_WITH_WORKING_DAY_SCHEDULE_WITH_END_DATE";

    @Id
    @SequenceGenerator(name = "traceability_contract_documentation_id_seq", sequenceName = "traceability_contract_documentation_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "traceability_contract_documentation_id_seq")
    @Column(name = "id", updatable = false)
    private Integer id;
    private Integer contractNumber;
    private Integer variationType;
    private Date startDate;
    private Date expectedEndDate;
    private Date IDCReceptionDate;
    private Date dateDeliveryContractDocumentationToClient;
    private Date contractEndNoticeReceptionDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(Integer contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Integer getVariationType() {
        return variationType;
    }

    public void setVariationType(Integer variationType) {
        this.variationType = variationType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(Date expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public Date getIDCReceptionDate() {
        return IDCReceptionDate;
    }

    public void setIDCReceptionDate(Date IDCReceptionDate) {
        this.IDCReceptionDate = IDCReceptionDate;
    }

    public Date getDateDeliveryContractDocumentationToClient() {
        return dateDeliveryContractDocumentationToClient;
    }

    public void setDateDeliveryContractDocumentationToClient(Date dateDeliveryContractDocumentationToClient) {
        this.dateDeliveryContractDocumentationToClient = dateDeliveryContractDocumentationToClient;
    }

    public Date getContractEndNoticeReceptionDate() {
        return contractEndNoticeReceptionDate;
    }

    public void setContractEndNoticeReceptionDate(Date contractEndNoticeReceptionDate) {
        this.contractEndNoticeReceptionDate = contractEndNoticeReceptionDate;
    }
}
