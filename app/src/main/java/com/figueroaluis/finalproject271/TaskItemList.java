package com.figueroaluis.finalproject271;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_item);
        mContext = this;
        ArrayList<String> tags = new ArrayList<String>();
        tags.add("Tag 1, Tag two, Tag three");
        Task task1 = new Task("Makeshift Title 1", "Makeshift Description 1", "99/99/9999", "99:99 a.m.", tags, "Important", "Audio.mp3");
        Task task2 = new Task("Makeshift Title 2", "Makeshift Description 2", "99/99/9999", "99:99 p.m.", tags, "Important", "Audio.mp3");
        Task task3 = new Task("Makeshift Title 3", "Makeshift Description 3", "99/99/9999", "99:99 a.m.", tags, "Important", "Audio.mp3");
        final ArrayList<Task> taskList = new ArrayList<Task>();
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);
        TaskItemAdapter adapter = new TaskItemAdapter(mContext, taskList);
        mListView = findViewById(R.id.task_item_listview);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task selectedTask = taskList.get(position);
                Intent detailIntent = new Intent(mContext, TaskDetailActivity.class);

                detailIntent.putExtra("title", selectedTask.getTitle());

                startActivity(detailIntent);
            }
        });

    }

}
