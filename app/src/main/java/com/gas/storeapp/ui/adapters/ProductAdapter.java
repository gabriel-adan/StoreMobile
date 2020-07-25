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

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    private Context context;
    private List<Product> products;

    public ProductAdapter(@NonNull Context context) {
        super(context, R.layout.product_item);
        this.context = context;
        products = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        }
        TextView textCode = view.findViewById(R.id.prod_code);
        TextView textDesc = view.findViewById(R.id.prod_desc);
        TextView textSizeColor = view.findViewById(R.id.prod_size_color);
        TextView textPrice = view.findViewById(R.id.prod_price);
        Product product = products.get(position);
        textCode.setText(product.getCode());
        textDesc.setText(product.getSpecification().getDescription());
        TextView textDetail = view.findViewById(R.id.prod_detail);
        textDetail.setText(product.getSpecification().getDetail());
        TextView textBrand = view.findViewById(R.id.prod_brand);
        textBrand.setText(product.getBrand());
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

    @Override
    public void clear() {
        products.clear();
        notifyDataSetChanged();
    }
}
