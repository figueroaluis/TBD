package com.figueroaluis.finalproject271;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Mshoga on 4/17/2018.
 */

public class TaskItemAdapter extends BaseAdapter implements Filterable {

    private Context mContext;
    private ArrayList<Task> mtaskList;
    private LayoutInflater mInflater;
    private TaskDAO taskDAO;

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
    public View getView(final int position, View convertView, ViewGroup parent){
        ViewHolder holder;

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.task_item_layout, parent, false);

            holder = new ViewHolder();
            holder.checkBox = convertView.findViewById(R.id.task_checkBox);
            holder.taskTitleTextView = convertView.findViewById(R.id.list_task_title);
            holder.taskDateTextView = convertView.findViewById(R.id.list_task_date);
            holder.taskTimeTextView = convertView.findViewById(R.id.list_task_time);
            holder.taskTagTextView = convertView.findViewById(R.id.list_task_tags);
            convertView.setTag(holder);

        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        CheckBox checkBox = holder.checkBox;
        TextView taskTitleTextView = holder.taskTitleTextView;
        TextView taskDateTextView = holder.taskDateTextView;
        TextView taskTimeTextView = holder.taskTimeTextView;
        TextView taskTagTextView = holder.taskTagTextView;

        final Task task = (Task) getItem(position);

        taskTitleTextView.setText(task.getTitle());
        taskTitleTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
        taskTitleTextView.setTextSize(18);

        taskDateTextView.setText(task.getDate());
        taskDateTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccentBlue));
        taskDateTextView.setTextSize(14);

        taskTimeTextView.setText(task.getTime());
        taskTimeTextView.setTextSize(14);

        taskTagTextView.setText(task.getTags());

        taskTagTextView.setTextSize(12);

        final AppDatabase database = Room.databaseBuilder(mContext, AppDatabase.class, "db_tasks").allowMainThreadQueries().build();
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox) view).isChecked()){
                    // delete it from the dao
                    Task selectedTask = (Task) getItem(position);
                    taskDAO = database.getTaskDAO();
                    taskDAO.delete(selectedTask);
                    Toast.makeText(mContext, "Completed Task", Toast.LENGTH_LONG).show();
                } else if(!((CheckBox) view).isChecked()){
                    Task selectedTask = (Task) getItem(position);
                    taskDAO = database.getTaskDAO();
                    taskDAO.insert(selectedTask);
                }
            }
        });

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
                        if(task.getTitle().toLowerCase().contains(charSequence.toString().toLowerCase())
                                || (task.getTags()!=null && task.getTags().toLowerCase().contains(charSequence.toString().toLowerCase()))){
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
        public CheckBox checkBox;
        public TextView taskTitleTextView;
        public TextView taskDateTextView;
        public TextView taskTimeTextView;
        public TextView taskTagTextView;

    }
}
