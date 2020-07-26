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
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.gas.storeapp.R;
import com.gas.storeapp.model.CreditResume;
import com.gas.storeapp.ui.adapters.CreditPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class CreditResumeFragment extends Fragment {

    private MainCreditViewModel mViewModel;
    private CreditPagerAdapter creditPagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(MainCreditViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_credit_resume, container, false);
        TabLayout tabLayout = view.findViewById(R.id.credit_tabs);
        ViewPager viewPager = view.findViewById(R.id.credit_viewpager);
        tabLayout.addTab(tabLayout.newTab().setText("Artículos"));
        tabLayout.addTab(tabLayout.newTab().setText("Pagos"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        creditPagerAdapter = new CreditPagerAdapter(getParentFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(creditPagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle creditId = getArguments();
        mViewModel.getCreditResume(creditId.getInt("id"));
    }
}
