package gmoldes;

import gmoldes.components.contract.contract_variation.components.ContractVariationContractVariations;
import gmoldes.components.contract.contract_variation.persistence.dao.ContractVariationDAO;
import gmoldes.components.contract.contract_variation.persistence.vo.ContractVariationVO;
import gmoldes.components.contract.manager.TypesContractVariationsManager;
import gmoldes.components.contract.new_contract.persistence.dao.ContractDAO;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.dto.ContractFullDataDTO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract.dto.ContractVariationDTO;
import gmoldes.domain.contract.dto.TypesContractVariationsDTO;
import gmoldes.domain.contract.mapper.MapperContractVariationVODTO;
import gmoldes.domain.person.dto.PersonDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApplicationMainController {

    private ApplicationMainManager applicationMainManager = new ApplicationMainManager();


    public List<ClientDTO> findAllClientWithContractInForceAtDate(LocalDate date){

        return applicationMainManager.findAllClientWithContractInForceAtDate(date);
    }

    public List<ContractFullDataDTO> findAllDataForContractInForceAtDateByClientId(Integer clientId, LocalDate date){

        return applicationMainManager.findAllDataForContractInForceAtDateByClientId(clientId, date);
    }

    public List<ContractNewVersionDTO> findAllContractInForceInPeriod(LocalDate initialDate, LocalDate finalDate){

        return applicationMainManager.findAllContractInForceInPeriod(initialDate, finalDate);

    }

    public List<TypesContractVariationsDTO> findAllTypesContractVariations(){
        TypesContractVariationsManager manager= new TypesContractVariationsManager();

        return manager.findAllTypesContractVariations();
    }

    public ClientDTO findClientById(Integer clientId){

        return applicationMainManager.retrieveClientByClientId(clientId);
    }

    public PersonDTO findPersonById(Integer personId){

        return applicationMainManager.retrievePersonByPersonID(personId);

    }

    public List<ContractNewVersionDTO> findAllTemporalContractInForceNow() {

        return applicationMainManager.findAllTemporalContractInForceNow();
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

}
