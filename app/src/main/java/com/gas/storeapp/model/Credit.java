package com.gas.storeapp.model;

import java.util.Date;

public class Credit {
    private int id;
    private Date date;
    private String userName;
    private float total;
    private float paymed;

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

    public float getPaymed() {
        return paymed;
    }

    public float getBalance() {
        return total - paymed;
    }
}
