package com.kursova.kep.test;

import com.kursova.kep.entity.BaseEntity;
import com.kursova.kep.entity.BaseWithName;
import com.kursova.kep.entity.Printer;

/**
 * Created by Mr. Skip.
 */
public class TestINstatceof {

    public static void main(String[] args) {
        Object o = "str";

        BaseEntity entity = new BaseEntity();
        BaseWithName baseWithName = new BaseWithName();
        Printer printer = new Printer();

        System.out.println(printer instanceof BaseEntity);
    }

}
