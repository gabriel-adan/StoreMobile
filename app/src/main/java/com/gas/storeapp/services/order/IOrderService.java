package com.gas.storeapp.services.order;

import com.gas.storeapp.model.OrderDetail;
import com.gas.storeapp.services.StandarResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IOrderService {
    @POST("api/Orders")
    Call<StandarResponse<String>> register(@Body OrderData body);

    @GET("api/Orders/Stock/{productId}/{amount}")
    Call<List<OrderDetail>> find(@Path("productId") int productId, @Path("amount") int amount);
}
