package com.gas.storeapp.services.credit;

import com.gas.storeapp.services.sale.SaleData;

import java.util.Date;

public class CreditData extends SaleData {
    private int customerId;

    public CreditData(Date date, int customerId) {
        super(date);
        this.customerId = customerId;
    }
}
