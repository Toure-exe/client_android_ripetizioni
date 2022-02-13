package com.example.onlinelessons;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @FormUrlEncoded
    @POST("MainServlet")
    Call<Post> createPost(
            @Field("submit") String submit,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("SessionServlet")
    Call<String> createPostLogin(
            @Field("submit") String submit,
            @Field("email") String email,
            @Field("password") String password
    );
}
