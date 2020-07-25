package com.gas.storeapp.services.credit;

import com.gas.storeapp.model.Credit;
import com.gas.storeapp.model.CreditResume;
import com.gas.storeapp.model.Customer;
import com.gas.storeapp.services.StandarResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ICreditService {
    @POST("api/Credits")
    Call<StandarResponse<String>> register(@Body CreditData body);

    @GET("api/Credits/CustomerList")
    Call<List<Customer>> getCustomerList();

    @GET("api/Credits/GetCreditList/{customerId}")
    Call<List<Credit>> getCredits(@Path("customerId") int customerId);

    @GET("api/Credits/Detail/{id}")
    Call<CreditResume> getDetail(@Path("id") int id);
}
