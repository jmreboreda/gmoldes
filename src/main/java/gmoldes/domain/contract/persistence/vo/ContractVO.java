package gmoldes.domain.contract.persistence.vo;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeStringType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import gmoldes.domain.client.persistence.vo.ClientVO;
import gmoldes.domain.contractjsondata.ContractScheduleJsonData;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@NamedQueries(value = {
        @NamedQuery(
                name = ContractVO.FIND_ALL_CONTRACTS_ORDERED_BY_CONTRACT_NUMBER,
                query = "select p from ContractVO p order by p.gmContractNumber"
        ),
        @NamedQuery(
                name = ContractVO.FIND_ALL_CONTRACTS_BY_CLIENT_ID,
                query = "select p from ContractVO p where p.clientId = :code"
        ),
        @NamedQuery(
                name = ContractVO.FIND_LAST_TUPLA_CONTRACT_BY_CONTRACT_NUMBER,
                query = "select p from ContractVO p where gmContractNumber = :contractNumber order by startDate desc"
        )
})
@Entity
@Table(name = "contract")
@TypeDefs({
        @TypeDef(name = "string-array", typeClass = StringArrayType.class),
        @TypeDef(name = "int-array", typeClass = IntArrayType.class),
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
        @TypeDef(name = "jsonb-node", typeClass = JsonNodeBinaryType.class),
        @TypeDef(name = "json-node", typeClass = JsonNodeStringType.class),
})

public class ContractVO implements Serializable {
    public static final String FIND_ALL_CONTRACTS_ORDERED_BY_CONTRACT_NUMBER = "ContractVO.FIND_ALL_CONTRACTS_ORDERED_BY_CONTRACT_NUMBER";
    public static final String FIND_ALL_CONTRACTS_BY_CLIENT_ID = "ContractVO.FIND_ALL_CONTRACTS_BY_CLIENT_ID";
    public static final String FIND_LAST_TUPLA_CONTRACT_BY_CONTRACT_NUMBER = "ContractVO.FIND_LAST_TUPLA_INITIAL_CONTRACT_BY_CONTRACT_NUMBER";

    @Id
    @SequenceGenerator(name = "contract_id_seq", sequenceName = "contract_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_id_seq")
    @Column(name = "id", updatable = false)
    private Integer id;
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name="clientid")
//    private ClientVO clientVO;
    private Integer clientId;
    private Integer workerId;
    private String contractType;
    private Integer gmContractNumber;
    private Integer variationType;
    private Date startDate;
    private Date expectedEndDate;
    private Date modificationDate;
    private Date endingDate;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private ContractScheduleJsonData contractScheduleJsonData;
    private String laborCategory;
    private String quoteAccountCode;
    private String identificationContractNumberINEM;
    private String publicNotes;
    private String privateNotes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public ClientVO getClientVO() {
//        return clientVO;
//    }
//
//    public void setClientVO(ClientVO clientVO) {
//        this.clientVO = clientVO;
//    }

    public Integer getClientId() {
        return clientId;
    }

    public void setEmployer(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setEmployee(Integer workerId) {
        this.workerId = workerId;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public Integer getGmContractNumber() {
        return gmContractNumber;
    }

    public void setGmContractNumber(Integer gmContractNumber) {
        this.gmContractNumber = gmContractNumber;
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

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public ContractScheduleJsonData getContractScheduleJsonData() {
        return contractScheduleJsonData;
    }

    public void setContractScheduleJsonData(ContractScheduleJsonData contractScheduleJsonData) {
        this.contractScheduleJsonData = contractScheduleJsonData;
    }

    public String getLaborCategory() {
        return laborCategory;
    }

    public void setLaborCategory(String laborCategory) {
        this.laborCategory = laborCategory;
    }

    public String getQuoteAccountCode() {
        return quoteAccountCode;
    }

    public void setQuoteAccountCode(String quoteAccountCode) {
        this.quoteAccountCode = quoteAccountCode;
    }

    public String getIdentificationContractNumberINEM() {
        return identificationContractNumberINEM;
    }

    public void setIdentificationContractNumberINEM(String identificationContractNumberINEM) {
        this.identificationContractNumberINEM = identificationContractNumberINEM;
    }

    public String getPublicNotes() {
        return publicNotes;
    }

    public void setPublicNotes(String publicNotes) {
        this.publicNotes = publicNotes;
    }

    public String getPrivateNotes() {
        return privateNotes;
    }

    public void setPrivateNotes(String privateNotes) {
        this.privateNotes = privateNotes;
    }
}
