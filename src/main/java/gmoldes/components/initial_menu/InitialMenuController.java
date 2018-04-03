package gmoldes.components.initial_menu;

import gmoldes.App;
import gmoldes.components.ViewLoader;
import gmoldes.components.contract.new_contract.controllers.NewContractMainController;
import gmoldes.components.timerecord.controllers.TimeRecordController;
import gmoldes.domain.client.manager.ClientManager;
import gmoldes.domain.person.manager.PersonManager;
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
    private Button exitButton;



    public InitialMenuController() {
        logger.info("Initilizing initial_menu fxml");
        this.parent = ViewLoader.load(this, INITIAL_MENU_FXML);
    }

    @FXML
    public void initialize() {
        newContractButton.setOnMouseClicked(this::onNewContract);
        timeRecordButton.setOnMouseClicked(this::onTimeRecord);
        exitButton.setOnMouseClicked(this::onExit);
    }

    private void onNewContract(MouseEvent event){
        NewContractMainController newContractController = new NewContractMainController();
        Scene scene = new Scene(newContractController);
        scene.getStylesheets().add(App.class.getResource("/css_stylesheet/application.css").toExternalForm());
        Stage newContractStage = new Stage();
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
        timeRecordStage.setTitle("Registro horario");
        timeRecordStage.setScene(scene);
        timeRecordStage.initOwner(primaryStage);
        timeRecordStage.initModality(Modality.APPLICATION_MODAL);
        timeRecordStage.show();
    }

    private void onExit(MouseEvent event) {
        Platform.exit();
        System.exit(0);
    }
}
