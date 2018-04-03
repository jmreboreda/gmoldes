/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmoldes.domain.contract.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

public class OldContractToSaveDTO {

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
    private String typeOfContract;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String identificationContractNumberINEM;
    private Boolean currentContract;
    private String notesForManager;
    private String privateNotes;
    private Character indefiniteOrTemporalContract;
    private Integer surrogateContract;
    private LocalDate quoteDataReportIDC;
    private LocalDate endOfContractNotice;


    public OldContractToSaveDTO(Integer contractNumber,
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
                                String typeOfContract,
                                LocalDate dateFrom,
                                LocalDate dateTo,
                                String identificationContractNumberINEM,
                                Boolean currentContract,
                                String notesForManager,
                                String privateNotes,
                                Character indefiniteOrTemporalContract,
                                Integer surrogateContract,
                                LocalDate quoteDataReportIDC,
                                LocalDate endOfContractNotice){

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
        this.typeOfContract = typeOfContract;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.identificationContractNumberINEM = identificationContractNumberINEM;
        this.currentContract = currentContract;
        this.notesForManager = notesForManager;
        this.privateNotes = privateNotes;
        this.indefiniteOrTemporalContract = indefiniteOrTemporalContract;
        this.surrogateContract = surrogateContract;
        this.quoteDataReportIDC = quoteDataReportIDC;
        this.endOfContractNotice = endOfContractNotice;
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

    public String getTypeOfContract() {
        return typeOfContract;
    }

    public void setTypeOfContract(String typeOfContract) {
        this.typeOfContract = typeOfContract;
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

    public Boolean getCurrentContract() {
        return currentContract;
    }

    public void setCurrentContract(Boolean currentContract) {
        this.currentContract = currentContract;
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
        return  contractNumber+ " :: " + variationNumber + " :: " + variationType + " :: " + clientGMId + " :: " + clientGMName + " :: " +
                quoteAccountCode + " :: " + workerId + " :: " + workerName + " :: " + laborCategory + " :: " +
                weeklyWorkHours + " :: " + daysOfWeekToWork + " :: " + fullPartialWorkday + " :: " + typeOfContract + " :: " +
                dateFrom + " :: " + dateTo + " :: " + identificationContractNumberINEM + " :: " + currentContract + " :: " +
                notesForManager + " :: " + privateNotes + " :: " + quoteDataReportIDC + " :: " + endOfContractNotice;
    }

    public static ContractBuilder create() {
        return new ContractBuilder();
    }

    public static class ContractBuilder {

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
        private String typeOfContract;
        private LocalDate dateFrom;
        private LocalDate dateTo;
        private String identificationContractNumberINEM;
        private Boolean currentContract;
        private String notesForManager;
        private String privateNotes;
        private Character indefiniteOrTemporalContract;
        private Integer surrogateContract;
        private LocalDate quoteDataReportIDC;
        private LocalDate endOfContractNotice;

        public ContractBuilder withContractNumber(Integer contractNumber) {
            this.contractNumber = contractNumber;
            return this;
        }

        public ContractBuilder withVariationNumber(Integer variationNumber) {
            this.variationNumber = variationNumber;
            return this;
        }

        public ContractBuilder withVariationType(Integer variationType) {
            this.variationType = variationType;
            return this;
        }

        public ContractBuilder withClientGMId(Integer clientGMId) {
            this.clientGMId = clientGMId;
            return this;
        }

        public ContractBuilder withClientGMName(String clientGMName) {
            this.clientGMName = clientGMName;
            return this;
        }

        public ContractBuilder withQuoteAccountCode(String quoteAccountCode) {
            this.quoteAccountCode = quoteAccountCode;
            return this;
        }

        public ContractBuilder withWorkerId(Integer workerId) {
            this.workerId = workerId;
            return this;
        }

        public ContractBuilder withWorkerName(String workerName) {
            this.workerName = workerName;
            return this;
        }

        public ContractBuilder withLaborCategory(String laborCategory) {
            this.laborCategory = laborCategory;
            return this;
        }

        public ContractBuilder withWeeklyWorkHours(String weeklyWorkHours) {
            this.weeklyWorkHours = weeklyWorkHours;
            return this;
        }

        public ContractBuilder withDaysOfWeekToWork(Set<DayOfWeek> jor_trab_dias) {
            this.daysOfWeekToWork = jor_trab_dias;
            return this;
        }

        public ContractBuilder withFullPartialWorkday(String fullPartialWorkday) {
            this.fullPartialWorkday = fullPartialWorkday;
            return this;
        }

        public ContractBuilder withTypeOfContract(String typeOfContract) {
            this.typeOfContract = typeOfContract;
            return this;
        }

        public ContractBuilder withDateFrom(LocalDate dateFrom) {
            this.dateFrom = dateFrom;
            return this;
        }

        public ContractBuilder withDateTo(LocalDate dateTo) {
            this.dateTo = dateTo;
            return this;
        }

        public ContractBuilder withIdentificationContractNumberINEM(String identificationContractNumberINEM) {
            this.identificationContractNumberINEM = identificationContractNumberINEM;
            return this;
        }

        public ContractBuilder withCurrentContract(Boolean currentContract) {
            this.currentContract = currentContract;
            return this;
        }

        public ContractBuilder withNotesForManager(String notesForManager) {
            this.notesForManager = notesForManager;
            return this;
        }

        public ContractBuilder withPrivateNotes(String privateNotes) {
            this.privateNotes = privateNotes;
            return this;
        }

        public ContractBuilder withIndefiniteOrTemporalContract(Character indefiniteOrTemporalContract) {
            this.indefiniteOrTemporalContract = indefiniteOrTemporalContract;
            return this;
        }

        public ContractBuilder withSurrogateContract(Integer surrogateContract) {
            this.surrogateContract = surrogateContract;
            return this;
        }

        public ContractBuilder withQuoteDataReportIDC(LocalDate date) {
            this.quoteDataReportIDC = date;
            return this;
        }

        public ContractBuilder withEndOfContractNotice(LocalDate date) {
            this.endOfContractNotice = date;
            return this;
        }

        public OldContractToSaveDTO build() {
            return new OldContractToSaveDTO(this.contractNumber, this.variationNumber, this.variationType, this.clientGMId, this.clientGMName,
            this.quoteAccountCode, this.workerId, this.workerName, this.laborCategory, this.weeklyWorkHours, this.daysOfWeekToWork, this.fullPartialWorkday,
            this.typeOfContract, this.dateFrom, this.dateTo, this.identificationContractNumberINEM, this.currentContract, this.notesForManager, this.privateNotes,
            this.indefiniteOrTemporalContract, this.surrogateContract, this.quoteDataReportIDC,this.endOfContractNotice);
        }
    }
}