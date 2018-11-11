/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmoldes.domain.contract.dto;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;


public class ContractInForceAtDateDTO {

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
    private Integer contractType;
    private LocalDate startDate;
    private LocalDate expectedEndDate;
    private LocalDate endingDate;
    private String identificationContractNumberINEM;

    public ContractInForceAtDateDTO(Integer id,
                                    Integer contractNumber,
                                    Integer variationType,
                                    Integer clientGMId,
                                    String quoteAccountCode,
                                    Integer workerId,
                                    String laborCategory,
                                    String weeklyWorkHours,
                                    Set<DayOfWeek> daysOfWeekToWork,
                                    String fullPartialWorkday,
                                    Integer contractType,
                                    LocalDate startDate,
                                    LocalDate expectedEndDate,
                                    LocalDate endingDate,
                                    String identificationContractNumberINEM)
                                    {

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
        this.contractType = contractType;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
        this.endingDate = endingDate;
        this.identificationContractNumberINEM = identificationContractNumberINEM;
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

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
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
        sb.append("ContractType: " + getContractType() + "\n");


        return sb.toString();
    }

    public static InitialContracDTOBuilder create() {
        return new InitialContracDTOBuilder();
    }

    public static class InitialContracDTOBuilder {

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
        private Integer contractType;
        private LocalDate startDate;
        private LocalDate expectedEndDate;
        private LocalDate endingDate;
        private String identificationContractNumberINEM;

        public InitialContracDTOBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public InitialContracDTOBuilder withContractNumber(Integer contractNumber) {
            this.contractNumber = contractNumber;
            return this;
        }

        public InitialContracDTOBuilder withVariationType(Integer variationType) {
            this.variationType = variationType;
            return this;
        }

        public InitialContracDTOBuilder withClientGMId(Integer clientGMId) {
            this.clientGMId = clientGMId;
            return this;
        }

        public InitialContracDTOBuilder withQuoteAccountCode(String quoteAccountCode) {
            this.quoteAccountCode = quoteAccountCode;
            return this;
        }

        public InitialContracDTOBuilder withWorkerId(Integer workerId) {
            this.workerId = workerId;
            return this;
        }

        public InitialContracDTOBuilder withLaborCategory(String laborCategory) {
            this.laborCategory = laborCategory;
            return this;
        }

        public InitialContracDTOBuilder withWeeklyWorkHours(String weeklyWorkHours) {
            this.weeklyWorkHours = weeklyWorkHours;
            return this;
        }

        public InitialContracDTOBuilder withDaysOfWeekToWork(Set<DayOfWeek> jor_trab_dias) {
            this.daysOfWeekToWork = jor_trab_dias;
            return this;
        }

        public InitialContracDTOBuilder withFullPartialWorkday(String fullPartialWorkday) {
            this.fullPartialWorkday = fullPartialWorkday;
            return this;
        }

        public InitialContracDTOBuilder withContractType(Integer contractType) {
            this.contractType = contractType;
            return this;
        }

        public InitialContracDTOBuilder withStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public InitialContracDTOBuilder withExpectedEndDate(LocalDate expectedEndDate) {
            this.expectedEndDate = expectedEndDate;
            return this;
        }

        public InitialContracDTOBuilder withEndingDate(LocalDate endingDate) {
            this.endingDate = endingDate;
            return this;
        }

        public InitialContracDTOBuilder withIdentificationContractNumberINEM(String identificationContractNumberINEM) {
            this.identificationContractNumberINEM = identificationContractNumberINEM;
            return this;
        }

        public ContractInForceAtDateDTO build() {
            return new ContractInForceAtDateDTO(this.id, this.contractNumber, this.variationType, this.clientGMId, this.quoteAccountCode, this.workerId, this.laborCategory, this.weeklyWorkHours, this.daysOfWeekToWork, this.fullPartialWorkday,
                    this.contractType, this.startDate, this.expectedEndDate, this.endingDate, this.identificationContractNumberINEM);
        }
    }
}