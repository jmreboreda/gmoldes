package gmoldes.domain.traceability_contract_documentation.dto;


import java.time.LocalDate;

public class TraceabilityContractDocumentationDTO {

    private Integer id;
    private Integer contractNumber;
    private Integer variationType;
    private LocalDate startDate;
    private LocalDate expectedEndDate;
    private LocalDate IDCReceptionDate;
    private LocalDate dateDeliveryContractDocumentationToClient;
    private LocalDate contractEndNoticeReceptionDate;

    public TraceabilityContractDocumentationDTO(
            Integer id,
            Integer contractNumber,
            Integer variationType,
            LocalDate startDate,
            LocalDate expectedEndDate,
            LocalDate IDCReceptionDate,
            LocalDate dateDeliveryContractDocumentationToClient,
            LocalDate contractEndNoticeReceptionDate) {

        this.id = id;
        this.contractNumber = contractNumber;
        this.variationType = variationType;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
        this.IDCReceptionDate = IDCReceptionDate;
        this.dateDeliveryContractDocumentationToClient = dateDeliveryContractDocumentationToClient;
        this.contractEndNoticeReceptionDate = contractEndNoticeReceptionDate;
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

    public LocalDate getIDCReceptionDate() {
        return IDCReceptionDate;
    }

    public void setIDCReceptionDate(LocalDate IDCReceptionDate) {
        this.IDCReceptionDate = IDCReceptionDate;
    }

    public LocalDate getDateDeliveryContractDocumentationToClient() {
        return dateDeliveryContractDocumentationToClient;
    }

    public void setDateDeliveryContractDocumentationToClient(LocalDate dateDeliveryContractDocumentationToClient) {
        this.dateDeliveryContractDocumentationToClient = dateDeliveryContractDocumentationToClient;
    }

    public LocalDate getContractEndNoticeReceptionDate() {
        return contractEndNoticeReceptionDate;
    }

    public void setContractEndNoticeReceptionDate(LocalDate contractEndNoticeReceptionDate) {
        this.contractEndNoticeReceptionDate = contractEndNoticeReceptionDate;
    }
    public static TraceabilityContractDocumentationDTO.TraceabilityBuilder create() {
        return new TraceabilityContractDocumentationDTO.TraceabilityBuilder();
    }

    public static class TraceabilityBuilder {

        private Integer id;
        private Integer contractNumber;
        private Integer variationType;
        private LocalDate startDate;
        private LocalDate expectedEndDate;
        private LocalDate IDCReceptionDate;
        private LocalDate dateDeliveryContractDocumentationToClient;
        private LocalDate contractEndNoticeReceptionDate;

        public TraceabilityContractDocumentationDTO.TraceabilityBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public TraceabilityContractDocumentationDTO.TraceabilityBuilder withContractNumber(Integer contractNumber) {
            this.contractNumber = contractNumber;
            return this;
        }

        public TraceabilityContractDocumentationDTO.TraceabilityBuilder withVariationType(Integer variationType) {
            this.variationType = variationType;
            return this;
        }

        public TraceabilityContractDocumentationDTO.TraceabilityBuilder  withStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public TraceabilityContractDocumentationDTO.TraceabilityBuilder  withExpectedEndDate(LocalDate expectedEndDate) {
            this.expectedEndDate = expectedEndDate;
            return this;
        }

        public TraceabilityContractDocumentationDTO.TraceabilityBuilder withIDCReceptionDate(LocalDate IDCReceptionDate) {
            this.IDCReceptionDate = IDCReceptionDate;
            return this;
        }

        public TraceabilityContractDocumentationDTO.TraceabilityBuilder withDateDeliveryContractDocumentationToClient(LocalDate dateDeliveryContractDocumentationToClient) {
            this.dateDeliveryContractDocumentationToClient = dateDeliveryContractDocumentationToClient;
            return this;
        }

        public TraceabilityContractDocumentationDTO.TraceabilityBuilder withContractEndNoticeReceptionDate(LocalDate contractEndNoticeReceptionDate) {
            this.contractEndNoticeReceptionDate = contractEndNoticeReceptionDate;
            return this;
        }


        public TraceabilityContractDocumentationDTO build() {
            return new TraceabilityContractDocumentationDTO(this.id, this.contractNumber, this.variationType, this.startDate, this.expectedEndDate, this.IDCReceptionDate,
                    this.dateDeliveryContractDocumentationToClient, this.contractEndNoticeReceptionDate);
        }
    }

}
