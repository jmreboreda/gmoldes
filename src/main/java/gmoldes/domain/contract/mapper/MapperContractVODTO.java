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
        contractDTO.setId(null);
        contractDTO.setEmployer(contractDTO.getEmployer());
        contractDTO.setEmployee(contractDTO.getEmployee());
        contractDTO.setContractType(contractDTO.getContractType());
        contractDTO.setGmContractNumber(contractDTO.getGmContractNumber());
        contractDTO.setVariationType(contractDTO.getVariationType());
        contractDTO.setStartDate(contractVO.getStartDate().toLocalDate());
        contractDTO.setExpectedEndDate(expectedEndDate);
        contractDTO.setModificationDate(modificationDate);
        contractDTO.setEndingDate(endingDate);
        contractDTO.setContractScheduleJsonData(contractDTO.getContractScheduleJsonData());
        contractDTO.setLaborCategory(contractDTO.getLaborCategory());
        contractDTO.setQuoteAccountCode(contractDTO.getQuoteAccountCode());
        contractDTO.setIdentificationContractNumberINEM(contractDTO.getIdentificationContractNumberINEM());
        contractDTO.setPublicNotes(contractDTO.getPublicNotes());
        contractDTO.setPrivateNotes(contractDTO.getPrivateNotes());

        return contractDTO;
    }
}
