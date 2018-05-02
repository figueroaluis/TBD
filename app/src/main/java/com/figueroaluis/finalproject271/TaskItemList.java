package com.figueroaluis.finalproject271;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

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



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_item_listview_layout);
        mContext = this;
        mToolBar = findViewById(R.id.task_list_toolbar);
        setSupportActionBar(mToolBar);

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

        /*
        ArrayList<String> tags = new ArrayList<String>();
        tags.add("Tag 1, Tag two, Tag three");
        Task task1 = new Task("Makeshift Title 1", "Makeshift Description 1", "99/99/9999", "99:99 a.m.", tags, "Important", "Audio.mp3");
        Task task2 = new Task("Makeshift Title 2", "Makeshift Description 2", "99/99/9999", "99:99 p.m.", tags, "Important", "Audio.mp3");
        Task task3 = new Task("Makeshift Title 3", "Makeshift Description 3", "99/99/9999", "99:99 a.m.", tags, "Important", "Audio.mp3");
        Testing
        final ArrayList<Task> taskList = new ArrayList<Task>();
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);
        */

        AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, "db_tasks").allowMainThreadQueries().build();
        taskDAO = database.getTaskDAO();
        taskList = new ArrayList<>();
        taskList.addAll(taskDAO.getTasks());
        adapter = new TaskItemAdapter(mContext, taskList);
        mListView = findViewById(R.id.task_item_listview);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task selectedTask = taskList.get(position);
                Intent detailIntent = new Intent(mContext, TaskDetailActivity.class);

                detailIntent.putExtra("taskID", selectedTask.getTaskID());

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
        taskList.addAll(taskDAO.getTasks());
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sort_listview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        return true;
    }



}
