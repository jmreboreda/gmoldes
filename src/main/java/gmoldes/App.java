package gmoldes;


import gmoldes.check.InitialChecks;
import gmoldes.controllers.InitialMenuController;
import gmoldes.domain.dto.ContractDTO;
import gmoldes.persistence.dao.ClientDAO;
import gmoldes.persistence.vo.ClientVO;
import gmoldes.persistence.vo.ServiceGMVO;
import gmoldes.utilities.Message;
import gmoldes.utilities.Utilities;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class App extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{

        InitialChecks.UpdateCurrentContracts();

        String alert = "";
        List<ContractDTO> contractsExpirations = InitialChecks.contractExpirationControl();
        if(!contractsExpirations.isEmpty()) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
            for (ContractDTO contractDTO : contractsExpirations) {
                alert = alert + "Preaviso del contrato de " + contractDTO.getTrabajador_name() + " con " + contractDTO.getClientegm_name()
                        + ": vencimiento el día " + dateFormatter.format(contractDTO.getF_hasta()) + ". Faltan  XXXXX días" + "\n";
            }

            Message.warningMessage(primaryStage.getOwner(), "Preavisos de fin de contrato", alert);
        }

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

