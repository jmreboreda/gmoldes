package gmoldes;


import com.lowagie.text.DocumentException;
import gmoldes.controllers.InitialMenuController;
import gmoldes.controllers.NewContractMainController;
import gmoldes.controllers.TimeRecordController;
import gmoldes.domain.dto.ClientDTO;
import gmoldes.forms.TimeRecord;
import gmoldes.manager.ClientManager;
import gmoldes.persistence.dao.ClientDAO;
import gmoldes.persistence.vo.ClientVO;
import gmoldes.persistence.vo.ServiceGMVO;
import gmoldes.services.Printer;
import gmoldes.utilities.Utilities;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class App extends Application{

    public static void main( String[] args ){

//        try {
//            createPDForm();
//        } catch (IOException | DocumentException | PrinterException e) {
//            e.printStackTrace();
//        }

        launch(args);


    }

    @Override
    public void start(Stage primaryStage) throws IOException {
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
}

