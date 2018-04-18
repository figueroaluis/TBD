package com.figueroaluis.finalproject271;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by luisfigueroa on 4/17/18.
 */

public class AddTaskActivity extends AppCompatActivity {
    private ImageButton cancel_new_task_button;
    private TextView add_task_title;
    private EditText add_task_title_input;
    private TextView add_task_date_title;
    private EditText add_task_date_input;
    private String importanceSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_activity);
        final ArrayList<String> importanceList = new ArrayList<String>();
        Spinner importanceDropdown = findViewById(R.id.add_task_importance_dropdown);
        importanceList.add("Normal");
        importanceList.add("Low Priority");
        importanceList.add("Important");
        importanceList.add("Very Important");
        ArrayAdapter<String> importanceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, importanceList);
        importanceDropdown.setAdapter(importanceAdapter);
        importanceDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                importanceSelect = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {importanceSelect = "Normal";}
        });


    }

    public void cancelTask(View view){
        Intent cancel = new Intent(getApplicationContext(), MainActivity.class);
        this.startActivity(cancel);
    }

    public void addTask(View view){
        Intent addTask = new Intent(getApplicationContext(), MainActivity.class);
        // need to add access to SQLite or something else here
        this.startActivity(addTask);
    }



}
