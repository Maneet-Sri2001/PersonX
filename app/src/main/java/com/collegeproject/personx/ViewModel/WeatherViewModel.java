package com.collegeproject.personx.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.collegeproject.personx.Model.WeatherModel;
import com.collegeproject.personx.Repository.WeatherRepo;

public class WeatherViewModel extends AndroidViewModel {
  public static WeatherRepo weatherRepo;
  public final LiveData<WeatherModel> weather;
  
  public WeatherViewModel(@NonNull Application application) {
    super(application);
    weatherRepo = new WeatherRepo(application);
    this.weather = weatherRepo.get();
  }
  
  public static void insert(WeatherModel weatherModel) {
    weatherRepo.insert(weatherModel);
  }
  
  public static void delete() {
    weatherRepo.deleteAll();
  }
  
  public LiveData<WeatherModel> getWeather() {
    return weather;
  }
}
