package com.gas.storeapp.ui.credit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gas.storeapp.R;
import com.gas.storeapp.model.Payment;
import com.gas.storeapp.ui.adapters.PaymentAdapter;

import java.util.List;

public class CreditPaymentFragment extends Fragment {

    private MainCreditViewModel mViewModel;
    private PaymentAdapter paymentAdapter;

    public static CreditPaymentFragment newInstance() {
        return new CreditPaymentFragment();
    }

    public CreditPaymentFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_credit_payment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rec_view_paymets);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        paymentAdapter = new PaymentAdapter();
        recyclerView.setAdapter(paymentAdapter);

        LifecycleOwner owner = getViewLifecycleOwner();
        mViewModel = new ViewModelProvider(requireActivity()).get(MainCreditViewModel.class);
        mViewModel.onPayments().observe(owner, new Observer<List<Payment>>() {
            @Override
            public void onChanged(List<Payment> payments) {
                paymentAdapter.clear();
                for (Payment payment : payments) {
                    paymentAdapter.add(payment);
                }
            }
        });
        return view;
    }
}
