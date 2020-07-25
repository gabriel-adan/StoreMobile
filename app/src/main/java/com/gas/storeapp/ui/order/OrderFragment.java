package com.gas.storeapp.ui.order;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.gas.storeapp.R;
import com.gas.storeapp.model.OrderDetail;
import com.gas.storeapp.model.Product;
import com.gas.storeapp.ui.adapters.ProductAdapter;
import com.gas.storeapp.ui.adapters.ProductItemAdapter;
import com.gas.storeapp.ui.dialogs.DatePickerDialogFragment;
import com.gas.storeapp.ui.dialogs.ProductInputDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderFragment extends Fragment implements ProductInputDialogFragment.INotifyOrderInputListener {

    private OrderViewModel mViewModel;
    private AutoCompleteTextView productSearch;
    private Context context;
    private ProductItemAdapter productItemAdapter;
    private EditText editDate, editTicketCode;

    public static OrderFragment newInstance() {
        return new OrderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        context = getContext();
        productSearch = view.findViewById(R.id.auto_search_prod);
        RecyclerView orderItemsView = view.findViewById(R.id.recycler_product_list);
        orderItemsView.setLayoutManager(new LinearLayoutManager(context));
        productItemAdapter = new ProductItemAdapter();
        orderItemsView.setAdapter(productItemAdapter);
        editTicketCode = view.findViewById(R.id.order_ticket_code);
        editDate = view.findViewById(R.id.order_date);
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialogFragment dialog = new DatePickerDialogFragment(editDate);
                dialog.show(getActivity().getSupportFragmentManager(), "OrderDate");
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        LifecycleOwner owner = getViewLifecycleOwner();
        ProductAdapter productAdapter = new ProductAdapter(context);
        productSearch.setAdapter(productAdapter);
        productSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == 66) {
                    mViewModel.findProduct(productSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });

        mViewModel.onProductList().observe(owner, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productAdapter.clear();
                for (Product product : products) {
                    productAdapter.add(product);
                }
                productAdapter.notifyDataSetChanged();
                if (products.size() > 0) {
                    productSearch.showDropDown();
                }
            }
        });

        productSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product product = (Product)adapterView.getItemAtPosition(i);
                productSearch.setText("", false);
                showInputDialog(product);
            }
        });

        mViewModel.onSuccess().observe(owner, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
                productItemAdapter.clear();
                editTicketCode.setText("");
                editDate.setText("");
            }
        });

        mViewModel.onError().observe(owner, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void OnPositiveClick(Product product, float cost, int amount) {
        OrderDetail orderDetail = new OrderDetail(product, cost, amount);
        productItemAdapter.addOrderDetail(orderDetail);
        mViewModel.addOrderItem(orderDetail);
    }

    @Override
    public void OnNegativeClick() {

    }

    private void showInputDialog(Product product) {
        ProductInputDialogFragment dialog = new ProductInputDialogFragment(this, product);
        dialog.show(getActivity().getSupportFragmentManager(), "OrderItem");
    }

    private void registerOrder() {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String strDate = editDate.getText().toString();
        try {
            date = format.parse(strDate);
        } catch (Exception ex) {
            editDate.setError("Debe indicar la fecha de la factura");
            Toast.makeText(context, "Debe indicar la fecha de la factura.", Toast.LENGTH_LONG).show();
            return;
        }
        String ticketCode = editTicketCode.getText().toString();
        if (TextUtils.isEmpty(ticketCode)) {
            editTicketCode.setError("Debe ingresar el número de factura.");
        } else {
            if (productItemAdapter.getItemCount() > 0) {
                mViewModel.register(date, ticketCode);
            } else {
                Toast.makeText(context, "No hay productos para registrar, ingrese los productos.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
