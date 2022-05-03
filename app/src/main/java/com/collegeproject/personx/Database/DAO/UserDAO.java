package com.collegeproject.personx.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;

import com.collegeproject.personx.Model.UserModel;
@Dao
public interface UserDAO {
  
  @Insert
  void registerUser(UserModel userModal);
}
