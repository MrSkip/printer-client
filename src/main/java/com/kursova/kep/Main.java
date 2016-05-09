package com.kursova.kep;

import com.kursova.kep.custom.stage.ExtendStage;
import javafx.stage.Stage;
import javafx.application.Application;

/**
 * by Mr Skip on 24.03.2016.
 */

public class Main extends Application {

    public static void main(String[] arg){
        launch(arg);
    }

    public void start(Stage primaryStage) throws Exception {
//        new ExtendStage(Main.class.getClassLoader().getResource("view/start/start.fxml"))
//                .showAndWait();

        ExtendStage myStage = new ExtendStage(Main.class.getClassLoader().getResource("view/main/main.fxml"));
        myStage.show();
    }
}
