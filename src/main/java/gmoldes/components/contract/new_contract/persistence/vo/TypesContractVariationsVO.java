package gmoldes.components.contract.new_contract.persistence.vo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "typescontractvariations")
@NamedQueries(value = {
        @NamedQuery(
                name = TypesContractVariationsVO.FIND_TYPES_CONTRACT_VARIATIONS_BY_ID,
                query = "select p from TypesContractVariationsVO p where p.id_variation = :code"
        ),
        @NamedQuery(
                name = TypesContractVariationsVO.FIND_ALL_TYPES_CONTRACT_VARIATIONS,
                query = "select p from TypesContractVariationsVO p"
        )
})

public class TypesContractVariationsVO implements Serializable {

    public static final String FIND_TYPES_CONTRACT_VARIATIONS_BY_ID = "TypesContractVariationsVO.FIND_TYPES_CONTRACT_VARIATIONS_BY_ID";
    public static final String FIND_ALL_TYPES_CONTRACT_VARIATIONS = "TypesContractVariationsVO.FIND_ALL_TYPES_CONTRACT_VARIATIONS";


    @Id
    @SequenceGenerator(name = "typescontractvariations_id_seq", sequenceName = "typescontractvariations_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "typescontractvariations_id_seq")
    @Column(name = "id", updatable = false)
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

    public TypesContractVariationsVO() {
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
}
