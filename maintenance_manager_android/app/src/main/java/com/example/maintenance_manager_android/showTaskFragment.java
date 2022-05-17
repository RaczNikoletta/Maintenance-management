package com.example.maintenance_manager_android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class showTaskFragment extends Fragment {

    EditText equipmentName;
    EditText state;
    EditText severity;
    EditText cause_error;
    EditText get_time;
    EditText location;

    public showTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_task, container, false);

        equipmentName = view.findViewById(R.id.toolEd);
        state = view.findViewById(R.id.stateEd);
        severity = view.findViewById(R.id.severityEd);
        cause_error = view.findViewById(R.id.cause_error);
        get_time = view.findViewById(R.id.allocationTime);
        location = view.findViewById(R.id.EDlocation);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            equipmentName.setText(bundle.getString("equipmentName"));
            state.setText(bundle.getString("state"));
            severity.setText(bundle.getString("severity"));
            cause_error.setText(bundle.getString("cause_error"));
            get_time.setText(bundle.getString("get_time"));
            if(bundle.getString("location") != null) {
                location.setText(bundle.getString("location"));
            }
        }
        else{
            equipmentName.setText("Task not detected.");
        }

        return view;
    }
}