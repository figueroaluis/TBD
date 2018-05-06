package com.figueroaluis.finalproject271;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ListDAO {

    @Insert
    public void insert(TaskList... taskLists);

    @Update
    public void update(TaskList... taskLists);

    @Delete
    public void delete(TaskList... taskLists);

    @Query("SELECT * FROM lists")
    public List<TaskList> getLists();

    @Query("SELECT * FROM lists WHERE taskListName = :taskListName")
    public TaskList getListByName(String taskListName);
}
