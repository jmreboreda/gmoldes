package gmoldes.domain.contractjsondata;

public class ContractJsonData {

    private Integer clientGMId;
    private Integer workerId;
    private String quoteAccountCode;
    private Integer contractType;
    private String laborCategory ;
    private String weeklyWorkHours;
    private String daysOfWeekToWork;
    private Object workWeekSchedule;
    private String fullPartialWorkDay;
    private String identificationContractNumberINEM;
    private String notesForContractManager;
    private String privateNotes;

    public ContractJsonData() {
    }

    public ContractJsonData(
            Integer clientGMId,
            Integer workerId,
            String quoteAccountCode,
            Integer contractType,
            String laborCategory,
            String weeklyWorkHours,
            String daysOfWeekToWork,
            Object workWeekSchedule,
            String fullPartialWorkDay,
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
        this.workWeekSchedule = workWeekSchedule;
        this.fullPartialWorkDay = fullPartialWorkDay;
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

    public Object getWorkWeekSchedule() {
        return workWeekSchedule;
    }

    public void setWorkWeekSchedule(Object workWeekSchedule) {
        this.workWeekSchedule = workWeekSchedule;
    }

    public String getFullPartialWorkDay() {
        return fullPartialWorkDay;
    }

    public void setFullPartialWorkDay(String fullPartialWorkDay) {
        this.fullPartialWorkDay = fullPartialWorkDay;
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

    public static ContractJsonDataBuilder create() {
        return new ContractJsonDataBuilder();
    }

    public static class ContractJsonDataBuilder {

        private Integer clientGMId;
        private Integer workerId;
        private String quoteAccountCode;
        private String laborCategory ;
        private Integer contractType;
        private String weeklyWorkHours;
        private String daysOfWeekToWork;
        private Object workWeekSchedule;
        private String fullPartialWorkDay;
        private String identificationContractNumberINEM;
        private String notesForContractManager;
        private String privateNotes;


        public ContractJsonDataBuilder withClientGMId(Integer clientGMId) {
            this.clientGMId = clientGMId;
            return this;
        }

        public ContractJsonDataBuilder withWorkerId(Integer workerId) {
            this.workerId = workerId;
            return this;
        }

        public ContractJsonDataBuilder withQuoteAccountCode(String quoteAccountCode) {
            this.quoteAccountCode = quoteAccountCode;
            return this;
        }

        public ContractJsonDataBuilder withContractType(Integer contractType) {
            this.contractType = contractType;
            return this;
        }

        public ContractJsonDataBuilder withLaborCategory(String laborCategory) {
            this.laborCategory = laborCategory;
            return this;
        }

        public ContractJsonDataBuilder withWeeklyWorkHours(String weeklyWorkHours) {
            this.weeklyWorkHours = weeklyWorkHours;
            return this;
        }

        public ContractJsonDataBuilder withDaysOfWeekToWork(String jor_trab_dias) {
            this.daysOfWeekToWork = jor_trab_dias;
            return this;
        }

        public ContractJsonDataBuilder withWorkWeekSchedule(Object workWeekSchedule) {
            this.workWeekSchedule = workWeekSchedule;
            return this;
        }

        public ContractJsonDataBuilder withFullPartialWorkDay(String fullPartialWorkDay) {
            this.fullPartialWorkDay = fullPartialWorkDay;
            return this;
        }


        public ContractJsonDataBuilder withIdentificationContractNumberINEM(String identificationContractNumberINEM) {
            this.identificationContractNumberINEM = identificationContractNumberINEM;
            return this;
        }

        public ContractJsonDataBuilder withNotesForContractManager(String notesForContractManager) {
            this.notesForContractManager = notesForContractManager;
            return this;
        }

        public ContractJsonDataBuilder withPrivateNotes(String privateNotes) {
            this.privateNotes = privateNotes;
            return this;
        }

        public ContractJsonData build() {
            return new ContractJsonData(this.clientGMId,  this.workerId, this.quoteAccountCode, this.contractType, this.laborCategory, this.weeklyWorkHours,
                    this.daysOfWeekToWork, this.workWeekSchedule, this.fullPartialWorkDay, this.identificationContractNumberINEM, this.notesForContractManager, this.privateNotes);
        }
    }
}
