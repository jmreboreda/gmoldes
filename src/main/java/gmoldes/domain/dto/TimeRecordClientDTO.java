/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmoldes.domain.dto;


public class TimeRecordClientDTO {

    private Integer idcliente;
    private String nom_rzsoc;

    public TimeRecordClientDTO() {
    }

    public TimeRecordClientDTO(Integer idcliente, String nom_rzsoc) {
        this.idcliente = idcliente;
        this.nom_rzsoc = nom_rzsoc;
    }

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public String getNom_rzsoc() {
        return nom_rzsoc;
    }

    public void setNom_rzsoc(String nom_rzsoc) {
        this.nom_rzsoc = nom_rzsoc;
    }

    @Override
    public String toString() {
        return getNom_rzsoc();
    }

    public static TimeRecordClientBuilder create() {
        return new TimeRecordClientBuilder();
    }

    public static class TimeRecordClientBuilder {

        private Integer idcliente;
        private String nom_rzsoc;

        public TimeRecordClientBuilder withIdcliente(Integer idcliente) {
            this.idcliente = idcliente;
            return this;
        }

        public TimeRecordClientBuilder withNom_rzsoc(String nom_rzsoc) {
            this.nom_rzsoc = nom_rzsoc;
            return this;
        }

        public TimeRecordClientDTO build() {
            return new TimeRecordClientDTO(this.idcliente, this.nom_rzsoc);
        }
    }
}