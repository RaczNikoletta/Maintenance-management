package com.example.maintenance_manager_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;
        textViewResult = findViewById(R.id.textViewResult);
        usernameEt = findViewById(R.id.usernameEt);
        passwordEt = findViewById(R.id.passwordEt);
        sendBtn = findViewById(R.id.sendBtn);

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
                Intent i = new Intent(context,adminMenu.class);
                startActivity(i);
            }
        });

    }

    private void login(String username,String password){
        LoginModel login = new LoginModel(username,password);

        Call<LoginModel> call = jsonPlaceHolderApi.loginuser(login);

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                LoginModel loginresponse = response.body();

                String content = "";
                content += "Username: "+ loginresponse.getUsername() + "\n";
                content += "Password: "+ loginresponse.getPassword() + "\n";

                textViewResult.setText(content);

                Intent i = new Intent(context,adminMenu.class);
                startActivity(i);
            }
            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                textViewResult.setText(t.toString());
                Intent i = new Intent(context,adminMenu.class);
                startActivity(i);

            }
        });
    }
    }
