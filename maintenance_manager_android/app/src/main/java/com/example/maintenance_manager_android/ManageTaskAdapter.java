package com.example.maintenance_manager_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.maintenance_manager_android.model.ManageTaskModel;

import java.util.List;

public class ManageTaskAdapter  extends ArrayAdapter<ManageTaskModel> {
    private Context activityContext;
    private List<ManageTaskModel> randomlist;
    public static final String TAG = "ListView";

    public ManageTaskAdapter(Context context,List<ManageTaskModel> randomlist) {
        super(context,R.layout.managetaskslistview, randomlist);
        this.activityContext = context;
        this.randomlist = randomlist;
    }
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup){

        final ViewHolder viewHolder;

        if (view == null) {
            view = LayoutInflater.from(activityContext).inflate(R.layout.managetaskslistview, null);
            viewHolder = new ViewHolder();

            viewHolder.top = (TextView) view.findViewById(R.id.top);
            viewHolder.bottom = (TextView) view.findViewById(R.id.bottom);
            viewHolder.top.setText(Integer.toString(randomlist.get(position).getId()));
            viewHolder.bottom.setText(randomlist.get(position).getSeverity());

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        return view;
    }

    private static class ViewHolder {

        TextView top;
        TextView bottom;
    }


}

