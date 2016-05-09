package com.kursova.kep.custom.table;

import com.kursova.kep.control.main.edit.ControlForeignStageSecond;
import com.kursova.kep.custom.CustomProperties;
import com.kursova.kep.custom.VariablesForCell;
import com.kursova.kep.custom.table.cell.EditCell;
import com.kursova.kep.custom.table.cell.EditCellBasic;
import com.kursova.kep.custom.table.cell.EditCellWithDatePick;
import com.kursova.kep.custom.table.cell.EditCellWithForeignKey;
import com.kursova.kep.entity.BaseEntity;
import com.kursova.kep.entity.BaseWithName;
import com.kursova.kep.rest.Client;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Mr. Skip.
 */

public class TableColumnsGenerator {
    private static TableView table = null;
    private List<VariablesForCell> customColumns;

    public static TableColumnsGenerator setTableView(TableView table) {
        TableColumnsGenerator.table = table;

        if (!table.getColumns().isEmpty()) {
            table.getColumns().clear();
            if (!table.getItems().isEmpty())
                table.getItems().clear();
        }

        return new TableColumnsGenerator();
    }

    public <S> void generateColumns(List<S> list, Class<S> sClass) {
        if (list != null) {
            ObservableList<S> observableList = FXCollections.observableArrayList(list);
            table.setItems(observableList);
        }

        table.setTableMenuButtonVisible(true);
        table.setEditable(true);

        if (sClass == String.class) {
            TableColumn<S, String> column
                    = new TableColumn<>("Поле результатів");
            column.setCellValueFactory(param -> new ReadOnlyStringWrapper((String) param.getValue()));
            table.getColumns().add(column);
            return;
        }

        customColumns = CustomProperties.getCellFormat(sClass.getSimpleName());

        Method[] methods = sClass.getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get") && !"getClass".equals(method.getName())) {
                table.getColumns().add(0, byType(method));
            }
        }
    }

    private <S extends BaseEntity, T> TableColumn<S, T> byType(Method method) {
        TableColumn<S, T> column = new TableColumn<>(getCustomNameForCell(method.getName().substring(3)));

        // Add editable to columns with foreign key (ManyToOne)
        if (method.getReturnType().getSuperclass() == BaseWithName.class
                || method.getReturnType().getSuperclass() == BaseEntity.class) {
            return generateReferColumn(method, column);
        }

        // Add editable to columns with foreign key (ManyToMany)
        if (method.getReturnType() == List.class || method.getReturnType() == Set.class) {
            return generateColumnWithForeignKeyMany(method, column);
        }

        column.setCellValueFactory(new PropertyValueFactory<>(method.getName().substring(3)));

        // A column with primary key don`t need to edit
        if (method.getName().equals("getId")) return column;

        // Make editable to column
        if (method.getReturnType() == Date.class)
            column.setCellFactory(param -> new EditCellWithDatePick<>());
        else
            column.setCellFactory(param -> new EditCellBasic<>(method.getReturnType(), customColumns));

        column.setOnEditCommit(event -> {
            try {
                BaseEntity baseEntity = event.getTableView().getItems().get(event.getTablePosition().getRow());
                Method method1 = baseEntity.getClass().getDeclaredMethod("set" + method.getName().substring(3), method.getReturnType());
                method1.invoke(baseEntity, (T) event.getNewValue());
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }

            Client.post(method.getDeclaringClass().getSimpleName().toLowerCase(), event.getTableView().getItems().get(event.getTablePosition().getRow()).getClass())
                    .setRequest(event.getTableView().getItems().get(event.getTablePosition().getRow()))
                    .build();
        });
        return column;
    }

    private <S extends BaseEntity, T> TableColumn<S, T> generateColumnWithForeignKeyMany(Method method,
                                                                                         TableColumn<S, T> column) {
        String typeName = method.getGenericReturnType().getTypeName();
        String substring = typeName.substring(typeName.indexOf('<') + 1, typeName.indexOf('>'));
        try {
            Class finalChildrenClass = Class.forName(substring).newInstance().getClass();
            column.setCellFactory(param -> new EditCell(finalChildrenClass, method.getDeclaringClass()) {
                @Override
                public void cancelEdit() {
                    super.cancelEdit();
                    Object baseEntity = getTableView().getItems().get(getTableRow().getIndex());
                    Method method1;
                    try {
                        method1 = baseEntity.getClass().getDeclaredMethod("set" + method.getName().substring(3), method.getReturnType());
                        method1.invoke(baseEntity, ((ControlForeignStageSecond) getStage().getController()).getListForFirst());

                        Client.post(parentClass.getSimpleName().toLowerCase(), parentClass)
                                .setRequest(baseEntity)
                                .build();
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        column.setStyle("-fx-alignment: CENTER");
        column.setOnEditCancel(event -> {

        });

        return column;
    }

    private <S extends BaseEntity, T> TableColumn<S, T> generateReferColumn(Method method, TableColumn<S, T> column) {
        column.setCellValueFactory(param -> {
            try {
                Method method1 = param.getValue().getClass().getMethod(method.getName());
                BaseEntity entity = (BaseEntity) method1.invoke(param.getValue());
                if (entity == null)
                    return null;
                BaseEntity baseEntity = (BaseEntity) method1.invoke(param.getValue());
                return new SimpleObjectProperty<>((T) baseEntity);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        });

        column.setCellFactory(param -> new EditCellWithForeignKey<>(method.getReturnType(), method.getDeclaringClass()));

        column.setOnEditCommit(event -> {
            Object object = Client.get(method.getReturnType().getSimpleName().toLowerCase() + "/" + ((BaseWithName) event.getNewValue()).getId(), method.getReturnType())
                    .build();

            try {
                Method method2 = method.getDeclaringClass().getMethod("set" + method.getReturnType().getSimpleName(), method.getReturnType());
                method2.invoke(event.getRowValue(), object);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

            Client.post(method.getDeclaringClass().getSimpleName().toLowerCase(), method.getDeclaringClass())
                    .setRequest(event.getRowValue())
                    .build();
        });
        return column;
    }

    private String getCustomNameForCell(String cellName) {
        if (customColumns == null)
            return "";
        for (VariablesForCell customColumn : customColumns) {
            if (customColumn.getOriginCellName().equalsIgnoreCase(cellName))
                return customColumn.getCustomCellName();
        }
        return "";
    }
}
