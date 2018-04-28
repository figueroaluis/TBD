package com.figueroaluis.finalproject271;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

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
    private EditText add_task_tags_input;
    private EditText add_task_description_input;
    private EditText add_task_time_input;
    private TimePickerDialog timePicker;
    private DatePickerDialog datePicker;
    private String timeSelect;
    private String dateSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_activity);
        final ArrayList<String> importanceList = new ArrayList<String>();
        audioRecordButton = findViewById(R.id.add_task_audio_button);
        Spinner importanceDropdown = findViewById(R.id.add_task_importance_dropdown);
        add_task_title_input = findViewById(R.id.add_task_title_input);
        add_task_date_input = findViewById(R.id.add_task_date_input);
        add_task_tags_input = findViewById(R.id.add_task_tags_input);
        add_task_description_input = findViewById(R.id.add_task_description_input);
        add_task_time_input = findViewById(R.id.add_task_time_input);
        audioFilePath = "";
        dateSelect="";
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

        add_task_time_input.setInputType(InputType.TYPE_NULL);
        add_task_time_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker = new TimePickerDialog(AddTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                        add_task_time_input.setText(String.format("%02d:%02d", hour, minute));
                        timeSelect = hour+":"+minute;
                    }
                }, 0,0,true);
                timePicker.show();
            }
        });

        add_task_date_input.setInputType(InputType.TYPE_NULL);
        add_task_date_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(calendar.YEAR);
                int month = calendar.get(calendar.MONTH);
                int day = calendar.get(calendar.DAY_OF_MONTH);

                datePicker = new DatePickerDialog(AddTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        add_task_date_input.setText(month +"/"+dayOfMonth+"/"+year);
                        dateSelect=year+"-"+month+"-"+dayOfMonth;
                    }
                }, year,month,day);
                datePicker.show();
            }
        });





    }

    public void cancelTask(View view){
        Intent cancel = new Intent(getApplicationContext(), MainActivity.class);
        this.startActivity(cancel);
    }

    public void addTask(View view){
        Intent addTask = new Intent(getApplicationContext(), MainActivity.class);
        AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, "db_tasks").allowMainThreadQueries().build();
        TaskDAO taskDAO = database.getTaskDAO();
        Task task = new Task();
        task.setTitle(add_task_title_input.getText().toString());
        task.setDate(dateSelect);
        task.setDescription(add_task_description_input.getText().toString());
        task.setAudioFileName(audioFilePath);
        task.setImportance(importanceSelect);
        task.setTags(add_task_tags_input.getText().toString());
        task.setTime(timeSelect);

        taskDAO.insert(task);
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
