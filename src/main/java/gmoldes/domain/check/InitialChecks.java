package gmoldes.domain.check;

import gmoldes.components.contract.controllers.ContractController;

import gmoldes.components.contract.controllers.TypesContractVariationsController;
import gmoldes.domain.check.dto.IDCControlDTO;
import gmoldes.domain.contract.dto.ContractDTO;
import gmoldes.utilities.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class InitialChecks {

    private static final Logger logger = LoggerFactory.getLogger(InitialChecks.class.getSimpleName());
    private static final String CURRENT_CONTRACT_UPDATE_TO = "Current contract update to ";

    public static void UpdateCurrentContracts(){
        ContractController controller = new ContractController();

        int result = controller.establishCurrentContracts();
        logger.info(CURRENT_CONTRACT_UPDATE_TO + "TRUE: " + result);
        int result1 = controller.establishNotCurrentContracts();
        logger.info(CURRENT_CONTRACT_UPDATE_TO + "FALSE: " + result1);
    }

    public static List<ContractDTO> contractExpirationControl(){
        ContractController controller = new ContractController();

        return controller.findContractsExpiration();
    }

    public static List<IDCControlDTO> findPendingQuoteDataReportIDC() throws ParseException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parameters.DEFAULT_DATE_FORMAT);
        List<IDCControlDTO> idcControlDTOList = new ArrayList<>();
        ContractController controller = new ContractController();
        List<ContractDTO> contractDTOList = controller.findPendingIDC();
        for(ContractDTO contractDTO : contractDTOList){
            IDCControlDTO idcControlDTO = new IDCControlDTO();
            idcControlDTO.setTrabajador_name(contractDTO.getWorkerName());
            idcControlDTO.setClientegm_name(contractDTO.getClientGMName());
            idcControlDTO.setDate_to(dateFormatter.format(contractDTO.getDateFrom()));
            Integer days = Period.between(LocalDate.now(), contractDTO.getDateFrom()).getDays();
            idcControlDTO.setDays(days);
            String variation_description = retrieveVariationDescriptionById(contractDTO.getVariationType());
            idcControlDTO.setDescr_variacion(variation_description);
            idcControlDTOList.add(idcControlDTO);
        }

        return idcControlDTOList;
    }

    public static String retrieveVariationDescriptionById(int idVariation){
        TypesContractVariationsController controller = new TypesContractVariationsController();

        return controller.findVariationDescriptionById(idVariation);
    }
}
