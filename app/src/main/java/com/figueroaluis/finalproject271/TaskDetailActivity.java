package com.figueroaluis.finalproject271;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by luisfigueroa on 4/17/18.
 */

public class TaskDetailActivity extends AppCompatActivity{

    private Button editButton;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_detail_activity);
        AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, "db_tasks").allowMainThreadQueries().build();
        final TaskDAO taskDAO = database.getTaskDAO();
        final Task selectedTask = taskDAO.getTaskbyID(this.getIntent().getExtras().getLong("taskID"));

        final EditText titleEditView = findViewById(R.id.task_detail_title);
        titleEditView.setText(selectedTask.getTitle());
        final EditText dateEditView = findViewById(R.id.task_detail_date);
        dateEditView.setText(selectedTask.getDate());
        final EditText tagsEditView = findViewById(R.id.task_detail_tags);
        tagsEditView.setText(selectedTask.getTags());
        final EditText importanceEditView = findViewById(R.id.task_detail_importance);
        importanceEditView.setText(selectedTask.getImportance());
        final EditText descriptionEditView = findViewById(R.id.task_detail_description);
        descriptionEditView.setText(selectedTask.getDescription());
        editButton = findViewById(R.id.task_detail_edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedTask.setTitle(titleEditView.getText().toString());
                selectedTask.setDate(dateEditView.getText().toString());
                selectedTask.setTags(tagsEditView.getText().toString());
                selectedTask.setImportance(importanceEditView.getText().toString());
                selectedTask.setDescription(descriptionEditView.getText().toString());

                taskDAO.update(selectedTask);

                Toast.makeText(getApplicationContext(), "Task Updated", Toast.LENGTH_LONG).show();
            }
        });




    }


    public void returnToList(View view){
        Intent addTask = new Intent(getApplicationContext(), TaskItemList.class);
        this.startActivity(addTask);
    }
}
