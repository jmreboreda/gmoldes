package gmoldes.domain.contract.dto;

public class ContractTypeDTO {

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

    public ContractTypeDTO(Integer id,
                              Integer contractCode,
                              String contractDescription,
                              String colloquial,
                              Boolean isInitialContract,
                              Boolean isTemporal,
                              Boolean isUndefined,
                              Boolean isPartialTime,
                              Boolean isFullTime,
                              Boolean isMenuSelectable) {
        this.id = id;
        this.contractCode = contractCode;
        this.contractDescription = contractDescription;
        this.colloquial = colloquial;
        this.isInitialContract = isInitialContract;
        this.isTemporal = isTemporal;
        this.isUndefined = isUndefined;
        this.isPartialTime = isPartialTime;
        this.isFullTime = isFullTime;
        this.isMenuSelectable = isMenuSelectable;
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

    public void setIsUndefined(Boolean isUndefined) {
        this.isUndefined = isUndefined;
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

    public void setIsMenuSelectable(Boolean isMenuSelectable) {
        this.isMenuSelectable = isMenuSelectable;
    }

    public String toString(){
        return  getColloquial() + " [ " + getContractDescription() + " ] ";
    }

    public static ContractTypeDTOBuilder create() {
        return new ContractTypeDTOBuilder();
    }

    public static class ContractTypeDTOBuilder {

        private Integer id;
        private Integer contractcode;
        private String contractdescription;
        private String colloquial;
        private Boolean isinitialcontract;
        private Boolean istemporal;
        private Boolean isundefined;
        private Boolean ispartialtime;
        private Boolean isfulltime;
        private Boolean ismenuselectable;

        public ContractTypeDTOBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public ContractTypeDTOBuilder withContractCode(Integer contractcode) {
            this.contractcode = contractcode;
            return this;
        }

        public ContractTypeDTOBuilder withContractDescription(String contractdescription) {
            this.contractdescription = contractdescription;
            return this;
        }

        public ContractTypeDTOBuilder withColloquial(String colloquial) {
            this.colloquial = colloquial;
            return this;
        }

        public ContractTypeDTOBuilder withIsInitialContract(Boolean isinitialcontract) {
            this.isinitialcontract = isinitialcontract;
            return this;
        }

        public ContractTypeDTOBuilder withIsTemporal(Boolean istemporal) {
            this.istemporal = istemporal;
            return this;
        }

        public ContractTypeDTOBuilder withIsUndefined(Boolean isundefined) {
            this.isundefined = isundefined;
            return this;
        }

        public ContractTypeDTOBuilder withIsPartialTime(Boolean ispartialtime) {
            this.ispartialtime = ispartialtime;
            return this;
        }

         public ContractTypeDTOBuilder withIsFullTime(Boolean isfulltime) {
            this.isfulltime = isfulltime;
            return this;
        }

        public ContractTypeDTOBuilder withIsMenuSelectable(Boolean ismenuselectable) {
            this.ismenuselectable = ismenuselectable;
            return this;
        }

        public ContractTypeDTO build() {
            return new ContractTypeDTO(this.id, this.contractcode, this.contractdescription, this.colloquial, this.isinitialcontract, this.istemporal,
            this.isundefined, this.ispartialtime, this.isfulltime, this.ismenuselectable);
        }
    }
}
