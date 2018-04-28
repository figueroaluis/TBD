package com.figueroaluis.finalproject271;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TaskDAO {

    @Insert
    public void insert(Task... tasks);

    @Update
    public void update(Task... tasks);

    @Delete
    public void delete(Task... tasks);

    @Query("SELECT * FROM tasks")
    public List<Task> getTasks();

    @Query("SELECT * FROM tasks WHERE importance = :importance")
    public List<Task> getTaskWithImportance(String importance);

    @Query("SELECT * FROM tasks WHERE primaryTag = :primaryTag")
    public List<Task> getTaskWithPrimaryTag(String primaryTag);

    @Query("SELECT * FROM tasks WHERE date BETWEEN :start AND :end")
    public List<Task> getTaskByDateRange(String start, String end);

    @Query("SELECT * FROM tasks WHERE taskID = :taskID")
    public Task getTaskbyID(long taskID);

    @Query("SELECT * FROM tasks WHERE date = :date")
    public List<Task> getTaskBySingleDate(String date);
}
