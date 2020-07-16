package com.gas.storeapp.model;

public class User {
    private String name;
    private String fullName;
    private String token;

    public User(String name, String fullName, String token) {
        this.name = name;
        this.fullName = fullName;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getToken() { return token; }
}
