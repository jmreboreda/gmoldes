package gmoldes;


import gmoldes.check.InitialChecks;
import gmoldes.controllers.InitialMenuController;
import gmoldes.domain.dto.ContractDTO;
import gmoldes.domain.dto.IDCControlDTO;
import gmoldes.utilities.Message;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class App extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{

        initialControlProcesses(primaryStage);

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
        updateCurrentContractsInDatabase();
        alertByContractExpiration(primaryStage);
        alertOfPendingIDC(primaryStage);
    }

    private void updateCurrentContractsInDatabase(){
        InitialChecks.UpdateCurrentContracts();
    }

    private void alertByContractExpiration(Stage primaryStage){
        String alert = "";
        String missingExceededText = "";
        List<ContractDTO> contractsExpiration = InitialChecks.contractExpirationControl();
        if(!contractsExpiration.isEmpty()) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
            Date now = new Date();
            for (ContractDTO contractDTO : contractsExpiration) {
                int days = (int)(long)((contractDTO.getF_hasta().getTime() - now.getTime())/(24*60*60*1000));
                if(days >= 0){
                    missingExceededText = "Faltan ";
                }else{
                    missingExceededText = "Excedido en ";
                }
                alert = alert + "Preaviso del contrato de " + contractDTO.getTrabajador_name() + " con " + contractDTO.getClientegm_name()
                        + ": vencimiento el día " + dateFormatter.format(contractDTO.getF_hasta()) + ". " + missingExceededText + Math.abs(days) + " días." + "\n\n";
            }

            Message.warningMessage(primaryStage.getOwner(), "Preavisos de fin de contrato pendientes de recepción", alert);
        }
    }

    private void alertOfPendingIDC(Stage primaryStage) throws ParseException {
        String alert = "";
        String missingExceededText = "";
        List<IDCControlDTO> idcControlDTOList = InitialChecks.findPendingIDC();
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

