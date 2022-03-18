package com.example.maintenance_manager_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawer;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private TextView textViewResult;
    private Boolean isLogged;
    private String username;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        username = "admin1";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        isLogged = false;
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //TODO: Get request
        if(username.equals("admin1")){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.drawer_admin);
            //Log.d("adduse","I am here");
        }return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("adduse","I am here");
        switch (item.getItemId()){
            case R.id.nav_manage:
                try {
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragment_container, new addUsersFragment(), "");
                    Log.d("adduse","I am here");
                    transaction.addToBackStack(null);
                    transaction.commit();
                }catch (Throwable e) {
                    Log.d("Fragment change error ", e.toString());
                }
                break;
            case  R.id.nav_manage_professions:
                break;
            default:
                Log.d("adduse","I am here");
                break;
        }return super.onOptionsItemSelected(item);
    }

    /*private void getPosts(){
        Call<Post> call = jsonPlaceHolderApi.getPosts();


        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(!response.isSuccessful()){
                    textViewResult.setText("Code: " + response.code());
                    return;
                }


                String key = response.body().getKey();
                String key2 = response.body().getKey2();

                String content = "";
                content += key;
                content += " ";
                content += key2;
                textViewResult.append(content);
                }


            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

    }*/

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);

        }else{
            super.onBackPressed();
        }
    }
}