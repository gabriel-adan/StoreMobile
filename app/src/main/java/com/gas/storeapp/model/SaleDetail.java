package com.gas.storeapp.model;

public class SaleDetail {
    private int id;
    private float unitPrice;
    private OrderDetail orderDetail;

    public SaleDetail() {

    }

    public SaleDetail(OrderDetail orderDetail, float unitPrice) {
        this.orderDetail = orderDetail;
        this.unitPrice = unitPrice;
    }

    public int getId() {
        return id;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }
}
