/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmoldes.domain.servicegm.dto;

import java.math.BigDecimal;
import java.time.LocalDate;


public class ServiceGMDTO {

    private Integer id;
    private Integer clientId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String service;
    private Boolean claimInvoices;

    public ServiceGMDTO(Integer id,
                        Integer clientId,
                        LocalDate dateFrom,
                        LocalDate dateTo,
                        String service,
                        Boolean claimInvoices) {
        this.id = id;
        this.clientId = clientId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.service = service;
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

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Boolean getClaimInvoices() {
        return claimInvoices;
    }

    public void setClaimInvoices(Boolean claimInvoices) {
        this.claimInvoices = claimInvoices;
    }

    public static ServiceGMDTO.ServiceGMBuilder create() {
        return new ServiceGMDTO.ServiceGMBuilder();
    }

    public static class ServiceGMBuilder {

        private Integer id;
        private Integer clientId;
        private LocalDate dateFrom;
        private LocalDate dateTo;
        private String service;
        private Boolean claimInvoices;

        public ServiceGMDTO.ServiceGMBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public ServiceGMDTO.ServiceGMBuilder withClientId(Integer clientId) {
            this.clientId = clientId;
            return this;
        }

        public ServiceGMDTO.ServiceGMBuilder withDateFrom(LocalDate dateFrom) {
            this.dateFrom = dateFrom;
            return this;
        }

        public ServiceGMDTO.ServiceGMBuilder withDateTo(LocalDate dateTo) {
            this.dateTo= dateTo;
            return this;
        }

        public ServiceGMDTO.ServiceGMBuilder withService(String service) {
            this.service = service;
            return this;
        }

        public ServiceGMDTO.ServiceGMBuilder withClaimInvoices(Boolean claimInvoices) {
            this.claimInvoices = claimInvoices;
            return this;
        }

        public ServiceGMDTO build() {
            return new ServiceGMDTO(this.id, this.clientId, this.dateFrom, this.dateTo, this.service, this.claimInvoices);
        }
    }
}