package gmoldes.components.contract.new_contract.persistence.vo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "contracttype")
@NamedQueries(value = {
        @NamedQuery(
                name = ContractTypeVO.FIND_ALL_CONTRACT_TYPES_SELECTABLES,
                query = "select p from ContractTypeVO p where ismenuselectable = true and isinitialcontract = true order by contractdescription"
        ),
        @NamedQuery(
                name = ContractTypeVO.FIND_CONTRACT_TYPE_BY_ID,
                query = "select p from ContractTypeVO p where p.contractcode = :contractTypeId"
        )
})

public class ContractTypeVO implements Serializable{

    public static final String FIND_ALL_CONTRACT_TYPES_SELECTABLES = "ContractTypeVO.FIND_ALL_CONTRACT_TYPES_SELECTABLES";
    public static final String FIND_CONTRACT_TYPE_BY_ID = "ContractTypeVO.FIND_CONTRACT_TYPE_BY_ID";


    @Id
    @SequenceGenerator(name = "contracttype_id_seq", sequenceName = "contracttype_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contracttype_id_seq")
    @Column(name = "id", updatable = false)
    private Integer id;
    private Integer contractcode;
    private String contractdescription;
    private String colloquial;
    private Boolean isinitialcontract;
    private Boolean istemporal;
    private Boolean ispartialtime;
    private Boolean isfulltime;
    private Boolean ismenuselectable;

    public ContractTypeVO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContractcode() {
        return contractcode;
    }

    public void setContractcode(Integer contractcode) {
        this.contractcode = contractcode;
    }

    public String getContractdescription() {
        return contractdescription;
    }

    public void setContractdescription(String contractdescription) {
        this.contractdescription = contractdescription;
    }

    public String getColloquial() {
        return colloquial;
    }

    public void setColloquial(String colloquial) {
        this.colloquial = colloquial;
    }

    public Boolean getIsinitialcontract() {
        return isinitialcontract;
    }

    public void setIsinitialcontract(Boolean isinitialcontract) {
        this.isinitialcontract = isinitialcontract;
    }

    public Boolean getIsTemporal() {
        return istemporal;
    }

    public void setIsTemporal(Boolean isTemporal) {
        istemporal = isTemporal;
    }

    public Boolean getIspartialtime() {
        return ispartialtime;
    }

    public void setIspartialtime(Boolean ispartialtime) {
        this.ispartialtime = ispartialtime;
    }

    public Boolean getIsfulltime() {
        return isfulltime;
    }

    public void setIsfulltime(Boolean isfulltime) {
        this.isfulltime = isfulltime;
    }

    public Boolean getIsMenuSelectable() {
        return ismenuselectable;
    }

    public void setIsMenuSelectable(Boolean ismenuselectable) {
        this.ismenuselectable = ismenuselectable;
    }
}
