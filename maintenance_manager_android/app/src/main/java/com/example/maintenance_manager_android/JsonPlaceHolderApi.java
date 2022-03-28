package com.example.maintenance_manager_android;

import android.util.Log;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {


    @POST("auth/login")
    Call<LoginResponse>loginuser(@Body JsonObject jsonObject);

    @POST("auth/create")
    Call<String> createUser(@Query("fnev")String username,
                                    @Query("nev") String name,
                                    @Query("password") String password,
                                    @Query("szerep")String role);

    @GET("qualification")
    Call<ArrayList<QualificationModel>>getQuals();

    @POST("qualification")
    Call<String>addQual(@Body JsonObject jsonObject);
}
