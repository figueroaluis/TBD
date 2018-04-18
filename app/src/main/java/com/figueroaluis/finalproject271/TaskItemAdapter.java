package com.figueroaluis.finalproject271;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mshoga on 4/17/2018.
 */

public class TaskItemAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Task> mtaskList;
    private LayoutInflater mInflater;

    public TaskItemAdapter(Context mContext, ArrayList<Task> mtaskList){
        this.mtaskList = mtaskList;
        this.mContext = mContext;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount(){return mtaskList.size();}

    @Override
    public Object getItem(int position) {return mtaskList.get(position);}

    @Override
    public long getItemId(int position) {return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.task_item_layout, parent, false);

            holder = new ViewHolder();
            holder.taskTitleTextView = convertView.findViewById(R.id.list_task_title);
            holder.taskDateTextView = convertView.findViewById(R.id.list_task_date);
            holder.taskTimeTextView = convertView.findViewById(R.id.list_task_time);

            convertView.setTag(holder);

        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }


        TextView taskTitleTextView = holder.taskTitleTextView;
        TextView taskDateTextView = holder.taskDateTextView;
        TextView taskTimeTextView = holder.taskTimeTextView;

        final Task task = (Task) getItem(position);

        taskTitleTextView.setText(task.getTitle());
        taskTitleTextView.setTextSize(18);

        taskDateTextView.setText(task.getDate());
        taskDateTextView.setTextSize(14);

        taskTimeTextView.setText(task.getTime());
        taskTimeTextView.setTextSize(14);

        return convertView;

    }




    private static class ViewHolder{
        public TextView taskTitleTextView;
        public TextView taskDateTextView;
        public TextView taskTimeTextView;

    }
}
