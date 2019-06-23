/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmoldes.domain.client.dto;

import gmoldes.domain.client.persistence.vo.ClientCCCVO;
import gmoldes.domain.contract.persistence.vo.ContractVO;
import gmoldes.domain.servicegm.persistence.vo.ServiceGMVO;

import java.time.LocalDate;
import java.util.Set;


public class ClientDTO {

    private Integer id;
    private Integer clientId;
    private Boolean isNaturalPerson;
    private String nieNif;
    private String surNames;
    private String name;
    private String rzSocial;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String sg21Code;
    private Boolean activeClient;
    private LocalDate withoutActivity;
    private Set<ServiceGMVO> servicesGM;
    private Set<ClientCCCVO> clientCCC;
    private Set<ContractVO> contracts;

    public ClientDTO() {
    }

    public ClientDTO(
            Integer id,
            Integer clientId,
            Boolean isNaturalPerson,
            String nieNif,
            String surNames,
            String name,
            String rzSocial,
            LocalDate dateFrom,
            LocalDate dateTo,
            String sg21Code,
            Boolean activeClient,
            LocalDate withoutActivity,
            Set<ServiceGMVO> servicesGM,
            Set<ClientCCCVO> clientCCC,
            Set<ContractVO> contracts) {
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
        this.servicesGM = servicesGM;
        this.clientCCC = clientCCC;
        this.contracts = contracts;
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

    public Set<ServiceGMVO> getServicesGM() {
        return servicesGM;
    }

    public void setServicesGM(Set<ServiceGMVO> servicesGM) {
        this.servicesGM = servicesGM;
    }

    public Set<ClientCCCVO> getClientCCC() {
        return clientCCC;
    }

    public void setClientCCC(Set<ClientCCCVO> clientCCC) {
        this.clientCCC = clientCCC;
    }

    public Set<ContractVO> getContracts() {
        return contracts;
    }

    public void setContracts(Set<ContractVO> contracts) {
        this.contracts = contracts;
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

    public Boolean isActiveClient(){
        if(getDateTo() == null){
            return true;
        }

        return false;
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
        private String sg21Code;
        private Boolean activeClient;
        private LocalDate withoutActivity;
        private Set<ServiceGMVO> servicesGM;
        private Set<ClientCCCVO> clientCCC;
        private Set<ContractVO> contracts;

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

        public ClientDTOOkBuilder withServicesGM(Set<ServiceGMVO> serviceGMVOSet){
            this.servicesGM = serviceGMVOSet;
            return this;
        }

        public ClientDTOOkBuilder withClientCCC(Set<ClientCCCVO> clientCCC){
            this.clientCCC = clientCCC;
            return this;
        }

        public ClientDTOOkBuilder withContracts(Set<ContractVO> contracts){
            this.contracts = contracts;
            return this;
        }

        public ClientDTO build() {
            return new ClientDTO(this.id, this.clientId, this.isNaturalPerson, this.nieNif, this.surNames, this.name,this.rzSocial, this.dateFrom, this.dateTo,
                    this.sg21Code, this.activeClient, this.withoutActivity, this.servicesGM, this.clientCCC, this.contracts);
        }
    }
}