package com.kursova.kep.message;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by Mr. Skip.
 */

public class MessageBox extends Stage{
    private static TextField textField;
    private String title;

    // for default format is handler only String text @see constructor
    private String format;

    private MessageBox(String title){
        if (textField == null)
            textField = new TextField();
        else textField.setText(null);

        this.title = title;
        format = "[A-Za-zА-Яа-яїЇіІєЄ @.-]";
    }

    public MessageBox setFormat(String format){
        if (format == null || format.isEmpty())
            return this;
        this.format = format;
        return this;
    }

    public String run(){

        initStyle(StageStyle.UTILITY);
        setScene(createScene());
        initModality(Modality.APPLICATION_MODAL);

        textField.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            TextField txt_TextField = (TextField) e.getSource();
            if (txt_TextField.getText() != null
                    && txt_TextField.getText().length() >= 100)
                e.consume();
            else if (!e.getCharacter().matches(format))
                e.consume();
        });

        showAndWait();

        return textField.getText();
    }

    private Scene createScene() {
        BorderPane borderPane = new BorderPane();

        borderPane.setTop(new Label(" " + title));
        borderPane.setCenter(textField);

        Button
                buttonClose = new Button("Закрити"),
                buttonOk = new Button("Відмінно");

        HBox hBox = new HBox(buttonOk, buttonClose);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setSpacing(10);

        buttonClose.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                textField.setText("");
                close();
            }
        });
        buttonOk.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY)
                close();
        });

        borderPane.setBottom(hBox);

        return new Scene(borderPane);
    }

    public static MessageBox setTitleForBox(String title){
        return new MessageBox(title);
    }


}
