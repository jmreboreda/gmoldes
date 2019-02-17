package gmoldes.domain.contract.persistence.vo;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeStringType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import gmoldes.components.contract.new_contract.components.WorkDaySchedule;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

@NamedQueries(value = {
        @NamedQuery(
                name = ContractVO.FIND_CONTRACTS_EXPIRATION,
                query = "select p from ContractVO p where p.preavisofin is null AND p.f_hasta is not null and date(p.f_hasta) - date(NOW()) <= 20"
        ),
        @NamedQuery(
                name = ContractVO.IDC_CONTROL,
                query = "select p from ContractVO p where p.idc is null and date(p.f_desde) - date (now()) <= 3"
        ),
        @NamedQuery(
                name = ContractVO.FIND_ALL_CONTRACTS_ORDERED_BY_CONTRACTNUMBER_AND_VARIATION,
                query = "select p from ContractVO p order by p.numcontrato, p.numvariacion"
        ),
        @NamedQuery(
                name = ContractVO.FIND_ALL_CONTRACTS_BY_CLIENT_ID,
                query = "select p from ContractVO p where p.idcliente_gm = :code order by p.trabajador_name"
        ),
        @NamedQuery(
                name = ContractVO.FIND_ALL_CONTRACTS_BY_CLIENT_ID_IN_PERIOD,
                query = "select p from ContractVO p where p.idcliente_gm = :code and p.f_desde <= :initialperiod and (p.f_hasta >= :initialperiod or p.f_hasta is null) order by p.trabajador_name"
        ),
        @NamedQuery(
                name = ContractVO.FIND_ALL_CONTRACTS_WITH_TIMERECORD_BY_CLIENT_ID_IN_PERIOD,
                query = "select p from ContractVO p where p.idcliente_gm = :code " +
                        "and tipovariacion < 800 " +
                        "and concat(trim(to_char(extract(year from p.f_desde),'9999')), trim(to_char(extract(month from p.f_desde),'09'))) <= :initialperiod " +
                        "and (concat(trim(to_char(extract(year from p.f_hasta),'9999')), trim(to_char(extract(month from p.f_hasta),'09'))) >= :initialperiod " +
                        "or p.f_hasta is null) and (jor_tipo = 'A tiempo parcial' or tipoctto = 'Formación') order by p.trabajador_name, p.f_desde"
        ),
        @NamedQuery(
                name = ContractVO.FIND_ALL_ACTIVE_CONTRACTS_BY_CLIENT_ID,
                query = "select p from ContractVO p where p.envigor = true and p.idcliente_gm = :code order by p.trabajador_name"
        ),
        @NamedQuery(
                name = ContractVO.FIND_ALL_CLIENT_WITH_ACTIVE_CONTRACT_SORTED,
                query = "select p from ContractVO p where p.envigor = true order by p.clientegm_name"
        ),
        @NamedQuery(
                name = ContractVO.FIND_ALL_CLIENT_WITH_CONTRACT_WITH_TIMERECORD_IN_PERIOD_SORTED,
                query = "select p from ContractVO p where tipovariacion < 800" +
                        "and concat(trim(to_char(extract(year from p.f_desde),'9999')), trim(to_char(extract(month from p.f_desde),'09'))) <= :yearMonth " +
                        "and (concat(trim(to_char(extract(year from p.f_hasta),'9999')), trim(to_char(extract(month from p.f_hasta),'09'))) >= :yearMonth " +
                        "or p.f_hasta is null) and (jor_tipo = 'A tiempo parcial' or tipoctto = 'Formación') order by p.clientegm_name"
        ),
        @NamedQuery(
                name = ContractVO.FIND_ALL_CLIENT_WITH_ACTIVE_CONTRACT_WITH_TIMERECORD_SORTED,
                query = "select p from ContractVO p where p.envigor = true and (p.jor_tipo = 'A tiempo parcial' or tipoctto = 'Formación') order by p.clientegm_name"
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
    public static final String FIND_CONTRACTS_EXPIRATION = "ContractVO.FIND_CONTRACTS_EXPIRATION";
    public static final String IDC_CONTROL = "ContractVO.IDC_CONTROL";
    public static final String FIND_ALL_CONTRACTS_ORDERED_BY_CONTRACTNUMBER_AND_VARIATION = "ContractVO.FIND_ALL_CONTRACTS_ORDERED_BY_CONTRACTNUMBER_AND_STARTDATE";
    public static final String FIND_ALL_CONTRACTS_BY_CLIENT_ID = "ContractVO.FIND_ALL_CONTRACTS_BY_CLIENT_ID";
    public static final String FIND_ALL_CONTRACTS_BY_CLIENT_ID_IN_PERIOD = "ContractVO.FIND_ALL_CONTRACTS_BY_CLIENT_ID_IN_PERIOD";
    public static final String FIND_ALL_ACTIVE_CONTRACTS_BY_CLIENT_ID = "ContractVO.FIND_ALL_ACTIVE_CONTRACTS_BY_CLIENT_ID";
    public static final String FIND_ALL_CLIENT_WITH_ACTIVE_CONTRACT_SORTED = "ContractVO.FIND_ALL_CLIENT_WITH_ACTIVE_CONTRACT_SORTED";
    public static final String FIND_ALL_CLIENT_WITH_ACTIVE_CONTRACT_WITH_TIMERECORD_SORTED = "ContractVO.FIND_ALL_CLIENT_WITH_ACTIVE_CONTRACT_WITH_TIMERECORD_SORTED";
    public static final String FIND_ALL_CONTRACTS_WITH_TIMERECORD_BY_CLIENT_ID_IN_PERIOD = "ContractVO.FIND_ALL_CONTRACTS_WITH_TIMERECORD_BY_CLIENT_ID_IN_PERIOD";
    public static final String FIND_ALL_CLIENT_WITH_CONTRACT_WITH_TIMERECORD_IN_PERIOD_SORTED = "ContractVO.FIND_ALL_CLIENT_WITH_CONTRACT_WITH_TIMERECORD_IN_PERIOD_SORTED";

    @Id
    @SequenceGenerator(name = "contract_id_seq", sequenceName = "contract_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_id_seq")
    @Column(name = "id", updatable = false)
    private Integer id;
    private Integer employer;
    private Integer employee;
    private String contractType;
    private Integer gmContractNumber;
    private String variationType;
    private Date startDate;
    private Date expectedEndDate;
    private Date modificationDate;
    private Date endingDate;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Set<WorkDaySchedule> weeklyWorkSchedule;
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

    public Integer getEmployer() {
        return employer;
    }

    public void setEmployer(Integer employer) {
        this.employer = employer;
    }

    public Integer getEmployee() {
        return employee;
    }

    public void setEmployee(Integer employee) {
        this.employee = employee;
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

    public String getVariationType() {
        return variationType;
    }

    public void setVariationType(String variationType) {
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

    public Set<WorkDaySchedule> getWeeklyWorkSchedule() {
        return weeklyWorkSchedule;
    }

    public void setWeeklyWorkSchedule(Set<WorkDaySchedule> weeklyWorkSchedule) {
        this.weeklyWorkSchedule = weeklyWorkSchedule;
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
