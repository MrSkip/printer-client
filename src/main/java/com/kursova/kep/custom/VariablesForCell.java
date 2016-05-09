package com.kursova.kep.custom;

/**
 * Created by Mr. Skip.
 */

public class VariablesForCell {
    private String originCellName;
    private String customCellName;
    private String regularExpress;

    public VariablesForCell(String originCellName, String customCellName, String regularExpress) {
        this.originCellName = originCellName;
        this.customCellName = customCellName;
        this.regularExpress = regularExpress;
    }

    public VariablesForCell(String originCellName, String customCellName) {
        this.originCellName = originCellName;
        this.customCellName = customCellName;
    }

    public String getOriginCellName() {
        return originCellName;
    }

    public void setOriginCellName(String originCellName) {
        this.originCellName = originCellName;
    }

    public String getCustomCellName() {
        return customCellName;
    }

    public void setCustomCellName(String customCellName) {
        this.customCellName = customCellName;
    }

    public String getRegularExpress() {
        return regularExpress;
    }

    public void setRegularExpress(String regularExpress) {
        this.regularExpress = regularExpress;
    }
}
