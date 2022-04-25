package com.example.maintenance_manager_android;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.maintenance_manager_android.model.ListTasksModel;
import com.example.maintenance_manager_android.model.ManageTaskModel;

import java.util.ArrayList;
import java.util.List;

public class listTasksFragment extends Fragment {

    ListView tasksList;

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


        List<ListTasksModel> list = new ArrayList<>();
        list.add(new ListTasksModel(1,2,3,"felvéve","alacsony"));
        list.add(new ListTasksModel(1,2,3,"felvéve","kritikus"));
        list.add(new ListTasksModel(1,2,3,"visszautasítva","kritikus"));
        listTasksAdapter adapter = new listTasksAdapter(list, tasksList.getContext());
        tasksList.setAdapter(adapter);

        tasksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Intent intent = new Intent(getActivity(), manageTasksFragment.class);
                // startActivity(intent);
            }
        });

        return view;
    }
}