package gmoldes.domain.check;

import gmoldes.components.initial_menu.InitialMenuController;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.*;
import javafx.concurrent.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.util.Duration;

import java.text.ParseException;

/**
 * Example of displaying a splash page for a standalone JavaFX application
 */
public class TaskBasedSplash extends Application {
    public static final String APPLICATION_ICON =
            "/pics/GMapp_PNG_64x64.png";
    public static final String SPLASH_IMAGE =
            "/pics/GMapp_PNG_64x64.png";

    private Pane splashLayout;
    private ProgressBar loadProgress;
    private Label progressText;
    private Stage mainStage;
    private static final int SPLASH_WIDTH = 300;
    private static final int SPLASH_HEIGHT = 100;

    @Override
    public void init() {
        ImageView splash = new ImageView(new Image(
                SPLASH_IMAGE
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
                        "derive(chocolate, 50%)" +
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
                    Thread.sleep(40);
                    updateProgress(i + 1, 10);
                }
                Thread.sleep(400);
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
                APPLICATION_ICON
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

                try {
                    initialControlProcesses(initStage);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
//                InitialChecks.alertByContractNewVersionWithPendingIDC(initStage);
//                InitialChecks.alertByContractNewVersionExpiration(initStage);
//                InitialChecks.alertByDelaySendingLaborDocumentationToClients(initStage);

                /* Initial menu */
                InitialMenuController controller = new InitialMenuController();
                mainStage = new Stage(StageStyle.DECORATED);
                mainStage.getIcons().add(new Image(
                        APPLICATION_ICON));
                mainStage.setResizable(false);
                mainStage.setTitle("Menú principal");
                Scene scene = new Scene(controller);
                scene.getStylesheets().add(TaskBasedSplash.class.getResource("/css_stylesheet/application.css").toExternalForm());
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
        InitialChecks.alertByContractNewVersionExpiration(primaryStage);
        InitialChecks.alertByDelaySendingLaborDocumentationToClients(primaryStage);
        InitialChecks.UpdateOldContractVersionInForce();
    }
}
