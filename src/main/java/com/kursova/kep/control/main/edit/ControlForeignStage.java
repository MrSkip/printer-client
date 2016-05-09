package com.kursova.kep.control.main.edit;

import com.kursova.kep.control.base.BaseController;
import com.kursova.kep.custom.table.TableColumnsGenerator;
import com.kursova.kep.rest.Client;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Mr. Skip.
 */

public class ControlForeignStage extends BaseController implements Initializable{
    public TableView table;
    public Button
            close,
            add;
    public Label label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setClass(Class aClass){
        List list = null;

        try {
            list = Client.get(aClass.getSimpleName().toLowerCase(),
                    Class.forName("[L" + aClass.getPackage().getName() + "." + aClass.getSimpleName() + ";")).build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        label.setText(" " + aClass.getSimpleName() + " ");

        if (table.getColumns() != null && !table.getColumns().isEmpty())
            table.getColumns().clear();

        TableColumnsGenerator.setTableView(table).generateColumns(list, aClass);
    }

    public Button getClose() {
        return close;
    }

    public Button getAdd() {
        return add;
    }

    public Label getLabel() {
        return label;
    }

    public void setId(int id) {
        table.getSelectionModel().select(id - 1);
    }
}
