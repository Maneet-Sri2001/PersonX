package com.collegeproject.personx.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.collegeproject.personx.Database.DAO.UserDAO;
import com.collegeproject.personx.Model.UserModel;

@Database(entities = {UserModel.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
  
  private static final String dbName = "PersonX@UserDB";
  private static UserDatabase userDatabase;
  
  public static synchronized UserDatabase getUserRoomDatabase(Context context) {
    if (userDatabase == null) {
      userDatabase = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, dbName)
          .fallbackToDestructiveMigration()
          .addCallback(roomCallBack)
          .build();
    }
    return userDatabase;
  }
  
  private static final RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);
    }
  };
  
  public abstract UserDAO userDao();
}
