package gmoldes.components.contract.manager;

import gmoldes.domain.contract.dto.ContractTypeDTO;
import gmoldes.components.contract.persistence.dao.ContractTypeDAO;
import gmoldes.components.contract.persistence.vo.ContractTypeVO;

import java.util.ArrayList;
import java.util.List;

public class ContractTypeManager {

    public ContractTypeManager() {
    }

    public List<ContractTypeDTO> findAllContractTypes(){
        ContractTypeDAO contractTypeDAO = ContractTypeDAO.ContractTypeDAOFactory.getInstance();
        List<ContractTypeVO> contractTypeVOList = contractTypeDAO.findAllContractTypes();

        List<ContractTypeDTO> contractTypeDTOList = new ArrayList<>();
        for(ContractTypeVO contractTypeVO : contractTypeVOList){
            ContractTypeDTO contractTypeDTO = new ContractTypeDTO(
                    contractTypeVO.getId(),
                    contractTypeVO.getIdtipocontrato(),
                    contractTypeVO.getDescripctto());
            contractTypeDTOList.add(contractTypeDTO);
        }

        return contractTypeDTOList;
    }
}
