/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmoldes.domain.client.dto;

import gmoldes.domain.client.persistence.vo.ServiceGMVO;

import java.time.LocalDate;
import java.util.Set;


public class ClientDTOOk {

    private Integer id;
    private Integer clientId;
    private Boolean isNaturalPerson;
    private String nieNif;
    private String surNames;
    private String name;
    private String rzSocial;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String clientType;
    private String sg21Code;
    private Boolean activeClient;
    private LocalDate withoutActivity;
    private Boolean claimInvoices;
    private Set<ServiceGMVO> servicesGM;

    public ClientDTOOk() {
    }

    public ClientDTOOk(
            Integer id,
            Integer clientId,
            Boolean isNaturalPerson,
            String nieNif,
            String surNames,
            String name,
            String rzSocial,
            LocalDate dateFrom,
            LocalDate dateTo,
            String clientType,
            String sg21Code,
            Boolean activeClient,
            LocalDate withoutActivity,
            Boolean claimInvoices,
            Set<ServiceGMVO> servicesGM) {
        this.id = id;
        this.clientId = clientId;
        this.isNaturalPerson = isNaturalPerson;
        this.nieNif = nieNif;
        this.surNames = surNames;
        this.name = name;
        this.rzSocial = rzSocial;
        this.sg21Code = sg21Code;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.activeClient = activeClient;
        this.withoutActivity = withoutActivity;
        this.clientType = clientType;
        this.claimInvoices = claimInvoices;
        this.servicesGM = servicesGM;
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

    public Boolean getNaturalPerson() {
        return isNaturalPerson;
    }

    public void setNaturalPerson(Boolean naturalPerson) {
        isNaturalPerson = naturalPerson;
    }

    public String getNieNif() {
        return nieNif;
    }

    public void setNieNif(String nieNif) {
        this.nieNif = nieNif;
    }

    public String getSurNames() {
        return surNames;
    }

    public void setSurNames(String surNames) {
        this.surNames = surNames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRzSocial() {
        return rzSocial;
    }

    public void setRzSocial(String rzSocial) {
        this.rzSocial = rzSocial;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getSg21Code() {
        return sg21Code;
    }

    public void setSg21Code(String sg21Code) {
        this.sg21Code = sg21Code;
    }

    public Boolean getActiveClient() {
        return activeClient;
    }

    public void setActiveClient(Boolean activeClient) {
        this.activeClient = activeClient;
    }

    public LocalDate getWithoutActivity() {
        return withoutActivity;
    }

    public void setWithoutActivity(LocalDate withoutActivity) {
        this.withoutActivity = withoutActivity;
    }

    public Boolean getClaimInvoices() {
        return claimInvoices;
    }

    public void setClaimInvoices(Boolean claimInvoices) {
        this.claimInvoices = claimInvoices;
    }

    public Set<ServiceGMVO> getServicesGM() {
        return servicesGM;
    }

    public void setServicesGM(Set<ServiceGMVO> servicesGM) {
        this.servicesGM = servicesGM;
    }

    public String toString() {
        if(isNaturalPerson){
            return getSurNames() + ", " + getName();
        }

        return getRzSocial();
    }

    public String toNaturalName(){
        if(isNaturalPerson){
            return getName() + " " + getSurNames();
        }

        return getRzSocial();
    }

    public static ClientDTOOkBuilder create() {
        return new ClientDTOOkBuilder();
    }

    public static class ClientDTOOkBuilder {

        private Integer id;
        private Integer clientId;
        private Boolean isNaturalPerson;
        private String nieNif;
        private String surNames;
        private String name;
        private String rzSocial;
        private LocalDate dateFrom;
        private LocalDate dateTo;
        private String clientType;
        private String sg21Code;
        private Boolean activeClient;
        private LocalDate withoutActivity;
        private Boolean claimInvoices;
        private Set<ServiceGMVO> servicesGM;

        public ClientDTOOkBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public ClientDTOOkBuilder withClientId(Integer clientId) {
            this.clientId = clientId;
            return this;
        }

        public ClientDTOOkBuilder withIsNaturalPerson(Boolean isNaturalPerson) {
            this.isNaturalPerson = isNaturalPerson;
            return this;
        }

        public ClientDTOOkBuilder withNieNIF(String nieNIF) {
            this.nieNif = nieNIF;
            return this;
        }

        public ClientDTOOkBuilder withSurnames(String surNames) {
            this.surNames = surNames;
            return this;
        }

        public ClientDTOOkBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ClientDTOOkBuilder withRzSocial(String rzSocial) {
            this.rzSocial = rzSocial;
            return this;
        }

        public ClientDTOOkBuilder withDateFrom(LocalDate dateFrom) {
            this.dateFrom = dateFrom;
            return this;
        }

        public ClientDTOOkBuilder withDateTo(LocalDate dateTo) {
            this.dateTo = dateTo;
            return this;
        }

        public ClientDTOOkBuilder withClientType(String clientType) {
            this.clientType = clientType;
            return this;
        }

        public ClientDTOOkBuilder withSg21Code(String sg21Code) {
            this.sg21Code = sg21Code;
            return this;
        }


        public ClientDTOOkBuilder withActiveClient(Boolean activeClient) {
            this.activeClient = activeClient;
            return this;
        }

        public ClientDTOOkBuilder withWithOutActivity(LocalDate withoutActivity) {
            this.withoutActivity = withoutActivity;
            return this;
        }

        public ClientDTOOkBuilder withClaimInvoices(Boolean claimInvoices) {
            this.claimInvoices = claimInvoices;
            return this;
        }

        public ClientDTOOk build() {
            return new ClientDTOOk(this.id, this.clientId, this.isNaturalPerson, this.nieNif, this.surNames, this.name,this.rzSocial, this.dateFrom, this.dateTo,
            this.clientType, this.sg21Code, this.activeClient, this.withoutActivity, this.claimInvoices, this.servicesGM);
        }
    }
}