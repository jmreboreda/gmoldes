package gmoldes.domain.types_contract_variations.dto;

import java.io.Serializable;


public class TypesContractVariationsDTO implements Serializable {

    private Integer id;
    private Integer id_variation;
    private String variation_description;
    private Boolean extinction;
    private Boolean conversion;
    private Boolean special;
    private Boolean extension;
    private Boolean category;
    private Boolean initial;
    private Boolean reincorporation;
    private Boolean workingDay;

    public TypesContractVariationsDTO(Integer id,
                                      Integer id_variation,
                                      String variation_description,
                                      Boolean extinction,
                                      Boolean conversion,
                                      Boolean special,
                                      Boolean extension,
                                      Boolean category,
                                      Boolean initial,
                                      Boolean reincorporation,
                                      Boolean workingDay) {
        this.id = id;
        this.id_variation = id_variation;
        this.variation_description = variation_description;
        this.extinction = extinction;
        this.conversion = conversion;
        this.special = special;
        this.extension = extension;
        this.category = category;
        this.initial = initial;
        this.reincorporation = reincorporation;
        this.workingDay = workingDay;
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

    public Boolean getExtinction() {
        return extinction;
    }

    public void setExtinction(Boolean extinction) {
        this.extinction = extinction;
    }

    public Boolean getConversion() {
        return conversion;
    }

    public void setConversion(Boolean conversion) {
        this.conversion = conversion;
    }

    public Boolean getSpecial() {
        return special;
    }

    public void setSpecial(Boolean special) {
        this.special = special;
    }

    public Boolean getExtension() {
        return extension;
    }

    public void setExtension(Boolean extension) {
        this.extension = extension;
    }

    public Boolean getCategory() {
        return category;
    }

    public void setCategory(Boolean category) {
        this.category = category;
    }

    public Boolean getInitial() {
        return initial;
    }

    public void setInitial(Boolean initial) {
        this.initial = initial;
    }

    public Boolean getReincorporation() {
        return reincorporation;
    }

    public void setReincorporation(Boolean reincorporation) {
        this.reincorporation = reincorporation;
    }

    public Boolean getWorkingDay() {
        return workingDay;
    }

    public void setWorkingDay(Boolean workingDay) {
        this.workingDay = workingDay;
    }

    public String toString(){
        return getVariation_description();
    }

    public static TypesContractVariationsDTOBuilder create() {
        return new TypesContractVariationsDTOBuilder();
    }

    public static class TypesContractVariationsDTOBuilder {

        private Integer id;
        private Integer id_variation;
        private String variation_description;
        private Boolean extinction;
        private Boolean conversion;
        private Boolean special;
        private Boolean extension;
        private Boolean category;
        private Boolean initial;
        private Boolean reincorporation;
        private Boolean workingDay;

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

        public TypesContractVariationsDTOBuilder withExtinction(Boolean extinction) {
            this.extinction = extinction;
            return this;
        }

        public TypesContractVariationsDTOBuilder withSpecial(Boolean special) {
            this.special = special;
            return this;
        }

        public TypesContractVariationsDTOBuilder withConversion(Boolean conversion) {
            this.conversion = conversion;
            return this;
        }

        public TypesContractVariationsDTOBuilder withExtension(Boolean extension) {
            this.extension = extension;
            return this;
        }

        public TypesContractVariationsDTOBuilder withCategory(Boolean category) {
            this.category = category;
            return this;
        }

        public TypesContractVariationsDTOBuilder withInitial(Boolean initial) {
            this.initial = initial;
            return this;
        }

        public TypesContractVariationsDTOBuilder withReincorporation(Boolean reincorporation) {
            this.reincorporation = reincorporation;
            return this;
        }

        public TypesContractVariationsDTOBuilder withWorkingDay(Boolean workingDay) {
            this.workingDay = workingDay;
            return this;
        }



        public TypesContractVariationsDTO build() {
            return new TypesContractVariationsDTO(this.id, this.id_variation, this.variation_description, this.extinction, this.conversion, this.special, this.extension,
                    this.category, this.initial, this.reincorporation, this.workingDay);
        }
    }

}
