package com.gas.storeapp.ui.sale;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.gas.storeapp.R;
import com.gas.storeapp.model.OrderDetail;
import com.gas.storeapp.model.Product;
import com.gas.storeapp.model.SaleDetail;
import com.gas.storeapp.ui.adapters.ProductAdapter;
import com.gas.storeapp.ui.adapters.SaleItem;
import com.gas.storeapp.ui.adapters.SaleItemAdapter;
import com.gas.storeapp.ui.dialogs.ProductInputDialogFragment;

import java.util.List;

public class SaleFragment extends Fragment implements ProductInputDialogFragment.INotifyOrderInputListener {

    private SaleViewModel mViewModel;
    private AutoCompleteTextView productSearch;
    private Context context;
    private SaleItemAdapter saleItemAdapter;
    private Product currentProduct;
    private float currentUnitPrice = 0;
    private int currentAmount = 0;
    private float total = 0.00f;
    private TextView totalTextView;

    public static SaleFragment newInstance() {
        return new SaleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale, container, false);
        context = getContext();
        productSearch = view.findViewById(R.id.sale_search_prod);
        RecyclerView saleItemsView = view.findViewById(R.id.recycler_sale_detail_list);
        saleItemsView.setLayoutManager(new LinearLayoutManager(context));
        saleItemAdapter = new SaleItemAdapter();
        saleItemsView.setAdapter(saleItemAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SaleViewModel.class);
        LifecycleOwner owner = getViewLifecycleOwner();
        ProductAdapter productAdapter = new ProductAdapter(context);
        productSearch.setAdapter(productAdapter);
        productSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == 66) {
                    mViewModel.find(productSearch.getText().toString());
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
                Product product = (Product) adapterView.getItemAtPosition(i);
                productSearch.setText("", false);
                showInputDialog(product);
            }
        });
        mViewModel.onOrderDetailList().observe(owner, new Observer<List<OrderDetail>>() {
            @Override
            public void onChanged(List<OrderDetail> orderDetails) {
                if (currentProduct != null && currentAmount > 0) {
                    int count = orderDetails.size();
                    if (currentAmount == count) {
                        saleItemAdapter.addSaleDetail(new SaleItem(currentUnitPrice, currentAmount, currentProduct));
                        for (OrderDetail orderDetail : orderDetails) {
                            mViewModel.addSaleDetail(orderDetail, currentUnitPrice);
                        }
                        total += currentAmount * currentUnitPrice;
                        totalTextView.setText(String.format("$ %.2f", total));
                    } else {
                        if (count == 0)
                            Toast.makeText(context, "No hay unidades en stock.", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(context, "Solo quedan " + count + " unidades en stock.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        mViewModel.onSuccess().observe(owner, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                saleItemAdapter.clear();
                total = 0;
                totalTextView.setText(String.format("$ %.2f", total));
                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
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
    public void OnPositiveClick(Product product, float unitPrice, int amount) {
        currentProduct = product;
        currentUnitPrice = unitPrice;
        currentAmount = amount;
        if (product != null) {
            mViewModel.find(product.getId(), amount);
        }
    }

    @Override
    public void OnNegativeClick() {

    }

    private void showInputDialog(Product product) {
        ProductInputDialogFragment dialog = new ProductInputDialogFragment(this, product);
        dialog.show(getActivity().getSupportFragmentManager(), "OrderItem");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sale, menu);
        MenuItem menuItem = menu.findItem(R.id.action_sale_total);
        totalTextView = (TextView) menuItem.getActionView();
        totalTextView.setText(String.format("$ %.2f", total));
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sale_save:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Registro de venta")
                        .setMessage("¿Está seguro que desea registrar la venta?")
                        .setPositiveButton("Registrar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mViewModel.register();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                builder.create().show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
