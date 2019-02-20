package gmoldes;

import gmoldes.components.initial_menu.InitialMenuController;
import gmoldes.domain.check.InitialChecks;
import gmoldes.domain.client.Client;
import gmoldes.domain.client.ClientService;
import gmoldes.domain.client.dto.ClientCCCDTO;
import gmoldes.domain.client.dto.ClientDTO;
import gmoldes.domain.client.persistence.vo.ClientCCCVO;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.text.ParseException;


public class App extends Application {

    private Pane splashLayout;
    private ProgressBar loadProgress;
    private Label progressText;
    private Stage mainStage;
    private static final int SPLASH_WIDTH = 300;
    private static final int SPLASH_HEIGHT = 100;

    @Override
    public void init() {
        ImageView splash = new ImageView(new Image(
                ApplicationConstants.SPLASH_IMAGE
        ));
        loadProgress = new ProgressBar();
        loadProgress.setPrefWidth(SPLASH_WIDTH - 20);
        progressText = new Label("Will find friends for peanuts . . .");
        splashLayout = new VBox();
        splashLayout.getChildren().addAll(splash, loadProgress, progressText);
        progressText.setAlignment(Pos.CENTER);
        splashLayout.setStyle(
                "-fx-padding: 5; " +
                        "-fx-background-color: cornsilk; " +
                        "-fx-border-width:5; " +
                        "-fx-border-color: " +
                        "linear-gradient(" +
                        "to bottom, " +
                        "chocolate, " +
                        "derive(chocolate, 25%)" +
                        ");"
        );
        splashLayout.setEffect(new DropShadow());
    }

    @Override
    public void start(final Stage initStage) throws Exception {
        final Task<ObservableList<String>> friendTask = new Task<ObservableList<String>>() {
            @Override
            protected ObservableList<String> call() throws InterruptedException, ParseException {
                ObservableList<String> foundFriends =
                        FXCollections.observableArrayList();

                for (int i = 0; i < 10; i++) {
                    Thread.sleep(125);
                    updateProgress(i + 1, 10);
                }
                Thread.sleep(100);
                return foundFriends;
            }
        };

        showSplash(
                initStage,
                friendTask,
                () -> showMainStage(friendTask.valueProperty())
        );
        new Thread(friendTask).start();

    }

    private void showMainStage(
            ReadOnlyObjectProperty<ObservableList<String>> friends
    ) {
        mainStage = new Stage(StageStyle.DECORATED);
        mainStage.setTitle("My Friends");
        mainStage.getIcons().add(new Image(
                ApplicationConstants.APPLICATION_ICON
        ));

        final ListView<String> peopleView = new ListView<>();
        peopleView.itemsProperty().bind(friends);

        mainStage.setScene(new Scene(peopleView));
        //mainStage.show();
    }

    private void showSplash(
            final Stage initStage,
            Task<?> task,
            InitCompletionHandler initCompletionHandler
    ) {
        progressText.textProperty().bind(task.messageProperty());
        loadProgress.progressProperty().bind(task.progressProperty());
        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                loadProgress.progressProperty().unbind();
                loadProgress.setProgress(1);
                initStage.toFront();
                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), splashLayout);
                fadeSplash.setFromValue(1.0);
                fadeSplash.setToValue(0.0);
                fadeSplash.setOnFinished(actionEvent -> initStage.hide());
                fadeSplash.play();

                // Initial checks
                try {
                    initialControlProcesses(initStage);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                /* Initial menu */
                InitialMenuController controller = new InitialMenuController();
                mainStage = new Stage(StageStyle.DECORATED);
                mainStage.getIcons().add(new Image(
                        ApplicationConstants.APPLICATION_ICON));
                mainStage.setResizable(false);
                mainStage.setTitle("Menú principal");
                Scene scene = new Scene(controller);
                scene.getStylesheets().add(App.class.getResource("/css_stylesheet/application.css").toExternalForm());
                mainStage.setScene(scene);
                mainStage.show();

                initCompletionHandler.complete();

            } // todo add code to gracefully handle other task states.
        });

        Scene splashScene = new Scene(splashLayout, Color.TRANSPARENT);
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        initStage.setScene(splashScene);
        initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH / 2);
        initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - SPLASH_HEIGHT / 2);
        initStage.initStyle(StageStyle.TRANSPARENT);
        initStage.setAlwaysOnTop(true);
        initStage.show();
    }

    public interface InitCompletionHandler {
        void complete();
    }

    private void initialControlProcesses(Stage primaryStage) throws ParseException {

        InitialChecks.alertByContractNewVersionWithPendingIDC(primaryStage);
        InitialChecks.alertOfWeeklyOfWorkingDayScheduleWithEndDate(primaryStage);
        InitialChecks.alertByContractNewVersionExpiration(primaryStage);
        InitialChecks.alertByDelaySendingLaborDocumentationToClients(primaryStage);

//        ClientService clientService = ClientService.ClientServiceFactory.getInstance();
//        ClientDTO clientDTO = clientService.findClientById(10);
//        for(ClientCCCVO clientCCCVO : clientDTO.getClientCCC()){
//            System.out.println(clientCCCVO.getCcc_inss());
//        }
    }
}
