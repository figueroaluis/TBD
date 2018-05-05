package com.figueroaluis.finalproject271;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by Mshoga on 4/17/2018.
 */

public class TaskItemList extends AppCompatActivity {

    private Context mContext;
    private ListView mListView;
    private Toolbar mToolBar;
    TaskItemAdapter adapter;
    private SearchView searchView;
    private ArrayList<Task> taskList;
    private TaskDAO taskDAO;
    public boolean isInFront;
    static final Comparator<Task> IMPORTANCE_ORDER = new Comparator<Task>(){
        public int compare(Task t1, Task t2){
            return t2.getImportance()-t1.getImportance();
        }
    };
    static final Comparator<Task> DATE_ORDER = new Comparator<Task>(){
        public int compare(Task t1, Task t2){
            return t1.getDate().compareTo(t2.getDate());
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_item_listview_layout);
        mContext = this;
        mToolBar = findViewById(R.id.task_list_toolbar);
        setSupportActionBar(mToolBar);

        mToolBar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent backButton = new Intent(getApplicationContext(), MainActivity.class);
                mContext.startActivity(backButton);
            }
        });

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        /*
        mToolBar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
            }
        });
        */

        searchView = findViewById(R.id.search);


        AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, "db_tasks").allowMainThreadQueries().build();
        taskDAO = database.getTaskDAO();
        taskList = new ArrayList<>();
        populateList();
        adapter = new TaskItemAdapter(mContext, taskList);
        mListView = findViewById(R.id.task_item_listview);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task selectedTask = (Task) parent.getAdapter().getItem(position);
                //Task selectedTask = taskList.get(position);
                Intent detailIntent = new Intent(mContext, TaskDetailActivity.class);

                detailIntent.putExtra("taskID", selectedTask.getTaskID());
                detailIntent.putExtra("isInFrontList", isInFront);

                startActivity(detailIntent);
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                //adapter.notifyDataSetChanged();
                return false;
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onResume(){
        super.onResume();
        taskList.clear();
        populateList();
        adapter.notifyDataSetChanged();

        isInFront = true;
    }

    @Override
    public void onPause() {
        super.onPause();

        isInFront = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sort_listview, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                if(NavUtils.getParentActivityName(this ) != null){
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }


    public void sortByImportance(MenuItem item){
        Collections.sort(taskList, IMPORTANCE_ORDER);
        adapter.notifyDataSetChanged();
    }

    public void sortByDate(MenuItem item){
        Collections.sort(taskList, DATE_ORDER);
        adapter.notifyDataSetChanged();
    }

    public void populateList(){
        String primaryTag = "";
        if(this.getIntent().getExtras().getString("PrimaryTag") != null) {
            primaryTag = this.getIntent().getExtras().getString("PrimaryTag");
        }

        if(primaryTag.equals("All Tasks")){
            taskList.addAll(taskDAO.getTasks());
        }
        else if(primaryTag.equals("Today")){
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(calendar.YEAR);
            int month = calendar.get(calendar.MONTH);
            int day = calendar.get(calendar.DAY_OF_MONTH);
            month++;
            String dateSelect=String.format(Locale.getDefault(),"%02d-%02d-%02d", year, month, day);
            taskList.addAll(taskDAO.getTaskBySingleDate(dateSelect));
        }
        else if(primaryTag.equals("This Week")){
            Calendar calendar = Calendar.getInstance();
            int startYear = calendar.get(calendar.YEAR);
            int startMonth = calendar.get(calendar.MONTH);
            int startDay = calendar.get(calendar.DAY_OF_MONTH);
            startMonth++;
            calendar.add(Calendar.DAY_OF_MONTH, 7);
            int endYear = calendar.get(calendar.YEAR);
            int endMonth = calendar.get(calendar.MONTH);
            int endDay = calendar.get(calendar.DAY_OF_MONTH);
            endMonth++;
            String dateStart=String.format(Locale.getDefault(),"%02d-%02d-%02d", startYear, startMonth, startDay);
            String dateEnd=String.format(Locale.getDefault(),"%02d-%02d-%02d", endYear, endMonth, endDay);
            taskList.addAll(taskDAO.getTaskByDateRange(dateStart,dateEnd));
        }
        else{
            taskList.addAll(taskDAO.getTaskWithPrimaryTag(primaryTag));
        }
    }



}
