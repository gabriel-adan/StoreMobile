package com.gas.storeapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gas.storeapp.R;
import com.gas.storeapp.model.CreditResume;

import java.util.ArrayList;
import java.util.List;

public class CreditItemResumeAdapter extends ArrayAdapter<CreditResume.CreditItemResume> {

    private Context context;
    private List<CreditResume.CreditItemResume> itemResumes;

    public CreditItemResumeAdapter(@NonNull Context context) {
        super(context, R.layout.credit_item_detail);
        itemResumes = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.credit_item_detail, parent, false);
        }

        return view;
    }
}
