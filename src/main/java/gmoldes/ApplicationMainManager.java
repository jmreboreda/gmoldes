package gmoldes;

import gmoldes.components.contract.new_contract.persistence.dao.TypesContractVariationsDAO;
import gmoldes.components.contract.new_contract.persistence.vo.TypesContractVariationsVO;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;
import gmoldes.domain.contract.mapper.MapperTypesContractVariationsVODTO;

public class ApplicationMainManager {

    public TypesContractVariationsDTO retrieveTypesContractVariations(Integer variationType){
        TypesContractVariationsDAO typesContractVariationsDAO = TypesContractVariationsDAO.TypesContractVariationsDAOFactory.getInstance();
        TypesContractVariationsVO typesContractVariationsDTO = typesContractVariationsDAO.findTypesContractVariationsById(variationType);

        return MapperTypesContractVariationsVODTO.mapTypesContractVariationsVODTO(typesContractVariationsDTO);
    }
}
