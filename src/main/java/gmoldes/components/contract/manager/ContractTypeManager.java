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

    public List<ContractTypeNewDTO> findAllContractTypes(){
//        ContractTypeDAO contractTypeDAO = ContractTypeDAO.ContractTypeDAOFactory.getInstance();
//        List<ContractTypeVO> contractTypeVOList = contractTypeDAO.findAllContractTypes();
//
        ContractTypeNewDAO contractTypeNewDAO = ContractTypeNewDAO.ContractTypeNewDAOFactory.getInstance();
        List<ContractTypeNewVO> contractTypeNewVOList = contractTypeNewDAO.findAllContractTypes();

        List<ContractTypeNewDTO> contractTypeNewDTOList = new ArrayList<>();
        for(ContractTypeNewVO contractTypeNewVO : contractTypeNewVOList){
            ContractTypeNewDTO contractTypeNewDTO = new ContractTypeNewDTO(
                    contractTypeNewVO.getId(),
                    contractTypeNewVO.getContractcode(),
                    contractTypeNewVO.getContractdescription(),
                    contractTypeNewVO.getColloquial(),
                    contractTypeNewVO.getIsinitialcontract(),
                    contractTypeNewVO.getIsTemporal(),
                    contractTypeNewVO.getIspartialtime(),
                    contractTypeNewVO.getIsfulltime(),
                    contractTypeNewVO.getIsMenuSelectable()
            );

            contractTypeNewDTOList.add(contractTypeNewDTO);
        }

        return contractTypeNewDTOList;
    }

    public ContractTypeNewDTO findContractTypeById(Integer contractTypeId){
        ContractTypeNewDAO contractTypeNewDAO = ContractTypeNewDAO.ContractTypeNewDAOFactory.getInstance();
        ContractTypeNewVO contractTypeNewVO = contractTypeNewDAO.findContractTypeById(contractTypeId);

        return new ContractTypeNewDTO(contractTypeNewVO.getId(),
                contractTypeNewVO.getContractcode(),
                contractTypeNewVO.getContractdescription(),
                contractTypeNewVO.getColloquial(),
                contractTypeNewVO.getIsinitialcontract(),
                contractTypeNewVO.getIsTemporal(),
                contractTypeNewVO.getIspartialtime(),
                contractTypeNewVO.getIsfulltime(),
                contractTypeNewVO.getIsMenuSelectable());
    }
}
