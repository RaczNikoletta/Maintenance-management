package com.example.maintenance_manager_android;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        ArrayAdapter<String> array = new ArrayAdapter<String>(getActivity(),
                android.R.layout.activity_list_item);
        // for (String str : array)
        //    array.add(str);
        tasksList.setAdapter(array);

        tasksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), manageTasksFragment.class);
                startActivity(intent);
            }
        });

        return view;
    }
}