package com.gas.storeapp.model;

public class Specification {
    private int id;
    private String description;
    private String mark;
    private String type;

    private Item category;

    public Specification() {

    }

    public Specification(String desc, String mark, String type, Item category) {
        this.description = desc;
        this.mark = mark;
        this.type = type;
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

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Item getCategory() {
        return category;
    }

    public void setCategory(Item category) {
        this.category = category;
    }
}
