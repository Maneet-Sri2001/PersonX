package com.collegeproject.personx.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.collegeproject.personx.Database.DAO.TaskDAO;
import com.collegeproject.personx.Model.TaskModel;

@Database(entities = {TaskModel.class}, version = 1)
public abstract class TaskDatabase extends RoomDatabase {
  public static final String DATABASE_NAME = "PersonX@TaskDB";
  public static TaskDatabase instance;
  
  public static synchronized TaskDatabase getInstance(Context context) {
    if (instance == null) {
      instance = Room.databaseBuilder(context.getApplicationContext(), TaskDatabase.class, DATABASE_NAME)
          .fallbackToDestructiveMigration()
          .addCallback(roomCallBack)
          .build();
    }
    return instance;
  }
  public static final RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
    
    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);
    }
  };
  public abstract TaskDAO taskDao();
}
