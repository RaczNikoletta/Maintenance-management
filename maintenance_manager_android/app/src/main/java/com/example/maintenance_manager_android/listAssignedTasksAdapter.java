package com.example.maintenance_manager_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
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
            TextView status;
            TextView taskSeverity;
            TextView date;
            TextView locationTv;
            TextView errorDesc;
            TextView startTime;
            LinearLayout secondLin;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ListAssignedTasksModel dataModel = getItem(position);

            final com.example.maintenance_manager_android.listAssignedTasksAdapter.ViewHolder viewHolder;

            if (convertView == null) {

                viewHolder = new com.example.maintenance_manager_android.listAssignedTasksAdapter.ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.list_assigned_tasks_listview, parent, false);

                viewHolder.status = (TextView) convertView.findViewById(R.id.taskLocation);
                viewHolder.taskSeverity = (TextView) convertView.findViewById(R.id.taskSeverityAssigned);
                viewHolder.date = (TextView) convertView.findViewById(R.id.taskDate);
                viewHolder.locationTv = convertView.findViewById(R.id.locationTv);
                viewHolder.errorDesc = convertView.findViewById(R.id.errorDesc);
                viewHolder.secondLin = convertView.findViewById(R.id.secondLin);
                viewHolder.startTime = convertView.findViewById(R.id.startTime);

                viewHolder.secondLin.setVisibility(View.INVISIBLE);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (com.example.maintenance_manager_android.listAssignedTasksAdapter.ViewHolder) convertView.getTag();
            }

            viewHolder.status.setText(dataModel.getStatus());
            viewHolder.date.setText(dataModel.getDate().toString());
            viewHolder.taskSeverity.setText(dataModel.getSeverity());
            if(dataModel.getStatus().equals("elfogadott")){
                viewHolder.secondLin.setVisibility(View.VISIBLE);
            }

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

            if(viewHolder.status.getText().equals("elkezdve")){
                viewHolder.locationTv.setText(dataModel.getLocation());
                viewHolder.errorDesc.setText(dataModel.getErrorDesc());
                //viewHolder.startTime.setText(dataModel.ge);

            }


            return convertView;
        }
    }


