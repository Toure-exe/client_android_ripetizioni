package com.example.onlinelessons;

import com.example.onlinelessons.Model.StudentTutoring;
import com.example.onlinelessons.Model.Tutoring;

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
    Call<String> createPost(
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

    @GET("SessionServlet")
    Call<List<StudentTutoring>> createGetActiveBooking(
            @Query("submit") String submit
    );

    @GET("SessionServlet")
    Call<String> createGetConfirmTutoring(
            @Query("submit") String submit,
            @Query("date") String date,
            @Query("hour") String hour,
            @Query("teacher") String teacher,
            @Query("subject") String subject
    );

    @FormUrlEncoded
    @POST("SessionServlet")
    Call<String> createPostCancelTutoring(
            @Field("submit") String submit,
            @Field("date") String date,
            @Field("hour") String hour,
            @Field("teacher") String teacher,
            @Field("subject") String subject,
            @Field("emailStudent") String emailStudent
    );

    @GET("MainServlet")
    Call<List<String>> createGetSubjectAvailable(
            @Query("submit") String submit
    );

    @GET("MainServlet")
    Call<List<String>> createGetTeachers(
            @Query("submit") String submit,
            @Query("subject") String subject,
            @Query("email") String email
    );

    @GET("MainServlet")
    Call<List<Tutoring>> createGetTeacherAvailability(
            @Query("submit") String submit,
            @Query("subject") String subject,
            @Query("teacher") String teacher
    );

    @GET("SessionServlet")
    Call<String> createGetInsertBooking(
            @Query("submit") String submit,
            @Query("teacher") String teacher,
            @Query("subject") String subject,
            @Query("day") String day,
            @Query("hour") String hour
    );

}
