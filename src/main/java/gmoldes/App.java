package gmoldes;


import gmoldes.components.contract.controllers.ContractController;
import gmoldes.components.initial_menu.InitialMenuController;
import gmoldes.domain.check.InitialChecks;
import gmoldes.domain.check.dto.IDCControlDTO;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.contract.dto.ContractDTO;
import gmoldes.domain.contract.dto.ContractNewVersionDTO;
import gmoldes.domain.contract.dto.InitialContractDTO;
import gmoldes.domain.person.Person;
import gmoldes.domain.person.dto.PersonDTO;
import gmoldes.domain.traceability_contract_documentation.dto.TraceabilityContractDocumentationDTO;
import gmoldes.utilities.Message;
import gmoldes.utilities.OldContractsToJSONUtility;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class App extends Application {

    public static final Integer END_OF_CONTRACT_NOTICE_DAYS = 20;

    @Override
    public void start(Stage primaryStage) throws Exception{

        /* Initial control processes */
        ProgressIndicator indicator = new ProgressIndicator();
        Scene initialScene = new Scene(indicator);
        primaryStage.setScene(initialScene);
        primaryStage.show();


        if(false) {
            OldContractsToJSONUtility ctJson = new OldContractsToJSONUtility();
            ctJson.oldContractToJsonGenerator();
        }


        initialControlProcesses(primaryStage);

        initialScene.getWindow().hide();

        /* Initial menu */
        InitialMenuController controller = new InitialMenuController();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Menú principal");
        Scene scene = new Scene(controller);
        scene.getStylesheets().add(App.class.getResource("/css_stylesheet/application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    public static void main( String[] args ){

        launch(args);
    }

    private void initialControlProcesses(Stage primaryStage) throws ParseException {

        updateContractsInForceInDatabase();
        //primaryStage.getScene().getWindow().hide();
        alertByContractExpiration(primaryStage);
        alertByContractNewVersionExpiration(primaryStage);
        //alertByNewContractExpiration(primaryStage);
        alertOfPendingIDC(primaryStage);
    }

    private void updateContractsInForceInDatabase(){
        InitialChecks.UpdateContractsInForce();
    }

    private void alertByContractExpiration(Stage primaryStage){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(gmoldes.utilities.Parameters.DEFAULT_DATE_FORMAT);
        String alert = "";
        String missingExceededText = "";
        List<ContractDTO> contractsExpiration = InitialChecks.contractExpirationControl();
        if(!contractsExpiration.isEmpty()) {
            for (ContractDTO contractDTO : contractsExpiration) {
                Integer daysForContractExpiration = Period.between(LocalDate.now(), contractDTO.getDateTo()).getDays();
                if(daysForContractExpiration >= 0){
                    missingExceededText = "Faltan ";
                }else{
                    missingExceededText = "Excedido en ";
                }
                alert = alert + "Preaviso del contrato de " + contractDTO.getWorkerName() + " con " + contractDTO.getClientGMName()
                        + ": vencimiento el día " + contractDTO.getDateTo().format(dateFormatter) + ". " + missingExceededText + Math.abs(daysForContractExpiration) + " días." + "\n\n";
            }

            Message.warningMessage(primaryStage.getOwner(), "Preavisos de fin de contrato pendientes de recepción", alert);
        }
    }

    private void alertByContractNewVersionExpiration(Stage primaryStage){

        String alertString = "";

        ApplicationMainController applicationMainController = new ApplicationMainController();
        List<TraceabilityContractDocumentationDTO>  traceabilityContractDocumentationDTOList = applicationMainController.findTraceabilityForAllContractWithPendingContractEndNotice();
        if(!traceabilityContractDocumentationDTOList.isEmpty()) {
            for (TraceabilityContractDocumentationDTO traceabilityDTO : traceabilityContractDocumentationDTOList) {
                if(ChronoUnit.DAYS.between(LocalDate.now(), traceabilityDTO.getExpectedEndDate()) <= 30){
                     InitialContractDTO initialContractDTO = applicationMainController.findInitialContractByContractNumber(traceabilityDTO.getContractNumber());
                     Integer clientGMId = initialContractDTO.getContractJsonData().getClientGMId();
                     ClientDTO clientDTO = applicationMainController.findClientById(clientGMId);
                     Integer workerId = initialContractDTO.getContractJsonData().getWorkerId();
                     PersonDTO workerDTO = applicationMainController.findPersonById(workerId);

                    System.out.println("Aquí tenemos uno de 30 días o menos: " + traceabilityDTO.getContractNumber() + " ::: " + clientDTO.getPersonOrCompanyName() + " ::: " + workerDTO.getApellidos());
                }
            }
        }



//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        String alert = "";
//        String missingExceededText = "";
//        ApplicationMainController applicationMainController = new ApplicationMainController();
//        List<ContractNewVersionDTO> contractNewVersionDTOList = applicationMainController.findAllTemporalContractInForceNow();
//        if(!contractNewVersionDTOList.isEmpty()) {
//            for (ContractNewVersionDTO contractNewVersionDTO : contractNewVersionDTOList) {
//                Integer daysForContractExpiration = Period.between(LocalDate.now(), contractNewVersionDTO.getExpectedEndDate()).getDays();
//                System.out.println(contractNewVersionDTO.getExpectedEndDate() + " - "+ LocalDate.now() + " = ¿"+ daysForContractExpiration + "? días");
//                if (daysForContractExpiration <= END_OF_CONTRACT_NOTICE_DAYS) {
//                    if(daysForContractExpiration - END_OF_CONTRACT_NOTICE_DAYS >= 0) {
//                        missingExceededText = "Faltan ";
//                    } else {
//                        missingExceededText = "Excedido en ";
//                    }
//
//                    PersonDTO worker = applicationMainController.findPersonById(contractNewVersionDTO.getContractJsonData().getWorkerId());
//                    ClientDTO client = applicationMainController.findClientById(contractNewVersionDTO.getContractJsonData().getClientGMId());
//                    alert = alert + "Preaviso del contrato de " + worker.getApellidos() + ", " + worker.getNom_rzsoc() + " con " + client.getPersonOrCompanyName()
//                            + ": vencimiento el día " + contractNewVersionDTO.getExpectedEndDate().format(formatter) + ". " + missingExceededText + Math.abs(daysForContractExpiration) + " días." + "\n\n";
//                }
//            }
//            if(!alert.isEmpty()) {
//                Message.warningMessage(primaryStage.getOwner(), "Preavisos de fin de contrato pendientes de recepción", alert);
//            }
//        }
    }


    private void alertOfPendingIDC(Stage primaryStage) throws ParseException {
        String alert = "";
        String missingExceededText = "";
        List<IDCControlDTO> idcControlDTOList = InitialChecks.findPendingQuoteDataReportIDC();
        if(!idcControlDTOList.isEmpty()){
            for(IDCControlDTO idcControlDTO : idcControlDTOList){
                if(idcControlDTO.getDays() >= 0){
                    missingExceededText = "Faltan ";
                }else{
                    missingExceededText = "Excedido en ";
                }
                int days = Math.abs(idcControlDTO.getDays());
                alert = alert + "IDC Pendiente: " + idcControlDTO.getDescr_variacion() + " de " + idcControlDTO.getTrabajador_name() + " con " +
                        idcControlDTO.getClientegm_name() + " desde " + idcControlDTO.getDate_to() + ". " + missingExceededText  + days + " días." + "\n\n";
            }

            Message.warningMessage(primaryStage.getOwner(), "IDC pendientes de recepción", alert);
        }
    }
}

