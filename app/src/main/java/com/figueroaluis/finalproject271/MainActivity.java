package com.figueroaluis.finalproject271;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;
    private RelativeLayout addTaskLayout;
    private RelativeLayout mRelativeLayout;
    private Toolbar mToolbar;
    private FloatingActionButton add_to_do_button;
    private ListView mListView;
    private ArrayList<TaskList> MainItemLists;
    private ArrayList<String> MainItemListNames;
    private StoreRetrieveMainListsData storeRetrieveData;
    public static final String FILENAME = "mainLists.json";
/*
    public static ArrayList<TaskList> getLocallyStoredData(StoreRetrieveMainListsData storeRetrieveData){
        ArrayList<TaskList> lists = null;
        try {
            lists  = storeRetrieveData.loadFromFile();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        if(lists == null){
            lists = new ArrayList<>();
        }
        return lists;
    }
*/

    // ---------------------- OnCreate() ----------------------- //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set mContext
        mContext = getApplicationContext();
        // get the main activity
        mActivity = MainActivity.this;
        mRelativeLayout = findViewById(R.id.main_layout);
        // set the tool bar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("firstRun",false)){
            AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, "db_tasks").allowMainThreadQueries().build();
            TaskDAO taskDAO = database.getTaskDAO();
            ArrayList<Task> academicTasks = Task.getTasksFromFile("spring2018.json", this);
            for(Task t : academicTasks){
                taskDAO.insert(t);
            }

            ListDAO listDAO = database.getListDAO();
            ArrayList<TaskList> listsToDB = new TaskListsDefault().defaultLists;
            for(TaskList tl : listsToDB){
                listDAO.insert(tl);
            }


            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstRun", true);
            editor.commit();

        }
        // MainLists from json file
        //storeRetrieveData = new StoreRetrieveMainListsData(this, FILENAME);
        //MainItemLists = getLocallyStoredData(storeRetrieveData);



        // ---------- Main Lists -----------
        // data to display

        AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, "db_tasks").allowMainThreadQueries().build();
        ListDAO listDAO = database.getListDAO();

        MainItemLists = new ArrayList<>();
        MainItemLists.addAll(listDAO.getLists());

        // MainItemLists = new TaskListsDefault().defaultLists;
        MainItemListNames = new TaskListsDefault().defaultListsNames;

        TaskListAdapter adapter = new TaskListAdapter(mContext, MainItemLists);
        mListView = findViewById(R.id.main_lists_list_view);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                TaskList selectedTaskList = MainItemLists.get(position);


                Intent detailIntent = new Intent(mContext, TaskItemList.class);
                startActivity(detailIntent);
            }
        });
        // ----------- End Main Lists, try to put in another file ----------


        add_to_do_button =  findViewById(R.id.add_to_do_button);
        add_to_do_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
                intent.putExtra("MainListNamesPrimaryTag", MainItemListNames);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.calendar_menu_button:
                Intent cal_intent = new Intent(this, CalendarActivity.class);
                startActivity(cal_intent);
                return true;
            /*
            case R.id.email_overview_menu_button:
                Intent email_intent = new Intent(this, EmailActivity.class);
                startActivity(email_intent);
                return true;
            case R.id.settings_menu_button:
                Intent settings_intent = new Intent(this, SettingsActivity.class);
                startActivity(settings_intent);
                return true;
            */
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
