package com.example.maintenance_manager_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.maintenance_manager_android.model.ListTasksModel;

import java.util.List;

public class listTasksAdapter extends ArrayAdapter<ListTasksModel>{
    private Context context;
    private List<ListTasksModel> taskList;
    public static final String TAG = "ListView";

    public listTasksAdapter(List<ListTasksModel> data, Context context) {
        super(context, R.layout.list_tasks_listview, data);
        this.taskList = data;
        this.context=context;

    }

    private static class ViewHolder {
        TextView taskID;
        TextView equipment;
        TextView taskState;
        TextView taskSeverity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListTasksModel dataModel = getItem(position);

        final ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_tasks_listview, parent, false);

            viewHolder.taskID = (TextView) convertView.findViewById(R.id.taskID);
            viewHolder.equipment = (TextView) convertView.findViewById(R.id.taskLocation);
            viewHolder.taskState = (TextView) convertView.findViewById(R.id.taskSeverityAssigned);
            viewHolder.taskSeverity = (TextView) convertView.findViewById(R.id.taskDate);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.taskID.setText(Integer.toString(dataModel.getTaskId()));
        viewHolder.equipment.setText(dataModel.getEquipment());
        viewHolder.taskState.setText(dataModel.getTaskState());
        viewHolder.taskSeverity.setText(dataModel.getTaskSeverity());

        if(viewHolder.taskSeverity.getText().toString().equalsIgnoreCase("alacsony"))
        {
            viewHolder.taskSeverity.setTextColor(getContext().getResources().getColor(R.color.green));
        }if(viewHolder.taskSeverity.getText().toString().equalsIgnoreCase("közepes")){
            viewHolder.taskSeverity.setTextColor(getContext().getResources().getColor(R.color.yellow));
        }if(viewHolder.taskSeverity.getText().toString().equalsIgnoreCase("magas")){
            viewHolder.taskSeverity.setTextColor(getContext().getResources().getColor(R.color.orange));
        }if(viewHolder.taskSeverity.getText().toString().equalsIgnoreCase("kritikus")){
            viewHolder.taskSeverity.setTextColor(getContext().getResources().getColor(R.color.red));
        }


        return convertView;
    }
}
