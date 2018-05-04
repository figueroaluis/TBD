package com.figueroaluis.finalproject271;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by luisfigueroa on 4/5/18.
 */
@Entity(tableName = "tasks")
public class Task {


    @PrimaryKey(autoGenerate = true)
    private long taskID;
    private String title;
    private String description;
    private String date;
    private String time;
    private String tags;
    private int importance;
    private String audioFileName;
    private String primaryTag;

    public String getPrimaryTag() {
        return primaryTag;
    }

    public void setPrimaryTag(String primaryTag) {
        this.primaryTag = primaryTag;
    }

    public Task(){

    }

    public String getTitle() {

        return title;
    }

    public long getTaskID() {
        return taskID;
    }

    public void setTaskID(long taskID) {
        this.taskID = taskID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public String getAudioFileName() {
        return audioFileName;
    }

    public void setAudioFileName(String audioFileName) {
        this.audioFileName = audioFileName;
    }


    public static ArrayList<Task> getTasksFromFile(String filename, Context context){
        ArrayList<Task> taskList = new ArrayList<Task>();


        // try to read from JSON file
        // get information by using the tags
        // construct recipe Object for each recipe in JSON
        // add each object to arrayList and return List
        try{
            String jsonString = loadJsonFromAsset(filename, context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray tasks = json.getJSONArray("schedule");
            // for loop to iterate through each recipe in recipes array
            for(int i = 0; i < tasks.length(); i++){
                Task task = new Task();
                task.title = tasks.getJSONObject(i).getString("Event");
                task.date = tasks.getJSONObject(i).getString("Date");
                task.primaryTag = "Academic Calendar";
                taskList.add(task);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return taskList;
    }
    // helper method that loads from any Json file
    private static String loadJsonFromAsset(String filename, Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

}
