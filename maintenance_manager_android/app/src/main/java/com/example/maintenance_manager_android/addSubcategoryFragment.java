package com.example.maintenance_manager_android;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.maintenance_manager_android.model.CategoryModel;
import com.example.maintenance_manager_android.model.QualificationModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
    ArrayList<String> categoryNames;
    ListView catList;
    String clickedcat;
    HashMap<String,Integer> qualHash;
    ArrayList<String> qualNames;
    HashMap<String,Integer> catHash;


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
        categoryNames = new ArrayList<>();
        qualNames = new ArrayList<>();
        qualHash = new HashMap<>();
        catHash = new HashMap<>();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/")
                .addConverterFactory(ScalarsConverterFactory.create()) //without this line "JSON is not fully consumed"
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call <ArrayList<CategoryModel>> categories = jsonPlaceHolderApi.getCategories();
        categories.enqueue(new Callback<ArrayList<CategoryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryModel>> call, Response<ArrayList<CategoryModel>> response) {
                if (!response.isSuccessful()) {
                    Log.d("categories: ", "category query failed: " +response.code());


                }else{
                    ArrayList<CategoryModel> temp = response.body();
                    for(int i=0;i<temp.size();i++){
                        catHash.put(temp.get(i).getCategoryName(),temp.get(i).getCategoryID());
                        categoryNames.add(temp.get(i).getCategoryName());
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CategoryModel>> call, Throwable t) {
                Log.d("categories: ", t.toString());

            }
        });

        Call<ArrayList<QualificationModel>> quals = jsonPlaceHolderApi.getQuals();

        quals.enqueue(new Callback<ArrayList<QualificationModel>>() {
            @Override
            public void onResponse(Call<ArrayList<QualificationModel>> call, Response<ArrayList<QualificationModel>> response) {
                if (!response.isSuccessful()) {
                    Log.d("qualification: ", "qualification query failed: " +response.code());



                }else{
                    ArrayList<QualificationModel> temp = response.body();
                    for(int i=0;i<temp.size();i++){
                        qualNames.add(temp.get(i).getqName());
                        qualHash.put(temp.get(i).getqName(),temp.get(i).getqId());
                    }
                    for(Map.Entry<String,Integer> entry: qualHash.entrySet()){
                        Log.d("qualkey",entry.getKey()+ " " +entry.getValue());
                    }
                }


            }

            @Override
            public void onFailure(Call<ArrayList<QualificationModel>> call, Throwable t) {

            }
        });



        professionEt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.prof_dialog);
                //dialog.setCancelable(true);
                catList = (ListView) dialog.findViewById(R.id.List);
                catList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        clickedcat = catList.getItemAtPosition(position).toString();
                        professionEt.setText(clickedcat);
                        dialog.dismiss();

                    }
                });
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, qualNames);
                catList.setAdapter(adapter);
                dialog.show();
                return false;
            }
        });



        toolCategory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.prof_dialog);
                //dialog.setCancelable(true);
                catList = (ListView) dialog.findViewById(R.id.List);
                catList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        clickedcat = catList.getItemAtPosition(position).toString();
                        toolCategory.setText(clickedcat);
                        dialog.dismiss();

                    }
                });
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, categoryNames);
                catList.setAdapter(adapter);
                dialog.show();


                return false;
            }
        });

        addSubcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //subcategoryID == categoryID
                if(!TextUtils.isEmpty(subcategory.getText())&&!TextUtils.isEmpty(toolCategory.getText())&&!TextUtils.isEmpty(professionEt.getText())
                                &&!TextUtils.isEmpty(repairInstruction.getText())&&!TextUtils.isEmpty(repairTime.getText()))
                {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("subCategoryId",0);
                    jsonObject.addProperty("categoryId",catHash.get((toolCategory.getText().toString())));

                    jsonObject.addProperty("subCategoryName",subcategory.getText().toString());
                    jsonObject.addProperty("qualificationId",qualHash.get((professionEt.getText().toString())));

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