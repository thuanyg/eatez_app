package com.thuanht.eatez.retrofit;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thuanht.eatez.jsonResponse.CategoryResponse;
import com.thuanht.eatez.jsonResponse.PostResponse;
import com.thuanht.eatez.jsonResponse.SliderResponse;
import com.thuanht.eatez.model.SliderHome;

import java.util.concurrent.TimeUnit;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    HttpLoggingInterceptor HTTP_LOGGING_INTERCEPTOR = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient.Builder BUILDER = new OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(HTTP_LOGGING_INTERCEPTOR);

    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();

    ApiService ApiService = new Retrofit.Builder()
            .baseUrl("https://htthuan.id.vn/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(ApiService.class);
    @GET("getPosts.php")
    Observable<PostResponse> getListPost(@Query("page") int id);
    @GET("getCategories.php")
    Observable<CategoryResponse> getCategories();
    @GET("getSliders.php")
    Observable<SliderResponse> getSliders();
}
