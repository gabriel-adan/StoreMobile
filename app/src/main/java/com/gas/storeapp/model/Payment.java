package com.gas.storeapp.model;

import java.util.Date;

public class Payment {
    private int id;
    private Date date;
    private String description;
    private float amount;

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public float getAmount() {
        return amount;
    }
}
