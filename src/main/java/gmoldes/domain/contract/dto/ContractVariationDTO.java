/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmoldes.domain.contract.dto;


import gmoldes.domain.contractjsondata.ContractJsonData;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;


public class ContractVariationDTO {

    private Integer id;
    private Integer contractNumber;
    private Integer variationType;
    private Integer clientGMId;
    private LocalDate startDate;
    private LocalDate expectedEndDate;
    private LocalDate modificationDate;
    private LocalDate endingDate;
    private ContractJsonData contractJsonData;

    public ContractVariationDTO() {
    }

    public ContractVariationDTO(Integer id,
                                Integer contractNumber,
                                Integer variationType,
                                LocalDate startDate,
                                LocalDate expectedEndDate,
                                LocalDate modificationDate,
                                LocalDate endingDate,
                                ContractJsonData contractJsonData){

        this.id = id;
        this.contractNumber = contractNumber;
        this.variationType = variationType;
        this.clientGMId = clientGMId;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
        this.modificationDate = modificationDate;
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

    public Integer getClientGMId() {
        return clientGMId;
    }

    public void setClientGMId(Integer clientGMId) {
        this.clientGMId = clientGMId;
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

    //@Override
    public String toMyString(){
        StringBuilder sb = new StringBuilder();
        sb.append("-----------------------------------------------------" + "\n");
        sb.append("ContractNumber: " + getContractNumber() + " -> INEM contrac number: " + getContractJsonData().getIdentificationContractNumberINEM() + "\n");
        sb.append("ClientGMId: " + getClientGMId() + " -> WorkerId: " + getContractJsonData().getWorkerId() + "\n");
        sb.append("DaysOfWeekToWork: " + getContractJsonData().getDaysOfWeekToWork() + "\n");
        sb.append("StartDate: " + getStartDate() + "\t");
        sb.append("ExpectedEndDate: " + getExpectedEndDate() + "\t");
        sb.append("EndingDate: " + getEndingDate() + "\n");
        sb.append("ContractType: " + getContractJsonData().getContractType() + "\n");


        return sb.toString();
    }

    public static ContractVariationDTOBuilder create() {
        return new ContractVariationDTOBuilder();
    }

    public static class ContractVariationDTOBuilder {

        private Integer id;
        private Integer contractNumber;
        private Integer variationType;
        private LocalDate startDate;
        private LocalDate expectedEndDate;
        private LocalDate modificationDate;
        private LocalDate endingDate;
        private ContractJsonData contractJsonData;

        public ContractVariationDTOBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public ContractVariationDTOBuilder withContractNumber(Integer contractNumber) {
            this.contractNumber = contractNumber;
            return this;
        }

        public ContractVariationDTOBuilder withVariationType(Integer variationType) {
            this.variationType = variationType;
            return this;
        }

        public ContractVariationDTOBuilder withStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public ContractVariationDTOBuilder withExpectedEndDate(LocalDate expectedEndDate) {
            this.expectedEndDate = expectedEndDate;
            return this;
        }

        public ContractVariationDTOBuilder withModificationDate(LocalDate modificationDate) {
            this.modificationDate = modificationDate;
            return this;
        }

        public ContractVariationDTOBuilder withEndingDate(LocalDate endingDate) {
            this.endingDate = endingDate;
            return this;
        }


        public ContractVariationDTOBuilder withContractJsonData(ContractJsonData contractJsonData) {
            this.contractJsonData = contractJsonData;
            return this;
        }

        public ContractVariationDTO build() {
            return new ContractVariationDTO(this.id, this.contractNumber, this.variationType, this.startDate, this.expectedEndDate, this.modificationDate, this.endingDate, this.contractJsonData);
        }
    }
}