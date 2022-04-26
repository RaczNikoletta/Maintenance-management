package com.example.maintenance_manager_android;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.maintenance_manager_android.model.CategoryModel;
import com.example.maintenance_manager_android.model.ListTasksModel;
import com.example.maintenance_manager_android.model.ManageTaskModel;
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

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/")
                .addConverterFactory(ScalarsConverterFactory.create()) //without this line "JSON is not fully consumed"
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        List<ListTasksModel> list = new ArrayList<>();
        list.add(new ListTasksModel(1,"Szalag","felvéve","alacsony"));
        list.add(new ListTasksModel(2,"Szalag","felvéve","közepes"));
        list.add(new ListTasksModel(3,"Szalag","felvéve","magas"));
        list.add(new ListTasksModel(4,"Szalag","visszautasítva","kritikus"));
        listTasksAdapter adapter = new listTasksAdapter(list, tasksList.getContext());
        tasksList.setAdapter(adapter);

        tasksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Intent intent = new Intent(getActivity(), manageTasksFragment.class);
                // startActivity(intent);
            }
        });



        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
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
}