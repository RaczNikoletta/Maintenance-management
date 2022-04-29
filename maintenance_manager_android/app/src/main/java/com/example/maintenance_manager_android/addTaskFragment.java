package com.example.maintenance_manager_android;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.maintenance_manager_android.model.CategoryModel;
import com.example.maintenance_manager_android.model.EquipmentModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class addTaskFragment extends Fragment {

    EditText tool;
    EditText cause;
    DatePicker datePicker;
    Button addTask;
    Spinner severitySpinner;
    private ArrayAdapter<String> customAdapter;
    private ArrayList<String> severities;
    private HashMap<String,Integer> equipHash;
    private ArrayList<String> equipmentNames;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private ListView eqList;
    private String clickedEq;
    private Context context;



    public addTaskFragment() {
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
        View view = inflater.inflate(R.layout.fragment_add_task,container, false);

        tool = view.findViewById(R.id.toolName);
        cause = view.findViewById(R.id.cause);
        datePicker = view.findViewById(R.id.datePicker); //(datePicker.getDayOfMonth()+"/"+datePicker.getMonth()+"/"+picker.getYear());
        addTask = view.findViewById(R.id.addTask);
        severitySpinner = view.findViewById(R.id.severitySpinner);
        severities = new ArrayList<String>();
        severities.add("alacsony");severities.add("közepes");severities.add("magas");severities.add("kritikus");
        customAdapter = new ArrayAdapter<String>(getContext(),R.layout.prof_spinner_item,severities);
        severitySpinner.setAdapter(customAdapter);
        equipHash = new HashMap<>();
        equipmentNames = new ArrayList<>();
        context = getContext();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/")
                .addConverterFactory(ScalarsConverterFactory.create()) //without this line "JSON is not fully consumed"
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<ArrayList<EquipmentModel>> categories = jsonPlaceHolderApi.getEquipments();
        categories.enqueue(new Callback<ArrayList<EquipmentModel>>() {
            @Override
            public void onResponse(Call<ArrayList<EquipmentModel>> call, Response<ArrayList<EquipmentModel>> response) {
                if (!response.isSuccessful()) {
                    Log.d("categories: ", "category query failed: " +response.code());


                }else{
                    ArrayList<EquipmentModel> temp = response.body();
                    for(int i=0;i<temp.size();i++){
                        equipHash.put(temp.get(i).getEquipmentName(),temp.get(i).getEquipmentId());
                        equipmentNames.add(temp.get(i).getEquipmentName());
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<EquipmentModel>> call, Throwable t) {
                Log.d("equipments: ", t.toString());

            }
        });

        tool.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.prof_dialog);
                //dialog.setCancelable(true);
                 eqList = (ListView) dialog.findViewById(R.id.List);
                eqList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        clickedEq = eqList.getItemAtPosition(position).toString();
                        tool.setText(clickedEq);
                        dialog.dismiss();

                    }
                });
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, equipmentNames);
                eqList.setAdapter(adapter);
                dialog.show();
                return false;
            }
        });



        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!TextUtils.isEmpty(tool.getText())&&!TextUtils.isEmpty(cause.getText())){

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("equipmentId",equipHash.get(tool.getText().toString()));
                    jsonObject.addProperty("taskSeverity",severitySpinner.getSelectedItem().toString());
                    jsonObject.addProperty("errorDescription",cause.getText().toString());

                    Call<String> taskadd = jsonPlaceHolderApi.addTask(jsonObject);

                    taskadd.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (!response.isSuccessful()) {
                                Log.d("addTask: ", "task add failed: " +response.code());
                        }
                            else{
                                new AlertDialog.Builder(getContext())
                                        .setTitle(R.string.databaseUpdated)
                                        .setMessage(R.string.databaseUpdated2)
                                        .setIcon(context.getDrawable(R.drawable.ic_baseline_check_24))
                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                FragmentManager manager = getFragmentManager();
                                                FragmentTransaction transaction = manager.beginTransaction();
                                                transaction.replace(R.id.fragment_container, new listTasksFragment(), "");
                                                transaction.addToBackStack(null);
                                                transaction.commit();

                                            }
                                        })
                                        .show();

                            }}

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("addTask", "Failure: "+ t.toString());

                        }
                    });
                }

            }
        });

        return view;
    }
}