package com.kursova.kep.entity;

/**
 * Created by Mr. Skip.
 */

public class BaseWithName extends BaseEntity {

    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}