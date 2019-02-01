package gmoldes;

import gmoldes.components.contract.contract_variation.persistence.dao.ContractVariationDAO;
import gmoldes.components.contract.contract_variation.persistence.vo.ContractVariationVO;
import gmoldes.components.contract.initial_contract.persistence.dao.InitialContractDAO;
import gmoldes.components.contract.initial_contract.persistence.vo.InitialContractVO;
import gmoldes.components.contract.manager.TypesContractVariationsManager;
import gmoldes.domain.contract.ContractService;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract.dto.ContractVariationDTO;
import gmoldes.domain.contract.dto.InitialContractDTO;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;
import gmoldes.domain.contract.mapper.MapperContractVariationVODTO;
import gmoldes.domain.contract.mapper.MapperInitialContractVODTO;
import gmoldes.domain.traceability_contract_documentation.dto.TraceabilityContractDocumentationDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApplicationMainController {

    private ApplicationMainManager applicationMainManager = new ApplicationMainManager();

    public List<ContractNewVersionDTO> findAllContractInForceInPeriod(LocalDate initialDate, LocalDate finalDate){

        return ContractService.findAllContractInForceInPeriod(initialDate, finalDate);

    }

    public List<TypesContractVariationsDTO> findAllTypesContractVariations(){
        TypesContractVariationsManager manager= new TypesContractVariationsManager();

        return manager.findAllTypesContractVariations();
    }


    public TypesContractVariationsDTO findTypeContractVariationById(Integer typeContractVariationId){

        return applicationMainManager.retrieveTypesContractVariations(typeContractVariationId);
    }

    public InitialContractDTO findInitialContractByContractNumber(Integer selectedContractNumber){

        InitialContractDAO initialContractDAO = InitialContractDAO.InitialContractDAOFactory.getInstance();
        InitialContractVO initialContractVO = initialContractDAO.findInitialContractByContractNumber(selectedContractNumber);

        return MapperInitialContractVODTO.map(initialContractVO);
    }


    public List<ContractVariationDTO> findAllContractVariationByContractNumber(Integer selectedContractNumber){

        List<ContractVariationDTO> contractVariationDTOList = new ArrayList<>();

        ContractVariationDAO contractVariationDAO = ContractVariationDAO.ContractVariationDAOFactory.getInstance();
        List<ContractVariationVO> contractVariationVOList = contractVariationDAO.findAllContractVariationByContractNumber(selectedContractNumber);
        for(ContractVariationVO contractVariationVO : contractVariationVOList){
            ContractVariationDTO contractVariationDTO = MapperContractVariationVODTO.map(contractVariationVO);
            contractVariationDTOList.add(contractVariationDTO);
        }

        return contractVariationDTOList;
    }

    public List<TraceabilityContractDocumentationDTO> findTraceabilityForAllContractWithPendingContractEndNotice(){

        return applicationMainManager.findTraceabilityForAllContractWithPendingContractEndNotice();
    }

    public List<TraceabilityContractDocumentationDTO> findTraceabilityForAllContractWithWorkingDayScheduleWithEndDate(){
        return applicationMainManager.findTraceabilityForAllContractWithWorkingDayScheduleWithEndDate();
    }

    public List<TraceabilityContractDocumentationDTO> findTraceabilityForAllContractWithPendingIDC(){

        return applicationMainManager.findTraceabilityForAllContractWithPendingIDC();
    }

    public List<TraceabilityContractDocumentationDTO> findTraceabilityForAllContractWithPendingLaborDocumentation(){

        return applicationMainManager.findTraceabilityForAllContractWithPendingLaborDocumentation();

    }

}
