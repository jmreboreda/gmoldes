package gmoldes.domain.contract.dto;

import gmoldes.domain.contractjsondata.ContractScheduleJsonData;

import java.time.LocalDate;

public class ContractDTO {

    private Integer id;
    private Integer employer;
    private Integer employee;
    private String contractType;
    private Integer gmContractNumber;
    private Integer variationType;
    private LocalDate startDate;
    private LocalDate expectedEndDate;
    private LocalDate modificationDate;
    private LocalDate endingDate;
    private ContractScheduleJsonData contractScheduleJsonData;
    private String laborCategory;
    private String quoteAccountCode;
    private String identificationContractNumberINEM;
    private String publicNotes;
    private String privateNotes;

    public ContractDTO() {
    }

    public ContractDTO(Integer id,
                       Integer employer,
                       Integer employee,
                       String contractType,
                       Integer gmContractNumber,
                       Integer variationType,
                       LocalDate startDate,
                       LocalDate expectedEndDate,
                       LocalDate modificationDate,
                       LocalDate endingDate,
                       ContractScheduleJsonData contractScheduleJsonData,
                       String laborCategory,
                       String quoteAccountCode,
                       String identificationContractNumberINEM,
                       String publicNotes,
                       String privateNotes) {

        this.id = id;
        this.employer = employer;
        this.employee = employee;
        this.contractType = contractType;
        this.gmContractNumber = gmContractNumber;
        this.variationType = variationType;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
        this.modificationDate = modificationDate;
        this.endingDate = endingDate;
        this.contractScheduleJsonData = contractScheduleJsonData;
        this.laborCategory = laborCategory;
        this.quoteAccountCode = quoteAccountCode;
        this.identificationContractNumberINEM = identificationContractNumberINEM;
        this.publicNotes = publicNotes;
        this.privateNotes = privateNotes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployer() {
        return employer;
    }

    public void setEmployer(Integer employer) {
        this.employer = employer;
    }

    public Integer getEmployee() {
        return employee;
    }

    public void setEmployee(Integer employee) {
        this.employee = employee;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public Integer getGmContractNumber() {
        return gmContractNumber;
    }

    public void setGmContractNumber(Integer gmContractNumber) {
        this.gmContractNumber = gmContractNumber;
    }

    public Integer getVariationType() {
        return variationType;
    }

    public void setVariationType(Integer variationType) {
        this.variationType = variationType;
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

    public LocalDate getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }

    public ContractScheduleJsonData getContractScheduleJsonData() {
        return contractScheduleJsonData;
    }

    public void setContractScheduleJsonData(ContractScheduleJsonData contractScheduleJsonData) {
        this.contractScheduleJsonData = contractScheduleJsonData;
    }

    public String getLaborCategory() {
        return laborCategory;
    }

    public void setLaborCategory(String laborCategory) {
        this.laborCategory = laborCategory;
    }

    public String getQuoteAccountCode() {
        return quoteAccountCode;
    }

    public void setQuoteAccountCode(String quoteAccountCode) {
        this.quoteAccountCode = quoteAccountCode;
    }

    public String getIdentificationContractNumberINEM() {
        return identificationContractNumberINEM;
    }

    public void setIdentificationContractNumberINEM(String identificationContractNumberINEM) {
        this.identificationContractNumberINEM = identificationContractNumberINEM;
    }

    public String getPublicNotes() {
        return publicNotes;
    }

    public void setPublicNotes(String publicNotes) {
        this.publicNotes = publicNotes;
    }

    public String getPrivateNotes() {
        return privateNotes;
    }

    public void setPrivateNotes(String privateNotes) {
        this.privateNotes = privateNotes;
    }

    public static ContractDTOBuilder create() {
        return new ContractDTOBuilder();
    }

    public static class ContractDTOBuilder {

        private Integer id;
        private Integer employer;
        private Integer employee;
        private String contractType;
        private Integer gmContractNumber;
        private Integer variationType;
        private LocalDate startDate;
        private LocalDate expectedEndDate;
        private LocalDate modificationDate;
        private LocalDate endingDate;
        private ContractScheduleJsonData weeklyWorkSchedule;
        private String laborCategory;
        private String quoteAccountCode;
        private String identificationContractNumberINEM;
        private String publicNotes;
        private String privateNotes;

        public ContractDTOBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public ContractDTOBuilder withEmployer(Integer employer) {
            this.employer = employer;
            return this;
        }

        public ContractDTOBuilder withEmployee(Integer employee) {
            this.employee = employee;
            return this;
        }

        public ContractDTOBuilder withContractType(String contractType) {
            this.contractType = contractType;
            return this;
        }


        public ContractDTOBuilder withGmContractNumber(Integer gmContractNumber) {
            this.gmContractNumber = gmContractNumber;
            return this;
        }

        public ContractDTOBuilder withVariationType(Integer variationType) {
            this.variationType = variationType;
            return this;
        }

        public ContractDTOBuilder withStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public ContractDTOBuilder withExpectedEndDate(LocalDate expectedEndDate) {
            this.expectedEndDate = expectedEndDate;
            return this;
        }

        public ContractDTOBuilder withModificationDate(LocalDate modificationDate) {
            this.modificationDate = modificationDate;
            return this;
        }

        public ContractDTOBuilder withEndingDate(LocalDate endingDate) {
            this.endingDate = endingDate;
            return this;
        }

        public ContractDTOBuilder withContractScheduleJsonData(ContractScheduleJsonData weeklyWorkSchedule) {
            this.weeklyWorkSchedule = weeklyWorkSchedule;
            return this;
        }

        public ContractDTOBuilder withLaborCategory(String laborCategory) {
            this.laborCategory = laborCategory;
            return this;
        }

        public ContractDTOBuilder withQuoteAccountCode(String quoteAccountCode) {
            this.quoteAccountCode = quoteAccountCode;
            return this;
        }

        public ContractDTOBuilder withIdentificationContractNumberINEM(String identificationContractNumberINEM) {
            this.identificationContractNumberINEM = identificationContractNumberINEM;
            return this;
        }

        public ContractDTOBuilder withPublicNotes(String publicNotes) {
            this.publicNotes = publicNotes;
            return this;
        }

        public ContractDTOBuilder withPrivateNotes(String privateNotes) {
            this.privateNotes = privateNotes;
            return this;
        }

        public ContractDTO build() {
            return new ContractDTO(this.id, this.employer, this.employee, this.contractType, this.gmContractNumber, this.variationType, this.startDate, this.expectedEndDate, this.modificationDate,
            this.endingDate, this.weeklyWorkSchedule, this.laborCategory, this.quoteAccountCode, this.identificationContractNumberINEM, this.publicNotes, this.privateNotes);
        }
    }
}
