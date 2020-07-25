package com.gas.storeapp.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.gas.storeapp.ui.credit.CreditDetailFragment;
import com.gas.storeapp.ui.credit.CreditPaymentFragment;

public class CreditPagerAdapter extends FragmentPagerAdapter {
    private int tabCount;
    public CreditPagerAdapter(@NonNull FragmentManager fm, int tabCount) {
        super(fm, tabCount);
        this.tabCount = tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CreditDetailFragment();
            case 1:
                return new CreditPaymentFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
