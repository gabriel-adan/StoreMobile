package com.gas.storeapp.ui.credit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gas.storeapp.model.Credit;
import com.gas.storeapp.model.CreditResume;
import com.gas.storeapp.model.Customer;
import com.gas.storeapp.services.HttpService;
import com.gas.storeapp.services.credit.ICreditService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainCreditViewModel extends ViewModel {

    private ICreditService creditService;
    private MutableLiveData<List<Credit>> creditList;
    private MutableLiveData<List<Customer>> customerList;
    private MutableLiveData<CreditResume> creditResume;
    private Credit creditSelected;
    private CreditResume creditResumeSelected;

    public MainCreditViewModel() {
        creditService = HttpService.createService(ICreditService.class);
        creditList = new MutableLiveData<>();
        customerList = new MutableLiveData<>();
        creditResume = new MutableLiveData<>();
    }

    public void getCredits(Customer customer) {
        creditService.getCredits(customer.getId()).enqueue(new Callback<List<Credit>>() {
            @Override
            public void onResponse(Call<List<Credit>> call, Response<List<Credit>> response) {
                if (response.isSuccessful()) {
                    creditList.setValue(response.body());
                } else {
                    creditList.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<Credit>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public LiveData<List<Credit>> onCreditList() {
        return creditList;
    }

    public void getCustomers() {
        creditService.getCustomerList().enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (response.isSuccessful()) {
                    customerList.setValue(response.body());
                } else {
                    customerList.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {

            }
        });
    }

    public LiveData<List<Customer>> onCustomerList() {
        return customerList;
    }

    public void getCreditResume() {
        if (creditSelected != null) {
            creditService.getDetail(creditSelected.getId()).enqueue(new Callback<CreditResume>() {
                @Override
                public void onResponse(Call<CreditResume> call, Response<CreditResume> response) {
                    if (response.isSuccessful()) {
                        creditResume.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<CreditResume> call, Throwable t) {

                }
            });
        }
    }

    public void selectCredit(Credit credit) {
        creditSelected = credit;
    }

    public LiveData<CreditResume> onCreditResume() {
        return creditResume;
    }

    public void setCreditResumeSelected(CreditResume creditResumeSelected) {
        this.creditResumeSelected = creditResumeSelected;
    }
}
