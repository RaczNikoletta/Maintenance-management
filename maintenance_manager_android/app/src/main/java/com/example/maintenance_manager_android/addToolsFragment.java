package com.example.maintenance_manager_android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class addToolsFragment extends Fragment {

    public addToolsFragment() {
        // Required empty public constructor
    }

    private EditText nameEt;
    private EditText catEt;
    private EditText descEt;
    private EditText placeEt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_toolsfragment, container,false);
        setRetainInstance(true);

        return view;
    }
}