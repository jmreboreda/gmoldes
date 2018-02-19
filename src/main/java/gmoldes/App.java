package gmoldes;


import gmoldes.check.InitialChecks;
import gmoldes.controllers.InitialMenuController;
import gmoldes.domain.dto.ContractDTO;
import gmoldes.domain.dto.IDCControlDTO;
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

        /* Update contracts DB */
        InitialChecks.UpdateCurrentContracts();

        /* Alert of contract expiration */
        String alert = "";
        List<ContractDTO> contractsExpiration = InitialChecks.contractExpirationControl();
        if(!contractsExpiration.isEmpty()) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
            Date now = new Date();
            int days = 0;
            for (ContractDTO contractDTO : contractsExpiration) {
                days = (int) (dateFormatter.parse(dateFormatter.format(contractDTO.getF_hasta())).getTime() - dateFormatter.parse(dateFormatter.format(now)).getTime())/86400000;
                alert = alert + "Preaviso del contrato de " + contractDTO.getTrabajador_name() + " con " + contractDTO.getClientegm_name()
                        + ": vencimiento el día " + dateFormatter.format(contractDTO.getF_hasta()) + ". Faltan  " + days + " días." + "\n";
            }

            Message.warningMessage(primaryStage.getOwner(), "Preavisos de fin de contrato", alert);
        }

        /* Alert for pending IDC */
        String alert1 = "";
        List<IDCControlDTO> idcControlDTOList = InitialChecks.findPendingIDC();
        if(!idcControlDTOList.isEmpty()){
            for(IDCControlDTO idcControlDTO : idcControlDTOList){

                alert1 = alert1 + "IDC Pendiente: " + idcControlDTO.getDescr_variacion() + " de " + idcControlDTO.getTrabajador_name() + " con " +
                        idcControlDTO.getClientegm_name() + " desde " + idcControlDTO.getDate_to() + ". Faltan " + idcControlDTO.getDays() + " días." + "\n";
            }

            Message.warningMessage(primaryStage.getOwner(), "IDC pendientes de recepción", alert1);
        }



        /* Initial menu */
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

