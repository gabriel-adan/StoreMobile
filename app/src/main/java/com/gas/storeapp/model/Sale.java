package com.gas.storeapp.model;

import java.util.Date;

public class Sale {
    private int id;
    private Date date;
    private String userName;
    private float total;

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getUserName() {
        return userName;
    }

    public float getTotal() {
        return total;
    }
}
