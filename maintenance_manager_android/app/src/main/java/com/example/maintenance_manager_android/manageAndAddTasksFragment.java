package com.example.maintenance_manager_android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.maintenance_manager_android.model.ManageTaskModel;

import java.util.ArrayList;
import java.util.List;


public class manageAndAddTasksFragment extends Fragment {


    public manageAndAddTasksFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    ListView taskViewListView;
    ImageButton addTaskButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage_and_add_tasks, container, false);

        taskViewListView = view.findViewById(R.id.taskViewListView);
        addTaskButton = view.findViewById(R.id.addTaskButton);

        taskViewListView = view.findViewById(R.id.taskViewListView);

        List<ManageTaskModel> list = new ArrayList<>();
        list.add(new ManageTaskModel(1,"randomSeverity"));

        ListAdapter adapter = new ManageTaskAdapter(taskViewListView.getContext(),list);
        taskViewListView.setAdapter(adapter);

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
}