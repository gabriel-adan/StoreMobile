package com.gas.storeapp.model;

import java.util.List;

public class CreditResume {
    private List<CreditItemResume> creditDetails;
    private List<Payment> payments;

    public List<CreditItemResume> getCreditDetails() {
        return creditDetails;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public class CreditItemResume {
        private int id;
        private String unitPrice;
        private String code;
        private String brand;
        private String description;
        private String detail;
        private String size;
        private String color;
        private String category;
    }
}
