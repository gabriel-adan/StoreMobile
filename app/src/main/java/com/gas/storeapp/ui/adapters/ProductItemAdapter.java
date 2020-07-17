package com.gas.storeapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gas.storeapp.R;
import com.gas.storeapp.model.OrderDetail;

import java.util.ArrayList;
import java.util.List;

public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.ItemViewHolder> {
    private List<OrderDetail> orderDetails;

    public ProductItemAdapter() {
        orderDetails = new ArrayList<>();
    }

    public void addOrderDetail(OrderDetail orderDetail) {
        orderDetails.add(orderDetail);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.order_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductItemAdapter.ItemViewHolder holder, int position) {
        OrderDetail orderDetail = orderDetails.get(position);
        holder.textDesc.setText(orderDetail.getDescription());
        holder.textCode.setText(orderDetail.getCode());
        holder.textSizeColor.setText(orderDetail.getSizeColor());
        holder.textMarkType.setText(orderDetail.getMarkType());
        holder.textUnitCost.setText("Precio de Costo: $" + orderDetail.getUnitCost());
        holder.textAmount.setText(orderDetail.getAmount() + " Uds");
    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    public void clear() {
        orderDetails.clear();
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView textDesc;
        public TextView textCode;
        public TextView textSizeColor;
        public TextView textMarkType;
        public TextView textUnitCost;
        public TextView textAmount;

        public ItemViewHolder(@NonNull View view) {
            super(view);
            textDesc = view.findViewById(R.id.order_item_desc);
            textCode = view.findViewById(R.id.order_item_code);
            textSizeColor = view.findViewById(R.id.order_item_color_size);
            textMarkType = view.findViewById(R.id.order_item_mark_type);
            textUnitCost = view.findViewById(R.id.order_item_unit_cost);
            textAmount = view.findViewById(R.id.amount);
        }
    }
}
