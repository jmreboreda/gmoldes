package gmoldes.domain.contract_documentation_control;

import java.time.LocalDate;

public class ContractDocumentationControlDataDTO {

    private String documentType;
    private LocalDate receptionDate;
    private LocalDate deliveryDate;

    public ContractDocumentationControlDataDTO(String documentType, LocalDate receptionDate, LocalDate deliveryDate) {
        this.documentType = documentType;
        this.receptionDate = receptionDate;
        this.deliveryDate = deliveryDate;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public LocalDate getReceptionDate() {
        return receptionDate;
    }

    public void setReceptionDate(LocalDate receptionDate) {
        this.receptionDate = receptionDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
