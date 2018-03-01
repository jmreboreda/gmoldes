/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmoldes.domain.dto;

import java.util.Date;


public class ClientDTO {

    private Integer id;
    private Integer clientId;
    private String taxIdentificationNumber;
    private Short taxIdentificationNumber_dup;
    private String personOrCompanyName;
    private Short numberOfTimes;
    private Character codeInSigaProgram;
    private Date dateFrom;
    private Date dateTo;
    private Boolean activeClient;
    private Date withoutActivity;
    private String clientType;

    public ClientDTO() {
    }

    public ClientDTO(Integer id, Integer clientId, String taxIdentificationNumber, Short taxIdentificationNumber_dup, String personOrCompanyName,
                     Short numberOfTimes, Character codeInSigaProgram, Date dateFrom, Date dateTo, Boolean activeClient,
                     Date withoutActivity, String clientType) {
        this.id = id;
        this.clientId = clientId;
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.taxIdentificationNumber_dup = taxIdentificationNumber_dup;
        this.personOrCompanyName = personOrCompanyName;
        this.numberOfTimes = numberOfTimes;
        this.codeInSigaProgram = codeInSigaProgram;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.activeClient = activeClient;
        this.withoutActivity = withoutActivity;
        this.clientType = clientType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public Short getTaxIdentificationNumber_dup() {
        return taxIdentificationNumber_dup;
    }

    public void setTaxIdentificationNumber_dup(Short taxIdentificationNumber_dup) {
        this.taxIdentificationNumber_dup = taxIdentificationNumber_dup;
    }

    public String getPersonOrCompanyName() {
        return personOrCompanyName;
    }

    public void setPersonOrCompanyName(String personOrCompanyName) {
        this.personOrCompanyName = personOrCompanyName;
    }

    public Short getNumberOfTimes() {
        return numberOfTimes;
    }

    public void setNumberOfTimes(Short numberOfTimes) {
        this.numberOfTimes = numberOfTimes;
    }

    public Character getCodeInSigaProgram() {
        return codeInSigaProgram;
    }

    public void setCodeInSigaProgram(Character codeInSigaProgram) {
        this.codeInSigaProgram = codeInSigaProgram;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Boolean getActiveClient() {
        return activeClient;
    }

    public void setActiveClient(Boolean activeClient) {
        this.activeClient = activeClient;
    }

    public Date getWithoutActivity() {
        return withoutActivity;
    }

    public void setWithoutActivity(Date withoutActivity) {
        this.withoutActivity = withoutActivity;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    @Override
    public String toString() {
        return getPersonOrCompanyName();
    }

    public static ClientDTO.PersonBuilder create() {
        return new ClientDTO.PersonBuilder();
    }

    public static class PersonBuilder {

        private Integer id;
        private Integer clientId;
        private String taxIdentificationNumber;
        private Short taxIdentificationNumber_dup;
        private String personOrCompanyName;
        private Short numberOfTimes;
        private Character codeInSigaProgram;
        private Date dateFrom;
        private Date dateTo;
        private Boolean activeClient;
        private Date withoutActivity;
        private String clientType;

        public ClientDTO.PersonBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public ClientDTO.PersonBuilder withClientId(Integer clientId) {
            this.clientId = clientId;
            return this;
        }

        public ClientDTO.PersonBuilder withTaxIdentificationNumber(String taxIdentificationNumber) {
            this.taxIdentificationNumber = taxIdentificationNumber;
            return this;
        }

        public ClientDTO.PersonBuilder withTaxIdentificationNumber_dup(Short withTaxIdentificationNumber_dup) {
            this.taxIdentificationNumber_dup = withTaxIdentificationNumber_dup;
            return this;
        }

        public ClientDTO.PersonBuilder withPersonOrCompanyName(String personOrCompanyName) {
            this.personOrCompanyName = personOrCompanyName;
            return this;
        }

        public ClientDTO.PersonBuilder withNumberOfTimes(Short numberOfTimes) {
            this.numberOfTimes = numberOfTimes;
            return this;
        }

        public ClientDTO.PersonBuilder withCodeInSigaProgram(Character codeInSigaProgram) {
            this.codeInSigaProgram = codeInSigaProgram;
            return this;
        }

        public ClientDTO.PersonBuilder withDateFrom(Date dateFrom) {
            this.dateFrom = dateFrom;
            return this;
        }

        public ClientDTO.PersonBuilder withDateTo(Date dateTo) {
            this.dateTo = dateTo;
            return this;
        }

        public ClientDTO.PersonBuilder withCltactivo(Boolean cltactivo) {
            this.activeClient = cltactivo;
            return this;
        }

        public ClientDTO.PersonBuilder withWithOutActivity(Date withoutActivity) {
            this.withoutActivity = withoutActivity;
            return this;
        }

        public ClientDTO.PersonBuilder withClientType(String clientType) {
            this.clientType = clientType;
            return this;
        }

        public ClientDTO build() {
            return new ClientDTO(this.id, this.clientId, this.taxIdentificationNumber, this.taxIdentificationNumber_dup, this.personOrCompanyName,
            this.numberOfTimes, this.codeInSigaProgram, this.dateFrom, this.dateTo, this.activeClient, this.withoutActivity, this.clientType);
        }
    }
}