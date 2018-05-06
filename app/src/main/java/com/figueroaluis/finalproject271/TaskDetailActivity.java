package com.figueroaluis.finalproject271;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by luisfigueroa on 4/17/18.
 */

public class TaskDetailActivity extends AppCompatActivity{

    private Button editButton;
    private ImageButton playAudioButton;
    private String audioFilePath;
    private MediaPlayer mPlayer;
    private Task selectedTask;
    private TaskDAO taskDAO;
    public boolean isInFrontCal;
    public boolean inInFrontList;
    private TimePickerDialog timePicker;
    private DatePickerDialog datePicker;
    private String timeSelect;
    private String dateSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_detail_activity);
        AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, "db_tasks").allowMainThreadQueries().build();
        taskDAO = database.getTaskDAO();
        selectedTask = taskDAO.getTaskbyID(this.getIntent().getExtras().getLong("taskID"));
        isInFrontCal = this.getIntent().getBooleanExtra("isInFrontCal", isInFrontCal);
        playAudioButton = findViewById(R.id.task_detail_play_audio_button);
        audioFilePath = selectedTask.getAudioFileName();
        final EditText titleEditView = findViewById(R.id.task_detail_title);
        titleEditView.setText(selectedTask.getTitle());
        final EditText dateEditView = findViewById(R.id.task_detail_date);
        dateEditView.setText(selectedTask.getDate());
        dateSelect = selectedTask.getDate();
        final EditText tagsEditView = findViewById(R.id.task_detail_tags);
        tagsEditView.setText(selectedTask.getTags());
        final String[] importance = {"Low Priority", "Normal", "Important", "Very Important"};
        final EditText importanceEditView = findViewById(R.id.task_detail_importance);
        final EditText timeEditView = findViewById(R.id.task_detail_time);
        timeEditView.setText(selectedTask.getTime());
        timeSelect = selectedTask.getTime();

        importanceEditView.setText(importance[selectedTask.getImportance()]);
        final EditText descriptionEditView = findViewById(R.id.task_detail_description);
        descriptionEditView.setText(selectedTask.getDescription());

        timeEditView.setInputType(InputType.TYPE_NULL);
        timeEditView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker = new TimePickerDialog(TaskDetailActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                        timeSelect = String.format(Locale.getDefault(),"%02d:%02d", hour, minute);
                        timeEditView.setText(timeSelect);
                    }
                }, 0,0,true);
                timePicker.show();
            }
        });

        dateEditView.setInputType(InputType.TYPE_NULL);
        dateEditView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(calendar.YEAR);
                int month = calendar.get(calendar.MONTH);
                int day = calendar.get(calendar.DAY_OF_MONTH);

                datePicker = new DatePickerDialog(TaskDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        month++;
                        dateSelect=String.format(Locale.getDefault(),"%02d-%02d-%02d", year, month, dayOfMonth);
                        dateEditView.setText(dateSelect);
                    }
                }, year,month,day);
                datePicker.show();
            }
        });




        editButton = findViewById(R.id.task_detail_edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedTask.setTitle(titleEditView.getText().toString());
                selectedTask.setDate(dateSelect);
                selectedTask.setTags(tagsEditView.getText().toString());
                selectedTask.setTime(timeSelect);
                String importance = importanceEditView.getText().toString();
                if(importance.equals("Low Priority")) {
                    selectedTask.setImportance(0);
                }
                else if(importance.equals("Normal")) {
                    selectedTask.setImportance(1);
                }
                else if(importance.equals("Important")) {
                    selectedTask.setImportance(2);
                }
                else if(importance.equals("Very Important")) {
                    selectedTask.setImportance(3);
                }


                selectedTask.setDescription(descriptionEditView.getText().toString());

                taskDAO.update(selectedTask);

                Toast.makeText(getApplicationContext(), "Task Updated", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        playAudioButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (mPlayer == null) {
                    mPlayer = new MediaPlayer();
                    mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            mPlayer.release();
                            mPlayer = null;
                        }
                    });

                    try {
                        mPlayer.setDataSource(audioFilePath);
                        mPlayer.prepare();
                        mPlayer.start();
                    } catch (IOException e) {
                        Toast.makeText(TaskDetailActivity.this, "No File to Play", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    mPlayer.stop();
                    mPlayer.release();
                    mPlayer = null;
                }
            }


        });




    }
    @Override
    public void onStop(){
        super.onStop();
        if(mPlayer != null){
            mPlayer.release();
            mPlayer = null;
        }
    }


    public void returnToPrevious(View view){
        Intent addOrUpdateTask;
        if(isInFrontCal){
            addOrUpdateTask = new Intent(getApplicationContext(), CalendarActivity.class);
            this.startActivity(addOrUpdateTask);
        } else{
            addOrUpdateTask = new Intent(getApplicationContext(), TaskItemList.class);
            addOrUpdateTask.putExtra("PrimaryTag", selectedTask.getPrimaryTag());
            this.startActivity(addOrUpdateTask);
        }

    }

    public void returnToList(View view){
        Intent addTask = new Intent(getApplicationContext(), TaskItemList.class);
        this.startActivity(addTask);
    }

    public void deleteTask(View view){
        Intent deleteTask;
        if(!isInFrontCal){
            deleteTask = new Intent(getApplicationContext(), TaskItemList.class);
        }
        else{
            deleteTask = new Intent(getApplicationContext(), CalendarActivity.class);
        }
        if(selectedTask.getAudioFileName() != null) {
            File deleteFile = new File(selectedTask.getAudioFileName());
            deleteFile.delete();
        }
        deleteTask.putExtra("PrimaryTag", selectedTask.getPrimaryTag());
        taskDAO.delete(selectedTask);
        this.startActivity(deleteTask);

        Toast.makeText(TaskDetailActivity.this, "Successfully Deleted Task", Toast.LENGTH_SHORT).show();
    }

}
