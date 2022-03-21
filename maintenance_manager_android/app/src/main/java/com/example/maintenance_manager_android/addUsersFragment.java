package com.example.maintenance_manager_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        setRetainInstance(true); // to enable changing orientation of the mobile

        sendWorkerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!TextUtils.isEmpty(positionEt.getText())
                        && !TextUtils.isEmpty(addNewWorkerusername.getText())
                && !TextUtils.isEmpty(firstNameEt.getText())
                        && !TextUtils.isEmpty(lastnameEt.getText())
                        && !TextUtils.isEmpty(newWorkerPassword.getText())
                        &&(!TextUtils.isEmpty(positionEt.getText())))
                {
                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://10.0.2.2:8080/api/")
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

                    jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
                    String namestring = firstNameEt.getText().toString() + " " + lastnameEt.getText().toString().toUpperCase();

                    Call<createResponse> call = jsonPlaceHolderApi.createUser(addNewWorkerusername.getText().toString()
                            ,namestring,newWorkerPassword.getText().toString(),positionEt.getText().toString());

                    call.enqueue(new Callback<createResponse>() {
                        @Override
                        public void onResponse(Call<createResponse> call, Response<createResponse> response) {

                            Log.d("addeduser","ok");
                            new AlertDialog.Builder(getContext())
                                    .setTitle(R.string.databaseUpdated)
                                    .setMessage(R.string.databaseUpdated2)
                                    .setIcon(getResources().getDrawable(R.drawable.ic_baseline_check_24))
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    })
                                    .show();

                        }

                        @Override
                        public void onFailure(Call<createResponse> call, Throwable t) {
                            Log.d("addeduser",t.toString());

                            new AlertDialog.Builder(getContext())
                                    .setTitle(R.string.databaseError)
                                    .setMessage(t.getMessage())
                                    .setIcon(getResources().getDrawable(R.drawable.ic_baseline_error_24))
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    })
                                    .show();

                        }
                    });

                }

            }
        });




        return view;
    }
}