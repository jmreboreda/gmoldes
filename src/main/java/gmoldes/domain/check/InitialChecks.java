package gmoldes.domain.check;

import gmoldes.ApplicationConstants;
import gmoldes.ApplicationMainController;
import gmoldes.components.contract.controllers.ContractController;
import gmoldes.components.contract.controllers.TypesContractVariationsController;
import gmoldes.domain.check.dto.IDCControlDTO;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.dto.InitialContractDTO;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.traceability_contract_documentation.dto.TraceabilityContractDocumentationDTO;
import gmoldes.utilities.Message;
import gmoldes.utilities.Parameters;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class InitialChecks {

    private static final Logger logger = LoggerFactory.getLogger(InitialChecks.class.getSimpleName());
    private static final String CONTRACT_IN_FORCE_UPDATE_TO = "Contract in force update to ";

    public static void alertByContractNewVersionExpiration(Stage primaryStage){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);
        StringBuilder bodyMessage = new StringBuilder();
        StringBuilder alertMessage = new StringBuilder();
        alertMessage.append("Preavisos de fin de contrato:\n\n");
        Integer counter = 1;
        String missingExceededText;

        ApplicationMainController applicationMainController = new ApplicationMainController();
        List<TraceabilityContractDocumentationDTO>  traceabilityContractDocumentationDTOList = applicationMainController.findTraceabilityForAllContractWithPendingContractEndNotice();
        if(!traceabilityContractDocumentationDTOList.isEmpty()) {
            for (TraceabilityContractDocumentationDTO traceabilityDTO : traceabilityContractDocumentationDTOList) {
                Long contractDurationDays = ChronoUnit.DAYS.between(traceabilityDTO.getStartDate(), traceabilityDTO.getExpectedEndDate()) + 1;
                if(contractDurationDays >= Parameters.MINIMUM_NUMBER_DAYS_CONTRACT_DURATION_TO_SEND_NOTICE_END_CONTRACT) {
                    Long daysToEndDate = ChronoUnit.DAYS.between(LocalDate.now(), traceabilityDTO.getExpectedEndDate());
                    if (daysToEndDate <= CheckConstants.END_OF_CONTRACT_NOTICE_DAYS) {

                        Integer contractNumber = traceabilityDTO.getContractNumber();
                        InitialContractDTO initialContractDTO = applicationMainController.findInitialContractByContractNumber(contractNumber);

                        Integer clientGMId = initialContractDTO.getContractJsonData().getClientGMId();
                        ClientDTO clientDTO = applicationMainController.findClientById(clientGMId);

                        Integer workerId = initialContractDTO.getContractJsonData().getWorkerId();
                        PersonDTO workerDTO = applicationMainController.findPersonById(workerId);

                        if (daysToEndDate >= 0) {
                            missingExceededText = "Faltan ";
                        } else {
                            missingExceededText = "Excedido en ";
                        }

                        bodyMessage.append(counter).append(") ")
                                .append(clientDTO.toNaturalName()).append(" con ")
                                .append(workerDTO.toNaturalName())
                                .append(": vencimiento el día ").append(traceabilityDTO.getExpectedEndDate().format(dateFormatter)).append(".\n")
                                .append(missingExceededText).append(Math.abs(daysToEndDate)).append(" días.").append("\n\n");
                        counter++;
                    }

                }
            }

            alertMessage.append(bodyMessage);

            if(bodyMessage.length() > 0) {
                Message.warningMessage(primaryStage.getOwner(), CheckConstants.INITIAL_CHECK_HEADER_TEXT.concat("Preavisos de fin de contrato pendientes de recepción"), alertMessage.toString());
            }
        }
    }

    public static void alertOfVariationsOfWorkingDayScheduleWithEndDate(){

        ApplicationMainController applicationMainController = new ApplicationMainController();
        List<TraceabilityContractDocumentationDTO>  traceabilityContractDocumentationDTOList = applicationMainController.findTraceabilityForAllContractWithWorkingDayScheduleWithEndDate();

    }

    public static void alertByContractNewVersionWithPendingIDC(Stage primaryStage) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);
        List<IDCControlDTO> idcControlDTOList = new ArrayList<>();

        ApplicationMainController applicationMainController = new ApplicationMainController();
        List<TraceabilityContractDocumentationDTO> traceabilityContractDocumentationDTOList = applicationMainController.findTraceabilityForAllContractWithPendingIDC();
        for(TraceabilityContractDocumentationDTO traceabilityDTO : traceabilityContractDocumentationDTOList) {
            IDCControlDTO idcControlDTO = new IDCControlDTO();

            Integer contractNumber = traceabilityDTO.getContractNumber();
            InitialContractDTO initialContractDTO = applicationMainController.findInitialContractByContractNumber(contractNumber);

            Integer clientGMId = initialContractDTO.getContractJsonData().getClientGMId();
            ClientDTO clientDTO = applicationMainController.findClientById(clientGMId);

            Integer workerId = initialContractDTO.getContractJsonData().getWorkerId();
            PersonDTO workerDTO = applicationMainController.findPersonById(workerId);

            idcControlDTO.setWorkerFullName(workerDTO.toNaturalName());
            idcControlDTO.setClientGMFullName(clientDTO.toNaturalName());
            idcControlDTO.setDateTo(dateFormatter.format(traceabilityDTO.getStartDate()));
            Integer days = Period.between(LocalDate.now(), traceabilityDTO.getStartDate()).getDays();
            idcControlDTO.setDays(days);
            String variation_description = retrieveVariationDescriptionById(traceabilityDTO.getVariationType());
            idcControlDTO.setVariationDescription(variation_description);
            idcControlDTOList.add(idcControlDTO);
        }

        if(!idcControlDTOList.isEmpty()){

            StringBuilder alertMessage = new StringBuilder();
            String missingExceededText;

            for(IDCControlDTO idcControlDTO : idcControlDTOList) {
                if (idcControlDTO.getDays() <= 3) {
                    if (idcControlDTO.getDays() >= 0) {
                        missingExceededText = "Faltan ";
                    } else {
                        missingExceededText = "Excedido en ";
                    }

                    int days = Math.abs(idcControlDTO.getDays());

                    alertMessage.append("IDC pendiente por ").append(idcControlDTO.getVariationDescription().toLowerCase()).append(":\n")
                            .append("De ").append(idcControlDTO.getClientGMFullName())
                            .append(" con ").append(idcControlDTO.getWorkerFullName())
                            .append(" desde ").append(idcControlDTO.getDateTo()).append(".\n")
                            .append(missingExceededText).append(days).append(" días.").append("\n\n");
                }
            }

            if(alertMessage.length() > 0) {
                Message.warningMessage(primaryStage.getOwner(), CheckConstants.INITIAL_CHECK_HEADER_TEXT.concat("IDC pendientes de recepción"), alertMessage.toString());
            }
        }
    }

    public static void alertByDelaySendingLaborDocumentationToClients(Stage primaryStage){
        ApplicationMainController applicationMainController = new ApplicationMainController();
        StringBuilder alertMessage = new StringBuilder();

        List<TraceabilityContractDocumentationDTO> traceabilityContractDocumentationDTOList = applicationMainController.findTraceabilityForAllContractWithPendingLaborDocumentation();
        if(!traceabilityContractDocumentationDTOList.isEmpty()){
            for(TraceabilityContractDocumentationDTO traceabilityContractDocumentationDTO : traceabilityContractDocumentationDTOList){
                Long daysOfDocumentationDelay = ChronoUnit.DAYS.between(traceabilityContractDocumentationDTO.getStartDate(), LocalDate.now());
                Integer contractNumber = traceabilityContractDocumentationDTO.getContractNumber();
                if(daysOfDocumentationDelay >= CheckConstants.LIMIT_DAYS_DELAY_RECEIPT_CONTRACT_LABOR_DOCUMENTATION){
                    InitialContractDTO initialContractDTO = applicationMainController.findInitialContractByContractNumber(contractNumber);
                    ClientDTO clientDTO = applicationMainController.findClientById(initialContractDTO.getContractJsonData().getClientGMId());
                    PersonDTO workerDTO = applicationMainController.findPersonById(initialContractDTO.getContractJsonData().getWorkerId());
                    String variation_description = retrieveVariationDescriptionById(traceabilityContractDocumentationDTO.getVariationType());
                    alertMessage.append("Contrato número: ").append(contractNumber).append("\n");
                    alertMessage.append("Entre  ").append(clientDTO.toNaturalName());
                    alertMessage.append(" y ").append(workerDTO.toNaturalName()).append("\n");
                    alertMessage.append("Documentación: ").append(variation_description).append(".\n");

                    alertMessage.append("La documentación indicada está pendiente desde hace ").append(daysOfDocumentationDelay).append(" días.\n\n");
                }
            }

            if(alertMessage.length() > 0) {

                Message.warningMessage(primaryStage.getOwner(), CheckConstants.INITIAL_CHECK_HEADER_TEXT.concat("Documentación de contratos pendiente de recepción"), alertMessage.toString());
            }
        }
    }

    public static void UpdateOldContractVersionInForce(){
        ContractController controller = new ContractController();

        int result = controller.establishContractsInForce();
        logger.info(CONTRACT_IN_FORCE_UPDATE_TO + "TRUE: " + result);
        int result1 = controller.establishContractsNotInForce();
        logger.info(CONTRACT_IN_FORCE_UPDATE_TO + "FALSE: " + result1);
    }

    private static String retrieveVariationDescriptionById(int idVariation){
        TypesContractVariationsController controller = new TypesContractVariationsController();

        return controller.findVariationDescriptionById(idVariation);
    }
}
