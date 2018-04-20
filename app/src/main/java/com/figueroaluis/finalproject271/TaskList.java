package com.figueroaluis.finalproject271;

import java.util.ArrayList;

/**
 * Created by luisfigueroa on 4/18/18.
 */

public class TaskList {

    // fields for this class
    String taskListName;
    ArrayList<Task> taskList;

    // use the default constructor
    public TaskList(String taskListName, ArrayList<Task> taskList) {
        this.taskListName = taskListName;
        this.taskList = taskList;
    }

    public String getTaskListName() {
        return taskListName;
    }

    public void setTaskListName(String taskListName) {
        this.taskListName = taskListName;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }




}
