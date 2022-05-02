package com.example.maintenance_manager_android;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.maintenance_manager_android.model.QualificationModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class addUsersFragment extends Fragment {
    private Spinner roleSpinner;
    private Button sendWorkerBtn;
    private EditText newWorkerPassword;
    private EditText lastnameEt;
    private EditText firstNameEt;
    private EditText addNewWorkerusername;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private ArrayList<QualificationModel> profs;
    private EditText positionEt2;
    private HashMap<String,Integer> professions;
    private ArrayList<String> professionNames;
    private  ArrayAdapter<String> Customadapter;
    private ListView profList;
    private String clickedProf;
    private TextView kepesitesTv;


    public addUsersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_worker, container,false);

        addNewWorkerusername = view.findViewById(R.id.addNewWorkerusernameEt);
        firstNameEt = view.findViewById(R.id.firstNameEt);
        lastnameEt = view.findViewById(R.id.lastnameEt);
        newWorkerPassword = view.findViewById(R.id.newWorkerPasswordEt);
        sendWorkerBtn = view.findViewById(R.id.sendWorkerBtn);
        positionEt2 = view.findViewById(R.id.positionEt2);
        profs = new ArrayList<>();
        roleSpinner = view.findViewById(R.id.spinner2);
        kepesitesTv = view.findViewById(R.id.kepesitesTv);

        professions = new HashMap<String,Integer>();
        professionNames = new ArrayList<>();

        setRetainInstance(true); // to enable changing orientation of the mobile
        getProfessions();
        ArrayList<String> roles = new ArrayList<>();
        roles.add("admin");
        roles.add(getString(R.string.maintainer));
        roles.add(getString(R.string.toolManager));
        roles.add("operator");
        Customadapter = new ArrayAdapter<String>(getContext(), R.layout.prof_spinner_item,roles);
        roleSpinner.setAdapter(Customadapter);

        //arrayAdapter.notifyDataSetChanged();



        sendWorkerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                if(!TextUtils.isEmpty(addNewWorkerusername.getText())
                && !TextUtils.isEmpty(firstNameEt.getText())
                        && !TextUtils.isEmpty(lastnameEt.getText())
                        && !TextUtils.isEmpty(newWorkerPassword.getText())
                        && roleSpinner.getSelectedItem() != null)
                {



                    String namestring = firstNameEt.getText().toString() + " " + lastnameEt.getText().toString().toUpperCase();
                    String username = addNewWorkerusername.getText().toString();
                    String pass = newWorkerPassword.getText().toString();
                    String pos = roleSpinner.getSelectedItem().toString();
                    int profid;
                        if(roleSpinner.getSelectedItem().equals("karbantarto"))
                        {
                            if(!TextUtils.isEmpty(positionEt2.getText())) {
                                profid = professions.get((positionEt2.getText().toString()));
                            }else
                                profid = 1;
                        }else{
                            profid = 1;
                        }
                        Call<String> call2 = jsonPlaceHolderApi.createUser(username,namestring,pass,pos,profid);

                    try {
                        call2.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {

                                Log.d("response",response.message());
                                if (response.code() == 200) {
                                    Log.v("create","Success"+ response.body());

                                    Log.d("addeduser", "ok");
                                    new AlertDialog.Builder(getContext())
                                            .setTitle(R.string.databaseUpdated)
                                            .setMessage(R.string.databaseUpdated2)
                                            .setIcon(getResources().getDrawable(R.drawable.ic_baseline_check_24))
                                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    FragmentManager manager = getFragmentManager();
                                                    FragmentTransaction transaction = manager.beginTransaction();
                                                    transaction.replace(R.id.fragment_container, new manageEmployees(), "");
                                                    transaction.addToBackStack(null);
                                                    transaction.commit();

                                                }
                                            })
                                            .show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.d("addeduser", t.toString());


                            }
                        });

                    }catch (Throwable e){
                    Log.d("adduser_exception",e.toString());
                    }
                }


            }catch (Throwable e){
                    Log.d("adduser_exception",e.toString());
                }
            }
        });

        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if ((roleSpinner.getSelectedItem().toString().equals("karbantarto"))) {
                    positionEt2.setVisibility(View.VISIBLE);
                    kepesitesTv.setVisibility(View.VISIBLE);
                    positionEt2.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            final Dialog dialog = new Dialog(getContext());
                            dialog.setContentView(R.layout.prof_dialog);
                            //dialog.setCancelable(true);
                            profList = (ListView) dialog.findViewById(R.id.List);
                            profList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    clickedProf = profList.getItemAtPosition(position).toString();
                                    positionEt2.setText(clickedProf);
                                    dialog.dismiss();

                                }
                            });
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, professionNames);
                            profList.setAdapter(adapter);
                            dialog.show();

                            return false;
                        }
                    });

                }else{
                    positionEt2.setVisibility(View.INVISIBLE);
                    kepesitesTv.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                positionEt2.setVisibility(View.INVISIBLE);
                kepesitesTv.setVisibility(View.INVISIBLE);
            }
        });




        return view;
    }

    public void getProfessions(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        //get professions
        Call<ArrayList<QualificationModel>> call = jsonPlaceHolderApi.getQuals();


        call.enqueue(new Callback<ArrayList<QualificationModel>>() {
            @Override
            public void onResponse(Call<ArrayList<QualificationModel>> call, Response<ArrayList<QualificationModel>> response) {
                if (!response.isSuccessful()) {
                    Log.d("Get request failed: "," "+response.code());
                } else {
                    profs = response.body();
                    Log.d("prof", profs.get(0).getqName());
                    for (int i = 0; i < profs.size(); i++) {
                        if(profs.get(i).getqName().equalsIgnoreCase("admin")){

                        }else {
                            professionNames.add(profs.get(i).getqName());
                            professions.put(profs.get(i).getqName(), profs.get(i).getqId());
                        }
                    }
                }

            }
            @Override
            public void onFailure(Call<ArrayList<QualificationModel>> call, Throwable t) {
                Log.d("professionFailure", t.toString());
            }
        });


    }

}