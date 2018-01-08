package gmoldes.persistence.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "clientes", uniqueConstraints = {@UniqueConstraint(columnNames = {"nifcif", "nifcif_dup"})})
@NamedQueries(value = {
        @NamedQuery(
                name = ClientVO.FIND_ALL_ACTIVE_CLIENTS_BY_NAME_PATTERN_IN_ALPHABETICAL_ORDER,
                query = " select p from ClientVO as p where lower(p.nom_rzsoc) like lower(:code) and p.cltactivo = true order by p.nom_rzsoc"
        ),
        @NamedQuery(
                name = ClientVO.FIND_ALL_ACTIVE_CLIENTS_IN_ALPHABETICAL_ORDER,
                query = " select p from ClientVO as p where p.cltactivo = true order by p.nom_rzsoc"
        ),
        @NamedQuery(
                name = ClientVO.FIND_CLIENT_BY_SAME_NAME,
                query = " select p from ClientVO as p where p.nom_rzsoc = :nom_rzsoc"
        )
})

public class ClientVO implements Serializable {

    public static final String FIND_ALL_ACTIVE_CLIENTS_BY_NAME_PATTERN_IN_ALPHABETICAL_ORDER = "ClientVO.FIND_ALL_ACTIVE_CLIENTS_BY_NAME_PATTERN_IN_ALPHABETICAL_ORDER";
    public static final String FIND_ALL_ACTIVE_CLIENTS_IN_ALPHABETICAL_ORDER = "ClientVO.FIND_ALL_ACTIVE_CLIENTS_IN_ALPHABETICAL_ORDER";
    public static final String FIND_CLIENT_BY_SAME_NAME = "ClientVO.FIND_CLIENT_BY_SAME_NAME";


    @Id
    @SequenceGenerator(name = "clientes_id_seq", sequenceName = "clientes_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientes_id_seq")
    @Column(name = "id", updatable = false)
    private Integer id;
    private Integer idcliente;
    private String nifcif;
    private Integer nifcif_dup;
    private String nom_rzsoc;
    private Integer numvez;
    private String cltsg21;
    private Date fdesde;
    private Date fhasta;
    private Boolean cltactivo;
    private Date sinactividad;
    private String tipoclte;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientVO", cascade = CascadeType.ALL)
    private Set<ServiceGMVO> servicesGM;

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

    public String getNifcif() {
        return nifcif;
    }

    public void setNifcif(String nifcif) {
        this.nifcif = nifcif;
    }

    public Integer getNifcif_dup() {
        return nifcif_dup;
    }

    public void setNifcif_dup(Integer nifcif_dup) {
        this.nifcif_dup = nifcif_dup;
    }

    public String getNom_rzsoc() {
        return nom_rzsoc;
    }

    public void setNom_rzsoc(String nom_rzsoc) {
        this.nom_rzsoc = nom_rzsoc;
    }

    public Integer getNumvez() {
        return numvez;
    }

    public void setNumvez(Integer numvez) {
        this.numvez = numvez;
    }

    public String getCltsg21() {
        return cltsg21;
    }

    public void setCltsg21(String cltsg21) {
        this.cltsg21 = cltsg21;
    }

    public Date getFdesde() {
        return fdesde;
    }

    public void setFdesde(Date fdesde) {
        this.fdesde = fdesde;
    }

    public Date getFhasta() {
        return fhasta;
    }

    public void setFhasta(Date fhasta) {
        this.fhasta = fhasta;
    }

    public Boolean getCltactivo() {
        return cltactivo;
    }

    public void setCltactivo(Boolean cltactivo) {
        this.cltactivo = cltactivo;
    }

    public Date getSinactividad() {
        return sinactividad;
    }

    public void setSinactividad(Date sinactividad) {
        this.sinactividad = sinactividad;
    }

    public String getTipoclte() {
        return tipoclte;
    }

    public void setTipoclte(String tipoclte) {
        this.tipoclte = tipoclte;
    }

    public Set<ServiceGMVO> getServicesGM() {
        return servicesGM;
    }

    public void setServicesGM(Set<ServiceGMVO> servicesGM) {
        this.servicesGM = servicesGM;
    }
}
