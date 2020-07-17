package com.gas.storeapp.model;

public class Product {
    private int id;
    private String code;
    private float price;

    private Specification specification;
    private Item size;
    private Item color;

    public Product() {

    }

    public Product(String code, float price, String desc, String mark, String type, Item category, Item size, Item color) {
        this.code = code;
        this.price = price;
        this.specification = new Specification(desc, mark, type, category);
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

    public String getSizeColor() {
        return "Talle: " + this.size.getName() + " Color: " + this.color.getName();
    }

    public String getMarkType() {
        String mark = specification.getMark();
        String type = specification.getType();
        if (mark != null && type != null) {
            return "Marca: " + mark + " Detalles: " + type;
        } else {
            if (mark == null && type != null) {
                return "Detalles: " + type;
            } else {
                if (mark != null && type == null) {
                    return "Marca: " + mark;
                } else {
                    return null;
                }
            }
        }
    }
}
