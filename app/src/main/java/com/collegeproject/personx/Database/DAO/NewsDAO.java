package com.collegeproject.personx.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.collegeproject.personx.Model.NewsModel;

import java.util.List;

@Dao
public interface NewsDAO {
  @Insert
  void insert(NewsModel newsModel);
  
  @Query("SELECT * FROM newsTable")
  LiveData<List<NewsModel>> getAll();
  
  @Query("DELETE FROM newsTable")
  void deleteAll();
}
