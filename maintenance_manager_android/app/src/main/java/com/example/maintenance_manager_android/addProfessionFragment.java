package com.example.maintenance_manager_android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class addProfessionFragment extends Fragment {

    ListView professionListView;
    ImageButton addProfButton;



    public addProfessionFragment() {
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
        View view = inflater.inflate(R.layout.fragment_add_profession, container, false);

       professionListView =  view.findViewById(R.id.professionList);
       addProfButton = view.findViewById(R.id.addProfButton);
        ArrayList<String> professionList = new ArrayList<>();
        professionList.add("test");
        ListAdapter listAdapter = new professionListAdapter(getActivity(),R.layout.profession_list,professionList);
        professionListView.setAdapter(listAdapter);

        addProfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });


        return view;
    }
}