package com.gas.storeapp.services.credit;

import com.gas.storeapp.services.StandarResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ICreditService {
    @POST("api/Credits")
    Call<StandarResponse<String>> register(@Body CreditData body);
}
