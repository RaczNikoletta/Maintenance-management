package com.example.maintenance_manager_android;

import com.example.maintenance_manager_android.model.CategoryModel;
import com.example.maintenance_manager_android.model.EmployeeModel;
import com.example.maintenance_manager_android.model.EquipmentModel;
import com.example.maintenance_manager_android.model.QualificationModel;
import com.example.maintenance_manager_android.model.SubCategoryModel;
import com.example.maintenance_manager_android.model.TaskModel;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {




    @POST("auth/login")
    Call<LoginResponse>loginuser(@Body JsonObject jsonObject);

    @POST("auth/create")
    Call<String> createUser(@Query("fnev")String username,
                                    @Query("nev") String name,
                                    @Query("password") String password,
                                    @Query("szerep")String role,
                                    @Query("kepesitesid") int kepid );

    @GET("qualification")
    Call<ArrayList<QualificationModel>>getQuals();

    @POST("qualification")
    Call<String>addQual(@Body JsonObject jsonObject);


    @POST("category")
    Call<String>addCategory(@Body JsonObject jsonObject);

    @GET("category")
    Call<ArrayList<CategoryModel>>getCategories();

    @POST("subcategory")
    Call<String>addSubCategory(@Body JsonObject jsonObject);

    @GET("subcategory")
    Call<ArrayList<SubCategoryModel>>getSubCategories();

    @POST("equipment")
    Call<String>addEquipment(@Body JsonObject jsonObject);

    @POST("task")
    Call<String>addTask(@Body JsonObject jsonObject);

    @GET("equipment")
    Call<ArrayList<EquipmentModel>>getEquipments();

    @GET("task/automatic")
    Call<String>getAutoTasks();

    @GET("task/all")
    Call<ArrayList<TaskModel>>getTasks();

    @POST("task/assign")
    Call<String>assignTask(@Query("userId")int userId,
                            @Query("taskId")int taskId);

    @GET("task/users")
    Call<ArrayList<EmployeeModel>>getUsers(@Query("role") String role);

    @GET("task")
    Call<ArrayList<TaskModel>>getTaskInProgress(@Query("userId")int userId,
                                                @Query("status")String status);

    @POST("task/change")
    Call<String>changeTaskStatus(@Query("taskId")int taskId,
                                 @Query("status")String status,
                                 @Query("reason")String reason);


}
