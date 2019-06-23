package gmoldes.domain.contract;

import gmoldes.components.contract.controllers.ContractTypeController;
import gmoldes.domain.contract.dto.ContractTypeDTO;

import java.util.List;

public class ContractTypeService {

    private ContractTypeService() {
    }

    public static class ContractTypeServiceFactory {

        private static ContractTypeService contractTypeService;

        public static ContractTypeService getInstance() {
            if(contractTypeService == null) {
                contractTypeService = new ContractTypeService();
            }

            return contractTypeService;
        }
    }

    private static ContractTypeController contractTypeController = new ContractTypeController();

    public List<ContractTypeDTO> findAllContractType(){

        return contractTypeController.findAllSelectableContractTypes();
    }

    public ContractTypeDTO findContractTypeByContractTypeCode(Integer contractTypeCode){

        return contractTypeController.findContractTypeByContractTypeCode(contractTypeCode);
    }
}
