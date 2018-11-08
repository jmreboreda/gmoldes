package gmoldes.components.contract.initial_contract.persistence.vo;

import gmoldes.domain.contractjsondata.InitialContractJSONData;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeStringType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@NamedQueries(value = {
        @NamedQuery(
                name = InitialContractVO.FIND_ALL_INITIAL_CONTRACT_SORTED,
                query = "select p from InitialContractVO p order by p.contractNumber, p.startDate"
        ),
        @NamedQuery(
                name = InitialContractVO.FIND_ALL_CONTRACTS_BY_CLIENT_ID,
                query = "select p from ContractVO p where p.idcliente_gm = :code order by p.trabajador_name"
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

    public static final String FIND_ALL_INITIAL_CONTRACT_SORTED = "InitialContractVO.FIND_ALL_CONTRACTS_ORDERED_BY_CONTRACTNUMBER_AND_VARIATION";
    public static final String FIND_ALL_CONTRACTS_BY_CLIENT_ID = "InitialContractVO.FIND_ALL_CONTRACTS_BY_CLIENT_ID";

    @Id
    @SequenceGenerator(name = "initialcontract_id_seq", sequenceName = "initialcontract_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "initialcontract_id_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;
    private Integer contractNumber;
    private Integer variationType;
    private Date startDate;
    private Date expectedEndDate;
    private Date endingDate;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private InitialContractJSONData initialContractJSONData;

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

    public Date getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public InitialContractJSONData getInitialContractJSONData() {
        return initialContractJSONData;
    }

    public void setInitialContractJSONData(InitialContractJSONData initialContractJSONData) {
        this.initialContractJSONData = initialContractJSONData;
    }
}
