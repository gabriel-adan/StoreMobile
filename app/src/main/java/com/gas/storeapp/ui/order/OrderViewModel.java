package com.gas.storeapp.ui.order;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gas.storeapp.model.OrderDetail;
import com.gas.storeapp.model.Product;
import com.gas.storeapp.services.HttpService;
import com.gas.storeapp.services.StandarResponse;
import com.gas.storeapp.services.catalog.ICatalogService;
import com.gas.storeapp.services.order.IOrderService;
import com.gas.storeapp.services.order.OrderData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderViewModel extends ViewModel {
    private ICatalogService productService;
    private MutableLiveData<List<Product>> productList;
    private List<OrderDetail> orderDetails;
    private IOrderService orderService;
    private MutableLiveData<String> messageOk;
    private MutableLiveData<String> messageError;

    public OrderViewModel() {
        productService = HttpService.createService(ICatalogService.class);
        productList = new MutableLiveData<>();
        orderDetails = new ArrayList<>();
        orderService = HttpService.createService(IOrderService.class);
        messageOk = new MutableLiveData<>();
        messageError = new MutableLiveData<>();
    }

    public LiveData<List<Product>> onProductList() {
        return productList;
    }

    public LiveData<String> onSuccess() {
        return messageOk;
    }

    public LiveData<String> onError() {
        return messageError;
    }

    public void findProduct(String code) {
        if (!TextUtils.isEmpty(code)) {
            productService.find(code).enqueue(new Callback<List<Product>>() {
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

    public void addOrderItem (OrderDetail item) {
        orderDetails.add(item);
    }

    public void removeOrderItem(int index) {
        orderDetails.remove(index);
    }

    public boolean register(Date date, String ticketCode) {
        try {
            OrderData orderBody = new OrderData(date, ticketCode);
            for (OrderDetail orderDetail : orderDetails) {
                orderBody.addOrderItem(orderDetail);
            }
            orderService.register(orderBody).enqueue(new Callback<StandarResponse<String>>() {
                @Override
                public void onResponse(Call<StandarResponse<String>> call, Response<StandarResponse<String>> response) {
                    if (response.isSuccessful()) {
                        orderDetails.clear();
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
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
