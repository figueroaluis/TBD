package com.figueroaluis.finalproject271;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;
    private RelativeLayout addTaskLayout;
    private RelativeLayout mRelativeLayout;
//    private DrawerLayout mDrawerLayout;
    private FloatingActionButton add_to_do_button;
    private ListView mListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set mContext
        mContext = getApplicationContext();
        // get the main activity
        mActivity = MainActivity.this;

        // ---------- Main Lists -----------
        // data to display
        final ArrayList<TaskList> MainItemLists = new ArrayList<>();
        final TaskList INBOX_LIST = new TaskList("Inbox", new ArrayList<Task>());
        final TaskList TODAY = new TaskList("Today", new ArrayList<Task>());
        final TaskList THIS_WEEK = new TaskList("This Week", new ArrayList<Task>());
        MainItemLists.add(INBOX_LIST);
        MainItemLists.add(TODAY);
        MainItemLists.add(THIS_WEEK);
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


        // create the adapter
        mRelativeLayout = findViewById(R.id.main_layout);
        add_to_do_button =  findViewById(R.id.add_to_do_button);


        // first set on click listener to open the second activity
        add_to_do_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
                startActivity(intent);
            }
        });



        // add new functions to make the app work better
    }
}
