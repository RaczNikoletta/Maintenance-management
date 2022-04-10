package com.example.maintenance_manager_android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class manageTasksFragment extends Fragment {

    EditText toolId;
    EditText reqProf;
    EditText employees;
    Button manageTask;

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
        reqProf = view.findViewById(R.id.reqProf);
        employees = view.findViewById(R.id.employeeId);
        manageTask = view.findViewById(R.id.manageTask);

        manageTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }
}