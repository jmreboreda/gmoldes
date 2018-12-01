package gmoldes.domain.contract.mapper;

import gmoldes.components.contract.contract_variation.persistence.vo.ContractVariationVO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;

import java.sql.Date;


public class MapperContractNewVersionDTOtoContractVariationVO {

    public static ContractVariationVO map(ContractNewVersionDTO contractNewVersionDTO) {

        ContractVariationVO contractVariationVO = new ContractVariationVO();
        contractVariationVO.setId(contractNewVersionDTO.getId());
        contractVariationVO.setContractNumber(contractNewVersionDTO.getContractNumber());
        contractVariationVO.setVariationType(contractNewVersionDTO.getVariationType());
        contractVariationVO.setStartDate(Date.valueOf(contractNewVersionDTO.getStartDate()));
        Date expectedEndDate = contractNewVersionDTO.getExpectedEndDate() != null ? Date.valueOf(contractNewVersionDTO.getExpectedEndDate()) : null;
        contractVariationVO.setExpectedEndDate(expectedEndDate);
        Date modificationDate = contractNewVersionDTO.getModificationDate() != null ? Date.valueOf(contractNewVersionDTO.getModificationDate()) : null;
        contractVariationVO.setModificationDate(modificationDate);
        Date endingDate = contractNewVersionDTO.getEndingDate() != null ? Date.valueOf(contractNewVersionDTO.getEndingDate()) : null;
        contractVariationVO.setEndingDate(endingDate);
        contractVariationVO.setContractJsonData(contractNewVersionDTO.getContractJsonData());

        return contractVariationVO;
    }
}
