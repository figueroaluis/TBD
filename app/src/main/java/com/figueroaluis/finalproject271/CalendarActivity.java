package com.figueroaluis.finalproject271;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {


    private CalendarView calendarView;
    private Context mContext;
    private Button backButton;
    private ArrayList<Task> taskList;
    private ListView calendarListView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);
        mContext = this;
        taskList = new ArrayList<Task>();

        Task task1 = new Task();
        task1.setTitle("Testing title");
        task1.setDate("Testing date");
        task1.setTime("Testing time");
        taskList.add(task1);

        calendarView = findViewById(R.id.calendarView);
        calendarListView = findViewById(R.id.calendar_listview);
        final TaskItemAdapter adapter = new TaskItemAdapter(mContext, taskList);
        calendarListView.setAdapter(adapter);
        calendarListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task selectedTask = taskList.get(position);
                Intent detailIntent = new Intent(mContext, TaskDetailActivity.class);

                detailIntent.putExtra("taskID", selectedTask.getTaskID());


                startActivity(detailIntent);
            }
        });
        backButton = findViewById(R.id.calendar_back_button);
        AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, "db_tasks").allowMainThreadQueries().build();
        final TaskDAO taskDAO = database.getTaskDAO();

        // On click equivalent

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth){
                month++;
                String monthOutput = String.format(Locale.getDefault(),"%02d", month);
                String dayOutput = String.format(Locale.getDefault(),"%02d", dayOfMonth);
                Toast.makeText(mContext, "Year: " + year + " Month: " + month + " Day: " + dayOfMonth,Toast.LENGTH_LONG).show();
                if(taskDAO.getTaskBySingleDate(year+"-"+monthOutput+"-"+dayOutput) != null){
                    Toast.makeText(mContext, "getTask not null", Toast.LENGTH_SHORT).show();
                    taskList.clear();
                    taskList.addAll(taskDAO.getTaskBySingleDate(year+"-"+monthOutput+"-"+dayOutput));
                    //taskList.addAll(taskDAO.getTasks());
                    adapter.notifyDataSetChanged();

                }



            }
        });

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent goBack = new Intent(mContext, MainActivity.class);
                startActivity(goBack);
            }

        });


    }



}
