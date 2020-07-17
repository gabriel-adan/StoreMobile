package com.gas.storeapp.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.gas.storeapp.R;
import com.gas.storeapp.model.Product;

public class ProductInputDialogFragment extends DialogFragment {
    private INotifyOrderInputListener listener;
    private Product product;

    public ProductInputDialogFragment(INotifyOrderInputListener listener, Product product) {
        this.listener = listener;
        this.product = product;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.product_input, null);
        TextView textCode = view.findViewById(R.id.prod_code);
        TextView textDetail = view.findViewById(R.id.text_detail);
        TextView textPrice = view.findViewById(R.id.text_price);
        TextView textSize = view.findViewById(R.id.text_size);
        TextView textColor = view.findViewById(R.id.text_color);
        EditText textCost = view.findViewById(R.id.input_cost);
        EditText textAmount = view.findViewById(R.id.input_amount);
        textCode.setText(product.getCode());
        textDetail.setText(product.getSpecification().getDescription());
        textPrice.setText("Precio de Venta: $" + product.getPrice());
        textSize.setText("Talle: " + product.getSize().getName());
        textColor.setText("Color: " + product.getColor().getName());
        builder.setView(view)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        float cost = 0.0f;
                        int amount = 0;
                        try {
                            cost = Float.parseFloat(textCost.getText().toString());
                        } catch (Exception ex) {
                            textCost.setError("Debe ingresar el precio de costo");
                            return;
                        }
                        try {
                            amount = Integer.parseInt(textAmount.getText().toString());
                        } catch (Exception ex) {
                            textAmount.setError("Debe ingresar una cantidad");
                            return;
                        }
                        listener.OnPositiveClick(product, cost, amount);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.OnNegativeClick();
                    }
                });
        return builder.create();
    }

    public interface INotifyOrderInputListener {
        public void OnPositiveClick(Product product, float cost, int amount);
        public void OnNegativeClick();
    }
}
