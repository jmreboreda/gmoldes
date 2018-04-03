package gmoldes.components.contract.persistence.vo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tiposcontratos")
@NamedQueries(value = {
        @NamedQuery(
                name = ContractTypeVO.FIND_ALL_CONTRACT_TYPES,
                query = "select p from ContractTypeVO p order by descripctto"
        )
})

public class ContractTypeVO implements Serializable{

    public static final String FIND_ALL_CONTRACT_TYPES = "ContractTypeVO.FIND_ALL_CONTRACT_TYPES";

    @Id
    @SequenceGenerator(name = "tiposcontratos_id_seq", sequenceName = "tiposcontratos_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tiposcontratos_id_seq")
    @Column(name = "id", updatable = false)
    private Integer id;
    private Integer idtipocontrato;
    private String descripctto;
    private Integer idsepe;
    private String nombresepe;
    private String duracionsepe;
    private String jornadasepe;


    public ContractTypeVO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdtipocontrato() {
        return idtipocontrato;
    }

    public void setIdtipocontrato(Integer idtipocontrato) {
        this.idtipocontrato = idtipocontrato;
    }

    public String getDescripctto() {
        return descripctto;
    }

    public void setDescripctto(String descripctto) {
        this.descripctto = descripctto;
    }

    public Integer getIdsepe() {
        return idsepe;
    }

    public void setIdsepe(Integer idsepe) {
        this.idsepe = idsepe;
    }

    public String getNombresepe() {
        return nombresepe;
    }

    public void setNombresepe(String nombresepe) {
        this.nombresepe = nombresepe;
    }

    public String getDuracionsepe() {
        return duracionsepe;
    }

    public void setDuracionsepe(String duracionsepe) {
        this.duracionsepe = duracionsepe;
    }

    public String getJornadasepe() {
        return jornadasepe;
    }

    public void setJornadasepe(String jornadasepe) {
        this.jornadasepe = jornadasepe;
    }
}
