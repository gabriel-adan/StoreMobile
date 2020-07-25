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
import com.gas.storeapp.model.Specification;

import java.util.ArrayList;
import java.util.List;

public class SpecificationAdapter extends ArrayAdapter<Specification> {
    private Context context;
    private List<Specification> specifications;

    public SpecificationAdapter(@NonNull Context context) {
        super(context, R.layout.specification_item);
        this.context = context;
        this.specifications = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.specification_item, parent, false);
        }
        Specification specification = specifications.get(position);
        TextView textDesc = view.findViewById(R.id.spec_desc);
        textDesc.setText(specification.getDescription());
        TextView textDetail = view.findViewById(R.id.spec_detail);
        textDetail.setText(specification.getDetail());
        TextView textCategory = view.findViewById(R.id.spec_category);
        textCategory.setText(specification.getCategory().getName());
        return view;
    }

    @Override
    public int getCount() {
        return specifications.size();
    }

    @Nullable
    @Override
    public Specification getItem(int position) {
        return specifications.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void clear() {
        specifications.clear();
        notifyDataSetChanged();
    }
}
