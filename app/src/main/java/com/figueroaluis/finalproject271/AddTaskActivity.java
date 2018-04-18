package com.figueroaluis.finalproject271;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by luisfigueroa on 4/17/18.
 */

public class AddTaskActivity extends AppCompatActivity {
    private ImageButton cancel_new_task_button;
    private TextView add_task_title;
    private EditText add_task_title_input;
    private TextView add_task_date_title;
    private EditText add_task_date_input;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_activity);
    }



}
