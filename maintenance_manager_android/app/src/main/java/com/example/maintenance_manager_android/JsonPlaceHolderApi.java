package com.example.maintenance_manager_android;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET("example/jsonresponse2/") /*url is jsonplacholder.typicode.com/posts */
    Call<Post> getPosts();

    //@GET("posts/{id}/comments")
    //Call<List<Comment>> getComments(@Path("id") int postId);
}
