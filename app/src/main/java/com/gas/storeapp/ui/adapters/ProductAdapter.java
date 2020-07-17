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
import com.gas.storeapp.model.Product;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    private Context context;
    private List<Product> products;

    public ProductAdapter(@NonNull Context context, @NonNull List<Product> objects) {
        super(context, R.layout.product_item, objects);
        this.context = context;
        products = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        }
        TextView textCode = view.findViewById(R.id.prod_code);
        TextView textDesc = view.findViewById(R.id.prod_desc);
        TextView textMarkType = view.findViewById(R.id.prod_mark_type);
        TextView textSizeColor = view.findViewById(R.id.prod_size_color);
        TextView textPrice = view.findViewById(R.id.prod_price);
        Product product = products.get(position);
        textCode.setText(product.getCode());
        textDesc.setText(product.getSpecification().getDescription());
        String markType = product.getMarkType();
        if (markType != null) {
            textMarkType.setText(markType);
        }
        textSizeColor.setText(product.getSizeColor());
        textPrice.setText("Precio $" + product.getPrice());
        return view;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Nullable
    @Override
    public Product getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void add(@Nullable Product object) {
        products.add(object);
    }
}
