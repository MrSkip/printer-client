package com.kursova.kep.control.main.edit;

import com.kursova.kep.control.base.BaseController;
import com.kursova.kep.custom.table.TableColumnsGenerator;
import com.kursova.kep.entity.BaseEntity;
import com.kursova.kep.rest.Client;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by Mr. Skip.
 */

public class ControlForeignStageSecond<S extends BaseEntity> extends BaseController implements Initializable {
    public Button buttonAdd;
    public Button buttonDelete;
    public Button buttonClose;

    public Label label;
    public Label label2;

    public TableView<S> tableViewFirst;
    public TableView<S> tableViewSecond;

    private List<S> list;
    private List<S> listForFirst;
    private List<S> listForSecond;

    private Class<S> aClass = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listForFirst = new ArrayList<>();
        listForSecond = new ArrayList<>();

        initButtons();
    }

    private void initButtons() {
        buttonAdd.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.PRIMARY){
                if (tableViewSecond.getSelectionModel().getSelectedItem() != null) {
                    S s = tableViewSecond.getSelectionModel().getSelectedItem();
                    listForFirst.add(s);

                    tableViewSecond.getItems().remove(s);
                    tableViewFirst.getItems().add(s);
                }
            }
        });

        buttonDelete.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.PRIMARY){
                if (tableViewFirst.getSelectionModel().getSelectedItem() != null) {
                    S s = tableViewFirst.getSelectionModel().getSelectedItem();
                    listForFirst.remove(s);

                    tableViewSecond.getItems().add(s);
                    tableViewFirst.getItems().remove(s);
                }
            }
        });

        buttonClose.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> ((Stage) buttonClose.getScene().getWindow()).close());

    }

    public void setClass(Class<S> aClass){
        label.setText(" " + aClass.getSimpleName() + " ");
        this.aClass = aClass;
    }

    public void initListForFirst(List<S> list){
        this.listForFirst = list;
        if (listForFirst == null){
            listForFirst = new ArrayList<>();
        }
        initList();
        initSecondList();
        initTables();
    }

    private void initSecondList() {
        listForSecond.clear();
        listForSecond.addAll(list.stream().filter(s -> !checkList(s)).collect(Collectors.toList()));
    }

    private boolean checkList(S s){
        for (S s1 : listForFirst) {
            if (s1.getId().equals(s.getId()))
                return true;
        }
        return false;
    }

    private void initTables() {
        TableColumnsGenerator.setTableView(tableViewFirst).generateColumns(listForFirst, aClass);
        TableColumnsGenerator.setTableView(tableViewSecond).generateColumns(listForSecond, aClass);
    }


    private void initList(){
        try {
            list = Client.get(aClass.getSimpleName().toLowerCase(), Class.forName("[L" + aClass.getPackage().getName() + "." + aClass.getSimpleName() + ";")).
                    build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<S> getListForFirst() {
        return listForFirst;
    }
}
