package gmoldes.domain.contract.mapper;

import gmoldes.domain.contract.dto.ContractDTO;
import gmoldes.domain.contract.persistence.vo.ContractVO;

import java.sql.Date;
import java.time.LocalDate;


public class MapperContractVODTO {

    public static ContractDTO map(ContractVO contractVO) {

        LocalDate expectedEndDate = contractVO.getExpectedEndDate() != null ? contractVO.getExpectedEndDate().toLocalDate() : null;
        LocalDate modificationDate = contractVO.getModificationDate() != null ? contractVO.getModificationDate().toLocalDate() : null;
        LocalDate endingDate = contractVO.getEndingDate() != null ? contractVO.getEndingDate().toLocalDate() : null;

        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setId(contractVO.getId());
        contractDTO.setEmployer(contractVO.getClientId());
        contractDTO.setEmployee(contractVO.getWorkerId());
        contractDTO.setContractType(contractVO.getContractType());
        contractDTO.setGmContractNumber(contractVO.getGmContractNumber());
        contractDTO.setVariationType(contractVO.getVariationType());
        contractDTO.setStartDate(contractVO.getStartDate().toLocalDate());
        contractDTO.setExpectedEndDate(expectedEndDate);
        contractDTO.setModificationDate(modificationDate);
        contractDTO.setEndingDate(endingDate);
        contractDTO.setContractScheduleJsonData(contractVO.getContractScheduleJsonData());
        contractDTO.setLaborCategory(contractVO.getLaborCategory());
        contractDTO.setQuoteAccountCode(contractVO.getQuoteAccountCode());
        contractDTO.setIdentificationContractNumberINEM(contractVO.getIdentificationContractNumberINEM());
        contractDTO.setPublicNotes(contractVO.getPublicNotes());
        contractDTO.setPrivateNotes(contractVO.getPrivateNotes());

        return contractDTO;
    }
}
