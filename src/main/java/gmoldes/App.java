package gmoldes;


import gmoldes.check.InitialChecks;
import gmoldes.controllers.InitialMenuController;
import gmoldes.persistence.dao.ClientDAO;
import gmoldes.persistence.vo.ClientVO;
import gmoldes.persistence.vo.ServiceGMVO;
import gmoldes.utilities.Utilities;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class App extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{

        InitialChecks.UpdateCurrentContracts();

        InitialMenuController controller = new InitialMenuController();
        primaryStage.setResizable(false);
        Scene scene = new Scene(controller);
        scene.getStylesheets().add(App.class.getResource("/css_stylesheet/application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

//        Set<ServiceGMVO> serviceGMVOSet = new HashSet<>();
//
//        ClientVO clientVO = new ClientVO();
//        clientVO.setNom_rzsoc("Cliente de prueba2");
//
//        ServiceGMVO serviceGMVO= new ServiceGMVO();
//        serviceGMVO.setDateFrom(new Date());
//        serviceGMVO.setService(Utilities.ServicesGM.REGISTRO_FACTURAS.getServiceGM());
//        serviceGMVO.setClientVO(clientVO);
//        serviceGMVOSet.add(serviceGMVO);
//
//        ServiceGMVO serviceGMVO1= new ServiceGMVO();
//        serviceGMVO1.setDateFrom(new Date());
//        serviceGMVO1.setService(Utilities.ServicesGM.CONTABILIDAD.getServiceGM());
//        serviceGMVO1.setClientVO(clientVO);
//        serviceGMVOSet.add(serviceGMVO1);
//        clientVO.setServicesGM(serviceGMVOSet);
//
//        ClientDAO clientDAO = ClientDAO.ClientDAOFactory.getInstance();
//        Integer idNewClient = clientDAO.createClient(clientVO);

    }

    public static void main( String[] args ){

        launch(args);
    }
}

