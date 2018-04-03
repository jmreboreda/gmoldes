/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmoldes.domain.person.dto;

import java.math.BigDecimal;
import java.util.Date;


public class PersonDTO {

    private Integer idpersona;
    private String apellidos;
    private String nom_rzsoc;
    private String nifcif;
    private Short nifcifdup;
    private String numafss;
    private Date fechanacim;
    private String estciv;
    private String direccion;
    private String localidad;
    private BigDecimal codpostal;
    private Integer nivestud;
    private String nacionalidad;

    public PersonDTO(Integer idpersona, String apellidos, String nom_rzsoc, String nifcif, Short nifcifdup,
                     String numafss, Date fechanacim, String estciv, String direccion, String localidad,
                     BigDecimal codpostal, Integer nivestud, String nacionalidad) {
        this.idpersona = idpersona;
        this.apellidos = apellidos;
        this.nom_rzsoc = nom_rzsoc;
        this.nifcif = nifcif;
        this.nifcifdup = nifcifdup;
        this.numafss = numafss;
        this.fechanacim = fechanacim;
        this.estciv = estciv;
        this.direccion = direccion;
        this.localidad = localidad;
        this.codpostal = codpostal;
        this.nivestud = nivestud;
        this.nacionalidad = nacionalidad;
    }

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

    public Short getNifcifdup() {
        return nifcifdup;
    }

    public void setNifcifdup(Short nifcifdup) {
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

    public BigDecimal getCodpostal() {
        return codpostal;
    }

    public void setCodpostal(BigDecimal codpostal) {
        this.codpostal = codpostal;
    }

    public Integer getNivestud() {
        return nivestud;
    }

    public void setNivestud(Integer nivestud) {
        this.nivestud = nivestud;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    @Override
    public String toString() {
        return getApellidos() + ", " + getNom_rzsoc();
    }

    public static PersonDTO.PersonBuilder create() {
        return new PersonDTO.PersonBuilder();
    }

    public static class PersonBuilder {

        private Integer idpersona;
        private String apellidos;
        private String nom_rzsoc;
        private String nifcif;
        private Short nifcifdup;
        private String numafss;
        private Date fechanacim;
        private String estciv;
        private String direccion;
        private String localidad;
        private BigDecimal codpostal;
        private Integer nivestud;
        private String nacionalidad;

        public PersonDTO.PersonBuilder withIdpersona(Integer idpersona) {
            this.idpersona = idpersona;
            return this;
        }

        public PersonDTO.PersonBuilder withApellidos(String apellidos) {
            this.apellidos = apellidos;
            return this;
        }

        public PersonDTO.PersonBuilder withNom_rzsoc(String nom_rzsoc) {
            this.nom_rzsoc = nom_rzsoc;
            return this;
        }

        public PersonDTO.PersonBuilder withNifcif(String nifcif) {
            this.nifcif = nifcif;
            return this;
        }

        public PersonDTO.PersonBuilder withNifcifdup(Short nifcifdup) {
            this.nifcifdup = nifcifdup;
            return this;
        }

        public PersonDTO.PersonBuilder withNumafss(String numafss) {
            this.numafss = numafss;
            return this;
        }

        public PersonDTO.PersonBuilder withFechanacim(Date fechanacim) {
            this.fechanacim = fechanacim;
            return this;
        }

        public PersonDTO.PersonBuilder withEstciv(String estciv) {
            this.estciv = estciv;
            return this;
        }

        public PersonDTO.PersonBuilder withDireccion(String direccion) {
            this.direccion = direccion;
            return this;
        }

        public PersonDTO.PersonBuilder withLocalidad(String localidad) {
            this.localidad = localidad;
            return this;
        }

        public PersonDTO.PersonBuilder withCodpostal(BigDecimal codpostal) {
            this.codpostal = codpostal;
            return this;
        }

        public PersonDTO.PersonBuilder withNivestud(Integer nivestud) {
            this.nivestud = nivestud;
            return this;
        }

        public PersonDTO.PersonBuilder withNacionalidad(String nacionalidad) {
            this.nacionalidad = nacionalidad;
            return this;
        }

        public PersonDTO build() {
            return new PersonDTO(this.idpersona, this.apellidos, this.nom_rzsoc, this.nifcif, this.nifcifdup, this.numafss,
            this.fechanacim, this.estciv, this.direccion, this.localidad, this.codpostal, this.nivestud, this.nacionalidad);
        }
    }
}