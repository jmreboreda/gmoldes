/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmoldes.domain.client.dto;

import java.util.Date;


public class ClientDTO {

    private Integer id;
    private Integer clientId;
    private String nieNIF;
    private Short nieNIF_dup;
    private String personOrCompanyName;
    private Short numberOfTimes;
    private String codeInSigaProgram;
    private Date dateFrom;
    private Date dateTo;
    private Boolean isActive;
    private Date withoutActivity;
    private String clientType;
    private Boolean claimInvoices;

    public ClientDTO() {
    }

    public ClientDTO(Integer id, Integer clientId, String nieNIF, Short nieNIF_dup, String personOrCompanyName,
                     Short numberOfTimes, String codeInSigaProgram, Date dateFrom, Date dateTo, Boolean isActive,
                     Date withoutActivity, String clientType, Boolean claimInvoices) {
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
        this.claimInvoices = claimInvoices;
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

    public String getCodeInSigaProgram() {
        return codeInSigaProgram;
    }

    public void setCodeInSigaProgram(String codeInSigaProgram) {
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

    public Boolean getClaimInvoices() {
        return claimInvoices;
    }

    public void setClaimInvoices(Boolean claimInvoices) {
        this.claimInvoices = claimInvoices;
    }

    public String toString() {
        return getPersonOrCompanyName();
    }

    public static ClientDTOBuilder create() {
        return new ClientDTOBuilder();
    }

    public static class ClientDTOBuilder {

        private Integer id;
        private Integer clientId;
        private String nieNIF;
        private Short nieNIF_dup;
        private String personOrCompanyName;
        private Short numberOfTimes;
        private String codeInSigaProgram;
        private Date dateFrom;
        private Date dateTo;
        private Boolean isActive;
        private Date withoutActivity;
        private String clientType;
        private Boolean claimInvoices;

        public ClientDTOBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public ClientDTOBuilder withClientId(Integer clientId) {
            this.clientId = clientId;
            return this;
        }

        public ClientDTOBuilder withNieNIF(String nieNIF) {
            this.nieNIF = nieNIF;
            return this;
        }

        public ClientDTOBuilder withNieNIF_dup(Short nieNIF_dup) {
            this.nieNIF_dup = nieNIF_dup;
            return this;
        }

        public ClientDTOBuilder withPersonOrCompanyName(String personOrCompanyName) {
            this.personOrCompanyName = personOrCompanyName;
            return this;
        }

        public ClientDTOBuilder withNumberOfTimes(Short numberOfTimes) {
            this.numberOfTimes = numberOfTimes;
            return this;
        }

        public ClientDTOBuilder withCodeInSigaProgram(String codeInSigaProgram) {
            this.codeInSigaProgram = codeInSigaProgram;
            return this;
        }

        public ClientDTOBuilder withDateFrom(Date dateFrom) {
            this.dateFrom = dateFrom;
            return this;
        }

        public ClientDTOBuilder withDateTo(Date dateTo) {
            this.dateTo = dateTo;
            return this;
        }

        public ClientDTOBuilder withIsActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public ClientDTOBuilder withWithOutActivity(Date withoutActivity) {
            this.withoutActivity = withoutActivity;
            return this;
        }

        public ClientDTOBuilder withClientType(String clientType) {
            this.clientType = clientType;
            return this;
        }

        public ClientDTOBuilder withClaimInvoices(Boolean claimInvoices) {
            this.claimInvoices = claimInvoices;
            return this;
        }

        public ClientDTO build() {
            return new ClientDTO(this.id, this.clientId, this.nieNIF, this.nieNIF_dup, this.personOrCompanyName,
            this.numberOfTimes, this.codeInSigaProgram, this.dateFrom, this.dateTo, this.isActive, this.withoutActivity, this.clientType, this.claimInvoices);
        }
    }
}