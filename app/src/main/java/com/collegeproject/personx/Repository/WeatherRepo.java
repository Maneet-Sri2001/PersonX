package com.collegeproject.personx.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.collegeproject.personx.Database.DAO.WeatherDAO;
import com.collegeproject.personx.Database.WeatherDatabse;
import com.collegeproject.personx.Model.WeatherModel;

public class WeatherRepo {
  private final WeatherDAO weatherDAO;
  public final LiveData<WeatherModel> weatherModelLiveData;
  
  public WeatherRepo(Application application) {
    WeatherDatabse weatherDatabse = WeatherDatabse.getInstance(application);
    this.weatherDAO = weatherDatabse.weatherDAO();
    weatherModelLiveData = weatherDAO.getAll();
  }
  public void insert(WeatherModel model) {
    new WeatherRepo.InsWeather(weatherDAO).execute(model);
  }
  
  public LiveData<WeatherModel> get() {
    return weatherModelLiveData;
  }
  
  public void deleteAll() {
    new WeatherRepo.Delete(weatherDAO).execute();
  }
  
  //Async Add
  private static class InsWeather extends AsyncTask<WeatherModel, Void, Void> {
    private final WeatherDAO weatherDAO;
  
    private InsWeather(WeatherDAO weatherDAO) {
      this.weatherDAO = weatherDAO;
    }
  
    @Override
    protected Void doInBackground(WeatherModel... weatherModels) {
      weatherDAO.insert(weatherModels[0]);
      return null;
    }
  }
  
  //Async Delete
  private static class Delete extends AsyncTask<WeatherModel, Void, Void> {
   private final WeatherDAO weatherDAO;
  
    private Delete(WeatherDAO weatherDAO) {
      this.weatherDAO = weatherDAO;
    }
  
    @Override
    protected Void doInBackground(WeatherModel... weatherModels) {
      weatherDAO.deleteAll();
      return null;
    }
  }
}
