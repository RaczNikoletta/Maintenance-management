package com.example.maintenance_manager_android;

import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.ListView;

import com.example.maintenance_manager_android.model.CategoryModel;
import com.example.maintenance_manager_android.model.EmployeeModel;
import com.example.maintenance_manager_android.model.EquipmentModel;
import com.example.maintenance_manager_android.model.QualificationModel;
import com.example.maintenance_manager_android.model.SubCategoryModel;
import com.example.maintenance_manager_android.model.TaskModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Query;

public class manageTasksFragment extends Fragment {

    EditText toolId;
    EditText severity;
    EditText reqProf;
    EditText employees;
    Button manageTask;

    JsonPlaceHolderApi jsonPlaceHolderApi;
    ListView empList;
    String clickedemp;
    ArrayList<String> employeeNames;
    HashMap<String,Integer> empHash;
    HashMap<String,Integer> eqHash;
    HashMap<Integer,Integer> subcHash;
    HashMap<Integer,String> qualHash;
    int subcatId;
    int qualificationId;
    int taskID;

    public manageTasksFragment() {
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
        View view = inflater.inflate(R.layout.fragment_manage_tasks, container, false);

        toolId = view.findViewById(R.id.toolID);
        severity = view.findViewById(R.id.severity);
        reqProf = view.findViewById(R.id.reqProf);
        employees = view.findViewById(R.id.employeeId);
        manageTask = view.findViewById(R.id.manageTask);
        employeeNames = new ArrayList<>();
        empHash = new HashMap<>();
        eqHash = new HashMap<>();
        subcHash = new HashMap<>();
        qualHash = new HashMap<>();


        Bundle bundle = this.getArguments();
        if(bundle != null){
            toolId.setText(bundle.getString("equipment"));
            severity.setText(bundle.getString("severity"));
        }
        else{
            toolId.setText("ListItem not detected.");
        }

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/")
                .addConverterFactory(ScalarsConverterFactory.create()) //without this line "JSON is not fully consumed"
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<ArrayList<TaskModel>> tasks = jsonPlaceHolderApi.getTasks();

        Call<ArrayList<EquipmentModel>> equipments = jsonPlaceHolderApi.getEquipments();
        equipments.enqueue(new Callback<ArrayList<EquipmentModel>>() {
            @Override
            public void onResponse(Call<ArrayList<EquipmentModel>> call, Response<ArrayList<EquipmentModel>> response) {
                if (!response.isSuccessful()) {
                    Log.d("equipments: ", "equipment query failed: " +response.code());
                }else{
                    ArrayList<EquipmentModel> temp = response.body();
                    for(int i=0;i<temp.size();i++){
                        eqHash.put(Integer.toString(temp.get(i).getEquipmentId()),temp.get(i).getSubCategoryId());
                    }
                    subcatId = eqHash.get(bundle.getString("equipmentID"));
                    Log.d("equipmentID", bundle.getString("equipmentID"));
                    Call<ArrayList<SubCategoryModel>> subcats = jsonPlaceHolderApi.getSubCategories();
                    subcats.enqueue(new Callback<ArrayList<SubCategoryModel>>() {
                        @Override
                        public void onResponse(Call<ArrayList<SubCategoryModel>> call, Response<ArrayList<SubCategoryModel>> response) {
                            if (!response.isSuccessful()) {
                                Log.d("subcategories: ", "subcategory query failed: " +response.code());
                            }else{
                                ArrayList<SubCategoryModel> temp = response.body();
                                for(int i=0;i<temp.size();i++){
                                    subcHash.put(temp.get(i).getSubCategoryId(),temp.get(i).getQualificationId());
                                }
                                qualificationId = subcHash.get(subcatId);
                                Log.d("qualificationId",Integer.toString(qualificationId));
                                Call<ArrayList<QualificationModel>> quals = jsonPlaceHolderApi.getQuals();
                                quals.enqueue(new Callback<ArrayList<QualificationModel>>() {
                                    @Override
                                    public void onResponse(Call<ArrayList<QualificationModel>> call, Response<ArrayList<QualificationModel>> response) {
                                        if (!response.isSuccessful()) {
                                            Log.d("qualifications: ", "qualification query failed: " +response.code());
                                        }else{
                                            ArrayList<QualificationModel> temp = response.body();
                                            for(int i=0;i<temp.size();i++){
                                                qualHash.put(temp.get(i).getqId(),temp.get(i).getqName());
                                            }
                                            reqProf.setText(qualHash.get(qualificationId));
                                            Log.d("reqProf",reqProf.getText().toString());

                                            Call<ArrayList<EmployeeModel>> emps = jsonPlaceHolderApi.getUsersByQualification(qualificationId);
                                            emps.enqueue(new Callback<ArrayList<EmployeeModel>>() {
                                                @Override
                                                public void onResponse(Call<ArrayList<EmployeeModel>> call, Response<ArrayList<EmployeeModel>> response) {
                                                    if (!response.isSuccessful()) {
                                                        Log.d("employee: ", Integer.toString(qualificationId));
                                                        Log.d("employee: ", "employee query failed: " +response.code());
                                                    }else{
                                                        ArrayList<EmployeeModel> temp = response.body();
                                                        for(int i=0;i<temp.size();i++){
                                                            employeeNames.add(temp.get(i).getNev());
                                                            empHash.put(temp.get(i).getNev(),temp.get(i).getId());
                                                        }
                                                        for(Map.Entry<String,Integer> entry: empHash.entrySet()){
                                                            Log.d("empkey",entry.getKey()+ " " +entry.getValue());
                                                        }
                                                    }
                                                }
                                                @Override
                                                public void onFailure(Call<ArrayList<EmployeeModel>> call, Throwable t) {
                                                    Log.d("employees: ", t.toString());
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ArrayList<QualificationModel>> call, Throwable t) {
                                        Log.d("qualifications: ", t.toString());
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<SubCategoryModel>> call, Throwable t) {
                            Log.d("subcategories: ", t.toString());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ArrayList<EquipmentModel>> call, Throwable t) {
                Log.d("equipments: ", t.toString());
            }
        });

        Log.d("taskID", bundle.getString("taskID"));
        taskID = Integer.parseInt(bundle.getString("taskID"));
        employees.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.prof_dialog);
                dialog.setCancelable(true);
                empList = (ListView) dialog.findViewById(R.id.List);
                empList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        clickedemp = empList.getItemAtPosition(position).toString();
                        employees.setText(clickedemp);
                        dialog.dismiss();

                    }
                });
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, employeeNames);
                empList.setAdapter(adapter);
                dialog.show();
                return false;
            }
        });


        manageTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(toolId.getText())&&!TextUtils.isEmpty(severity.getText())&&!TextUtils.isEmpty(reqProf.getText())
                        &&!TextUtils.isEmpty(employees.getText())&&(taskID!=0))
                {
                    //String userID = employees.getText().toString();
                    String userID = empHash.get(employees.getText().toString()).toString();
                    Log.d("userID",userID);
                    Call<String> assignTask  = jsonPlaceHolderApi.assignTask(Integer.parseInt(userID),taskID);
                    assignTask.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(!response.isSuccessful()){
                                Log.d("Assign task: ", ""+response.code());

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
                                                transaction.replace(R.id.fragment_container, new listTasksFragment(), "");
                                                transaction.addToBackStack(null);
                                                transaction.commit();

                                            }
                                        })
                                        .show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("assignTask", "Failure: "+ t.toString());

                        }
                    });
                }
            }
        });

        return view;
    }
}