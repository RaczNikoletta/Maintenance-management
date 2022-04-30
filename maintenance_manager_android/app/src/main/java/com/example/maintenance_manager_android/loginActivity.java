package com.example.maintenance_manager_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class loginActivity extends AppCompatActivity {
    private TextView textViewResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private EditText usernameEt;
    private EditText passwordEt;
    private Button sendBtn;
    private Context context;
    private SessionManager sessionManager;
    private LoginResponse loginResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager();
        context = this;
        textViewResult = findViewById(R.id.textViewResult);
        usernameEt = findViewById(R.id.usernameEt);
        passwordEt = findViewById(R.id.passwordEt);
        sendBtn = findViewById(R.id.sendBtn);

        boolean isLogged = getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                .getBoolean("isLogged", false); //check login Token from sp
        if (isLogged){
            Intent i = new Intent(context,MainActivity.class); //login automatically
            startActivity(i);
            finish();
        }

        try {


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();



            jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        }catch (Throwable e){
            textViewResult.setText(e.toString());
        }

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(usernameEt.getText().toString(),passwordEt.getText().toString());
                //Intent i = new Intent(context,adminMenu.class);
                //startActivity(i);
            }
        });

    }

    private void login(String username,String password){
        sessionManager.setShared(this);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username",username );
        jsonObject.addProperty("password", password);

        Call<LoginResponse> call = jsonPlaceHolderApi.loginuser(jsonObject);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                loginResponse = response.body();
                String jsonString = response.message();
                if(jsonString.equals("OK")){
                    sessionManager.saveAuthToken(username,loginResponse.aut_token);
                    Intent i = new Intent(context,MainActivity.class);
                    startActivity(i);
                }
                else {

                    textViewResult.setText(jsonString);
                }

            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                textViewResult.setText(t.toString());
                //Intent i = new Intent(context,adminMenu.class);
                //startActivity(i);

            }
        });
    }
    }
