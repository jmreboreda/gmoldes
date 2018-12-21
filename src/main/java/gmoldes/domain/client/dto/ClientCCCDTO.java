package gmoldes.domain.client.dto;

public class ClientCCCDTO {

    private Integer id;
    private Integer clientId;
    private String cccInss;

    public ClientCCCDTO(Integer id, Integer clientId, String cccInss) {
        this.id = id;
        this.clientId = clientId;
        this.cccInss = cccInss;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getCccInss() {
        return cccInss;
    }

    public void setCccInss(String cccInss) {
        this.cccInss = cccInss;
    }

    public String toString(){
        return cccInss;
    }

    public static ClientCCCDTOBuilder create() {
        return new ClientCCCDTOBuilder();
    }

    public static class ClientCCCDTOBuilder {

        private Integer id;
        private Integer clientId;
        private String cccInss;

        public ClientCCCDTOBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public ClientCCCDTOBuilder withClientId(Integer clientId) {
            this.clientId = clientId;
            return this;
        }

        public ClientCCCDTOBuilder withCccInss(String cccInss) {
            this.cccInss = cccInss;
            return this;
        }

        public ClientCCCDTO build() {
            return new ClientCCCDTO(this.id, this.clientId, this.cccInss);
        }
    }
}
