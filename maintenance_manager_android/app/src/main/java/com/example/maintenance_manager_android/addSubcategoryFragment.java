package com.example.maintenance_manager_android;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class addSubcategoryFragment extends Fragment {

    EditText subcategory;
    EditText toolCategory;
    EditText professionEt;
    EditText repairInstruction;
    EditText repairTime;
    Button addSubcategory;
    JsonPlaceHolderApi jsonPlaceHolderApi;


    public addSubcategoryFragment() {
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
        View view = inflater.inflate(R.layout.fragment_add_subcategory, container, false);

        subcategory = view.findViewById(R.id.subCategory);
        toolCategory = view.findViewById(R.id.toolCategory);
        professionEt  = view.findViewById(R.id.profession);
        repairInstruction = view.findViewById(R.id.repairInstruction);
        repairTime = view.findViewById(R.id.repairTime);
        addSubcategory = view.findViewById(R.id.addSubcategory);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/")
                .addConverterFactory(ScalarsConverterFactory.create()) //without this line "JSON is not fully consumed"
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        addSubcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //subcategoryID == categoryID
                if(!TextUtils.isEmpty(subcategory.getText())&&!TextUtils.isEmpty(toolCategory.getText())&&!TextUtils.isEmpty(professionEt.getText())
                                &&!TextUtils.isEmpty(repairInstruction.getText())&&!TextUtils.isEmpty(repairTime.getText()))
                {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("categoryId",Integer.parseInt(toolCategory.getText().toString()));
                    jsonObject.addProperty("subCategoryName",subcategory.getText().toString());
                    jsonObject.addProperty("qualificationId",Integer.parseInt(professionEt.getText().toString()));
                    jsonObject.addProperty("standardTime",Integer.parseInt(repairTime.getText().toString()));
                    jsonObject.addProperty("order",repairInstruction.getText().toString());
                    Call<String> subCat = jsonPlaceHolderApi.addSubCategory(jsonObject);

                    subCat.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (!response.isSuccessful()) {
                                Log.d("subcat: ", "subcat add failed: " +response.code());
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
                                                transaction.replace(R.id.fragment_container, new addSubcategoryFragment(), "");
                                                transaction.addToBackStack(null);
                                                transaction.commit();

                                            }
                                        })
                                        .show();

                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("addsubcatfailure", "Failure: "+ t.toString());

                        }
                    });

                }

            }
        });





        return view;
    }
}