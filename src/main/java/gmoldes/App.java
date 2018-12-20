package gmoldes;


import gmoldes.components.initial_menu.InitialMenuController;
import gmoldes.domain.check.InitialChecks;
import gmoldes.domain.client.Client;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.client.mapper.MapperClientVODTO;
import gmoldes.domain.client.persistence.dao.ClientDAO;
import gmoldes.domain.client.persistence.vo.ClientCCCVO;
import gmoldes.domain.client.persistence.vo.ClientVO;
import gmoldes.domain.servicegm.persistence.vo.ServiceGMVO;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import java.text.ParseException;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        /* Initial control processes */
        ProgressIndicator indicator = new ProgressIndicator();
        Scene initialScene = new Scene(indicator);
        primaryStage.setScene(initialScene);
        primaryStage.show();


//        //***************************************************************************************************
//        ClientDAO clientDAO = ClientDAO.ClientDAOFactory.getInstance();
//        ClientVO clientVO = clientDAO.findClientById(7);
//
//        ClientDTO clientDTO = MapperClientVODTO.map(clientVO);
//
//        System.out.println(clientDTO.toNaturalName());
//        for(ServiceGMVO serviceGMVO :clientDTO.getServicesGM()){
//            System.out.println(serviceGMVO.getService());
//        }
//
//        for(ClientCCCVO clientCCCVO : clientDTO.getClientCCC()) {
//            System.out.println(clientCCCVO.getCcc_inss());
//        }
//        //***************************************************************************************************

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

        InitialChecks.alertByContractNewVersionWithPendingIDC(primaryStage);
        InitialChecks.alertByContractNewVersionExpiration(primaryStage);
        InitialChecks.alertByDelaySendingLaborDocumentationToClients(primaryStage);
        InitialChecks.UpdateOldContractVersionInForce();
    }
 }

