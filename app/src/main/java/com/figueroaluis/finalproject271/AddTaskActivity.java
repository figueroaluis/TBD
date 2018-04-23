package com.figueroaluis.finalproject271;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    private ImageButton audioRecordButton;
    private String audioFilePath;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_activity);
        final ArrayList<String> importanceList = new ArrayList<String>();
        audioRecordButton = findViewById(R.id.add_task_audio_button);
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
        audioRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startAudio = new Intent(getApplicationContext(), AudioRecordActivity.class);
                startActivityForResult(startAudio, 1);
            }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                audioFilePath = data.getStringExtra("audioFilePath");
                Toast.makeText(getApplicationContext(), "Audio Saved", Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == RESULT_CANCELED){
                Toast.makeText(getApplicationContext(), "No Audio Saved", Toast.LENGTH_LONG).show();
            }
        }
    }




}
