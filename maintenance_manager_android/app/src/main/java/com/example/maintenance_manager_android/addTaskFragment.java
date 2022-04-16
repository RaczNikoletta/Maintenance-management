package com.example.maintenance_manager_android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class addTaskFragment extends Fragment {

    EditText tool;
    EditText cause;
    DatePicker datePicker;
    Button addTask;

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

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }
}