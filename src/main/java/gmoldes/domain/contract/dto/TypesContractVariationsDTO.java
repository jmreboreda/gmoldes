package gmoldes.domain.contract.dto;

import java.io.Serializable;


public class TypesContractVariationsDTO implements Serializable {

    private Integer id;
    private Integer id_variation;
    private String variation_description;

    public TypesContractVariationsDTO(Integer id,
                                      Integer id_variation,
                                      String variation_description) {
        this.id = id;
        this.id_variation = id_variation;
        this.variation_description = variation_description;
    }

    public TypesContractVariationsDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_variation() {
        return id_variation;
    }

    public void setId_variation(Integer id_variation) {
        this.id_variation = id_variation;
    }

    public String getVariation_description() {
        return variation_description;
    }

    public void setVariation_description(String variation_description) {
        this.variation_description = variation_description;
    }

    public static TypesContractVariationsDTOBuilder create() {
        return new TypesContractVariationsDTOBuilder();
    }

    public static class TypesContractVariationsDTOBuilder {

        private Integer id;
        private Integer id_variation;
        private String variation_description;

        public TypesContractVariationsDTOBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public TypesContractVariationsDTOBuilder withId_Variation(Integer id_variation) {
            this.id_variation = id_variation;
            return this;
        }

        public TypesContractVariationsDTOBuilder withVariationDescription(String variation_description) {
            this.variation_description = variation_description;
            return this;
        }

        public TypesContractVariationsDTO build() {
            return new TypesContractVariationsDTO(this.id, this.id_variation, this.variation_description);
        }
    }

}
