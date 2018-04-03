package gmoldes.domain.client.persistence.vo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "clientes_ccc_inss")
@NamedQueries(value = {
        @NamedQuery(
                name = ClientCCCVO.FIND_ALL_CCC_BY_CLIENTID,
                query = "select p from ClientCCCVO p where idcliente = :code"
        )
})

public class ClientCCCVO implements Serializable{

    public static final String FIND_ALL_CCC_BY_CLIENTID = "ClientCCCVO.FIND_ALL_CCC_BY_CLIENTID";

    @Id
    @SequenceGenerator(name = "ccc_inss_id_seq", sequenceName = "ccc_inss_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ccc_inss_id_seq")
    @Column(name = "id", updatable = false)
    private Integer id;
    private Integer idcliente;
    private String ccc_inss;


    public ClientCCCVO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public String getCcc_inss() {
        return ccc_inss;
    }

    public void setCcc_inss(String ccc_inss) {
        this.ccc_inss = ccc_inss;
    }
}
