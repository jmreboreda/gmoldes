package gmoldes.domain.contract.dto;

import gmoldes.domain.contractjsondata.ContractJsonData;
import gmoldes.domain.contractjsondata.ContractScheduleJsonData;

import java.time.LocalDate;

public class ContractNewVersionDTO {

    private Integer id;
    private Integer contractNumber;
    private Integer variationType;
    private LocalDate initialContractDate;
    private LocalDate startDate;
    private LocalDate expectedEndDate;
    private LocalDate modificationDate;
    private LocalDate endingDate;
    private ContractJsonData contractJsonData;
    private ContractScheduleJsonData contractScheduleJsonData;

    public ContractNewVersionDTO() {
    }

    public ContractNewVersionDTO(Integer id,
                                 Integer contractNumber,
                                 Integer variationType,
                                 LocalDate initialContractDate,
                                 LocalDate startDate,
                                 LocalDate expectedEndDate,
                                 LocalDate modificationDate,
                                 LocalDate endingDate,
                                 ContractJsonData contractJsonData,
                                 ContractScheduleJsonData contractScheduleJsonData) {
        this.id = id;
        this.contractNumber = contractNumber;
        this.variationType = variationType;
        this.initialContractDate = initialContractDate;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
        this.modificationDate = modificationDate;
        this.endingDate = endingDate;
        this.contractJsonData = contractJsonData;
        this.contractScheduleJsonData = contractScheduleJsonData;
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

    public LocalDate getInitialContractDate() {
        return initialContractDate;
    }

    public void setInitialContractDate(LocalDate initialContractDate) {
        this.initialContractDate = initialContractDate;
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

    public ContractJsonData getContractJsonData() {
        return contractJsonData;
    }

    public void setContractJsonData(ContractJsonData contractJsonData) {
        this.contractJsonData = contractJsonData;
    }

    public ContractScheduleJsonData getContractScheduleJsonData() {
        return contractScheduleJsonData;
    }

    public void setContractScheduleJsonData(ContractScheduleJsonData contractScheduleJsonData) {
        this.contractScheduleJsonData = contractScheduleJsonData;
    }

    //    public String toMyString(){
//
//        return "------------------------------------" + "\n" +
//                "Contrato: "+ getContractNumber() + " - VariationType: " + getVariationType() + "\n" +
//                "Fecha inicio: " + getStartDate();
//    }
//
//    public String toClipboardString(){
//        return getContractNumber() + ";" + getStartDate() + ";" + getExpectedEndDate() + ";" + getModificationDate() + ";" + getEndingDate() + "\n";
//    }

    public static ContractNewVersionDTOBuilder create() {
        return new ContractNewVersionDTOBuilder();
    }

    public static class ContractNewVersionDTOBuilder {

        private Integer id;
        private Integer contractNumber;
        private Integer variationType;
        private LocalDate initialContractDate;
        private LocalDate startDate;
        private LocalDate expectedEndDate;
        private LocalDate modificationDate;
        private LocalDate endingDate;
        private ContractJsonData contractJsonData;
        private ContractScheduleJsonData contractScheduleJsonData;

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

        public ContractNewVersionDTOBuilder withInitialContractDate(LocalDate initialContractDate) {
            this.initialContractDate = initialContractDate;
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

        public ContractNewVersionDTOBuilder withModificationDate(LocalDate modificationDate) {
            this.modificationDate = modificationDate;
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

        public ContractNewVersionDTOBuilder withContractScheduleJsonData(ContractScheduleJsonData contractScheduleJsonData) {
            this.contractScheduleJsonData = contractScheduleJsonData;
            return this;
        }

        public ContractNewVersionDTO build() {
            return new ContractNewVersionDTO(this.id, this.contractNumber, this.variationType, this.initialContractDate, this.startDate, this.expectedEndDate, this.modificationDate, this.endingDate, this.contractJsonData, this.contractScheduleJsonData);
        }
    }

}
