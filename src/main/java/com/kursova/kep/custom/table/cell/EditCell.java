package com.kursova.kep.custom.table.cell;

import com.kursova.kep.control.main.edit.ControlForeignStageSecond;
import com.kursova.kep.custom.stage.ExtendStage;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.stage.Modality;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Mr. Skip.
 */
public class EditCell<S, T> extends TableCell<S, T> {
    protected Class childrenClass;
    protected Class parentClass;
    private ExtendStage stage;

    public EditCell(Class childrenClass, Class parentClass) {
        this.childrenClass = childrenClass;
        this.parentClass = parentClass;
        createStage();
    }

    private void createStage() {
        stage = new ExtendStage(EditCellWithForeignKey.class.getClassLoader()
                .getResource("view/main/cell_second/view.fxml"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showingProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue)
                cancelEdit();
        });
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText("+");
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();
        initController();
        stage.showAndWait();
    }

    private void initController() {
        ControlForeignStageSecond control = stage.getController();
        control.setClass(childrenClass);

        String methodName = "get" + childrenClass.getSimpleName() + "Set";
        List list = null;
        try {

            Method method = parentClass.getMethod(methodName);
            list = (List) method.invoke(getTableView().getSelectionModel().getSelectedItem());

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        control.initListForFirst(list);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        stage.close();
    }

    public ExtendStage getStage() {
        return stage;
    }
}
