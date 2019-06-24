package gmoldes.domain.contract.mapper;

import gmoldes.domain.types_contract_variations.persistence.vo.TypesContractVariationsVO;
import gmoldes.domain.types_contract_variations.dto.TypesContractVariationsDTO;

public class MapperTypesContractVariationsVODTO {

    public static TypesContractVariationsDTO mapTypesContractVariationsVODTO(TypesContractVariationsVO typesContractVariationsVO){

       return TypesContractVariationsDTO.create()
               .withId(typesContractVariationsVO.getId())
               .withId_Variation(typesContractVariationsVO.getId_variation())
               .withVariationDescription(typesContractVariationsVO.getVariation_description())
               .build();
    }
}
