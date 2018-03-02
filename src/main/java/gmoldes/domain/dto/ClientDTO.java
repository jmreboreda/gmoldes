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
    private String nieNIF;
    private Short nieNIF_dup;
    private String personOrCompanyName;
    private Short numberOfTimes;
    private Character codeInSigaProgram;
    private Date dateFrom;
    private Date dateTo;
    private Boolean isActive;
    private Date withoutActivity;
    private String clientType;

    public ClientDTO() {
    }

    public ClientDTO(Integer id, Integer clientId, String nieNIF, Short nieNIF_dup, String personOrCompanyName,
                     Short numberOfTimes, Character codeInSigaProgram, Date dateFrom, Date dateTo, Boolean isActive,
                     Date withoutActivity, String clientType) {
        this.id = id;
        this.clientId = clientId;
        this.nieNIF = nieNIF;
        this.nieNIF_dup = nieNIF_dup;
        this.personOrCompanyName = personOrCompanyName;
        this.numberOfTimes = numberOfTimes;
        this.codeInSigaProgram = codeInSigaProgram;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.isActive = isActive;
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

    public String getNieNIF() {
        return nieNIF;
    }

    public void setNieNIF(String nieNIF) {
        this.nieNIF = nieNIF;
    }

    public Short getNieNIF_dup() {
        return nieNIF_dup;
    }

    public void setNieNIF_dup(Short nieNIF_dup) {
        this.nieNIF_dup = nieNIF_dup;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
        private String nieNIF;
        private Short nieNIF_dup;
        private String personOrCompanyName;
        private Short numberOfTimes;
        private Character codeInSigaProgram;
        private Date dateFrom;
        private Date dateTo;
        private Boolean isActive;
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

        public ClientDTO.PersonBuilder withNieNIF(String nieNIF) {
            this.nieNIF = nieNIF;
            return this;
        }

        public ClientDTO.PersonBuilder withNieNIF_dup(Short nieNIF_dup) {
            this.nieNIF_dup = nieNIF_dup;
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

        public ClientDTO.PersonBuilder withIsActive(Boolean isActive) {
            this.isActive = isActive;
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
            return new ClientDTO(this.id, this.clientId, this.nieNIF, this.nieNIF_dup, this.personOrCompanyName,
            this.numberOfTimes, this.codeInSigaProgram, this.dateFrom, this.dateTo, this.isActive, this.withoutActivity, this.clientType);
        }
    }
}