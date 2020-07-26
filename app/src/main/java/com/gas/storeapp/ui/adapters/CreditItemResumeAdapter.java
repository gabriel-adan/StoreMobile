package com.gas.storeapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gas.storeapp.R;
import com.gas.storeapp.model.CreditResume;

import java.util.ArrayList;
import java.util.List;

public class CreditItemResumeAdapter extends ArrayAdapter<CreditResume.CreditItemResume> {

    private List<CreditResume.CreditItemResume> itemResumes;

    public CreditItemResumeAdapter(@NonNull Context context) {
        super(context, R.layout.credit_item_detail);
        itemResumes = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.credit_item_detail, parent, false);
        }
        CreditResume.CreditItemResume creditItemResume = itemResumes.get(position);
        TextView textCode = view.findViewById(R.id.tv_credit_code);
        TextView textDesc = view.findViewById(R.id.tv_credit_desc);
        TextView textBrandDetail = view.findViewById(R.id.tv_credit_brand_detail);
        TextView textSizeColor = view.findViewById(R.id.tv_credit_size_color);
        TextView textCategory = view.findViewById(R.id.tv_category);
        TextView textPrice = view.findViewById(R.id.tv_credit_price);

        textCode.setText(creditItemResume.getCode());
        textDesc.setText(creditItemResume.getDescription());
        textBrandDetail.setText(creditItemResume.getBrandDetail());
        textSizeColor.setText(creditItemResume.getSizeColor());
        textCategory.setText("Categoría: " + creditItemResume.getCategory());
        textPrice.setText("Precio: $ " + creditItemResume.getUnitPrice());
        return view;
    }

    @Override
    public int getCount() {
        return itemResumes.size();
    }

    @Nullable
    @Override
    public CreditResume.CreditItemResume getItem(int position) {
        return itemResumes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void add(@Nullable CreditResume.CreditItemResume object) {
        itemResumes.add(object);
    }

    @Override
    public void clear() {
        itemResumes.clear();
        notifyDataSetChanged();
    }
}
