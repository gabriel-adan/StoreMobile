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
        private String code;
        private String description;
        private String detail;
        private String brand;
        private String unitPrice;
        private String size;
        private String color;
        private String category;

        public String getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public String getUnitPrice() {
            return unitPrice;
        }

        public String getBrandDetail() {
            if (brand != null && detail != null) {
                return "Marca: " + brand + " Detalles: " + detail;
            } else {
                if (brand != null && detail == null) {
                    return "Marca: " + brand;
                } else {
                    if (brand == null && detail != null) {
                        return "Detalles: " + detail;
                    }
                }
            }
            return null;
        }

        public String getSizeColor() {
            return "Talle: " + size + " Color: " + color;
        }

        public String getCategory() {
            return category;
        }
    }
}
