package gmoldes.components.contract.initial_contract.persistence.vo;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeStringType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import gmoldes.domain.contractjsondata.ContractJsonData;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@NamedQueries(value = {
        @NamedQuery(
                name = InitialContractVO.FIND_ALL_INITIAL_CONTRACT_IN_PERIOD,
                query = "select p from InitialContractVO  p where startDate <= :codeFinalDate and (endingDate is null or endingDate >= :codeInitialDate) " +
                        "and (expectedEndDate is null or expectedEndDate >= :codeInitialDate) " +
                        "and (modificationDate is null or modificationDate >= :codeInitialDate) " +
                        "and variationType < 800 order by contractNumber, startDate"
        ),
        @NamedQuery(
                name = InitialContractVO.FIND_ALL_CONTRACTS_ORDERED_BY_CONTRACTNUMBER_AND_STARTDATE,
                query = "select p from InitialContractVO p order by p.contractNumber, p.startDate"
        ),
        @NamedQuery(
                name = InitialContractVO.FIND_INITIAL_CONTRACT_BY_CONTRACT_NUMBER,
                query = "select p from InitialContractVO p where contractNumber = :code"
        ),
        @NamedQuery(
                name = InitialContractVO.FIND_ALL_DATA_FOR_INITIAL_CONTRACT_IN_FORCE_AT_DATE,
                query = "select p from InitialContractVO p where p.startDate <= :date and p.endingDate is null and p.modificationDate is null"
        ),
        @NamedQuery(
                name = InitialContractVO.FIND_ALL_INITIAL_CONTRACT_IN_FORCE_NOW_BY_CLIENT_ID,
                query = "select p from InitialContractVO p where p.startDate <= :date and p.endingDate is null and p.modificationDate is null"
        )
})

@Entity
@Table(name = "initialcontract")
@TypeDefs({
        @TypeDef(name = "string-array", typeClass = StringArrayType.class),
        @TypeDef(name = "int-array", typeClass = IntArrayType.class),
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
        @TypeDef(name = "jsonb-node", typeClass = JsonNodeBinaryType.class),
        @TypeDef(name = "json-node", typeClass = JsonNodeStringType.class),
})

public class InitialContractVO implements Serializable {

    public static final String FIND_ALL_INITIAL_CONTRACT_IN_PERIOD = "InitialContractVO.FIND_ALL_INITIAL_CONTRACT_IN_PERIOD";
    public static final String FIND_ALL_CONTRACTS_ORDERED_BY_CONTRACTNUMBER_AND_STARTDATE = "InitialContractVO.FIND_ALL_CONTRACTS_ORDERED_BY_CONTRACTNUMBER_AND_STARTDATE";
    public static final String FIND_INITIAL_CONTRACT_BY_CONTRACT_NUMBER = "InitialContractVO.FIND_INITIAL_CONTRACT_BY_CONTRACT_NUMBER";
    public static final String FIND_ALL_DATA_FOR_INITIAL_CONTRACT_IN_FORCE_AT_DATE = "InitialContractVO.FIND_ALL_DATA_FOR_INITIAL_CONTRACT_IN_FORCE_AT_DATE";
    public static final String FIND_ALL_INITIAL_CONTRACT_IN_FORCE_NOW_BY_CLIENT_ID = "InitialContractVO.FIND_ALL_INITIAL_CONTRACT_IN_FORCE_NOW_BY_CLIENT_ID";


    @Id
    @SequenceGenerator(name = "initialcontract_id_seq", sequenceName = "initialcontract_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "initialcontract_id_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;
    private Integer contractNumber;
    private Integer variationType;
    private Date startDate;
    private Date expectedEndDate;
    private Date modificationDate;
    private Date endingDate;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private ContractJsonData contractJsonData;

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

    public ContractJsonData getContractJsonData() {
        return contractJsonData;
    }

    public void setContractJsonData(ContractJsonData contractJsonData) {
        this.contractJsonData = contractJsonData;
    }
}
