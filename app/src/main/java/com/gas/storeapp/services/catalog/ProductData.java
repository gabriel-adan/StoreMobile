package com.gas.storeapp.services.catalog;

public class ProductData {
    private String code;
    private float price;
    private String description;
    private String brand;
    private int specification;
    private String detail;
    private int color;
    private int size;
    private int category;

    public ProductData(String code, float price, String description, String brand, String detail, int specification, int color, int size, int category) {
        this.code = code;
        this.price = price;
        this.description = description;
        this.brand = brand;
        this.detail = detail;
        this.specification = specification;
        this.color = color;
        this.size = size;
        this.category = category;
    }
}
