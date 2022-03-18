package com.example.maintenance_manager_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class addUsersFragment extends Fragment {

    public addUsersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("addus","I am here");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("addus","I am here");
        View view = inflater.inflate(R.layout.fragment_add_worker, container,false);
        setRetainInstance(true); // to enable changing orientation of the mobile



        return view;
    }
}