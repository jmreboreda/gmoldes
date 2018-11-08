package gmoldes.domain.contractjsondata;

public class InitialContractJSONData {

    private Integer clientGMId;
    private Integer workerId;
    private String quoteAccountCode;
    private Integer contractType;
    private String laborCategory ;
    private String weeklyWorkHours;
    private String daysOfWeekToWork;
    private String fullPartialWorkday;
    private String identificationContractNumberINEM;
    private String notesForContractManager;
    private String privateNotes;

    public InitialContractJSONData() {
    }

    public InitialContractJSONData(
            Integer clientGMId,
            Integer workerId,
            String quoteAccountCode,
            Integer contractType,
            String laborCategory,
            String weeklyWorkHours,
            String daysOfWeekToWork,
            String fullPartialWorkday,
            String identificationContractNumberINEM,
            String notesForContractManager,
            String privateNotes){

        this.clientGMId = clientGMId;
        this.workerId = workerId;
        this.quoteAccountCode = quoteAccountCode;
        this.contractType = contractType;
        this.laborCategory = laborCategory;
        this.weeklyWorkHours = weeklyWorkHours;
        this.daysOfWeekToWork = daysOfWeekToWork;
        this.fullPartialWorkday = fullPartialWorkday;
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

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
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

    public static InitialContractJSONDataBuilder create() {
        return new InitialContractJSONDataBuilder();
    }

    public static class InitialContractJSONDataBuilder {

        private Integer clientGMId;
        private Integer workerId;
        private String quoteAccountCode;
        private String laborCategory ;
        private Integer contractType;
        private String weeklyWorkHours;
        private String daysOfWeekToWork;
        private String fullPartialWorkday;
        private String identificationContractNumberINEM;
        private String notesForContractManager;
        private String privateNotes;


        public InitialContractJSONDataBuilder withClientGMId(Integer clientGMId) {
            this.clientGMId = clientGMId;
            return this;
        }

        public InitialContractJSONDataBuilder withWorkerId(Integer workerId) {
            this.workerId = workerId;
            return this;
        }

        public InitialContractJSONDataBuilder withQuoteAccountCode(String quoteAccountCode) {
            this.quoteAccountCode = quoteAccountCode;
            return this;
        }

        public InitialContractJSONDataBuilder withContractType(Integer contractType) {
            this.contractType = contractType;
            return this;
        }

        public InitialContractJSONDataBuilder withLaborCategory(String laborCategory) {
            this.laborCategory = laborCategory;
            return this;
        }

        public InitialContractJSONDataBuilder withWeeklyWorkHours(String weeklyWorkHours) {
            this.weeklyWorkHours = weeklyWorkHours;
            return this;
        }

        public InitialContractJSONDataBuilder withDaysOfWeekToWork(String jor_trab_dias) {
            this.daysOfWeekToWork = jor_trab_dias;
            return this;
        }

        public InitialContractJSONDataBuilder withFullPartialWorkday(String fullPartialWorkday) {
            this.fullPartialWorkday = fullPartialWorkday;
            return this;
        }


        public InitialContractJSONDataBuilder withIdentificationContractNumberINEM(String identificationContractNumberINEM) {
            this.identificationContractNumberINEM = identificationContractNumberINEM;
            return this;
        }

        public InitialContractJSONDataBuilder withNotesForContractManager(String notesForContractManager) {
            this.notesForContractManager = notesForContractManager;
            return this;
        }

        public InitialContractJSONDataBuilder withPrivateNotes(String privateNotes) {
            this.privateNotes = privateNotes;
            return this;
        }

        public InitialContractJSONData build() {
            return new InitialContractJSONData(this.clientGMId,  this.workerId, this.quoteAccountCode, this.contractType, this.laborCategory, this.weeklyWorkHours,
                    this.daysOfWeekToWork, this.fullPartialWorkday, this.identificationContractNumberINEM, this.notesForContractManager, this.privateNotes);
        }
    }
}
