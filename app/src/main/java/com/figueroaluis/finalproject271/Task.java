package com.figueroaluis.finalproject271;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by luisfigueroa on 4/5/18.
 */

public class Task {

    private String title;
    private String description;
    private String date;
    private String time;
    private ArrayList<String> tags;
    private String importance;
    private String audioFileName;

    public Task(String title, String description, String date, String time, ArrayList<String> tags, String importance, String audioFileName) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.tags = tags;
        this.importance = importance;
        this.audioFileName = audioFileName;
    }

    public Task(){

    }

    public String getTitle() {

        return title;
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

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
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
            String jsonString = loadJsonFromAsset("spring2018.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray tasks = json.getJSONArray("schedule");
            // for loop to iterate through each recipe in recipes array
            for(int i = 0; i < tasks.length(); i++){
                Task task = new Task();
                task.title = tasks.getJSONObject(i).getString("Event");
                task.date = tasks.getJSONObject(i).getString("Date");
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
