/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmoldes.domain.contract.dto;


import gmoldes.domain.contractjsondata.ContractJsonData;

import java.time.LocalDate;


public class ContractInForceAtDateDTO {

    private Integer id;
    private Integer contractNumber;
    private Integer variationType;
    private LocalDate startDate;
    private LocalDate expectedEndDate;
    private LocalDate endingDate;
    private ContractJsonData contractJsonData;

    public ContractInForceAtDateDTO(Integer id,
                                    Integer contractNumber,
                                    Integer variationType,
                                    LocalDate startDate,
                                    LocalDate expectedEndDate,
                                    LocalDate endingDate,
                                    ContractJsonData contractJsonData)
                                    {

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

    public static InitialContracDTOBuilder create() {
        return new InitialContracDTOBuilder();
    }

    public static class InitialContracDTOBuilder {

        private Integer id;
        private Integer contractNumber;
        private Integer variationType;
        private LocalDate startDate;
        private LocalDate expectedEndDate;
        private LocalDate endingDate;
        private ContractJsonData contractJsonData;

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

        public InitialContracDTOBuilder withContractJsonData(ContractJsonData contractJsonData) {
            this.contractJsonData = contractJsonData;
            return this;
        }

        public ContractInForceAtDateDTO build() {
            return new ContractInForceAtDateDTO(this.id, this.contractNumber, this.variationType, this.startDate, this.expectedEndDate, this.endingDate, this.contractJsonData);
        }
    }
}