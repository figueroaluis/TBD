package com.figueroaluis.finalproject271;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class TasksListDefaultTest extends AppCompatActivity {

    private ArrayList<TaskList> defaultLists;
    private TaskList taskList;
    private StoreRetrieveMainListsData storeRetrieveData;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storeRetrieveData = new StoreRetrieveMainListsData(this, MainActivity.FILENAME);
        defaultLists = MainActivity.getLocallyStoredData(storeRetrieveData);

        final TaskList INBOX_LIST = new TaskList("Inbox", new ArrayList<Task>());
        defaultLists.add(INBOX_LIST);
        saveData();
    }


    private void saveData(){
        try{
            storeRetrieveData.saveToFile(defaultLists);
        } catch (JSONException | IOException e){
            e.printStackTrace();
        }
    }
}
