package com.kursova.kep.custom.stage;

/**
 * Created by Mr. Skip.
 */
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// Class can show the minor stage over the main stage
public class StageUtil {
    private Stage
            mainStage = null,
            minorStage = null;
    private double x, y, x2, y2;
    private Object object = null;

    public static StageUtil createStageUtil(double x, double y, Stage minorStage, Stage mainStage){
        return new StageUtil(x, y, minorStage, mainStage);
    }

    /*
    * Constructor get the parameters:
    * x - x coordinate of minorStage
    * y - y coordinate of minorStage
    * mainStage - the main stage
    * minorStage - the minor stage (this stage show over the mainStage)
    */

    private StageUtil(double x, double y, Stage minorStage, Stage mainStage){
        helpConstructor(x, y, minorStage, mainStage);
    }

    private void helpConstructor(double x, double y, Stage minorStage, Stage mainStage){
        this.x2 = x;
        this.y2 = y;
        this.minorStage = minorStage;
        this.mainStage = mainStage;

        this.x = mainStage.getX();
        this.y = mainStage.getY();

        setStageProperties();
    }

    private void setStageProperties(){
        minorStage.initStyle(StageStyle.UNDECORATED);
        minorStage.initOwner(mainStage);

        // Add listener that can be close minorStage if window is change coordinate X
        mainStage.xProperty().addListener((observable, oldValue, newValue) -> {
            closeStudy();
        });

        // Add listener than can be close minorStage when window change coordinate Y
        mainStage.yProperty().addListener((observable, oldValue, newValue) -> {
            closeStudy();
        });

        // Add listener than can be close minorStage when window change height
        mainStage.heightProperty().addListener((observable, oldValue, newValue) -> {
            closeStudy();
        });

        // Add listener than can be close minorStage when window change width
        mainStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            closeStudy();
        });
    }

    // When the windows resize or first show than take position at object
    private void setPosition(){

        this.x = mainStage.getX() + 8;
        this.y = mainStage.getY() + 30;

        if (object instanceof Button){
            Button button = (Button) object;
            x += button.localToScene(Point2D.ZERO).getX();
            y += button.localToScene(Point2D.ZERO).getY();
        }
        else if (object instanceof Label){
            Label label = (Label) object;
            x += label.localToScene(Point2D.ZERO).getX();
            y += label.localToScene(Point2D.ZERO).getY();
        }
        else if (object instanceof TextField){
            TextField textField = (TextField) object;
            x += textField.localToScene(Point2D.ZERO).getX();
            y += textField.localToScene(Point2D.ZERO).getY();
        }
        else if (object instanceof ImageView){
            ImageView imageView = (ImageView) object;
            x += imageView.localToScene(Point2D.ZERO).getX();
            y += imageView.localToScene(Point2D.ZERO).getY();
        }
    }

    // Method references button with minorStage
    public void setObject(Button button){
        object = button;
        setCloseOnObject(button.getWidth(), button.getHeight());
        button.setOnAction(event -> closeOrShow());
        setPosition();
    }

    // Method references label with minorStage
    public void setObject(Label label){
        object = label;
        setCloseOnObject(label.getWidth(), label.getHeight());
        label.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> closeOrShow());
        setPosition();
    }

    public void setObject(TextField textField){
        object = textField;
        setCloseOnObject(textField.getWidth(), textField.getHeight());
        setPosition();
    }

    public void setObject(ImageView imageView){
        object = imageView;
        setCloseOnObject(imageView.getFitWidth(), imageView.getFitHeight());
        setPosition();
    }

    private void setCloseOnObject(double width, double height){
        /*
        * Create EventFilter
        * If cursor over the `object` than not close mStage
        * else close minorStage
        */
        mainStage.getScene().addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            if (!(mouseEvent.getScreenX() >= x && mouseEvent.getScreenX() <= x + width
                    && mouseEvent.getScreenY() >= y && mouseEvent.getScreenY() <= y + height))
                closeStudy();
        });
    }

    // Method close the minorStage (If minorStage is showing)
    public void closeStudy(){
        if (minorStage.isShowing())
            minorStage.close();
    }

    public void setShowing(){
        if (!minorStage.isShowing()){
            setPosition();
            minorStage.setX(x + x2);
            minorStage.setY(y + y2);
            minorStage.show();
        }
    }

    // Method close mStage (is minorStage is show) - and show minorStage (if minorStage is close)
    public void closeOrShow(){
        if (!minorStage.isShowing())
            setShowing();
        else
            closeStudy();
    }

    public Stage getMinorStage(){
        return minorStage;
    }

    public Stage getMainStage(){
        return mainStage;
    }

    public void setMinorStage(Stage stage){
        minorStage = stage;
        setStageProperties();
    }

    public void setX(double x){
        this.x2 = x;
    }
}
