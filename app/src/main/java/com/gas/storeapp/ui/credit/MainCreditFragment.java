package com.gas.storeapp.ui.credit;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.gas.storeapp.R;
import com.gas.storeapp.model.Credit;
import com.gas.storeapp.model.Customer;
import com.gas.storeapp.ui.adapters.CreditAdapter;

import java.util.List;

public class MainCreditFragment extends Fragment implements AdapterView.OnItemSelectedListener, CreditAdapter.OnCreditClickListener {

    private MainCreditViewModel mViewModel;
    private Context context;
    private AppCompatSpinner customerSpinner;
    private CreditAdapter creditAdapter;

    public static MainCreditFragment newInstance() {
        return new MainCreditFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_credit_main, container, false);
        customerSpinner = view.findViewById(R.id.spinner_customers);
        customerSpinner.setOnItemSelectedListener(this);
        RecyclerView creditRecyclerView = view.findViewById(R.id.recycler_credits);
        creditRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        creditAdapter = new CreditAdapter(this);
        creditRecyclerView.setAdapter(creditAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainCreditViewModel.class);
        context = getContext();
        LifecycleOwner owner = getViewLifecycleOwner();
        mViewModel.onCustomerList().observe(owner, new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customers) {
                ArrayAdapter<Customer> customerAdapter = new ArrayAdapter<Customer>(context, android.R.layout.simple_spinner_dropdown_item, customers);
                customerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                customerSpinner.setAdapter(customerAdapter);
            }
        });
        mViewModel.onCreditList().observe(owner, new Observer<List<Credit>>() {
            @Override
            public void onChanged(List<Credit> credits) {
                creditAdapter.clear();
                for (Credit credit : credits) {
                    creditAdapter.add(credit);
                }
                creditAdapter.notifyDataSetChanged();
            }
        });
        mViewModel.getCustomers();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Customer customer = (Customer) adapterView.getSelectedItem();
        if (customer != null) {
            mViewModel.getCredits(customer);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void OnCreditClick(View view, Credit credit) {
        mViewModel.selectCredit(credit);
        Navigation.findNavController(view).navigate(R.id.action_nav_main_credit_to_creditResumeFragment);
    }
}
