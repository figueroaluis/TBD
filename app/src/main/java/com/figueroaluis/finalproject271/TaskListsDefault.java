package com.figueroaluis.finalproject271;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by luisfigueroa on 4/19/18.
 */

public class TaskListsDefault{

    // instance fields
    ArrayList<TaskList> defaultLists;
    private StoreRetrieveMainListsData storeRetrieveData;

    public TaskListsDefault() {
        defaultLists = new ArrayList<>();
        final TaskList INBOX_LIST = new TaskList("Inbox", new ArrayList<Task>());
        final TaskList TODAY = new TaskList("Today", new ArrayList<Task>());
        final TaskList THIS_WEEK = new TaskList("This Week", new ArrayList<Task>());

        // this is just a place holder
        final TaskList ADD_NEW_LIST = new TaskList("Create List...", new ArrayList<Task>());

        defaultLists.add(INBOX_LIST);
        defaultLists.add(TODAY);
        defaultLists.add(THIS_WEEK);

        // testing and placeholder
        defaultLists.add(ADD_NEW_LIST);
        // defaultLists.add(defaultLists.size()-1, THIS_WEEK2);
    }





    private void saveData(){
        try{
            storeRetrieveData.saveToFile(defaultLists);
        } catch (JSONException | IOException e){
            e.printStackTrace();
        }
    }


}
