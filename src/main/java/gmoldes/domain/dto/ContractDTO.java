/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmoldes.domain.dto;

import java.util.Date;


public class ContractDTO {

    private Integer id;
    private Integer numcontrato;
    private Integer numvariacion;
    private Integer tipovariacion;
    private Integer idcliente_gm;
    private String clientegm_name;
    private String contrato_ccc;
    private Integer idtrabajador;
    private String trabajador_name;
    private String categoria;
    private String jor_trab;
    private String jor_trab_dias;
    private String jor_tipo;
    private String tipoctto;
    private Date f_desde;
    private Date f_hasta;
    private String id_ctto_inem;
    private Boolean envigor;
    private String notas_gestor;
    private String notas_privadas;
    private String duracion;
    private Integer subrogacion;
    private Date idc;
    private Date preavisofin;

    public ContractDTO(Integer id,
                       Integer numcontrato,
                       Integer numvariacion,
                       Integer tipovariacion,
                       Integer idcliente_gm,
                       String clientegm_name,
                       String contrato_ccc,
                       Integer idtrabajador,
                       String trabajador_name,
                       String categoria,
                       String jor_trab,
                       String jor_trab_dias,
                       String jor_tipo,
                       String tipoctto,
                       Date f_desde,
                       Date f_hasta,
                       String id_ctto_inem,
                       Boolean envigor,
                       String notas_gestor,
                       String notas_privadas,
                       String duracion,
                       Integer subrogacion,
                       Date idc,
                       Date preavisofin){

        this.id = id;
        this.numcontrato = numcontrato;
        this.numvariacion = numvariacion;
        this.tipovariacion = tipovariacion;
        this.idcliente_gm = idcliente_gm;
        this.clientegm_name = clientegm_name;
        this.contrato_ccc = contrato_ccc;
        this.idtrabajador = idtrabajador;
        this.trabajador_name = trabajador_name;
        this.categoria = categoria;
        this.jor_trab = jor_trab;
        this.jor_trab_dias = jor_trab_dias;
        this.jor_tipo = jor_tipo;
        this.tipoctto = tipoctto;
        this.f_desde = f_desde;
        this.f_hasta = f_hasta;
        this.id_ctto_inem = id_ctto_inem;
        this.envigor = envigor;
        this.notas_gestor = notas_gestor;
        this.notas_privadas = notas_privadas;
        this.duracion = duracion;
        this.subrogacion = subrogacion;
        this.idc = idc;
        this.preavisofin = preavisofin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumcontrato() {
        return numcontrato;
    }

    public void setNumcontrato(Integer numcontrato) {
        this.numcontrato = numcontrato;
    }

    public Integer getNumvariacion() {
        return numvariacion;
    }

    public void setNumvariacion(Integer numvariacion) {
        this.numvariacion = numvariacion;
    }

    public Integer getTipovariacion() {
        return tipovariacion;
    }

    public void setTipovariacion(Integer tipovariacion) {
        this.tipovariacion = tipovariacion;
    }

    public Integer getIdcliente_gm() {
        return idcliente_gm;
    }

    public void setIdcliente_gm(Integer idcliente_gm) {
        this.idcliente_gm = idcliente_gm;
    }

    public String getClientegm_name() {
        return clientegm_name;
    }

    public void setClientegm_name(String clientegm_name) {
        this.clientegm_name = clientegm_name;
    }

    public String getContrato_ccc() {
        return contrato_ccc;
    }

    public void setContrato_ccc(String contrato_ccc) {
        this.contrato_ccc = contrato_ccc;
    }

    public Integer getIdtrabajador() {
        return idtrabajador;
    }

    public void setIdtrabajador(Integer idtrabajador) {
        this.idtrabajador = idtrabajador;
    }

    public String getTrabajador_name() {
        return trabajador_name;
    }

    public void setTrabajador_name(String trabajador_name) {
        this.trabajador_name = trabajador_name;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getJor_trab() {
        return jor_trab;
    }

    public void setJor_trab(String jor_trab) {
        this.jor_trab = jor_trab;
    }

    public String getJor_trab_dias() {
        return jor_trab_dias;
    }

    public void setJor_trab_dias(String jor_trab_dias) {
        this.jor_trab_dias = jor_trab_dias;
    }

    public String getJor_tipo() {
        return jor_tipo;
    }

    public void setJor_tipo(String jor_tipo) {
        this.jor_tipo = jor_tipo;
    }

    public String getTipoctto() {
        return tipoctto;
    }

    public void setTipoctto(String tipoctto) {
        this.tipoctto = tipoctto;
    }

    public Date getF_desde() {
        return f_desde;
    }

    public void setF_desde(Date f_desde) {
        this.f_desde = f_desde;
    }

    public Date getF_hasta() {
        return f_hasta;
    }

    public void setF_hasta(Date f_hasta) {
        this.f_hasta = f_hasta;
    }

    public String getId_ctto_inem() {
        return id_ctto_inem;
    }

    public void setId_ctto_inem(String id_ctto_inem) {
        this.id_ctto_inem = id_ctto_inem;
    }

    public Boolean getEnvigor() {
        return envigor;
    }

    public void setEnvigor(Boolean envigor) {
        this.envigor = envigor;
    }

    public String getNotas_gestor() {
        return notas_gestor;
    }

    public void setNotas_gestor(String notas_gestor) {
        this.notas_gestor = notas_gestor;
    }

    public String getNotas_privadas() {
        return notas_privadas;
    }

    public void setNotas_privadas(String notas_privadas) {
        this.notas_privadas = notas_privadas;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public Integer getSubrogacion() {
        return subrogacion;
    }

    public void setSubrogacion(Integer subrogacion) {
        this.subrogacion = subrogacion;
    }

    public static ContractBuilder create() {
        return new ContractBuilder();
    }

    public static class ContractBuilder {

        private Integer id;
        private Integer numcontrato;
        private Integer numvariacion;
        private Integer tipovariacion;
        private Integer idcliente_gm;
        private String clientegm_name;
        private String contrato_ccc;
        private Integer idtrabajador;
        private String trabajador_name;
        private String categoria;
        private String jor_trab;
        private String jor_trab_dias;
        private String jor_tipo;
        private String tipoctto;
        private Date f_desde;
        private Date f_hasta;
        private String id_ctto_inem;
        private Boolean envigor;
        private String notas_gestor;
        private String notas_privadas;
        private String duracion;
        private Integer subrogacion;
        private Date idc;
        private Date preavisofin;

        public ContractBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public ContractBuilder withNumcontrato(Integer numcontrato) {
            this.numcontrato = numcontrato;
            return this;
        }

        public ContractBuilder withNumvariacion(Integer numvariacion) {
            this.numvariacion = numvariacion;
            return this;
        }

        public ContractBuilder withTipovariacion(Integer tipovariacion) {
            this.tipovariacion = tipovariacion;
            return this;
        }

        public ContractBuilder withIdcliente_gm(Integer idcliente_gm) {
            this.idcliente_gm = idcliente_gm;
            return this;
        }

        public ContractBuilder withClientegm_name(String clientegm_name) {
            this.clientegm_name = clientegm_name;
            return this;
        }

        public ContractBuilder withContrato_ccc(String contrato_ccc) {
            this.contrato_ccc = contrato_ccc;
            return this;
        }

        public ContractBuilder withIdtrabajador(Integer idtrabajador) {
            this.idtrabajador = idtrabajador;
            return this;
        }

        public ContractBuilder withTrabajador_name(String trabajador_name) {
            this.trabajador_name = trabajador_name;
            return this;
        }

        public ContractBuilder withCategoria(String categoria) {
            this.categoria = categoria;
            return this;
        }

        public ContractBuilder withJor_trab(String jor_trab) {
            this.jor_trab = jor_trab;
            return this;
        }

        public ContractBuilder withJor_trab_dias(String jor_trab_dias) {
            this.jor_trab_dias = jor_trab_dias;
            return this;
        }

        public ContractBuilder withJor_tipo(String jor_tipo) {
            this.jor_tipo = jor_tipo;
            return this;
        }

        public ContractBuilder withTipoctto(String tipoctto) {
            this.tipoctto = tipoctto;
            return this;
        }

        public ContractBuilder withF_desde(Date f_desde) {
            this.f_desde = f_desde;
            return this;
        }

        public ContractBuilder withF_hasta(Date f_hasta) {
            this.f_hasta = f_hasta;
            return this;
        }

        public ContractBuilder withId_ctto_inem(String id_ctto_inem) {
            this.id_ctto_inem = id_ctto_inem;
            return this;
        }

        public ContractBuilder withEnvigor(Boolean envigor) {
            this.envigor = envigor;
            return this;
        }

        public ContractBuilder withNotas_gestor(String notas_gestor) {
            this.notas_gestor = notas_gestor;
            return this;
        }

        public ContractBuilder withNotas_privadas(String notas_privadas) {
            this.notas_privadas = notas_privadas;
            return this;
        }

        public ContractBuilder withDuracion(String duracion) {
            this.duracion = duracion;
            return this;
        }

        public ContractBuilder withSubrogacion(Integer subrogacion) {
            this.subrogacion = subrogacion;
            return this;
        }

        public ContractBuilder withIdc(Date idc) {
            this.idc = idc;
            return this;
        }

        public ContractBuilder withPreavisofin(Date preavisofin) {
            this.preavisofin = preavisofin;
            return this;
        }

        public ContractDTO build() {
            return new ContractDTO(this.id, this.numcontrato, this.numvariacion, this.tipovariacion, this.idcliente_gm, this.clientegm_name,
            this.contrato_ccc, this.idtrabajador, this.trabajador_name, this.categoria, this.jor_trab, this.jor_trab_dias, this.jor_tipo,
            this.tipoctto, this.f_desde, this.f_hasta, this.id_ctto_inem, this.envigor, this.notas_gestor, this.notas_privadas,
            this.duracion, this.subrogacion, this.idc, this.preavisofin);
        }
    }
}