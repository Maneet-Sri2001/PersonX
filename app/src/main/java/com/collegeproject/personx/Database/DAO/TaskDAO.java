package com.collegeproject.personx.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.collegeproject.personx.Model.TaskModel;

import java.util.List;

@Dao
public interface TaskDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(TaskModel task);
  
  @Update
  void update(TaskModel task);
  
  @Delete
  void delete(TaskModel task);
  
  @Query("DELETE FROM taskTable")
  void deleteAll();
  
  @Query("SELECT * FROM taskTable")
  LiveData<List<TaskModel>> getAll();
  
  @Query("SELECT * FROM taskTable WHERE taskTable._id == :id")
  LiveData<TaskModel> getSingle(String id);
  
  @Query("SELECT * FROM taskTable WHERE taskTable.category == :category")
  LiveData<List<TaskModel>> getCategory(String category);
  
  @Query("UPDATE taskTable SET status = 'Completed' WHERE taskTable._id == :id")
  void updateTask(String id);
}
