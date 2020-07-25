package com.gas.storeapp.model;

import androidx.annotation.NonNull;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private boolean isEnabled;

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    @NonNull
    @Override
    public String toString() {
        String fullName = null;
        if (firstName != null && lastName == null)
            return firstName;
        if (firstName == null && lastName != null)
            return lastName;
        return firstName + " " + lastName;
    }
}
