package gmoldes.persistence.vo;

import gmoldes.domain.dto.ContractDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "contratoshistorico")
@NamedQueries(value = {
        @NamedQuery(
                name = ContractVO.FIND_CONTRACTS_EXPIRATION,
                query = "select p from ContractVO p where p.preavisofin is null AND p.f_hasta is not null and date(p.f_hasta) - date(NOW()) <= 20"
        ),
        @NamedQuery(
                name = ContractVO.IDC_CONTROL,
                query = "select p from ContractVO p where p.idc is null and date(p.f_desde) - date (now()) <= 3"
        ),
        @NamedQuery(
                name = ContractVO.FIND_ALL_CONTRACTS_BY_CLIENT_ID,
                query = "select p from ContractVO p where p.idcliente_gm = :code order by p.trabajador_name"
        ),
        @NamedQuery(
                name = ContractVO.FIND_ALL_CONTRACTS_BY_CLIENT_ID_IN_PERIOD,
                query = "select p from ContractVO p where p.idcliente_gm = :code and p.f_desde <= :initialperiod and (p.f_hasta >= :initialperiod or p.f_hasta is null) order by p.trabajador_name"
        ),
        @NamedQuery(
                name = ContractVO.FIND_ALL_CONTRACTS_WITH_TIMERECORD_BY_CLIENT_ID_IN_PERIOD,
                query = "select p from ContractVO p where p.idcliente_gm = :code " +
                        "and tipovariacion < 800 " +
                        "and concat(trim(to_char(extract(year from p.f_desde),'9999')), trim(to_char(extract(month from p.f_desde),'09'))) <= :initialperiod " +
                        "and (concat(trim(to_char(extract(year from p.f_hasta),'9999')), trim(to_char(extract(month from p.f_hasta),'09'))) >= :initialperiod " +
                        "or p.f_hasta is null) and (jor_tipo = 'Parcial' or tipoctto = 'Formación') order by p.trabajador_name, p.f_desde"
        ),
        @NamedQuery(
                name = ContractVO.FIND_ALL_ACTIVE_CONTRACTS_BY_CLIENT_ID,
                query = "select p from ContractVO p where p.envigor = true and p.idcliente_gm = :code order by p.trabajador_name"
        ),
        @NamedQuery(
                name = ContractVO.FIND_ALL_CLIENT_WITH_ACTIVE_CONTRACT_SORTED,
                query = "select p from ContractVO p where p.envigor = true order by p.clientegm_name"
        ),
        @NamedQuery(
                name = ContractVO.FIND_ALL_CLIENT_WITH_CONTRACT_WITH_TIMERECORD_IN_PERIOD_SORTED,
                query = "select p from ContractVO p where tipovariacion < 800" +
                        "and concat(trim(to_char(extract(year from p.f_desde),'9999')), trim(to_char(extract(month from p.f_desde),'09'))) <= :yearMonth " +
                        "and (concat(trim(to_char(extract(year from p.f_hasta),'9999')), trim(to_char(extract(month from p.f_hasta),'09'))) >= :yearMonth " +
                        "or p.f_hasta is null) and (jor_tipo = 'Parcial' or tipoctto = 'Formación') order by p.clientegm_name"
        ),
        @NamedQuery(
                name = ContractVO.FIND_ALL_CLIENT_WITH_ACTIVE_CONTRACT_WITH_TIMERECORD_SORTED,
                query = "select p from ContractVO p where p.envigor = true and (p.jor_tipo = 'Parcial' or tipoctto = 'Formación') order by p.clientegm_name"
        )
})

public class ContractVO implements Serializable {
    public static final String FIND_CONTRACTS_EXPIRATION = "ContractVO.FIND_CONTRACTS_EXPIRATION";
    public static final String IDC_CONTROL = "ContractVO.IDC_CONTROL";
    public static final String FIND_HIGHEST_CONTRACT_NUMBER = "ContractVO.FIND_HIGHEST_CONTRACT_NUMBER";
    public static final String FIND_ALL_CONTRACTS_BY_CLIENT_ID = "ContractVO.FIND_ALL_CONTRACTS_BY_CLIENT_ID";
    public static final String FIND_ALL_CONTRACTS_BY_CLIENT_ID_IN_PERIOD = "ContractVO.FIND_ALL_CONTRACTS_BY_CLIENT_ID_IN_PERIOD";
    public static final String FIND_ALL_ACTIVE_CONTRACTS_BY_CLIENT_ID = "ContractVO.FIND_ALL_ACTIVE_CONTRACTS_BY_CLIENT_ID";
    public static final String FIND_ALL_CLIENT_WITH_ACTIVE_CONTRACT_SORTED = "ContractVO.FIND_ALL_CLIENT_WITH_ACTIVE_CONTRACT_SORTED";
    public static final String FIND_ALL_CLIENT_WITH_ACTIVE_CONTRACT_WITH_TIMERECORD_SORTED = "ContractVO.FIND_ALL_CLIENT_WITH_ACTIVE_CONTRACT_WITH_TIMERECORD_SORTED";
    public static final String FIND_ALL_CONTRACTS_WITH_TIMERECORD_BY_CLIENT_ID_IN_PERIOD = "ContractVO.FIND_ALL_CONTRACTS_WITH_TIMERECORD_BY_CLIENT_ID_IN_PERIOD";
    public static final String FIND_ALL_CLIENT_WITH_CONTRACT_WITH_TIMERECORD_IN_PERIOD_SORTED = "ContractVO.FIND_ALL_CLIENT_WITH_CONTRACT_WITH_TIMERECORD_IN_PERIOD_SORTED";

    @Id
    @SequenceGenerator(name = "contratoshistorico_id_seq", sequenceName = "contratoshistorico_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contratoshistorico_id_seq")
    @Column(name = "id", updatable = false)
    private Integer id;
    private Integer numcontrato;
    private Integer numvariacion;
    private Integer tipovariacion;
    private Integer idcliente_gm;
    @Column(name = "clientegm_name", length = 100)
    private String clientegm_name;
    @Column(name = "contrato_ccc", length = 20)
    private String contrato_ccc;
    @Column(name = "idtrabajador", nullable = false)
    private Integer idtrabajador;
    @Column(name = "trabajador_name", length = 100)
    private String trabajador_name;
    @Column(name = "categoria", length = 250)
    private String categoria;
    @Column(name = "jor_trab", length = 100)
    private String jor_trab;
    @Column(name = "jor_trab_dias", length = 7)
    private String jor_trab_dias;
    @Column(name = "jor_tipo", length = 100)
    private String jor_tipo;
    @Column(name = "tipoctto", length = 100)
    private String tipoctto;
    private Date f_desde;
    private Date f_hasta;
    @Column(name = "id_ctto_inem", length = 30)
    private String id_ctto_inem;
    private Boolean envigor;
    private String notas_gestor;
    private String notas_privadas;
    @Column(name = "duracion", length = 1)
    private Character duracion;
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

    public Character getDuracion() {
        return duracion;
    }

    public void setDuracion(Character duracion) {
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


//    public static ContractVO.ContractBuilder create() {
//        return new ContractVO.ContractBuilder();
//    }
//
//    public static class ContractBuilder {
//
//        private Integer id;
//        private Integer numcontrato;
//        private Integer numvariacion;
//        private Integer tipovariacion;
//        private Integer idcliente_gm;
//        private String clientegm_name;
//        private String contrato_ccc;
//        private Integer idtrabajador;
//        private String trabajador_name;
//        private String categoria;
//        private String jor_trab;
//        private String jor_trab_dias;
//        private String jor_tipo;
//        private String tipoctto;
//        private Date f_desde;
//        private Date f_hasta;
//        private String id_ctto_inem;
//        private Boolean envigor;
//        private String notas_gestor;
//        private String notas_privadas;
//        private Character duracion;
//        private Integer subrogacion;
//        private Date idc;
//        private Date preavisofin;
//
//        public ContractVO.ContractBuilder withId(Integer id) {
//            this.id = id;
//            return this;
//        }
//
//        public ContractVO.ContractBuilder withContractNumber(Integer contractNumber) {
//            this.numcontrato = contractNumber;
//            return this;
//        }
//
//        public ContractVO.ContractBuilder withVariationNumber(Integer variationNumber) {
//            this.numvariacion = variationNumber;
//            return this;
//        }
//
//        public ContractVO.ContractBuilder withVariationType(Integer variationType) {
//            this.tipovariacion = variationType;
//            return this;
//        }
//
//        public ContractVO.ContractBuilder withClientGMId(Integer clientGMId) {
//            this.idcliente_gm = clientGMId;
//            return this;
//        }
//
//        public ContractVO.ContractBuilder withClientGMName(String clientGMName) {
//            this.clientegm_name = clientGMName;
//            return this;
//        }
//
//        public ContractVO.ContractBuilder withQuoteAccountCode(String quoteAccountCode) {
//            this.contrato_ccc = quoteAccountCode;
//            return this;
//        }
//
//        public ContractVO.ContractBuilder withWorkerId(Integer workerId) {
//            this.idtrabajador = workerId;
//            return this;
//        }
//
//        public ContractVO.ContractBuilder withWorkerName(String workerName) {
//            this.trabajador_name = workerName;
//            return this;
//        }
//
//        public ContractVO.ContractBuilder withLaborCategory(String laborCategory) {
//            this.categoria = laborCategory;
//            return this;
//        }
//
//        public ContractVO.ContractBuilder withWeeklyWorkHours(String weeklyWorkHours) {
//            this.jor_trab = weeklyWorkHours;
//            return this;
//        }
//
//        public ContractVO.ContractBuilder withDaysOfWeekToWork(String jor_trab_dias) {
//            this.jor_trab_dias = jor_trab_dias;
//            return this;
//        }
//
//        public ContractVO.ContractBuilder withFullPartialWorkday(String fullPartialWorkday) {
//            this.jor_tipo = fullPartialWorkday;
//            return this;
//        }
//
//        public ContractVO.ContractBuilder withTypeOfContract(String typeOfContract) {
//            this.tipoctto = typeOfContract;
//            return this;
//        }
//
//        public ContractVO.ContractBuilder withDateFrom(LocalDate dateFrom) {
//            this.f_desde = Date.valueOf(dateFrom);
//            return this;
//        }
//
//        public ContractVO.ContractBuilder withDateTo(LocalDate dateTo) {
//            this.f_hasta = Date.valueOf(dateTo);
//            return this;
//        }
//
//        public ContractVO.ContractBuilder withIdentificationContractNumberINEM(String identificationContractNumberINEM) {
//            this.id_ctto_inem = identificationContractNumberINEM;
//            return this;
//        }
//
//        public ContractVO.ContractBuilder withCurrentContract(Boolean currentContract) {
//            this.envigor = currentContract;
//            return this;
//        }
//
//        public ContractVO.ContractBuilder withNotesForManager(String notesForManager) {
//            this.notas_gestor = notesForManager;
//            return this;
//        }
//
//        public ContractVO.ContractBuilder withPrivateNotes(String privateNotes) {
//            this.notas_privadas = privateNotes;
//            return this;
//        }
//
//        public ContractVO.ContractBuilder withIndefiniteOrTemporalContract(Character indefiniteOrTemporalContract) {
//            this.duracion = indefiniteOrTemporalContract;
//            return this;
//        }
//
//        public ContractVO.ContractBuilder withSurrogateContract(Integer surrogateContract) {
//            this.subrogacion = surrogateContract;
//            return this;
//        }
//
//        public ContractVO.ContractBuilder withQuoteDataReportIDC(LocalDate quoteDataReportIDC) {
//            this.idc = Date.valueOf(quoteDataReportIDC);
//            return this;
//        }
//
//        public ContractVO.ContractBuilder withEndOfContractNotice(LocalDate endOfContractNotice) {
//            this.preavisofin = Date.valueOf(endOfContractNotice);
//            return this;
//        }
//
//        public ContractVO build() {
//            return new ContractVO(this.id, this.numcontrato, this.numvariacion, this.tipovariacion, this.idcliente_gm, this.clientegm_name,
//                    this.contrato_ccc, this.idtrabajador, this.trabajador_name, this.categoria, this.jor_trab, this.jor_trab_dias, this.jor_tipo,
//                    this.tipoctto, this.f_desde, this.f_hasta, this.id_ctto_inem, this.envigor, this.notas_gestor, this.notas_privadas,
//                    this.duracion, this.subrogacion, this.idc, this.preavisofin);
//        }
//    }
}
