package com.gas.storeapp.ui.catalog;

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

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gas.storeapp.R;
import com.gas.storeapp.model.Item;
import com.gas.storeapp.model.Product;
import com.gas.storeapp.model.Specification;
import com.gas.storeapp.ui.adapters.ProductAdapter;
import com.gas.storeapp.ui.adapters.SpecificationAdapter;

import java.util.ArrayList;
import java.util.List;

public class CatalogFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private CatalogViewModel mViewModel;
    private Context context;
    private AutoCompleteTextView productSearch, specificationSearch;
    private Spinner colorSpinner, sizeSpinner, categorySpinner;
    private EditText textPrice, textBrand, textDetail;
    private Button btnAccept, btnCancel;
    private List<Product> productList;
    private List<Specification> specificationList;

    public static CatalogFragment newInstance() {
        return new CatalogFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);
        productSearch = view.findViewById(R.id.input_prod_search);
        colorSpinner = view.findViewById(R.id.spinner_color);
        sizeSpinner = view.findViewById(R.id.spinner_size);
        textPrice = view.findViewById(R.id.text_price);
        categorySpinner = view.findViewById(R.id.spinner_categories);
        specificationSearch = view.findViewById(R.id.input_spec_search);
        textBrand = view.findViewById(R.id.text_brand);
        textDetail = view.findViewById(R.id.text_detail);
        btnAccept = view.findViewById(R.id.btn_prod_accept);
        btnCancel = view.findViewById(R.id.btn_prod_cancel);
        categorySpinner.setOnItemSelectedListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CatalogViewModel.class);
        context = getContext();
        LifecycleOwner owner = getViewLifecycleOwner();
        colorSpinner.setOnItemSelectedListener(this);
        sizeSpinner.setOnItemSelectedListener(this);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(productSearch.getText().toString())) {
                    productSearch.setError("Debe ingresar un código de producto.");
                    productSearch.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(textPrice.getText().toString())) {
                    textPrice.setError("Debe ingresar un precio de venta");
                    textPrice.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(specificationSearch.getText().toString())) {
                    specificationSearch.setError("Debe ingresar una descripción del producto.");
                    specificationSearch.requestFocus();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Registro de producto")
                        .setMessage("¿Está seguro que desea registrar el nuevo producto?")
                        .setPositiveButton("Registrar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                try {
                                    float price = Float.parseFloat(textPrice.getText().toString());
                                    mViewModel.register(productSearch.getText().toString(), price, specificationSearch.getText().toString(), textBrand.getText().toString(), textDetail.getText().toString());
                                } catch (Exception ex) {
                                    textPrice.setError("Precio de venta inválido");
                                    textPrice.requestFocus();
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                builder.create().show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClear();
            }
        });
        productList = new ArrayList<>();
        specificationList = new ArrayList<>();
        ProductAdapter productAdapter = new ProductAdapter(context, productList);
        productSearch.setAdapter(productAdapter);
        SpecificationAdapter specificationAdapter = new SpecificationAdapter(context, specificationList);
        specificationSearch.setAdapter(specificationAdapter);
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
        productSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product product = (Product)adapterView.getItemAtPosition(i);
                onProductSelected(product);
            }
        });
        specificationSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == 66) {
                    mViewModel.findSpecification(specificationSearch.getText().toString());
                }
                return false;
            }
        });
        specificationSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Specification specification = (Specification)adapterView.getItemAtPosition(i);
                onSpecificationSelected(specification);
            }
        });
        mViewModel.onProductList().observe(owner, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productList.clear();
                for (Product product : products) {
                    productList.add(product);
                }
                productAdapter.notifyDataSetChanged();
                if (products.size() > 0) {
                    productSearch.showDropDown();
                }
            }
        });
        mViewModel.onSpecificationList().observe(owner, new Observer<List<Specification>>() {
            @Override
            public void onChanged(List<Specification> specifications) {
                specificationList.clear();
                for (Specification specification : specifications) {
                    specificationList.add(specification);
                }
                specificationAdapter.notifyDataSetChanged();
                if (specifications.size() > 0) {
                    specificationSearch.showDropDown();
                }
            }
        });
        mViewModel.getColors().observe(owner, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                loadSpinner(colorSpinner, items);
            }
        });
        mViewModel.getSizes().observe(owner, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                loadSpinner(sizeSpinner, items);
            }
        });

        mViewModel.getCategories().observe(owner, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                loadSpinner(categorySpinner, items);
            }
        });
        mViewModel.onSuccess().observe(owner, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                onClear();
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
        switch (adapterView.getId()) {
            case R.id.spinner_color:
                mViewModel.setColor(index);
                break;
            case R.id.spinner_size:
                mViewModel.setSize(index);
                break;
            case R.id.spinner_categories:
                mViewModel.setCategory(index);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private synchronized void loadSpinner(Spinner spinner, List<Item> items) {
        int size = items.size();
        String[] names = new String[size];
        for(int i = 0; i < size; i++)
            names[i] = items.get(i).getName();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void onProductSelected(Product product) {
        if (product != null) {
            productSearch.setText(product.getCode(), false);
            ArrayAdapter<String> colorAdapter = (ArrayAdapter<String>)colorSpinner.getAdapter();
            int pos = colorAdapter.getPosition(product.getColor().getName());
            colorSpinner.setSelection(pos);
            ArrayAdapter<String> sizeAdapter = (ArrayAdapter<String>)sizeSpinner.getAdapter();
            pos = sizeAdapter.getPosition(product.getSize().getName());
            sizeSpinner.setSelection(pos);
            ArrayAdapter<String> categoryAdapter = (ArrayAdapter<String>)categorySpinner.getAdapter();
            pos = categoryAdapter.getPosition(product.getSpecification().getCategory().getName());
            categorySpinner.setSelection(pos);
            specificationSearch.setText(product.getSpecification().getDescription());
            textBrand.setText(product.getBrand());
            textDetail.setText(product.getSpecification().getDetail());
            textPrice.setText("" + product.getPrice());
            onSpecificationSelected(product.getSpecification());
        }
    }

    private void onSpecificationSelected(Specification specification) {
        if (specification != null) {
            specificationSearch.setText(specification.getDescription(), false);
            textDetail.setText(specification.getDetail());
            ArrayAdapter<String> categoryAdapter = (ArrayAdapter<String>)categorySpinner.getAdapter();
            int pos = categoryAdapter.getPosition(specification.getCategory().getName());
            categorySpinner.setSelection(pos);
            mViewModel.setSpecification(specification);
        }
    }

    private void onClear() {
        productSearch.setText("", false);
        colorSpinner.setSelection(0);
        sizeSpinner.setSelection(0);
        categorySpinner.setSelection(0);
        specificationSearch.setText("", false);
        textBrand.setText("");
        textDetail.setText("");
        textPrice.setText("");
    }
}
