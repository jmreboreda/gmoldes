package gmoldes.components.contract.manager;

import gmoldes.components.contract.new_contract.persistence.dao.ContractTypeNewDAO;
import gmoldes.components.contract.new_contract.persistence.vo.ContractTypeNewVO;
import gmoldes.domain.contract.dto.ContractTypeDTO;
import gmoldes.components.contract.new_contract.persistence.dao.ContractTypeDAO;
import gmoldes.components.contract.new_contract.persistence.vo.ContractTypeVO;
import gmoldes.domain.contract.dto.ContractTypeNewDTO;

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

    public ContractTypeNewDTO findContractTypeById(Integer contractTypeId){
        ContractTypeNewDAO contractTypeNewDAO = ContractTypeNewDAO.ContractTypeDAOFactory.getInstance();
        ContractTypeNewVO contractTypeNewVO = contractTypeNewDAO.findContractTypeById(contractTypeId);

        return new ContractTypeNewDTO(contractTypeNewVO.getId(),
                contractTypeNewVO.getContractcode(),
                contractTypeNewVO.getContractdescription(),
                contractTypeNewVO.getColloquial(),
                contractTypeNewVO.getIsinitialcontract(),
                contractTypeNewVO.getIspartialtime(),
                contractTypeNewVO.getIsfulltime());
    }
}
