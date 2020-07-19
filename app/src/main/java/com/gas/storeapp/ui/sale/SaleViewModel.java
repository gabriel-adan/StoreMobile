package com.gas.storeapp.ui.sale;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gas.storeapp.model.OrderDetail;
import com.gas.storeapp.model.Product;
import com.gas.storeapp.model.SaleDetail;
import com.gas.storeapp.services.HttpService;
import com.gas.storeapp.services.StandarResponse;
import com.gas.storeapp.services.catalog.ICatalogService;
import com.gas.storeapp.services.order.IOrderService;
import com.gas.storeapp.services.sale.ISaleService;
import com.gas.storeapp.services.sale.SaleData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleViewModel extends ViewModel {

    private ISaleService saleService;
    private ICatalogService catalogService;
    private IOrderService orderService;
    private MutableLiveData<List<Product>> productList;
    private List<SaleDetail> saleDetails;
    private MutableLiveData<String> messageOk;
    private MutableLiveData<String> messageError;
    private MutableLiveData<List<OrderDetail>> orderList;

    public SaleViewModel() {
        productList = new MutableLiveData<>();
        saleDetails = new ArrayList<>();
        saleService = HttpService.createService(ISaleService.class);
        catalogService = HttpService.createService(ICatalogService.class);
        orderService = HttpService.createService(IOrderService.class);
        messageOk = new MutableLiveData<>();
        messageError = new MutableLiveData<>();
        orderList = new MutableLiveData<>();
    }

    public void find(String code) {
        if (!TextUtils.isEmpty(code)) {
            catalogService.find(code).enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    if (response.isSuccessful()) {
                        productList.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {

                }
            });
        }
    }

    public void find(int productId, int amount) {
        orderService.find(productId, amount).enqueue(new Callback<List<OrderDetail>>() {
            @Override
            public void onResponse(Call<List<OrderDetail>> call, Response<List<OrderDetail>> response) {
                if (response.isSuccessful()) {
                    orderList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<OrderDetail>> call, Throwable t) {

            }
        });
    }

    public LiveData<List<Product>> onProductList() {
        return productList;
    }

    public LiveData<List<OrderDetail>> onOrderDetailList() {
        return orderList;
    }

    public void addSaleDetail(OrderDetail orderDetail, float unitPrice) {
        saleDetails.add(new SaleDetail(orderDetail, unitPrice));
    }

    public LiveData<String> onSuccess() {
        return messageOk;
    }

    public LiveData<String> onError() {
        return messageError;
    }

    public void register() {
        SaleData saleData = new SaleData(new Date());
        for (SaleDetail saleDetail : saleDetails) {
            int orderDetailId = saleDetail.getOrderDetail().getId();
            float unitPrice = saleDetail.getUnitPrice();
            saleData.addItem(orderDetailId, unitPrice);
        }
        saleService.register(saleData).enqueue(new Callback<StandarResponse<String>>() {
            @Override
            public void onResponse(Call<StandarResponse<String>> call, Response<StandarResponse<String>> response) {
                if (response.isSuccessful()){
                    saleDetails.clear();
                    messageOk.setValue(response.body().result);
                } else {
                    StandarResponse<String> result = HttpService.ResponseError(response.errorBody(), StandarResponse.class);
                    if (result != null && result.errorMessage != null) {
                        messageError.setValue(result.errorMessage);
                    } else {
                        messageError.setValue("Error de comunicación.");
                    }
                }
            }

            @Override
            public void onFailure(Call<StandarResponse<String>> call, Throwable t) {
                messageError.setValue("ERROR: " + t.getMessage());
            }
        });
    }
}
