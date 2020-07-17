package com.gas.storeapp.services.order;

import com.gas.storeapp.services.StandarResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IOrderService {
    @POST("api/Orders")
    Call<StandarResponse<String>> register(@Body OrderData body);
}
