package com.example.maintenance_manager_android;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.maintenance_manager_android.model.EquipmentModel;
import com.example.maintenance_manager_android.model.ListAssignedTasksModel;
import com.example.maintenance_manager_android.model.TaskModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class assignedTasksFragment extends Fragment {
    private ListView listView;
    private List<ListAssignedTasksModel> list;
    private ArrayList<TaskModel> taskList;
    private ArrayList<String> locationList;
    private ArrayList <Date> dates;
    private ArrayList <String> severities;
    private ArrayList <Integer> toolIds;
    private  JsonPlaceHolderApi jsonPlaceHolderApi;
    private ArrayList<EquipmentModel> equipmentModels;


    public assignedTasksFragment() {
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
        View view = inflater.inflate(R.layout.fragment_assignedtasks, container, false);
        taskList = new ArrayList<>();
        list = new ArrayList<>();
        severities = new ArrayList<>();
        dates = new ArrayList<Date>();
        locationList = new ArrayList<>();
        listView = view.findViewById(R.id.taskAssigned);
        toolIds = new ArrayList<>();
        equipmentModels = new ArrayList<>();

        Gson gson = new GsonBuilder()
                .setLenient()
                .setDateFormat("yyyy-MM-dd' 'HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/")
                .addConverterFactory(ScalarsConverterFactory.create()) //without this line "JSON is not fully consumed"
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        //status: kiosztva
        int userID = this.getActivity().getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE)
                .getInt("userId", -1);
        Log.d("userid: ", Integer.toString(userID));
        Call<ArrayList<TaskModel>> assignedTasks = jsonPlaceHolderApi.getTaskInProgress(userID,"kiosztva");
        Call<ArrayList<EquipmentModel>> locations = jsonPlaceHolderApi.getEquipments();

        assignedTasks.enqueue(new Callback<ArrayList<TaskModel>>() {
            @Override
            public void onResponse(Call<ArrayList<TaskModel>> call, Response<ArrayList<TaskModel>> response) {
                if(!response.isSuccessful()){
                    Log.d("Get assigned tasks: ", ""+response.code());

                }else{
                    taskList = response.body();
                    Log.d("else ag",Integer.toString(taskList.size()));
                    for(int i=0;i<taskList.size();i++){
                        toolIds.add(taskList.get(i).getEszoz_id());
                        dates.add(taskList.get(i).getKiosztva());
                        severities.add(taskList.get(i).getSulyossag());
                    }

                    locations.enqueue(new Callback<ArrayList<EquipmentModel>>() {
                        @Override
                        public void onResponse(Call<ArrayList<EquipmentModel>> call, Response<ArrayList<EquipmentModel>> response) {
                            if(!response.isSuccessful()) {
                                Log.d("locations", "get equipments failed: "+ response.code());
                            }else{
                                equipmentModels = response.body();
                                for(int i=0;i<equipmentModels.size();i++){
                                    Log.d("i",Integer.toString(i));
                                    for(int j=0;j<severities.size();j++){
                                        Log.d("j",Integer.toString(j));
                                        if(equipmentModels.get(i).getEquipmentId()==toolIds.get(j)){
                                            locationList.add(equipmentModels.get(i).getSite());
                                        }
                                    }
                                }
                                createList();
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<EquipmentModel>> call, Throwable t) {
                            Log.d("getEquipments ", t.toString());

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TaskModel>> call, Throwable t) {
                Log.d("onfailure","assigned tasks failure");

            }
        });



        return view;
    }

    public void createList(){
        for(int i=0;i<toolIds.size();i++){
            list.add(new ListAssignedTasksModel(locationList.get(i),severities.get(i),dates.get(i)));
            Log.d("severity",severities.get(i));
        }
        listAssignedTasksAdapter adapter = new listAssignedTasksAdapter(list,listView.getContext());
        listView.setAdapter(adapter);

    }
}