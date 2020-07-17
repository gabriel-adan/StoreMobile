package com.gas.storeapp.services.catalog;

public class ProductData {
    private String code;
    private float price;
    private String description;
    private String mark;
    private String type;
    private int specification;
    private int color;
    private int size;
    private int category;

    public ProductData(String code, float price, String description, String mark, String type, int specification, int color, int size, int category) {
        this.code = code;
        this.price = price;
        this.description = description;
        this.mark = mark;
        this.type = type;
        this.specification = specification;
        this.color = color;
        this.size = size;
        this.category = category;
    }
}
