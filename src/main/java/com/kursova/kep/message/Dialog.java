package com.kursova.kep.message;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Created by Mr. Skip.
 */
public class Dialog {

    public static boolean showConfirmDialog(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public static void showErrorDialog(String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Вікно помилок");
        alert.setHeaderText("Виникла помилка");
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showInformationDialog(String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Інформаційне вікно");
        alert.setHeaderText("Зверніть увагу");
        alert.setContentText(content);

        alert.showAndWait();
    }

}
