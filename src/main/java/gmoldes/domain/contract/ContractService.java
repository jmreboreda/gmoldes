package gmoldes.domain.contract;

import gmoldes.components.contract.contract_variation.persistence.dao.ContractVariationDAO;
import gmoldes.components.contract.contract_variation.persistence.vo.ContractVariationVO;
import gmoldes.components.contract.controllers.ContractController;
import gmoldes.components.contract.initial_contract.persistence.dao.InitialContractDAO;
import gmoldes.components.contract.initial_contract.persistence.vo.InitialContractVO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract.dto.InitialContractDTO;
import gmoldes.domain.contract.mapper.MapperContractVariationVOtoContractNewVersionDTO;
import gmoldes.domain.contract.mapper.MapperInitialContractVOtoContractNewVersionDTO;

import java.util.List;

public class ContractService {

    private ContractService() {
    }

    private static ContractController contractController = new ContractController();

    public static InitialContractDTO findInitialContractByContractNumber(Integer contractNumber){

        return contractController.findInitialContractByContractNumber(contractNumber);
    }

    public static List<ContractNewVersionDTO> findHistoryOfContractByContractNumber(Integer contractNumber){

        List<ContractNewVersionDTO> contractNewVersionDTOList = contractController.findHistoryOfContractByContractNumber(contractNumber);

        InitialContractDAO initialContractDAO =  InitialContractDAO.InitialContractDAOFactory.getInstance();
        InitialContractVO initialContractVO = initialContractDAO.findInitialContractByContractNumber(contractNumber);

        ContractVariationDAO contractVariationDAO = ContractVariationDAO.ContractVariationDAOFactory.getInstance();
        List<ContractVariationVO> contractVariationVOList = contractVariationDAO.findAllContractVariationByContractNumber(contractNumber);

        contractNewVersionDTOList.add(MapperInitialContractVOtoContractNewVersionDTO.map(initialContractVO));
        for(ContractVariationVO contractVariationVO : contractVariationVOList){
            contractNewVersionDTOList.add(MapperContractVariationVOtoContractNewVersionDTO.map(contractVariationVO));
        }

        return contractNewVersionDTOList;
    }
}
