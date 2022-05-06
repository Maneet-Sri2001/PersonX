package com.collegeproject.personx.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.collegeproject.personx.Database.DAO.NewsDAO;
import com.collegeproject.personx.Database.DAO.WeatherDAO;
import com.collegeproject.personx.Model.NewsModel;
import com.collegeproject.personx.Model.WeatherModel;

@Database(entities = {WeatherModel.class}, version = 1)
public abstract class WeatherDatabse extends RoomDatabase {
  public static WeatherDatabse weatherDatabse;
  
  public static synchronized WeatherDatabse getInstance(Context context) {
    if (weatherDatabse == null) {
      weatherDatabse = Room.databaseBuilder(context.getApplicationContext(), WeatherDatabse.class, "Weather")
          .fallbackToDestructiveMigration()
          .addCallback(roomCallback)
          .build();
    }
    return weatherDatabse;
  }
  
  public static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);
    }
  };
  
  public abstract WeatherDAO weatherDAO();
}
