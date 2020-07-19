package com.gas.storeapp.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.DecodeException;
import com.auth0.android.jwt.JWT;
import com.gas.storeapp.model.User;
import com.gas.storeapp.services.HttpService;
import com.gas.storeapp.services.StandarResponse;
import com.gas.storeapp.services.auth.AuthData;
import com.gas.storeapp.services.auth.AuthResult;
import com.gas.storeapp.services.auth.IAuthService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<User> tokenLiveData;
    private MutableLiveData<String> errorLiveData;

    public LoginViewModel() {
        tokenLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
    }

    public LiveData<User> onSuccessLogin() {
        return tokenLiveData;
    }

    public LiveData<String> onErrorLogin() {
        return errorLiveData;
    }

    public void logIn(String username, String password) {
        AuthData authData = new AuthData(username, password);
        IAuthService authService = HttpService.createService(IAuthService.class);
        authService.logIn(authData).enqueue(new Callback<AuthResult>() {
            @Override
            public void onResponse(Call<AuthResult> call, Response<AuthResult> response) {
                if (response.isSuccessful()) {
                    AuthResult authResult = response.body();
                    HttpService.TOKEN = authResult.access_token;
                    try {
                        JWT jwt = new JWT(authResult.access_token);
                        System.out.println(authResult.access_token);
                        Claim userName = jwt.getClaim("User");
                        Claim accountName = jwt.getClaim("UserName");
                        User user = new User(userName.asString(), accountName.asString(), authResult.access_token);
                        tokenLiveData.setValue(user);
                    } catch (DecodeException e) {
                        errorLiveData.setValue("Error al iniciar sesion...");
                    }
                } else {
                    StandarResponse<String> result = HttpService.ResponseError(response.errorBody(), StandarResponse.class);
                    if (result != null && result.errorMessage != null) {
                        errorLiveData.setValue(result.errorMessage);
                    } else {
                        errorLiveData.setValue("Error de comunicación: " + response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthResult> call, Throwable t) {
                errorLiveData.setValue("ERROR: " + t.getMessage());
            }
        });
    }
}
