package com.collegeproject.personx.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.collegeproject.personx.Model.TaskModel;
import com.collegeproject.personx.Repository.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
  public static TaskRepository taskRepository;
  public final LiveData<List<TaskModel>> allTask;
  
  public TaskViewModel(@NonNull Application application) {
    super(application);
    taskRepository = new TaskRepository(application);
    this.allTask = taskRepository.getAllTask();
  }
  public static void insert(TaskModel task) {
    taskRepository.insert(task);
  }
  
  public static void update(TaskModel task) {
    taskRepository.update(task);
  }
  
  public static void delete(TaskModel task) {
    taskRepository.delete(task);
  }
  
  public static void deleteAll() {
    taskRepository.deleteAll();
  }
  
  public LiveData<TaskModel> get(String id) {
    return taskRepository.getTask(id);
  }
  
  public LiveData<List<TaskModel>> getAllTask() {
    return allTask;
  }
}
