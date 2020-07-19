package com.gas.storeapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gas.storeapp.R;
import com.gas.storeapp.model.Sale;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.SaleViewHolder> {
    private List<Sale> saleList;
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    public SaleAdapter() {
        saleList = new ArrayList<>();
    }

    @NonNull
    @Override
    public SaleAdapter.SaleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sale_item, parent, false);
        return new SaleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleAdapter.SaleViewHolder holder, int position) {
        Sale sale = saleList.get(position);
        try {
            holder.textDate.setText("Fecha: " + format.format(sale.getDate()));
        } catch (Exception ex) {
            holder.textDate.setText("Fecha: ");
        }
        holder.textUser.setText("Vendedor: " + sale.getUserName());
        holder.textTotal.setText(String.format("$ %.2f", sale.getTotal()));
    }

    public void addSale(Sale sale) {
        saleList.add(sale);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return saleList.size();
    }

    public void clear() {
        saleList.clear();
        notifyDataSetChanged();
    }

    public class SaleViewHolder extends RecyclerView.ViewHolder {
        public TextView textDate;
        public TextView textUser;
        public TextView textTotal;

        public SaleViewHolder(@NonNull View itemView) {
            super(itemView);
            textDate = itemView.findViewById(R.id.txtvw_date);
            textUser = itemView.findViewById(R.id.txtvw_user);
            textTotal = itemView.findViewById(R.id.txtvw_total);
        }
    }
}
