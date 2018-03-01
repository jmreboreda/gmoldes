package gmoldes.check;

import gmoldes.controllers.ContractController;

import gmoldes.controllers.TypesContractVariationsController;
import gmoldes.domain.dto.ContractDTO;
import gmoldes.domain.dto.IDCControlDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InitialChecks {

    private static final Logger logger = LoggerFactory.getLogger(InitialChecks.class.getSimpleName());

    public static void UpdateCurrentContracts(){
        ContractController controller = new ContractController();

        int result = controller.establishCurrentContracts();
        logger.info("Current contract update to TRUE: " + result);
        int result1 = controller.establishNotCurrentContracts();
        logger.info("Current contract update to FALSE: " + result1);
    }

    public static List<ContractDTO> contractExpirationControl(){
        ContractController controller = new ContractController();

        return controller.findContractsExpiration();
    }

    public static List<IDCControlDTO> findPendingIDC() throws ParseException {
        Date now = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        List<IDCControlDTO> idcControlDTOList = new ArrayList<>();
        ContractController controller = new ContractController();
        List<ContractDTO> contractDTOList = controller.findPendingIDC();
        for(ContractDTO contractDTO : contractDTOList){
            IDCControlDTO idcControlDTO = new IDCControlDTO();
            idcControlDTO.setTrabajador_name(contractDTO.getWorkerName());
            idcControlDTO.setClientegm_name(contractDTO.getClientGMName());
            idcControlDTO.setDate_to(dateFormatter.format(contractDTO.getDateFrom()));
            int days = (int)(long)((contractDTO.getDateFrom().getTime() - now.getTime())/(24*60*60*1000));
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
