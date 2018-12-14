package gmoldes.domain.check;

import gmoldes.ApplicationMainController;
import gmoldes.components.contract.controllers.ContractController;

import gmoldes.components.contract.controllers.TypesContractVariationsController;
import gmoldes.domain.check.dto.IDCControlDTO;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.dto.ContractDTO;
import gmoldes.domain.contract.dto.InitialContractDTO;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.traceability_contract_documentation.dto.TraceabilityContractDocumentationDTO;
import gmoldes.utilities.Message;
import gmoldes.utilities.Parameters;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class InitialChecks {

    private static final Logger logger = LoggerFactory.getLogger(InitialChecks.class.getSimpleName());
    private static final String CONTRACT_IN_FORCE_UPDATE_TO = "Contract in force update to ";

    public static final Integer END_OF_CONTRACT_NOTICE_DAYS = 20;

    public static void alertByContractNewVersionExpiration(Stage primaryStage){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(gmoldes.utilities.Parameters.DEFAULT_DATE_FORMAT);
        String alertMessage = "";
        String missingExceededText;

        ApplicationMainController applicationMainController = new ApplicationMainController();
        List<TraceabilityContractDocumentationDTO>  traceabilityContractDocumentationDTOList = applicationMainController.findTraceabilityForAllContractWithPendingContractEndNotice();
        if(!traceabilityContractDocumentationDTOList.isEmpty()) {
            for (TraceabilityContractDocumentationDTO traceabilityDTO : traceabilityContractDocumentationDTOList) {
                Long daysToEndDate = ChronoUnit.DAYS.between(LocalDate.now(), traceabilityDTO.getExpectedEndDate());
                if(daysToEndDate <= END_OF_CONTRACT_NOTICE_DAYS){

                    Integer contractNumber = traceabilityDTO.getContractNumber();
                    InitialContractDTO initialContractDTO = applicationMainController.findInitialContractByContractNumber(contractNumber);

                    Integer clientGMId = initialContractDTO.getContractJsonData().getClientGMId();
                    ClientDTO clientDTO = applicationMainController.findClientById(clientGMId);

                    Integer workerId = initialContractDTO.getContractJsonData().getWorkerId();
                    PersonDTO workerDTO = applicationMainController.findPersonById(workerId);

                    if(daysToEndDate >= 0){
                        missingExceededText = "Faltan ";
                    }else{
                        missingExceededText = "Excedido en ";
                    }

                    alertMessage = alertMessage +  "Preaviso de fin de contrato del contrato de " + clientDTO.getPersonOrCompanyName() + " con " + workerDTO.toString()
                            + ": vencimiento el día " + traceabilityDTO.getExpectedEndDate().format(dateFormatter) + ". " + missingExceededText + Math.abs(daysToEndDate) + " días." + "\n\n";
                }
            }

            if(!alertMessage.isEmpty()) {
                Message.warningMessage(primaryStage.getOwner(), "Preavisos de fin de contrato pendientes de recepción", alertMessage);
            }
        }
    }

















    public static void UpdateContractsInForce(){
        ContractController controller = new ContractController();

        int result = controller.establishContractsInForce();
        logger.info(CONTRACT_IN_FORCE_UPDATE_TO + "TRUE: " + result);
        int result1 = controller.establishContractsNotInForce();
        logger.info(CONTRACT_IN_FORCE_UPDATE_TO + "FALSE: " + result1);
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
