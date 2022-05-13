package com.collegeproject.personx.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.collegeproject.personx.Model.UserModel;

@Dao
public interface UserDAO {
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void registerUser(UserModel userModal);
  
  @Query("DELETE FROM userTable")
  void delete();
  
  @Query("SELECT * FROM userTable")
  LiveData<UserModel> getUser();
  
  @Update
  void update(UserModel userModel);
}