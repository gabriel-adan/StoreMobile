package com.gas.storeapp.services.catalog;

import com.gas.storeapp.model.Item;
import com.gas.storeapp.model.Product;
import com.gas.storeapp.model.Specification;
import com.gas.storeapp.services.StandarResponse;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ICatalogService {
    @GET("api/Catalog/Find/{code}")
    Call<List<Product>> find(@Path("code") String code);

    @GET("api/Catalog/Colors")
    Call<List<Item>> getColors();

    @GET("api/Catalog/Sizes")
    Call<List<Item>> getSizes();

    @GET("api/Catalog/Categories")
    Call<List<Item>> getCategories();

    @GET("api/Catalog/Specifications/{description}")
    Call<List<Specification>> getSpecifications(@Path("description") String description);

    @POST("api/Catalog")
    Call<StandarResponse<String>> register(@Body ProductData body);
}
