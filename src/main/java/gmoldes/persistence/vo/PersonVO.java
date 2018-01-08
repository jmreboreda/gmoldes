package gmoldes.persistence.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "personas")
@NamedQueries(value = {
        @NamedQuery(
                name = PersonVO.FIND_ALL_PERSONS_BY_NAME_PATTERN_IN_ALPHABETICAL_ORDER,
                query = " select p from PersonVO as p where lower(p.apellidos) like lower(:code) or lower(p.nom_rzsoc) like lower(:code) order by p.apellidos, p.nom_rzsoc"
        ),
        @NamedQuery(
                name = PersonVO.FIND_PERSON_BY_ID,
                query = " select p from PersonVO as p where p.idpersona = :code"
        ),
        @NamedQuery(
                name = PersonVO.FIND_PERSON_BY_SAME_NAME,
                query = " select p from ClientVO as p where p.nom_rzsoc = :nom_rzsoc"
        )
})

public class PersonVO implements Serializable {

    public static final String FIND_ALL_PERSONS_BY_NAME_PATTERN_IN_ALPHABETICAL_ORDER = "PersonVO.FIND_ALL_PERSONS_BY_NAME_PATTERN_IN_ALPHABETICAL_ORDER";
    public static final String FIND_PERSON_BY_ID = "PersonVO.FIND_PERSON_BY_ID";
    public static final String FIND_PERSON_BY_SAME_NAME = "PersonVO.FIND_PERSON_BY_SAME_NAME";


    @Id
    @SequenceGenerator(name = "autoincremento",
            sequenceName = "autoincremento",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "autoincremento")
    @Column(name = "idpersona", updatable = false)
    private Integer idpersona;
    private String apellidos;
    private String nom_rzsoc;
    private String nifcif;
    private String nifcifdup;
    private String numafss;
    private Date fechanacim;
    private String estciv;
    private String direccion;
    private String localidad;
    private Integer codpostal;
    private String nivestud;
    private String nacionalidad;

    public Integer getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(Integer idpersona) {
        this.idpersona = idpersona;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNom_rzsoc() {
        return nom_rzsoc;
    }

    public void setNom_rzsoc(String nom_rzsoc) {
        this.nom_rzsoc = nom_rzsoc;
    }

    public String getNifcif() {
        return nifcif;
    }

    public void setNifcif(String nifcif) {
        this.nifcif = nifcif;
    }

    public String getNifcifdup() {
        return nifcifdup;
    }

    public void setNifcifdup(String nifcifdup) {
        this.nifcifdup = nifcifdup;
    }

    public String getNumafss() {
        return numafss;
    }

    public void setNumafss(String numafss) {
        this.numafss = numafss;
    }

    public Date getFechanacim() {
        return fechanacim;
    }

    public void setFechanacim(Date fechanacim) {
        this.fechanacim = fechanacim;
    }

    public String getEstciv() {
        return estciv;
    }

    public void setEstciv(String estciv) {
        this.estciv = estciv;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Integer getCodpostal() {
        return codpostal;
    }

    public void setCodpostal(Integer codpostal) {
        this.codpostal = codpostal;
    }

    public String getNivestud() {
        return nivestud;
    }

    public void setNivestud(String nivestud) {
        this.nivestud = nivestud;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    @Override
    public String toString(){
        return getApellidos() + ", " + getNom_rzsoc();
    }
}
