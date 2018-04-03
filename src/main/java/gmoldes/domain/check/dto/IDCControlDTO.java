package gmoldes.domain.check.dto;

public class IDCControlDTO {

    private String descr_variacion;
    private String trabajador_name;
    private String clientegm_name;
    private String date_to;
    private Integer days;

    public IDCControlDTO() {
    }

    public String getDescr_variacion() {
        return descr_variacion;
    }

    public void setDescr_variacion(String descr_variacion) {
        this.descr_variacion = descr_variacion;
    }

    public String getTrabajador_name() {
        return trabajador_name;
    }

    public void setTrabajador_name(String trabajador_name) {
        this.trabajador_name = trabajador_name;
    }

    public String getClientegm_name() {
        return clientegm_name;
    }

    public void setClientegm_name(String clientegm_name) {
        this.clientegm_name = clientegm_name;
    }

    public String getDate_to() {
        return date_to;
    }

    public void setDate_to(String date_to) {
        this.date_to = date_to;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
