package gmoldes;

import gmoldes.domain.types_contract_variations.persistence.dao.TypesContractVariationsDAO;
import gmoldes.domain.types_contract_variations.persistence.vo.TypesContractVariationsVO;
import gmoldes.domain.types_contract_variations.dto.TypesContractVariationsDTO;
import gmoldes.domain.contract.mapper.MapperTypesContractVariationsVODTO;

public class ApplicationMainManager {

    public TypesContractVariationsDTO retrieveTypesContractVariations(Integer variationType){
        TypesContractVariationsDAO typesContractVariationsDAO = TypesContractVariationsDAO.TypesContractVariationsDAOFactory.getInstance();
        TypesContractVariationsVO typesContractVariationsDTO = typesContractVariationsDAO.findTypesContractVariationsById(variationType);

        return MapperTypesContractVariationsVODTO.mapTypesContractVariationsVODTO(typesContractVariationsDTO);
    }
}
