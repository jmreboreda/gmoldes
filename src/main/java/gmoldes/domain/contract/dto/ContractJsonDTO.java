/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmoldes.domain.contract.dto;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;


public class ContractJsonDTO {

    private Integer id;
    private Integer contractNumber;
    private Integer variationType;
    private Integer clientGMId;
    private String quoteAccountCode;
    private Integer workerId;
    private String laborCategory;
    private String weeklyWorkHours;
    private Set<DayOfWeek> daysOfWeekToWork;
    private String fullPartialWorkday;
    private Integer typeOfContract;
    private LocalDate startDate;
    private LocalDate expectedEndDate;
    private LocalDate endingDate;
    private String identificationContractNumberINEM;
    private String notesForManager;
    private String privateNotes;

    public ContractJsonDTO(Integer id,
                           Integer contractNumber,
                           Integer variationType,
                           Integer clientGMId,
                           String quoteAccountCode,
                           Integer workerId,
                           String laborCategory,
                           String weeklyWorkHours,
                           Set<DayOfWeek> daysOfWeekToWork,
                           String fullPartialWorkday,
                           Integer typeOfContract,
                           LocalDate startDate,
                           LocalDate expectedEndDate,
                           LocalDate endingDate,
                           String identificationContractNumberINEM,
                           String notesForManager,
                           String privateNotes){

        this.id = id;
        this.contractNumber = contractNumber;
        this.variationType = variationType;
        this.clientGMId = clientGMId;
        this.quoteAccountCode = quoteAccountCode;
        this.workerId = workerId;
        this.laborCategory = laborCategory;
        this.weeklyWorkHours = weeklyWorkHours;
        this.daysOfWeekToWork = daysOfWeekToWork;
        this.fullPartialWorkday = fullPartialWorkday;
        this.typeOfContract = typeOfContract;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
        this.endingDate = endingDate;
        this.identificationContractNumberINEM = identificationContractNumberINEM;
        this.notesForManager = notesForManager;
        this.privateNotes = privateNotes;
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

    public Integer getTypeOfContract() {
        return typeOfContract;
    }

    public void setTypeOfContract(Integer typeOfContract) {
        this.typeOfContract = typeOfContract;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(LocalDate expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }

    public String getIdentificationContractNumberINEM() {
        return identificationContractNumberINEM;
    }

    public void setIdentificationContractNumberINEM(String identificationContractNumberINEM) {
        this.identificationContractNumberINEM = identificationContractNumberINEM;
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

    //@Override
    public String toMyString(){
        StringBuilder sb = new StringBuilder();
        sb.append("-----------------------------------------------------" + "\n");
        sb.append("ContractNumber: " + getContractNumber() + " -> INEM contrac number: " + getIdentificationContractNumberINEM() + "\n");
        sb.append("ClientGMId: " + getClientGMId() + " -> WorkerId: " + getWorkerId() + "\n");
        sb.append("DaysOfWeekToWork: " + getDaysOfWeekToWork() + "\n");
        sb.append("StartDate: " + getStartDate() + "\t");
        sb.append("ExpectedEndDate: " + getExpectedEndDate() + "\t");
        sb.append("EndingDate: " + getEndingDate() + "\n");
        sb.append("TypeOfContract: " + getTypeOfContract() + "\n");


        return sb.toString();
    }

    public static ContractJsonDTOBuilder create() {
        return new ContractJsonDTOBuilder();
    }

    public static class ContractJsonDTOBuilder {

        private Integer id;
        private Integer contractNumber;
        private Integer variationType;
        private Integer clientGMId;
        private String quoteAccountCode;
        private Integer workerId;
        private String laborCategory;
        private String weeklyWorkHours;
        private Set<DayOfWeek> daysOfWeekToWork;
        private String fullPartialWorkday;
        private Integer typeOfContract;
        private LocalDate startDate;
        private LocalDate expectedEndDate;
        private LocalDate endingDate;
        private String identificationContractNumberINEM;
        private String notesForManager;
        private String privateNotes;

        public ContractJsonDTOBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public ContractJsonDTOBuilder withContractNumber(Integer contractNumber) {
            this.contractNumber = contractNumber;
            return this;
        }

        public ContractJsonDTOBuilder withVariationType(Integer variationType) {
            this.variationType = variationType;
            return this;
        }

        public ContractJsonDTOBuilder withClientGMId(Integer clientGMId) {
            this.clientGMId = clientGMId;
            return this;
        }

        public ContractJsonDTOBuilder withQuoteAccountCode(String quoteAccountCode) {
            this.quoteAccountCode = quoteAccountCode;
            return this;
        }

        public ContractJsonDTOBuilder withWorkerId(Integer workerId) {
            this.workerId = workerId;
            return this;
        }

        public ContractJsonDTOBuilder withLaborCategory(String laborCategory) {
            this.laborCategory = laborCategory;
            return this;
        }

        public ContractJsonDTOBuilder withWeeklyWorkHours(String weeklyWorkHours) {
            this.weeklyWorkHours = weeklyWorkHours;
            return this;
        }

        public ContractJsonDTOBuilder withDaysOfWeekToWork(Set<DayOfWeek> jor_trab_dias) {
            this.daysOfWeekToWork = jor_trab_dias;
            return this;
        }

        public ContractJsonDTOBuilder withFullPartialWorkday(String fullPartialWorkday) {
            this.fullPartialWorkday = fullPartialWorkday;
            return this;
        }

        public ContractJsonDTOBuilder withTypeOfContract(Integer typeOfContract) {
            this.typeOfContract = typeOfContract;
            return this;
        }

        public ContractJsonDTOBuilder withStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public ContractJsonDTOBuilder withExpectedEndDate(LocalDate expectedEndDate) {
            this.expectedEndDate = expectedEndDate;
            return this;
        }

        public ContractJsonDTOBuilder withEndingDate(LocalDate endingDate) {
            this.endingDate = endingDate;
            return this;
        }

        public ContractJsonDTOBuilder withIdentificationContractNumberINEM(String identificationContractNumberINEM) {
            this.identificationContractNumberINEM = identificationContractNumberINEM;
            return this;
        }

        public ContractJsonDTOBuilder withNotesForManager(String notesForManager) {
            this.notesForManager = notesForManager;
            return this;
        }

        public ContractJsonDTOBuilder withPrivateNotes(String privateNotes) {
            this.privateNotes = privateNotes;
            return this;
        }

        public ContractJsonDTO build() {
            return new ContractJsonDTO(this.id, this.contractNumber, this.variationType, this.clientGMId, this.quoteAccountCode, this.workerId, this.laborCategory, this.weeklyWorkHours, this.daysOfWeekToWork, this.fullPartialWorkday,
                    this.typeOfContract, this.startDate, this.expectedEndDate, this.endingDate, this.identificationContractNumberINEM, this.notesForManager, this.privateNotes);
        }
    }
}