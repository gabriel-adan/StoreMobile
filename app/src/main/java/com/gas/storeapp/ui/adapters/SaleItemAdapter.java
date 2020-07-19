package com.gas.storeapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gas.storeapp.R;

import java.util.ArrayList;
import java.util.List;

public class SaleItemAdapter extends RecyclerView.Adapter<SaleItemAdapter.SaleViewHolder> {
    private List<SaleItem> saleItems;

    public SaleItemAdapter() {
        saleItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public SaleItemAdapter.SaleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sale_detail_item, parent, false);
        return new SaleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleItemAdapter.SaleViewHolder holder, int position) {
        SaleItem saleItem = saleItems.get(position);
        holder.textCode.setText(saleItem.getProduct().getCode());
        holder.textViewDescription.setText(saleItem.getProduct().getSpecification().getDescription());
        holder.textDetail.setText(saleItem.getProduct().getSpecification().getDetail());
        holder.textBrand.setText(saleItem.getProduct().getBrand());
        holder.textSizeColor.setText(saleItem.getProduct().getSizeColor());
        int amount = saleItem.getAmount();
        holder.textAmount.setText("Cant: " + amount + " u");
        float unitPrice = saleItem.getUnitPrice();
        holder.textUnitPrice.setText("Prec: " + unitPrice);
        float subTotal = (amount * unitPrice);
        holder.textSubTotal.setText("$ " + subTotal);
    }

    @Override
    public int getItemCount() {
        return saleItems.size();
    }

    public void addSaleDetail(SaleItem saleItem) {
        saleItems.add(saleItem);
        notifyDataSetChanged();
    }

    public void clear() {
        saleItems.clear();
        notifyDataSetChanged();
    }

    public class SaleViewHolder extends RecyclerView.ViewHolder {
        public TextView textCode;
        public TextView textViewDescription;
        public TextView textDetail;
        public TextView textBrand;
        public TextView textSizeColor;
        public TextView textAmount;
        public TextView textUnitPrice;
        public TextView textSubTotal;

        public SaleViewHolder(@NonNull View itemView) {
            super(itemView);
            textCode = itemView.findViewById(R.id.sdi_code);
            textViewDescription = itemView.findViewById(R.id.sdi_description);
            textDetail = itemView.findViewById(R.id.sdi_detail);
            textBrand = itemView.findViewById(R.id.sdi_brand);
            textSizeColor = itemView.findViewById(R.id.sdi_size_color);
            textAmount = itemView.findViewById(R.id.sdi_unit_price);
            textUnitPrice = itemView.findViewById(R.id.sdi_unit_price);
            textSubTotal = itemView.findViewById(R.id.sdi_sub_total);
        }
    }
}
