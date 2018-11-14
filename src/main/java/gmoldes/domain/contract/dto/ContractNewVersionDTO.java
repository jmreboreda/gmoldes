package gmoldes.domain.contract.dto;

import gmoldes.domain.contractjsondata.ContractJsonData;
import java.sql.Date;
import java.time.LocalDate;

public class ContractNewVersionDTO {

    private Integer id;
    private Integer contractNumber;
    private Integer variationType;
    private LocalDate startDate;
    private LocalDate expectedEndDate;
    private LocalDate endingDate;
    private ContractJsonData contractJsonData;

    public ContractNewVersionDTO() {
    }

    public ContractNewVersionDTO(Integer id,
                                 Integer contractNumber,
                                 Integer variationType,
                                 LocalDate startDate,
                                 LocalDate expectedEndDate,
                                 LocalDate endingDate,
                                 ContractJsonData contractJsonData) {
        this.id = id;
        this.contractNumber = contractNumber;
        this.variationType = variationType;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
        this.endingDate = endingDate;
        this.contractJsonData = contractJsonData;
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

    public ContractJsonData getContractJsonData() {
        return contractJsonData;
    }

    public void setContractJsonData(ContractJsonData contractJsonData) {
        this.contractJsonData = contractJsonData;
    }

    public String toMyString(){

        return "------------------------------------" + "\n" +
                "Contrato: "+ getContractNumber() + " - VariationType: " + getVariationType() + "\n" +
                "Fecha inicio: " + getStartDate();
    }

    public static ContractNewVersionDTOBuilder create() {
        return new ContractNewVersionDTOBuilder();
    }

    public static class ContractNewVersionDTOBuilder {

        private Integer id;
        private Integer contractNumber;
        private Integer variationType;
        private LocalDate startDate;
        private LocalDate expectedEndDate;
        private LocalDate endingDate;
        private ContractJsonData contractJsonData;

        public ContractNewVersionDTOBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public ContractNewVersionDTOBuilder withContractNumber(Integer contractNumber) {
            this.contractNumber = contractNumber;
            return this;
        }

        public ContractNewVersionDTOBuilder withVariationType(Integer variationType) {
            this.variationType = variationType;
            return this;
        }

        public ContractNewVersionDTOBuilder withStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public ContractNewVersionDTOBuilder withExpectedEndDate(LocalDate expectedEndDate) {
            this.expectedEndDate = expectedEndDate;
            return this;
        }

        public ContractNewVersionDTOBuilder withEndingDate(LocalDate endingDate) {
            this.endingDate = endingDate;
            return this;
        }

        public ContractNewVersionDTOBuilder withContractJsonData(ContractJsonData contractJsonData) {
            this.contractJsonData = contractJsonData;
            return this;
        }

        public ContractNewVersionDTO build() {
            return new ContractNewVersionDTO(this.id, this.contractNumber, this.variationType, this.startDate, this.expectedEndDate, this.endingDate, this.contractJsonData);
        }
    }

}