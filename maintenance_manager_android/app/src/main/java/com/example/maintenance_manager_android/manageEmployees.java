package com.example.maintenance_manager_android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class manageEmployees extends Fragment {

    ImageButton addUsersImageButton;
    ImageButton manageUserImageButton;

    public manageEmployees() {
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
        View view = inflater.inflate(R.layout.fragment_manage_employees, container, false);
        addUsersImageButton = view.findViewById(R.id.addUsersImageButton);
        manageUserImageButton = view.findViewById(R.id.manageUserImageButton);

        addUsersImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container, new addUsersFragment(), "");
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}