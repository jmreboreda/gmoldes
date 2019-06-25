package gmoldes.components.contract_variation.persistence.vo;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeStringType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import gmoldes.domain.contractjsondata.ContractJsonData;
import gmoldes.domain.contractjsondata.ContractScheduleJsonData;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@NamedQueries(value = {
        @NamedQuery(
                name = ContractVariationVO.FIND_ALL_ACTIVE_CONTRACT_VARIATION_IN_PERIOD,
                query = "select p from ContractVariationVO  p where startDate <= :codeFinalDate and (endingDate is null or endingDate >= :codeInitialDate) " +
                        "and (expectedEndDate is null or expectedEndDate >= :codeInitialDate) " +
                        "and (modificationDate is null or modificationDate >= :codeInitialDate) " +
                        "and variationType < 800 order by p.contractNumber, p.modificationDate, p.endingDate"
        ),
        @NamedQuery(
                name = ContractVariationVO.FIND_ALL_CONTRACT_VARIATION_TEMPORAL_IN_FORCE_NOW,
                query = "select p from ContractVariationVO p where startDate <= now() and endingDate is null and modificationDate is null and expectedEndDate is not null"
        ),
        @NamedQuery(
                name = ContractVariationVO.FIND_ALL_CONTRACT_VARIATION_IN_FORCE_IN_PERIOD,
                query = "select p from ContractVariationVO  p where startDate <= :codeFinalDate " +
                        "and (endingDate is null or endingDate >= :codeInitialDate) " +
                        "and (expectedEndDate is null or expectedEndDate >= :codeInitialDate) " +
                        "and (modificationDate is null or modificationDate >= :codeInitialDate) " +
                        "order by p.contractNumber, p.modificationDate, p.endingDate"
        ),
        @NamedQuery(
                name = ContractVariationVO.FIND_ALL_CONTRACT_VARIATION_BY_CONTRACT_NUMBER,
                query = "select p from ContractVariationVO p where p.contractNumber = :code order by p.startDate, p.modificationDate, p.variationType, p.endingDate"
        ),
        @NamedQuery(
                name = ContractVariationVO.FIND_ALL_CONTRACT_VARIATION_BY_ID,
                query = "select p from ContractVariationVO p where p.variationType = :code order by p.modificationDate, p.endingDate"
        ),
        @NamedQuery(
                name = ContractVariationVO.FIND_ALL_CONTRACT_VARIATIONS_IN_FORCE_AT_DATE,
                query = "select p from ContractVariationVO p where p.startDate <= :date and (p.endingDate is null or p.endingDate >= :date)" +
                        " and (p.modificationDate is null or p.modificationDate >= :date) and (p.expectedEndDate is null or p.expectedEndDate >= :date) order by p.modificationDate," +
                        " p.endingDate"
        ),
        @NamedQuery(
                name = ContractVariationVO.FIND_ALL_CONTRACT_VARIATIONS_ORDERED_BY_CONTRACT_NUMBER_AND_START_DATE,
                query = "select p from ContractVariationVO p order by p.contractNumber, p.startDate"
        ),
        @NamedQuery(
                name = ContractVariationVO.FIND_ALL_CONTRACT_VARIATIONS_IN_FORCE_NOW_BY_CLIENT_ID,
                query = "select p from InitialContractVO p where p.startDate <= :date and p.endingDate is null and p.modificationDate is null order by p.modificationDate, p.endingDate"
        ),
        @NamedQuery(
                name = ContractVariationVO.FIND_ALL_CONTRACT_VARIATIONS_AFTER_DATE_BY_CONTRACT_NUMBER,
                query = "select p from ContractVariationVO p where contractNumber = :contractNumber and (startDate >= :dateFromSearch or expectedEndDate >= :dateFromSearch)" +
                        " order by p.modificationDate, p.endingDate"
        )
})

@Entity
@Table(name = "contract_variation")
@TypeDefs({
        @TypeDef(name = "string-array", typeClass = StringArrayType.class),
        @TypeDef(name = "int-array", typeClass = IntArrayType.class),
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
        @TypeDef(name = "jsonb-node", typeClass = JsonNodeBinaryType.class),
        @TypeDef(name = "json-node", typeClass = JsonNodeStringType.class),
})

public class ContractVariationVO implements Serializable {

    public static final String FIND_ALL_CONTRACT_VARIATION_BY_CONTRACT_NUMBER = "ContractVariationVO.FIND_ALL_CONTRACT_VARIATION_BY_CONTRACT_NUMBER";
    public static final String FIND_ALL_CONTRACT_VARIATION_BY_ID = "ContractVariationVO.FIND_ALL_CONTRACT_VARIATION_BY_ID";
    public static final String FIND_ALL_ACTIVE_CONTRACT_VARIATION_IN_PERIOD = "ContractVariationVO.FIND_ALL_ACTIVE_CONTRACT_VARIATION_IN_PERIOD";
    public static final String FIND_ALL_CONTRACT_VARIATION_TEMPORAL_IN_FORCE_NOW = "ContractVariationVO.FIND_ALL_CONTRACT_VARIATION_TEMPORAL_IN_FORCE_NOW";
    public static final String FIND_ALL_CONTRACT_VARIATION_IN_FORCE_IN_PERIOD = "ContractVariationVO.FIND_ALL_CONTRACT_VARIATION_IN_FORCE_IN_PERIOD";
    public static final String FIND_ALL_CONTRACT_VARIATIONS_IN_FORCE_AT_DATE = "ContractVariationVO.FIND_ALL_CONTRACT_VARIATIONS_IN_FORCE_AT_DATE";
    public static final String FIND_ALL_CONTRACT_VARIATIONS_ORDERED_BY_CONTRACT_NUMBER_AND_START_DATE = "ContractVariationVO.FIND_ALL_CONTRACT_VARIATIONS_ORDERED_BY_CONTRACT_NUMBER_AND_START_DATE";
    public static final String FIND_ALL_CONTRACT_VARIATIONS_IN_FORCE_NOW_BY_CLIENT_ID = "ContractVariationVO.FIND_ALL_CONTRACT_VARIATIONS_IN_FORCE_NOW_BY_CLIENT_ID";
    public static final String FIND_ALL_CONTRACT_VARIATIONS_AFTER_DATE_BY_CONTRACT_NUMBER = "ContractVariationVO.FIND_ALL_CONTRACT_VARIATIONS_AFTER_DATE_BY_CONTRACT_NUMBER";

    @Id
    @SequenceGenerator(name = "contractvariation_id_seq", sequenceName = "contractvariation_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contractvariation_id_seq")
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
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private ContractScheduleJsonData contractScheduleJsonData;


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

    public ContractScheduleJsonData getContractScheduleJsonData() {
        return contractScheduleJsonData;
    }

    public void setContractScheduleJsonData(ContractScheduleJsonData contractScheduleJsonData) {
        this.contractScheduleJsonData = contractScheduleJsonData;
    }
}
