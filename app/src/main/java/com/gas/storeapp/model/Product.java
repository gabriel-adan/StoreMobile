package com.gas.storeapp.model;

public class Product {
    private int id;
    private String code;
    private float price;
    private String brand;

    private Specification specification;
    private Item size;
    private Item color;

    public Product() {

    }

    public Product(String code, float price, String brand, String desc, String detail, String type, Item category, Item size, Item color) {
        this.code = code;
        this.price = price;
        this.brand = brand;
        this.specification = new Specification(desc, detail, category);
        this.size = size;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public Item getSize() {
        return size;
    }

    public void setSize(Item size) {
        this.size = size;
    }

    public Item getColor() {
        return color;
    }

    public void setColor(Item color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSizeColor() {
        return "Talle: " + this.size.getName() + " Color: " + this.color.getName();
    }

    public String getBrandDetail() {
        String detail = specification.getDetail();
        if (brand != null && detail != null) {
            return "Marca: " + brand + " Detalles: " + detail;
        } else {
            if (brand != null && detail == null) {
                return "Marca: " + brand;
            } else {
                if (brand == null && detail != null) {
                    return "Detalles: " + detail;
                }
            }
        }
        return null;
    }
}
