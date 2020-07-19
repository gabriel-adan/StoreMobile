package com.gas.storeapp.ui.sale;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gas.storeapp.R;
import com.gas.storeapp.model.Sale;
import com.gas.storeapp.ui.adapters.SaleAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainSaleFragment extends Fragment {

    private MainSaleViewModel mViewModel;
    private Context context;
    private SaleAdapter saleAdapter;

    public static MainSaleFragment newInstance() {
        return new MainSaleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale_main, container, false);
        context = getContext();
        RecyclerView salesView = view.findViewById(R.id.recycler_sale_list);
        salesView.setLayoutManager(new LinearLayoutManager(context));
        FloatingActionButton newSaleFab = view.findViewById(R.id.fab_new_sale);
        newSaleFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_main_sale_to_saleFragment);
            }
        });
        saleAdapter = new SaleAdapter();
        salesView.setAdapter(saleAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainSaleViewModel.class);
        LifecycleOwner owner = getViewLifecycleOwner();
        mViewModel.onSaleList().observe(owner, new Observer<List<Sale>>() {
            @Override
            public void onChanged(List<Sale> sales) {
                saleAdapter.clear();
                for (Sale sale : sales) {
                    saleAdapter.addSale(sale);
                }
                saleAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sale_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sale_now:
                System.out.println("NOW");
                break;
            case R.id.action_sale_date:
                System.out.println("DATE");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
