package gmoldes;

import gmoldes.components.contract.contract_variation.persistence.dao.ContractVariationDAO;
import gmoldes.components.contract.contract_variation.persistence.vo.ContractVariationVO;
import gmoldes.components.contract.initial_contract.persistence.dao.InitialContractDAO;
import gmoldes.components.contract.initial_contract.persistence.vo.InitialContractVO;
import gmoldes.components.contract.manager.TypesContractVariationsManager;
import gmoldes.domain.client.dto.ClientDTOOk;
import gmoldes.domain.contract.dto.*;
import gmoldes.domain.contract.mapper.MapperContractVariationVODTO;
import gmoldes.domain.contract.mapper.MapperContractVariationVOtoContractNewVersionDTO;
import gmoldes.domain.contract.mapper.MapperInitialContractVODTO;
import gmoldes.domain.contract.mapper.MapperInitialContractVOtoContractNewVersionDTO;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.traceability_contract_documentation.dto.TraceabilityContractDocumentationDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApplicationMainController {

    private ApplicationMainManager applicationMainManager = new ApplicationMainManager();

    public List<ContractNewVersionDTO> findHistoryOfContractByContractNumber(Integer contractNumber){

        List<ContractNewVersionDTO> contractNewVersionDTOList = new ArrayList<>();

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


    public List<ClientDTOOk> findAllClientWithContractInForceAtDate(LocalDate date){

        return applicationMainManager.findAllClientWithContractInForceAtDate(date);
    }

    public List<ContractFullDataDTO> findAllDataForContractInForceAtDateByClientId(Integer clientId, LocalDate date){

        return applicationMainManager.findAllDataForContractInForceAtDateByClientId(clientId, date);
    }

    public List<ContractNewVersionDTO> findAllContractInForceInPeriod(LocalDate initialDate, LocalDate finalDate){

        return applicationMainManager.findAllContractInForceInPeriod(initialDate, finalDate);

    }

    public List<ClientDTOOk> findAllClientGMWithInvoiceInForceInPeriod(LocalDate initialDate, LocalDate finalDate){
        return applicationMainManager.findAllClientGMWithInvoiceInForceInPeriod(initialDate, finalDate);
    }

    public List<TypesContractVariationsDTO> findAllTypesContractVariations(){
        TypesContractVariationsManager manager= new TypesContractVariationsManager();

        return manager.findAllTypesContractVariations();
    }

    public ClientDTOOk findClientById(Integer clientId){

        return applicationMainManager.retrieveClientByClientId(clientId);
    }

    public PersonDTO findPersonById(Integer personId){

        return applicationMainManager.retrievePersonByPersonID(personId);

    }

    public List<ContractNewVersionDTO> findAllTemporalContractInForceNow() {

        return applicationMainManager.findAllTemporalContractInForceNow();
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

    public List<TraceabilityContractDocumentationDTO> findTraceabilityForAllContractWithPendingIDC(){

        return applicationMainManager.findTraceabilityForAllContractWithPendingIDC();
    }

}
