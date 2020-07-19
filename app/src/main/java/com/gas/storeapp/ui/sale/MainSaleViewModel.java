package com.gas.storeapp.ui.sale;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gas.storeapp.model.Sale;
import com.gas.storeapp.services.HttpService;
import com.gas.storeapp.services.StandarResponse;
import com.gas.storeapp.services.sale.ISaleService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainSaleViewModel extends ViewModel {
    private ISaleService saleService;
    private MutableLiveData<List<Sale>> saleList;

    public MainSaleViewModel() {
        saleService = HttpService.createService(ISaleService.class);
        saleList = new MutableLiveData<>();
        Date currentTime = Calendar.getInstance().getTime();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(currentTime);
        saleService.list(date).enqueue(new Callback<List<Sale>>() {
            @Override
            public void onResponse(Call<List<Sale>> call, Response<List<Sale>> response) {
                if (response.isSuccessful()) {
                    saleList.setValue(response.body());
                } else {
                    StandarResponse<String> result = HttpService.ResponseError(response.errorBody(), StandarResponse.class);
                    if (result != null && result.errorMessage != null) {

                    } else {

                    }
                    saleList.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<Sale>> call, Throwable t) {

            }
        });
    }

    public LiveData<List<Sale>> onSaleList() {
        return saleList;
    }
}
