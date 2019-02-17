package gmoldes.domain.servicegm;

import java.time.LocalDate;


public class ServiceGM {

    private Integer id;
    private Integer clientId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String service;
    private Boolean claimInvoices;

    public ServiceGM(Integer id,
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

    public static ServiceGM.ServiceGMBuilder create() {
        return new ServiceGM.ServiceGMBuilder();
    }

    public static class ServiceGMBuilder {

        private Integer id;
        private Integer clientId;
        private LocalDate dateFrom;
        private LocalDate dateTo;
        private String service;
        private Boolean claimInvoices;

        public ServiceGM.ServiceGMBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public ServiceGM.ServiceGMBuilder withClientId(Integer clientId) {
            this.clientId = clientId;
            return this;
        }

        public ServiceGM.ServiceGMBuilder withDateFrom(LocalDate dateFrom) {
            this.dateFrom = dateFrom;
            return this;
        }

        public ServiceGM.ServiceGMBuilder withDateTo(LocalDate dateTo) {
            this.dateTo= dateTo;
            return this;
        }

        public ServiceGM.ServiceGMBuilder withService(String service) {
            this.service = service;
            return this;
        }

        public ServiceGM.ServiceGMBuilder withClaimInvoices(Boolean claimInvoices) {
            this.claimInvoices = claimInvoices;
            return this;
        }

        public ServiceGM build() {
            return new ServiceGM(this.id, this.clientId, this.dateFrom, this.dateTo, this.service, this.claimInvoices);
        }
    }
}