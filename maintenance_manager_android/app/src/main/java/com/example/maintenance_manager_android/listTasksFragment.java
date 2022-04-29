package com.example.maintenance_manager_android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.maintenance_manager_android.model.EquipmentModel;
import com.example.maintenance_manager_android.model.ListTasksModel;
import com.example.maintenance_manager_android.model.TaskModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class listTasksFragment extends Fragment {

    ListView tasksList;
    private ImageButton addTaskButton;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private ArrayList<TaskModel> taskList;
    private ArrayList<Integer> taskId;
    private ArrayList<String> status;
    private ArrayList <Integer> toolId;
    private ArrayList <String> severities;
    private ArrayList<EquipmentModel> equipmentModels;
    private  List<ListTasksModel> list;

    public listTasksFragment() {
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
        View view = inflater.inflate(R.layout.fragment_list_tasks, container, false);

        tasksList = (ListView)view.findViewById(R.id.tasksList);
        addTaskButton = view.findViewById(R.id.addTaskButton);
        taskList = new ArrayList<>();
        taskId = new ArrayList<>();
        status = new ArrayList<>();
        toolId = new ArrayList<>();
        severities = new ArrayList<>();
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



        list = new ArrayList<>();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<ArrayList<TaskModel>> tasks = jsonPlaceHolderApi.getTasks();
        Call<String> autoTask = jsonPlaceHolderApi.getAutoTasks();

        autoTask.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.d("AutoTasks ", "Automatic task generation failed " +response.code());


                }else{
                    Log.d("AutoTasks","Automatic task are successfully generated");
                    }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("AutoTasks: ", t.toString());
            }
        });

        tasks.enqueue(new Callback<ArrayList<TaskModel>>() {
            @Override
            public void onResponse(Call<ArrayList<TaskModel>> call, Response<ArrayList<TaskModel>> response) {
                if (!response.isSuccessful()) {
                    Log.d("Get request failed: ", " " + response.code());
                } else {
                    taskList = response.body();
                    Log.d("resp"," "+response.code());
                    Log.d("prof", " "+taskList.get(0).getEszoz_id());
                    for (int i = 0; i < taskList.size(); i++) {
                        taskId.add(taskList.get(i).getFeladat_id());
                        toolId.add(taskList.get(i).getEszoz_id());
                        status.add(taskList.get(i).getAllapot());
                        severities.add(taskList.get(i).getSulyossag());
                    }
                    createList();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<TaskModel>> call, Throwable t) {
                Log.d("onfailure", "taklist failure: " + t.toString());
            }
        });


        tasksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Intent intent = new Intent(getActivity(), manageTasksFragment.class);
                // startActivity(intent);
            }
        });


        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container, new addTaskFragment(), "");
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    ///@Override
    /*public boolean onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }*/

    public void createList(){
        for(int i=0;i<taskId.size();i++) {
            list.add(new ListTasksModel(taskId.get(i),toolId.get(i).toString(),status.get(i),severities.get(i)));
            Log.d("toolid:", " " +toolId.get(i));
        }
        listTasksAdapter adapter = new listTasksAdapter(list, tasksList.getContext());
        tasksList.setAdapter(adapter);
    }
}