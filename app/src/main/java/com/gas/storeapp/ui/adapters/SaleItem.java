package com.gas.storeapp.ui.adapters;

import com.gas.storeapp.model.Product;

public class SaleItem {
    private float unitPrice;
    private int amount;
    private Product product;

    public SaleItem(float unitPrice, int amount, Product product) {
        this.unitPrice = unitPrice;
        this.amount = amount;
        this.product = product;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public int getAmount() {
        return amount;
    }

    public Product getProduct() {
        return product;
    }
}
