package gmoldes.persistence.vo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "servicegm")
public class ServiceGMVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String service;
    private Date dateFrom;
    private Date dateTo;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="idcliente")
    private ClientVO clientVO;

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

    public void setClientVO(ClientVO clientVO) {
        this.clientVO = clientVO;
    }
}
