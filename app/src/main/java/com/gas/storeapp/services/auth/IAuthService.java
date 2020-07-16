package com.gas.storeapp.services.auth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IAuthService {
    @POST("api/Accounts")
    Call<AuthResult> logIn(@Body AuthData user);
}
