package com.example.maintenance_manager_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class addUsersFragment extends Fragment {
    private EditText positionEt;
    private Button sendWorkerBtn;
    private EditText newWorkerPassword;
    private EditText lastnameEt;
    private EditText firstNameEt;
    private EditText addNewWorkerusername;
    private JsonPlaceHolderApi jsonPlaceHolderApi;


    public addUsersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_worker, container,false);
        positionEt = view.findViewById(R.id.positionEt);
        addNewWorkerusername = view.findViewById(R.id.addNewWorkerusernameEt);
        firstNameEt = view.findViewById(R.id.firstNameEt);
        lastnameEt = view.findViewById(R.id.lastnameEt);
        newWorkerPassword = view.findViewById(R.id.newWorkerPasswordEt);
        positionEt = view.findViewById(R.id.positionEt);
        sendWorkerBtn = view.findViewById(R.id.sendWorkerBtn);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        setRetainInstance(true); // to enable changing orientation of the mobile

        sendWorkerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                if(!TextUtils.isEmpty(positionEt.getText())
                        && !TextUtils.isEmpty(addNewWorkerusername.getText())
                && !TextUtils.isEmpty(firstNameEt.getText())
                        && !TextUtils.isEmpty(lastnameEt.getText())
                        && !TextUtils.isEmpty(newWorkerPassword.getText())
                        &&(!TextUtils.isEmpty(positionEt.getText()))) {
                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://10.0.2.2:8080/api/")
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

                    jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
                    String namestring = firstNameEt.getText().toString() + " " + lastnameEt.getText().toString().toUpperCase();
                    String username = addNewWorkerusername.getText().toString();
                    String pass = newWorkerPassword.getText().toString();
                    String pos = positionEt.getText().toString();
                    Call<String> call = jsonPlaceHolderApi.createUser(username,namestring,pass,pos);

                    try {
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {

                                Log.d("response",response.message());
                                if (response.code() == 200) {
                                    Log.v("create","Success"+ response.body());

                                    Log.d("addeduser", "ok");
                                    new AlertDialog.Builder(getContext())
                                            .setTitle(R.string.databaseUpdated)
                                            .setMessage(R.string.databaseUpdated2)
                                            .setIcon(getResources().getDrawable(R.drawable.ic_baseline_check_24))
                                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    FragmentManager manager = getFragmentManager();
                                                    FragmentTransaction transaction = manager.beginTransaction();
                                                    transaction.replace(R.id.fragment_container, new manageEmployees(), "");
                                                    transaction.addToBackStack(null);
                                                    transaction.commit();

                                                }
                                            })
                                            .show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.d("addeduser", t.toString());


                            }
                        });

                    }catch (Throwable e){
                    Log.d("adduser_exception",e.toString());
                    }
                }


            }catch (Throwable e){
                    Log.d("adduser_exception",e.toString());
                }
            }
        });




        return view;
    }
}