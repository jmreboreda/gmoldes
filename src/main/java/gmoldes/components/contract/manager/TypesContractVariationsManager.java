package gmoldes.components.contract.manager;

import gmoldes.components.contract.new_contract.persistence.dao.TypesContractVariationsDAO;
import gmoldes.components.contract.new_contract.persistence.vo.TypesContractVariationsVO;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;

import java.util.ArrayList;
import java.util.List;

public class TypesContractVariationsManager {

    TypesContractVariationsDAO typesContractVariationsDAO = TypesContractVariationsDAO.TypesContractVariationsDAOFactory.getInstance();


    public TypesContractVariationsManager() {
    }

    public String findVariationDescriptionById(int variationId){
        //TypesContractVariationsDAO typesContractVariationsDAO = TypesContractVariationsDAO.TypesContractVariationsDAOFactory.getInstance();

        TypesContractVariationsVO typesContractVariationVO = typesContractVariationsDAO.findVariationDescriptionById(variationId);

        return typesContractVariationVO.getVariation_description();
    }

    public List<TypesContractVariationsDTO> findAllTypesContractVariations(){

        List<TypesContractVariationsDTO> typesContractVariationsDTOList = new ArrayList<>();

        List<TypesContractVariationsVO> typesContractVariationsVOList = typesContractVariationsDAO.findAllTypesContractVariations();
        for(TypesContractVariationsVO typesContractVariationsVO : typesContractVariationsVOList){
            TypesContractVariationsDTO typesContractVariationsDTO = TypesContractVariationsDTO.create()
                    .withId(typesContractVariationsVO.getId())
                    .withId_Variation(typesContractVariationsVO.getId_variation())
                    .withVariationDescription(typesContractVariationsVO.getVariation_description())
                    .withExtinction(typesContractVariationsVO.getExtinction())
                    .withConversion(typesContractVariationsVO.getConversion())
                    .withSpecial(typesContractVariationsVO.getSpecial())
                    .withExtension(typesContractVariationsVO.getExtension())
                    .withCategory(typesContractVariationsVO.getCategory())
                    .withInitial(typesContractVariationsVO.getInitial())
                    .withReincorporation(typesContractVariationsVO.getReincorporation())
                    .withWorkingDay(typesContractVariationsVO.getWorkingDay())
                    .build();
            typesContractVariationsDTOList.add(typesContractVariationsDTO);
        }

        return typesContractVariationsDTOList;
    }

    public TypesContractVariationsDTO findTypesContractVariationsById(Integer typesContractVariationId){

        TypesContractVariationsVO typesContractVariationsVO = typesContractVariationsDAO.findTypesContractVariationsById(typesContractVariationId);

        return TypesContractVariationsDTO.create()
                .withId(typesContractVariationsVO.getId())
                .withId_Variation(typesContractVariationsVO.getId_variation())
                .withVariationDescription(typesContractVariationsVO.getVariation_description())
                .withExtinction(typesContractVariationsVO.getExtinction())
                .withConversion(typesContractVariationsVO.getConversion())
                .withSpecial(typesContractVariationsVO.getSpecial())
                .withExtension(typesContractVariationsVO.getExtension())
                .withCategory(typesContractVariationsVO.getCategory())
                .withInitial(typesContractVariationsVO.getInitial())
                .withReincorporation(typesContractVariationsVO.getReincorporation())
                .withWorkingDay(typesContractVariationsVO.getWorkingDay())
                .build();
    }
}
