package gmoldes.components.contract.manager;

import gmoldes.components.contract.persistence.dao.TypesContractVariationsDAO;
import gmoldes.components.contract.persistence.vo.TypesContractVariationsVO;

public class TypesContractVariationsManager {

    public TypesContractVariationsManager() {
    }

    public String findVariationDescriptionById(int variationId){
        TypesContractVariationsDAO typesContractVariationsDAO = TypesContractVariationsDAO.TypesContractVariationsDAOFactory.getInstance();

        TypesContractVariationsVO typesContractVariationVO = typesContractVariationsDAO.findVariationDescriptionById(variationId);

        return typesContractVariationVO.getVariation_description();
    }
}
