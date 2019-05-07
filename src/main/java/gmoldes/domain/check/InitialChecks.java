package gmoldes.domain.check;

import gmoldes.ApplicationConstants;
import gmoldes.components.contract.contract_variation.controllers.ContractVariationController;
import gmoldes.components.contract.controllers.TypesContractVariationsController;
import gmoldes.domain.check.dto.IDCControlDTO;
import gmoldes.domain.client.ClientService;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.ContractService;
import gmoldes.domain.contract.dto.ContractVariationDTO;
import gmoldes.domain.contract.dto.InitialContractDTO;
import gmoldes.domain.person.PersonService;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.traceability_contract_documentation.controllers.TraceabilityContractDocumentationController;
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
    private static final Integer DAYS_OF_NOTICE_FOR_END_OF_WEEKLY_WORK_DAY = 10;

    public static void alertByContractNewVersionExpiration(Stage primaryStage){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);
        StringBuilder bodyMessage = new StringBuilder();
        StringBuilder alertMessage = new StringBuilder();
        alertMessage.append("Preavisos de fin de contrato:\n\n");
        Integer counter = 1;
        String missingExceededText;

        TraceabilityContractDocumentationController traceabilityController = new TraceabilityContractDocumentationController();
        List<TraceabilityContractDocumentationDTO>  traceabilityContractDocumentationDTOList = traceabilityController.findTraceabilityForAllContractWithPendingContractEndNotice();
        if(!traceabilityContractDocumentationDTOList.isEmpty()) {
            for (TraceabilityContractDocumentationDTO traceabilityDTO : traceabilityContractDocumentationDTOList) {
                Long contractDurationDays = ChronoUnit.DAYS.between(traceabilityDTO.getStartDate(), traceabilityDTO.getExpectedEndDate()) + 1;
                if(contractDurationDays >= Parameters.MINIMUM_NUMBER_DAYS_CONTRACT_DURATION_TO_SEND_NOTICE_END_CONTRACT) {
                    Long daysToEndDate = ChronoUnit.DAYS.between(LocalDate.now(), traceabilityDTO.getExpectedEndDate());
                    if (daysToEndDate <= CheckConstants.END_OF_CONTRACT_NOTICE_DAYS) {

                        Integer contractNumber = traceabilityDTO.getContractNumber();
                        ContractService contractService = ContractService.ContractServiceFactory.getInstance();
                        InitialContractDTO initialContractDTO = contractService.findInitialContractByContractNumber(contractNumber);

                        Integer clientGMId = initialContractDTO.getContractJsonData().getClientGMId();
                        ClientService clientService = ClientService.ClientServiceFactory.getInstance();
                        ClientDTO clientDTO = clientService.findClientById(clientGMId);

                        Integer workerId = initialContractDTO.getContractJsonData().getWorkerId();
                        PersonService personService = PersonService.PersonServiceFactory.getInstance();
                        PersonDTO workerDTO = personService.findPersonById(workerId);

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

    public static void alertOfWeeklyOfWorkingDayScheduleWithEndDate(Stage primaryStage){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);
        StringBuilder bodyMessage = new StringBuilder();
        StringBuilder alertMessage = new StringBuilder();
        alertMessage.append("Variaciones de jornada de trabajo con fecha de finalización:\n\n");
        Integer counter = 1;
        String missingExceededText;

        ContractVariationController contractVariationController = new ContractVariationController();
        Integer contractVariationId = 230;
        List<ContractVariationDTO> contractVariationDTOList = contractVariationController.findAllContractVariationById(contractVariationId);

        for(ContractVariationDTO contractVariationDTO : contractVariationDTOList){
            if(contractVariationDTO.getExpectedEndDate() != null && contractVariationDTO.getModificationDate() == null) {
                Long daysFromTodayToExpiration = ChronoUnit.DAYS.between(LocalDate.now(), contractVariationDTO.getExpectedEndDate());
                if (daysFromTodayToExpiration <= DAYS_OF_NOTICE_FOR_END_OF_WEEKLY_WORK_DAY) {
                    Integer contractNumber = contractVariationDTO.getContractNumber();
                    ContractService contractService = ContractService.ContractServiceFactory.getInstance();
                    InitialContractDTO initialContractDTO = contractService.findInitialContractByContractNumber(contractNumber);

                    Integer clientGMId = initialContractDTO.getContractJsonData().getClientGMId();
                    ClientService clientService = ClientService.ClientServiceFactory.getInstance();
                    ClientDTO clientDTO = clientService.findClientById(clientGMId);

                    Integer workerId = initialContractDTO.getContractJsonData().getWorkerId();
                    PersonService personService = PersonService.PersonServiceFactory.getInstance();
                    PersonDTO workerDTO = personService.findPersonById(workerId);

                    if(daysFromTodayToExpiration >= 0){
                       missingExceededText = "Faltan ";
                    }else{
                        missingExceededText = "Excedido en ";
                    }

                    bodyMessage.append(counter).append(") ")
                            .append(clientDTO.toNaturalName()).append(" con ")
                            .append(workerDTO.toNaturalName())
                            .append(": vencimiento el día ").append(contractVariationDTO.getExpectedEndDate().format(dateFormatter)).append(".\n")
                            .append(missingExceededText).append(Math.abs(daysFromTodayToExpiration)).append(" días.").append("\n\n");
                    counter++;

                    System.out.println("Contrato " + contractVariationDTO.getContractNumber() + ": variación de jornada a " + ChronoUnit.DAYS.between(LocalDate.now(), contractVariationDTO.getExpectedEndDate()) + " días de extinguirse ...");
                }
            }
        }

        alertMessage.append(bodyMessage);

        if(bodyMessage.length() > 0) {
            Message.warningMessage(primaryStage.getOwner(), CheckConstants.INITIAL_CHECK_HEADER_TEXT.concat("Variaciones de jornada de trabajo con fecha de finalización"), alertMessage.toString());
        }
    }

    public static void alertByContractNewVersionWithPendingIDC(Stage primaryStage) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DEFAULT_DATE_FORMAT);
        List<IDCControlDTO> idcControlDTOList = new ArrayList<>();

        TraceabilityContractDocumentationController traceabilityController = new TraceabilityContractDocumentationController();
        List<TraceabilityContractDocumentationDTO> traceabilityContractDocumentationDTOList = traceabilityController.findTraceabilityForAllContractWithPendingIDC();
        for(TraceabilityContractDocumentationDTO traceabilityDTO : traceabilityContractDocumentationDTOList) {
            IDCControlDTO idcControlDTO = new IDCControlDTO();

            Integer contractNumber = traceabilityDTO.getContractNumber();
            ContractService contractService = ContractService.ContractServiceFactory.getInstance();
            InitialContractDTO initialContractDTO = contractService.findInitialContractByContractNumber(contractNumber);

            Integer clientGMId = initialContractDTO.getContractJsonData().getClientGMId();
            ClientService clientService = ClientService.ClientServiceFactory.getInstance();
            ClientDTO clientDTO = clientService.findClientById(clientGMId);

            Integer workerId = initialContractDTO.getContractJsonData().getWorkerId();
            PersonService personService = PersonService.PersonServiceFactory.getInstance();
            PersonDTO workerDTO = personService.findPersonById(workerId);

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
        TraceabilityContractDocumentationController traceabilityController = new TraceabilityContractDocumentationController();
        StringBuilder alertMessage = new StringBuilder();

        List<TraceabilityContractDocumentationDTO> traceabilityContractDocumentationDTOList = traceabilityController.findTraceabilityForAllContractWithPendingLaborDocumentation();
        if(!traceabilityContractDocumentationDTOList.isEmpty()){
            for(TraceabilityContractDocumentationDTO traceabilityContractDocumentationDTO : traceabilityContractDocumentationDTOList){
                Long daysOfDocumentationDelay = ChronoUnit.DAYS.between(traceabilityContractDocumentationDTO.getStartDate(), LocalDate.now());
                Integer contractNumber = traceabilityContractDocumentationDTO.getContractNumber();
                if(daysOfDocumentationDelay >= CheckConstants.LIMIT_DAYS_DELAY_RECEIPT_CONTRACT_LABOR_DOCUMENTATION){
                    ContractService contractService = ContractService.ContractServiceFactory.getInstance();
                    InitialContractDTO initialContractDTO = contractService.findInitialContractByContractNumber(contractNumber);

                    ClientService clientService = ClientService.ClientServiceFactory.getInstance();
                    ClientDTO clientDTO = clientService.findClientById(initialContractDTO.getContractJsonData().getClientGMId());

                    PersonService personService = PersonService.PersonServiceFactory.getInstance();
                    PersonDTO workerDTO = personService.findPersonById(initialContractDTO.getContractJsonData().getWorkerId());

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

    private static String retrieveVariationDescriptionById(int idVariation){
        TypesContractVariationsController controller = new TypesContractVariationsController();

        return controller.findVariationDescriptionById(idVariation);
    }
}
