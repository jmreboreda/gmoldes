package gmoldes;


import gmoldes.components.initial_menu.InitialMenuController;
import gmoldes.domain.check.InitialChecks;
import gmoldes.domain.check.dto.IDCControlDTO;
import gmoldes.domain.contract.dto.ContractDTO;
import gmoldes.utilities.OldContractsToJSONUtility;
import gmoldes.utilities.Message;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        /* Initial control processes */
        ProgressIndicator indicator = new ProgressIndicator();
        Scene initialScene = new Scene(indicator);
        primaryStage.setScene(initialScene);
        primaryStage.show();

        OldContractsToJSONUtility ctJson = new OldContractsToJSONUtility();
        ctJson.oldContractToJsonGenerator();

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

