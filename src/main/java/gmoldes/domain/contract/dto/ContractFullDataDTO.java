package gmoldes.domain.contract.dto;

import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.person.dto.PersonDTO;

import java.time.LocalDate;

public class ContractFullDataDTO {

    private ClientDTO employer;
    private PersonDTO employee;
    private LocalDate initialContractDate;
    private ContractNewVersionDTO contractNewVersion;
    private ContractTypeDTO contractType;

    public ContractFullDataDTO(ClientDTO employer,
                               PersonDTO employee,
                               LocalDate initialContractDate,
                               ContractNewVersionDTO contractNewVersion,
                               ContractTypeDTO contractType) {

        this.employer = employer;
        this.employee = employee;
        this.initialContractDate = initialContractDate;
        this.contractNewVersion = contractNewVersion;
        this.contractType = contractType;
    }

    public ClientDTO getEmployer() {
        return employer;
    }

    public void setEmployer(ClientDTO employer) {
        this.employer = employer;
    }

    public PersonDTO getEmployee() {
        return employee;
    }

    public void setEmployee(PersonDTO employee) {
        this.employee = employee;
    }

    public LocalDate getInitialContractDate() {
        return initialContractDate;
    }

    public void setInitialContractDate(LocalDate initialContractDate) {
        this.initialContractDate = initialContractDate;
    }

    public ContractNewVersionDTO getContractNewVersion() {
        return contractNewVersion;
    }

    public void setContractNewVersion(ContractNewVersionDTO contractNewVersion) {
        this.contractNewVersion = contractNewVersion;
    }

    public ContractTypeDTO getContractType() {
        return contractType;
    }

    public void setContractType(ContractTypeDTO contractType) {
        this.contractType = contractType;
    }

    public String toString(){
//        DecimalFormat formatter = new DecimalFormat("0000");
//        "[Contrato GM: " + formatter.format(getContractNewVersion().getContractNumber()) + "] " +

        return employee.getApellidos() + ", " + employee.getNom_rzsoc();
    }


    public static ContractFullDataDTOBuilder create() {
        return new ContractFullDataDTOBuilder();
    }

    public static class ContractFullDataDTOBuilder {

        private ClientDTO employer;
        private PersonDTO employee;
        private LocalDate initialContractDate;
        private ContractNewVersionDTO contractNewVersion;
        private ContractTypeDTO contractType;

        public ContractFullDataDTOBuilder withEmployer(ClientDTO employer) {
            this.employer = employer;
            return this;
        }

        public ContractFullDataDTOBuilder withEmployee(PersonDTO employee) {
            this.employee = employee;
            return this;
        }

        public ContractFullDataDTOBuilder withInitialContractDate(LocalDate initialContractDate) {
            this.initialContractDate = initialContractDate;
            return this;
        }

        public ContractFullDataDTOBuilder withContractNewVersionDTO(ContractNewVersionDTO contractNewVersionDTO) {
            this.contractNewVersion = contractNewVersionDTO;
            return this;
        }

        public ContractFullDataDTOBuilder withContractType(ContractTypeDTO contractType) {
            this.contractType = contractType;
            return this;
        }

        public ContractFullDataDTO build() {
            return new ContractFullDataDTO(this.employer, this.employee, this.initialContractDate, this.contractNewVersion, this.contractType);
        }
    }
}
