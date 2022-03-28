package com.example.maintenance_manager_android;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class addProfessionFragment extends Fragment {

    ListView professionListView;
    ImageButton addProfButton;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    ArrayList<QualificationModel> profs;
    int lastProfId = 0;
    EditText professionEt;



    public addProfessionFragment() {
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
        View view = inflater.inflate(R.layout.fragment_add_profession, container, false);

       professionListView =  view.findViewById(R.id.professionList);
       addProfButton = view.findViewById(R.id.addProfButton);
       professionEt = view.findViewById(R.id.professionEt);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/")
                .addConverterFactory(ScalarsConverterFactory.create()) //without this line "JSON is not fully consumed"
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        profs = new ArrayList<>();
        Call<ArrayList<QualificationModel>> call = jsonPlaceHolderApi.getQuals();

        ArrayList<String> professionList = new ArrayList<>();

        call.enqueue(new Callback<ArrayList<QualificationModel>>() {
            @Override
            public void onResponse(Call<ArrayList<QualificationModel>> call, Response<ArrayList<QualificationModel>> response) {
                if (!response.isSuccessful()) {
                    professionList.add("Get request failed: " + response.code());
                    return;
                } else {
                    profs = response.body();
                    Log.d("prof", profs.get(0).getqName());
                    for (int i = 0; i < profs.size(); i++) {
                        professionList.add(profs.get(i).getqName());
                        if(i==profs.size()-1){
                            lastProfId = profs.get(i).getqId(); //for id numbering
                        }
                    }
                }
                    ListAdapter listAdapter = new professionListAdapter(getActivity(),R.layout.profession_list,professionList);
                    professionListView.setAdapter(listAdapter);

            }
            @Override
            public void onFailure(Call<ArrayList<QualificationModel>> call, Throwable t) {
                    Log.d("professionFailure", t.toString());
            }
        });



        addProfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!TextUtils.isEmpty(professionEt.getText())){
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("qualificationID",lastProfId+1);
                    jsonObject.addProperty("qualificationName",professionEt.getText().toString());
                    Call<String> call2 = jsonPlaceHolderApi.addQual(jsonObject);

                    call2.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (!response.isSuccessful()) {
                                Log.d("Add_prof", "Get request failed: " + response.code());
                                return;
                            }
                                else{
                                new AlertDialog.Builder(getContext())
                                        .setTitle(R.string.databaseUpdated)
                                        .setMessage(R.string.databaseUpdated2)
                                        .setIcon(getResources().getDrawable(R.drawable.ic_baseline_check_24))
                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                FragmentManager manager = getFragmentManager();
                                                FragmentTransaction transaction = manager.beginTransaction();
                                                transaction.replace(R.id.fragment_container, new addProfessionFragment(), "");
                                                transaction.addToBackStack(null);
                                                transaction.commit();

                                            }
                                        })
                                        .show();

                            }


                            }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("Add_prof", t.toString());

                        }
                    });
                }




            }
        });


        return view;
    }
}