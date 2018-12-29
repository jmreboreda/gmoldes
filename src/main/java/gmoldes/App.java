package gmoldes;


import gmoldes.components.initial_menu.InitialMenuController;
import gmoldes.domain.check.InitialChecks;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.text.ParseException;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        /* Initial control processes */
//        ProgressIndicator indicator = new ProgressIndicator();
//        Scene initialScene = new Scene(indicator);
//        primaryStage.setScene(initialScene);
//        primaryStage.show();

        initialControlProcesses(primaryStage);

//        initialScene.getWindow().hide();

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

