package com.collegeproject.personx.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.collegeproject.personx.Model.UserModel;
import com.collegeproject.personx.Repository.UserRepo;
import com.uk.tastytoasty.TastyToasty;

public class UserViewModel extends AndroidViewModel {
  public static UserRepo userRepo;
  public final LiveData<UserModel> user;
  
  public UserViewModel(@NonNull Application application) {
    super(application);
    userRepo = new UserRepo(application);
    this.user = userRepo.get();
  }
  
  public static void insert(UserModel userModel) {
    userRepo.insert(userModel);
  }
  
  public static void delete() {
    userRepo.deleteAll();
  }
  
  public LiveData<UserModel> getUser() {
    return user;
  }
  
  public static void updateUser(UserModel user) {
    userRepo.update(user);
  }
}
