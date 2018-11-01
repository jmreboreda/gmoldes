package gmoldes.domain.initialcontractdata;

public class InitialContractData {

    private Integer clientGMId;
    private Integer workerId;
    private String quoteAccountCode;
    private String laborCategory ;
    private String weeklyWorkHours;
    private String daysOfWeekToWork;
    private String fullPartialWorkday;
    private String identificationContractNumberINEM;

    public InitialContractData() {
    }

    public InitialContractData(
            Integer clientGMId,
            Integer workerId,
            String quoteAccountCode,
            String laborCategory,
            String weeklyWorkHours,
            String daysOfWeekToWork,
            String fullPartialWorkday,
            String identificationContractNumberINEM) {

        this.clientGMId = clientGMId;
        this.workerId = workerId;
        this.quoteAccountCode = quoteAccountCode;
        this.laborCategory = laborCategory;
        this.weeklyWorkHours = weeklyWorkHours;
        this.daysOfWeekToWork = daysOfWeekToWork;
        this.fullPartialWorkday = fullPartialWorkday;
        this.identificationContractNumberINEM = identificationContractNumberINEM;
    }

    public Integer getClientGMId() {
        return clientGMId;
    }

    public void setClientGMId(Integer clientGMId) {
        this.clientGMId = clientGMId;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public String getQuoteAccountCode() {
        return quoteAccountCode;
    }

    public void setQuoteAccountCode(String quoteAccountCode) {
        this.quoteAccountCode = quoteAccountCode;
    }

    public String getLaborCategory() {
        return laborCategory;
    }

    public void setLaborCategory(String laborCategory) {
        this.laborCategory = laborCategory;
    }

    public String getWeeklyWorkHours() {
        return weeklyWorkHours;
    }

    public void setWeeklyWorkHours(String weeklyWorkHours) {
        this.weeklyWorkHours = weeklyWorkHours;
    }

    public String getDaysOfWeekToWork() {
        return daysOfWeekToWork;
    }

    public void setDaysOfWeekToWork(String daysOfWeekToWork) {
        this.daysOfWeekToWork = daysOfWeekToWork;
    }

    public String getFullPartialWorkday() {
        return fullPartialWorkday;
    }

    public void setFullPartialWorkday(String fullPartialWorkday) {
        this.fullPartialWorkday = fullPartialWorkday;
    }

    public String getIdentificationContractNumberINEM() {
        return identificationContractNumberINEM;
    }

    public void setIdentificationContractNumberINEM(String identificationContractNumberINEM) {
        this.identificationContractNumberINEM = identificationContractNumberINEM;
    }

    public static InitialContractDataBuilder create() {
        return new InitialContractDataBuilder();
    }

    public static class InitialContractDataBuilder {

        private Integer clientGMId;
        private Integer workerId;
        private String quoteAccountCode;
        private String laborCategory ;
        private String weeklyWorkHours;
        private String daysOfWeekToWork;
        private String fullPartialWorkday;
        private String identificationContractNumberINEM;


        public InitialContractDataBuilder withClientGMId(Integer clientGMId) {
            this.clientGMId = clientGMId;
            return this;
        }

        public InitialContractDataBuilder withWorkerId(Integer workerId) {
            this.workerId = workerId;
            return this;
        }

        public InitialContractDataBuilder withQuoteAccountCode(String quoteAccountCode) {
            this.quoteAccountCode = quoteAccountCode;
            return this;
        }


        public InitialContractDataBuilder withLaborCategory(String laborCategory) {
            this.laborCategory = laborCategory;
            return this;
        }

        public InitialContractDataBuilder withWeeklyWorkHours(String weeklyWorkHours) {
            this.weeklyWorkHours = weeklyWorkHours;
            return this;
        }

        public InitialContractDataBuilder withDaysOfWeekToWork(String jor_trab_dias) {
            this.daysOfWeekToWork = jor_trab_dias;
            return this;
        }

        public InitialContractDataBuilder withFullPartialWorkday(String fullPartialWorkday) {
            this.fullPartialWorkday = fullPartialWorkday;
            return this;
        }


        public InitialContractDataBuilder withIdentificationContractNumberINEM(String identificationContractNumberINEM) {
            this.identificationContractNumberINEM = identificationContractNumberINEM;
            return this;
        }

        public InitialContractData build() {
            return new InitialContractData(this.clientGMId,  this.workerId, this.quoteAccountCode, this.laborCategory, this.weeklyWorkHours, this.daysOfWeekToWork, this.fullPartialWorkday,
                    this.identificationContractNumberINEM);
        }
    }
}
