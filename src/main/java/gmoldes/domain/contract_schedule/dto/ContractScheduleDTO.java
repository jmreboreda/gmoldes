package gmoldes.domain.contract_schedule.dto;

import gmoldes.domain.contractjsondata.ContractDayScheduleJsonData;
import gmoldes.domain.contractjsondata.ContractScheduleJsonData;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class ContractScheduleDTO {

    private Integer id;
    private Integer contractNumber;
    private Integer variationType;
    private LocalDate startDate;
    private LocalDate expectedEndDate;
    private LocalDate modificationDate;
    private LocalDate endingDate;
    private String contractScheduleJsonData;
    private Boolean isInitialContract;
    private Integer variationId;

    public ContractScheduleDTO(Integer id,
                               Integer contractNumber,
                               Integer variationType,
                               LocalDate startDate,
                               LocalDate expectedEndDate,
                               LocalDate modificationDate,
                               LocalDate endingDate,
                               String contractScheduleJsonData,
                               Boolean isInitialContract,
                               Integer variationId) {
        this.id = id;
        this.contractNumber = contractNumber;
        this.variationType = variationType;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
        this.modificationDate = modificationDate;
        this.endingDate = endingDate;
        this.contractScheduleJsonData = contractScheduleJsonData;
        this.isInitialContract = isInitialContract;
        this.variationId = variationId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getContractNumber() {
        return contractNumber;
    }

    public Integer getVariationType() {
        return variationType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getExpectedEndDate() {
        return expectedEndDate;
    }

    public LocalDate getModificationDate() {
        return modificationDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public String getContractScheduleJsonData() {
        return contractScheduleJsonData;
    }

    public Boolean getInitialContract() {
        return isInitialContract;
    }

    public Integer getVariationId() {
        return variationId;
    }

    public static ContractScheduleDTOBuilder create() {
        return new ContractScheduleDTOBuilder();
    }

    public static class ContractScheduleDTOBuilder {

        private Integer id;
        private Integer contractNumber;
        private Integer variationType;
        private LocalDate startDate;
        private LocalDate expectedEndDate;
        private LocalDate modificationDate;
        private LocalDate endingDate;
        private String contractScheduleJsonData;
        private Boolean isInitialContract;
        private Integer variationId;

        public ContractScheduleDTOBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public ContractScheduleDTOBuilder withContractNumber(Integer contractNumber) {
            this.contractNumber = contractNumber;
            return this;
        }

        public ContractScheduleDTOBuilder withVariationType(Integer variationType) {
            this.variationType = variationType;
            return this;
        }

        public ContractScheduleDTOBuilder withStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public ContractScheduleDTOBuilder withExpectedEndDate(LocalDate expectedEndDate) {
            this.expectedEndDate = expectedEndDate;
            return this;
        }

        public ContractScheduleDTOBuilder withModificationDate(LocalDate modificationDate) {
            this.modificationDate = modificationDate;
            return this;
        }

        public ContractScheduleDTOBuilder withEndingDate(LocalDate endingDate) {
            this.endingDate = endingDate;
            return this;
        }


        public ContractScheduleDTOBuilder withContractScheduleJsonData(String contractScheduleJsonData) {
            this.contractScheduleJsonData = contractScheduleJsonData;
            return this;
        }

        public ContractScheduleDTOBuilder withIsInitialContract(Boolean isInitialContract) {
            this.isInitialContract = isInitialContract;
            return this;
        }

        public ContractScheduleDTOBuilder withVariationId(Integer variationId) {
            this.variationId = variationId;
            return this;
        }



        public ContractScheduleDTO build() {
            return new ContractScheduleDTO(this.id, this.contractNumber, this.variationType, this.startDate, this.expectedEndDate, this.modificationDate, this.endingDate, this.contractScheduleJsonData, this.isInitialContract,this.variationId);
        }
    }
}
