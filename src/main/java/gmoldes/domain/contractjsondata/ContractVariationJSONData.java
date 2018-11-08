package gmoldes.domain.contractjsondata;

public class ContractVariationJSONData {

    private Integer clientGMId;
    private Integer workerId;
    private String quoteAccountCode;
    private String laborCategory ;
    private String weeklyWorkHours;
    private String daysOfWeekToWork;
    private String fullPartialWorkDay;
    private Integer contractType;
    private String identificationContractNumberINEM;
    private String notesForContractManager;
    private String privateNotes;

    public ContractVariationJSONData() {
    }

    public ContractVariationJSONData(
            Integer clientGMId,
            Integer workerId,
            String quoteAccountCode,
            String laborCategory,
            String weeklyWorkHours,
            String daysOfWeekToWork,
            String fullPartialWorkDay,
            Integer contractType,
            String identificationContractNumberINEM,
            String notesForContractManager,
            String privateNotes){

        this.clientGMId = clientGMId;
        this.workerId = workerId;
        this.quoteAccountCode = quoteAccountCode;
        this.laborCategory = laborCategory;
        this.weeklyWorkHours = weeklyWorkHours;
        this.daysOfWeekToWork = daysOfWeekToWork;
        this.fullPartialWorkDay = fullPartialWorkDay;
        this.contractType = contractType;
        this.identificationContractNumberINEM = identificationContractNumberINEM;
        this.notesForContractManager = notesForContractManager;
        this.privateNotes = privateNotes;
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

    public String getFullPartialWorkDay() {
        return fullPartialWorkDay;
    }

    public void setFullPartialWorkDay(String fullPartialWorkDay) {
        this.fullPartialWorkDay = fullPartialWorkDay;
    }

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }

    public String getIdentificationContractNumberINEM() {
        return identificationContractNumberINEM;
    }

    public void setIdentificationContractNumberINEM(String identificationContractNumberINEM) {
        this.identificationContractNumberINEM = identificationContractNumberINEM;
    }

    public String getNotesForContractManager() {
        return notesForContractManager;
    }

    public void setNotesForContractManager(String notesForContractManager) {
        this.notesForContractManager = notesForContractManager;
    }

    public String getPrivateNotes() {
        return privateNotes;
    }

    public void setPrivateNotes(String privateNotes) {
        this.privateNotes = privateNotes;
    }

    public static ContractVariationJSONDataBuilder create() {
        return new ContractVariationJSONDataBuilder();
    }

    public static class ContractVariationJSONDataBuilder {

        private Integer clientGMId;
        private Integer workerId;
        private String quoteAccountCode;
        private String laborCategory ;
        private String weeklyWorkHours;
        private String daysOfWeekToWork;
        private String fullPartialWorkday;
        private Integer contractType;
        private String identificationContractNumberINEM;
        private String notesForContractManager;
        private String privateNotes;


        public ContractVariationJSONDataBuilder withClientGMId(Integer clientGMId) {
            this.clientGMId = clientGMId;
            return this;
        }

        public ContractVariationJSONDataBuilder withWorkerId(Integer workerId) {
            this.workerId = workerId;
            return this;
        }

        public ContractVariationJSONDataBuilder withQuoteAccountCode(String quoteAccountCode) {
            this.quoteAccountCode = quoteAccountCode;
            return this;
        }

        public ContractVariationJSONDataBuilder withLaborCategory(String laborCategory) {
            this.laborCategory = laborCategory;
            return this;
        }

        public ContractVariationJSONDataBuilder withWeeklyWorkHours(String weeklyWorkHours) {
            this.weeklyWorkHours = weeklyWorkHours;
            return this;
        }

        public ContractVariationJSONDataBuilder withDaysOfWeekToWork(String jor_trab_dias) {
            this.daysOfWeekToWork = jor_trab_dias;
            return this;
        }

        public ContractVariationJSONDataBuilder withFullPartialWorkday(String fullPartialWorkday) {
            this.fullPartialWorkday = fullPartialWorkday;
            return this;
        }

        public ContractVariationJSONDataBuilder withContractType(Integer contractType) {
            this.contractType = contractType;
            return this;
        }

        public ContractVariationJSONDataBuilder withIdentificationContractNumberINEM(String identificationContractNumberINEM) {
            this.identificationContractNumberINEM = identificationContractNumberINEM;
            return this;
        }

        public ContractVariationJSONDataBuilder withNotesForContractManager(String notesForContractManager) {
            this.notesForContractManager = notesForContractManager;
            return this;
        }

        public ContractVariationJSONDataBuilder withPrivateNotes(String privateNotes) {
            this.privateNotes = privateNotes;
            return this;
        }

        public ContractVariationJSONData build() {
            return new ContractVariationJSONData(this.clientGMId,  this.workerId, this.quoteAccountCode, this.laborCategory,
                    this.weeklyWorkHours, this.daysOfWeekToWork, this.fullPartialWorkday, this.contractType, this.identificationContractNumberINEM, this.notesForContractManager,
                    this.privateNotes);
        }
    }
}
