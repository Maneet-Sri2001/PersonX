package com.collegeproject.personx.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.collegeproject.personx.Model.WeatherModel;

@Dao
public interface WeatherDAO {
  @Insert
  void insert(WeatherModel weatherModel);
  
  @Query("SELECT * FROM weatherrecord")
  LiveData<WeatherModel> getAll();
  
  @Query("DELETE FROM weatherrecord")
  void deleteAll();
}
