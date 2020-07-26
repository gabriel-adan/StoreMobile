package com.gas.storeapp.ui.credit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.gas.storeapp.R;
import com.gas.storeapp.model.CreditResume;
import com.gas.storeapp.ui.adapters.CreditItemResumeAdapter;

import java.util.List;

public class CreditDetailFragment extends Fragment {

    private MainCreditViewModel mViewModel;
    private CreditItemResumeAdapter creditItemResumeAdapter;
    ListView listView;

    public static CreditDetailFragment newInstance() {
        return new CreditDetailFragment();
    }

    public CreditDetailFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LifecycleOwner owner = getViewLifecycleOwner();
        mViewModel = new ViewModelProvider(requireActivity()).get(MainCreditViewModel.class);
        mViewModel.onCreditItemResume().observe(owner, new Observer<List<CreditResume.CreditItemResume>>() {
            @Override
            public void onChanged(List<CreditResume.CreditItemResume> creditItemResumes) {
                creditItemResumeAdapter.clear();
                for (CreditResume.CreditItemResume creditItemResume : creditItemResumes) {
                    creditItemResumeAdapter.add(creditItemResume);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_credit_detail, container, false);
        creditItemResumeAdapter = new CreditItemResumeAdapter(getContext());
        listView = view.findViewById(R.id.list_view_credit_item);
        listView.setAdapter(creditItemResumeAdapter);
        return view;
    }
}
