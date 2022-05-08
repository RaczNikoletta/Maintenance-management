package com.example.maintenance_manager_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.auth0.android.jwt.JWT;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private Boolean isLogged;
    private int id;
    private String role;
    private Context context;
    private NavigationView navigationView;
    private  TextView workerName;
    private TextView posName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        View header = navigationView.getHeaderView(0);
        workerName =header.findViewById(R.id.WorkerName);
        posName = header.findViewById(R.id.PosName);

        setSupportActionBar(toolbar);

        isLogged = false;
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            String token = getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                    .getString("user_token", "token not found"); //check login Token from sp
            Log.d("token", token);
            String username = getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                    .getString("username","username not found");
            JWT jwt = new JWT(token);
            role = jwt.getClaim("role").asString();
            id = jwt.getClaim("id").asInt();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            SharedPreferences prefs = getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isLogged", true);// save logged state
            editor.putInt("userId",id);
            editor.apply();
            posName.setText(role);
            workerName.setText(username);
           if(role.equals("admin")) {
               Log.d("role", jwt.getClaim("role").asString());
               //MenuInflater inflater = getMenuInflater();
               //inflater.inflate(R.menu.drawer_admin, menu);
               navigationView.getMenu().clear();
               navigationView.inflateMenu(R.menu.drawer_admin);
               transaction.replace(R.id.fragment_container, new manageEmployees(), "");
               transaction.addToBackStack(null);
               transaction.commit();
           }
           else if(role.equals(getString(R.string.toolManager))) {
               navigationView.getMenu().clear();
               navigationView.inflateMenu(R.menu.drawer_tool_manager);
               transaction.replace(R.id.fragment_container, new addCategoryFragment(), "");
               transaction.addToBackStack(null);
               transaction.commit();

           }
           else if(role.equals(getString(R.string.maintainer))){
               navigationView.inflateMenu(R.menu.drawer_maintenance);
               transaction.replace(R.id.fragment_container, new assignedTasksFragment(), "");
               transaction.addToBackStack(null);
               transaction.commit();

           }
           else if(role.equals("operator")){
               navigationView.inflateMenu(R.menu.drawer_operator);
               transaction.replace(R.id.fragment_container, new listTasksFragment(), "");
               transaction.addToBackStack(null);
               transaction.commit();

           }
                else{
                    Log.d("role", jwt.getClaim("role").asString());
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.drawer_admin);
                    transaction.replace(R.id.fragment_container, new manageEmployees(), "");
                    transaction.addToBackStack(null);
                    transaction.commit();
                }

        } catch (Throwable e) {
            Log.d("exception_jwt", e.toString());
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction(); // set ready to change fragment
        SharedPreferences prefs = getSharedPreferences(context.getString(R.string.app_name),
                Context.MODE_PRIVATE);
        if(role.equals("admin")) {
            switch (item.getItemId()) {
                case R.id.nav_addsubcat:
                    try {
                        transaction.replace(R.id.fragment_container, new manageEmployees(), "");
                        transaction.addToBackStack(null);
                        transaction.commit();
                    } catch (Throwable e) {
                        Log.d("Fragment change error ", e.toString());
                    }
                    break;
                case R.id.nav_manage_professions:
                    transaction.replace(R.id.fragment_container, new addProfessionFragment(), "");
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;
                case  R.id.nav_logout:
                    prefs.edit().putBoolean("isLogged",false).apply();
                    prefs.edit().putString("user_token",null).apply();
                    Intent i = new Intent(context,loginActivity.class);
                    startActivity(i);
                    finish();
                default:
                    transaction.replace(R.id.fragment_container, new manageEmployees(), "");
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;
            }
        }else if(role.equals(getString(R.string.toolManager))){
            switch (item.getItemId()){
                case  R.id.nav_addcat:
                    transaction.replace(R.id.fragment_container, new addCategoryFragment(), "");
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;
                case R.id.nav_addsubcat:
                    transaction.replace(R.id.fragment_container, new addSubcategoryFragment(), "");
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;
                case R.id.nav_addtools:
                    transaction.replace(R.id.fragment_container, new addToolFragment(), "");
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;
                case R.id.nav_logout_toolman:
                    prefs.edit().putBoolean("isLogged",false).apply();
                    prefs.edit().putString("user_token",null).apply();
                    Intent i = new Intent(context,loginActivity.class);
                    startActivity(i);
                    finish();
                    break;
                default:
                    transaction.replace(R.id.fragment_container, new addCategoryFragment(), "");
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;

            }

        }
        else if(role.equals(getString(R.string.maintainer))){
            switch(item.getItemId()){
                case R.id.nav_tasks:
                    transaction.replace(R.id.fragment_container, new assignedTasksFragment(), "");
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;
                case R.id.nav_logout:
                    prefs.edit().putBoolean("isLogged",false).apply();
                    prefs.edit().putString("user_token",null).apply();
                    Intent i = new Intent(context,loginActivity.class);
                    startActivity(i);
                    finish();

            }

        }
        else if(role.equals("operator")) {
            switch (item.getItemId()) {
                case R.id.nav_overview:
                    transaction.replace(R.id.fragment_container, new listTasksFragment(), "");
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;

                case R.id.nav_logout_operator:
                    prefs.edit().putBoolean("isLogged", false).apply();
                    prefs.edit().putString("user_token", null).apply();
                    Intent i = new Intent(context, loginActivity.class);
                    startActivity(i);
                    finish();
                    break;


            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
