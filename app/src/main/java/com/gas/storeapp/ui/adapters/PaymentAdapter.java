package com.gas.storeapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gas.storeapp.R;
import com.gas.storeapp.model.Payment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {
    private List<Payment> payments;
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    public PaymentAdapter() {
        payments = new ArrayList<>();
    }

    @NonNull
    @Override
    public PaymentAdapter.PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.credit_item_payment, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentAdapter.PaymentViewHolder holder, int position) {
        Payment payment = payments.get(position);
        try {
            holder.textDate.setText("Fecha: " + format.format(payment.getDate()));
        } catch (Exception ex) {
            holder.textDate.setText("Fecha: ");
        }
        holder.textDesc.setText(payment.getDescription());
        holder.textAmount.setText(String.format("Monto pagado: $ %.2f", payment.getAmount()));
    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public void add(Payment payment) {
        payments.add(payment);
    }

    public void clear() {
        payments.clear();
        notifyDataSetChanged();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder {
        public TextView textDate;
        public TextView textDesc;
        public TextView textAmount;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            textDate = itemView.findViewById(R.id.tv_payment_date);
            textDesc = itemView.findViewById(R.id.tv_payment_desc);
            textAmount = itemView.findViewById(R.id.tv_payment_amount);
        }
    }
}
