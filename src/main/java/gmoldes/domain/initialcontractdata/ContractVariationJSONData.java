package gmoldes.domain.initialcontractdata;

import java.time.LocalDate;
import java.util.Date;

public class ContractVariationJSONData {

    private Integer clientGMId;
    private Integer workerId;
    private String quoteAccountCode;
    private Date dateFrom;
    private Date dateTo;
    private String laborCategory ;
    private String weeklyWorkHours;
    private String daysOfWeekToWork;
    private String fullPartialWorkday;
    private String identificationContractNumberINEM;
    private String notesForContractManager;
    private String privateNotes;

    public ContractVariationJSONData() {
    }

    public ContractVariationJSONData(
            Integer clientGMId,
            Integer workerId,
            String quoteAccountCode,
            Date dateFrom,
            Date dateTo,
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
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
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

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
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

    public static ContractVariationJSONDataBuilder create() {
        return new ContractVariationJSONDataBuilder();
    }

    public static class ContractVariationJSONDataBuilder {

        private Integer clientGMId;
        private Integer workerId;
        private String quoteAccountCode;
        private Date dateFom;
        private Date dateTo;
        private String laborCategory ;
        private String weeklyWorkHours;
        private String daysOfWeekToWork;
        private String fullPartialWorkday;
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

        public ContractVariationJSONDataBuilder withDateFrom(Date dateFrom) {
            this.dateFom = dateFrom;
            return this;
        }

        public ContractVariationJSONDataBuilder withDateTo(Date dateTo) {
            this.dateTo = dateTo;
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
            return new ContractVariationJSONData(this.clientGMId,  this.workerId, this.quoteAccountCode, this.dateFom, this.dateTo, this.laborCategory,
                    this.weeklyWorkHours, this.daysOfWeekToWork, this.fullPartialWorkday, this.identificationContractNumberINEM, this.notesForContractManager,
                    this.privateNotes);
        }
    }
}
