package com.example.onlinelessons;

import com.example.onlinelessons.Model.StudentTutoring;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    @FormUrlEncoded
    @POST("SessionServlet")
    Call<String> createPostLogout(
            @Field("submit") String submit
    );


    @GET("SessionServlet")
    Call<List<StudentTutoring>> createPostGetHistory(
            @Query("submit") String submit
    );
}
