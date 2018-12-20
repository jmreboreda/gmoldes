package gmoldes.domain.email;

import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.person.dto.PersonDTO;

import java.nio.file.Path;

public class EmailDataCreationDTO {

    private Path path;
    private String fileName;
    private ClientDTO employer;
    private PersonDTO employee;
    private String variationTypeText;

    public EmailDataCreationDTO(Path path, String fileName, ClientDTO employer, PersonDTO employee, String variationTypeText) {
        this.path = path;
        this.fileName = fileName;
        this.employer = employer;
        this.employee = employee;
        this.variationTypeText = variationTypeText;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public String getVariationTypeText() {
        return variationTypeText;
    }

    public void setVariationTypeText(String variationTypeText) {
        this.variationTypeText = variationTypeText;
    }

    public static EmailDataCreationDTOBuilder create() {
        return new EmailDataCreationDTOBuilder();
    }

    public static class EmailDataCreationDTOBuilder {

        private Path path;
        private String fileName;
        private ClientDTO employer;
        private PersonDTO employee;
        private String variationTypeText;

        public EmailDataCreationDTOBuilder withPath(Path path) {
            this.path = path;
            return this;
        }

        public EmailDataCreationDTOBuilder withFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public EmailDataCreationDTOBuilder withEmployer(ClientDTO employer) {
            this.employer = employer;
            return this;
        }

        public EmailDataCreationDTOBuilder withEmployee(PersonDTO employee) {
            this.employee = employee;
            return this;
        }

        public EmailDataCreationDTOBuilder withVariationTypeText(String variationTypeText) {
            this.variationTypeText = variationTypeText;
            return this;
        }

        public EmailDataCreationDTO build() {
            return new EmailDataCreationDTO(this.path, this.fileName, this.employer, this.employee, this.variationTypeText);
        }

    }
}
