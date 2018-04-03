package gmoldes.domain.client.dto;

public class ClientCCCDTO {

    private Integer id;
    private Integer idcliente;
    private String ccc_inss;

    public ClientCCCDTO(Integer id, Integer idcliente, String ccc_inss) {
        this.id = id;
        this.idcliente = idcliente;
        this.ccc_inss = ccc_inss;
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

    public String getCcc_inss() {
        return ccc_inss;
    }

    public void setCcc_inss(String ccc_inss) {
        this.ccc_inss = ccc_inss;
    }

    public String toString(){
        return ccc_inss;
    }
}
