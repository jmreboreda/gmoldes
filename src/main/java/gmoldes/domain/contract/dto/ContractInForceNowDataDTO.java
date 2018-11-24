package gmoldes.domain.contract.dto;

import gmoldes.domain.person.dto.PersonDTO;

public class ContractInForceNowDataDTO {

    private PersonDTO employee;
    private ContractNewVersionDTO contract;

    public PersonDTO getEmployee() {
        return employee;
    }

    public void setEmployee(PersonDTO employee) {
        this.employee = employee;
    }

    public ContractNewVersionDTO getContract() {
        return contract;
    }

    public void setContract(ContractNewVersionDTO contract) {
        this.contract = contract;
    }

    public String toString() {

        return getEmployee().getApellidos() + ", " + getEmployee().getNom_rzsoc();
    }
}
