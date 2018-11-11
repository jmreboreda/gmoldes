package gmoldes.domain.contract.dto;

import gmoldes.domain.contractjsondata.ContractNewVersionJSONData;
import java.sql.Date;

public class ContractNewVersionDTO {

    private Integer id;
    private Integer contractNumber;
    private Integer variationType;
    private Date startDate;
    private Date expectedEndDate;
    private Date endingDate;
    private ContractNewVersionJSONData contractNewVersionJSONData;

    public ContractNewVersionDTO(Integer id, Integer contractNumber, Integer variationType, Date startDate, Date expectedEndDate, Date endingDate, ContractNewVersionJSONData contractNewVersionJSONData) {
        this.id = id;
        this.contractNumber = contractNumber;
        this.variationType = variationType;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
        this.endingDate = endingDate;
        this.contractNewVersionJSONData = contractNewVersionJSONData;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(Date expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public ContractNewVersionJSONData getContractNewVersionJSONData() {
        return contractNewVersionJSONData;
    }

    public void setContractNewVersionJSONData(ContractNewVersionJSONData contractNewVersionJSONData) {
        this.contractNewVersionJSONData = contractNewVersionJSONData;
    }

    public String toMyString(){

        return "------------------------" + "\n" +
                "Contrato: "+ getContractNumber()+ "\n" +
                "Fecha inicio: " + getStartDate();
    }

    public static ContractNewVersionDTOBuilder create() {
        return new ContractNewVersionDTOBuilder();
    }

    public static class ContractNewVersionDTOBuilder {

        private Integer id;
        private Integer contractNumber;
        private Integer variationType;
        private Date startDate;
        private Date expectedEndDate;
        private Date endingDate;
        private ContractNewVersionJSONData contractNewVersionJSONData;

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

        public ContractNewVersionDTOBuilder withStartDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public ContractNewVersionDTOBuilder withExpectedEndDate(Date expectedEndDate) {
            this.expectedEndDate = expectedEndDate;
            return this;
        }

        public ContractNewVersionDTOBuilder withEndingDate(Date endingDate) {
            this.endingDate = endingDate;
            return this;
        }

        public ContractNewVersionDTOBuilder withContractNewVersionJSONData(ContractNewVersionJSONData contractNewVersionJSONData) {
            this.contractNewVersionJSONData = contractNewVersionJSONData;
            return this;
        }

        public ContractNewVersionDTO build() {
            return new ContractNewVersionDTO(this.id, this.contractNumber, this.variationType, this.startDate, this.expectedEndDate, this.endingDate, this.contractNewVersionJSONData);
        }
    }

}
