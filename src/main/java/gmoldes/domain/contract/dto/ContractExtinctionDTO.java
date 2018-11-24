package gmoldes.domain.contract.dto;

import java.time.LocalDate;

public class ContractExtinctionDTO {

    private Integer id;
    private Integer contractNumber;
    private LocalDate endingDate;
    private Integer variationTypeContractTermination;

    public ContractExtinctionDTO() {
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

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }

    public Integer getVariationTypeContractTermination() {
        return variationTypeContractTermination;
    }

    public void setVariationTypeContractTermination(Integer variationTypeContractTermination) {
        this.variationTypeContractTermination = variationTypeContractTermination;
    }
}
