package com.example.maintenance_manager_android;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.maintenance_manager_android.model.EquipmentModel;
import com.example.maintenance_manager_android.model.ListAssignedTasksModel;
import com.example.maintenance_manager_android.model.ListTasksModel;
import com.example.maintenance_manager_android.model.TaskModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.internal.concurrent.Task;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class assignedTasksFragment extends Fragment {
    private ListView listView;
    private List<ListAssignedTasksModel> list;
    private int clickedPos;
    private ArrayList<TaskModel> taskList;
    private ArrayList<String> locationList;
    private ArrayList <Date> dates;
    private ArrayList <String> severities;
    private ArrayList <Integer> toolIds;
    private  JsonPlaceHolderApi jsonPlaceHolderApi;
    private ArrayList<EquipmentModel> equipmentModels;
    private ArrayList<Integer> taskids;
    private String status = "kiosztva";
    private ArrayList<String> allStatus;
    private ArrayList<String> equipmentNames;
    private ArrayList<String> errors;
    private Date tempdate;
    private String dateStr = "0000-00-00' '00:00:00";
    private ArrayList<Date> startDate;
    private Bundle bundle;
    private String reason;
    private ArrayList<Date> assignTime;



    public assignedTasksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assignedtasks, container, false);
        taskList = new ArrayList<>();
        list = new ArrayList<>();
        severities = new ArrayList<>();
        dates = new ArrayList<Date>();
        locationList = new ArrayList<>();
        listView = view.findViewById(R.id.taskAssigned);
        registerForContextMenu(listView);
        toolIds = new ArrayList<>();
        equipmentNames = new ArrayList<>();
        taskids = new ArrayList<>();
        allStatus = new ArrayList<>();
        errors = new ArrayList<>();
        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
        try {
            tempdate = (Date) curFormater.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        startDate = new ArrayList<>();
        assignTime = new ArrayList<>();


        Gson gson = new GsonBuilder()
                .setLenient()
                .setDateFormat("yyyy-MM-dd' 'HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/")
                .addConverterFactory(ScalarsConverterFactory.create()) //without this line "JSON is not fully consumed"
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        //status: kiosztva
        int userID = this.getActivity().getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE)
                .getInt("userId", -1);
        //Log.d("userid: ", Integer.toString(userID));
        Call<ArrayList<TaskModel>> assignedTasks = jsonPlaceHolderApi.getTaskInProgress(userID,"all");
        Call<ArrayList<EquipmentModel>> locations = jsonPlaceHolderApi.getEquipments();


        assignedTasks.enqueue(new Callback<ArrayList<TaskModel>>() {
            @Override
            public void onResponse(Call<ArrayList<TaskModel>> call, Response<ArrayList<TaskModel>> response) {
                if(!response.isSuccessful()){
                    Log.d("Get assigned tasks: ", ""+response.code());

                }else{
                    taskList = response.body();
                    //Log.d("else ag",Integer.toString(taskList.size()));
                    for(int i=0;i<taskList.size();i++){
                        if(!taskList.get(i).getAllapot().equals("elutasitott")) {
                            toolIds.add(taskList.get(i).getEszoz_id());
                            dates.add(taskList.get(i).getKiosztva());
                            severities.add(taskList.get(i).getSulyossag());
                            taskids.add(taskList.get(i).getFeladat_id());
                            allStatus.add(taskList.get(i).getAllapot());
                            if (!(taskList.get(i).getAllapot().equals("kiosztva") || taskList.get(i).getAllapot().equals("elfogadott")
                                    || taskList.get(i).getAllapot().equals("elutasitott"))) {
                                startDate.set(i, taskList.get(i).getFelveve());
                            }
                            assignTime.add(taskList.get(i).getKiosztva());

                            errors.add(taskList.get(i).getHiba_leiras());
                        }
                    }

                    locations.enqueue(new Callback<ArrayList<EquipmentModel>>() {
                        @Override
                        public void onResponse(Call<ArrayList<EquipmentModel>> call, Response<ArrayList<EquipmentModel>> response) {
                            if(!response.isSuccessful()) {
                                Log.d("locations", "get equipments failed: "+ response.code());
                            }else{
                                equipmentModels = response.body();
                                for(int i=0;i<equipmentModels.size();i++){
                                    Log.d("i",Integer.toString(i));
                                    equipmentNames.add(equipmentModels.get(i).getEquipmentName());
                                    //Log.d("equipmentname:",equipmentNames.get(i));
                                    for(int j=0;j<severities.size();j++){
                                        Log.d("j",Integer.toString(j));
                                        if(equipmentModels.get(i).getEquipmentId()==toolIds.get(j)){
                                            locationList.add(equipmentModels.get(i).getSite());
                                        }
                                    }
                                }
                                createList();
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<EquipmentModel>> call, Throwable t) {
                            Log.d("getEquipments ", t.toString());

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TaskModel>> call, Throwable t) {
                Log.d("onfailure","assigned tasks failure");

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                clickedPos = position;

                ListAssignedTasksModel taskModel = (ListAssignedTasksModel) (parent.getItemAtPosition(position));

                bundle = new Bundle();
                bundle.putString("equipmentName", equipmentNames.get(position));
                Log.d("equipmentName", taskModel.getDate().toString());
                bundle.putString("state", taskModel.getStatus());
                Log.d("state", taskModel.getStatus());
                bundle.putString("severity", taskModel.getSeverity());
                bundle.putString("cause_error", taskModel.getErrorDesc());
                //bundle.putString("get_time", taskModel.getStartTime().toString());
                //Log.d("datum", DateFormat.format("yyyy-MM-dd' 'HH:mm:ss", taskModel.getStartTime()).toString());
                return false;
            }
        });



        return view;
    }

    public void createList(){
        for(int i=0;i<toolIds.size();i++){
            if(!(allStatus.get(i).equals("kiosztva")  || allStatus.get(i).equals("elfogadott") || allStatus.get(i).equals("elutasitott"))) {
                list.add(new ListAssignedTasksModel(allStatus.get(i), severities.get(i), dates.get(i)
                        , locationList.get(i), errors.get(i),startDate.get(i)));
            }else{
                list.add(new ListAssignedTasksModel(allStatus.get(i), severities.get(i), dates.get(i)
                        , locationList.get(i), errors.get(i),tempdate));
            }
        }
        listAssignedTasksAdapter adapter = new listAssignedTasksAdapter(list,listView.getContext());
        listView.setAdapter(adapter);

    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo info) {
        super.onCreateContextMenu(menu,v,info);
        //menu.setHeaderTitle("Lehetőségek");
        //menu.add(Menu.NONE,0,menu.NONE,"Részletek");
        //menu.add(Menu.NONE,1,menu.NONE,"Elfogad");
        //menu.add(Menu.NONE,2,menu.NONE,"Elutasít");
        if(allStatus.get(clickedPos).equals("kiosztva")) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.assigned_task_menu, menu);

        }else if(allStatus.get(clickedPos).equals("elfogadott")){
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.accepted_task_menu, menu);

        }
    }

    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.option_1:
                Log.d("option 1", "option1 selected");
                changeToShowFragment();
                break;
            case R.id.option_2:
                Call<String>acceptTask = jsonPlaceHolderApi.changeTaskStatus(taskids.get(clickedPos),"elfogadott","reason");
                acceptTask.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (!response.isSuccessful()) {
                            Log.d("acceptation failed", Integer.toString(response.code()));
                        } else {

                            Toast.makeText(getActivity(), "Feladat sikeresen elfogadva", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("acceptation failed",t.toString());

                    }
                });
                break;
            case R.id.option_3:
                declineTask();
                break;
            case R.id.accept_option_1:
                changeToShowFragment();
                break;
            case R.id.accept_option_2:
                break;
            case R.id.accept_option_3:
               declineTask();
                break;
        }

        return true;
    }

    public void declineTask(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        final EditText edittext = new EditText(getActivity());
        alert.setMessage("Indok");
        alert.setTitle("Elutasítás indoka");

        alert.setView(edittext);

        alert.setPositiveButton("Küldés", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                reason = edittext.getText().toString();
            }
        });

        alert.setNegativeButton("Mégse", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                alert.setCancelable(true);
            }
        });

        alert.show();
        Call<String>declineTask = jsonPlaceHolderApi.changeTaskStatus(taskids.get(clickedPos),"elutasitott",reason);
        declineTask.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {

                } else {

                    //Toast.makeText(getActivity(), "Feladat sikeresen elfogadva", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("acceptation failed",t.toString());

            }
        });
    }

    public void changeToShowFragment(){
        showTaskFragment fragment = new showTaskFragment();
        fragment.setArguments(bundle);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, "");
        transaction.addToBackStack(null);
        transaction.commit();
    }
}