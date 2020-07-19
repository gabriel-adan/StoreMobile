package com.gas.storeapp.services.sale;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SaleData {
    private Date date;
    private List<Integer> orderDetailIds;
    private List<Float> unitPrices;

    public SaleData(Date date) {
        this.date = date;
        this.orderDetailIds = new ArrayList<>();
        this.unitPrices = new ArrayList<>();
    }

    public void addItem(int orderId, float unitPrice) {
        orderDetailIds.add(orderId);
        unitPrices.add(unitPrice);
    }
}
