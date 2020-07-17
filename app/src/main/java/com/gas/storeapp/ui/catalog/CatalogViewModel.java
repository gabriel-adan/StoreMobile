package com.gas.storeapp.ui.catalog;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gas.storeapp.model.Item;
import com.gas.storeapp.model.Product;
import com.gas.storeapp.model.Specification;
import com.gas.storeapp.services.HttpService;
import com.gas.storeapp.services.StandarResponse;
import com.gas.storeapp.services.catalog.ICatalogService;
import com.gas.storeapp.services.catalog.ProductData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatalogViewModel extends ViewModel {
    private ICatalogService catalogService;
    private MutableLiveData<List<Item>> colorList;
    private MutableLiveData<List<Item>> sizeList;
    private MutableLiveData<List<Item>> categoryList;
    private MutableLiveData<List<Product>> productList;
    private MutableLiveData<List<Specification>> specificationList;
    private MutableLiveData<String> messageOk;
    private MutableLiveData<String> messageError;
    private List<Item> colors, sizes, categories;
    private Specification specificationSelected;
    private Item colorSelected;
    private Item sizeSelected;
    private Item categorySelected;

    public CatalogViewModel() {
        colorList = new MutableLiveData<>();
        sizeList = new MutableLiveData<>();
        productList = new MutableLiveData<>();
        specificationList = new MutableLiveData<>();
        categoryList = new MutableLiveData<>();
        messageOk = new MutableLiveData<>();
        messageError = new MutableLiveData<>();
        catalogService = HttpService.createService(ICatalogService.class);
        colors = new ArrayList<>();
        sizes = new ArrayList<>();
        categories = new ArrayList<>();
        catalogService.getColors().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful()) {
                    colors = response.body();
                    colorList.setValue(colors);
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

            }
        });
        catalogService.getSizes().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful()) {
                    sizes = response.body();
                    sizeList.setValue(sizes);
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

            }
        });
        catalogService.getCategories().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful()) {
                    categories = response.body();
                    categoryList.setValue(categories);
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

            }
        });
    }

    public LiveData<List<Item>> getColors() {
        return colorList;
    }

    public LiveData<List<Item>> getSizes() {
        return sizeList;
    }

    public LiveData<List<Item>> getCategories() {
        return categoryList;
    }

    public LiveData<String> onSuccess() {
        return messageOk;
    }

    public LiveData<String> onError() {
        return messageError;
    }

    public LiveData<List<Product>> onProductList() {
        return productList;
    }

    public LiveData<List<Specification>> onSpecificationList() {
        return specificationList;
    }

    public void setColor(int index) {
        colorSelected = colors.get(index);
    }

    public void setSize(int index) {
        sizeSelected = sizes.get(index);
    }

    public void setCategory(int index) {
        categorySelected = categories.get(index);
    }

    public void setSpecification(Specification specification) {
        specificationSelected = specification;
        categorySelected = specification.getCategory();
    }

    public void findProduct(String code) {
        if (!TextUtils.isEmpty(code)) {
            catalogService.find(code).enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    if (response.isSuccessful()) {
                        List<Product> products = response.body();
                        if (products.size() > 0) {
                            productList.setValue(products);
                        }
                    } else {
                        StandarResponse<String> result = HttpService.ResponseError(response.errorBody(), StandarResponse.class);
                        if (result != null && result.errorMessage != null) {
                            messageError.setValue(result.errorMessage);
                        } else {
                            messageError.setValue("Error de comunicación.");
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {
                    messageError.setValue("ERROR: " + t.getMessage());
                }
            });
        }
    }

    public void findSpecification(String description) {
        if (!TextUtils.isEmpty(description)) {
            catalogService.getSpecifications(description).enqueue(new Callback<List<Specification>>() {
                @Override
                public void onResponse(Call<List<Specification>> call, Response<List<Specification>> response) {
                    if (response.isSuccessful()) {
                        List<Specification> specifications = response.body();
                        if (specifications.size() > 0) {
                            specificationList.setValue(specifications);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Specification>> call, Throwable t) {

                }
            });
        }
    }

    public void register(String code, float price, String desc, String mark, String type) {
        int specificationId = 0;
        if (specificationSelected != null)
            specificationId = specificationSelected.getId();
        ProductData productData = new ProductData(code, price, desc, mark, type, specificationId,
                colorSelected.getId(), sizeSelected.getId(), categorySelected.getId());
        catalogService.register(productData).enqueue(new Callback<StandarResponse<String>>() {
            @Override
            public void onResponse(Call<StandarResponse<String>> call, Response<StandarResponse<String>> response) {
                if (response.isSuccessful()) {
                    messageOk.setValue(response.body().result);
                    specificationSelected = null;
                    colorSelected = null;
                    sizeSelected = null;
                    categorySelected = null;
                } else {
                    StandarResponse<String> result = HttpService.ResponseError(response.errorBody(), StandarResponse.class);
                    messageError.setValue(result.errorMessage);
                }
            }

            @Override
            public void onFailure(Call<StandarResponse<String>> call, Throwable t) {
                messageError.setValue("ERROR: " + t.getMessage());
            }
        });
    }
}
