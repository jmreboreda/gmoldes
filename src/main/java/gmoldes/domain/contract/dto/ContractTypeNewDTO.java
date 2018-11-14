package gmoldes.domain.contract.dto;

public class ContractTypeNewDTO {

    private Integer id;
    private Integer contractcode;
    private String contractdescription;
    private String colloquial;
    private Boolean isinitialcontract;
    private Boolean ispartialtime;
    private Boolean isfulltime;

    public ContractTypeNewDTO(Integer id,
                              Integer contractcode,
                              String contractdescription,
                              String colloquial,
                              Boolean isinitialcontract,
                              Boolean ispartialtime,
                              Boolean isfulltime) {
        this.id = id;
        this.contractcode = contractcode;
        this.contractdescription = contractdescription;
        this.colloquial = colloquial;
        this.isinitialcontract = isinitialcontract;
        this.ispartialtime = ispartialtime;
        this.isfulltime = isfulltime;
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
