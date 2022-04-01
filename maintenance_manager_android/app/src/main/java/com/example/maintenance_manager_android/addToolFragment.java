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
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class addToolFragment extends Fragment {

    EditText tool;
    EditText toolSubcategory;
    EditText description;
    EditText placement;
    Button addTool;
    JsonPlaceHolderApi jsonPlaceHolderApi;

    public addToolFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_tool,container, false);

        tool = view.findViewById(R.id.tool);
        toolSubcategory = view.findViewById(R.id.toolSubcategory);
        description = view.findViewById(R.id.description);
        placement = view.findViewById(R.id.placement);
        addTool = view.findViewById(R.id.addTool);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/")
                .addConverterFactory(ScalarsConverterFactory.create()) //without this line "JSON is not fully consumed"
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        addTool.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!TextUtils.isEmpty(toolSubcategory.getText())&&!TextUtils.isEmpty(tool.getText())&&!TextUtils.isEmpty(placement.getText())
                        &&!TextUtils.isEmpty(description.getText()))
                {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("subCategoryId",Integer.parseInt(toolSubcategory.getText().toString()));
                    jsonObject.addProperty("equipmentName",tool.getText().toString());
                    jsonObject.addProperty("site",placement.getText().toString());
                    jsonObject.addProperty("description",description.getText().toString());
                    Call<String> toolCall = jsonPlaceHolderApi.addEquipment(jsonObject);

                    toolCall.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (!response.isSuccessful()) {
                                Log.d("toolCall: ", "toolCall add failed: " +response.code());
                            }else{
                                new AlertDialog.Builder(getContext())
                                        .setTitle(R.string.databaseUpdated)
                                        .setMessage(R.string.databaseUpdated2)
                                        .setIcon(getResources().getDrawable(R.drawable.ic_baseline_check_24))
                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                FragmentManager manager = getFragmentManager();
                                                FragmentTransaction transaction = manager.beginTransaction();
                                                transaction.replace(R.id.fragment_container, new addToolFragment(), "");
                                                transaction.addToBackStack(null);
                                                transaction.commit();

                                            }
                                        })
                                        .show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("addtoolFailure", "Failure: "+ t.toString());
                        }
                    });
                }
            }
        });

        return view;
    }
}