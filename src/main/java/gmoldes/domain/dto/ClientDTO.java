/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmoldes.domain.dto;

import java.util.Date;


public class ClientDTO {

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

    public ClientDTO() {
    }

    public ClientDTO(Integer id, Integer idcliente, String nifcif, Integer nifcif_dup, String nom_rzsoc,
                     Integer numvez, String cltsg21, Date fdesde, Date fhasta, Boolean cltactivo,
                     Date sinactividad, String tipoclte) {
        this.id = id;
        this.idcliente = idcliente;
        this.nifcif = nifcif;
        this.nifcif_dup = nifcif_dup;
        this.nom_rzsoc = nom_rzsoc;
        this.numvez = numvez;
        this.cltsg21 = cltsg21;
        this.fdesde = fdesde;
        this.fhasta = fhasta;
        this.cltactivo = cltactivo;
        this.sinactividad = sinactividad;
        this.tipoclte = tipoclte;
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

    @Override
    public String toString() {
        return getNom_rzsoc();
    }

    public static ClientDTO.PersonBuilder create() {
        return new ClientDTO.PersonBuilder();
    }

    public static class PersonBuilder {

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

        public ClientDTO.PersonBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public ClientDTO.PersonBuilder withIdcliente(Integer idcliente) {
            this.idcliente = idcliente;
            return this;
        }

        public ClientDTO.PersonBuilder withNifcif(String nifcif) {
            this.nifcif = nifcif;
            return this;
        }

        public ClientDTO.PersonBuilder withNifcif_dup(Integer nifcif_dup) {
            this.nifcif_dup = nifcif_dup;
            return this;
        }

        public ClientDTO.PersonBuilder withNom_rzsoc(String nom_rzsoc) {
            this.nom_rzsoc = nom_rzsoc;
            return this;
        }

        public ClientDTO.PersonBuilder withNumvez(Integer numvez) {
            this.numvez = numvez;
            return this;
        }

        public ClientDTO.PersonBuilder withCltsg21(String cltsg21) {
            this.cltsg21 = cltsg21;
            return this;
        }

        public ClientDTO.PersonBuilder withFdesde(Date fdesde) {
            this.fdesde = fdesde;
            return this;
        }

        public ClientDTO.PersonBuilder withFhasta(Date fhasta) {
            this.fhasta = fhasta;
            return this;
        }

        public ClientDTO.PersonBuilder withCltactivo(Boolean cltactivo) {
            this.cltactivo = cltactivo;
            return this;
        }

        public ClientDTO.PersonBuilder withSinactividad(Date sinactividad) {
            this.sinactividad = sinactividad;
            return this;
        }

        public ClientDTO.PersonBuilder withTipoclte(String tipoclte) {
            this.tipoclte = tipoclte;
            return this;
        }

        public ClientDTO build() {
            return new ClientDTO(this.id, this.idcliente, this.nifcif, this.nifcif_dup, this.nom_rzsoc,
            this.numvez, this.cltsg21, this.fdesde, this.fhasta, this.cltactivo, this.sinactividad, this.tipoclte);
        }
    }
}