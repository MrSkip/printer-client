package com.kursova.kep.custom.table.cell;

import com.kursova.kep.custom.VariablesForCell;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.util.List;

/**
 * Created by Mr. Skip.
 */

public class EditCellBasic<S, T> extends TableCell<S, T> {
    private final Class aClass;
    private TextField textField;
    private List<VariablesForCell> variablesForCells;

    public EditCellBasic(Class aClass, List<VariablesForCell> variablesForCells) {
        this.aClass = aClass;
        this.variablesForCells = variablesForCells;
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();

            createTextField();
            setText(null);

            setGraphic(textField);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            textField.selectAll();
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getString());
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            } else {
                setText(getString());
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
        textField.focusedProperty().addListener((arg0, arg1, arg2) -> {
            if (!arg2) {
                String s = textField.getText();
                if (s == null || (s.isEmpty()
                        && (aClass == Long.class
                        || aClass == Double.class
                        || aClass == long.class
                        || aClass == double.class
                        || aClass == int.class
                        || aClass == Integer.class)))
                    commitEdit(null);
                else if (aClass == Long.class)
                    commitEdit((T) (Long) Long.parseLong(s));
                else if (aClass == Double.class)
                    commitEdit((T) (Double) Double.parseDouble(s));
                else if (aClass == Integer.class)
                    commitEdit((T) (Integer) Integer.parseInt(s));
                else
                {
                    commitEdit((T) textField.getText());
                }
            }
        });
        textField.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            TextField txt_TextField = (TextField) e.getSource();

            if (txt_TextField.getText().length() >= 100) {
                e.consume();
            }

            if (e.getCharacter().matches(getCellMatches(aClass))){}
            else e.consume();

        });

    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }

    private String getCellMatches(Class aClass){
        String columnName = getTableColumn().getText();
        String userMatches = null;

        for (VariablesForCell variablesForCell : variablesForCells) {
            if (variablesForCell.getCustomCellName().equals(columnName)){
                userMatches = variablesForCell.getRegularExpress();
                break;
            }
        }

        if (userMatches == null){
            if (aClass == String.class) {
                userMatches = "[A-Za-zА-Яа-яїЇіІєЄ @.-]";
            }
            else if (aClass == long.class
                    || aClass == int.class
                    || aClass == Integer.class
                    || aClass == Long.class){
                userMatches = "[0-9]";
            }
            else if (aClass == double.class || aClass == Double.class){
                userMatches = "[0-9.]";
            }
        }
        return userMatches == null ? "" : userMatches;
    }
}
