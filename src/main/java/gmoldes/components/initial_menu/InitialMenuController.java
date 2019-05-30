package gmoldes.components.initial_menu;

import gmoldes.App;
import gmoldes.components.ViewLoader;
import gmoldes.components.client_invoice_check_list.controllers.ClientInvoiceCheckListMainController;
import gmoldes.components.contract.contract_variation.controllers.ContractVariationMainController;
import gmoldes.components.contract.new_contract.controllers.NewContractMainController;
import gmoldes.components.person_management.controllers.PersonManagementMainController;
import gmoldes.components.payroll_check_list.controllers.PayrollCheckListMainController;
import gmoldes.components.timerecord.controllers.TimeRecordController;
import gmoldes.domain.client.manager.ClientManager;
import gmoldes.domain.person.PersonManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class InitialMenuController extends AnchorPane {


    private static final Logger logger = Logger.getLogger(InitialMenuController.class.getSimpleName());
    private static final String INITIAL_MENU_FXML = "/fxml/initial_menu/initial_menu.fxml";

    private Stage primaryStage;
    private ClientManager clientManager = new ClientManager();
    private PersonManager personManager = new PersonManager();

    private Parent parent;

    @FXML
    private Button newContractButton;
    @FXML
    private Button timeRecordButton;
    @FXML
    private Button contractVariationButton;
    @FXML
    private Button clientInvoiceCheckListButton;
    @FXML
    private Button payrollCheckListButton;
    @FXML
    private Button personManagementButton;
    @FXML
    private Button exitButton;



    public InitialMenuController() {
        logger.info("Initilizing initial_menu fxml");
        this.parent = ViewLoader.load(this, INITIAL_MENU_FXML);

        //contractVariationButton.setVisible(false);
    }

    @FXML
    public void initialize() {
        newContractButton.setOnMouseClicked(this::onNewContract);
        timeRecordButton.setOnMouseClicked(this::onTimeRecord);
        contractVariationButton.setOnMouseClicked(this::onContractVariation);
        clientInvoiceCheckListButton.setOnMouseClicked(this::onClientInvoiceCheckList);
        payrollCheckListButton.setOnMouseClicked(this::onPayrollCheckList);
        personManagementButton.setOnMouseClicked(this::onPersonManagementButton);
        exitButton.setOnMouseClicked(this::onExit);
    }

    private void onNewContract(MouseEvent event){
        NewContractMainController newContractController = new NewContractMainController();
        Scene scene = new Scene(newContractController);
        scene.getStylesheets().add(App.class.getResource("/css_stylesheet/application.css").toExternalForm());
        Stage newContractStage = new Stage();
        newContractStage.setResizable(false);
        newContractStage.setTitle("Nuevo contrato de trabajo");
        newContractStage.setScene(scene);
        newContractStage.initOwner(primaryStage);
        newContractStage.initModality(Modality.APPLICATION_MODAL);
        newContractStage.show();
    }

    private void onTimeRecord(MouseEvent event){
        TimeRecordController timeRecordController = new TimeRecordController();
        Scene scene = new Scene(timeRecordController);
        scene.getStylesheets().add(App.class.getResource("/css_stylesheet/application.css").toExternalForm());
        Stage timeRecordStage = new Stage();
        timeRecordStage.setResizable(false);
        timeRecordStage.setTitle("Registro horario");
        timeRecordStage.setScene(scene);
        timeRecordStage.initOwner(primaryStage);
        timeRecordStage.initModality(Modality.APPLICATION_MODAL);
        timeRecordStage.show();
    }

    private void onContractVariation(MouseEvent event){
        ContractVariationMainController contractVariationMainController = new ContractVariationMainController();
        Scene scene = new Scene(contractVariationMainController);
        scene.getStylesheets().add(App.class.getResource("/css_stylesheet/application.css").toExternalForm());
        Stage contractVariationStage = new Stage();
        contractVariationStage.setResizable(false);
        contractVariationStage.setTitle("Variaciones de contratos de trabajo");
        contractVariationStage.setScene(scene);
        contractVariationStage.initOwner(primaryStage);
        contractVariationStage.initModality(Modality.APPLICATION_MODAL);
        contractVariationStage.show();
    }

    private void onClientInvoiceCheckList(MouseEvent event){
        ClientInvoiceCheckListMainController clientInvoiceCheckListMainController = new ClientInvoiceCheckListMainController();
        Scene scene = new Scene(clientInvoiceCheckListMainController);
        scene.getStylesheets().add(App.class.getResource("/css_stylesheet/application.css").toExternalForm());
        Stage contractVariationStage = new Stage();
        contractVariationStage.setResizable(false);
        contractVariationStage.setTitle("Relación de empresas y empresarios para control de envío de facturas de la actividad");
        contractVariationStage.setScene(scene);
        contractVariationStage.initOwner(primaryStage);
        contractVariationStage.initModality(Modality.APPLICATION_MODAL);
        contractVariationStage.show();
    }

    private void onPayrollCheckList(MouseEvent event){
        PayrollCheckListMainController payrollCheckListMainController = new PayrollCheckListMainController();
        Scene scene = new Scene(payrollCheckListMainController);
        scene.getStylesheets().add(App.class.getResource("/css_stylesheet/application.css").toExternalForm());
        Stage contractVariationStage = new Stage();
        contractVariationStage.setResizable(false);
        contractVariationStage.setTitle("Relación de personas para control de nóminas");
        contractVariationStage.setScene(scene);
        contractVariationStage.initOwner(primaryStage);
        contractVariationStage.initModality(Modality.APPLICATION_MODAL);
        contractVariationStage.show();
    }

    private void onPersonManagementButton(MouseEvent event){
        PersonManagementMainController personManagementMainController = new PersonManagementMainController();
        Scene scene = new Scene(personManagementMainController);
        scene.getStylesheets().add(App.class.getResource("/css_stylesheet/application.css").toExternalForm());
        Stage personManagementStage = new Stage();
        personManagementStage.setResizable(false);
        personManagementStage.setTitle("Mantenimiento de personas");
        personManagementStage.setScene(scene);
        personManagementStage.initOwner(primaryStage);
        personManagementStage.initModality(Modality.APPLICATION_MODAL);
        personManagementStage.show();
    }

    private void onExit(MouseEvent event) {

        logger.info("Application GMoldes: exiting program.");

        Platform.exit();
        System.exit(0);
    }
}
