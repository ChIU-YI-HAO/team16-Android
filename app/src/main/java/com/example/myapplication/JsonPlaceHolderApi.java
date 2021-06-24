package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {
    @POST("posts")
    Call<Post> createPost(@Body Post post);
    @GET("users")
    Call<List<User>> getUsers();
    @POST("users")
    Call<User> createUser(@Body User user);
    @GET("posts/{username}")
    Call<List<Post>>getPosts(@Path("username") String username);
    @GET("dislikeposts/{username}")
    Call<List<Post>>getdislikePosts(@Path("username") String username);
    @GET("posts/delete/{username}/{url}")
    Call<List<Post>>deletePost(
            @Path("username") String username,
            @Path("url")String url);
    @GET("rates/{req}/{percent}/{upordown}")
    Call<List<Rate>>searchrate(
            @Path("req") String req,
            @Path("percent") int percent,
            @Path("upordown") String upordown);

    @GET("rates")
    Call<List<Rate>> getRates();

}

