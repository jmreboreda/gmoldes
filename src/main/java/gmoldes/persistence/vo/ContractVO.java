/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmoldes.persistence.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "contratoshistorico")
@NamedQueries(value = {
        @NamedQuery(
                name = ContractVO.FIND_ALL_CONTRACTS_BY_CLIENT_ID,
                query = " select p from ContractVO as p where p.idcliente_gm = :code order by p.trabajador_name"
        ),
        @NamedQuery(
                name = ContractVO.FIND_ALL_CONTRACTS_BY_CLIENT_ID_IN_PERIOD,
                query = " select p from ContractVO as p where p.idcliente_gm = :code and p.f_desde <= :initialperiod and (p.f_hasta >= :initialperiod or p.f_hasta is null) order by p.trabajador_name"
        ),

        @NamedQuery(
                name = ContractVO.FIND_ALL_ACTIVE_CONTRACTS_BY_CLIENT_ID,
                query = " select p from ContractVO as p where p.envigor = true and p.idcliente_gm = :code order by p.trabajador_name"
        ),
        @NamedQuery(
                name = ContractVO.FIND_ALL_CLIENT_WITH_ACTIVE_CONTRACT_SORTED,
                query = "select p from ContractVO as p where p.envigor = true order by p.clientegm_name"
        )
})
public class ContractVO implements Serializable {
    public static final String FIND_ALL_CONTRACTS_BY_CLIENT_ID = "ContractVO.FIND_ALL_CONTRACTS_BY_CLIENT_ID";
    public static final String FIND_ALL_CONTRACTS_BY_CLIENT_ID_IN_PERIOD = "ContractVO.FIND_ALL_CONTRACTS_BY_CLIENT_ID_IN_PERIOD";
    public static final String FIND_ALL_ACTIVE_CONTRACTS_BY_CLIENT_ID = "ContractVO.FIND_ALL_ACTIVE_CONTRACTS_BY_CLIENT_ID";
    public static final String FIND_ALL_CLIENT_WITH_ACTIVE_CONTRACT_SORTED = "ContractVO.FIND_ALL_CLIENT_WITH_ACTIVE_CONTRACT_SORTED";


    @Id
    @SequenceGenerator(name = "contratoshistorico_id_seq", sequenceName = "contratoshistorico_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contratoshistorico_id_seq")
    @Column(name = "id", updatable = false)
    private Integer id;
    private Integer numcontrato;
    private Integer numvariacion;
    private Integer tipovariacion;
    private Integer idcliente_gm;
    private String clientegm_name;
    private String contrato_ccc;
    @Column(name = "idtrabajador", nullable = false)
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

    public Date getIdc() {
        return idc;
    }

    public void setIdc(Date idc) {
        this.idc = idc;
    }

    public Date getPreavisofin() {
        return preavisofin;
    }

    public void setPreavisofin(Date preavisofin) {
        this.preavisofin = preavisofin;
    }
}
