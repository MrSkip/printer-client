package com.kursova.kep.custom.stage;

import com.kursova.kep.control.base.BaseController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Mr. Skip.
 */

public class ExtendStage extends Stage{
    private FXMLLoader fxmlLoader = null;

    public ExtendStage(URL path){
        createScene(path);
    }

    private void createScene(URL location){
        try {
            fxmlLoader = createFxmlLoader( location );
            Parent root = fxmlLoader.load(location.openStream());
            setScene( new Scene(root) );
            ((BaseController) fxmlLoader.getController()).setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <S> S getController(){
        return fxmlLoader.getController();
    }

    private FXMLLoader createFxmlLoader(URL location){
        return new FXMLLoader(location, null, new JavaFXBuilderFactory(), null, Charset.forName(FXMLLoader.DEFAULT_CHARSET_NAME));
    }

}
