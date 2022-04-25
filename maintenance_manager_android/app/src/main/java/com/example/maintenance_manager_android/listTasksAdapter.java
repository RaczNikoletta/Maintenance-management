package com.example.maintenance_manager_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
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
        TextView equipmentId;
        TextView professionId;
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
            viewHolder.equipmentId = (TextView) convertView.findViewById(R.id.equipmentId);
            viewHolder.professionId = (TextView) convertView.findViewById(R.id.professionId);
            viewHolder.taskState = (TextView) convertView.findViewById(R.id.taskState);
            viewHolder.taskSeverity = (TextView) convertView.findViewById(R.id.taskSeverity);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.taskID.setText(Integer.toString(dataModel.getTaskId()));
        viewHolder.equipmentId.setText(Integer.toString(dataModel.getEquipmentId()));
        viewHolder.professionId.setText(Integer.toString(dataModel.getQualificationID()));
        viewHolder.taskState.setText(dataModel.getTaskState());
        viewHolder.taskSeverity.setText(dataModel.getTaskSeverity());

        return convertView;
    }
}
