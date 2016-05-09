package com.kursova.kep.entity;

/**
 * by Mr Skip on 31.03.2016.
 */
public class Printer extends BaseWithName {

    private String model;
    private String color;
    private Double price;
    private Integer guaranteeMonth;
    private String graduationYear;
    private Integer salesInYear;
    private Firma firma;
    private Specifications specifications;

    public Printer() {
    }

    public Printer(Long id,
                   String name,
                   String model,
                   String color,
                   Double price,
                   Integer guaranteeMonth,
                   String graduationYear,
                   Integer salesInYear,
                   Firma firma,
                   Specifications specifications
    ) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.color = color;
        this.price = price;
        this.guaranteeMonth = guaranteeMonth;
        this.graduationYear = graduationYear;
        this.salesInYear = salesInYear;
        this.firma = firma;
        this.specifications = specifications;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getGuaranteeMonth() {
        return guaranteeMonth;
    }

    public void setGuaranteeMonth(Integer guaranteeMonth) {
        this.guaranteeMonth = guaranteeMonth;
    }

    public String getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(String graduationYear) {
        this.graduationYear = graduationYear;
    }

    public Integer getSalesInYear() {
        return salesInYear;
    }

    public void setSalesInYear(Integer salesInYear) {
        this.salesInYear = salesInYear;
    }

    public Firma getFirma() {
        return firma;
    }

    public void setFirma(Firma firma) {
        this.firma = firma;
    }

    public Specifications getSpecifications() {
        return specifications;
    }

    public void setSpecifications(Specifications specifications) {
        this.specifications = specifications;
    }
}
