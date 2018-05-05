package com.figueroaluis.finalproject271;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Task.class, TaskList.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDAO getTaskDAO();
    public abstract ListDAO getListDAO();

}
