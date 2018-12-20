package gmoldes.domain.servicegm.persistence.vo;

import gmoldes.domain.client.Client;
import gmoldes.domain.client.persistence.vo.ClientVO;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "servicegm")
@NamedQueries(value = {
        @NamedQuery(
                name = ServiceGMVO.FIND_SERVICE_GM_BY_CLIENT_ID,
                query = "select p from ServiceGMVO as p where clientId = :clientId"
        ),
        @NamedQuery(
                name = ServiceGMVO.FIND_ALL_CLIENT_WITH_INVOICES_TO_CLAIM_IN_PERIOD,
                query = "select p from ServiceGMVO as p where (p.dateFrom is null or p.dateFrom <= :periodFinalDate) and (p.dateTo is null or p.dateTo >= :periodFinalDate) and claimInvoices = true"
        )
})

public class ServiceGMVO implements Serializable{

    public static final String FIND_SERVICE_GM_BY_CLIENT_ID = "ServiceGMVO.FIND_SERVICE_GM_BY_CLIENT_ID";
    public static final String FIND_ALL_CLIENT_WITH_INVOICES_TO_CLAIM_IN_PERIOD = "ServiceGMVO.FIND_ALL_CLIENT_WITH_INVOICES_TO_CLAIM_IN_PERIOD";

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @SequenceGenerator(name = "servicegm_id_seq", sequenceName = "servicegm_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "servicegm_id_seq")
    @Column(name = "id", updatable = false)
    private Integer id;
    private Date dateFrom;
    private Date dateTo;
    private String service;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="clientid")
    private ClientVO clientVO;
    private Boolean claimInvoices;

    public ServiceGMVO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
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

    public ClientVO getClientVO() {
        return clientVO;
    }

    public void setClientId(ClientVO clientVO) {
        this.clientVO = clientVO;
    }

    public Boolean getClaimInvoices() {
        return claimInvoices;
    }

    public void setClaimInvoices(Boolean claimInvoices) {
        this.claimInvoices = claimInvoices;
    }
}
