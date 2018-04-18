package com.figueroaluis.finalproject271;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by luisfigueroa on 4/17/18.
 */

public class TaskDetailActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_detail_activity);
        String title = this.getIntent().getExtras().getString("title");

        TextView titleTextView = findViewById(R.id.task_detail_title);
        titleTextView.setText("Task Title: " + title);

    }


    public void returnToList(View view){
        Intent addTask = new Intent(getApplicationContext(), TaskItemList.class);
        this.startActivity(addTask);
    }
}
