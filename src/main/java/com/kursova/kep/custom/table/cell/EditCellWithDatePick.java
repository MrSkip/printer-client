package com.kursova.kep.custom.table.cell;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

import com.kursova.kep.entity.BaseEntity;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.util.StringConverter;

/**
 * Created by Mr. Skip.
 */

public class EditCellWithDatePick<S extends BaseEntity, T> extends TableCell<S, T> {

    private DatePicker datePicker;

    @Override
    public void startEdit() {
        if (!isEmpty()){
            super.startEdit();
            createDatePicker();

            setGraphic(datePicker);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

            Platform.runLater(() -> datePicker.requestFocus());
        }
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (item == null){
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
        SimpleDateFormat smp = new SimpleDateFormat("dd/mm/yyyy");

        if (null == datePicker) {
            createDatePicker();
        }

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {

            if (isEditing()) {

                setDatePikerDate(smp.format(item));
                setText(smp.format(item));

                setGraphic(datePicker);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            } else {

                setText(getString());
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }
    }

    private void setDatePikerDate(String dateAsStr) {

        LocalDate ld;
        int jour, mois, annee;

        try {
            jour = Integer.parseInt(dateAsStr.substring(0, 2));
            mois = Integer.parseInt(dateAsStr.substring(3, 5));
            annee = Integer.parseInt(dateAsStr.substring(6, dateAsStr.length()));
        } catch (NumberFormatException e) {
            System.out.println("setDatepikerDate / unexpected error " + e);
            datePicker.setValue(LocalDate.parse(Calendar.getInstance().getTime().toString()));
            return;
        }

        ld = LocalDate.of(annee, mois, jour);
        datePicker.setValue(ld);
    }

    private void createDatePicker() {
        this.datePicker = new DatePicker();

        datePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(/*"MMM dd, yyyy"*/"yyyy-MM-dd");
            @Override
            public String toString(LocalDate object) {
                return object == null
                        ? null
                        : dateFormatter.format(object);
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        datePicker.setPromptText("dd/mm/yyyy");
        if (getString() != null && !getString().isEmpty())
            datePicker.setValue(LocalDate.parse(getString()));
        datePicker.setEditable(true);

        datePicker.setOnAction(t -> {
            Calendar cal = getCalendar();
            commitEdit(cal == null ? null : (T) new Date(cal.getTime().getTime()));
        });

        datePicker.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue){
                Calendar cal = getCalendar();
                commitEdit(cal == null
                        ? null
                        :(T) new Date(cal.getTime().getTime()));
            }
        });
        setAlignment(Pos.CENTER);
    }

    private Calendar getCalendar() {
        LocalDate date = datePicker.getValue();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");

        Calendar cal = Calendar.getInstance();
        try {

            cal.set(Calendar.YEAR, date.getYear());
            cal.set(Calendar.MONTH, date.getMonthValue() - 1);
            cal.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());

        } catch (NullPointerException calendar){
            return null;
        }
        return cal;
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getString());
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    private String getString() {
        return getItem() == null ? null : getItem().toString();
    }
}
