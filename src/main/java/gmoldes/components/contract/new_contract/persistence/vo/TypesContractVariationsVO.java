package gmoldes.components.contract.new_contract.persistence.vo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "typescontractvariations")
@NamedQueries(value = {
        @NamedQuery(
                name = TypesContractVariationsVO.FIND_TYPES_CONTRACT_VARIATIONS_BY_ID,
                query = "select p from TypesContractVariationsVO p where p.id_variation = :code"
        )
})

public class TypesContractVariationsVO implements Serializable {

    public static final String FIND_TYPES_CONTRACT_VARIATIONS_BY_ID = "TypesContractVariationsVO.FIND_TYPES_CONTRACT_VARIATIONS_BY_ID";

    @Id
    @SequenceGenerator(name = "typescontractvariations_id_seq", sequenceName = "typescontractvariations_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "typescontractvariations_id_seq")
    @Column(name = "id", updatable = false)
    private Integer id;
    private Integer id_variation;
    private String variation_description;

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
}
