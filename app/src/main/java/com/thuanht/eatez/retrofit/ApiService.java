package com.thuanht.eatez.retrofit;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thuanht.eatez.jsonResponse.CategoryResponse;
import com.thuanht.eatez.jsonResponse.CommentResponse;
import com.thuanht.eatez.jsonResponse.FavouriteResponse;
import com.thuanht.eatez.jsonResponse.ProfileResponse;
import com.thuanht.eatez.jsonResponse.LoginResponse;
import com.thuanht.eatez.jsonResponse.PostResponse;
import com.thuanht.eatez.jsonResponse.StatusResponse;
import com.thuanht.eatez.jsonResponse.SuggestResponse;
import com.thuanht.eatez.jsonResponse.TrendingResponse;
import com.thuanht.eatez.jsonResponse.SignupResponse;
import com.thuanht.eatez.jsonResponse.SliderResponse;
import com.thuanht.eatez.jsonResponse.UserResponse;

import java.util.concurrent.TimeUnit;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    HttpLoggingInterceptor HTTP_LOGGING_INTERCEPTOR = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(HTTP_LOGGING_INTERCEPTOR).build();

    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();

    ApiService ApiService = new Retrofit.Builder()
            .baseUrl("https://htthuan.id.vn/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(ApiService.class);


    @GET("getPosts.php")
    Observable<PostResponse> getListPost(@Query("page") int pageNumber);

    @GET("getCategories.php")
    Observable<CategoryResponse> getCategories();

    @GET("getSliders.php")
    Observable<SliderResponse> getSliders();

    @GET("getPostsOfCategory.php")
    Observable<PostResponse> getListPostOfCategory(@Query("categoryid") int cid, @Query("page") int pageNumber);

    @GET("getFavouritePost.php")
    Observable<FavouriteResponse> getFavouritePost(@Query("userid") int userid, @Query("page") int pageNumber);

    @GET("getPost.php")
    Observable<PostResponse> getDetailPost(@Query("postid") int postid);

    @GET("login.php")
    Observable<LoginResponse> Login(@Query("email") String email, @Query("password") String password);

    @GET("register.php")
    Observable<SignupResponse> Register(@Query("fullname") String fullname, @Query("email") String email, @Query("password") String password, @Query("avatar_image") String avatar_image);

    @GET("getUser.php")
    Observable<UserResponse> getUser(@Query("userid") int userid);

    @GET("getUserByEmail.php")
    Observable<UserResponse> getUserByEmail(@Query("email") String email);

    @GET("getDishTrending.php")
    Observable<TrendingResponse> getTrendingHome();

    @GET("searchPostByDishID.php")
    Observable<PostResponse> getPostTrending(@Query("dishid") int dishID, @Query("page") int pageNumber);

    @GET("setFavouritePost.php")
    Observable<StatusResponse> savePost(@Query("userid") int userid, @Query("post_id") int postid);

    @GET("unsetFavouritePost.php")
    Observable<StatusResponse> unSavePost(@Query("userid") int userid, @Query("post_id") int postid);

    @GET("checkSaveBefore.php")
    Observable<StatusResponse> checkSave(@Query("userid") int userid, @Query("post_id") int postid);

    @GET("setComment.php")
    Observable<StatusResponse> setComment(@Query("userid") int userId, @Query("postid") int postId, @Query("content") String content, @Query("rating") int rating);

    @GET("getComments.php")
    Observable<CommentResponse> getComments(@Query("postid") int postid, @Query("page") int pageNumber);
    @Multipart
    @POST("setUser.php")
    Observable<UserResponse> updateProfileUser(
            @Part("userid") int userid,
            @Part("fullName") RequestBody fullName,
            @Part MultipartBody.Part avatarImage
    );

    @GET("searchPosts.php")
    Observable<PostResponse> searchPost(@Query("key") String key);

    @GET("getSuggestion.php")
    Observable<SuggestResponse> getSuggestionValue();
    @GET("setToken.php")
    Observable<SignupResponse> setToken(@Query("userid") int userid, @Query("token") String token);
    @GET("setToken.php")
    Call<SignupResponse> updateToken(@Query("userid") int userid, @Query("token") String token);
}
