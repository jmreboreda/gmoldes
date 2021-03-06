package gmoldes.domain.contract_type.persistence.vo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "contract_type")
@NamedQueries(value = {
        @NamedQuery(
                name = ContractTypeVO.FIND_ALL_CONTRACT_TYPES_SELECTABLES,
                query = "select p from ContractTypeVO p where isMenuSelectable = true and isInitialContract = true order by contractDescription"
        ),
        @NamedQuery(
                name = ContractTypeVO.FIND_CONTRACT_TYPE_BY_CONTRACT_TYPE_CODE,
                query = "select p from ContractTypeVO p where p.contractCode = :contractTypeCode"
        )
})

public class ContractTypeVO implements Serializable{

    public static final String FIND_ALL_CONTRACT_TYPES_SELECTABLES = "ContractTypeVO.FIND_ALL_CONTRACT_TYPES_SELECTABLES";
    public static final String FIND_CONTRACT_TYPE_BY_CONTRACT_TYPE_CODE = "ContractTypeVO.FIND_CONTRACT_TYPE_BY_CONTRACT_TYPE_CODE";

    @Id
    @SequenceGenerator(name = "contracttype_id_seq", sequenceName = "contracttype_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contracttype_id_seq")
    @Column(name = "id", updatable = false)
    private Integer id;
    private Integer contractCode;
    private String contractDescription;
    private String colloquial;
    private Boolean isInitialContract;
    private Boolean isTemporal;
    private Boolean isUndefined;
    private Boolean isPartialTime;
    private Boolean isFullTime;
    private Boolean isMenuSelectable;
    private Boolean isDeterminedDuration;
    private Boolean isSurrogate;
    private Boolean isAdminPartnerSimilar;
    private Boolean isInterim;

    public ContractTypeVO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContractCode() {
        return contractCode;
    }

    public void setContractCode(Integer contractCode) {
        this.contractCode = contractCode;
    }

    public String getContractDescription() {
        return contractDescription;
    }

    public void setContractDescription(String contractDescription) {
        this.contractDescription = contractDescription;
    }

    public String getColloquial() {
        return colloquial;
    }

    public void setColloquial(String colloquial) {
        this.colloquial = colloquial;
    }

    public Boolean getIsInitialContract() {
        return isInitialContract;
    }

    public void setIsInitialContract(Boolean isInitialContract) {
        this.isInitialContract = isInitialContract;
    }

    public Boolean getIsTemporal() {
        return isTemporal;
    }

    public void setIsTemporal(Boolean isTemporal) {
        this.isTemporal = isTemporal;
    }

      public Boolean getIsUndefined() {
        return isUndefined;
    }

    public void setIsUndefined(Boolean isundefined) {
        this.isUndefined = isundefined;
    }

    public Boolean getIsPartialTime() {
        return isPartialTime;
    }

    public void setIsPartialTime(Boolean isPartialTime) {
        this.isPartialTime = isPartialTime;
    }

    public Boolean getIsFullTime() {
        return isFullTime;
    }

    public void setIsFullTime(Boolean isFullTime) {
        this.isFullTime = isFullTime;
    }

    public Boolean getIsMenuSelectable() {
        return isMenuSelectable;
    }

    public void setIsMenuSelectable(Boolean isMenuselectable) {
        this.isMenuSelectable = isMenuselectable;
    }

    public Boolean getIsDeterminedDuration() {
        return isDeterminedDuration;
    }

    public void setIsDeterminedDuration(Boolean isDeterminedDuration) {
        this.isDeterminedDuration = isDeterminedDuration;
    }

    public Boolean getSurrogate() {
        return isSurrogate;
    }

    public void setSurrogated(Boolean surrogate) {
        isSurrogate = surrogate;
    }

    public Boolean getAdminPartnerSimilar() {
        return isAdminPartnerSimilar;
    }

    public void setAdminPartnerSimilar(Boolean adminPartnerSimilar) {
        isAdminPartnerSimilar = adminPartnerSimilar;
    }

    public Boolean getInterim() {
        return isInterim;
    }

    public void setInterim(Boolean interim) {
        isInterim = interim;
    }
}
