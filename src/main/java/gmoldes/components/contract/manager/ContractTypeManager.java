package gmoldes.components.contract.manager;

import gmoldes.components.contract.new_contract.persistence.dao.ContractTypeNewDAO;
import gmoldes.components.contract.new_contract.persistence.vo.ContractTypeVO;
import gmoldes.domain.contract.dto.ContractTypeNewDTO;
import java.util.ArrayList;
import java.util.List;

public class ContractTypeManager {

    public ContractTypeManager() {
    }

    public List<ContractTypeNewDTO> findAllContractTypes(){
        ContractTypeNewDAO contractTypeNewDAO = ContractTypeNewDAO.ContractTypeNewDAOFactory.getInstance();
        List<ContractTypeVO> contractTypeVOList = contractTypeNewDAO.findAllContractTypes();

        List<ContractTypeNewDTO> contractTypeNewDTOList = new ArrayList<>();
        for(ContractTypeVO contractTypeVO : contractTypeVOList){
            ContractTypeNewDTO contractTypeNewDTO = new ContractTypeNewDTO(
                    contractTypeVO.getId(),
                    contractTypeVO.getContractcode(),
                    contractTypeVO.getContractdescription(),
                    contractTypeVO.getColloquial(),
                    contractTypeVO.getIsinitialcontract(),
                    contractTypeVO.getIsTemporal(),
                    contractTypeVO.getIspartialtime(),
                    contractTypeVO.getIsfulltime(),
                    contractTypeVO.getIsMenuSelectable()
            );

            contractTypeNewDTOList.add(contractTypeNewDTO);
        }

        return contractTypeNewDTOList;
    }

    public ContractTypeNewDTO findContractTypeById(Integer contractTypeId){
        ContractTypeNewDAO contractTypeNewDAO = ContractTypeNewDAO.ContractTypeNewDAOFactory.getInstance();
        ContractTypeVO contractTypeVO = contractTypeNewDAO.findContractTypeById(contractTypeId);

        return new ContractTypeNewDTO(contractTypeVO.getId(),
                contractTypeVO.getContractcode(),
                contractTypeVO.getContractdescription(),
                contractTypeVO.getColloquial(),
                contractTypeVO.getIsinitialcontract(),
                contractTypeVO.getIsTemporal(),
                contractTypeVO.getIspartialtime(),
                contractTypeVO.getIsfulltime(),
                contractTypeVO.getIsMenuSelectable());
    }
}
