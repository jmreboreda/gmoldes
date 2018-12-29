package gmoldes.utilities;

import gmoldes.domain.check.CheckConstants;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.util.Optional;

public class Message {

//    //private static Image icon = new Image("/resources/new_contract_icon/GMapp_PNG_64x64.png");
//    private static Image icon = new Image("/pics/new_contract_icon/GMapp_PNG_64x64.png");

    public static Boolean standardConfirmationMessage(Window window, String title, String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(window);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setResizable(true);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }

    public static Boolean confirmationMessage(Window window, String title, String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(window);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setResizable(true);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setContentText(message);
        alert.getDialogPane().setCursor(Cursor.WAIT);

        ButtonType buttonTypeYes = new ButtonType("Si");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeYes){
            return true;
        } else {
            return false;
        }
    }

    public static void warningMessage(Window window, String title, String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initStyle(StageStyle.UTILITY);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(window);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setResizable(true);
        //System.out.println(title.length());
        if(title.contains(CheckConstants.INITIAL_CHECK_HEADER_TEXT)){
            Double minimumWidth = title.length() < 74 ? 74*9.5 : title.length() * 9.5;      // Yea!, magic numbers!
            alert.getDialogPane().setMinWidth(minimumWidth);
        }else{
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        }

        alert.getDialogPane().setCursor(Cursor.WAIT);

        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void errorMessage(Window window, String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initStyle(StageStyle.UTILITY);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(window);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setResizable(true);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
