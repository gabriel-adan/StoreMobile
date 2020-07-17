package com.gas.storeapp.services.order;

import com.gas.storeapp.model.OrderDetail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderData {
    private Date date;
    private String ticketCode;
    private List<Integer> productIds;
    private List<Float> unitCosts;
    private List<Integer> amounts;

    public OrderData(Date date, String ticketCode) {
        this.date = date;
        this.ticketCode = ticketCode;
        productIds = new ArrayList<>();
        unitCosts = new ArrayList<>();
        amounts = new ArrayList<>();
    }

    public void addOrderItem(OrderDetail orderDetail) {
        productIds.add(orderDetail.getProductId());
        unitCosts.add(orderDetail.getUnitCost());
        amounts.add(orderDetail.getAmount());
    }
}
