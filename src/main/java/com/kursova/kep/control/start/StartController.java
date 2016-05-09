package com.kursova.kep.control.start;

import com.kursova.kep.control.base.BaseController;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * by Mr Skip on 07.04.2016.
 */

public class StartController extends BaseController implements Initializable {
    public ProgressBar progressBar;
    public Button close;

    public void initialize(URL location, ResourceBundle resources) {
        close.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY){
                System.exit(1);
            }
        });

        Task task = new Task<ProgressBar>(){
            @Override
            protected ProgressBar call() throws Exception {
                while (true){
                    progressBar.setProgress(progressBar.getProgress() + 0.1);
                    if (progressBar.getProgress() >= 1) break;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return progressBar;
            }
        };

        task.setOnSucceeded(event -> ((Stage) close.getScene().getWindow()).close());
        new Thread(task).start();
    }

}
