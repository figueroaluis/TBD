package com.figueroaluis.finalproject271;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by luisfigueroa on 4/18/18.
 */

public class DefaultLists extends AppCompatActivity {

    private Context mContext;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

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

                // create the intent to open the next view

            }
        });
    }


    // this is for testing purposes
    public ArrayList<Task> testTasks(){
        ArrayList<Task> taskArrayList = new ArrayList<>();
        ArrayList<String> tags = new ArrayList<>();
        tags.add("Tag 1, Tag 2, Tag 3");
        Task task1 = new Task("Makeshift Title 1", "Makeshift Description 1", "99/99/9999", "99:99 a.m.", tags, "Important", "Audio.mp3");
        Task task2 = new Task("Makeshift Title 2", "Makeshift Description 2", "99/99/9999", "99:99 p.m.", tags, "Important", "Audio.mp3");
        Task task3 = new Task("Makeshift Title 3", "Makeshift Description 3", "99/99/9999", "99:99 a.m.", tags, "Important", "Audio.mp3");
        taskArrayList.add(task1);
        taskArrayList.add(task2);
        taskArrayList.add(task3);
        return taskArrayList;
    }

}


