package gmoldes.domain.client.persistence.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

@Entity
//@Table(name = "client", uniqueConstraints = {@UniqueConstraint(columnNames = {"nifcif", "nifcif_dup"})})
@Table(name = "client_ok")

@NamedQueries(value = {
        @NamedQuery(
                name = ClientVO.FIND_ALL_ACTIVE_CLIENTS_BY_NAME_PATTERN,
                query = "select p from ClientVO as p where p.clientType like '%Asesoría%' and (lower(p.rzSocial) like lower(:lettersOfName) or lower(p.surNames) like lower(:lettersOfName) or lower(p.name) like lower(:lettersOfName)) and p.activeClient = true"
        ),
        @NamedQuery(
                name = ClientVO.FIND_ALL_ACTIVE_CLIENTS_IN_ALPHABETICAL_ORDER,
                query = "select p from ClientVO as p where p.activeClient = true order by p.surNames, p.name, p.rzSocial"
        ),
        @NamedQuery(
                name = ClientVO.FIND_CLIENT_BY_SAME_NAME,
                query = "select p from ClientVO as p where (p.rzSocial = :nom_rzsoc or concat(surNames, ', ', name) = :nom_rzsoc)"
        ),
        @NamedQuery(
                name = ClientVO.FIND_CLIENT_BY_CLIENT_ID,
                query = "select p from ClientVO as p where p.clientId = :clientId"
        ),
        @NamedQuery(
                name = ClientVO.FIND_ALL_CLIENT_WITH_INVOICES_TO_BE_REQUIRED_IN_PERIOD,
                query = "select p from ClientVO as p where (p.dateFrom is null or p.dateFrom <= :finalDate) and (p.dateTo is null or p.dateTo >= :finalDate) and p.withoutActivity is null and claiminvoices = true order by p.rzSocial, p.surNames, p.name"
        )
})

public class ClientVO implements Serializable {

    public static final String FIND_ALL_ACTIVE_CLIENTS_BY_NAME_PATTERN = "ClientVO.FIND_ALL_ACTIVE_CLIENTS_BY_NAME_PATTERN";
    public static final String FIND_ALL_ACTIVE_CLIENTS_IN_ALPHABETICAL_ORDER = "ClientVO.FIND_ALL_ACTIVE_CLIENTS_IN_ALPHABETICAL_ORDER";
    public static final String FIND_CLIENT_BY_SAME_NAME = "ClientVO.FIND_CLIENT_BY_SAME_NAME";
    public static final String FIND_CLIENT_BY_CLIENT_ID = "ClientVO.FIND_CLIENT_BY_CLIENT_ID";
    public static final String FIND_ALL_CLIENT_WITH_INVOICES_TO_BE_REQUIRED_IN_PERIOD = "ClientVO.FIND_ALL_CLIENT_WITH_INVOICES_TO_BE_REQUIRED_IN_PERIOD";

    @Id
    @SequenceGenerator(name = "client_ok_id_seq", sequenceName = "client_ok_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_ok_id_seq")
    @Column(name = "id", updatable = false)
    private Integer id;
    @Column(name = "clientid")
    private Integer clientId;
    private Boolean isNaturalPerson;
    private String nieNif;
    private String surNames;
    private String name;
    private String rzSocial;
    private Date dateFrom;
    private Date dateTo;
    private String clientType;
    @Column(name = "sg21code", length = 4)
    private String sg21Code;
    private Boolean activeClient;
    private Date withoutActivity;
    private Boolean claimInvoices;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientVO", cascade = CascadeType.ALL)
    private Set<ServiceGMVO> servicesGM;

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

    public void setNifCif(String nieNif) {
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

    public Date getWithoutActivity() {
        return withoutActivity;
    }

    public void setWithoutActivity(Date withoutActivity) {
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
}

