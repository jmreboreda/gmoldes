package gmoldes.domain.contract.mapper;

import gmoldes.components.contract.new_contract.persistence.vo.TypesContractVariationsVO;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;

public class MapperTypesContractVariationsVODTO {

    public static TypesContractVariationsDTO mapTypesContractVariationsVODTO(TypesContractVariationsVO typesContractVariationsVO){

       return TypesContractVariationsDTO.create()
               .withId(typesContractVariationsVO.getId())
               .withId_Variation(typesContractVariationsVO.getId_variation())
               .withVariationDescription(typesContractVariationsVO.getVariation_description())
               .build();
    }
}
