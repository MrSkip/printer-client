package com.kursova.kep.custom;

import com.kursova.kep.entity.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr. Skip.
 */
public class CustomProperties {

    public static Class getClassFromHeaderButtons(String buttonName){
        Class aClass = null;
        switch (buttonName){
            case "Фірма":
                aClass = Firma.class;
                break;
            case "Принтер":
                aClass = Printer.class;
                break;
            case "Специфікації":
                aClass = Specifications.class;
                break;
        }
        return aClass;
    }

    public static List<VariablesForCell> getCellFormat(String originTableName){
        switch (originTableName){
            case "Printer":
                return cellFormatForPrinter();
            case "Firma":
                return cellFormatForFirma();
            case "Specifications":
                return cellFormatForSpecification();
            case "String": return null;
        }
        return null;
    }

    private static List<VariablesForCell> cellFormatForFirma() {
        List<VariablesForCell> forCells = new ArrayList<>();
        forCells.add(new VariablesForCell("id", "Номер", null));
        forCells.add(new VariablesForCell("name", "Назва"));
        forCells.add(new VariablesForCell("address", "Адреса", "[A-Za-zА-Яа-яїЇіІєЄ0-9 .-]"));
        forCells.add(new VariablesForCell("country", "Країна"));
        forCells.add(new VariablesForCell("phoneNumber", "Мобільний телефон", "[0-9() ]"));
        forCells.add(new VariablesForCell("pibManager", "Ім’я менеджера"));
        forCells.add(new VariablesForCell("email", "Поштова скринька", "[A-Za-z@0-9.]"));
        forCells.add(new VariablesForCell("webSite", "Веб-сайт", "[A-Za-z@0-9\\.]"));
        return forCells;
    }

    private static List<VariablesForCell> cellFormatForSpecification() {
        List<VariablesForCell> forCells = new ArrayList<>();
        forCells.add(new VariablesForCell("id", "Номер", null));
        forCells.add(new VariablesForCell("typeOfPrinter", "Тип принтера"));
        forCells.add(new VariablesForCell("size", "Розміри", "[0-9вшдxх -]"));
        forCells.add(new VariablesForCell("typeOfCartridge", "Тип картриджа", null));
        forCells.add(new VariablesForCell("weight", "Вага", "[0-9,]"));
        forCells.add(new VariablesForCell("speed", "Швидкільсть друку", "[0-9]"));
        forCells.add(new VariablesForCell("cartrigeResource", "Ресурс картриджа", "[0-9]"));
        forCells.add(new VariablesForCell("interfaceI", "Інтерфейс", "[0-9-]"));
        return forCells;
    }

    private static List<VariablesForCell> cellFormatForPrinter() {
        List<VariablesForCell> forCells = new ArrayList<>();
        forCells.add(new VariablesForCell("id", "Номер", null));
        forCells.add(new VariablesForCell("name", "Назва", "[A-Za-zА-Яа-яїЇіІєЄ0-9-]"));
        forCells.add(new VariablesForCell("model", "Модель", "[A-Za-zА-Яа-яїЇіІєЄ0-9-]"));
        forCells.add(new VariablesForCell("specifications", "Специфікації"));
        forCells.add(new VariablesForCell("color", "Колір"));
        forCells.add(new VariablesForCell("price", "Ціна", "[0-9.]"));
        forCells.add(new VariablesForCell("guaranteeMonth", "Гарантія", "[0-9"));
        forCells.add(new VariablesForCell("graduationYear", "Кінець року", "[0-9]"));
        forCells.add(new VariablesForCell("salesInYear", "К-ість продаж у році", null));
        forCells.add(new VariablesForCell("firma", "Фірма", null));
        return forCells;
    }

}
