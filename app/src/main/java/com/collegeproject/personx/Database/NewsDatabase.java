package com.collegeproject.personx.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.collegeproject.personx.Database.DAO.NewsDAO;
import com.collegeproject.personx.Model.NewsModel;

@Database(entities = {NewsModel.class}, version = 1)
public abstract class NewsDatabase extends RoomDatabase {
  public static NewsDatabase newsDatabase;
  
  public static synchronized NewsDatabase getInstance(Context context) {
    if (newsDatabase == null) {
      newsDatabase = Room.databaseBuilder(context.getApplicationContext(), NewsDatabase.class, "NewsCollection")
          .fallbackToDestructiveMigration()
          .addCallback(roomCallback)
          .build();
    }
    return newsDatabase;
  }
  
  public static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);
    }
  };
  
  public abstract NewsDAO newsDAO();
}
