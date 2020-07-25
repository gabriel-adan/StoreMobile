package com.gas.storeapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.gas.storeapp.R;
import com.gas.storeapp.model.Credit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.CreditViewHolder> {

    private List<Credit> creditList;
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private OnCreditClickListener creditClickListener;

    public CreditAdapter(OnCreditClickListener creditClickListener) {
        this.creditClickListener = creditClickListener;
        creditList = new ArrayList<>();
    }

    @NonNull
    @Override
    public CreditAdapter.CreditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.credit_item, parent, false);
        return new CreditViewHolder(view, creditClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditAdapter.CreditViewHolder holder, int position) {
        Credit credit = creditList.get(position);
        try {
            holder.textDate.setText("Fecha: " + format.format(credit.getDate()));
        } catch (Exception ex) {
            holder.textDate.setText("Fecha: ");
        }
        holder.textTotal.setText(String.format("Total: $ %.2f", credit.getTotal()));
        holder.textPaymed.setText(String.format("A cuenta: $ %.2f", credit.getPaymed()));
        holder.textBalance.setText(String.format("Saldo: $ %.2f", credit.getBalance()));
        holder.textUser.setText("Usuario: " + credit.getUserName());
    }

    @Override
    public int getItemCount() {
        return creditList.size();
    }

    public void add(Credit credit) {
        creditList.add(credit);
    }

    public void clear() {
        creditList.clear();
        notifyDataSetChanged();
    }

    public class CreditViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textDate;
        public TextView textTotal;
        public TextView textPaymed;
        public TextView textBalance;
        public TextView textUser;

        public CreditViewHolder(@NonNull View itemView, OnCreditClickListener creditClickListener) {
            super(itemView);
            textDate = itemView.findViewById(R.id.credit_date);
            textTotal = itemView.findViewById(R.id.credit_total);
            textPaymed = itemView.findViewById(R.id.credit_paymed);
            textBalance = itemView.findViewById(R.id.credit_balance);
            textUser = itemView.findViewById(R.id.credit_user);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Credit credit = creditList.get(getAdapterPosition());
            creditClickListener.OnCreditClick(view, credit);
        }
    }

    public interface OnCreditClickListener {
        void OnCreditClick(View view, Credit credit);
    }
}
