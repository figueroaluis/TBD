package com.figueroaluis.finalproject271;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by luisfigueroa on 4/17/18.
 */

public class TaskListAdapter extends BaseAdapter {
    // instance variables for the main lists
    private Context mContext;
    private ArrayList<TaskList> mainTaskLists;
    private LayoutInflater mInflater;


    public TaskListAdapter(Context mContext, ArrayList<TaskList> mainTaskLists) {
        this.mContext = mContext;
        this.mainTaskLists = mainTaskLists;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        return mainTaskLists.size();
    }

    @Override
    public Object getItem(int position) {
        return mainTaskLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.task_list_item, parent, false);

            holder = new ViewHolder();

            holder.taskListTitleTextView = convertView.findViewById(R.id.task_list_item_name);
            holder.taskListIcon = convertView.findViewById(R.id.task_list_icon);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        TextView taskListTitleTextView = holder.taskListTitleTextView;
        ImageView taskListIcon = holder.taskListIcon;

        TaskList taskList = (TaskList) getItem(position);

        taskListTitleTextView.setText(taskList.taskListName);
        taskListTitleTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
        taskListTitleTextView.setTextSize(18);

        if(taskListTitleTextView.getText().equals("All Tasks")){
            Picasso.with(mContext).load(R.drawable.inbox_colored).into(taskListIcon);
        } else if(taskListTitleTextView.getText().equals("Academic Calendar")){
            Picasso.with(mContext).load(R.drawable.calendar).into(taskListIcon);
        } else if(taskListTitleTextView.getText().equals("This Week")) {
            Picasso.with(mContext).load(R.drawable.thisweek).into(taskListIcon);
        } else if(taskListTitleTextView.getText().equals("Today")) {
            Picasso.with(mContext).load(R.drawable.today).into(taskListIcon);
        } else if(taskListTitleTextView.getText().equals("Someday")) {
            Picasso.with(mContext).load(R.drawable.someday).into(taskListIcon);
        } else{
            Picasso.with(mContext).load(R.drawable.list_slim).into(taskListIcon);
        }



        return convertView;
    }


    private static class ViewHolder{
        public TextView taskListTitleTextView;
        public ImageView taskListIcon;
    }
}
