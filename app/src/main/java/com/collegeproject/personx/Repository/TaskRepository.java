package com.collegeproject.personx.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.collegeproject.personx.Database.DAO.TaskDAO;
import com.collegeproject.personx.Database.TaskDatabase;
import com.collegeproject.personx.Model.TaskModel;

import java.util.List;

public class TaskRepository {
  private final TaskDAO taskDAO;
  public final LiveData<List<TaskModel>> allTask;
  
  public TaskRepository(Application application) {
    TaskDatabase database = TaskDatabase.getInstance(application);
    this.taskDAO = database.taskDao();
    this.allTask = taskDAO.getAll();
  }
  
  public void insert(TaskModel taskModal) {
    new InsertTaskAsync(taskDAO).execute(taskModal);
  }
  
  public void update(TaskModel taskModal) {
    new UpdateTaskAsync(taskDAO).execute(taskModal);
  }
  
  public void completeTask(TaskModel taskModal) {
    new CompleteTaskAsync(taskDAO).execute(taskModal);
  }
  
  public void delete(TaskModel taskModal) {
    new DeleteTaskAsync(taskDAO).execute(taskModal);
  }
  
  public void deleteAll() {
    new DeleteAllTaskAsync(taskDAO).execute();
  }
  
  public LiveData<List<TaskModel>> getAllTask() {
    return allTask;
  }
  
  public LiveData<TaskModel> getTask(String id) {
    return taskDAO.getSingle(id);
  }
  
  public LiveData<List<TaskModel>> getTaskCategory(String category) {
    return taskDAO.getCategory(category);
  }
  
  //Async Task Insert
  private static class InsertTaskAsync extends AsyncTask<TaskModel, Void, Void> {
    private final TaskDAO taskDAO;
    
    public InsertTaskAsync(TaskDAO taskDAO) {
      this.taskDAO = taskDAO;
    }
    
    @Override
    protected Void doInBackground(TaskModel... taskModals) {
      taskDAO.insert(taskModals[0]);
      return null;
    }
  }
  
  //Async Task Update
  private static class UpdateTaskAsync extends AsyncTask<TaskModel, Void, Void> {
    private final TaskDAO taskDAO;
    
    public UpdateTaskAsync(TaskDAO taskDAO) {
      this.taskDAO = taskDAO;
    }
    
    @Override
    protected Void doInBackground(TaskModel... taskModals) {
      taskDAO.update(taskModals[0]);
      return null;
    }
  }
  
  //Async Task TaskComplete
  private static class CompleteTaskAsync extends AsyncTask<TaskModel, Void, Void> {
    private final TaskDAO taskDAO;
    
    public CompleteTaskAsync(TaskDAO taskDAO) {
      this.taskDAO = taskDAO;
    }
    
    @Override
    protected Void doInBackground(TaskModel... taskModals) {
      taskDAO.updateTask(taskModals[0].get_id());
      return null;
    }
  }
  
  //Async Task Delete
  private static class DeleteTaskAsync extends AsyncTask<TaskModel, Void, Void> {
    private final TaskDAO taskDAO;
    
    public DeleteTaskAsync(TaskDAO taskDAO) {
      this.taskDAO = taskDAO;
    }
    
    @Override
    protected Void doInBackground(TaskModel... taskModals) {
      taskDAO.delete(taskModals[0]);
      return null;
    }
  }
  
  //Async All Task Delete
  private static class DeleteAllTaskAsync extends AsyncTask<TaskModel, Void, Void> {
    private final TaskDAO taskDAO;
    
    public DeleteAllTaskAsync(TaskDAO taskDAO) {
      this.taskDAO = taskDAO;
    }
    
    @Override
    protected Void doInBackground(TaskModel... taskModals) {
      taskDAO.deleteAll();
      return null;
    }
  }
}
