package com.kursova.kep.entity;

/**
 * by Mr Skip on 31.03.2016.
 */
public class Specifications extends BaseEntity {

    private String typeOfPrinter;
    private String size;
    private String typeOfCartridge;
    private Double weight;
    private Integer speed;
    private String cartrigeResource;
    private Integer interfaceI;

    public Specifications() {
    }

    public Specifications(Long id,
                          String typeOfPrinter,
                          String size,
                          String typeOfCartridge,
                          Double weight,
                          Integer speed,
                          String cartrigeResource,
                          Integer interfaceI
    ) {
        this.id = id;
        this.typeOfPrinter = typeOfPrinter;
        this.size = size;
        this.typeOfCartridge = typeOfCartridge;
        this.weight = weight;
        this.speed = speed;
        this.cartrigeResource = cartrigeResource;
        this.interfaceI = interfaceI;
    }

    public String getTypeOfPrinter() {
        return typeOfPrinter;
    }

    public void setTypeOfPrinter(String typeOfPrinter) {
        this.typeOfPrinter = typeOfPrinter;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTypeOfCartridge() {
        return typeOfCartridge;
    }

    public void setTypeOfCartridge(String typeOfCartridge) {
        this.typeOfCartridge = typeOfCartridge;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public String getCartrigeResource() {
        return cartrigeResource;
    }

    public void setCartrigeResource(String cartrigeResource) {
        this.cartrigeResource = cartrigeResource;
    }

    public Integer getInterfaceI() {
        return interfaceI;
    }

    public void setInterfaceI(Integer interfaceI) {
        this.interfaceI = interfaceI;
    }
}