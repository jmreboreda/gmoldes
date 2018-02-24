package gmoldes.manager;

import gmoldes.domain.dto.ContractTypeDTO;
import gmoldes.persistence.dao.ContractTypeDAO;
import gmoldes.persistence.dao.TypesContractVariationsDAO;
import gmoldes.persistence.vo.ContractTypeVO;
import gmoldes.persistence.vo.TypesContractVariationsVO;

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
