package com.gas.storeapp.services.sale;

import com.gas.storeapp.model.Sale;
import com.gas.storeapp.services.StandarResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ISaleService {
    @POST("api/Sales")
    Call<StandarResponse<String>> register(@Body SaleData body);

    @GET("api/Sales/Find/{date}")
    Call<List<Sale>> list(@Path("date") String date);
}
