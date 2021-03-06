package com.gas.storeapp.model;

public class OrderDetail {
    private int id;
    private float unitCost;
    private int amount;

    private Product product;

    public OrderDetail() {

    }

    public OrderDetail(Product product, float unitCost, int amount) {
        this.product = product;
        this.unitCost = unitCost;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return product.getId();
    }

    public String getCode() {
        return product.getCode();
    }

    public String getDescription() {
        return product.getSpecification().getDescription();
    }

    public float getUnitCost() {
        return unitCost;
    }

    public int getAmount() {
        return amount;
    }

    public String getSizeColor() {
        return product.getSizeColor();
    }

    public float getPrice() {
        return product.getPrice();
    }

    public Product getProduct() {
        return product;
    }

    public String getBrand() {
        return product.getBrand();
    }

    public String getDetail() {
        return product.getSpecification().getDetail();
    }

    public String getBrandDetail() {
        return product.getBrandDetail();
    }
}
