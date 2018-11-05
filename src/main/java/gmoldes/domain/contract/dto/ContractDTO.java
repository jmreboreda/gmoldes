/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmoldes.domain.contract.dto;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;


public class ContractDTO {

    private Integer id;
    private Integer contractNumber;
    private Integer variationNumber;
    private Integer variationType;
    private Integer clientGMId;
    private String clientGMName;
    private String quoteAccountCode;
    private Integer workerId;
    private String workerName;
    private String laborCategory;
    private String weeklyWorkHours;
    private Set<DayOfWeek> daysOfWeekToWork;
    private String fullPartialWorkday;
    private String contractType;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String identificationContractNumberINEM;
    private Boolean contractInForce;
    private String notesForManager;
    private String privateNotes;
    private Character indefiniteOrTemporalContract;
    private Integer surrogateContract;
    private LocalDate quoteDataReportIDC;
    private LocalDate endOfContractNotice;

    public ContractDTO(Integer id,
                       Integer contractNumber,
                       Integer variationNumber,
                       Integer variationType,
                       Integer clientGMId,
                       String clientGMName,
                       String quoteAccountCode,
                       Integer workerId,
                       String workerName,
                       String laborCategory,
                       String weeklyWorkHours,
                       Set<DayOfWeek> daysOfWeekToWork,
                       String fullPartialWorkday,
                       String contractType,
                       LocalDate dateFrom,
                       LocalDate dateTo,
                       String identificationContractNumberINEM,
                       Boolean contractInForce,
                       String notesForManager,
                       String privateNotes,
                       Character indefiniteOrTemporalContract,
                       Integer surrogateContract,
                       LocalDate quoteDataReportIDC,
                       LocalDate endOfContractNotice){

        this.id = id;
        this.contractNumber = contractNumber;
        this.variationNumber = variationNumber;
        this.variationType = variationType;
        this.clientGMId = clientGMId;
        this.clientGMName = clientGMName;
        this.quoteAccountCode = quoteAccountCode;
        this.workerId = workerId;
        this.workerName = workerName;
        this.laborCategory = laborCategory;
        this.weeklyWorkHours = weeklyWorkHours;
        this.daysOfWeekToWork = daysOfWeekToWork;
        this.fullPartialWorkday = fullPartialWorkday;
        this.contractType = contractType;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.identificationContractNumberINEM = identificationContractNumberINEM;
        this.contractInForce = contractInForce;
        this.notesForManager = notesForManager;
        this.privateNotes = privateNotes;
        this.indefiniteOrTemporalContract = indefiniteOrTemporalContract;
        this.surrogateContract = surrogateContract;
        this.quoteDataReportIDC = quoteDataReportIDC;
        this.endOfContractNotice = endOfContractNotice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(Integer contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Integer getVariationNumber() {
        return variationNumber;
    }

    public void setVariationNumber(Integer variationNumber) {
        this.variationNumber = variationNumber;
    }

    public Integer getVariationType() {
        return variationType;
    }

    public void setVariationType(Integer variationType) {
        this.variationType = variationType;
    }

    public Integer getClientGMId() {
        return clientGMId;
    }

    public void setClientGMId(Integer clientGMId) {
        this.clientGMId = clientGMId;
    }

    public String getClientGMName() {
        return clientGMName;
    }

    public void setClientGMName(String clientGMName) {
        this.clientGMName = clientGMName;
    }

    public String getQuoteAccountCode() {
        return quoteAccountCode;
    }

    public void setQuoteAccountCode(String quoteAccountCode) {
        this.quoteAccountCode = quoteAccountCode;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getLaborCategory() {
        return laborCategory;
    }

    public void setLaborCategory(String laboralCategory) {
        this.laborCategory = laboralCategory;
    }

    public String getWeeklyWorkHours() {
        return weeklyWorkHours;
    }

    public void setWeeklyWorkHours(String weeklyWorkHours) {
        this.weeklyWorkHours = weeklyWorkHours;
    }

    public Set<DayOfWeek> getDaysOfWeekToWork() {
        return daysOfWeekToWork;
    }

    public void setDaysOfWeekToWork(Set<DayOfWeek> daysOfWeekToWork) {
        this.daysOfWeekToWork = daysOfWeekToWork;
    }

    public String getFullPartialWorkday() {
        return fullPartialWorkday;
    }

    public void setFullPartialWorkday(String fullPartialWorkday) {
        this.fullPartialWorkday = fullPartialWorkday;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public String getIdentificationContractNumberINEM() {
        return identificationContractNumberINEM;
    }

    public void setIdentificationContractNumberINEM(String identificationContractNumberINEM) {
        this.identificationContractNumberINEM = identificationContractNumberINEM;
    }

    public Boolean getContractInForce() {
        return contractInForce;
    }

    public void setContractInForce(Boolean contractInForce) {
        this.contractInForce = contractInForce;
    }

    public String getNotesForManager() {
        return notesForManager;
    }

    public void setNotesForManager(String notesForManager) {
        this.notesForManager = notesForManager;
    }

    public String getPrivateNotes() {
        return privateNotes;
    }

    public void setPrivateNotes(String privateNotes) {
        this.privateNotes = privateNotes;
    }

    public Character getIndefiniteOrTemporalContract() {
        return indefiniteOrTemporalContract;
    }

    public void setIndefiniteOrTemporalContract(Character indefiniteOrTemporalContract) {
        this.indefiniteOrTemporalContract = indefiniteOrTemporalContract;
    }

    public Integer getSurrogateContract() {
        return surrogateContract;
    }

    public void setSurrogateContract(Integer surrogateContract) {
        this.surrogateContract = surrogateContract;
    }

    public LocalDate getQuoteDataReportIDC() {
        return quoteDataReportIDC;
    }

    public void setQuoteDataReportIDC(LocalDate quoteDataReportIDC) {
        this.quoteDataReportIDC = quoteDataReportIDC;
    }

    public LocalDate getEndOfContractNotice() {
        return endOfContractNotice;
    }

    public void setEndOfContractNotice(LocalDate endOfContractNotice) {
        this.endOfContractNotice = endOfContractNotice;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("clientegm_id = " + getClientGMId() + "\t");
        sb.append("clientegm_name = " + getClientGMName());

        return sb.toString();
    }

    public static ContractDTOBuilder create() {
        return new ContractDTOBuilder();
    }

    public static class ContractDTOBuilder {

        private Integer id;
        private Integer contractNumber;
        private Integer variationNumber;
        private Integer variationType;
        private Integer clientGMId;
        private String clientGMName;
        private String quoteAccountCode;
        private Integer workerId;
        private String workerName;
        private String laborCategory;
        private String weeklyWorkHours;
        private Set<DayOfWeek> daysOfWeekToWork;
        private String fullPartialWorkday;
        private String contractType;
        private LocalDate dateFrom;
        private LocalDate dateTo;
        private String identificationContractNumberINEM;
        private Boolean contractInForce;
        private String notesForManager;
        private String privateNotes;
        private Character indefiniteOrTemporalContract;
        private Integer surrogateContract;
        private LocalDate quoteDataReportIDC;
        private LocalDate endOfContractNotice;

        public ContractDTOBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public ContractDTOBuilder withContractNumber(Integer contractNumber) {
            this.contractNumber = contractNumber;
            return this;
        }

        public ContractDTOBuilder withVariationNumber(Integer variationNumber) {
            this.variationNumber = variationNumber;
            return this;
        }

        public ContractDTOBuilder withVariationType(Integer variationType) {
            this.variationType = variationType;
            return this;
        }

        public ContractDTOBuilder withClientGMId(Integer clientGMId) {
            this.clientGMId = clientGMId;
            return this;
        }

        public ContractDTOBuilder withClientGMName(String clientGMName) {
            this.clientGMName = clientGMName;
            return this;
        }

        public ContractDTOBuilder withQuoteAccountCode(String quoteAccountCode) {
            this.quoteAccountCode = quoteAccountCode;
            return this;
        }

        public ContractDTOBuilder withWorkerId(Integer workerId) {
            this.workerId = workerId;
            return this;
        }

        public ContractDTOBuilder withWorkerName(String workerName) {
            this.workerName = workerName;
            return this;
        }

        public ContractDTOBuilder withLaborCategory(String laborCategory) {
            this.laborCategory = laborCategory;
            return this;
        }

        public ContractDTOBuilder withWeeklyWorkHours(String weeklyWorkHours) {
            this.weeklyWorkHours = weeklyWorkHours;
            return this;
        }

        public ContractDTOBuilder withDaysOfWeekToWork(Set<DayOfWeek> jor_trab_dias) {
            this.daysOfWeekToWork = jor_trab_dias;
            return this;
        }

        public ContractDTOBuilder withFullPartialWorkday(String fullPartialWorkday) {
            this.fullPartialWorkday = fullPartialWorkday;
            return this;
        }

        public ContractDTOBuilder withTContractType(String contractType) {
            this.contractType = contractType;
            return this;
        }

        public ContractDTOBuilder withDateFrom(LocalDate dateFrom) {
            this.dateFrom = dateFrom;
            return this;
        }

        public ContractDTOBuilder withDateTo(LocalDate dateTo) {
            this.dateTo = dateTo;
            return this;
        }

        public ContractDTOBuilder withIdentificationContractNumberINEM(String identificationContractNumberINEM) {
            this.identificationContractNumberINEM = identificationContractNumberINEM;
            return this;
        }

        public ContractDTOBuilder withContractInForce(Boolean contractInForce) {
            this.contractInForce = contractInForce;
            return this;
        }

        public ContractDTOBuilder withNotesForManager(String notesForManager) {
            this.notesForManager = notesForManager;
            return this;
        }

        public ContractDTOBuilder withPrivateNotes(String privateNotes) {
            this.privateNotes = privateNotes;
            return this;
        }

        public ContractDTOBuilder withIndefiniteOrTemporalContract(Character indefiniteOrTemporalContract) {
            this.indefiniteOrTemporalContract = indefiniteOrTemporalContract;
            return this;
        }

        public ContractDTOBuilder withSurrogateContract(Integer surrogateContract) {
            this.surrogateContract = surrogateContract;
            return this;
        }

        public ContractDTOBuilder withQuoteDataReportIDC(LocalDate quoteDataReportIDC) {
            this.quoteDataReportIDC = quoteDataReportIDC;
            return this;
        }

        public ContractDTOBuilder withEndOfContractNotice(LocalDate endOfContractNotice) {
            this.endOfContractNotice = endOfContractNotice;
            return this;
        }

        public ContractDTO build() {
            return new ContractDTO(this.id, this.contractNumber, this.variationNumber, this.variationType, this.clientGMId, this.clientGMName,
            this.quoteAccountCode, this.workerId, this.workerName, this.laborCategory, this.weeklyWorkHours, this.daysOfWeekToWork, this.fullPartialWorkday,
            this.contractType, this.dateFrom, this.dateTo, this.identificationContractNumberINEM, this.contractInForce, this.notesForManager, this.privateNotes,
            this.indefiniteOrTemporalContract, this.surrogateContract, this.quoteDataReportIDC, this.endOfContractNotice);
        }
    }
}