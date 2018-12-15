package gmoldes;


import gmoldes.components.initial_menu.InitialMenuController;
import gmoldes.domain.check.InitialChecks;
import gmoldes.utilities.OldContractsToJSONUtility;
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


        if(false) {
            OldContractsToJSONUtility ctJson = new OldContractsToJSONUtility();
            ctJson.oldContractToJsonGenerator();
        }


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

        InitialChecks.alertByContractNewVersionExpiration(primaryStage);
        InitialChecks.alertByContractWithPendingIDC(primaryStage);

        updateContractsInForceInDatabase();
    }

    private void updateContractsInForceInDatabase(){
        InitialChecks.UpdateContractsInForce();
    }

}

