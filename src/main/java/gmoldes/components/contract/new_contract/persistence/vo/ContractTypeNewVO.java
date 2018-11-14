package gmoldes.components.contract.new_contract.persistence.vo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "contracttype")
@NamedQueries(value = {
        @NamedQuery(
                name = ContractTypeNewVO.FIND_ALL_CONTRACT_TYPES,
                query = "select p from ContractTypeNewVO p order by contractdescription"
        ),
        @NamedQuery(
                name = ContractTypeNewVO.FIND_CONTRACT_TYPE_BY_ID,
                query = "select p from ContractTypeNewVO p where p.contractcode = :contractTypeId"
        )
})

public class ContractTypeNewVO implements Serializable{

    public static final String FIND_ALL_CONTRACT_TYPES = "ContractTypeNewVO.FIND_ALL_CONTRACT_TYPES";
    public static final String FIND_CONTRACT_TYPE_BY_ID = "ContractTypeNewVO.FIND_CONTRACT_TYPE_BY_ID";


    @Id
    @SequenceGenerator(name = "contracttype_id_seq", sequenceName = "contracttype_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contracttype_id_seq")
    @Column(name = "id", updatable = false)
    private Integer id;
    private Integer contractcode;
    private String contractdescription;
    private String colloquial;
    private Boolean isinitialcontract;
    private Boolean ispartialtime;
    private Boolean isfulltime;

    public ContractTypeNewVO() {
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
}
