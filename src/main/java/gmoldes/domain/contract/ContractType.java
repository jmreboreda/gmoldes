package gmoldes.domain.contract;

public class ContractType {

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

    public ContractType(Integer id,
                        Integer contractCode,
                        String contractDescription,
                        String colloquial,
                        Boolean isInitialContract,
                        Boolean isTemporal,
                        Boolean isUndefined,
                        Boolean isPartialTime,
                        Boolean isFullTime,
                        Boolean isMenuSelectable,
                        Boolean isDeterminedDuration,
                        Boolean isSurrogate,
                        Boolean isAdminPartnerSimilar,
                        Boolean isInterim) {
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
        this.isDeterminedDuration = isDeterminedDuration;
        this.isSurrogate = isSurrogate;
        this.isAdminPartnerSimilar = isAdminPartnerSimilar;
        this.isInterim = isInterim;

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

    public Boolean getIsDeterminedDuration() {
        return isDeterminedDuration;
    }

    public void setIsDeterminedDuration(Boolean isDeterminedDuration) {
        this.isDeterminedDuration = isDeterminedDuration;
    }

    public Boolean getSurrogate() {
        return isSurrogate;
    }

    public void setSurrogate(Boolean surrogate) {
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

    public String toString(){
        return  getColloquial() + " [ " + getContractDescription() + " ] ";
    }

    public static ContractTypeDTOBuilder create() {
        return new ContractTypeDTOBuilder();
    }

    public static class ContractTypeDTOBuilder {

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

        public ContractTypeDTOBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public ContractTypeDTOBuilder withContractCode(Integer contractcode) {
            this.contractCode = contractcode;
            return this;
        }

        public ContractTypeDTOBuilder withContractDescription(String contractdescription) {
            this.contractDescription = contractdescription;
            return this;
        }

        public ContractTypeDTOBuilder withColloquial(String colloquial) {
            this.colloquial = colloquial;
            return this;
        }

        public ContractTypeDTOBuilder withIsInitialContract(Boolean isinitialcontract) {
            this.isInitialContract = isinitialcontract;
            return this;
        }

        public ContractTypeDTOBuilder withIsTemporal(Boolean istemporal) {
            this.isTemporal = istemporal;
            return this;
        }

        public ContractTypeDTOBuilder withIsUndefined(Boolean isundefined) {
            this.isUndefined = isundefined;
            return this;
        }

        public ContractTypeDTOBuilder withIsPartialTime(Boolean ispartialtime) {
            this.isPartialTime = ispartialtime;
            return this;
        }

         public ContractTypeDTOBuilder withIsFullTime(Boolean isfulltime) {
            this.isFullTime = isfulltime;
            return this;
        }

        public ContractTypeDTOBuilder withIsMenuSelectable(Boolean ismenuselectable) {
            this.isMenuSelectable = ismenuselectable;
            return this;
        }

        public ContractTypeDTOBuilder withIsDeterminedDuration(Boolean isDeterminedDuration) {
            this.isDeterminedDuration = isDeterminedDuration;
            return this;
        }

        public ContractTypeDTOBuilder withIsSurrogate(Boolean isSurrogate) {
            this.isSurrogate = isSurrogate;
            return this;
        }

        public ContractTypeDTOBuilder withIsAdminPartnerSimilar(Boolean isAdminPartnerSimilar) {
            this.isAdminPartnerSimilar = isAdminPartnerSimilar;
            return this;
        }

        public ContractTypeDTOBuilder withIsInterim(Boolean isInterim) {
            this.isInterim = isInterim;
            return this;
        }

        public ContractType build() {
            return new ContractType(this.id, this.contractCode, this.contractDescription, this.colloquial, this.isInitialContract, this.isTemporal,
            this.isUndefined, this.isPartialTime, this.isFullTime, this.isMenuSelectable, this.isDeterminedDuration, this.isSurrogate, this.isAdminPartnerSimilar, this.isInterim);
        }
    }
}
