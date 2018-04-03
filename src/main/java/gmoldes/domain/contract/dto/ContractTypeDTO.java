package gmoldes.domain.contract.dto;

public class ContractTypeDTO {

    private Integer id;
    private Integer idtipocontrato;
    private String descripctto;

    public ContractTypeDTO(Integer id, Integer idtipocontrato, String descripctto) {
        this.id = id;
        this.idtipocontrato = idtipocontrato;
        this.descripctto = descripctto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdtipocontrato() {
        return idtipocontrato;
    }

    public void setIdtipocontrato(Integer idtipocontrato) {
        this.idtipocontrato = idtipocontrato;
    }

    public String getDescripctto() {
        return descripctto;
    }

    public void setDescripctto(String descripctto) {
        this.descripctto = descripctto;
    }

    public String toString(){
        return descripctto;
    }
}
