package com.figueroaluis.finalproject271;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mshoga on 4/17/2018.
 */

public class TaskItemAdapter extends BaseAdapter implements Filterable {

    private Context mContext;
    private ArrayList<Task> mtaskList;
    private LayoutInflater mInflater;

    private ArrayList<Task> originalTasks;
    private ArrayList<Task> filteredTasks;

    public TaskItemAdapter(Context mContext, ArrayList<Task> mtaskList){
        this.mtaskList = mtaskList;
        this.mContext = mContext;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.originalTasks = mtaskList;
        this.filteredTasks = mtaskList;
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

    @Override
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                if(charSequence == null || charSequence.length() == 0){
                    results.values = originalTasks;
                    results.count = originalTasks.size();
                } else{
                    ArrayList<Task> filterResultData = new ArrayList<>();
                    for(Task task: originalTasks){
                        if(task.getTitle().toLowerCase().contains(charSequence.toString().toLowerCase())){
                            filterResultData.add(task);
                        }
                    }
                    results.values = filterResultData;
                    results.count = filterResultData.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mtaskList = (ArrayList<Task>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }



    private static class ViewHolder{
        public TextView taskTitleTextView;
        public TextView taskDateTextView;
        public TextView taskTimeTextView;

    }
}
