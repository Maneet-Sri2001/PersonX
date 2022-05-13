package com.collegeproject.personx.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.collegeproject.personx.Database.DAO.UserDAO;
import com.collegeproject.personx.Database.UserDatabase;
import com.collegeproject.personx.Model.UserModel;

public class UserRepo {
  private final UserDAO userDAO;
  public final LiveData<UserModel> userModelLiveData;
  
  public UserRepo(Application application) {
    UserDatabase userDatabase = UserDatabase.getInstance(application);
    this.userDAO = userDatabase.userDao();
    userModelLiveData = userDAO.getUser();
  }
  
  public void insert(UserModel model) {
    new InsUser(userDAO).execute(model);
  }
  
  public void update(UserModel model) {
    new UpdateUser(userDAO).execute(model);
  }
  
  public LiveData<UserModel> get() {
    return userModelLiveData;
  }
  
  public void deleteAll() {
    new UserRepo.Delete(userDAO).execute();
  }
  
  private static class InsUser extends AsyncTask<UserModel, Void, Void> {
    private final UserDAO userDAO;
    
    private InsUser(UserDAO userDAO) {
      this.userDAO = userDAO;
    }
    
    @Override
    protected Void doInBackground(UserModel... userModels) {
      userDAO.registerUser(userModels[0]);
      return null;
    }
  }
  
  private static class Delete extends AsyncTask<UserModel, Void, Void> {
    private final UserDAO userDAO;
    
    private Delete(UserDAO userDAO) {
      this.userDAO = userDAO;
    }
    
    @Override
    protected Void doInBackground(UserModel... userModels) {
      userDAO.delete();
      return null;
    }
  }
  
  private static class UpdateUser extends AsyncTask<UserModel, Void, Void> {
    private final UserDAO userDAO;
  
    private UpdateUser(UserDAO userDAO) {
      this.userDAO = userDAO;
    }
  
    @Override
    protected Void doInBackground(UserModel... userModels) {
      userDAO.update(userModels[0]);
      return null;
    }
  }
}
