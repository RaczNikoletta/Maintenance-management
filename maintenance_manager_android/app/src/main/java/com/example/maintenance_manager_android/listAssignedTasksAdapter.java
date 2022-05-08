package com.example.maintenance_manager_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.maintenance_manager_android.model.ListAssignedTasksModel;

import java.util.List;

public class listAssignedTasksAdapter extends ArrayAdapter<ListAssignedTasksModel> {

        private Context context;
        private List<ListAssignedTasksModel> taskList;
        public static final String TAG = "ListView";

        public listAssignedTasksAdapter(List<ListAssignedTasksModel> data, Context context) {
            super(context, R.layout.list_assigned_tasks_listview, data);
            this.taskList = data;
            this.context=context;

        }

        private static class ViewHolder {
            TextView location;
            TextView taskSeverity;
            TextView date;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ListAssignedTasksModel dataModel = getItem(position);

            final com.example.maintenance_manager_android.listAssignedTasksAdapter.ViewHolder viewHolder;

            if (convertView == null) {

                viewHolder = new com.example.maintenance_manager_android.listAssignedTasksAdapter.ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.list_assigned_tasks_listview, parent, false);

                viewHolder.location = (TextView) convertView.findViewById(R.id.taskLocation);
                viewHolder.taskSeverity = (TextView) convertView.findViewById(R.id.taskSeverityAssigned);
                viewHolder.date = (TextView) convertView.findViewById(R.id.taskDate);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (com.example.maintenance_manager_android.listAssignedTasksAdapter.ViewHolder) convertView.getTag();
            }

            viewHolder.location.setText(dataModel.getLocation());
            viewHolder.date.setText(dataModel.getDate().toString());
            viewHolder.taskSeverity.setText(dataModel.getSeverity());

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


