package com.figueroaluis.finalproject271;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by luisfigueroa on 4/18/18.
 */

@Entity(tableName = "lists")
public class TaskList {

    // fields for this class
    @PrimaryKey
    @NonNull
    String taskListName;
    //ArrayList<Task> taskList;
    //private static final String TASKLISTNAME = "tasklistname";


    // use the default constructor
    public TaskList(String taskListName) {
        this.taskListName = taskListName;
        //this.taskList = taskList;
    }


    public String getTaskListName() {
        return taskListName;
    }

    public void setTaskListName(String taskListName) {
        this.taskListName = taskListName;
    }

    /*
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }
    */





}
