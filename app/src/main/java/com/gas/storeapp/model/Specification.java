package com.gas.storeapp.model;

public class Specification {
    private int id;
    private String description;
    private String detail;

    private Item category;

    public Specification() {

    }

    public Specification(String desc, String detail, Item category) {
        this.description = desc;
        this.detail = detail;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Item getCategory() {
        return category;
    }

    public void setCategory(Item category) {
        this.category = category;
    }
}
