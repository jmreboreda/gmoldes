package gmoldes.domain.client.persistence.vo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "clientes_ccc_inss")
@NamedQueries(value = {
        @NamedQuery(
                name = ClientCCCVO.FIND_ALL_CCC_BY_CLIENTID,
                query = "select p from ClientCCCVO p where clientId = :code"
        )
})//    private Integer clientId;


public class ClientCCCVO implements Serializable{

    public static final String FIND_ALL_CCC_BY_CLIENTID = "ClientCCCVO.FIND_ALL_CCC_BY_CLIENTID";

    @Id
    @SequenceGenerator(name = "ccc_inss_id_seq", sequenceName = "ccc_inss_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ccc_inss_id_seq")
    @Column(name = "id", updatable = false)
    private Integer id;
    private String ccc_inss;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="clientid")
    private ClientVO clientVO;

    public ClientCCCVO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public Integer getIdcliente() {
//        return clientId;
//    }
//
//    public void setIdcliente(Integer idcliente) {
//        this.clientId = idcliente;
//    }

    public String getCcc_inss() {
        return ccc_inss;
    }

    public void setCcc_inss(String ccc_inss) {
        this.ccc_inss = ccc_inss;
    }

    public ClientVO getClientVO() {
        return clientVO;
    }

    public void setClientVO(ClientVO clientVO) {
        this.clientVO = clientVO;
    }
}
